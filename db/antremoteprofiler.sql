------------------------------------------------
-- Export file for user BUILDPROFILER         --
-- Created by mmarani on 31/08/2018, 09:49:59 --
------------------------------------------------

spool antremoteprofiler.log

prompt
prompt Creating function ELAPSED_TO_SECONDS
prompt ====================================
prompt
create or replace function elapsed_to_seconds(start_ts timestamp, end_ts timestamp) return number is

begin
  return (extract(second from end_ts-start_ts)
    + 60 * (extract(minute from end_ts-start_ts)
      + 60 * (extract(hour from end_ts-start_ts)
        + 24 * (extract(day from end_ts-start_ts) ))));
        
end elapsed_to_seconds;
/

prompt
prompt Creating view V_PROFILED_BUILD
prompt ==============================
prompt
create or replace view v_profiled_build as
select id_build,
       projectname,
       hostname,
       username,
       cast(start_ts as date) start_date,
       cast(end_ts as date) end_date,
       (end_ts - start_ts) elapsed,
       elapsed_to_seconds(start_ts,end_ts) elapsed_sec,
       success
  from profiled_build;

prompt
prompt Creating view V_PROFILED_TARGET
prompt ===============================
prompt
create or replace view v_profiled_target as
select b.id_build,
       b.projectname,
       t.id_target,
       targetname,
       cast(t.start_ts as date) start_date,
       cast(t.end_ts as date) end_date,
       (t.end_ts - t.start_ts) elapsed,
       elapsed_to_seconds(t.start_ts,t.end_ts) elapsed_sec
  from profiled_target t, v_profiled_build b
 where b.id_build = t.id_build
order by id_target;

prompt
prompt Creating view V_AGGREGATE_TARGETS
prompt =================================
prompt
CREATE OR REPLACE VIEW V_AGGREGATE_TARGETS AS
SELECT projectname,targetname,sum(t.elapsed_sec) sum_elapsed_sec,avg(t.elapsed_sec) avg_elapsed_sec,
       count(1) num_executions
  FROM v_profiled_target t
group by projectname,targetname
order by sum_elapsed_sec desc;

prompt
prompt Creating view V_DAYS_STATS
prompt ==========================
prompt
create or replace view v_days_stats as
select trunc(start_date) start_day,
       round(sum(elapsed_sec)) tot_sec,
       round(avg(elapsed_sec)) avg_build_sec,
       count(distinct username) distinct_users,
       round(sum(elapsed_sec)/count(distinct username)) avg_user_sec
  from v_profiled_build
 group by trunc(start_date)
 order by start_day desc;

prompt
prompt Creating view V_LAST_TARGET
prompt ===========================
prompt
create or replace view v_last_target as
select b.id_build,t.id_target,b.start_date,b.elapsed_sec build_elapsed_sec,b.projectname,t.targetname,success from v_profiled_build b, v_profiled_target t
where t.id_build = b.id_build
and not exists (select 1 from profiled_target t1 where t1.id_build=t.id_build and t1.id_target>t.id_target);


spool off

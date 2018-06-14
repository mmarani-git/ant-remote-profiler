------------------------------------------------
-- Export file for user BUILDPROFILER         --
-- Created by mmarani on 14/06/2018, 17:57:12 --
------------------------------------------------

spool views.log

prompt
prompt Creating view V_PROFILED_BUILD
prompt ==============================
prompt
create or replace view buildprofiler.v_profiled_build as
select id_build, projectname, hostname, username, start_ts, end_ts, (end_ts-start_ts) elapsed from profiled_build;

prompt
prompt Creating view V_PROFILED_TARGET
prompt ===============================
prompt
create or replace view buildprofiler.v_profiled_target as
select id_build, id_target, targetname, start_ts, end_ts, (end_ts-start_ts) elapsed  from profiled_target;


spool off

------------------------------------------------
--this view has been removed when the task management has been removed
------------------------------------------------

spool removed.log

prompt
prompt Creating view V_PROFILED_TASK
prompt =============================
prompt
create or replace view buildprofiler.v_profiled_task as
select id_build, ta.id_target, id_task, taskname, ta.start_ts, ta.end_ts, (ta.end_ts-ta.start_ts) elapsed from profiled_task ta, profiled_target tg where tg.id_target=ta.id_target;


spool off

db is created with the auto-ddl update feature of hibernate.
Here I commit some useful view:
v_profiled_build:		summary on build
v_profiled_target:		summary on single targets
v_last_target			for each build, shows the last target - so that you can find which one was requested or has failed
v_aggregate_targets:		shows aggregate stats on each target
v_days_stats:			shows aggregate stats on a daily basis

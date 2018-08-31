ANT REMOTE PROFILER

Custom ant logger to send to a database build stats.
Stats are collected for whole builds and single targets. No tasks.
The logger extends the default one so no feature is lost.
Database is self-build by hibernate using the autoddl feature.

NOTE
The database location is in server/persistence.xml and currently points to the C4C development database!

INSTALLATION
- deploy the client jar in your ant/lib directory
- deploy the server war on any appserver (tested on wildfly 8.2)

on the client, set these two env variables:
ANT_OPTS=-DRemoteProfilerHost=172.19.223.225 -DRemoteProfilerPort=80
	(hostname/ip and port of the appserver where the war is deployed)
	
ANT_ARGS=-logger eu.dedalus.cis.tools.ant.logger.remoteprofiler.RemoteProfilerLogger
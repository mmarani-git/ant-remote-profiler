call mvn --batch-mode release:update-versions -DdevelopmentVersion=%1 -DautoVersionSubmodules=true
call mvn install
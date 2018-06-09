see https://stackoverflow.com/questions/25128219/missing-artifact-com-oracleojdbc6jar11-2-0-in-pom-xml


mvn install:install-file -Dfile=ojdbc6.jar -DgroupId=com.oracle -DartifactId=ojdbc6 -Dversion=11.2.0 -Dpackaging=jar
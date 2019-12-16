@echo off
mvn install:install-file -Dfile=./sapjco3.jar -DgroupId=de.fh.albsig.digitalfactory -DartifactId=sapjco3 -Dversion=1.0.0 -Dpackaging=jar -DgeneratePom=true
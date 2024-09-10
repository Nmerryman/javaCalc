
default:
	mvn package
	java -cp "target\my-app-1.0-SNAPSHOT.jar;target\dependency\*" dev.hydris.cover.App

install:
	mvn clean install -U
	mvn dependency:copy-dependencies



default:
	mvn package
	java -cp .\target\my-app-1.0-SNAPSHOT.jar com.hydris.cover.App


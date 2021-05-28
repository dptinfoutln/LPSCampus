mvn clean install
mvn sonar:sonar  \
	-D sonar.branch.name="$(git rev-parse --abbrev-ref HEAD|tr / _ )" \
	-DskipTests=true \
	--activate-profiles sonar

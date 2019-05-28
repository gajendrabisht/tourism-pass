# Tourism Pass

* API documentation on swagger 'http://localhost:5000/swagger-ui.html'
* To use existing data update datasource.url with checkout directory in 'src/main/resources/application.yml' 
* BDD tests using Cucumber Java. Run './gradlew clean cucumber'
* TDD using Junit 5. './gradlew clean test'
* H2 file persistent store for production - ../data/
* H2 in memory for cucumber integration tests

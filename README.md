# GitHubRepositoryViewer
<!-- ABOUT THE PROJECT -->
## 🎓 About The Project 
GitHub repository viewer is an app allowing quick preview of basic information about repositories. App itself is gaining data concerning repositories from github-provided rest API endpoints. Application uses HTTP protocol to both send data to the user, and retrieve it from GitHub. During development I did my best to follow both Clean Code and SOLID principles.

<!-- TECHNOLOGIES USED -->
## 🔨  Technologies used 
* [Java](https://www.java.com/)
* [Gradle](https://gradle.org/)
* [Spring Boot](https://spring.io/projects/spring-boot)
* [JUnit 5](https://junit.org/junit5/)
* [Mockito](https://site.mockito.org/)
* [WireMock](http://wiremock.org/)

<!-- PREREQUISITES -->
## ⚙️Prerequisites
* Java 16
* Gradle

<!-- INSTALLATION AND USAGE -->
## 🧭 Installation 
Application can be set up in two ways. First:

* clone repository

  `git clone https://github.com/hantczak/GitHubRepositoryViewer.git`

* cd into project directory

* run project using 
  
  `gradle bootRun` 

 Second approach is not as tricky, as it just revolves around starting the app in the IDE.

<!-- INSTALLATION AND USAGE -->
## 🎯 Functionalities 
In current state of the app, all endpoints are accessible via HTTP requests on localhost:8080.

App has three basic functionalities. It can provide its user with list of all public repositories owned by a particular user, and also return sum of all stars assigned to his repositories. Third function enables user to search for particular user's repository. All three endpoints are shown below:

* GET all public repositories owned by a user

  `http://localhost:8080/users/{userName}/repositories`

* GET particular user repository by its name

  `http://localhost:8080/repos/{userName}/{repositoryName}`

* GET summed number of stars from all user repositories

  `http://localhost:8080/users/{userName}/starSum`

<!-- REMARKS -->
## ⚠️ Remarks 

* I've decided to add functionality regarding searching for single repository, since it was natural for me to enable user to not only obtain a list of repositories, but also a single one that he was searching for.

* Validator used to validate usernames before connecting to GitHub API was created to omit unnecessary requests to API, that would anyway return status code 404. I assumed, that if username is not a valid one, it cannot be searched for in my application. I could not find rules for creating valid usernames in GitHub documentation, however the rules are listed out on the Sign Up screen.

    <a href="https://imgbb.com/"><img src="https://i.ibb.co/G7SCndj/Github-rules.jpg" alt="Github-rules" border="0"></a>

    Since I could not find similar rules for repository names, I've decided not to create validator for them.

<!-- STATUS -->
## 🏗️ Ideas for further development 

* First idea that comes to my mind is adding possibility to list which users starred a particular repository. In this case it would be wise to create it as a separate, standalone module, with own controller, service and package, named `users`. Implementation of this feature would be trivial, becouse GitHub provides an endpoint for this data:

  `https://api.github.com/repos/{username}/{repositoryName}/stargazers}`
  
* Second feature coming to my mind is returning average star number for user repositories. It could use same list of repositories as fuction returning sum of stars. This value could be calculated in service and returned as a field inside UserStarInfoDto object, alongside sum of stars of the user.

* As GitHub also provides endpoint for getting repositories assigned to an organisation, so it would be a good idea to implement also this feature. The endpoint is:

  `https://api.github.com/orgs/{org}/repos`

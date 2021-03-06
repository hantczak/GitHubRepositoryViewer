![CI](https://github.com/hantczak/GitHubRepositoryViewer/actions/workflows/gradle.yml/badge.svg)
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
* [Docker](https://www.docker.com/)

<!-- PREREQUISITES -->
## ⚙️Prerequisites
* Java 16
* Gradle
* Docker *(optional)*

<!-- INSTALLATION AND USAGE -->
## 🧭 Installation
Application can be set up in three distinct ways. 

### First way:
* clone repository

  `git clone https://github.com/hantczak/GitHubRepositoryViewer.git`

* cd into project directory

* run project using

  `gradle bootRun`

### Second way:
* Approach revolves around using docker to set up docker container and start app inside it. Docker image can be downloaded directly from DockerHub with using:

  `docker run -p 8080:8080 -it hantczak/github-repository-viewer`

* or either built locally with Dockerfile that can be found in the main folder of the project:

  `docker build . -t github-repository-viewer`

* and then:

  `docker run -p 8080:8080 -it github-repository-viewer`

### Third way:
Third approach is not as tricky, as it just revolves around starting the app in the IDE.

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

* Validator used to validate usernames before connecting to GitHub API was created to omit unnecessary requests to API, that would anyway return status code 404. I assumed, that if username is not a valid one, it cannot be searched for in my application. I could not find rules for creating valid usernames in GitHub documentation, however the rules are listed out on the Sign Up screen.

  <a href="https://imgbb.com/"><img src="https://i.ibb.co/G7SCndj/Github-rules.jpg" alt="Github-rules" border="0"></a>

  Since I could not find similar rules for repository names, I've decided not to create validator for them.

<!-- STATUS -->
## 🏗️ Ideas for further development

Built in educational purposes, project is still in development phase, implementation of additional functionalities is possible.

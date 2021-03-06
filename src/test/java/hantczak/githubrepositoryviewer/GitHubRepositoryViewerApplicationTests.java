package hantczak.githubrepositoryviewer;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import static com.github.tomakehurst.wiremock.client.WireMock.*;


@ActiveProfiles("integration")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GitHubRepositoryViewerApplicationTests {

    protected RestTemplate restTemplate = new RestTemplate();

    @LocalServerPort
    protected int port;

    protected WireMockServer wireMockServer;

    @BeforeEach
    protected void init() {
        this.wireMockServer = new WireMockServer(8089);
        wireMockServer.start();
    }

    @AfterEach
    protected void cleanup(){
        this.wireMockServer.stop();
    }

    protected void stubGithubRepositoriesProviderForRepoList(String username, int statusCode) {
        wireMockServer.stubFor(get(urlEqualTo("/users/" + username + "/repos?page=1"))
                .willReturn(aResponse()
                        .withStatus(statusCode)
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBodyFile("repositoryListResponse.json"))
        );
    }

    protected void stubGithubRepositoriesProviderForRepoListEmptySecondPage(String username, int statusCode) {
        wireMockServer.stubFor(get(urlEqualTo("/users/" + username + "/repos?page=2"))
                .willReturn(aResponse()
                        .withStatus(statusCode)
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBodyFile("emptyListResponse.json"))
        );
    }

    protected void stubGithubRepositoriesProviderForSingleRepo(String username,String repoName, int statusCode) {
        String urlBase = "http://localhost:" + "8089";
        wireMockServer.stubFor(get(urlEqualTo("/repos/" + username + "/" + repoName))
                .willReturn(aResponse()
                        .withStatus(statusCode)
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBodyFile("singleRepositoryResponse.json"))
        );
    }
}

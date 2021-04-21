package hantczak.githubrepositoryviewer.repository.domain;

import hantczak.githubrepositoryviewer.repository.infrastructure.api.GithubRepositoriesProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryFacadeConfiguration {

    @Bean
    public RepositoryFacade repositoryFacade(){
        RepositoryInfoAccessInterface githubRepositoriesProvider = new GithubRepositoriesProvider();
        RepositoryService repositoryService = new RepositoryService(githubRepositoriesProvider);
        return new RepositoryFacade(repositoryService);
    }
}

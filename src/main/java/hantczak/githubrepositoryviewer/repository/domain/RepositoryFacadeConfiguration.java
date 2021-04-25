package hantczak.githubrepositoryviewer.repository.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryFacadeConfiguration {

    @Bean
    public RepositoryFacade repositoryFacade(RepositoryProvider repositoryProvider) {
        UserNameValidator userNameValidator = new UserNameValidator();
        RepositoryService repositoryService = new RepositoryService(repositoryProvider, userNameValidator);
        return new RepositoryFacade(repositoryService);
    }
}

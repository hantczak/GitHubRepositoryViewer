package hantczak.githubrepositoryviewer.repository.domain;

public class RepositoryProviderException extends RuntimeException{

    public RepositoryProviderException(Throwable cause) {
        super("Repository provider exception", cause);
    }
}

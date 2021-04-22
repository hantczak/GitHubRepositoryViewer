package hantczak.githubrepositoryviewer.repository.domain;

public class RepositoryDoesNotExistException extends RuntimeException{
    private final String username;
    private final String repositoryName;

    public RepositoryDoesNotExistException(String username,String repositoryName) {
        super("Repository provider exception - repository: "+ repositoryName + ", belonging to user: " + username + " - does not exist.");
        this.username = username;
        this.repositoryName=repositoryName;
    }
}
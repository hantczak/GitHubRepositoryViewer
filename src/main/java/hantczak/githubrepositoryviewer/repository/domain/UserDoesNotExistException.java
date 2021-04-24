package hantczak.githubrepositoryviewer.repository.domain;

public class UserDoesNotExistException extends RuntimeException {
    private final String username;

    public UserDoesNotExistException(String username) {
        super("Repository provider exception - user does not exist - " + username);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}

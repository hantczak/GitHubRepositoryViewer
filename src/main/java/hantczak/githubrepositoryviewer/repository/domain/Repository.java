package hantczak.githubrepositoryviewer.repository.domain;

public class Repository {

    private final String repositoryName;
    private final long starsCount;

    public Repository(String repositoryName, long starsCount) {
        this.repositoryName = repositoryName;
        this.starsCount = starsCount;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public long getStarsCount() {
        return starsCount;
    }
}


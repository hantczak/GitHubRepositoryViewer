package hantczak.githubrepositoryviewer.repository.domain;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Repository that = (Repository) o;
        return starsCount == that.starsCount && repositoryName.equals(that.repositoryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(repositoryName, starsCount);
    }

    @Override
    public String toString() {
        return "Repository{" +
                "repositoryName='" + repositoryName + '\'' +
                ", starsCount=" + starsCount +
                '}';
    }
}


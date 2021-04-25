package hantczak.githubrepositoryviewer.repository.domain;

import java.util.Objects;

public class UserStarInfo {
    private final Long starSum;

    public UserStarInfo(Long starSum) {
        this.starSum = starSum;
    }

    public Long getStarSum() {
        return starSum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserStarInfo userStarInfo1 = (UserStarInfo) o;
        return Objects.equals(starSum, userStarInfo1.starSum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(starSum);
    }
}

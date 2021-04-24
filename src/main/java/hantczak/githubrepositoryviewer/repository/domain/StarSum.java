package hantczak.githubrepositoryviewer.repository.domain;

import java.util.Objects;

public class StarSum {
    private final Long starSum;

    public StarSum(Long starSum) {
        this.starSum = starSum;
    }

    public Long getStarSum() {
        return starSum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StarSum starSum1 = (StarSum) o;
        return Objects.equals(starSum, starSum1.starSum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(starSum);
    }
}

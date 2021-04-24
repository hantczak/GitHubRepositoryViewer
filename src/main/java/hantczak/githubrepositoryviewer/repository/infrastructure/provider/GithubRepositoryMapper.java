package hantczak.githubrepositoryviewer.repository.infrastructure.provider;

import hantczak.githubrepositoryviewer.repository.domain.Repository;

import java.util.List;
import java.util.stream.Collectors;

public class GithubRepositoryMapper {

    public static Repository fromDto(GithubRepositoryDto githubRepositoryDto) {
        return new Repository(githubRepositoryDto.getRepositoryName(),
                githubRepositoryDto.getStarsCount());
    }

    public static List<Repository> GithubRepositoryDtoListToRepositoryList(List<GithubRepositoryDto> repositories) {
        return repositories.stream()
                .map(GithubRepositoryMapper::fromDto)
                .collect(Collectors.toList());
    }
}


package hantczak.githubrepositoryviewer.repository.infrastructure.api;

import hantczak.githubrepositoryviewer.repository.domain.Repository;

import java.util.List;
import java.util.stream.Collectors;

public class RepositoryMapper {

    public static RepositoryDto toDto(Repository repository) {
        return new RepositoryDto(repository.getRepositoryName(),
                repository.getStarsCount());
    }

    public static Repository fromDto(RepositoryDto repositoryDto) {
        return new Repository(repositoryDto.getRepositoryName(),
                repositoryDto.getStarsCount());
    }

    public static List<RepositoryDto> repositoryListToRepositoryDtoList(List<Repository> repositories) {
        return repositories.stream()
                .map(RepositoryMapper::toDto)
                .collect(Collectors.toList());
    }

    public static List<Repository> repositoryDtoListToRepositoryList(List<RepositoryDto> repositories) {
        return repositories.stream()
                .map(RepositoryMapper::fromDto)
                .collect(Collectors.toList());
    }
}

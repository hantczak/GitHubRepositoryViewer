package hantczak.githubrepositoryviewer.repository.domain;

import java.util.List;
import java.util.Optional;

public class RepositoryService {
    private final RepositoryProvider repositoryInfoAccess;
    private final UserNameValidator userNameValidator;

    public RepositoryService(RepositoryProvider repositoryInfoAccess, UserNameValidator userNameValidator) {
        this.repositoryInfoAccess = repositoryInfoAccess;
        this.userNameValidator = userNameValidator;
    }

    public List<Repository> getAllUserRepositories(String userName) {
        userNameValidator.validateUserName(userName);
        return repositoryInfoAccess.getAllUserRepositories(userName);
    }

    public Optional<Repository> getRepositoryByName(String userName, String repoName) {
        userNameValidator.validateUserName(userName);
        return repositoryInfoAccess.getRepositoryByName(userName, repoName);
    }

    public Optional<Long> getSumOfStarsForAllUserRepositories(String userName) {
        userNameValidator.validateUserName(userName);
        List<Repository> repositories = getAllUserRepositories(userName);
        if (repositories.isEmpty()) {
            return Optional.empty();
        }
        Long starSum = repositories.stream()
                .mapToLong(Repository::getStarsCount)
                .sum();
        return Optional.of(starSum);
    }
}

//        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
//        ObjectMapper mapper = new ObjectMapper();
//        Repository[] repositoryArray=null;
//        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        try{
//            repositoryArray=mapper.readValue(response.getBody(),Repository[].class);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        System.out.println(Arrays.toString(repositoryArray));
//        List<Repository> repositoryList = new ArrayList<>();
//        Arrays.stream(repositoryArray)
//                .forEach(repository -> repositoryList.add(repository));
//        return repositoryList;

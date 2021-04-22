package hantczak.githubrepositoryviewer.repository.domain;

import java.util.List;

public class InvalidUserNameException extends RuntimeException{

    InvalidUserNameException(List<String> usernameErrors){
        super(usernameErrors.toString());
    }
}

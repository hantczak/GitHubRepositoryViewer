package hantczak.githubrepositoryviewer.repository.domain;

import java.util.ArrayList;
import java.util.List;

public class UserNameValidator {

    public UserNameValidator() {
    }

    void validateUserName(String userName){
        List<String> errors = new ArrayList<>();
        String regex = "^[a-zA-Z0-9-]*$";

        if(userName.length()>39){
            errors.add("Username is too long. Please choose shorter one.");
        }

        if(userName.startsWith("-")){
            errors.add("Username cannot start with the hyphen sign.");
        }

        if(userName.endsWith("-")){
            errors.add("Username cannot end with the hyphen sign.");
        }

        if(userName.contains("--")){
            errors.add("Username cannot contain multiple consecutive hyphens.");
        }

        if(userName.contains(" ")){
            errors.add("Username cannot contain spaces.");
        }

        if(!userName.matches(regex)){
            errors.add("Username can only contain alphanumeric characters or hyphens.");
        }

        if(!errors.isEmpty()){
            throw new InvalidUserNameException(errors);
        }
    }
}

package hantczak.githubrepositoryviewer;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "application.yml",ignoreResourceNotFound = true)
public class AppConfig {

}

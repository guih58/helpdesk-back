package tech.guih58.helpdesk.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import tech.guih58.helpdesk.services.DBService;

@Configuration
@Profile("test")
public class testConfig {
    @Autowired
    private DBService dbService;

    @Bean
    public void instanciaDB(){
        this.dbService.instanciaDB();
    }
}

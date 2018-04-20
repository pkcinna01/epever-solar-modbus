package com.xmonit.solar.modbus;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.CommandLinePropertySource;
import org.springframework.core.env.SimpleCommandLinePropertySource;


@SpringBootApplication
@EnableAutoConfiguration
public class App {

    @Autowired
    AppConfig cfg;

    public static void main(String[] args) {

        SpringApplication.run(App.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {

        return args -> {

            try {
                CommandLinePropertySource propSrc = new SimpleCommandLinePropertySource(args);

                //Optional.ofNullable(propSrc.getProperty("cmd")).ifPresent(str -> cfg.cmd = str);
                // Optional.ofNullable(propSrc.getProperty("commPortRegEx")).ifPresent(str -> cfg.commPortRegEx = str);
                //Optional.ofNullable(propSrc.getProperty("remoteHostRegEx")).ifPresent(str -> cfg.remoteHostRegEx = str);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        };
    }

}
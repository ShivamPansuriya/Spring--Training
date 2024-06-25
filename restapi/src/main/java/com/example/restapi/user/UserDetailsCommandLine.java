package com.example.restapi.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsCommandLine implements CommandLineRunner {

    private UserRepository repository;

    public UserDetailsCommandLine(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        repository.save(new UserDetails("John", "admin"));
        repository.save(new UserDetails("Mike", "user"));
        repository.save(new UserDetails("Shivam", "admin"));


    }
}

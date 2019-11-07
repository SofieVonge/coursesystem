package dk.kea.kurser.services;

import dk.kea.kurser.dto.Credentials;
import dk.kea.kurser.models.User;
import dk.kea.kurser.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User authenticatedAs(Credentials credentials) {
        return userRepository.findByEmailAndSecret(credentials.getEmail(), credentials.getSecret());
    }
}

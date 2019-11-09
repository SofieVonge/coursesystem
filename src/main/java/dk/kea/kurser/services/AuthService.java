package dk.kea.kurser.services;

import dk.kea.kurser.dto.Credentials;
import dk.kea.kurser.models.User;
import dk.kea.kurser.repositories.UserRepository;
import org.springframework.stereotype.Service;

/**
 * @author Andreas Dan Petersen
 * @since 09-11-2019
 * @version 1.0
 */
@Service
public class AuthService {

    private UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Authentication service used with login
     * @param credentials the credentials of the user authenticating
     * @return the user that was authenticated
     */
    public User authenticatedAs(Credentials credentials) {
        return userRepository.findByEmailAndSecret(credentials.getEmail(), credentials.getSecret());
    }
}

package dk.kea.kurser.services;

import dk.kea.kurser.models.User;
import dk.kea.kurser.repositories.UserRepository;
import dk.kea.kurser.models.UserPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public AuthDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(s);
        if (user == null) {
            throw new UsernameNotFoundException("Username was not found.");
        }

        return new UserPrincipal(user);
    }
}

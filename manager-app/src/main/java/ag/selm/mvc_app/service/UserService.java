package ag.selm.mvc_app.service;

import ag.selm.mvc_app.entity.Authority;
import ag.selm.mvc_app.entity.SelmagUser;
import ag.selm.mvc_app.repositoty.AuthorityRepository;
import ag.selm.mvc_app.repositoty.SelmagUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class UserService {

    private final SelmagUserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    public boolean isUserExists(String username, String passwordHash) {
        System.out.println(username+ passwordHash);
        return userRepository.existsByUsernameOrPasswordHash(username, passwordHash);
    }

    public Authority findAuthority(String role) {
        // Ищем роль в базе данных
        Authority authority = authorityRepository.findByAuthority(role);
        return authority;
    }

    public void saveUser(SelmagUser user) {
        userRepository.save(user);
    }
}
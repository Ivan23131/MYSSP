package ag.selm.mvc_app.repositoty;

import ag.selm.mvc_app.entity.SelmagUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SelmagUserRepository extends CrudRepository<SelmagUser, Integer> {

    Optional<SelmagUser> findByUsername(String username);

    boolean existsByUsernameOrPasswordHash(String username, String passwordHash);
}

package ag.selm.mvc_app.repositoty;

import ag.selm.mvc_app.entity.Authority;
import org.springframework.data.repository.CrudRepository;

public interface AuthorityRepository extends CrudRepository<Authority, Integer> {
    // Поиск роли по названию
    Authority findByAuthority(String authority);
}
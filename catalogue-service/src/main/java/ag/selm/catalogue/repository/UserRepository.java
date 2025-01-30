package ag.selm.catalogue.repository;

import ag.selm.catalogue.entity.SelmagUser;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<SelmagUser, Integer> {
}

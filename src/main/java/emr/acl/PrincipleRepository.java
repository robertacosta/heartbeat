package emr.acl;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "user", path = "user")
public interface PrincipleRepository extends JpaRepository<Principle, Long> {
	List<Principle> findByUsername(@Param("username") String username);
}

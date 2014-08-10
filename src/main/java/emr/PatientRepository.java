package emr;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "patient", path = "patient")
public interface PatientRepository extends MongoRepository<Patient, String> {

	List<Patient> findByLastName(@Param("last_name") String lastName);

}
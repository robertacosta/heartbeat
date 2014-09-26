package emr.patient.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import emr.patient.model.Patient;

@RepositoryRestResource(collectionResourceRel = "patient", path = "patient")
public interface PatientRepository extends MongoRepository<Patient, String> {
	
	List<Patient> findByLastName(@Param("last_name") String lastName);
	List<Patient> findByFirstNameAndLastName(@Param("first_name") String firstName, @Param("last_name") String lastName);
	List<Patient> findById(@Param("id") String id);  // Probably change to predicate
	
	// On the listing it should show: Name (Last, First), Medical Record Number (ID), Date of Birthday
	// Make a predicate for advanced search (birthday and ID)
}
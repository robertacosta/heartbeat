package emr.assessment.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import emr.assessment.model.Assessment;

@RepositoryRestResource(collectionResourceRel = "assessment", path = "assessment")
public interface AssessmentRepository extends MongoRepository<Assessment, String> {

}

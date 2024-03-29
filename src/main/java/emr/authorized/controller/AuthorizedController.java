package emr.authorized.controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import emr.acl.model.Principle;
import emr.acl.repository.PrincipleRepository;
import emr.assessment.model.Assessment;
import emr.assessment.repository.AssessmentRepository;
import emr.authorized.model.AssociateAssessmentModel;
import emr.authorized.model.AssociatePatientModel;
import emr.authorized.model.UpdatePasswordModel;
import emr.patient.model.Patient;
import emr.patient.repository.PatientRepository;

@RestController
@RequestMapping("/authorized")
public class AuthorizedController {
	@Autowired
	PrincipleRepository adminRepo;
	
	@Autowired
	PatientRepository patientRepo;
	
	@Autowired
	AssessmentRepository assessmentRepo;
	
	DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/dd/yyyy");

	// If someone can access this endpoint, they have permission to use the EMR
	@RequestMapping(value="/verify", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
    public Principle verify(@RequestParam(value="username", required=true) String username) {
		List<Principle> users = adminRepo.findByUsername(username);
		if(users.size() == 1) {
			return users.get(0);
		}
		return null;
    }

	// Returns the list of Patients that are assigned to a nurse
	@RequestMapping(value="/patients", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
    public List<Patient> patientList(@RequestParam(value="userid", required=true) Long userId) {
		Principle user = adminRepo.findOne(userId);
		List<Patient> patients = new ArrayList<Patient>();
		Set<String> patientIds = user.getPatientIds();
		if(patientIds != null) {
			for(String patientHref : patientIds) {
				patients.add(patientRepo.findOne(getId(patientHref)));
			}
		}
		
		return patients;
    }
	
	// Strips the patient ID from the end of the resource HREF
	private String getId(String patientHref) {
		try {
			URL patientUrl = new URL(patientHref);
			String path = patientUrl.getPath();
			String[] pathElements = path.split("/");
			return pathElements[pathElements.length - 1];
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// Assigns a patient to a nurse
	@RequestMapping(value="/patients", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public Principle addPatientToList(@RequestBody AssociatePatientModel model) {
		Principle user = adminRepo.findOne(model.getUserId());
		Set<String> patientIds = user.getPatientIds();
		if(!patientIds.contains(model.getPatientId())) {
			patientIds.add(model.getPatientId());
			adminRepo.save(user);
		}
        return user;
    }
	
	// Removes a patient from a nurse
	@RequestMapping(value="/patients", method=RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public Principle deletePatientFromList(@RequestBody AssociatePatientModel model) {
		Principle user = adminRepo.findOne(model.getUserId());
		Set<String> patientIds = user.getPatientIds();
		if(patientIds.contains(model.getPatientId())) {
			patientIds.remove(model.getPatientId());
			adminRepo.save(user);
		}
        return user;
    }
	
	// Returns the list of Patient assessments
	@RequestMapping(value="/assessments", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
    public List<Assessment> assessmentList(@RequestParam(value="patientid", required=true) String userId) {
		Patient patient = patientRepo.findOne(userId);
		List<Assessment> assessments = new ArrayList<Assessment>();
		List<String> assessmentIds = patient.getAssessments();
		if(assessmentIds != null) {
			for(String assessmentId : assessmentIds) {
				assessments.add(assessmentRepo.findOne(assessmentId));
			}
		}
		
		return assessments;
    }
	
	// Creates a new assessment and associates it to a patient
	@RequestMapping(value="/assessment", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public Assessment addAssessment(@RequestBody AssociateAssessmentModel model) {
		Assessment createdAssessment = assessmentRepo.save(model.getAssessment());
		Patient patient = patientRepo.findOne(model.getPatientId());
		List<String> assessmentIds = patient.getAssessments();
		if(assessmentIds == null) {
			assessmentIds = new ArrayList<String>();
		}
		assessmentIds.add(createdAssessment.getId());
		patient.setAssessments(assessmentIds);
		patientRepo.save(patient);

		return createdAssessment;
	}
	
	// Removes an assessment from a patient and deletes it
	@RequestMapping(value="/assessment", method=RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public Patient deleteAssessment(@RequestBody AssociateAssessmentModel model) {
		Logger logger = LoggerFactory.getLogger(AuthorizedController.class);
	    logger.info("DELETE Assessment: %s from Patient: %s", model.getAssessment().getId(), model.getPatientId());

		Patient patient = patientRepo.findOne(model.getPatientId());
		List<String> assessmentIds = patient.getAssessments();
		Assessment deletedAssement = model.getAssessment();
		if(assessmentIds.contains(deletedAssement.getId())) {
			assessmentIds.remove(deletedAssement.getId());
			patientRepo.save(patient);
			assessmentRepo.delete(deletedAssement);
		}

		return patient;
	}

	@RequestMapping(value="/password", method=RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public Principle changePassword(@RequestBody UpdatePasswordModel model) {
		Principle user = adminRepo.findOne(model.getUserId());
		StandardPasswordEncoder encoder = new StandardPasswordEncoder(System.getenv("SALT"));
		String encodedPassword = encoder.encode(model.getPassword());
		user.setPassword(encodedPassword);
		user.setLastPasswordChange(LocalDate.now().toString(formatter));
		adminRepo.save(user);
        return user;
	}
}

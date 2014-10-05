package emr.authorized.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import emr.acl.model.Principle;
import emr.acl.repository.PrincipleRepository;
import emr.authorized.model.AddPatientModel;
import emr.patient.model.Patient;
import emr.patient.repository.PatientRepository;

@RestController
@RequestMapping("/authorized")
public class AuthorizedController {
	@Autowired
	PrincipleRepository adminRepo;
	
	@Autowired
	PatientRepository patientRepo;
	
	@RequestMapping(value="/patients", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
    public List<Patient> patientList(@RequestParam(value="userid", required=true) Long userId) {
		Principle user = adminRepo.findOne(userId);
		Set<String> patientIds = user.getPatientIds();
		List<Patient> patients = new ArrayList<Patient>();
		for(String patientId : patientIds) {
			patients.add(patientRepo.findOne(patientId));
		}
		
        return patients;
    }
	
	@RequestMapping(value="/patients", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public Principle addPatientToList(@RequestBody AddPatientModel model) {
		Principle user = adminRepo.findOne(model.getUserId());
		Set<String> patientIds = user.getPatientIds();
		if(!patientIds.contains(model.getPatientId())) {
			patientIds.add(model.getPatientId());
			adminRepo.save(user);
		}
        return user;
    }
}
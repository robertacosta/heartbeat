package emr.authorized.model;

import emr.assessment.model.Assessment;

public class AssociateAssessmentModel {
	private Assessment assessment;
	private String patientId;
	
	public Assessment getAssessment() {
		return assessment;
	}
	public void setAssessment(Assessment assessment) {
		this.assessment = assessment;
	}
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
}

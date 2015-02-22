package emr.assessment.model;

import org.springframework.data.annotation.Id;

public class Assessment {
	@Id private String id;
	private String date;
	private String time;
	
	private Neurological nuerological;
	private Respiratory respiratory;
	private Cardio cardio;
	private Gastrointestinal gastrointestinal;
	private Skin skin;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Neurological getNuerological() {
		return nuerological;
	}
	public void setNuerological(Neurological nuerological) {
		this.nuerological = nuerological;
	}
	public Respiratory getRespiratory() {
		return respiratory;
	}
	public void setRespiratory(Respiratory respiratory) {
		this.respiratory = respiratory;
	}
	public Cardio getCardio() {
		return cardio;
	}
	public void setCardio(Cardio cardio) {
		this.cardio = cardio;
	}
	public Gastrointestinal getGastrointestinal() {
		return gastrointestinal;
	}
	public void setGastrointestinal(Gastrointestinal gastrointestinal) {
		this.gastrointestinal = gastrointestinal;
	}
	public Skin getSkin() {
		return skin;
	}
	public void setSkin(Skin skin) {
		this.skin = skin;
	}
}

package emr.assessment.model;

public class Breakdown {
	private String site;
	private boolean drainage;
	private boolean redness;
	private boolean dressing;
	private String stage;

	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public boolean isDrainage() {
		return drainage;
	}
	public void setDrainage(boolean drainage) {
		this.drainage = drainage;
	}
	public boolean isRedness() {
		return redness;
	}
	public void setRedness(boolean redness) {
		this.redness = redness;
	}
	public boolean isDressing() {
		return dressing;
	}
	public void setDressing(boolean dressing) {
		this.dressing = dressing;
	}
}

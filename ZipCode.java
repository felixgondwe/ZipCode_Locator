/*
 * This class contains all the information about a zip code.
 */
public class  ZipCode {
	/*
	 * instance data
	 */
	private String stateCode;
	private String zipCode;
	private String stateAbbreviation;
	private String stateName;
	private double longitude;
	private double latitude;
	/*
	 * Constructor
	 * Initialize instance data
	 */
	public ZipCode(String stateCode, String zipCode, String stateAbbreviation, 
			String stateName, double longitude, double latitude){
		this.stateCode = stateCode;
		this.zipCode = zipCode;
		this.stateAbbreviation = stateAbbreviation;
		this.stateName = stateName;
		this.longitude = longitude;
		this.latitude = latitude;
	}

	/*
	 * getter methods to access the instance data
	 */
	public String getZip(){
		return this.zipCode;
	}

	public double getLongitude(){
		return this.longitude;
	}

	public double getLatitude(){
		return this.latitude;
	}

	/*
	 * String representation of the ZipCode object
	 */
	public String toString(){
		return " \nSTATE CODE: " + this.stateCode + "\n" + 
				"ZIP CODE: " + this.zipCode + "\n" +
				"STATE ABBREVIATION: "+ this.stateAbbreviation + "\n" +
				"NAME: "+ this.stateName+"\n" +
				"LONGITUTE: " + this.longitude + "\n" + "LATITUDE: " + this.latitude + "\n.................................";
	}

}

import java.util.*;
import java.io.IOException;
import java.net.*;
/*
 * This class will create the map which will contain all the zip codes.
 * Also contains operations to manipulate the data in the created map
 */
public class AMap {
	//instance data
	private Scanner scanner,scanner1;
	private Map<String, ZipCode> aMap;
	private String key, stateCode;
	private ZipCode zipCode;
	private double longitude, latitude;
	private static final double R = 6371 ;
	private double minLon,maxLon, minLat, maxLat;

	//constructor
	public AMap(String url){
		URL webfile = null;
		try{
			webfile = new URL(url);
		}
		catch(MalformedURLException e){
			System.out.println("Bad url " + url );
			return;
		}

		try{
			scanner = new Scanner(webfile.openStream());
		}
		catch (IOException e){
			System.out.println("Could not open the url");
			return;
		}

		/*
		 * initialize instance data
		 */
		key = "";
		scanner1 = new Scanner(System.in);
		longitude = 0.0;
		latitude = 0.0;
		aMap = new HashMap<String, ZipCode>();
		minLon = 125;
		maxLon = 0.0;
		minLat = 125.0;
		maxLat = 0.0;

		/*
		 * create the map
		 */
		buildMap();
	}

	/*
	 * build the hash map
	 * plug in data
	 */
	public void buildMap(){
		while(scanner.nextLine().startsWith("STATE")){
			break;
		}// to skip first line in text file

		while(scanner.hasNext()){

			String [] lines = split(scanner.nextLine());
			longitude = Double.parseDouble(lines[4]);
			latitude = Double.parseDouble(lines[5]);
			key = lines[1];
			stateCode = lines[0];
			//create zipcode object
			zipCode = new ZipCode(stateCode,key,lines[2],lines[3],longitude,latitude);
			// put the zipcode into the map
			insertZip();
		}

	}
	/*
	 * postcondition: aMap = null, key != null, zip code != null
	 * postcondition: aMap should contain all the data from the zip code text file
	 */
	private void insertZip() {
		//include only main land
		if(!(stateCode.startsWith("02") || stateCode.startsWith("15"))){
			aMap.put(key, zipCode);
		}
	}
	
	/*************** user options *********************/
	/*
	 * user option 1
	 * get zip and print zip information
	 */
	public void option1() {
		
		System.out.println("Enter zip: ");
		String zipcode = scanner1.next();
		if(aMap.get(zipcode) == null){
			System.out.println(">>>>>>>>>>>Invalid zip code<<<<<<<<<<");
			option1();
		}
		else{
			System.out.println(aMap.get(zipcode));
		
		}
	}// option 1
	
	/*
	 * user option 2
	 * print out the farthest zip codes in North, South, East, West
	 */
	public void option2(){
		System.out.println("Farthest State in North: " + maxLat());
		System.out.println("Farthest State in South: " + minLat());
		System.out.println("Farthest State in East: " + minLon());
		System.out.println("Farthest State in West: " + maxLon());
	}// option 2

	/*
	 * user option 3
	 * get two zip codes
	 * calculate the distance between the given zip codes
	 */
	public void option3() {

		System.out.println("Enter two zip codes for distance: ");
		String zip1 = scanner1.next();
		String zip2 = scanner1.next();
		if(aMap.get(zip1) != null && aMap.get(zip2) != null){
			//calculate distance between the two zip codes entered
			double result = distance(aMap.get(zip1),aMap.get(zip2));
			System.out.format("The distance between "+ zip1 +" and " + zip2 + " is : %.2fkm%n", result);
		}
		else{
			System.out.println(">>>>>>>>Inavlid zip code<<<<<<<<");
			option3();
		}
		
		
	}// Option 3
	
	/*
	 * precondition: zip1 != null, zip2 != null, R != 0
	 * postcondition: return distance between zip codes
	 */
	protected double distance(ZipCode zip1, ZipCode zip2) {
		//convert longitudes and latitudes from degrees to radians
		double lon1 =  Math.toRadians(zip1.getLongitude());
		double lon2 =  Math.toRadians(zip2.getLongitude());
		double lat1 = Math.toRadians(zip1.getLatitude());
		double lat2 =  Math.toRadians(zip2.getLatitude());
		//compute the distance between two zip codes
		double distance = (Math.acos((Math.sin(lat1)*Math.sin(lat2)) +
				((Math.cos(lat1))*(Math.cos(lat2))* (Math.cos(lon2 - lon1))))) * R;
		return distance;
	}
	
	/*
	 * precondition: this.minLat > 0 , aMap != null
	 * postcondition: this.minLat should have smallest latitude
	 * return largest latitude
	 */

	private ZipCode minLat() {
		ZipCode min_lat=null;
		//loop through the map to find smallest latitude
		for(ZipCode zipcode: aMap.values()){
			if(minLat > (zipcode).getLatitude()){
				minLat= (zipcode).getLatitude();
				min_lat = zipcode;
			}
		}
		return min_lat;

	}
	/*
	 * precondition: this.maxLat > 0 , aMap != null
	 * postcondition: this.maxLon should have largest longitude
	 * return largest latitude
	 */
	
	private ZipCode maxLat() {
		ZipCode max_lat=null;
		//loop through the map to find largest latitude
		for(ZipCode zipcode: aMap.values()){
			if(maxLat < (zipcode).getLatitude()){
				maxLat= (zipcode).getLatitude();
				max_lat=zipcode;
			}
		}
		return max_lat;
	}
	
	/*
	 * precondition: this.maxLon > 0 , aMap != null
	 * postcondition: this.maxLon should have largest longitude
	 * return largest longitude
	 */
	private ZipCode maxLon() {
		ZipCode max_lon = null;
		//loop through the map to find largest longitude
		for(ZipCode zipcode: aMap.values()){
			if(maxLon < (zipcode).getLongitude()){
				maxLon= (zipcode).getLongitude();
				max_lon=zipcode;
			}
		}
		return max_lon;
	}

	/*
	 * precondition: this.midnlon > 0, aMap != null
	 * postcondition: this.minlon should have the largest longitude
	 * return smallest longitude
	 */
	private ZipCode minLon() {
		ZipCode min_lon = null;
		//loop through the map to find largest longitude
		for(ZipCode zipcode: aMap.values()){
			if(this.minLon > (zipcode).getLongitude()){
				this.minLon= (zipcode).getLongitude();
				min_lon = zipcode;
			}
		}
		return min_lon;
	}
	
	/*
	 * split a string into an array
	 */
	private String[] split(String str){
		return str.split(",");
	}


}

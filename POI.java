public class POI implements Comparable<POI> {
    //instance variables:
    // String name
    // double longitude
    // double latitude
    // String type (“playstreet”, “picnic”, “playground”, “pool”, “bookstore”)
    // String address
    // int zip
    private final String name;
    private final double longitude;
    private final double latitude;

    private final String type;

    private final String address;

    private final int zip;

    private final int id;


    //constructor
    public POI(String name, double longitude, double latitude, String type, String address, int zip, int id){
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.type = type;
        this.address = address;
        this.zip = zip;
        this.id = id;
    }

    //accessor methods for all the instance variables
    public String getName(){
        return name;
    }

    public double getLongitude(){
        return longitude;
    }

    public double getLatitude(){
        return latitude;
    }

    public String getType(){
        return type;
    }

    public String getAddress(){
        return address;
    }

    public int getZip(){
        return zip;
    }

    public int getId() {
        return id;
    }

    //method for finding distance (in miles) to another POI
    public double dist(POI other){
        double oLong = other.getLongitude();
        double oLat = other.getLatitude();
        return Math.sqrt(
                (Math.abs(this.latitude - oLat))*(Math.abs(this.latitude - oLat)) +
                        (Math.abs(this.longitude - oLong))*(Math.abs(this.longitude - oLong)));
    }

    @Override
    public int compareTo(POI o) {
        return (int) dist(o);
    }

    @Override
    public String toString() {
        if(getAddress() != null) {
            return getType() + ": " + getName() + " at address: " + getAddress();
        }
        return getType() + ": " + getName();
    }
}
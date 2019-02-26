package adapters;

public class Earthquake {

    private double magnitude;
    private String city;
    private long date;
    private String url;

    public Earthquake(double magnitude, String city, long date, String url) {
        this.magnitude = magnitude;
        this.city = city;
        this.date = date;
        this.url = url;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

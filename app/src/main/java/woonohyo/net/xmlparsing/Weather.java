package woonohyo.net.xmlparsing;

/**
 * Created by Woonohyo on 15. 2. 6..
 */
public class Weather {
    private String time;
    private String city;
    private String weather;

    public Weather() {
        this.time = "Unknown";
        this.city = "Unknown";
        this.weather = "Unknown";
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String toString() {
        return "Time: " + time + " Weather: " + weather;
    }
}

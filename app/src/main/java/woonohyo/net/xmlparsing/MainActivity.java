package woonohyo.net.xmlparsing;

import android.os.StrictMode;
import android.sax.Element;
import android.sax.ElementListener;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.RootElement;
import android.sax.StartElementListener;
import android.sax.TextElementListener;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.xml.sax.Attributes;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements View.OnClickListener, EndElementListener {
    Button refreshButton;
    ListView listViewNews;
    NewsAdapter newsAdapter;
    String feedURLString = "http://kma.go.kr/weather/forecast/mid-term-rss3.jsp?stnId=109";
    ArrayList<Weather> weatherList;
    Weather weatherData;
    private String currentCity;

    private void parseNewsfeed() {
        RootElement root = new RootElement("rss");
        Element channel = root.getChild("channel");
        Element item = channel.getChild("item");
        Element city = item.getChild("description").getChild("body").getChild("location").getChild("city");
        Element location = item.getChild("description").getChild("body").getChild("location");
        Element data = item.getChild("description").getChild("body").getChild("location").getChild("data");
        Element weather = data.getChild("wf");
        Element time = data.getChild("tmEf");

        root.setEndElementListener(this);

        location.setElementListener(new ElementListener() {
            @Override
            public void end() {

            }

            @Override
            public void start(Attributes attributes) {

            }
        });



        city.setTextElementListener(new TextElementListener() {
            @Override
            public void end(String body) {
                currentCity = body;

            }

            @Override
            public void start(Attributes attributes) {
            }
        });

        data.setEndElementListener(new EndElementListener() {
            @Override
            public void end() {
                weatherData.setCity(currentCity);
                weatherList.add(weatherData);
            }
        });
        data.setStartElementListener(new StartElementListener() {
            @Override
            public void start(Attributes attributes) {
                weatherData = new Weather();
            }
        });
        time.setEndTextElementListener(new EndTextElementListener() {
            @Override
            public void end(String body) {
                weatherData.setTime(body);
            }
        });
        weather.setEndTextElementListener(new EndTextElementListener() {
            @Override
            public void end(String body) {
                weatherData.setWeather(body);
            }
        });


        try {
            URL feedURL = new URL(feedURLString);
            InputStream is = feedURL.openStream();
            Xml.parse(is, Xml.Encoding.UTF_8, root.getContentHandler());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.enableDefaults();
        setContentView(R.layout.activity_main);
        weatherList = new ArrayList<>();
        listViewNews = (ListView) findViewById(R.id.listView_weathers);
        newsAdapter = new NewsAdapter(weatherList, this);
        listViewNews.setAdapter(newsAdapter);
        refreshButton = (Button) findViewById(R.id.button_refresh);
        refreshButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_refresh: {
                weatherList.clear();
                Toast.makeText(this, "Refreshing...", Toast.LENGTH_SHORT).show();
                parseNewsfeed();
            }
        }
    }

    @Override
    public void end() {
        newsAdapter.notifyDataSetChanged();
    }
}

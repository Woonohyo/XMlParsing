package woonohyo.net.xmlparsing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by Woonohyo on 15. 2. 6..
 */
public class NewsAdapter extends BaseAdapter {
    ArrayList<Weather> weatherList;
    Context context;
    LayoutInflater inflater;

    public NewsAdapter(ArrayList<Weather> weatherList, Context context) {
        this.weatherList = weatherList;
        this.context = context;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return weatherList.size();
    }

    @Override
    public Object getItem(int position) {
        return weatherList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.newslist_row, null);
        }
        TextView textViewTime = (TextView) convertView.findViewById(R.id.textView_time);
        TextView textViewWeather = (TextView) convertView.findViewById(R.id.textView_weather);
        TextView textViewCity = (TextView) convertView.findViewById(R.id.textView_city);

        Weather data = weatherList.get(position);
        textViewTime.setText(data.getTime());
        textViewWeather.setText(data.getWeather());
        textViewCity.setText(data.getCity());

        return convertView;
    }
}

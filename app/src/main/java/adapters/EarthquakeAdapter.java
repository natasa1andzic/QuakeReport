package adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.quakereport.R;

import java.util.ArrayList;


public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    public EarthquakeAdapter(Context context, ArrayList<Earthquake> words) {
        super(context, 0, words);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        // Get the {@link Word} object located at this position in the list
        Earthquake currentEarthquake = getItem(position);

        TextView magnitudeTextView = (TextView) listItemView.findViewById(R.id.magnitudeTv);
        magnitudeTextView.setText(currentEarthquake.getMagnitude());

        TextView cityTextView = (TextView) listItemView.findViewById(R.id.cityTv);
        cityTextView.setText(currentEarthquake.getCity());

        TextView dateTextView = (TextView) listItemView.findViewById(R.id.dateTv);
        dateTextView.setText(currentEarthquake.getDate());

        // za boju list item-a :D
        //View textContainer = listItemView.findViewById(R.id.list_item);
       // int color = ContextCompat.getColor(getContext(), );
       // textContainer.setBackgroundColor(color);

        return listItemView;
    }
}

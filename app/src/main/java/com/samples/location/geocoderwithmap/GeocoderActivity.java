package com.samples.location.geocoderwithmap;

import android.app.ListActivity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.List;

public class GeocoderActivity extends ListActivity {

    private EditText text;
    private Button bSearch;
    private Geocoder geocoder;
    private List<Address> addresses;
    private ListView listView;

    public View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String placeName = text.getText().toString();
            try{
                addresses = geocoder.getFromLocationName(placeName,10);
                int size = addresses.size();
                if (size == 0){
                    text.setText("");
                    text.setHint("No results. Enter new place");
                }else{
                    String[] list = new String[size];
                    for (int i = 0; i < size; i++){
                        Address loc = addresses.get(i);
                        list[i] = "Latitude: " + loc.getLatitude() +
                                "\nLongitude" + loc.getLongitude();
                    }
                    setListAdapter(new ArrayAdapter<String>(getApplicationContext(),
                            android.R.layout.simple_list_item_1, list));
                }
            }catch (Exception e){
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geocoder);

        text = (EditText)findViewById(R.id.eText);
        bSearch = (Button)findViewById(R.id.bSearch);
        listView = (ListView)findViewById(android.R.id.list);

        geocoder = new Geocoder(getApplicationContext());

        bSearch.setOnClickListener(listener);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Address loc = addresses.get(position);
        Uri uri = Uri.parse(String.format("geo:%f,%f",
                loc.getLatitude(), loc.getLongitude()));
        Intent geoMap = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(geoMap);
    }
}

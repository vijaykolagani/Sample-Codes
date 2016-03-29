package com.appzollo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.appzollo.classes.Category;
import com.appzollo.classes.PlaceStickerDetailsPojo;
import com.appzollo.classes.Segments;
import com.appzollo.classes.SettingsListAdapter;

import java.util.ArrayList;

/**
 * Created by saikrishna on 11/11/14.
 */
public class AddPlaceActivityTwo extends BaseActivity {

    EditText et1,et2,et3,et4,et5,et6;
    TextView segments;
    String[] items = {
            "Bungee Jump",
            "Camping","Cycling", "Fishing Tours","Flying Fox",
            "Hot Aur Bollooning", "Jeep Safari", "Kayaking", "Motorcycling", "Mountain Biking","Mountaineering", "Paintball",
            "Hot Aur Bollooning", "Jeep Safari", "Kayaking", "Motorcycling", "Mountain Biking","Mountaineering", "Paintball",
            "Paragliding", "Parasailing","Rafting", "Rappelling","Rock Climbing",  "Running", "Scuba Diving", "Skiing",
            "Skydiving","Snorkelling",
            "Trekking", "Wall Climbing","Water Sports","Art And Culture Trip","Food Trip","Historical Trip","Photography Trip",
            "Cultural Tour","Historical Tour","Wildlife"
    };
    int[] itemId = {10,11,9,48,44,12,42,13,37,55,14,39,15,16,8,46,43,53,17,18,22,45,19,20,54,62,61,63,64,57,58,59};
    private boolean mEdit;
    private PlaceStickerDetailsPojo placeDetails;
    ArrayList<Category>   categories;
    ExpandableListView myList;
    ArrayList<String> interest_array = new ArrayList<String>();
    SettingsListAdapter adapter;
    LinearLayout llDone,llCancel;
String intIds ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
        setContentView(R.layout.form_12a_2);

        mEdit = getIntent().getBooleanExtra("edit", false);
        if (mEdit) {
            placeDetails = getIntent().getParcelableExtra("place");
        }

        et1 = (EditText)findViewById(R.id.et1);
        et2 = (EditText)findViewById(R.id.et2);
        et3 = (EditText)findViewById(R.id.et3);
        et4 = (EditText)findViewById(R.id.et4);
        et5 = (EditText)findViewById(R.id.et5);
        et6 = (EditText)findViewById(R.id.et6);
        segments = (TextView) findViewById(R.id.spSegments);
        segments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSegements();
            }
        });

        llCancel = (LinearLayout) findViewById(R.id.llCancel);
        llDone = (LinearLayout) findViewById(R.id.llDone);

        llCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddPlaceActivityTwo.this.finish();
            }
        });

        llDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((et1.getText().toString().length() > 0 || et2.getText().toString().length() > 0 || et3.getText().toString().length() > 0)&&
                        et4.getText().toString().length() > 0 || et5.getText().toString().length() > 0 || et5.getText().toString().length() > 0) {
                    startActivity(new Intent(AddPlaceActivityTwo.this, BroadcastActivity.class)
                                    .putExtra("latitude", getIntent().getExtras().getString("latitude") + "")
                                    .putExtra("longitude", getIntent().getExtras().getString("longitude") + "")
                                    .putExtra("palce1", getIntent().getExtras().getString("place1"))
                                    .putExtra("palce2", getIntent().getExtras().getString("place2"))
                                    .putExtra("place3", getIntent().getExtras().getString("place3"))
                                    .putExtra("name", getIntent().getExtras().getString("name"))
                                    .putExtra("segment", intIds)
                                    .putExtra("placefull", getIntent().getExtras().getString("placefull"))
                                    .putExtra("img", getIntent().getExtras().getString("img"))
                                    .putExtra("thingstodo", et1.getText().toString() + et2.getText().toString() + et3.getText().toString())
                                    .putExtra("howtoreach", et4.getText().toString() + et5.getText().toString() + et6.getText().toString())
                                    .putExtra("which", 2)
                                    .putExtra("edit", mEdit)
                                    .putExtra("place", placeDetails)
                    );
                }else{
                    Toast.makeText(getApplicationContext(), "Fields should not be empty", Toast.LENGTH_LONG).show();
                }
               // startActivity(new Intent(AddPlaceActivityTwo.this, BroadcastActivity.class));
            }
        });

        if (mEdit) {
            et1.setText("" + placeDetails.getData().get(0).getTodo());
            et4.setText("" + placeDetails.getData().get(0).getHowtoreach());
            if (placeDetails.getData().get(0).getInterest_id() != null
                    && placeDetails.getData().get(0).getInterest_id().size() > 0) {
                final String[] adv = {
                        "Bungee Jumping","Camping", "Cycling", "Fishing Tours","Flying Fox", "Hot Air Ballooning","Jeep Safari","Kayaking", "Motorcycling", "Mountain Biking","Mountaineering","Paintball", "Paragliding",
                        "Parasailing","Rafting","Rappelling"
                        , "Rock Climbing", "Running","Scuba Diving","Skiing", "Sky Diving", "Snorkelling", "Trekking","Wall Climbing",  "Water Sports","Wild Life Safari"
                        ,"Art And Culture Trip","Food Trip","Historical Trip","Photography Trip","Cultural Tour","Historical Tour",
                        "Wildlife"};
                int[] itemId = {10,11,9,48,44,12,42,13,37,55,14,39,15,16,8,46,43,53,17,18,22,45,19,20,54,62,61,63,64,57,58,59};

                for(int l = 0 ; l < placeDetails.getData().get(0).getInterest_id().size() ; l++){

                    segments.append("" + Segments.getSegmentName(placeDetails.getData().get(0).getInterest_id().get(l).getId()) + ",");
                    segments.setText(segments.getText().toString().replace(",,",",").replace(",,,",",").replace(",,,,",","));

                    interest_array.add(Segments.getSegmentName(placeDetails.getData().get(0).getInterest_id().get(l).getId()));

                    for (int p = 0; p < adv.length; p++) {

                        if (adv[p].equalsIgnoreCase(Segments.getSegmentName(placeDetails.getData().get(0).getInterest_id().get(l).getId()))) {
                            intIds += items[p] + ",";
                           // Toast.makeText(getApplicationContext(),intIds+"",Toast.LENGTH_SHORT).show();
                        }

                    }
                }


            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
    }
    public void showSegements(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select something");

        builder

                .setCancelable(false)
                .setPositiveButton("Done",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, close
                        // current activity
                        dialog.cancel();
                    }
                });

        myList = new ExpandableListView(this);
        categories = Category.getCategories();

         adapter = new SettingsListAdapter(this,
                categories, myList,interest_array);
       /* ExpandableCheckListAdapter myAdapter = new ExpandableCheckListAdapter(this,headerData, mExpListData);
        myList.setAdapter(myAdapter);
*/
        myList.setAdapter(adapter);
        builder.setView(myList);
        AlertDialog dialog = builder.create();
        dialog.show();
        myList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                adapter = new SettingsListAdapter(AddPlaceActivityTwo.this,
                        categories, myList,interest_array);
                adapter.notifyDataSetChanged();
              //  myList.setAdapter(adapter);
                return false;
            }
        });

        myList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {


                CheckedTextView checkbox = (CheckedTextView) v.findViewById(R.id.list_item_text_child);
                checkbox.toggle();
                final String[] adv = {
                        "Bungee Jumping","Camping", "Cycling", "Fishing Tours","Flying Fox", "Hot Air Ballooning","Jeep Safari","Kayaking", "Motorcycling", "Mountain Biking","Mountaineering","Paintball", "Paragliding",
                        "Parasailing","Rafting","Rappelling"
                        , "Rock Climbing", "Running","Scuba Diving","Skiing", "Sky Diving", "Snorkelling", "Trekking","Wall Climbing",  "Water Sports","Wild Life Safari"
                        ,"Art And Culture Trip","Food Trip","Historical Trip","Photography Trip","Cultural Tour","Historical Tour",
                        "Wildlife"};
                int items[]= {10,11,9,48,44,12,42,13,37,55,14,39,15,16,8,46,43,53,17,18,22,45,19,20,54,21,62,61,63,64,57,58,59};


                if(checkbox.isChecked()) {
                        interest_array.add(checkbox.getText().toString());
                    if (segments.getText().toString().length() == 0) {

                        segments.append(categories.get(groupPosition).children.get(childPosition).name);
                        segments.setText(segments.getText().toString().replace(",,",",").replace(",,,",",").replace(",,,,",","));

                    } else {
                        if (segments.getText().toString().contains(categories.get(groupPosition).children.get(childPosition).name)) {

                        } else
                            segments.append("," + categories.get(groupPosition).children.get(childPosition).name);
                        segments.setText(segments.getText().toString().replace(",,",",").replace(",,,",",").replace(",,,,",","));

                    }
                    for (int l = 0; l < adv.length; l++) {

                        if (adv[l].equalsIgnoreCase(categories.get(groupPosition).children.get(childPosition).name)) {
                            intIds += items[l] + ",";
                        }

                    }

                }else{
                    for(int l = 0 ; l < adv.length ; l++){
                        if(adv[l].equalsIgnoreCase(checkbox.getText().toString())){
                            interest_array.remove(checkbox.getText().toString());
                            Toast.makeText(getApplication(),interest_array+"",Toast.LENGTH_SHORT).show();
                        }
                    }
                    segments.setText(segments.getText().toString().replace(categories.get(groupPosition).children.get(childPosition).name+",",""));
                    segments.setText(segments.getText().toString().replace(categories.get(groupPosition).children.get(childPosition).name,""));
                    for (int l = 0; l < adv.length; l++) {

                        if (adv[l].equalsIgnoreCase(categories.get(groupPosition).children.get(childPosition).name)) {
                            intIds =intIds.replaceFirst(items[l] + ",","");;
                        }

                    }
                }



                View parentView = myList.findViewWithTag(categories.get(groupPosition).name);
                if (parentView != null) {
                    TextView sub = (TextView) parentView.findViewById(R.id.list_item_text_subscriptions);

                }
                return true;
            }

        });
    }


}

package com.appzollo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appzollo.classes.Category;
import com.appzollo.classes.EventDetail;
import com.appzollo.classes.Segments;
import com.appzollo.classes.SettingsListAdapter;

import java.util.ArrayList;

/**
 * Created by saikrishna on 15/11/14.
 */
public class TripDetailsOneActivity extends BaseActivity implements View.OnClickListener {
    private Button done, cancel,bt_addcost;
    EditText et_food,et_accom,et_trans,et_add,et_add1,et_add2,et_cost,et_details,et_others;
    CheckBox cb_trans_y,cb_trans_n,cb_food_y,cb_food_n,cb_accom_y,cb_accom_n;
    String isfood = "0",isaccom = "0",istrans = "0";
    private EventDetail events;
    private boolean edit;
    TextView segments;
    LinearLayout ll_cost;
    String cost  = "",costdetails  = "";

    ArrayList<Category>   categories;
    ArrayList<String> interest_array = new ArrayList<String>();
    ExpandableListView myList;
    String intIds = "";
    final String[] adv = {
            "Bungee Jumping","Camping", "Cycling", "Fishing Tours","Flying Fox", "Hot Air Ballooning","Jeep Safari","Kayaking", "Motorcycling", "Mountain Biking","Mountaineering","Paintball", "Paragliding",
            "Parasailing","Rafting","Rappelling"
            , "Rock Climbing", "Running","Scuba Diving","Skiing", "Sky Diving", "Snorkelling", "Trekking","Wall Climbing",  "Water Sports","Wild Life Safari"
            ,"Art And Culture Trip","Food Trip","Historical Trip","Photography Trip","Cultural Tour","Historical Tour",
            "Wildlife"};
    int items[]= {10,11,9,48,44,12,42,13,37,55,14,39,15,16,8,46,43,53,17,18,22,45,19,20,54,21,62,61,63,64,57,58,59};
    String itinerary="";
    SettingsListAdapter adapter;

LinearLayout llDone,llCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
        setContentView(R.layout.add_trip_details_1);

        edit = getIntent().getBooleanExtra("edit", false);
        if (edit) {
            events = getIntent().getParcelableExtra("event");
        }

        llDone = (LinearLayout) findViewById(R.id.llDone);
        llCancel = (LinearLayout) findViewById(R.id.llCancel);

        bt_addcost = (Button) findViewById(R.id.bt_costadd);
        segments = (TextView) findViewById(R.id.spSegments);

        et_food = (EditText) findViewById(R.id.et_food);
        et_accom = (EditText) findViewById(R.id.et_accom);
        et_trans = (EditText) findViewById((R.id.et_trans));
        et_add = (EditText) findViewById(R.id.et_add);
        et_add1 = (EditText) findViewById(R.id.et_add1);
        et_add2 = (EditText) findViewById(R.id.et_add2);
        et_cost = (EditText) findViewById(R.id.etCost1);
        et_details = (EditText) findViewById(R.id.et_details);
        et_others= (EditText) findViewById(R.id.et_others);

        cb_food_y = (CheckBox) findViewById(R.id.cb_food_y);
        cb_food_n = (CheckBox) findViewById(R.id.cb_food_n);
        cb_accom_y = (CheckBox) findViewById(R.id.cb_accom_y);
        cb_accom_n = (CheckBox) findViewById(R.id.cb_accom_n);
        cb_trans_y = (CheckBox) findViewById(R.id.cb_trans_y);
        cb_trans_n = (CheckBox) findViewById(R.id.cb_trans_n);

        ll_cost = (LinearLayout) findViewById(R.id.dates_layout);

        segments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSegements();
            }
        });
        if (edit) {
            et_food.setText(events.getFood());
            et_accom.setText(events.getAccommodation());
            et_trans.setText(events.getTravel());
           // et_add.setText(events.getOther());
            et_others.setText(events.getOther());
            if (events.getIsfood().equals("0")) {
                cb_food_n.setChecked(true);
            } else {
                isfood = "1";
                cb_food_y.setChecked(true);
            }
            if (events.getIstravel().equals("0")) {
                cb_trans_n.setChecked(true);
            } else {
                istrans = "1";
                cb_trans_y.setChecked(true);
            }
            if (events.getIsaccommodation().equals("0")) {
                cb_accom_n.setChecked(true);
            } else {
                isaccom = "1";
                cb_accom_y.setChecked(true);
            }
            if (events.getSegment() != null
                    && events.getSegment().size() > 0) {
                for(int l = 0 ; l < events.getSegment().size() ; l++){
                    final String[] adv = {
                            "Bungee Jumping","Camping", "Cycling", "Fishing Tours","Flying Fox", "Hot Air Ballooning","Jeep Safari","Kayaking", "Motorcycling", "Mountain Biking","Mountaineering","Paintball", "Paragliding",
                            "Parasailing","Rafting","Rappelling"
                            , "Rock Climbing", "Running","Scuba Diving","Skiing", "Sky Diving", "Snorkelling", "Trekking","Wall Climbing",  "Water Sports","Wild Life Safari"
                            ,"Art And Culture Trip","Food Trip","Historical Trip","Photography Trip","Cultural Tour","Historical Tour",
                            "Wildlife"};
                    int[] itemId = {10,11,9,48,44,12,42,13,37,55,14,39,15,16,8,46,43,53,17,18,22,45,19,20,54,62,61,63,64,57,58,59};

                    segments.append("" + Segments.getSegmentName(events.getSegment().get(l).getId()) + ",");
                    segments.setText(segments.getText().toString().replace(",,",",").replace(",,,",",").replace(",,,,",","));

                    interest_array.add(Segments.getSegmentName(events.getSegment().get(l).getId()));

                    for (int p = 0; p < adv.length; p++) {

                        if (adv[p].equalsIgnoreCase(Segments.getSegmentName(events.getSegment().get(l).getId()))) {
                            intIds += items[p] + ",";
                           // Toast.makeText(getApplicationContext(),intIds+"",Toast.LENGTH_SHORT).show();
                        }

                    }
                }
                //Toast.makeText(getApplicationContext(),Segments.getSegmentName(events.getSegment().get(0).getId())+"jjkk",Toast.LENGTH_SHORT).show();

            }
            if(events.getCostplans() != null
                    && events.getCostplans().size() >0){
                for(int i = 0 ; i < events.getCostplans().size() ; i++){
                    if(i == 0 ){
                        et_cost.setText(events.getCostplans().get(i).getcost()+"");
                        et_details.setText(events.getCostplans().get(i).getdetails());
                        Log.d("test",events.getCostplans().size()+"");
                    }else {
                        View view1 = View.inflate(TripDetailsOneActivity.this, R.layout.add_cost_layout, null);
                        LinearLayout addDate = (LinearLayout) view1.findViewById(R.id.ll_date);
                        EditText etdate = (EditText) view1.findViewById(R.id.etCostadd);
                        EditText etdetails = (EditText) view1.findViewById(R.id.et_details);

                        ll_cost.addView(view1);
                        etdate.setText(events.getCostplans().get(i).getcost()+"");
                        etdetails.setText(events.getCostplans().get(i).getdetails());
                        Log.d("test","22222");
                    }
                }

            }
            if(events.getCost_description() != null){
                et_add.setText(Html.fromHtml(events.getDescription().toString()));
            }
           /* if (events.getDescription() != null)
                et_add.setText("" + Html.fromHtml(events.getDescription()));*/
        }


        bt_addcost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View view1 = View.inflate(TripDetailsOneActivity.this, R.layout.add_cost_layout, null);
                LinearLayout addDate = (LinearLayout) view1.findViewById(R.id.ll_date);
                EditText etdate = (EditText) view1.findViewById(R.id.etCostadd);
                EditText etdetails = (EditText) view1.findViewById(R.id.et_details);

                ll_cost.addView(view1);
            }
        });


        cb_food_y.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    cb_food_n.setChecked(false);
                    isfood = "1";
                }
            }
        });
        cb_food_n.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    cb_food_y.setChecked(false);
                    isfood = "0";
                }
            }
        });

        cb_accom_y.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    cb_accom_n.setChecked(false);
                    isaccom = "1";
                }
            }
        });
        cb_accom_n.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    cb_accom_y.setChecked(false);
                    isaccom = "0";
                }
            }
        });

        cb_trans_y.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    cb_trans_n.setChecked(false);
                    istrans = "1";
                }
            }
        });
        cb_trans_n.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    cb_trans_y.setChecked(false);
                    istrans = "0";
                }
            }
        });



        llDone.setOnClickListener(this);
        llCancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llDone:
                if(((cb_trans_y.isChecked() &&  et_trans.getText().toString().length() > 0 ) ||(cb_trans_n.isChecked()))
                        &&((cb_accom_y.isChecked() && et_accom.getText().toString().length() >0 ) ||(cb_accom_n.isChecked()))
                        &&((cb_food_y.isChecked() && et_food.getText().toString().length() > 0 ) || (cb_food_n.isChecked())))
                    if ((
                            et_add.getText().toString().length() > 0 ||
                                    et_add1.getText().toString().length() > 0 ||
                                    et_add2.getText().toString().length() > 0)&& et_cost.getText().toString().length() >0 &&
                            et_details.getText().length()>0 ) {
                        cost = et_cost.getText().toString();
                        costdetails = et_details.getText().toString().replace(System.getProperty("line.separator"), "");
                        itinerary = et_add.getText().toString();
                        if(et_add1.getText().toString().length()>0){
                            itinerary =itinerary+ ":"+et_add1.getText().toString();
                        }
                        for (int i = 0; i < ll_cost.getChildCount(); i++) {
                            View child = ll_cost.getChildAt(i);
                            EditText childTextView = (EditText)(child.findViewById(R.id.etCostadd));
                            EditText costPlan = (EditText)(child.findViewById(R.id.et_details));
                            cost = cost+","+childTextView.getText().toString();
                            costdetails =costdetails +":"+ costPlan.getText().toString().replace(System.getProperty("line.separator"), "");
                          Log.d("ANDROID DYNAMIC VIEWS:", "EdiText: "+costdetails );

                        }
                        Toast.makeText(getApplicationContext(),et_add.getText().toString(),Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, TripDetailsActivity.class)
                                        .putExtra("name", getIntent().getExtras().getString("name"))
                                        .putExtra("where", getIntent().getExtras().getString("where"))
                                        .putExtra("latitude", getIntent().getExtras().getString("latitude"))
                                        .putExtra("longitude", getIntent().getExtras().getString("longitude"))
                                        .putExtra("place1", getIntent().getExtras().getString("place1"))
                                        .putExtra("place2", getIntent().getExtras().getString("place2"))
                                        .putExtra("place3", getIntent().getExtras().getString("place3"))
                                        .putExtra("placefull", getIntent().getExtras().getString("placefull"))
                                        .putExtra("segment", intIds)
                                        .putExtra("img", getIntent().getExtras().getString("img"))
                                        .putExtra("isfood", isfood)
                                        .putExtra("istrans", istrans)
                                        .putExtra("isaccom", isaccom)
                                        .putExtra("cost_plan",cost)
                                        .putExtra("cost_details",costdetails)
                                        .putExtra("detail_itinerary",itinerary)
                                        .putExtra("food", et_food.getText().toString())
                                        .putExtra("trans", et_trans.getText().toString())
                                        .putExtra("accom", et_accom.getText().toString())
                                        .putExtra("others", et_others.getText().toString())
                                        .putExtra("edit", edit)
                                        .putExtra("event", events)

                        );
                    } else {
                        Toast.makeText(getApplicationContext(), "Text Field should not be empty", Toast.LENGTH_LONG);
                    }
                else{
                Toast.makeText(getApplicationContext(),"Field should not be empty",Toast.LENGTH_LONG).show();
            }
                break;
            case R.id.llCancel:
                finish();
                break;
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
                adapter = new SettingsListAdapter(TripDetailsOneActivity.this,
                        categories, myList,interest_array);
                adapter.notifyDataSetChanged();
                //myList.setAdapter(adapter);
               // Toast.makeText(getApplicationContext(),i+"",Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        myList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {


                CheckedTextView checkbox = (CheckedTextView) v.findViewById(R.id.list_item_text_child);
                checkbox.toggle();

                // find parent view by tag
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
                            Log.d("ADD",intIds);

                        }
                          }

                }else{
                    for(int l = 0 ; l < adv.length ; l++){
                        if(adv[l].equalsIgnoreCase(checkbox.getText().toString())){
                            interest_array.remove(checkbox.getText().toString());
                            //Toast.makeText(getApplication(),interest_array+"",Toast.LENGTH_SHORT).show();
                        }
                    }
                    for (int l = 0; l < adv.length; l++) {

                        if (adv[l].equalsIgnoreCase(categories.get(groupPosition).children.get(childPosition).name)) {
                            intIds = intIds.replaceFirst(items[l] + ",","");
                        }

                    }
                    segments.setText(segments.getText().toString().replace(categories.get(groupPosition).children.get(childPosition).name+",",""));
                    segments.setText(segments.getText().toString().replace(categories.get(groupPosition).children.get(childPosition).name,""));

                   // Toast.makeText(getApplicationContext(),segments.getText().toString(),Toast.LENGTH_SHORT).show();
                }


                Log.d("items", intIds + "");
                View parentView = myList.findViewWithTag(categories.get(groupPosition).name);
                if (parentView != null) {
                    TextView sub = (TextView) parentView.findViewById(R.id.list_item_text_subscriptions);

                }
                return true;
            }

        });
    }

}

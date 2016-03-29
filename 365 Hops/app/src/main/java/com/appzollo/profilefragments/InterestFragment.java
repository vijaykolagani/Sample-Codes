package com.appzollo.profilefragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.appzollo.AddIntrest;
import com.appzollo.R;
import com.appzollo.classes.JSONParser;
import com.appzollo.classes.LoadProfileImageAsync;
import com.appzollo.classes.RowItemInterest;
import com.appzollo.interfaces.OnLoadCompleteListener;
import com.appzollo.utils.CommonsUtils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class InterestFragment extends Fragment implements OnItemClickListener, OnLoadCompleteListener, ExpandableListView.OnChildClickListener {

    String[] menutitles = {};
    int[] images = {R.drawable.bunjee_icon, R.drawable.camping_icon, R.drawable.balloon_icon};

    private List<RowItemInterest> rowItems;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    View view;
    ProgressBar progress;

    private JSONParser jParser;
    ArrayList<ArrayList<String>> similar = new ArrayList<ArrayList<String>>();
    ArrayList<String> intList;
    ArrayList<String> intListImg = new ArrayList<String>();
    Button more;
    SharedPreferences pref;
    public ArrayList<String> groups;
    private ArrayList<String> child1;
    private ArrayList<String> child2;
    private ArrayList<String> child3;
    private ArrayList<String> child4;
    private ArrayList<String> child5;
    private ArrayList<ArrayList<String>> children;
    SavedTabsListAdapter simListAdapter;
    private HashMap<String, String> hashIcons = new HashMap<String, String>();

    int[] items = {10, 11, 9, 48, 44, 12, 42, 13, 37, 55, 14, 39, 15, 16, 8, 46, 43, 53, 17, 18, 22, 33, 45, 19, 20, 54, 21};
    int[] itemLocalId = {62, 61, 63, 64};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeAllViews();

        }
        try {
            view = inflater.inflate(R.layout.fragment_profile_interests, null, false);

            // list = (ListView) view.findViewById(R.id.listView);
            // get the listview
            expListView = (ExpandableListView) view.findViewById(R.id.lvExp);

            more = (Button) view.findViewById(R.id.btMore);
            progress = (ProgressBar) view.findViewById(R.id.progressBar_main);
            progress.setVisibility(View.VISIBLE);
            more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //    showDialog();
                    ArrayList<String> arr = new ArrayList<String>();
                    ArrayList<String> arr1 = new ArrayList<String>();
                    for (int k = 0; k < similar.size(); k++) {
                        arr.add(similar.get(k).get(1));
                        arr1.add(similar.get(k).get(0));
                    }


                    Intent i = new Intent(getActivity(), AddIntrest.class);
                    i.putStringArrayListExtra("interest_array", arr);
                    i.putStringArrayListExtra("interest_id",arr1);
                    startActivityForResult(i, 2890);


                }
            });
            pref = getActivity().getSharedPreferences("com.appzollo", Context.MODE_PRIVATE);
            if (this.getArguments().getString("userid").equals(CommonsUtils.getPrefString(getActivity(), "userid"))) {
                more.setVisibility(View.VISIBLE);
            } else {
                more.setVisibility(View.GONE);

            }

            buildList();
            //Toast.makeText(getActivity(),this.getArguments().getString("userid")+"jj",Toast.LENGTH_SHORT).show();
        } catch (InflateException e) {

        }
        return view;
    }

    private void buildList() {
        groups = new ArrayList<String>();

        child1 = new ArrayList<String>();
        child2 = new ArrayList<String>();
        child3 = new ArrayList<String>();
        child4 = new ArrayList<String>();
        child5 = new ArrayList<String>();
        children = new ArrayList<ArrayList<String>>();

        jParser = new JSONParser();
        GetSummaryTask gst = new GetSummaryTask();
        String url = "http://www.365hops.com/webservice/controller.php?Servicename=getUserInterests&userid=" + CommonsUtils.getPrefString(getActivity(), "profileid");
        gst.execute(url);
    }

    public class SavedTabsListAdapter extends BaseExpandableListAdapter {


        private List<String> headerData;
        private HashMap<String, List<String>> childData;
        private List<String> loadedList;
        private HashMap<String, ImageView> images;

        public SavedTabsListAdapter(List<String> headerData,
                                     HashMap<String, List<String>> childData) {
            this.headerData = headerData;
            this.childData = childData;
            loadedList = new ArrayList<String>();
            images = new HashMap<String, ImageView>();
        }

        @Override
        public int getGroupCount() {
            return this.headerData.size();
        }

        @Override
        public int getChildrenCount(int i) {
            return this.childData.get(headerData.get(i)).size();
        }

        @Override
        public Object getGroup(int i) {
            return this.headerData.get(i);
        }

        @Override
        public Object getChild(int i, int i2) {
            return this.childData.get(this.headerData.get(i)).get(i2);
        }

        @Override
        public long getGroupId(int i) {
            return i;
        }

        @Override
        public long getChildId(int i, int i2) {
            return i2;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
            if (view != null) {
                ViewGroup parent = (ViewGroup) view.getParent();
                if (parent != null)
                    parent.removeAllViews();

            }
            try {
                if (view == null) {
                    LayoutInflater mInflater = (LayoutInflater) getActivity()
                            .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                    view = mInflater.inflate(R.layout.item_profile_interests, null);
                }

                ImageView imgIcon = (ImageView) view.findViewById(R.id.iv_icon);
                TextView txtTitle = (TextView) view.findViewById(R.id.tv_name);
                txtTitle.setText(getGroup(i).toString());
                LoadProfileImageAsync task = new LoadProfileImageAsync(imgIcon, InterestFragment.this);

                if (similar != null) {

                    //   groups[i] = similar.get(i).get(1);

                    // task.execute(similar.get(i).get(2));
                }
           /* TextView textView = new TextView(InterestFragment.this.getActivity());


            for(int j = 0 ; j<= intList.size() ; j++){
                groups[j] = similar.get(j).get(1);
                textView.setText(getGroup(j).toString());
            }*/
            } catch (InflateException ex) {

            }
            return view;
        }

        @Override
        public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
            String text = getChild(i, i1).toString();

            LayoutInflater mInflater = (LayoutInflater) getActivity()
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = mInflater.inflate(R.layout.item_profile_interests, null);

            ImageView imgIcon = (ImageView) view.findViewById(R.id.iv_icon);
            TextView txtTitle = (TextView) view.findViewById(R.id.tv_name);
            txtTitle.setText(getChild(i, i1).toString());
            if (!loadedList.contains(text)){
                LoadProfileImageAsync task = new LoadProfileImageAsync(imgIcon, InterestFragment.this);
                task.execute(hashIcons.get(getChild(i, i1).toString()));
                loadedList.add(text);
                images.put(text, imgIcon);
            } else {
                imgIcon.setImageDrawable(((ImageView)images.get(text)).getDrawable());
            }

            return view;
        }

        @Override
        public boolean isChildSelectable(int i, int i1) {
            return true;
        }

    }


    @Override
    public void onLoadComplete(View view, Bitmap bitmap) {
        if (bitmap != null && view != null) {
            if (view instanceof ImageView) {
                ((ImageView) view).setImageBitmap(bitmap);
            } else if (view instanceof ImageView) {

                Drawable d = new BitmapDrawable(bitmap);
                if (d != null) {
                    if (android.os.Build.VERSION.SDK_INT >= 16) {
                        view.setBackground(d);
                    } else {
                        view.setBackgroundDrawable(d);
                    }
                }

            }
        }
    }

    @Override
    public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i2, long l) {
        return false;
    }

    public class GetSummaryTask extends AsyncTask<String, Void, String> {

        private ArrayList<Bitmap> bitmap = new ArrayList<Bitmap>();

        @Override
        protected String doInBackground(String... urls) {
            try {
                //Create an HTTP client
                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost(urls[0]);

                //Perform the request and check the status code
                HttpResponse response = client.execute(post);
                StatusLine statusLine = response.getStatusLine();
                // if(statusLine.getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();

                try {
                    //Read the server response and attempt to parse it as JSON

                    BufferedReader reader = new BufferedReader(new InputStreamReader(
                            content, "UTF8"), 8);


                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    content.close();


//                        GsonBuilder gsonBuilder = new GsonBuilder();
//                        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                    //String json = jParser.getJSONFromUrl(urls[0]);
                    // Gson gson = new Gson();

                    //eventDetailStickers = gson.fromJson(json, EventDetailSticker.class);


                    JSONObject jObj = new JSONObject(sb.toString());
                    JSONObject data = jObj.getJSONObject("data");
                    JSONArray dataInterests = data.getJSONArray("interest");
                    //similar.clear();
                    //intList.clear();
                    // Log.d("kkkkkkkkkkkkkkkkk", data.toString());
                    if (similar != null) {
                        similar.clear();
                    }
                    for (int i = 0; i < dataInterests.length(); i++) {
                        JSONObject obj = dataInterests.getJSONObject(i);

                        intList = new ArrayList<String>();
                        intList.add(obj.getString("interest_id"));
                        intList.add(obj.getString("name"));
                        intList.add(obj.getString("icon_small"));
                        similar.add(intList);
                        hashIcons.put(obj.getString("name"), obj.getString("icon_small"));


                    }

                    content.close();
                    // handlePostsList();
                } catch (Exception ex) {
                    Log.e("Inbox", "Failed to parse JSON due to: " + ex);
                    //  failedLoadingPosts();
                }
               /* } else {
                    Log.e(TAG, "Server responded with status code: " + statusLine.getStatusCode());
                    failedLoadingPosts();
                }*/
            } catch (Exception ex) {
                Log.e("Inbox", "Failed to send HTTP POST request due to: " + ex);
                // failedLoadingPosts();
            }

            return null;

        }

        @Override
        protected void onCancelled() {


        }

        @Override
        protected void onPostExecute(String result) {

            progress.setVisibility(View.GONE);
            List<String> headerData = new ArrayList<String>();


            List<String> childData1 = new ArrayList<String>();
            List<String> childData2 = new ArrayList<String>();
            List<String> childData3 = new ArrayList<String>();
            List<String> childData4 = new ArrayList<String>();
            List<String> childData5 = new ArrayList<String>();

            final HashMap<String, List<String>> mExpListData;
            mExpListData = new HashMap<String, List<String>>();

            int count1 = 0, count = 0;
            int count2 = 0;
            int count3 = 0;
            int count4 = 0;
            int count5 = 0;
            for (int i = 0; i < similar.size(); i++) {
                // Log.d("id",similar.get(i).get(0));
                for (int j = 0; j < items.length; j++) {
                    // Log.d("item",items[j]+"");
                    if (similar.get(i).get(0).equals(items[j] + "")) {
                        childData1.add(similar.get(i).get(1));
                        //count++;
                    }

                }

                for (int j = 0; j < itemLocalId.length; j++) {
                    if (similar.get(i).get(0).equals(itemLocalId[j] + "")) {
                        childData2.add(similar.get(i).get(1));
                    }

                }

                if (similar.get(i).get(0).equals("57")) {
                    childData3.add(similar.get(i).get(1));
                   // count3++;

                } else if (similar.get(i).get(0).equals("58")) {
                    childData4.add(similar.get(i).get(1));
                   // count4++;

                } else if (similar.get(i).get(0).equals("59")) {
                    childData5.add(similar.get(i).get(1));
                   // count5++;

                }

            }
            if (childData1.size() > 0) {
                headerData.add("Adventure");
                mExpListData.put("Adventure", childData1);

            }
            if (childData2.size() > 0) {
                headerData.add("Local Tours");
                mExpListData.put("Local Tours", childData2);


            }
            if(childData3.size()>0){
                headerData.add("Cultural Tour");
                mExpListData.put("Cultural Tour", childData3);




            }if(childData4.size()>0){
                headerData.add("Historical Tour");
                mExpListData.put("Historical Tour", childData4);

            }
            if(childData5.size()>0){
               headerData.add("Wildlife");
                mExpListData.put("Wildlife", childData5);

            }
            // similar.clear();
            if(headerData.size()>0){
                simListAdapter = new SavedTabsListAdapter(headerData, mExpListData);
                expListView.setAdapter(simListAdapter);
            }

            for(int y = 0 ; y < similar.size() ; y++){
                Log.d("similar",similar.get(y)+"");

            }
            //list.setAdapter(adapter);
            //list.setOnItemClickListener(InterestFragment.this);
            // similar.clear();
        }

        @Override
        protected void onPreExecute() {


        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == 2890) {
                buildList();
                if (simListAdapter != null)
                    simListAdapter.notifyDataSetChanged();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

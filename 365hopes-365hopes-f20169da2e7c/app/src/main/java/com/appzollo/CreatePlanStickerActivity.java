package com.appzollo;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;


public class CreatePlanStickerActivity extends Activity {

    private static final int RESULT_CODE_PICKER_IMAGES = 9000;
    private static Gallery gallery;
    private static Uri[] mUrls;
    String[] filePath1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_plan_sticker);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayUseLogoEnabled(false);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_plan_sticker, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }else if(id == android.R.id.home){
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_create_plan_sticker, container, false);
            Button btGallery = (Button)rootView.findViewById(R.id.bt_gallery);
             gallery = (Gallery)rootView.findViewById(R.id.gallery);

            btGallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    getActivity().startActivityForResult(Intent.createChooser(intent,"Select Picture"), RESULT_CODE_PICKER_IMAGES);
                }
            });
            return rootView;
        }
    }

    void showGallery(String uris[]){
        mUrls = new Uri[uris.length];
        for(int i=0; i < uris.length; i++)
        {
            mUrls[i] = Uri.parse(uris[i]);
        }
        Log.e("lentgh", uris.length + "-" + mUrls
                .length); Toast.makeText(this,uris.length + "-" + mUrls
                .length,Toast.LENGTH_SHORT).show();
        gallery.setAdapter(new ImageAdapter(this));
        gallery.setFadingEdgeLength(40);
    }
    public static  class ImageAdapter extends BaseAdapter {

        int mGalleryItemBackground;
        public ImageAdapter(Context c) {
            mContext = c;
        }
        public int getCount(){
            return mUrls.length;
        }
        public Object getItem(int position){
            return position;
        }
        public long getItemId(int position) {
            return position;
        }
        public View getView(int position, View convertView, ViewGroup parent){
            ImageView i = new ImageView(mContext);

            i.setImageURI(mUrls[position]);
            //i.setScaleType(ImageView.ScaleType.FIT_XY);
         //   i.setLayoutParams(new Gallery.LayoutParams(260, 210));
            return i;
        }
        private Context mContext;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Toast.makeText(this,requestCode+"ok",Toast.LENGTH_SHORT).show();
        switch (requestCode){
            case RESULT_CODE_PICKER_IMAGES:
                if(resultCode == Activity.RESULT_OK){
                    Uri pickedImage = data.getData();
                    // Let's read picked image path using content resolver
                    String[] filePath = { MediaStore.Images.Media.DATA };
                    Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
                    cursor.moveToFirst();
                    Toast.makeText(this,filePath.length+"ok",Toast.LENGTH_SHORT).show();
                    showGallery(filePath);
                }
                break;

            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }
}

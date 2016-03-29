package com.appzollo.classes;

/**
 * Created by vijay on 12-11-2014.
 */

        import java.util.ArrayList;

        import android.app.Activity;
        import android.graphics.Typeface;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.appzollo.R;

public class CustomList extends ArrayAdapter<String> {
    private final Activity context;
    private final ArrayList<String> web;
    int images[];
    Typeface tf;
    int checkAuto ;

    public CustomList(Activity context, ArrayList<String> web, int[] images,int checkAuto) {
        super(context, R.layout.autocomplete_row, web);
        this.context = context;
        this.web = web;
        this.images = images;
        this.checkAuto = checkAuto;

    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.auto_row, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.tv_sug_head);
        TextView txtTitle_sub = (TextView) rowView.findViewById(R.id.tv_sug_subhead);
        ImageView ivDir = (ImageView)rowView.findViewById(R.id.iv_dir);

            ivDir.setBackgroundResource(images[0]);





        String str[] = web.get(position).toString().split(", ",2);
        txtTitle.setText(str[0]);
        if(str.length > 1){
            txtTitle_sub.setText(str[1]);
        }



        return rowView;
    }
}


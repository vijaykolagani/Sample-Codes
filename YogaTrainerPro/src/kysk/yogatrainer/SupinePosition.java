package kysk.yogatrainer;

import java.util.List;
import java.util.Vector;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ParseException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class SupinePosition extends ListActivity {

	private LayoutInflater inflater;
	private Vector<RowData> data;
	RowData rd;
	CustomAdapter adapter;

	Integer[] imageIds = { R.drawable.fish_pose, R.drawable.full_wheel_pose,
			R.drawable.gas_release_pose, R.drawable.inverted_pose,
			R.drawable.leg_raised_pose };
	String[] positions;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_layout);

		positions = getResources().getStringArray(R.array.supine_position);
		inflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		data = new Vector<RowData>();

		for (int i = 0; i < positions.length; i++) {

			try {
				rd = new RowData(positions[i], imageIds[i]);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			data.add(rd);
		}

		adapter = new CustomAdapter(this, R.layout.row_layout, R.id.title, data);
		setListAdapter(adapter);
		getListView().setTextFilterEnabled(true);
	}

	public void onListItemClick(ListView parent, View v, int position, long id) {
		if (position == 0)
			startActivity(new Intent(SupinePosition.this, FishPose.class));
		else if (position == 1)
			startActivity(new Intent(SupinePosition.this, FullWheelPose.class));
		else if (position == 2)
			startActivity(new Intent(SupinePosition.this, GasReleasePose.class));
		else if (position == 3)
			startActivity(new Intent(SupinePosition.this, InvertedPose.class));
		else if (position == 4)
			startActivity(new Intent(SupinePosition.this, LegRaisedPose.class));
	}

	class RowData {
		String text;
		int resource;

		public RowData(String text, int resource) {
			this.text = text;
			this.resource = resource;
		}

		public String getText() {
			return text;
		}

		public int getResource() {
			return resource;
		}
	}

	class CustomAdapter extends ArrayAdapter<RowData> {

		public CustomAdapter(Context context, int resource,
				int textViewResourceId, List<RowData> objects) {
			super(context, resource, textViewResourceId, objects);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup viewGroup) {
			ViewHolder holder = null;
			TextView title = null;
			ImageView image = null;

			RowData row = (RowData) getItem(position);

			if (convertView == null) {
				convertView = inflater.inflate(R.layout.row_layout, null);
				holder = new ViewHolder(convertView);
				convertView.setTag(holder);
			}

			holder = (ViewHolder) convertView.getTag();

			title = holder.getTitle();
			title.setText(row.text);

			image = holder.getImage();
			image.setImageResource(row.resource);

			return convertView;
		}

		private class ViewHolder {
			private View mRow;
			private TextView title = null;
			private ImageView image = null;

			public ViewHolder(View row) {
				mRow = row;
			}

			public TextView getTitle() {
				if (null == title) {
					title = (TextView) mRow.findViewById(R.id.title);
				}
				return title;
			}

			public ImageView getImage() {
				if (null == image) {
					image = (ImageView) mRow.findViewById(R.id.image);
				}
				return image;
			}
		}
	}
}
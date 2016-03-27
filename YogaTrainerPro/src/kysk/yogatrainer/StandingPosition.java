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

public class StandingPosition extends ListActivity {

	private LayoutInflater inflater;
	private Vector<RowData> data;
	RowData rd;
	CustomAdapter adapter;
	Integer[] imageIds = { R.drawable.drum_pose, R.drawable.difficult_pose,
			R.drawable.eagle_pose, R.drawable.ferocious_pose,
			R.drawable.gracious_warrior_pose, R.drawable.hand_to_leg_pose,
			R.drawable.headstand_pose, R.drawable.kite_pose,
			R.drawable.triangle_pose, R.drawable.tree_pose,
			R.drawable.twisted_triangle_pose, R.drawable.warrior_pose };
	String[] positions;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_layout);
		
		positions = getResources().getStringArray(R.array.standing_position);
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
			startActivity(new Intent(StandingPosition.this, DrumPose.class));
		else if (position == 1)
			startActivity(new Intent(StandingPosition.this, DifficultPose.class));
		else if (position == 2)
			startActivity(new Intent(StandingPosition.this, EaglePose.class));
		else if (position == 3)
			startActivity(new Intent(StandingPosition.this, FerociousPose.class));
		else if (position == 4)
			startActivity(new Intent(StandingPosition.this,
					GraciousWarriorPose.class));
		else if (position == 5)
			startActivity(new Intent(StandingPosition.this, HandToLegPose.class));
		else if (position == 6)
			startActivity(new Intent(StandingPosition.this, HeadStandPose.class));
		else if (position == 7)
			startActivity(new Intent(StandingPosition.this, KitePose.class));
		else if (position == 8)
			startActivity(new Intent(StandingPosition.this, TrianglePose.class));
		else if (position == 9)
			startActivity(new Intent(StandingPosition.this, TreePose.class));
		else if (position == 10)
			startActivity(new Intent(StandingPosition.this,
					TwistedTrianglePose.class));
		else if (position == 11)
			startActivity(new Intent(StandingPosition.this, WarriorPose.class));

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
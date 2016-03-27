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

public class SittingPosition extends ListActivity {

	private LayoutInflater inflater;
	private Vector<RowData> data;
	RowData rd;
	CustomAdapter adapter;

	Integer[] imageIds = { R.drawable.auspicious_pose, R.drawable.balance_pose,
			R.drawable.bow_pose_1, R.drawable.cockerel_pose,
			R.drawable.crow_pose, R.drawable.foetus_pose,
			R.drawable.forward_bend_pose, R.drawable.half_lotus_bound_pose,
			R.drawable.lotus_pose, R.drawable.lifted_lotus_pose,
			R.drawable.lion_pose, R.drawable.mountain_pose,
			R.drawable.single_toe_balance_pose,
			R.drawable.single_legto_head_pose, R.drawable.swan_pose,
			R.drawable.scale_pose, R.drawable.tied_lotus_pose,
			R.drawable.toe_balance_pose, R.drawable.thunder_bolt_pose };
	String[] positions;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_layout);

		positions = getResources().getStringArray(R.array.sitting_position);
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
			startActivity(new Intent(SittingPosition.this, AuspiciousPose.class));
		else if (position == 1)
			startActivity(new Intent(SittingPosition.this, BalancePose.class));
		else if (position == 2)
			startActivity(new Intent(SittingPosition.this, BowPose.class));
		else if (position == 3)
			startActivity(new Intent(SittingPosition.this, CockerelPose.class));
		else if (position == 4)
			startActivity(new Intent(SittingPosition.this, CrowPose.class));
		else if (position == 5)
			startActivity(new Intent(SittingPosition.this, FoetusPose.class));
		else if (position == 6)
			startActivity(new Intent(SittingPosition.this, ForwardBend.class));
		else if (position == 7)
			startActivity(new Intent(SittingPosition.this,
					HalfLotusBoundPose.class));
		else if (position == 8)
			startActivity(new Intent(SittingPosition.this, LotusPose.class));
		else if (position == 9)
			startActivity(new Intent(SittingPosition.this, LiftedLotus.class));
		else if (position == 10)
			startActivity(new Intent(SittingPosition.this, LionPose.class));
		else if (position == 11)
			startActivity(new Intent(SittingPosition.this, MountainPose.class));
		else if (position == 12)
			startActivity(new Intent(SittingPosition.this,
					SingleToeBalancePose.class));
		else if (position == 13)
			startActivity(new Intent(SittingPosition.this,
					SingleLegToHeadPose.class));
		else if (position == 14)
			startActivity(new Intent(SittingPosition.this, SwanPose.class));
		else if (position == 15)
			startActivity(new Intent(SittingPosition.this, ScalePose.class));
		else if (position == 16)
			startActivity(new Intent(SittingPosition.this, TiedLotus.class));
		else if (position == 17)
			startActivity(new Intent(SittingPosition.this, ToeBalancePose.class));
		else if (position == 18)
			startActivity(new Intent(SittingPosition.this,
					ThunderboltPose.class));

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
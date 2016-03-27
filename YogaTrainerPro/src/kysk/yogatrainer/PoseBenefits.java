package kysk.yogatrainer;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class PoseBenefits extends Activity {
	
	private TextView pose_heading, pose_description;
	private Button back;
	private String desc[], pose_name;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_layout);
		
		pose_heading = (TextView) findViewById(R.id.pose_heading);
		pose_description = (TextView) findViewById(R.id.pose_description);
		
		back = (Button) findViewById(R.id.back);
		back.setTextColor(Color.parseColor("#FFFFFF"));

		
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				PoseBenefits.this.finish();
			}
		});
		
		Bundle b = getIntent().getExtras();
		int pose = b.getInt("pose");
		pose_name = b.getString("pose_name");
		pose_heading.setText(pose_name + " : "	+ getString(R.string.benefits));
		
		switch (pose) {
		case 1:
			desc = getResources().getStringArray(R.array.auspicious_benefits);
			pose_description.setText(" " + desc[0]);
			break;
		case 2:
			desc = getResources().getStringArray(R.array.balance_benefits);
			pose_description.setText(" " + desc[0]);
			break;
		case 3:
			desc = getResources().getStringArray(R.array.bent_benefits);
			pose_description.setText(" " + desc[0]);
			break;
		case 4:
			desc = getResources().getStringArray(R.array.boat_benefits);
			pose_description.setText(" " + desc[0]);
			break;
		case 5:
			desc = getResources().getStringArray(R.array.bow_benefits);
			pose_description.setText(" " + desc[0]);
			break;
		case 6:
			desc = getResources().getStringArray(R.array.cock_benefits);
			pose_description.setText(" " + desc[0] + "\n\n" + " " + desc[1]
					+ "\n\n" + " " + desc[2]);
			break;
		case 7:
			desc = getResources().getStringArray(R.array.cro_benefits);
			pose_description.setText(" " + desc[0]);
			break;
		case 8:
			desc = getResources().getStringArray(R.array.crow_benefits);
			pose_description.setText(" " + desc[0] + "\n\n" + " " + desc[1]
					+ "\n\n" + " " + desc[2]);
			break;
		case 9:
			desc = getResources().getStringArray(R.array.difficult_benefits);
			pose_description
			.setText(" " + desc[0] + "\n\n" + " " + desc[1]);
			break;
		case 10:
			desc = getResources().getStringArray(R.array.drum_benefits);
			pose_description
			.setText(" " + desc[0] + "\n\n" + " " + desc[1]);
			break;
		case 11:
			desc = getResources().getStringArray(R.array.eagle_benefits);
			pose_description.setText(" " + desc[0] + "\n\n" + " " + desc[1]);
			break;
		case 12:
			desc = getResources().getStringArray(R.array.ferocious_benefits);
			pose_description.setText(" " + desc[0] + "\n\n" + " " + desc[1]
					+ "\n\n" + " " + desc[2] + "\n\n" + " " + desc[3]
					+ "\n\n" + " " + desc[4]);
			break;
		case 13:
			desc = getResources().getStringArray(R.array.fish_benefits);
			pose_description.setText(" " + desc[0]);
			break;
		case 14:
			desc = getResources().getStringArray(R.array.foetus_benefits);
			pose_description.setText(" " + desc[0] + "\n\n" + " " + desc[1]
					+ "\n\n" + " " + desc[2] + "\n\n" + " " + desc[3]
					+ "\n\n" + " " + desc[4] + "\n\n" + " " + desc[5]);
			break;
		case 15:
			desc = getResources().getStringArray(R.array.forward_benefits);
			pose_description.setText(" " + desc[0] + "\n\n" + " " + desc[1]
					+ "\n\n" + " " + desc[2]);
			break;
		case 16:
			desc = getResources().getStringArray(R.array.full_benefits);
			pose_description.setText(" " + desc[0] + "\n\n" + " " + desc[1]
					+ "\n\n" + " " + desc[2] + "\n\n" + " " + desc[3]);
			break;
		case 17:
			desc = getResources().getStringArray(R.array.gas_benefits);
			pose_description.setText(" " + desc[0]);
			break;
		case 18:
			desc = getResources().getStringArray(R.array.gracious_benefits);
			pose_description.setText(" " + desc[0] + "\n\n" + " " + desc[1]
					+ "\n\n" + " " + desc[2] + "\n\n" + " " + desc[3]
					+ "\n\n" + " " + desc[4]);
			break;
		case 19:
			desc = getResources().getStringArray(R.array.hlb_benefits);
			pose_description.setText(" " + desc[0] + "\n\n" + " " + desc[1]
					+ "\n\n" + " " + desc[2] + "\n\n" + " " + desc[3]);
			break;
		case 20:
			desc = getResources().getStringArray(R.array.hand_benefits);
			pose_description.setText(" " + desc[0] + "\n\n" + " " + desc[1]
					+ "\n\n" + " " + desc[2] + "\n\n" + " " + desc[3]);
			break;
		case 21:
			desc = getResources().getStringArray(R.array.head_benefits);
			pose_description.setText(" " + desc[0] + "\n\n" + " " + desc[1]
					+ "\n\n" + " " + desc[2] + "\n\n" + " " + desc[3]
					+ "\n\n" + " " + desc[4]);
			break;
		case 22:
			desc = getResources().getStringArray(R.array.in_benefits);
			pose_description.setText(" " + desc[0]);
			break;
		case 23:
			desc = getResources().getStringArray(R.array.kite_benefits);
			pose_description.setText(" " + desc[0] + "\n\n" + " " + desc[1]
					+ "\n\n" + " " + desc[2]);
			break;
		case 24:
			desc = getResources().getStringArray(R.array.leg_benefits);
			pose_description.setText(" " + desc[0]);
			break;
		case 25:
			desc = getResources().getStringArray(R.array.lifted_benefits);
			pose_description.setText(" " + desc[0]);
			break;
		case 26:
			desc = getResources().getStringArray(R.array.lion_benefits);
			pose_description
			.setText(" " + desc[0] + "\n\n" + " " + desc[1]);
			break;
		case 27:
			desc = getResources().getStringArray(R.array.lotus_benefits);
			pose_description.setText(" " + desc[0]);
			break;
		case 28:
			desc = getResources().getStringArray(R.array.mt_benefits);
			pose_description.setText(" " + desc[0]);
			break;
		case 29:
			desc = getResources().getStringArray(R.array.scale_benefits);
			pose_description.setText(" " + desc[0] + "\n\n" + " " + desc[1]
					+ "\n\n" + " " + desc[2]);
			break;
		case 30:
			desc = getResources().getStringArray(R.array.slhp_benefits);
			pose_description.setText(" " + desc[0] + "\n\n" + " " + desc[1]
					+ "\n\n" + " " + desc[2] + "\n\n" + " " + desc[3]
					+ "\n\n" + " " + desc[4] + "\n\n" + " " + desc[5]);
			break;
		case 31:
			desc = getResources().getStringArray(R.array.stbp_benefits);
			pose_description.setText(" " + desc[0] + "\n\n" + " " + desc[1]
					+ "\n\n" + " " + desc[2] + "\n\n" + " " + desc[3]
					+ "\n\n" + " " + desc[4]);
			break;
		case 32:
			desc = getResources().getStringArray(R.array.swan_benefits);
			pose_description.setText(" " + desc[0] + "\n\n" + " " + desc[1]
					+ "\n\n" + " " + desc[2]);
			break;
		case 33:
			desc = getResources().getStringArray(R.array.th_benefits);
			pose_description
			.setText(" " + desc[0] + "\n\n" + " " + desc[1]);
			break;
		case 34:
			desc = getResources().getStringArray(R.array.tied_benefits);
			pose_description.setText(" " + desc[0]);
			break;
		case 35:
			desc = getResources().getStringArray(R.array.toe_benefits);
			pose_description
			.setText(" " + desc[0] + "\n\n" + " " + desc[1]);
			break;
		case 36:
			desc = getResources().getStringArray(R.array.tree_benefits);
			pose_description
			.setText(" " + desc[0] + "\n\n" + " " + desc[1]);
			break;
		case 37:
			desc = getResources().getStringArray(R.array.triangle_benefits);
			pose_description.setText(" " + desc[0]);
			break;
		case 38:
			desc = getResources().getStringArray(R.array.twisted_benefits);
			pose_description.setText(" " + desc[0] + "\n\n" + " " + desc[1]
					+ "\n\n" + " " + desc[2] + "\n\n" + " " + desc[3]
					+ "\n\n" + " " + desc[4]);
			break;
		case 39:
			desc = getResources().getStringArray(R.array.warrior_benefits);
			pose_description.setText(" " + desc[0]);
			break;
		default:
			break;
			
		}
		
	}

}

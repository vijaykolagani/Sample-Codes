package com.onetaxi.fragments;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.onetaxi.taxiplace.Caldroid;
import com.onetaxi.taxiplace.R;
import com.onetaxi.taxiplace.SearchCity;
import com.onetaxi.taxiplace.SearchTaxi;

public class HomeFragment extends Fragment implements OnClickListener{
	LinearLayout llFrom,llTo,llDateOneWay,llDateReturn;
	TextView tvDateOneWay,tvDayOneWay,tvMonthOneWay,tvDateReturn,tvDayReturn,tvMonthReturn,tvFrom,tvTo;
	Button btSearch;
	
	SharedPreferences pref;
	public HomeFragment(){

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_home, container, false);
		pref = getActivity().getSharedPreferences(
				"com.onetaxi.taxiplace", Context.MODE_PRIVATE);
		initViews(rootView);

		return rootView;
	}

	void initViews(View v){
		llFrom = (LinearLayout) v.findViewById(R.id.ll_from);
		llTo = (LinearLayout) v.findViewById(R.id.ll_to);
		llDateOneWay = (LinearLayout) v.findViewById(R.id.ll_oneway);
		llDateReturn = (LinearLayout) v.findViewById(R.id.ll_return);

		tvFrom = (TextView) v.findViewById(R.id.tv_from);
		tvTo = (TextView) v.findViewById(R.id.tv_to);
		tvDateOneWay = (TextView) v.findViewById(R.id.tv_date_oneway);
		tvDayOneWay = (TextView) v.findViewById(R.id.tv_day_oneway);
		tvMonthOneWay = (TextView) v.findViewById(R.id.tv_month_oneway);
		tvDateReturn = (TextView) v.findViewById(R.id.tv_date_return);
		tvDayReturn = (TextView) v.findViewById(R.id.tv_day_return);
		tvMonthReturn = (TextView) v.findViewById(R.id.tv_month_return);
		
		btSearch = (Button) v.findViewById(R.id.bt_search);

		tvFrom.setText(pref.getString("from", "Enter City"));
		tvTo.setText(pref.getString("to", "Enter City"));


		setDateOneWay();
		setDateReturn();
		llFrom.setOnClickListener(this);
		llTo.setOnClickListener(this);
		llDateOneWay.setOnClickListener(this);
		llDateReturn.setOnClickListener(this);
		btSearch.setOnClickListener(this);

	}

	void setDateOneWay(){
		if(pref.getString("dateoneway", "").length() <1){
			Calendar c = Calendar.getInstance();
			int day = c.get(Calendar.DAY_OF_WEEK);
			int month = c.get(Calendar.MONTH);

			if(day == Calendar.MONDAY){
				tvDayOneWay.setText("MONDAY");
			}else if(day == Calendar.TUESDAY){
				tvDayOneWay.setText("TUESDAY");
			}else if(day == Calendar.WEDNESDAY){
				tvDayOneWay.setText("WEDNESDAY");
			}else if(day == Calendar.THURSDAY){
				tvDayOneWay.setText("THURSDAY");
			}else if(day == Calendar.FRIDAY){
				tvDayOneWay.setText("FRIDAY");
			}else if(day == Calendar.SATURDAY){
				tvDayOneWay.setText("SATURDAY");
			}else if(day == Calendar.SUNDAY){
				tvDayOneWay.setText("SUNDAY");
			}

			if(month == Calendar.JANUARY){
				tvMonthOneWay.setText("JANUARY");
			}else if(month == Calendar.FEBRUARY){
				tvMonthOneWay.setText("FEBRUARY");
			}else if(month == Calendar.MARCH){
				tvMonthOneWay.setText("MARCH");
			}else if(month == Calendar.APRIL){
				tvMonthOneWay.setText("APRIL");
			}else if(month == Calendar.MAY){
				tvMonthOneWay.setText("MAY");
			}else if(month == Calendar.JUNE){
				tvMonthOneWay.setText("JUNE");
			}else if(month == Calendar.JULY){
				tvMonthOneWay.setText("JULY");
			}else if(month == Calendar.AUGUST){
				tvMonthOneWay.setText("AUGUST");
			}else if(month == Calendar.SEPTEMBER){
				tvMonthOneWay.setText("SEPTEMBER");
			}else if(month == Calendar.OCTOBER){
				tvMonthOneWay.setText("OCTOBER");
			}else if(month == Calendar.NOVEMBER){
				tvMonthOneWay.setText("NOVEMBER");
			}else if(month == Calendar.DECEMBER){
				tvMonthOneWay.setText("DECEMBER");
			}  	
			tvDateOneWay.setText(c.get(Calendar.DATE)+"");
		}else{
			try {
				SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
				
				java.util.Date date = formatter.parse(pref.getString("dateoneway", ""));
				tvDateOneWay.setText(date.getDate()+"");
				int day = date.getDay()+1;
				int month = date.getMonth();
				
				if(day == Calendar.MONDAY){
					tvDayOneWay.setText("MONDAY");
				}else if(day == Calendar.TUESDAY){
					tvDayOneWay.setText("TUESDAY");
				}else if(day == Calendar.WEDNESDAY){
					tvDayOneWay.setText("WEDNESDAY");
				}else if(day == Calendar.THURSDAY){
					tvDayOneWay.setText("THURSDAY");
				}else if(day == Calendar.FRIDAY){
					tvDayOneWay.setText("FRIDAY");
				}else if(day == Calendar.SATURDAY){
					tvDayOneWay.setText("SATURDAY");
				}else if(day == Calendar.SUNDAY){
					tvDayOneWay.setText("SUNDAY");
				}

				if(month == Calendar.JANUARY){
					tvMonthOneWay.setText("JANUARY");
				}else if(month == Calendar.FEBRUARY){
					tvMonthOneWay.setText("FEBRUARY");
				}else if(month == Calendar.MARCH){
					tvMonthOneWay.setText("MARCH");
				}else if(month == Calendar.APRIL){
					tvMonthOneWay.setText("APRIL");
				}else if(month == Calendar.MAY){
					tvMonthOneWay.setText("MAY");
				}else if(month == Calendar.JUNE){
					tvMonthOneWay.setText("JUNE");
				}else if(month == Calendar.JULY){
					tvMonthOneWay.setText("JULY");
				}else if(month == Calendar.AUGUST){
					tvMonthOneWay.setText("AUGUST");
				}else if(month == Calendar.SEPTEMBER){
					tvMonthOneWay.setText("SEPTEMBER");
				}else if(month == Calendar.OCTOBER){
					tvMonthOneWay.setText("OCTOBER");
				}else if(month == Calendar.NOVEMBER){
					tvMonthOneWay.setText("NOVEMBER");
				}else if(month == Calendar.DECEMBER){
					tvMonthOneWay.setText("DECEMBER");
				}  	
		 
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}

	void setDateReturn(){
		if(pref.getString("datereturn", "").length() <1){
			Calendar c = Calendar.getInstance();
			int day = c.get(Calendar.DAY_OF_WEEK);
			int month = c.get(Calendar.MONTH);

			if(day == Calendar.MONDAY){
				tvDayReturn.setText("MONDAY");
			}else if(day == Calendar.TUESDAY){
				tvDayReturn.setText("TUESDAY");
			}else if(day == Calendar.WEDNESDAY){
				tvDayReturn.setText("WEDNESDAY");
			}else if(day == Calendar.THURSDAY){
				tvDayReturn.setText("THURSDAY");
			}else if(day == Calendar.FRIDAY){
				tvDayReturn.setText("FRIDAY");
			}else if(day == Calendar.SATURDAY){
				tvDayReturn.setText("SATURDAY");
			}else if(day == Calendar.SUNDAY){
				tvDayReturn.setText("SUNDAY");
			}

			if(month == Calendar.JANUARY){
				tvMonthReturn.setText("JANUARY");
			}else if(month == Calendar.FEBRUARY){
				tvMonthReturn.setText("FEBRUARY");
			}else if(month == Calendar.MARCH){
				tvMonthReturn.setText("MARCH");
			}else if(month == Calendar.APRIL){
				tvMonthReturn.setText("APRIL");
			}else if(month == Calendar.MAY){
				tvMonthReturn.setText("MAY");
			}else if(month == Calendar.JUNE){
				tvMonthReturn.setText("JUNE");
			}else if(month == Calendar.JULY){
				tvMonthReturn.setText("JULY");
			}else if(month == Calendar.AUGUST){
				tvMonthReturn.setText("AUGUST");
			}else if(month == Calendar.SEPTEMBER){
				tvMonthReturn.setText("SEPTEMBER");
			}else if(month == Calendar.OCTOBER){
				tvMonthReturn.setText("OCTOBER");
			}else if(month == Calendar.NOVEMBER){
				tvMonthReturn.setText("NOVEMBER");
			}else if(month == Calendar.DECEMBER){
				tvMonthReturn.setText("DECEMBER");
			}  	
			tvDateReturn.setText(c.get(Calendar.DATE)+"");
		}else{
			try {
				SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
				
				java.util.Date date = formatter.parse(pref.getString("datereturn", ""));
				tvDateReturn.setText(date.getDate()+"");
				int day = date.getDay()+1;
				int month = date.getMonth();
				
				if(day == Calendar.MONDAY){
					tvDayReturn.setText("MONDAY");
				}else if(day == Calendar.TUESDAY){
					tvDayReturn.setText("TUESDAY");
				}else if(day == Calendar.WEDNESDAY){
					tvDayReturn.setText("WEDNESDAY");
				}else if(day == Calendar.THURSDAY){
					tvDayReturn.setText("THURSDAY");
				}else if(day == Calendar.FRIDAY){
					tvDayReturn.setText("FRIDAY");
				}else if(day == Calendar.SATURDAY){
					tvDayReturn.setText("SATURDAY");
				}else if(day == Calendar.SUNDAY){
					tvDayReturn.setText("SUNDAY");
				}

				if(month == Calendar.JANUARY){
					tvMonthReturn.setText("JANUARY");
				}else if(month == Calendar.FEBRUARY){
					tvMonthReturn.setText("FEBRUARY");
				}else if(month == Calendar.MARCH){
					tvMonthReturn.setText("MARCH");
				}else if(month == Calendar.APRIL){
					tvMonthReturn.setText("APRIL");
				}else if(month == Calendar.MAY){
					tvMonthReturn.setText("MAY");
				}else if(month == Calendar.JUNE){
					tvMonthReturn.setText("JUNE");
				}else if(month == Calendar.JULY){
					tvMonthReturn.setText("JULY");
				}else if(month == Calendar.AUGUST){
					tvMonthReturn.setText("AUGUST");
				}else if(month == Calendar.SEPTEMBER){
					tvMonthReturn.setText("SEPTEMBER");
				}else if(month == Calendar.OCTOBER){
					tvMonthReturn.setText("OCTOBER");
				}else if(month == Calendar.NOVEMBER){
					tvMonthReturn.setText("NOVEMBER");
				}else if(month == Calendar.DECEMBER){
					tvMonthReturn.setText("DECEMBER");
				}  	
		 
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}

	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ll_from:
			Intent intent = new Intent(getActivity(), SearchCity.class).putExtra("type", 1);
			getActivity().startActivity(intent);
			getActivity().overridePendingTransition( 
					R.anim.activity_push_up_in,
					R.anim.push_up_out);
			getActivity().finish();
			break;
		case R.id.ll_to:
			Intent intent1 = new Intent(getActivity(), SearchCity.class).putExtra("type", 2);
			getActivity().startActivity(intent1);
			getActivity().overridePendingTransition( 
					R.anim.activity_push_up_in,
					R.anim.push_up_out);
			getActivity().finish();
			break;
		case R.id.ll_oneway:
			Intent intent2 = new Intent(getActivity(), Caldroid.class).putExtra("type", 1);
			getActivity().startActivity(intent2);
			getActivity().overridePendingTransition( 
					R.anim.activity_push_up_in,
					R.anim.push_up_out);
			getActivity().finish();
			break;
		case R.id.ll_return:
			Intent intent3 = new Intent(getActivity(), Caldroid.class).putExtra("type", 2);
			getActivity().startActivity(intent3);
			getActivity().overridePendingTransition( 
					R.anim.activity_push_up_in,
					R.anim.push_up_out);
			getActivity().finish();
			break;
			
		case R.id.bt_search:
			Intent intent4 = new Intent(getActivity(), SearchTaxi.class);
			getActivity().startActivity(intent4);
			getActivity().overridePendingTransition( 
					R.anim.push_side_out,
					R.anim.push_side_in);
			break;
		default:
			break;
		}
	}




}

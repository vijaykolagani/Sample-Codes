package com.onetaxi.slidingmenu.adapter;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import android.app.Activity;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.onetaxi.taxiplace.R;




public class SearchAdapter extends ArrayAdapter<String> {
	private final Activity context;
	private ArrayList<String> web;
	private ArrayList<String> cost;
	private ArrayList<String> web1;
	private ArrayList<String> travelsName;
	private ArrayList<String> eta;
	private ArrayList<String> carType;
	private ArrayList<String> seats;

	int sortCost[];

	Typeface tf;


	public SearchAdapter(Activity context, ArrayList<String> web, ArrayList<String> cost, ArrayList<String> travelsName, ArrayList<String> eta,
			ArrayList<String> carType, ArrayList<String> seats) {
		super(context, R.layout.searchadapter, web);
		this.context = context;
		this.web = web;
		this.web1 = cost;
		this.cost = cost;
		this.travelsName = travelsName;
		this.eta = eta;
		this.carType = carType;
		this.seats = seats;

	}


	@Override
	public View getView(int position, View view, ViewGroup parent) {

		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.searchadapter, null, true);
		TextView tvTime = (TextView)rowView.findViewById(R.id.tv_time);
		TextView tvCost = (TextView)rowView.findViewById(R.id.tv_rate);
		TextView tvTravelsName = (TextView)rowView.findViewById(R.id.tv_travelsname);
		TextView tvEta = (TextView)rowView.findViewById(R.id.tv_eta);
		TextView tvCarType = (TextView)rowView.findViewById(R.id.tv_cartype);
		TextView tvSeats = (TextView)rowView.findViewById(R.id.tv_seats);
		if(position < web.size())
			tvTime.setText(web.get(position));
		if(position < cost.size())
			tvCost.setText(cost.get(position));
		if(position < travelsName.size())
			tvTravelsName.setText(travelsName.get(position));
		if(position < eta.size())
			tvEta.setText(eta.get(position));
		if(position < carType.size())
			tvCarType.setText(carType.get(position));
		if(position < seats.size())
			tvSeats.setText(seats.get(position));
		return rowView;
	}

	public Filter getFilter() {
		Filter filter = new Filter() {

			@Override 
			protected FilterResults performFiltering(CharSequence prefijo) {

				FilterResults results = new FilterResults();
				ArrayList<String> aux = new ArrayList<String>();
				ArrayList<String> auxName = new ArrayList<String>();

				// El prefijo tiene que ser mayor que 0 y existir 
				if (prefijo != null && prefijo.toString().length() > 0) {

					for (int index = 0; index < web1.size(); index++) {
						String si = web1.get(index);
						if(prefijo.toString().equalsIgnoreCase("1") ){

							sortCost = new int[cost.size()];
							ArrayList<Integer> test = new ArrayList<Integer>();
							for(int i = 0 ; i < travelsName.size() ; i++){
								sortCost[i] = Integer.parseInt(cost.get(i));
								test.add(Integer.parseInt(cost.get(i)));
							}
							Collections.sort(test);
							for (int number : test) {

								aux.add(number+"");
							}

						}else if(prefijo.toString().equalsIgnoreCase("2")){


							sortCost = new int[cost.size()];

							ArrayList<Integer> test = new ArrayList<Integer>();
							for(int i = 0 ; i < travelsName.size() ; i++){
								sortCost[i] = Integer.parseInt(cost.get(i));
								test.add(Integer.parseInt(cost.get(i)));
								//Log.d("sort",sortCost[i]+"");
							}
							Collections.sort(test, Collections.reverseOrder());
							for (int number = 0 ; number < test.size() ; number++) {
								aux.add(test.get(number)+"");
							}

						}
						else if(prefijo.toString().equalsIgnoreCase("3") || prefijo.toString().equalsIgnoreCase("4")){							
							sortCost = new int[web.size()];
							ArrayList<Integer> test = new ArrayList<Integer>();
							for(int i = 0 ; i < travelsName.size() ; i++){
								String time[] = web.get(i).split(":");
								sortCost[i] = Integer.parseInt(time[0]);
								test.add(Integer.parseInt(time[0]));
								Log.d("sort time",test.get(i)+"");
							}
							if(prefijo.toString().equalsIgnoreCase("3"))
								Collections.sort(test);
							else
								Collections.sort(test, Collections.reverseOrder());
							for (int number = 0 ; number < test.size() ; number++) {
								aux.add(test.get(number)+"");
							}

						}
						else{
							//String nombre = si.getNombre();
							String nom[] = si.split(" ");
							for(int i=0; i<nom.length; i++){
								if (nom[i].toLowerCase().startsWith(prefijo.toString().toLowerCase())) {
									aux.add(si);
								} 
							} 
						}
					}
					results.values = aux;
					results.count = aux.size();
				} else { 
					synchronized (web1) {
						results.values = web1; 
						results.count = web1.size(); 
					} 
				} 
				return results;
			} 

			@SuppressWarnings("unchecked") 
			@Override 
			protected void publishResults(CharSequence prefijo,FilterResults results) {

				ArrayList<String> costDummy = new ArrayList<String>();
				ArrayList<String> costDummy1 = new ArrayList<String>();
				ArrayList<String> travelsDummy = new ArrayList<String>();
				ArrayList<String> timedummy = new ArrayList<String>();
				ArrayList<String> etadummy = new ArrayList<String>();
				ArrayList<String> cartypedummy = new ArrayList<String>();
				ArrayList<String> seatsdummy = new ArrayList<String>();
				costDummy = (ArrayList<String>) results.values;
				boolean check = false; 
				Log.d("prefijo",prefijo.toString());
				if(prefijo.toString().contains("1") || prefijo.toString().contains("2")){
					for(int i = 0 ; i < travelsName.size() ; i++){
						check = true;
						for(int j = 0 ; j < travelsName.size(); j++){

							if(costDummy.get(i).equalsIgnoreCase(cost.get(j))){
								if(check){
									Log.d("check",travelsName.get(j)+","+cost.get(j));
									travelsDummy.add(travelsName.get(j));
									timedummy.add(web.get(j));
									etadummy.add(eta.get(j));
									cartypedummy.add(carType.get(j));
									seatsdummy.add(seats.get(j));
									costDummy1.add(cost.get(j));
									check = false;
								}
							}
						}
					}

				}else if(prefijo.toString().contains("3") || prefijo.toString().contains("4")){
					for(int i = 0 ; i < travelsName.size() ; i++){
						check = true;
						for(int j = 0 ; j < travelsName.size(); j++){
							String time[] = web.get(j).split(":");
							if(costDummy.get(i).equalsIgnoreCase(time[0])){
								if(check){
									Log.d("check",travelsName.get(j)+","+cost.get(j));
									travelsDummy.add(travelsName.get(j));
									timedummy.add(web.get(j));
									etadummy.add(eta.get(j));
									cartypedummy.add(carType.get(j));
									seatsdummy.add(seats.get(j));
									costDummy1.add(cost.get(j));
									check = false;
								}
							}
						}
					}
				}
				travelsName = travelsDummy;
				web = timedummy;
				eta = etadummy;
				carType = cartypedummy;
				seats= seatsdummy;
				cost = costDummy1;
				Log.d("at last cost size",cost.size()+"");

				notifyDataSetChanged();

			} 
		}; 
		return filter;
	} 
}


package com.appzollo.classes;

import java.util.HashMap;

/**
 * Created by Saikrishna on 25-12-2014.
 */
public class Segments {
    private static HashMap<String, String> segments;
    int[] itemId = {10, 11, 9, 48, 44, 12, 42, 13, 37, 55, 14, 39, 15, 16, 8, 46, 43, 53, 17, 18, 22, 45, 19, 20, 54,21};
    int[] itemLocalId1 = {62, 61, 63, 64};
    int[] itemLocalId2 = {57};
    int[] itemLocalId3 = {58};
    int[] itemLocalId4 = {59};

    Segments() {
        segments = new HashMap<String, String>();
        initialize();

    }

    private void initialize() {

        segments.put("58", "Historical Tour");
        segments.put("59", "Wildlife");
        segments.put("10", "Bungee Jumping");
        segments.put("11", "Camping");
        segments.put("9", "Cycling");
        segments.put("48", "Fishing Tours");
        segments.put("44", "Flying Fox");
        segments.put("12", "Hot Air Ballooning");
        segments.put("42", "Jeep Safari");
        segments.put("13", "Kayaking");
        segments.put("37", "Motorcycling");
        segments.put("55", "Mountain Biking");
        segments.put("14", "Mountaineering");
        segments.put("39", "Paintball");
        segments.put("15", "Paragliding");
        segments.put("16", "Parasailing");
        segments.put("8", "Rafting");
        segments.put("46", "Rappelling");
        segments.put("43", "Rock Climbing");
        segments.put("53", "Running");
        segments.put("17", "Scuba Diving");
        segments.put("18", "Skiing");
        segments.put("22", "Sky Diving");
        segments.put("45", "Snorkelling");
        segments.put("19", "Trekking");
        segments.put("20", "Wall Climbing");
        segments.put("54", "Water Sports");
        segments.put("21", "Wild Life Safari");
        segments.put("62", "Art and culture Trip");
        segments.put("61", "Food Trip");
        segments.put("63", "Historical Trip");
        segments.put("64", "Photography Trip");
        segments.put("57","Cultural Tour");
    }

    public static String getSegmentName(String id) {
        new Segments();
        if(Segments.segments.get(id) != null)
            return Segments.segments.get(id);
        else
            return "";
    }
}

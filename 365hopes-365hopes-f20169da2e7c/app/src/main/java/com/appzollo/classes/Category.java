package com.appzollo.classes;

/**
 * Created by vijay on 20-11-2014.
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


public class Category {

    public ArrayList<Category> children;
    public ArrayList<String> selection;


    public String name;

    public Category() {
        children = new ArrayList<Category>();
        selection = new ArrayList<String>();
    }

    public Category(String name) {
        this();
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    // generate some random amount of child objects (1..10)
    private void generateChildren() {
        Random rand = new Random();
        for(int i=0; i < rand.nextInt(9)+1; i++) {
            Category cat = new Category("Child ");
            this.children.add(cat);
        }
    }
    private void generateChildren_adv() {
        final String[] adv = {
                "Bungee Jumping","Camping", "Cycling","Fishing Tours", "Flying Fox","Hot Air Ballooning", "Jeep Safari","Kayaking", "Motorcycling","Mountain Biking", "Mountaineering", "Paintball",
                "Paragliding", "Parasailing","Rafting","Rappelling"
                , "Rock Climbing","Running", "Scuba Diving","Skiing", "Sky Diving", "Snorkelling", "Trekking","Wall Climbing","Water Sports",
        };
        for(int i=0; i < 25; i++) {
            Category cat = new Category(adv[i]);
            this.children.add(cat);
        }
    }
    /*private void generateChildren_sports() {
        final String[] adv = {
                "Badminton", "Billiards", "Boxing", " Cricket", "Formula1", "HorseRiding", "Pool/Snooker", "Skating"
                , "Swimming", "Tennis", "Basketball", "Bowling", "Chess", "Football", "Hockey", "Karate",
                "Shooting", "Squash", "Table Tennis", "Volley ball"
        };
        for(int i=0; i < adv.length; i++) {
            Category cat = new Category(adv[i]);
            this.children.add(cat);
        }
    }*/
    private void generateChildren_local() {
        final String[] adv = {"Art and culture Trip","Food Trip","Historical Trip","Photography Trip"
        };
        for(int i=0; i < adv.length; i++) {
            Category cat = new Category(adv[i]);
            this.children.add(cat);
        }
    }
    private void generateChildren_cultural() {
        final String[] adv = {"Cultural Tour"
        };
        for(int i=0; i < adv.length; i++) {
            Category cat = new Category(adv[i]);
            this.children.add(cat);
        }
    }
    private void generateChildren_historical() {
        final String[] adv = {"Historical Tour"
        };
        for(int i=0; i < adv.length; i++) {
            Category cat = new Category(adv[i]);
            this.children.add(cat);
        }
    }
    private void generateChildren_wildlife() {
        final String[] adv = {"Wildlife"
        };
        for(int i=0; i < adv.length; i++) {
            Category cat = new Category(adv[i]);
            this.children.add(cat);
        }
    }

    public static ArrayList<Category> getCategories() {
        ArrayList<Category> categories = new ArrayList<Category>();
        for(int i = 0; i < 10 ; i++) {

            if(i == 0){
                Category cat = new Category("Adventure");
                cat.generateChildren_adv();
                categories.add(cat);
            }/*else if(i == 1){
                Category cat = new Category("Sports");
                cat.generateChildren_sports();
                categories.add(cat);
            }*/else if(i == 2){
                Category cat = new Category("Local Tour");
                cat.generateChildren_local();
                categories.add(cat);
            }else if(i == 3){
                Category cat = new Category("Cultural Tour");
                cat.generateChildren_cultural();
                categories.add(cat);
            }else if(i == 4){
                Category cat = new Category("Historical Tour");
                cat.generateChildren_historical();
                categories.add(cat);
            }else if(i == 5){
                Category cat = new Category("Wildlife");
                cat.generateChildren_wildlife();
                categories.add(cat);
            }



        }
        return categories;
    }

    public static Category get(String name)
    {
        ArrayList<Category> collection = Category.getCategories();
        for (Iterator<Category> iterator = collection.iterator(); iterator.hasNext();) {
            Category cat = (Category) iterator.next();
            if(cat.name.equals(name)) {
                return cat;
            }

        }
        return null;
    }
}
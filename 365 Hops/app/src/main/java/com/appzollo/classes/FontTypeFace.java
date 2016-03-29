package com.appzollo.classes;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by prasad on 12/21/2014.
 */
public class FontTypeFace {
   public static Typeface tfsegsb,tfseg,tfsegb,tfsegl,tflatosb,tflatom;

    public FontTypeFace(Context ctx){
        tfsegsb = Typeface.createFromAsset(ctx.getAssets(), "seguisb.ttf");
        tfseg = Typeface.createFromAsset(ctx.getAssets(), "segoeui.ttf");
        tfsegb = Typeface.createFromAsset(ctx.getAssets(), "segoeuib.ttf");
        tfsegl = Typeface.createFromAsset(ctx.getAssets(), "segoeuil.ttf");
        tflatosb = Typeface.createFromAsset(ctx.getAssets(), "latosb.ttf");
        tflatom = Typeface.createFromAsset(ctx.getAssets(), "latom.ttf");
    }

    public static Typeface getTfsegsb(Context ctx) {
        return Typeface.createFromAsset(ctx.getAssets(), "seguisb.ttf");
    }

    public static Typeface getTfseg() {
        return tfseg;
    }

    public static Typeface getTfsegb() {
        return tfsegb;
    }

    public static Typeface getTfsegl() {
        return tfsegl;
    }

    public static Typeface getTflatosb() {
        return tflatosb;
    }

    public static Typeface getTflatom() {
        return tflatom;
    }
}

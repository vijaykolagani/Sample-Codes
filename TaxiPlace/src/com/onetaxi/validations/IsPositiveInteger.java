package com.onetaxi.validations;

import com.onetaxi.taxiplace.R;

import android.content.Context;

 

 
public class IsPositiveInteger extends BaseValidation {

    public static final String POSITIVE_INT_PATTERN = "\\d+";

    private IsPositiveInteger(Context context) {
        super(context);
    }

    public static Validation build(Context context) {
        return new IsPositiveInteger(context);
    }

    @Override
    public String getErrorMessage() {
        return mContext.getString(R.string.zvalidations_not_positive_integer);
    }

    @Override
    public boolean isValid(String text) {
        return text.matches(POSITIVE_INT_PATTERN);
    }
}
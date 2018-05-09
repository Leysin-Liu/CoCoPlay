package com.leysin.cocoplay.view;

import android.view.animation.RotateAnimation;

public class CustomRotateAnimation extends RotateAnimation{
		
    public CustomRotateAnimation(float fromDegrees, float toDegrees, int pivotXType, float pivotXValue,
            int pivotYType, float pivotYValue) {
    				super(fromDegrees,toDegrees,pivotXType,pivotXValue,pivotYType,pivotYValue);
    }
}

package com.example.smalley.sakhatyla2;

import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by Smalley on 4/14/2016.
 */
public class ButtonHighlighterOnTouchListener {

    final ImageButton imageButton;


    public ButtonHighlighterOnTouchListener(final ImageButton imageButton) {
        super();
        this.imageButton = imageButton;
    }


    public boolean onTouch(final View view, final MotionEvent motionEvent, final ImageButton imageButton) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            //grey color filter, you can change the color as you like
            imageButton.setColorFilter(Color.argb(155, 185, 185, 185));
        } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            imageButton.setColorFilter(Color.argb(0, 185, 185, 185));
        }
        return false;
    }


}
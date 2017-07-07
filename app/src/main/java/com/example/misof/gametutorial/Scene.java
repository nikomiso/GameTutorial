package com.example.misof.gametutorial;

import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * Created by misof on 4/7/17.
 */

public interface Scene {
    public void update();
    public void draw(Canvas canvas);
    public void terminate();
    public void recieveTouch(MotionEvent event);
}

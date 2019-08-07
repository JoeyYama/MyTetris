package com.jyamanouchi.mytetris;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

public class GameActivity extends Activity implements View.OnClickListener, GestureDetector.OnGestureListener {

    GameSurface gameSurface;
    FrameLayout frameLayout;
    RelativeLayout relativeLayout;
    Button leftButton, rotateButton, rightButton;
    GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameSurface = new GameSurface(this);
        frameLayout = new FrameLayout(this);
        relativeLayout = new RelativeLayout(this);
        gestureDetector = new GestureDetector(this);

        leftButton = new Button(this);
        leftButton.setText("Left");
        leftButton.setId(1);

        rotateButton = new Button(this);
        rotateButton.setText("Rotate");
        rotateButton.setId(2);

        rightButton = new Button(this);
        rightButton.setText("Right");
        rightButton.setId(3);

        RelativeLayout.LayoutParams r1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams lButton = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams rButton = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams roButton = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        relativeLayout.setLayoutParams(r1);
        relativeLayout.addView(leftButton);
        relativeLayout.addView(rotateButton);
        relativeLayout.addView(rightButton);

        lButton.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        lButton.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);

        roButton.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        roButton.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);

        rButton.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        rButton.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);

        leftButton.setLayoutParams(lButton);
        rightButton.setLayoutParams(rButton);
        rotateButton.setLayoutParams(roButton);

        frameLayout.addView(gameSurface);
        frameLayout.addView(relativeLayout);

        setContentView(frameLayout);

        View lButtonListener = findViewById(1);
        lButtonListener.setOnClickListener(this);

        View roButtonListener = findViewById(2);
        roButtonListener.setOnClickListener(this);

        View rButtonListener = findViewById(3);
        rButtonListener.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == leftButton) {
            gameSurface.moveLeft();
        } else if (v == rightButton) {
            gameSurface.moveRight();
        } else if (v == rotateButton) {
            gameSurface.rotate();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameSurface.pause();
    }

    //running the game when activity is resumed
    @Override
    protected void onResume() {
        super.onResume();
        gameSurface.resume();
    }

    @Override
    public boolean onDown(MotionEvent e) {
        if(gameSurface.gameOver) {
            startActivity(new Intent(GameActivity.this, MainActivity.class));
            return true;
        }
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        boolean result=false;
        float diffX = e2.getX() - e1.getX();
        float diffY = e2.getY() - e1.getY();
        if(Math.abs(diffX) > Math.abs(diffY)) {
            if(diffX > 50) {
                gameSurface.hardRight();
                result = true;
            } else if(diffX < -50) {
                gameSurface.hardLeft();
                result = true;
            }
        } else {
            if(diffY> 50) {
                gameSurface.hardDown();
                result = true;
            }
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

}

package com.example.misof.gametutorial;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

/**
 * Created by misof on 18/6/17.
 */

public class ObstacleManager {
    //Higher index = lower screen = higher Y value - tut3

    private ArrayList<Obstacle> obstacles;
    private int playerGap;
    private int obstacleGap;
    private int obstacleHeight;
    private int color;

    private long startTime;
    private long initTime;

    private int score;

    public ObstacleManager(int playerGap, int obstacleGap, int obstacleHeight, int color){
        this.playerGap = playerGap;
        this.obstacleGap = obstacleGap;
        this.obstacleHeight = obstacleHeight;
        this.color = color;

        startTime = initTime = System.currentTimeMillis();

        obstacles = new ArrayList<>();

        populateObstacles();
    }

    public boolean playerCollide (RectPlayer player){
        for (Obstacle ob : obstacles){
            if (ob.playerCollide(player)){
                return true;
            }
        }
        return false;
    }

    private void populateObstacles() {
        int currY = -5*Constants.SCREEN_HEIGHT/4;
        //generate random obstacles - tut3
        while (currY < 0){
            int xStart = (int)(Math.random() * (Constants.SCREEN_WIDTH - playerGap)); //generate x value - tut3
            obstacles.add(new Obstacle(obstacleHeight, color, xStart, currY, playerGap));
            currY += obstacleHeight + obstacleGap;
        }
    }

    public void update(){
        int elapsedTime = (int)(System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();
        //increase speed when the time goes on - tut4
        //higher the divided (starTime - initTime) slower the obstacles - tut4
        float speed = (float)(Math.sqrt(1 + (startTime - initTime)/2000.0)) * Constants.SCREEN_HEIGHT/10000.f;

        for(Obstacle ob : obstacles){
            ob.incrementY(speed * elapsedTime);
        }

        //generate new rectangle - tut3
        if(obstacles.get(obstacles.size() - 1).getRectangle().top >= Constants.SCREEN_HEIGHT){
            int xStart = (int)(Math.random() * (Constants.SCREEN_WIDTH - playerGap));
            obstacles.add(0, new Obstacle(obstacleHeight, color, xStart, obstacles.get(0).getRectangle().top
                    - obstacleHeight - obstacleGap, playerGap));
            obstacles.remove(obstacles.size() - 1);
            score++;
        }
    }

    public void draw(Canvas canvas){
        for (Obstacle ob : obstacles){
            ob.draw(canvas);
        }

        //Display score on screen for every passed obstacle - tut4
        Paint paint = new Paint();
        paint.setTextSize(100);
        paint.setColor(Color.GRAY);
        canvas.drawText("" + score, 50, 50 + paint.descent() - paint.ascent(), paint);
    }
}

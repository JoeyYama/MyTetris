package com.jyamanouchi.mytetris;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameSurface extends SurfaceView implements Runnable {

    volatile boolean playing = true, gameOver = false;
    private Thread gameThread = null;
    public static int margin=20;
    public static int width = Resources.getSystem().getDisplayMetrics().widthPixels-2*margin;
    public static int height = Resources.getSystem().getDisplayMetrics().heightPixels;
    public static int columns = 10;
    public static int squareSize = (int) width / columns;
    public static int rows = (int) height / squareSize;
    public static int top = height - rows*squareSize;
    private Paint paint, textPaint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    List<Shape> shapes = new ArrayList<Shape>();
    Shape shape, activeShape;
    List<Coordinate> coords;
    Random rand = new Random();
    int startLeftPosition;
    public int[][] board = new int[columns][rows];
    int numFullRows;
    ArrayList<Integer> fullRows;
    int score;
    int highScore[] = new int[4]; //the high Scores Holder
    SharedPreferences sharedPreferences; //Shared Prefernces to store the High Scores
    private GestureDetector gestureDetector;
    Context context;

    public GameSurface(Context context) {
        super(context);
        surfaceHolder = getHolder();
        paint = new Paint();
        textPaint = new Paint();

        //Initiallze board
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows - 1; j++) {
                board[i][j] = 0; //0 indicates that the space is open
            }
            board[i][rows - 1] = 1; //bottom of the screen 1 indicate do not pass
        }

        //Log.d("GameSurface", "constructor");
        activeShape = makeNewShape(rand.nextInt(7)); //randomly choose among 7 shapes
        //select start position based on object picked
        switch(activeShape.getPiece()) {
            case "Box":
                startLeftPosition = rand.nextInt(9);
                break;
            case "Line":
                startLeftPosition = rand.nextInt(10);
                break;
            case "T":
                startLeftPosition = rand.nextInt(8);
                break;
            case "Z":
                startLeftPosition = rand.nextInt(8);
                break;
            case "S":
                startLeftPosition = rand.nextInt(8);
                break;
            case "L":
                startLeftPosition = rand.nextInt(9);
                break;
            case "RL":
                startLeftPosition = rand.nextInt(9);
                break;
        }
        activeShape.setX(startLeftPosition);
        shapes.add(activeShape);
        score=0;

        sharedPreferences = context.getSharedPreferences("SHAR_PREF_NAME",Context.MODE_PRIVATE);

        //initializing the array high scores with the previous values
        highScore[0] = sharedPreferences.getInt("score1",0);
        highScore[1] = sharedPreferences.getInt("score2",0);
        highScore[2] = sharedPreferences.getInt("score3",0);
        highScore[3] = sharedPreferences.getInt("score4",0);

        this.context = context;
    }

    public int getColor(String piece) {
        switch (piece) {
            case "Box":
                return Color.argb(255, 245, 100, 10);
            case "Line":
                return Color.argb(255, 200, 75, 50);
            case "T":
                return Color.argb(255, 150, 186, 90);
            case "Z":
                return Color.argb(255, 120, 51, 120);
            case "S":
                return Color.argb(255, 90, 250, 150);
            case "L":
                return Color.argb(255, 60, 200, 200);
            case "RL":
                return Color.argb(255, 30, 10, 220);
            default:
                return Color.argb(255, 10, 51, 242);
        }
    }

    public Shape makeNewShape(int num) {
        switch (num) {
            case 0:
                return Shape.Box();
            case 1:
                return Shape.Line();
            case 2:
                return Shape.L();
            case 3:
                return Shape.RL();
            case 4:
                return Shape.Z();
            case 5:
                return Shape.S();
            case 6:
                return Shape.T();
            default:
                return Shape.Box();
        }
    }

    @Override
    public void run() {
        //Log.d("GameSurface", "run");
        while (playing) {
            update();
            draw();
            control();
        }
    }

    public void update() {
        //Log.d("GameSurface", "update");
        //DONE
        //See if active piece can move down
        //retrieve shapes and find the active one
        for (int i = 0; i < shapes.size(); i++) {
            if (shapes.get(i).getActive()) {
                activeShape = shapes.get(i);
                break;
            }
        }
        if (moveCheck()) {
            //If ok, move down
            moveDown();
            //DONE
            //determine if there is a full row
            for (int row = rows - 2; row >= 0; row--) { //start at the bottom row
                boolean bContinue = true;
                int multiRowCount = 0;
                while(bContinue) {
                    int columnCount = 0;
                    //cycle through each column and count the number of filled oolumns
                    for (int col = 0; col < columns; col++) {
                        if (board[col][row] == 1) {
                            columnCount++;
                        } else {
                            break; //if any column is open, leave for loop
                        }
                    }
                    //if there are any full row
                    //see if the number of filled columns equal the column width
                    if (columnCount == columns) { //all columns filled
                        //Log.d("GameSurface", "Full Row");
                        //increase the score
                        multiRowCount++;
                        if(multiRowCount> 1) {
                            score += multiRowCount*100;
                        } else {
                            score += 100;
                        }

                        //remove coordinates in a full row
                        //and drop the shape coordinates above a full row
                        for (int z = 0; z < shapes.size(); z++) {
                            shape = shapes.get(z);
                            if (shape.getActive() == false) { //only check inactive shapes
                                coords = shape.shapeCoordinates();
                                for (int a = 0; a < 4; a++) {
                                    if (coords.get(a).y == row) {
                                        coords.get(a).y = rows+5; //move full row off screen
                                    }
                                    if (coords.get(a).y < row) {  //move down any coordinate above a full row
                                        coords.get(a).y++;
                                    }
                                }
                            }
                        }
                        //for each full row, move rows above down
                        for (int y = row; y >= 0; y--) { //do not replace the bottom row
                            for (int x = 0; x < columns; x++) {
                                if(y > 0) {
                                    board[x][y] = board[x][y-1];
                                } else {
                                    board[x][0] = 0; //assign the top row as empty
                                }
                            }
                        }
                    } else { //no full row
                        bContinue = false;
                    }
                }  //end of while loop
            } //check next row


        } else {
            //DONE
            //If no, see if active piece is at the top
            if (atTop()) {
                //If yes, Game Over
                playing = false;
                gameOver = true;

                for(int i=0;i<4;i++){ //Assigning the scores to the highscore integer array
                    if(highScore[i]<score){
                        int j= 3;
                        while(j>i) {
                            highScore[j] = highScore[j - 1];
                            j--;
                        }
                            //final int finalI = i;
                            highScore[i] = score;
                            break;

                    }
                }

                //storing the scores through shared Preferences
                SharedPreferences.Editor e = sharedPreferences.edit();
                for(int i=0;i<4;i++){
                    int j = i+1;
                    e.putInt("score"+j,highScore[i]);
                }
                e.apply();
            } else {
                //DONE
                //If not at the top, but against an obstruction
                // deactiveate the active piece
                activeShape.setActive(false);
                // update the filled positions on the board
                coords = activeShape.shapeCoordinates();
                for (int i = 0; i < 4; i++) {
                    if (coords.get(i).x >= 0 && coords.get(i).y >= 0) {
                        board[coords.get(i).x][coords.get(i).y] = 1; //indicate that the position is filled
                    }
                }
                // and start a new piece
                activeShape = makeNewShape(rand.nextInt(7)); //randomly choose among 7 shapes
                startLeftPosition = rand.nextInt(7); //randomly select starting position and keep in frame
                activeShape.setX(startLeftPosition);
                shapes.add(activeShape);
                //Add to score with each new shape
                score +=10;
            }
        }
    }

    public void draw() {
        Log.d("GameSurface", "draw");
        if (surfaceHolder.getSurface().isValid()) {
            //locking the canvas
            canvas = surfaceHolder.lockCanvas();

            //drawing a background color for canvas
            canvas.drawColor(Color.WHITE);

            //Draw border lines
            paint.setColor(Color.BLACK);
            canvas.drawLine(margin, top, margin, height, paint);
            canvas.drawLine(width + margin, top, width + margin, height, paint);
            canvas.drawLine(margin, height, width - squareSize, height, paint);

            //Draw shape
            //DONE
            for (int i = 0; i < shapes.size(); i++) { //cycle through each shape created
                shape = shapes.get(i);
                String piece = shape.getPiece();
                int color = getColor(piece);
                paint.setColor(color);
                coords = shape.shapeCoordinates();
                for (int j = 0; j < 4; j++) {  //get the four coordinates per shape
                    float pos1 = coords.get(j).x * squareSize+margin;
                    float pos2 = coords.get(j).y * squareSize+top;
                    float pos3 = (coords.get(j).x + 1) * squareSize+margin;
                    float pos4 = (coords.get(j).y + 1) * squareSize+top;
                    if(pos2 >= rows) {
                        canvas.drawRect(pos1, pos2, pos3, pos4, paint);
                    }
                }
            }
            if (gameOver) {
                textPaint.setColor(Color.BLACK);
                textPaint.setTextSize(100);
                textPaint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText("GAME OVER", width / 2, height / 2, textPaint);
            }

            //Place score on the lower left
            textPaint.setColor(Color.BLACK);
            textPaint.setTextSize(80);
            textPaint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText("Score: " + String.valueOf(score), width/2, 300, textPaint);

            //Unlocking the canvas
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void control() {
        Log.d("GameSurface", "control");
        try {
            gameThread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
        }
    }

    public void resume() {
        Log.d("GameSurface", "resume");
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public boolean moveCheck() {
        //DONE
        //get the active shape coordinates
        coords = activeShape.shapeCoordinates();
        //check if board position is filled below shape
        for (int i = 0; i < 4; i++) {
            int xPosition = (int) coords.get(i).x;
            int yPosition = (int) coords.get(i).y + 1; //checking the position below
            //see if the position below is filled
            if (xPosition >= 0 && xPosition < 10 && yPosition >= 0) {
                if (board[xPosition][yPosition] == 1) {
                    return false;
                }
            }
        }
        return true; //none of the board position below are taken
    }

    public void moveDown() {
        activeShape.movedown();
    }

    public void moveLeft() {

        activeShape.moveLeft(board);
    }

    public void moveRight() {
        activeShape.moveRight(columns, board);
    }

    public void hardLeft() { activeShape.hardLeft(board);}

    public void hardRight() { activeShape.hardRight(columns, board);}

    public void hardDown() { activeShape.hardDown(board);}

    public void rotate() {
        activeShape.rotate(board);
    }

    public boolean atTop() {
        //DONE
        //get the shape coordinates
        coords = activeShape.shapeCoordinates();
        //see if the coordinates are at the top
        for (int i = 0; i < coords.size(); i++) {
            if (coords.get(i).y <= 0) {
                return true;
            }
        }
        return false;
    }

    View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            // pass the events to the gesture detector
            // a return value of true means the detector is handling it
            // a return value of false means the detector didn't
            // recognize the event
            return gestureDetector.onTouchEvent(event);

        }
    };
}

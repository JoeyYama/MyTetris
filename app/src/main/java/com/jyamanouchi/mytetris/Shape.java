package com.jyamanouchi.mytetris;

import java.util.ArrayList;
import java.util.List;

public class Shape {
    private Coordinate coordA, coordB, coordC, coordD;
    private String piece;
    private Boolean active;
    GameSurface gameSurface;

    public Shape(Coordinate coordA, Coordinate coordB, Coordinate coordC, Coordinate coordD, String piece, Boolean active) {
        this.coordA = coordA;
        this.coordB = coordB;
        this.coordC = coordC;
        this.coordD = coordD;
        this.piece = piece;
        this.active = active;
    }

    public static Shape Box() {
        return new Shape(
                new Coordinate(0, 0),
                new Coordinate(0, -1),
                new Coordinate(1, 0),
                new Coordinate(1, -1),
                "Box", true);
    }

    public static Shape Line() {
        return new Shape(
                new Coordinate(0, -3),
                new Coordinate(0, -2),
                new Coordinate(0, -1),
                new Coordinate(0, 0),
                "Line", true);
    }

    public static Shape L() {
        return new Shape(
                new Coordinate(0, -2),
                new Coordinate(0, -1),
                new Coordinate(0, 0),
                new Coordinate(1, 0),
                "L", true);
    }

    public static Shape RL() {
        return new Shape(
                new Coordinate(1, -2),
                new Coordinate(1, -1),
                new Coordinate(1, 0),
                new Coordinate(0, 0),
                "RL", true);
    }

    public static Shape Z() {
        return new Shape(
                new Coordinate(0, -1),
                new Coordinate(1, -1),
                new Coordinate(1, 0),
                new Coordinate(2, 0),
                "Z", true);
    }

    public static Shape S() {
        return new Shape(
                new Coordinate(0, 0),
                new Coordinate(1, 0),
                new Coordinate(1, -1),
                new Coordinate(2, -1),
                "S", true);
    }

    public static Shape T() {
        return new Shape(
                new Coordinate(0, -1),
                new Coordinate(1, -1),
                new Coordinate(2, -1),
                new Coordinate(1, 0),
                "T", true);
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean getActive() {
        return this.active;
    }

    //used for the starting position
    public void setX(int position) {
        this.coordA.x += position;
        this.coordB.x += position;
        this.coordC.x += position;
        this.coordD.x += position;
    }

    public void setY() {
        this.coordA.y++;
        this.coordB.y++;
        this.coordC.y++;
        this.coordD.y++;
    }

    public String getPiece() {
        return this.piece;
    }

    public List<Coordinate> shapeCoordinates() {
        List<Coordinate> coords = new ArrayList<Coordinate>();
        coords.add(coordA);
        coords.add(coordB);
        coords.add(coordC);
        coords.add(coordD);
        return coords;
    }

    public void movedown() {
        coordA.y++;
        coordB.y++;
        coordC.y++;
        coordD.y++;
    }

    public void moveLeft(int[][] board) {
        if (coordA.y > 0 && coordB.y > 0 && coordC.y > 0 && coordD.y > 0) {
            if ((coordA.x > 0 && board[coordA.x - 1][coordA.y] != 1) &&
                    (coordB.x > 0 && board[coordB.x - 1][coordB.y] != 1) &&
                    (coordC.x > 0 && board[coordC.x - 1][coordC.y] != 1) &&
                    (coordD.x > 0) && board[coordD.x - 1][coordD.y] != 1) {
                coordA.x--;
                coordB.x--;
                coordC.x--;
                coordD.x--;
            }
        }
    }

    public void moveRight(int col, int[][] board) {
        if (coordA.y > 0 && coordB.y > 0 && coordC.y > 0 && coordD.y > 0) {
            if ((coordA.x < col - 1 && board[coordA.x + 1][coordA.y] != 1) &&
                    (coordB.x < col - 1 && board[coordB.x + 1][coordB.y] != 1) &&
                    (coordC.x < col - 1 && board[coordC.x + 1][coordC.y] != 1) &&
                    (coordD.x < col - 1 && board[coordD.x + 1][coordD.y] != 1)) {
                coordA.x++;
                coordB.x++;
                coordC.x++;
                coordD.x++;
            }
        }
    }

    public void hardLeft(int[][] board) {
        if (coordA.y > 0 && coordB.y > 0 && coordC.y > 0 && coordD.y > 0) {
            while ((coordA.x > 0 && board[coordA.x - 1][coordA.y] != 1) &&
                    (coordB.x > 0 && board[coordB.x - 1][coordB.y] != 1) &&
                    (coordC.x > 0 && board[coordC.x - 1][coordC.y] != 1) &&
                    (coordD.x > 0) && board[coordD.x - 1][coordD.y] != 1) {
                coordA.x--;
                coordB.x--;
                coordC.x--;
                coordD.x--;
            }
        }
    }

    public void hardRight(int col, int[][] board) {
        if (coordA.y > 0 && coordB.y > 0 && coordC.y > 0 && coordD.y > 0) {
            while ((coordA.x < col - 1 && board[coordA.x + 1][coordA.y] != 1) &&
                    (coordB.x < col - 1 && board[coordB.x + 1][coordB.y] != 1) &&
                    (coordC.x < col - 1 && board[coordC.x + 1][coordC.y] != 1) &&
                    (coordD.x < col - 1 && board[coordD.x + 1][coordD.y] != 1)) {
                coordA.x++;
                coordB.x++;
                coordC.x++;
                coordD.x++;
            }
        }
    }

    public void hardDown(int[][] board) {
        if (coordA.y > 0 && coordB.y > 0 && coordC.y > 0 && coordD.y > 0) {
            while (board[coordA.x][coordA.y + 1] != 1 &&
                    board[coordB.x][coordB.y + 1] != 1 &&
                    board[coordC.x][coordC.y + 1] != 1 &&
                    board[coordD.x][coordD.y + 1] != 1) {
                coordA.y++;
                coordB.y++;
                coordC.y++;
                coordD.y++;
            }
        }
    }

    public void rotate(int[][] board) {
        //TODO
        int xPos, yPos;
        //rotate around the second coordinate
        xPos = coordB.x;
        yPos = coordB.y;
        switch (piece) {
            case "Line":
                if (coordA.x == coordB.x && coordB.x > 0 && coordB.x < gameSurface.columns - 2) {
                    //Line is vertical
                    // and is not against the border - cannot rotate if along the border
                    //Check the final rotation position will not intersect an existing shape
                    if (board[xPos - 1][yPos] != 1 && board[xPos + 1][yPos] != 1 && board[xPos + 2][yPos] != 1) {
                        //make line horizontal
                        coordA.x = xPos - 1;
                        coordA.y = yPos;
                        coordC.x = xPos + 1;
                        coordC.y = yPos;
                        coordD.x = xPos + 2;
                        coordD.y = yPos;
                    }
                } else {
                    //Line is horizontal
                    //Make line vertical
                    if(board[xPos][yPos-1] != 1 && board[xPos][yPos+1] != 1 && board[xPos][yPos+2] != 1) {
                        //only rotate if thre is no shape below
                        coordA.x = xPos;
                        coordA.y = yPos - 1;
                        coordC.x = xPos;
                        coordC.y = yPos + 1;
                        coordD.x = xPos;
                        coordD.y = yPos + 2;
                    }
                }
                break;
            case "L":
                xPos = coordB.x;
                yPos = coordB.y;
                if (coordA.x == coordB.x) {
                    //Line is vertical
                    if (coordD.x > coordC.x) {
                        //side line on bottom
                        //move side line to right side if not against the left border
                        if (coordB.x > 0) {
                            coordA.x = xPos - 1;
                            coordA.y = yPos;
                            coordC.x = xPos + 1;
                            coordC.y = yPos;
                            coordD.x = xPos + 1;
                            coordD.y = yPos - 1;
                        }
                    } else if (coordB.x < gameSurface.columns - 1) {
                        //side line on top
                        //move side line to left if not against the right border
                        coordA.x = xPos + 1;
                        coordA.y = yPos;
                        coordC.x = xPos - 1;
                        coordC.y = yPos;
                        coordD.x = xPos - 1;
                        coordD.y = yPos + 1;
                    }
                } else {
                    if (coordD.x > coordA.x) {
                        //side line on right
                        //move side line to top
                        coordA.x = xPos;
                        coordA.y = yPos + 1;
                        coordC.x = xPos;
                        coordC.y = yPos - 1;
                        coordD.x = xPos - 1;
                        coordD.y = yPos - 1;
                    } else {
                        //side line on left
                        //move side line to bottom
                        coordA.x = xPos;
                        coordA.y = yPos - 1;
                        coordC.x = xPos;
                        coordC.y = yPos + 1;
                        coordD.x = xPos + 1;
                        coordD.y = yPos + 1;
                    }
                }
                break;
            case "RL":
                if (coordD.x < coordC.x) {
                    //points left
                    if (coordB.x < gameSurface.columns - 1) {
                        //move down if not against the right border
                        coordA.x = xPos - 1;
                        coordA.y = yPos;
                        coordC.x = xPos + 1;
                        coordC.y = yPos;
                        coordD.x = xPos + 1;
                        coordD.y = yPos + 1;
                    }
                } else if (coordD.y > coordC.y) {
                    //points down
                    //move to right
                    coordA.x = xPos;
                    coordA.y = yPos + 1;
                    coordC.x = xPos;
                    coordC.y = yPos - 1;
                    coordD.x = xPos + 1;
                    coordD.y = yPos - 1;
                } else if (coordD.x > coordC.x) {
                    //points right
                    if (coordB.x > 0) {
                        //move to up if not against the left border
                        coordA.x = xPos + 1;
                        coordA.y = yPos;
                        coordC.x = xPos - 1;
                        coordC.y = yPos;
                        coordD.x = xPos - 1;
                        coordD.y = yPos - 1;
                    }
                } else {
                    //points up
                    //move to left
                    coordA.x = xPos;
                    coordA.y = yPos - 1;
                    coordC.x = xPos;
                    coordC.y = yPos + 1;
                    coordD.x = xPos - 1;
                    coordD.y = yPos + 1;
                }
                break;
            case "T":
                if (coordD.y > coordB.y) {
                    //T points down
                    //point T to right
                    coordA.x = xPos;
                    coordA.y = yPos + 1;
                    coordC.x = xPos;
                    coordC.y = yPos - 1;
                    coordD.x = xPos + 1;
                    coordD.y = yPos;
                } else if (coordD.x > coordB.x && coordB.x > 0) {
                    //T points right
                    // point T to top
                    coordA.x = xPos + 1;
                    coordA.y = yPos;
                    coordC.x = xPos - 1;
                    coordC.y = yPos;
                    coordD.x = xPos;
                    coordD.y = yPos - 1;
                } else if (coordD.y < coordB.y) {
                    //T points top
                    // point T to left
                    coordA.x = xPos;
                    coordA.y = yPos - 1;
                    coordC.x = xPos;
                    coordC.y = yPos + 1;
                    coordD.x = xPos - 1;
                    coordD.y = yPos;
                } else if (coordD.x < coordB.x && coordB.x < gameSurface.columns - 1) {
                    //T points left
                    //point T to the bottom
                    coordA.x = xPos - 1;
                    coordA.y = yPos;
                    coordC.x = xPos + 1;
                    coordC.y = yPos;
                    coordD.x = xPos;
                    coordD.y = yPos + 1;
                }
                break;
            case "Z":
                //Unlike the other shapes, pivoting around coordinate C
                xPos = coordC.x;
                yPos = coordC.y;
                if (coordA.x < coordB.x) {
                    //upright
                    //move sideways
                    coordA.x = xPos - 1;
                    coordA.y = yPos + 1;
                    coordB.x = xPos - 1;
                    coordB.y = yPos;
                    coordD.x = xPos;
                    coordD.y = yPos - 1;
                } else if (coordB.x < coordC.x) {
                    //sideways
                    //moave upright if not against the right border
                    if (coordC.x < gameSurface.columns - 1) {
                        coordA.x = xPos + 1;
                        coordA.y = yPos + 1;
                        coordB.x = xPos;
                        coordB.y = yPos + 1;
                        coordD.x = xPos - 1;
                        coordD.y = yPos;
                    }
                } else if (coordA.x > coordB.x) {
                    coordA.x = xPos + 1;
                    coordA.y = yPos - 1;
                    coordB.x = xPos + 1;
                    coordB.y = yPos;
                    coordD.x = xPos;
                    coordD.y = yPos + 1;
                } else if (coordC.x > 0) {
                    coordA.x = xPos - 1;
                    coordA.y = yPos - 1;
                    coordB.x = xPos;
                    coordB.y = yPos - 1;
                    coordD.x = xPos + 1;
                    coordD.y = yPos;
                }
                break;
            case "S":
                if (coordC.x < coordD.x) {
                    //upright
                    //move sideways
                    coordA.x = xPos;
                    coordA.y = yPos + 1;
                    coordC.x = xPos - 1;
                    coordC.y = yPos;
                    coordD.x = xPos - 1;
                    coordD.y = yPos - 1;
                } else if (coordD.y < coordC.y) {
                    //sideways
                    //moave upright if not against the right border
                    if (coordB.x < gameSurface.columns - 1) {
                        coordA.x = xPos + 1;
                        coordA.y = yPos;
                        coordC.x = xPos;
                        coordC.y = yPos + 1;
                        coordD.x = xPos - 1;
                        coordD.y = yPos + 1;
                    }
                } else if (coordC.x > coordD.x) {
                    //upside down
                    //flip on side
                    coordA.x = xPos;
                    coordA.y = yPos - 1;
                    coordC.x = xPos + 1;
                    coordC.y = yPos;
                    coordD.x = xPos + 1;
                    coordD.y = yPos + 1;
                } else if (coordD.y > coordC.y && coordB.x > 0) {
                    coordA.x = xPos - 1;
                    coordA.y = yPos;
                    coordC.x = xPos;
                    coordC.y = yPos - 1;
                    coordD.x = xPos + 1;
                    coordD.y = yPos - 1;
                }
                break;
        }
    }
}

package a8;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class FramePuzzleWidget extends JPanel implements KeyListener, MouseListener{

    //Private variables: 2 2D arrays, one serves as undisturbed original for references, the other for manipulation and display
    private JPanel tileContainer;
    private ObservablePicture solidTile;
    private Picture originalPicture;
    private PictureView[][] pictureTileArray;
    private PictureView[][] pictureTileArrayScreen;
    private int tileWidth;
    private int tileHeight;
    private int solidTileX;
    private int solidTileY;


    //Constructor
    public FramePuzzleWidget(Picture picture) {
        setLayout(new BorderLayout());

        //5x5 Grid to hold picture tiles
        tileContainer = new JPanel();
        tileContainer.setLayout(new GridLayout(5,5));

        //Initialization of different variables
        pictureTileArray = new PictureView[5][5];
        pictureTileArrayScreen = new PictureView[5][5];
        originalPicture = picture;
        tileWidth = picture.getWidth() / 5;
        tileHeight = picture.getHeight() / 5;

        //Properties for solid tile
        Picture tempSolidPicture = new PictureImpl(tileWidth, tileHeight);
        Pixel whitePicture = new ColorPixel(1.0,1.0,1.0);
        for (int col = 0; col < tileWidth; col++) {
            for (int row = 0; row < tileHeight; row++) {
                tempSolidPicture.setPixel(col,row,whitePicture);
            }
        }
        solidTile = tempSolidPicture.createObservable();
        solidTileX = 4;
        solidTileY = 4;

        //Heart of Application, initializes grid and then displays
        createTileArray();
        pictureTileArrayScreen[4][4].setPicture(solidTile);
        groupTileArray();
        add(tileContainer, BorderLayout.CENTER);
    }

    //Splits picture into 5x5 grid, calls "createTile" method, adds created tile to 2D array
    public void createTileArray() {
        PictureView tempTile;
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                tempTile = createTile(col,row);
                pictureTileArray[col][row] = tempTile;
                pictureTileArrayScreen [col][row] = tempTile;
                pictureTileArrayScreen[col][row].addKeyListener(this);
                pictureTileArrayScreen[col][row].addMouseListener(this);
                pictureTileArrayScreen[col][row].setFocusable(true);
            }
        }
    }

    //Creates a new PictureView object (tile) from a specific location
    public PictureView createTile(int gridColumn, int gridRow) {

        //Local variables within method
        int xOffset = (tileWidth * gridColumn);
        int yOffset = (tileHeight * gridRow);
        Picture tilePicture = new PictureImpl(tileWidth, tileHeight);
        PictureView tempTile;

        //Loops through the tile, copies Pixels from orignalPicture, returns the tile as PictureView object
        for (int col = 0; col < tileWidth; col++) {
            for (int row = 0; row < tileHeight; row++) {
                tilePicture.setPixel(col, row, originalPicture.getPixel(xOffset + col, yOffset + row));
            }
        }
        tempTile = new PictureView(tilePicture.createObservable());
        return tempTile;
    }

    //Displays TileArray
    public void groupTileArray() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                tileContainer.add(pictureTileArrayScreen[j][i]);
            }
        }
    }

    //Move Commands:
    ObservablePicture pictureHolder;

    public void moveUp() {

    }

    public void moveDown() {

    }

    public void moveLeft() {

    }

    public void moveRight() {

    }


    //KeyListener Methods

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        //Determines which arrow key is pressed, Swaps target and solid pictures
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            if (solidTileY > 0) {
                pictureTileArrayScreen[solidTileX][solidTileY].setPicture(pictureTileArrayScreen[solidTileX][solidTileY - 1].getPicture());
                pictureTileArrayScreen[solidTileX][solidTileY - 1].setPicture(solidTile);
                solidTileY--;
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            if (solidTileY < 4) {
                pictureTileArrayScreen[solidTileX][solidTileY].setPicture(pictureTileArrayScreen[solidTileX][solidTileY + 1].getPicture());
                pictureTileArrayScreen[solidTileX][solidTileY + 1].setPicture(solidTile);
                solidTileY++;
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (solidTileX > 0) {
                pictureTileArrayScreen[solidTileX][solidTileY].setPicture(pictureTileArrayScreen[solidTileX - 1][solidTileY].getPicture());
                pictureTileArrayScreen[solidTileX - 1][solidTileY].setPicture(solidTile);
                solidTileX--;
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (solidTileX < 4) {
                pictureTileArrayScreen[solidTileX][solidTileY].setPicture(pictureTileArrayScreen[solidTileX + 1][solidTileY].getPicture());
                pictureTileArrayScreen[solidTileX + 1][solidTileY].setPicture(solidTile);
                solidTileX++;
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    //MouseListener Methods

    @Override
    public void mouseClicked(MouseEvent e) {
        int clickedX = e.getXOnScreen() / tileWidth;
        int clickedY = e.getYOnScreen() / tileHeight;

        //Large if/for loop to set pictures within the array with respect to distance between the solid and target tiles.
        if (clickedY == solidTileY) {   //Target and Solid on the same row
            if (clickedX < solidTileX) {    //Target is to the left of solid tile
                for (int diff = 0; diff < (solidTileX - clickedX); diff++) {
                    pictureTileArrayScreen[solidTileX - diff][clickedY].setPicture(pictureTileArrayScreen[solidTileX - diff - 1][clickedY].getPicture());
                }
            } else if (clickedX > solidTileX) { //Target is to the right of solid tile
                for (int diff = 0; diff < (clickedX - solidTileX); diff++) {
                    pictureTileArrayScreen[solidTileX + diff][clickedY].setPicture(pictureTileArrayScreen[solidTileX + diff + 1][clickedY].getPicture());
                }
            }
            solidTileX = clickedX;
            pictureTileArrayScreen[clickedX][clickedY].setPicture(solidTile);
        } else if (clickedX == solidTileX) {   //Target and Solid on the same column
            if (clickedY < solidTileY) {
                for (int diff = 0; diff < (solidTileY - clickedY); diff++) {
                    pictureTileArrayScreen[clickedX][solidTileY - diff].setPicture(pictureTileArrayScreen[clickedX][solidTileY - diff - 1].getPicture());
                }
            } else if (clickedY > solidTileY) {
                for (int diff = 0; diff < (clickedY - solidTileY); diff++) {
                    pictureTileArrayScreen[clickedX][solidTileY + diff].setPicture(pictureTileArrayScreen[clickedX][solidTileY + diff + 1].getPicture());
                }
            }
            solidTileY = clickedY;
            pictureTileArrayScreen[clickedX][clickedY].setPicture(solidTile);
        }


    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}

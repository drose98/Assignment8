package a8;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class FramePuzzleWidget extends JPanel implements KeyListener, MouseListener{

    //Private variables: 2 2D arrays, one serves as undisturbed original for references, the other for manipulation and display
    private Picture solidBlock;
    private Picture originalPicture;
    private PictureView[][] pictureTileArray;
    private PictureView[][] pictureTileArrayScreen;
    private int tileWidth;
    private int tileHeight;


    //Constructor
    public FramePuzzleWidget(Picture picture) {

        //5x5 Grid to hold picture tiles
        setLayout(new GridLayout(5,5));

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

        createTileArray();
        displayTileArray();
    }

    //Splits picture into 5x5 grid, calls "createTile" method, adds created tile to 2D array
    public void createTileArray() {
        PictureView tempTile;
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                tempTile = createTile(col,row);
                pictureTileArray[col][row] = tempTile;
                pictureTileArrayScreen [col][row] = tempTile;
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
    public void displayTileArray() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                add(pictureTileArrayScreen[j][i]);
            }
        }
    }


    //KeyListener Methods
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    //MouseListener Methods
    @Override
    public void mouseClicked(MouseEvent e) {

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

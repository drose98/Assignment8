package a8;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class PixelInspectorWidget extends JPanel implements MouseListener{

    //Declare two subpanels and an observable picture
    private PictureView picView;
    private JPanel picturePanel;
    private JPanel infoGrid;

    //Declare box component values
    private Pixel clickedPixel;
    private int xCoord, yCoord;
    private double greenVal, redVal, blueVal, brightVal;


/*  Constructor: Takes in a picture object
    Creates Picture panel and info panel    */
    public PixelInspectorWidget(Picture picture) {

        //Set layout for "Master Panel"
        setLayout(new BorderLayout());

        //Initialize the two "Sub Panels" and the observable picture
        picView = new PictureView(picture.createObservable());
        picturePanel = new JPanel();
        infoGrid = new JPanel();

        //Border layout to house observable picture inside the picturePanel sub-panel
        picturePanel.setLayout(new BorderLayout());

        //Vertical layout with 5 boxes for the infoGrid sub-panel
        infoGrid.setLayout(new GridLayout(6, 1));
        infoGrid.setPreferredSize(new Dimension(100, 400));

        //Add picView within the picturePanel sub-panel
        picView.addMouseListener(this);
        picturePanel.add(picView, BorderLayout.CENTER);

/*        Note: picturePanel is now set-up with the picture and mouseListener
          Still need to set-up infoBox information and add subpanels to master panel*/

        buildInfoPanel();

        //adds Sub-Panels to Master-Panel
        add(picturePanel, BorderLayout.CENTER);
        add(infoGrid, BorderLayout.WEST);

    }

    public void buildInfoPanel() {
        //declares and initializes values within JLabels
        JLabel xBox = new JLabel("X: " + xCoord);
        JLabel yBox = new JLabel("Y: " + yCoord);
        JLabel redBox = new JLabel("Red: " + redVal);
        JLabel greenBox = new JLabel("Green: " + greenVal);
        JLabel blueBox = new JLabel("Blue: " + blueVal);
        JLabel brightBox = new JLabel("Brightness: " + brightVal);

        //Add boxes to infoGrid vertically
        infoGrid.add(xBox);
        infoGrid.add(yBox);
        infoGrid.add(redBox);
        infoGrid.add(greenBox);
        infoGrid.add(blueBox);
        infoGrid.add(brightBox);
    }

    //removes subpanel from main panel, updates subpanel, adds subpanel back to main panel
    public void updateInfoPanel() {
        this.remove(infoGrid);
        infoGrid.removeAll();
        buildInfoPanel();
        add(infoGrid);
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        //sets clicked pixel to temporary pixel variable "clickedPixel"
        clickedPixel = picView.getPicture().getPixel(e.getX(), e.getY());

        //updates object information values based on clickedPixel
        xCoord = e.getX();
        yCoord = e.getY();
        redVal = (Math.round(clickedPixel.getRed() * 100.00)) / 100.00;
        greenVal = Math.round(clickedPixel.getGreen() * 100.00) / 100.00;
        blueVal = Math.round(clickedPixel.getBlue() * 100.00) / 100.00;
        brightVal = Math.round(clickedPixel.getRed() * 100.00) / 100.00;

        //updates subpanel and then updates main panel
        updateInfoPanel();
        updateUI();
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

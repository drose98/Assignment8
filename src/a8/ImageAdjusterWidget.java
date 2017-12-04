package a8;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class ImageAdjusterWidget extends JPanel implements ChangeListener {

    //Declares two main subpanels and two smaller subpanels
    private PictureView picView;
    private JPanel picturePanel;
    private JPanel sliderPanel;
    private JPanel sliders;
    private JPanel sliderNames;

    //Declares sliders
    JSlider blur;
    JSlider saturation;
    JSlider brightness;

    //Declares temporary Picture which is used to hold filter effects
    private Picture tempPic;
    private Picture originalPic;


    public ImageAdjusterWidget(Picture picture) {
        setLayout(new BorderLayout());
        originalPic = picture;

        //Initializes the subpanels
        picView = new PictureView(originalPic.createObservable());
        picturePanel = new JPanel();
        sliderPanel = new JPanel();


        //Establishes layouts for subpanels
        picturePanel.setLayout(new BorderLayout());
        sliderPanel.setLayout(new BorderLayout());
        sliderPanel.setPreferredSize(new Dimension(400, 150));

        picturePanel.add(picView, BorderLayout.CENTER);


        //Initializes sliders
        blur = new JSlider(0,5,0);
        saturation = new JSlider(-100, 100,0);
        brightness = new JSlider(-100, 100,0);


        //Sets Major and Minor Ticks for sliders
        blur.setMajorTickSpacing(1);
        saturation.setMajorTickSpacing(25);
        saturation.setMinorTickSpacing(1);
        brightness.setMajorTickSpacing(25);
        brightness.setMinorTickSpacing(1);


        //Visible labels and snap to ticks
        blur.setSnapToTicks(true);
        saturation.setSnapToTicks(true);
        brightness.setSnapToTicks(true);
        blur.setPaintLabels(true);
        saturation.setPaintLabels(true);
        brightness.setPaintLabels(true);


        //Creates titles for sliders
        sliderNames = new JPanel();
        sliderNames.setLayout(new GridLayout(3,1));

        JLabel blurLabel = new JLabel("Blur: ");
        JLabel saturationLabel = new JLabel("Saturation: ");
        JLabel brightnessLabel = new JLabel("Brightness: ");


        //Layout management within sliderPanel
        sliders = new JPanel();
        sliders.setLayout(new GridLayout(3,1));

        sliders.add(blur);
        sliders.add(saturation);
        sliders.add(brightness);
        sliderNames.add(blurLabel);
        sliderNames.add(saturationLabel);
        sliderNames.add(brightnessLabel);

        sliderPanel.add(sliders, BorderLayout.CENTER);
        sliderPanel.add(sliderNames, BorderLayout.WEST);


        //Allows changes to sliders to be observed
        blur.addChangeListener(this);
        saturation.addChangeListener(this);
        brightness.addChangeListener(this);


        //Adds Sub-Panels to Master Panel
        add(picturePanel, BorderLayout.CENTER);
        add(sliderPanel, BorderLayout.SOUTH);


        initializeClone();

    }

    @Override
    public void stateChanged(ChangeEvent e) {
        //Makes sure sliders are done moving
        if (!blur.getValueIsAdjusting() && !saturation.getValueIsAdjusting() && !brightness.getValueIsAdjusting()) {

            //Blur slider is changed
            if (e.getSource() == blur) {
                blurPicture(blur.getValue());
                picView.setPicture(tempPic.createObservable());
            }

            if (e.getSource() == brightness) {
                brightenPicture(brightness.getValue());
                picView.setPicture(tempPic.createObservable());
            }
        }
    }


    //Assigns primitive data to tempPic instead of reference pointers
    public void initializeClone() {
        tempPic = new PictureImpl(originalPic.getWidth(), originalPic.getHeight());
        for (int col = 0; col < tempPic.getWidth(); col++ ) {
            for (int row = 0; row < tempPic.getHeight(); row++) {
                Pixel tempPixel = new ColorPixel(originalPic.getPixel(col,row).getRed(),
                        originalPic.getPixel(col,row).getGreen(),
                        originalPic.getPixel(col,row).getBlue());

                tempPic.setPixel(col,row,tempPixel);
            }
        }

    }

    public void blurPicture(int amount) {
        double redSum = 0;
        double greenSum = 0;
        double blueSum = 0;
        double count = 0.0;

        //Outer loop
        for (int col = 0; col < tempPic.getWidth(); col++ ) {
            for (int row = 0; row < tempPic.getHeight(); row++) {

                //Inner loop to rotate through surrounding Pixels
                for (int c = col - amount; c <= col + amount; c++) {
                    for (int r = row - amount; r <= row + amount; r++) {
                        //Checks if values are out of bounds
                        if (c >= 0 && r >=0 && c < tempPic.getWidth() && r < tempPic.getHeight()) {
                            redSum += originalPic.getPixel(c, r).getRed();
                            greenSum += originalPic.getPixel(c, r).getGreen();
                            blueSum += originalPic.getPixel(c, r).getBlue();
                            count++;
                        }
                    }
                }
                //Changes pixel on tempPic
                Pixel averagePixel = new ColorPixel((redSum/count), (greenSum/count), (blueSum/count));
                tempPic.setPixel(col, row, averagePixel);
                //Resets sums for next blurred pixel
                redSum = 0;
                greenSum = 0;
                blueSum = 0;
                count = 0;
            }
        }
    }

    public void brightenPicture(int amount) {

        double tempRed, tempGreen, tempBlue;

        for (int col = 0; col < tempPic.getWidth(); col++ ) {
            for (int row = 0; row < tempPic.getHeight(); row++) {

                if (amount > 0) {
                    tempRed = tempPic.getPixel(col,row).getRed() + ((1- tempPic.getPixel(col,row).getRed()) * (amount / 100.00));
                    tempGreen = tempPic.getPixel(col,row).getGreen() + ((1- tempPic.getPixel(col,row).getGreen()) * (amount / 100.00));
                    tempBlue = tempPic.getPixel(col,row).getBlue() + ((1- tempPic.getPixel(col,row).getBlue()) * (amount / 100.00));
                } else if (amount < 0) {
                    tempRed = tempPic.getPixel(col,row).getRed() + (tempPic.getPixel(col,row).getRed() * (amount / 100.00));
                    tempGreen = tempPic.getPixel(col,row).getGreen() + (tempPic.getPixel(col,row).getGreen() * (amount / 100.00));
                    tempBlue = tempPic.getPixel(col,row).getBlue() + (tempPic.getPixel(col,row).getBlue() * (amount / 100.00));
                } else {
                    tempRed = tempPic.getPixel(col,row).getRed();
                    tempGreen = tempPic.getPixel(col,row).getGreen();
                    tempBlue = tempPic.getPixel(col,row).getBlue();
                }

                Pixel tempPixel = new ColorPixel(tempRed, tempGreen, tempBlue);
                tempPic.setPixel(col, row, tempPixel);
            }
        }

    }

}

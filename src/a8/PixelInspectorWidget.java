package a8;

import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import java.awt.event.MouseListener;

public class PixelInspectorWidget extends SimplePictureViewWidget implements MouseListener {

    //Constructor, takes in picture and title and calls parent constructor
    public PixelInspectorWidget(Picture picture, String title) {
        super(picture, title);
        setLayout(new BoxLayout(6));
    }
}

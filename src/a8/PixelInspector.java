package a8;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

//Application that displays a picture along with information about it
public class PixelInspector {
    public static void main(String[] args) throws IOException {
        Picture p = A8Helper.readFromURL("http://www.cs.unc.edu/~kmp/kmp-in-namibia.jpg");
        PixelInspectorWidget inspectorWidget = new PixelInspectorWidget(p);

        JFrame main_frame = new JFrame();
        main_frame.setTitle("PixelInspector");
        main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel top_panel = new JPanel();
        top_panel.setLayout(new BorderLayout());
        top_panel.add(inspectorWidget, BorderLayout.CENTER);
        main_frame.setContentPane(top_panel);

        main_frame.pack();
        main_frame.setVisible(true);

    }
}

import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;


public class MyTextArea extends JTextArea {
    private Image img;

    public MyTextArea(String location, int width, int height) {
        try {
            img = ImageIO.read(ClassLoader.getSystemResource(location)).getScaledInstance(width, height,
                    Image.SCALE_DEFAULT);
        } catch (IOException e) {
            System.out.println(e.toString());
            return;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, this);
        super.paintComponent(g);
    }
}

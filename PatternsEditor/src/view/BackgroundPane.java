package view;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class BackgroundPane extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private Image image;

	public BackgroundPane(String filePath) {
		try {
			image = ImageIO.read(Files.newInputStream(Paths.get(filePath)));
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }
	
	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
	

}

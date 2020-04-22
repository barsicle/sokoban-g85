package gui;

import java.util.HashMap;
import java.util.Map;
import java.io.FileInputStream; 
import java.io.FileNotFoundException; 
import javafx.scene.image.Image;
import static gui.ImageFactory.SoortImage.*;

public class ImageFactory { 
	public enum SoortImage { WALL, VELD, DOEL, KIST, MANNETJE, LEEG, ERASER;}
	
	private static final Map<SoortImage, Image> mapImage = new HashMap<>() { 
		{ 
			try {
				put(WALL, new Image(new FileInputStream("/resources/images/wall.png")));
				put(VELD, new Image(new FileInputStream("/resources/images/tile.png")));
				put(DOEL, new Image(new FileInputStream("/resources/images/goal.png")));
				put(KIST, new Image(new FileInputStream("/resources/images/box.png")));
				put(MANNETJE, new Image(new FileInputStream("/resources/images/dr-mario-game.png")));
				put(LEEG, new Image(new FileInputStream("/resources/images/surface.png")));
				put(ERASER, new Image(new FileInputStream("/resources/images/eraser.png")));
				} catch (FileNotFoundException e) {
					e.printStackTrace(); }
			} 
	}; 
		
	public static Image geefImage(SoortImage soort) { 
			return mapImage.get(soort); 
	} 
}

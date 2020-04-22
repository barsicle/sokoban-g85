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
				System.out.println("Path: "+ImageFactory.class.getClass().getResource("/resources/images/wall.png").toExternalForm());
				put(WALL, new Image(ImageFactory.class.getClass().getResource("/resources/images/wall.png").toExternalForm()));
				/*put(VELD, new Image(new FileInputStream("../resources/images/tile.png")));
				put(DOEL, new Image(new FileInputStream("../resources/images/goal.png")));
				put(KIST, new Image(new FileInputStream("../resources/images/box.png")));
				put(MANNETJE, new Image(new FileInputStream("../resources/images/dr-mario-game.png")));
				put(LEEG, new Image(new FileInputStream("../resources/images/surface.png")));
				put(ERASER, new Image(new FileInputStream("../resources/images/eraser.png")));*/
				put(VELD, null);
				put(DOEL, null);
				put(KIST,null);
				put(LEEG,null);
				put(ERASER,null);
				} catch (Exception e) {
					e.printStackTrace(); }
			} 
	}; 
		
	public static Image geefImage(SoortImage soort) { 
			return mapImage.get(soort); 
	} 
}

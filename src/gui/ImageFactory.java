package gui;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.image.Image;
import static gui.ImageFactory.SoortImage.*;

public class ImageFactory { 
	public enum SoortImage { WALL, VELD, DOEL, KIST, MANNETJE, LEEG, ERASER, MARIO, BOWSER;}
	
	private static final Map<SoortImage, Image> mapImage = new HashMap<>() { 
		{ 
			try {
                put(WALL, new Image(getClass().getResource("/resources/images/wall.png").toString()));
                put(VELD, new Image(getClass().getResource("/resources/images/tile.png").toString()));
                put(DOEL, new Image(getClass().getResource("/resources/images/goal.png").toString()));
                put(KIST, new Image(getClass().getResource("/resources/images/box.png").toString()));
                put(MANNETJE, new Image(getClass().getResource("/resources/images/dr-mario-game.png").toString()));
                put(LEEG, new Image(getClass().getResource("/resources/images/surface.png").toString()));
                put(ERASER, new Image(getClass().getResource("/resources/images/eraser.png").toString()));
                put(MARIO, new Image(getClass().getResource("/resources/images/mario.png").toString()));
                put(BOWSER, new Image(getClass().getResource("/resources/images/bowser.png").toString()));
                
				} catch (Exception e) {
					e.printStackTrace(); }
			} 
	}; 
		
	public static Image geefImage(SoortImage soort) { 
			return mapImage.get(soort); 
	} 
}

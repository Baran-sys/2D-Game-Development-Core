package object;

import java.io.File;
import javax.imageio.ImageIO;

import Main.GamePanel;

public class OBJ_Door extends SuperObject {

    public OBJ_Door(GamePanel gp) {
        name = "Door";
        try {
            image = ImageIO.read(new File("res/objects/door.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize); // Scale the door image to the tile size
        } catch (Exception e) {
            e.printStackTrace();
        }
        collision = true; // Set collision to true for the door object
    }

}

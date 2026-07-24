package object;

import java.io.File;
import javax.imageio.ImageIO;

import Main.GamePanel;

public class OBJ_Key extends SuperObject {

    public OBJ_Key(GamePanel gp) {
        name = "Key";
        try {
            image = ImageIO.read(new File("res/objects/key.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize); // Scale the key image to the tile size
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

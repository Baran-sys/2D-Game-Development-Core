package object;

import java.io.File;

import javax.imageio.ImageIO;

import Main.GamePanel;

public class OBJ_Boots extends SuperObject {

    public OBJ_Boots(GamePanel gp) {
        name = "Boots";
        try {
            image = ImageIO.read(new File("res/objects/boots.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize); // Scale the boots image to the tile size
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

package object;

import java.io.File;
import javax.imageio.ImageIO;

import Main.GamePanel;

public class OBJ_Chest extends SuperObject {
    private boolean isOpen = false; // Track whether the chest is open or closed
    public OBJ_Chest(GamePanel gp) {
        name = "Chest";
        try {
            image = ImageIO.read(new File("res/objects/chest.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize); // Scale the chest image to the tile size
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setOpen(boolean b) {
        isOpen = b;
    }
    public boolean isOpen() {
        return isOpen;
    }

}

package object;

import java.io.File;
import javax.imageio.ImageIO;

public class OBJ_Door extends SuperObject {

    public OBJ_Door() {
        name = "Door";
        try {
            image = ImageIO.read(new File("res/objects/door.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        collision = true; // Set collision to true for the door object
    }

}

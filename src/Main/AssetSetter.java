package Main;

import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {

        gp.obj[0] = new OBJ_Key(gp);
        gp.obj[0].worldX = 23 * gp.tileSize;
        gp.obj[0].worldY = 7 * gp.tileSize;

        gp.obj[1] = new OBJ_Door(gp);
        gp.obj[1].worldX = 18 * gp.tileSize;
        gp.obj[1].worldY = 27 * gp.tileSize;

        gp.obj[2] = new OBJ_Chest(gp);
        gp.obj[2].worldX = 22 * gp.tileSize;
        gp.obj[2].worldY = 3 * gp.tileSize;

        gp.obj[3] = new OBJ_Boots(gp);
        gp.obj[3].worldX = 22 * gp.tileSize;
        gp.obj[3].worldY = 4 * gp.tileSize;
    }

}

package tile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import Main.GamePanel;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][];

    private File resolveFile(String relativePath) {
        File currentDir = new File(System.getProperty("user.dir"));
        while (currentDir != null) {
            File candidate = new File(currentDir, relativePath);
            if (candidate.exists() && candidate.isFile()) {
                return candidate;
            }
            currentDir = currentDir.getParentFile();
        }
        return new File(relativePath);
    }
    
    private BufferedImage loadImage(String... relativePaths) {
        for (String relativePath : relativePaths) {
            try {
                File file = resolveFile(relativePath);
                if (file.exists() && file.isFile()) {
                    return ImageIO.read(file);
                }
            } catch (Exception ignored) {
            }
        }
        return null;
    }

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[10];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap();
    }

    public void getTileImage() {
        try {
            tile[0] = new Tile();
            tile[0].image = loadImage("res/tiles/grass.png");

            tile[1] = new Tile();
            tile[1].image = loadImage("res/tiles/wall.png");
            tile[1].collision = true;

            tile[2] = new Tile();
            tile[2].image = loadImage("res/tiles/water.png");
            tile[2].collision = true;

            tile[3] = new Tile();
            tile[3].image = loadImage("res/tiles/earth.png");

            tile[4] = new Tile();
            tile[4].image = loadImage("res/tiles/tree.png");
            tile[4].collision = true;

            tile[5] = new Tile();
            tile[5].image = loadImage("res/tiles/sand.png");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadMap() {
        try {
            File mapFile = resolveFile("res/maps/map2.txt");
            BufferedReader br = new BufferedReader(new FileReader(mapFile));

            int row = 0;
            while (row < gp.maxWorldRow) {
                String line = br.readLine();
                if (line == null) break;

                String numbers[] = line.split(" ");
                for (int col = 0; col < gp.maxWorldCol && col < numbers.length; col++) {
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                }
                row++;
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
            int tileNum = mapTileNum[worldCol][worldRow];

            if (tileNum >= 0 && tileNum < tile.length && tile[tileNum] != null && tile[tileNum].image != null) {
                int worldX = worldCol * gp.tileSize;
                int worldY = worldRow * gp.tileSize;
                int screenX = worldX - gp.player.worldX + gp.player.screenX;
                int screenY = worldY - gp.player.worldY + gp.player.screenY;
                
                if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                   worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                   worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                   worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
                    g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
                }
            }

            worldCol++;
            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}

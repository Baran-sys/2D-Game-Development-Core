package Entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Main.GamePanel;
import Main.KeyHandler;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyH;

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

    public final int screenX;
    public final int screenY;
    public Rectangle solidArea = new Rectangle(8, 16, 32, 32); // Adjust the solid area to match the player's sprite size

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(resolveFile("res/player/boy_up_1.png"));
            up2 = ImageIO.read(resolveFile("res/player/boy_up_2.png"));
            down1 = ImageIO.read(resolveFile("res/player/boy_down_1.png"));
            down2 = ImageIO.read(resolveFile("res/player/boy_down_2.png"));
            left1 = ImageIO.read(resolveFile("res/player/boy_left_1.png"));
            left2 = ImageIO.read(resolveFile("res/player/boy_left_2.png"));
            right1 = ImageIO.read(resolveFile("res/player/boy_right_1.png"));
            right2 = ImageIO.read(resolveFile("res/player/boy_right_2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (keyH.upPressed) {
            direction = "up";
            moveIfPossible(0, -speed);
        }if (keyH.downPressed) {
            direction = "down";
            moveIfPossible(0, speed);
        }if (keyH.leftPressed) {
            direction = "left";
            moveIfPossible(-speed, 0);
        }if (keyH.rightPressed) {
            direction = "right";
            moveIfPossible(speed, 0);
        }

        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
    }

    private void moveIfPossible(int deltaX, int deltaY) {
        collisionOn = false;
        gp.cChecker.checkTile(this);

        if (!collisionOn) {
            worldX += deltaX;
            worldY += deltaY;
        }
    }

    public void draw(Graphics2D g2) {
       // g2.setColor(Color.white);
       // g2.fillRect(x, y, gp.tileSize, gp.tileSize);

        BufferedImage image = null;
        switch (direction) {
            case "up":
                if (spriteNum == 1) {
                    image = up1;
                } else if (spriteNum == 2) {
                    image = up2;
                }
                break;
            case "down":
                if (spriteNum == 1) {
                    image = down1;
                } else if (spriteNum == 2) {
                    image = down2;
                }
                break;
            case "left":
                if (spriteNum == 1) {
                    image = left1;
                } else if (spriteNum == 2) {
                    image = left2;
                }
                break;
            case "right":
                if (spriteNum == 1) {
                    image = right1;
                } else if (spriteNum == 2) {
                    image = right2;
                }
                break;
        }
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
 
    }


}

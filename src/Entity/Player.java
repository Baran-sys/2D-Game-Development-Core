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

    public final int screenX;
    public final int screenY;
    public Rectangle solidArea = new Rectangle(10, 14, 28, 28); // Adjust the solid area to match the player's sprite size
    int solidAreaDefaultX = solidArea.x;
    int solidAreaDefaultY = solidArea.y;
    int hasKey = 0;


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
            up1 = ImageIO.read(new File("res/player/boy_up_1.png"));
            up2 = ImageIO.read(new File("res/player/boy_up_2.png"));
            down1 = ImageIO.read(new File("res/player/boy_down_1.png"));
            down2 = ImageIO.read(new File("res/player/boy_down_2.png"));
            left1 = ImageIO.read(new File("res/player/boy_left_1.png"));
            left2 = ImageIO.read(new File("res/player/boy_left_2.png"));
            right1 = ImageIO.read(new File("res/player/boy_right_1.png"));
            right2 = ImageIO.read(new File("res/player/boy_right_2.png"));
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

        int objectIndex = gp.cChecker.checkObject(this, true);
        pickUpObject(objectIndex);

        if (!collisionOn) {
            worldX += deltaX;
            worldY += deltaY;
        }
    }


    public void pickUpObject(int index) {
        if (index != -1) {
            String objectName = gp.obj[index].name;

            switch (objectName) {
                case "Key":
                    hasKey++;
                    gp.obj[index] = null;
                    System.out.println("You got a key! Total keys: " + hasKey);
                    break;
                case "Door":
                    if (hasKey > 0) {
                        hasKey--;
                        gp.obj[index] = null;
                        System.out.println("You opened the door! Remaining keys: " + hasKey);
                    } else {
                        System.out.println("You need a key to open this door.");
                    }
                    break;
                case "Chest":
                    System.out.println("You found a chest!");
                    // Add logic for opening the chest or collecting items
                    break;
            }
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

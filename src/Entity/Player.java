package Entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Main.GamePanel;
import Main.KeyHandler;
import Main.UtilityTool;
import object.OBJ_Chest;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    public Rectangle solidArea = new Rectangle(10, 14, 28, 28); // Adjust the solid area to match the player's sprite size
    int solidAreaDefaultX = solidArea.x;
    int solidAreaDefaultY = solidArea.y;
    public int hasKey = 0;
    private int lastInteractedObjectIndex = -1;


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
            up1 = setup("boy_up_1");
            up2 = setup("boy_up_2");
            down1 = setup("boy_down_1");
            down2 = setup("boy_down_2");
            left1 = setup("boy_left_1");
            left2 = setup("boy_left_2");
            right1 = setup("boy_right_1");
            right2 = setup("boy_right_2");
    }

    public BufferedImage setup(String imagePath) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("res/player/" + imagePath + ".png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
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

        if (objectIndex != -1) {
            if (objectIndex != lastInteractedObjectIndex) {
                pickUpObject(objectIndex);
                lastInteractedObjectIndex = objectIndex;
            }
        } else {
            lastInteractedObjectIndex = -1;
        }

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
                    gp.playSE(1); // Play key pickup sound effect
                    hasKey++; 
                    gp.obj[index] = null;
                    gp.ui.showMessage("You got a key! Total keys: " + hasKey);
                    break;
                case "Door":
                    if (hasKey > 0) {
                        gp.playSE(3); // Play door opening sound effect
                        hasKey--;
                        gp.obj[index] = null;
                        gp.ui.showMessage("You opened the door! Remaining keys: " + hasKey);
                    } else {
                        gp.ui.showMessage("You need a key to open this door.");
                    }
                    break;
                case "Chest":
                    if(gp.obj[index] instanceof OBJ_Chest) {
                        OBJ_Chest chest = (OBJ_Chest) gp.obj[index];
                        if (!chest.isOpen()) {
                            chest.setOpen(true); // Mark the chest as open
                            gp.playSE(2); // Play chest opening sound effect
                            gp.ui.showMessage("You found a chest!");
                        } else {
                            gp.ui.showMessage("The chest is already open."); 
                        }
                    }
                    break;
                case "Boots":
                    gp.playSE(4); // Play boots pickup sound effect
                    gp.ui.showMessage("You got boots! Your speed has increased.");
                    gp.obj[index] = null;
                    speed += 2; // Increase speed by 2 when boots are picked up
            }
        }
    }

    public void draw(Graphics2D g2) {
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
        g2.drawImage(image, screenX, screenY, null);
 
    }


}

package Main;

import java.awt.Font;
import java.awt.image.BufferedImage;

import object.OBJ_Key;

public class UI {
    GamePanel gp;
    Font arial_40;
    BufferedImage keyImage;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;

    public UI(GamePanel gp) {
        this.gp = gp;
        this.arial_40 = new Font("Arial", Font.PLAIN, 40);
        OBJ_Key key = new OBJ_Key(gp);
        this.keyImage = key.image;
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
}

    public void draw(java.awt.Graphics2D g2) {
        g2.setFont(arial_40);
        g2.setColor(java.awt.Color.white);

        // Display player's score
        String scoreText = "x " + gp.player.hasKey;
        g2.drawImage(keyImage, 20, 20, gp.tileSize, gp.tileSize, null);
        g2.drawString(scoreText, 25 + gp.tileSize, 62);

        if (messageOn) {
            g2.setFont(g2.getFont().deriveFont(30f)); // Set font size for the message
            g2.drawString(message, gp.tileSize * 3, gp.tileSize * 11);
            // You can add a timer to turn off the message after a few seconds if needed
            messageCounter++;

            if (messageCounter > 90) { // Display message for 2 seconds (assuming 60 FPS)
                messageOn = false;
                messageCounter = 0;
            }
        }
    }
}

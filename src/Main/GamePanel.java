package Main;

import java.awt.*;
import javax.swing.JPanel;

import Entity.Player;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {
    
    // Screen Settings
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3;
    public final int tileSize = originalTileSize * scale; // 48x48 tile
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    //World Settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    
    //FPS
    int FPS = 60;

    TileManager tileM = new TileManager(this);
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public Sound music = new Sound();
    public Sound se = new Sound();
    Thread gameThread;

    // Entity and Object
    KeyHandler keyH = new KeyHandler();
    public Player player = new Player(this, keyH);
    public SuperObject obj[] = new SuperObject[10];

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {
       
        aSetter.setObject();

        playMusic(0);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000/FPS; // 0.01666 seconds
        double nextDrawTime = System.nanoTime() + drawInterval;
        double timer = 0;
        int drawCount = 0;
        long lastTime = System.nanoTime();

        while (gameThread !=null) {
            update();
            repaint();
            drawCount++;

            long currentTime = System.nanoTime();
            timer += currentTime - lastTime;
            lastTime = currentTime;

            if (timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer -= 1000000000;
            }

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000; // Convert to milliseconds

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime); // Pause the thread for the remaining time until the next frame

                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        player.update();
    }

    public void draw(Graphics2D g2) {
        player.draw(g2);
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        tileM.draw(g2); // Draw tiles

        // Draw objects first, then player on top
        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null) {
                obj[i].draw(g2, this);
            }
        }

        player.draw(g2); // Draw player on top
        ui.draw(g2); // Draw UI on top of everything
        g2.dispose();
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.setVolume(0.5f); // Set volume to 50%
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSE(int i) {
        se.setFile(i);
        se.setVolume(0.5f);
        se.play();
    }

}

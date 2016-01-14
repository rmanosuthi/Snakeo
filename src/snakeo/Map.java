/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakeo;

import audio.AudioPlayer;
import environment.Environment;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import grid.Grid;
import images.ResourceTools;
import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.io.FileInputStream;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author panpjp
 */
class Map extends Environment implements CellDataProviderIntf, LocationValidatorIntf {

    private final Grid grid;
    private Snake lenny;
    private Image healthImage;
    private ArrayList<Barrier> barriers;
    private ArrayList<Item> items;
    private Image healthBar00;
    private Image healthBar10;
    private Image healthBar20;
    private Image healthBar30;
    private Image healthBar40;
    private Image healthBar50;
    private Image healthBar60;
    private Image healthBar70;
    private Image healthBar80;
    private Image healthBar90;
    private Image healthBar100;
    

    public Map() {
        this.setBackground(Color.WHITE);

        grid = new Grid(55, 30, 20, 20, new Point(20, 50), Color.BLACK);
        lenny = new Snake(Direction.LEFT, grid, this);

        healthImage = ResourceTools.loadImageFromResource("snakeo/ui/healthbar/hb_empty.png");
/*        barriers = new ArrayList<>();
        barriers.add(new Barrier(10, 10, Color.GREEN, this, false));
        barriers.add(new Barrier(10, 11, Color.GREEN, this, false));
        barriers.add(new Barrier(10, 12, Color.GREEN, this, false));
        barriers.add(new Barrier(10, 13, Color.GREEN, this, false));
        barriers.add(new Barrier(10, 14, Color.GREEN, this, false));
        barriers.add(new Barrier(10, 15, Color.GREEN, this, false));
        barriers.add(new Barrier(10, 16, Color.GREEN, this, false));
        */
        
//        myBarrier = new Barrier(10, 15, Color.GREEN, this, false);
        healthBar00 = ResourceTools.loadImageFromResource("snakeo/ui/healthbar/hb_empty.png");
        healthBar10 = ResourceTools.loadImageFromResource("snakeo/ui/healthbar/hb_10.png");
        healthBar20 = ResourceTools.loadImageFromResource("snakeo/ui/healthbar/hb_20.png");
        healthBar30 = ResourceTools.loadImageFromResource("snakeo/ui/healthbar/hb_30.png");
        healthBar40 = ResourceTools.loadImageFromResource("snakeo/ui/healthbar/hb_40.png");
        healthBar50 = ResourceTools.loadImageFromResource("snakeo/ui/healthbar/hb_50.png");
        healthBar60 = ResourceTools.loadImageFromResource("snakeo/ui/healthbar/hb_60.png");
        healthBar70 = ResourceTools.loadImageFromResource("snakeo/ui/healthbar/hb_70.png");
        healthBar80 = ResourceTools.loadImageFromResource("snakeo/ui/healthbar/hb_80.png");
        healthBar90 = ResourceTools.loadImageFromResource("snakeo/ui/healthbar/hb_90.png");
        healthBar100 = ResourceTools.loadImageFromResource("snakeo/ui/healthbar/hb_full.png");

        items = new ArrayList<>();
        items.add(new Item(10, 5, "POWER_UP", ResourceTools.loadImageFromResource("snakeo/powerup.png"), this));
    }

    @Override
    public void initializeEnvironment() {
    }

    int moveDelay = 0;
    int moveDelayLimit = 5;

    @Override
    public void timerTaskHandler() {
        if (lenny != null) {
            /* if counted to limit, then move snake and reset counter, else keep counting */
            if (moveDelay >= moveDelayLimit) {
                moveDelay = 0;
                lenny.move();
            } else {
                moveDelay++;
            }
//            checkIntersections();
        }
    }

    public void checkIntersections() {
        if (barriers != null) {
            for (Barrier barrier : barriers) {
                if (barrier.getLocation().equals(lenny.getHead())) {
                    lenny.rmHealth(100);
                }
            }
        }
    }

    public boolean checkBarriers(Point location) {
        if (barriers != null) {
            for (Barrier barrier : barriers) {
                if (barrier.getLocation().equals(location)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void checkObjects() {
//        for 
    }

    @Override
    public void keyPressedHandler(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            lenny.setDirection(Direction.UP);
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            lenny.setDirection(Direction.DOWN);
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            lenny.setDirection(Direction.LEFT);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            lenny.setDirection(Direction.RIGHT);
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            AudioPlayer.play("snakeo/audio/MP5.wav");
        }

    }

    @Override
    public void keyReleasedHandler(KeyEvent e) {
    }

    @Override
    public void environmentMouseClicked(MouseEvent e) {
    }

    private Image getHealthImage() {
        int snakeHealth = lenny.getHealthRounded();
        if (snakeHealth <= 10) {
            return healthBar00;
        } else if (snakeHealth <= 20) {
            return healthBar10;
        } else if (snakeHealth <= 30) {
            return healthBar20;
        } else if (snakeHealth <= 40) {
            return healthBar30;
        } else if (snakeHealth <= 50) {
            return healthBar40;
        } else if (snakeHealth <= 60) {
            return healthBar50;
        } else if (snakeHealth <= 70) {
            return healthBar60;
        } else if (snakeHealth <= 80) {
            return healthBar70;
        } else if (snakeHealth <= 90) {
            return healthBar80;
        } else if (snakeHealth <= 100) {
            return healthBar90;
        } else {
            return healthBar100;
        }
    }

    @Override
    public void paintEnvironment(Graphics graphics) {
        if (grid != null) {
            grid.paintComponent(graphics);
        }

        if (lenny != null) {
            lenny.draw(graphics);
        }
        
        graphics.drawImage(getHealthImage(), 10, 10, this);
//        
//        if (myBarrier != null) {
//            myBarrier.draw(graphics);
//        }
        if (barriers != null) {
            for (int i = 0; i < barriers.size(); i++) {
                barriers.get(i).draw(graphics);
                
            }
        }
        if (items != null) {
            for (int i = 0; i < items.size(); i++) {
                items.get(i).draw(graphics);

            }
        }
        drawRectEdge(graphics, 60, 30, 0, 0);
//        printHealthBar(graphics);
    }

    
/*    private void printHealthBar(Graphics graphics) {
        int hr;
        hr = snakeo.Snake.getHealthRounded();
        if (hr = 10) {
            graphics.drawImage(healthBar10, 100, 5, 240, 40, null);
        }
    } */

    public void drawRectEdge(Graphics graphics, int width, int height, int xst, int yst){
        barriers = new ArrayList<>();
        // upper
        for (int x = xst; x < width; x++) {
            barriers.add(new Barrier(x, 0, Color.GREEN, this, false));
        }
        // right side
        for (int y = yst; y < height; y++) {
            barriers.add(new Barrier(width, y, Color.GREEN, this, false));
        }
        // bottom
        for (int x = xst; x < width; x++) {
            barriers.add(new Barrier(x, height, Color.GREEN, this, false));
        }
        // left side
        for (int y = yst; y < height; y++) {
            barriers.add(new Barrier(0, y, Color.GREEN, this, false));
        }
        
    }
    @Override
    public int getCellWidth() {
        return grid.getCellWidth();
    }

    @Override
    public int getCellHeight() {
        return grid.getCellHeight();
    }

    @Override
    public int getSystemCoordX(int x, int y) {
        return grid.getCellSystemCoordinate(x, y).x;
    }

    @Override
    public int getSystemCoordY(int x, int y) {
        return grid.getCellSystemCoordinate(x, y).y;
    }

//<editor-fold defaultstate="collapsed" desc="LocationValidatorIntf">
    @Override
    public Point validate(Point proposedLocation) {
        //check is he is trying to put his hean into a wall
        // - is so, block the damn snake!!!!!!!
        //          make a farting noise
        //          say something mean to the player...
        
        if (checkBarriers(proposedLocation)) {
            lenny.addHealth(-1);
            lenny.setBlocked(true);
            System.out.println("Hey, you're a crappy driver...");
        }
        return proposedLocation;
    }
//</editor-fold>


}
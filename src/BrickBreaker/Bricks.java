package BrickBreaker;

import java.awt.Color;
import java.awt.Graphics2D;

public class Bricks {
    public int map[][];
    public int brickWidth;
    public int brickHeight;

    public Bricks(int row, int column) {
        map = new int[row][column];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j] = 1;
            }
        }
        brickWidth = (600 - 7 * (column - 1)) / column;     // 7 piksel aralık için
        brickHeight = (180 - 7 * (row - 1)) / row;
    }

    public void draw(Graphics2D g) {
    

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] > 0) {
                    int brickX = j * (brickWidth + 7) + 50;
                    int brickY = i * (brickHeight + 7) + 40;
                    g.setColor(Color.cyan);
                    g.fillRect(brickX, brickY, brickWidth, brickHeight);
                }
            }
        }
    }

   public void setBrickValue(int value, int row, int column) {
	   map[row][column]=value;
   }
}
package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import logic.Logic;

public class MazePanel extends JPanel {
	private static final long serialVersionUID = -8592548139766967002L;
	public static int cellSize = 50;
	private MazeImages images;
	private Logic game;
	ArrayList<Integer[]> firePositions;
	
	private int rightKey = KeyEvent.VK_D;
	private int downKey = KeyEvent.VK_S;
	private int leftKey = KeyEvent.VK_A;
	private int upKey = KeyEvent.VK_W;
	
	private int rightArrow = KeyEvent.VK_H;
	private int downArrow = KeyEvent.VK_G;
	private int leftArrow = KeyEvent.VK_F;
	private int upArrow = KeyEvent.VK_T;

	public MazePanel() {
		newGame();
		addKeyListener(new MyKeyboardAdapter());
		setFocusable(true);
		images = new MazeImages();
		requestFocus();
    }
	
	public void newGame() {
		if( GameConfig.defaultMaze)
			game = new Logic(GameConfig.dragonMovementState);
		else
			game = new Logic(GameConfig.size, GameConfig.numberDragons, GameConfig.numberDragons);
		this.setSize(cellSize*GameConfig.size, cellSize*GameConfig.size);
		firePositions = new ArrayList<Integer[]>();
		repaint();
	}

	public Dimension getPreferredSize() {
        return new Dimension(cellSize*GameConfig.size, cellSize*GameConfig.size);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        drawMaze(g);
    }

	private void drawMaze(Graphics g) {
		char[][] map = game.getMap();
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				for(BufferedImage image: images.get(map[i][j]))
					g.drawImage(image, cellSize*j, cellSize*i, null);
			}
		}
		
		if(!firePositions.isEmpty()) {
			for(Integer[] position : firePositions)
				g.drawImage(images.fire(), cellSize*position[1], cellSize*position[0], null);
		}
		
		g.setColor(Color.white);
		g.drawString(game.heroDarts()+" darts in your backpack (f, g, h, t to launch them)", 25, 25);
	}
	
	private class MyKeyboardAdapter extends KeyAdapter {
		
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			
			
			if (key == KeyEvent.VK_RIGHT || key == rightKey)
				game.play(Logic.MOVE_RIGHT);
			else if (key == KeyEvent.VK_DOWN || key == downKey)
				game.play(Logic.MOVE_DOWN);
			else if (key == KeyEvent.VK_LEFT || key == leftKey)
				game.play(Logic.MOVE_LEFT);
			else if (key == KeyEvent.VK_UP || key == upKey)
				game.play(Logic.MOVE_UP);
			else if (key == rightArrow)
				game.play(Logic.SHOOT_RIGHT);
			else if (key == downArrow)
				game.play(Logic.SHOOT_DOWN);
			else if (key == leftArrow)
				game.play(Logic.SHOOT_LEFT);
			else if (key == upArrow)
				game.play(Logic.SHOOT_UP);
			
			if(game.gameEnded()) {
				firePositions = game.getDragonBreath();
				
				repaint();
				JOptionPane.showMessageDialog(null, game.getGameEndedMessage());
				newGame();
			}
			
			repaint();
		}
	}
}

package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class GameFrame implements ActionListener {
	private static JPanel maze = new MazePanel();
	private static OptionsDialog options = new OptionsDialog();
	
	
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
	}
	
	private static void createAndShowGUI() {
        JFrame f = new JFrame("Amazing Maze");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(250,250);
        
        ButtonGroup group = new ButtonGroup();
        JPanel menu = new JPanel();
        menu.setFocusable(false);
        
        ArrayList<JButton>buttons = createButtons(menu);
        for(JButton button: buttons) {
        	group.add(button);
        	button.setFocusable(false);
        }
        for(JButton button: buttons)
        	menu.add(button);
        
        f.add(menu, BorderLayout.SOUTH);
        f.add(maze, BorderLayout.NORTH);
        maze.requestFocus();
        f.pack();
        
        f.setVisible(true);
    }

	private static ArrayList<JButton> createButtons(JPanel menu) {
		ArrayList<JButton> buttons = new ArrayList<JButton>();
		
		JButton newGame = new JButton("New Game");
		newGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String msg = "Do you want to start a new game?";
				int res = JOptionPane.showConfirmDialog(menu.getRootPane() , msg);

				if (res == JOptionPane.YES_OPTION) {
					((MazePanel) maze).newGame();
				}
			}
		});
		buttons.add(newGame);
		
		
		// Options button
		JButton btnOptions = new JButton("Options");
		btnOptions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				options.setVisible(true);
			}
		});
		
		buttons.add(btnOptions);
		
		
		// Exit button
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
				
				String msg = "Do you want to exit?";
				int res = JOptionPane.showConfirmDialog(menu.getRootPane() , msg);
				
				if (res == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
		buttons.add(btnExit);
		
		return buttons;
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
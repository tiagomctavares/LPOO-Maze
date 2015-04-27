package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;


public class OptionsDialog extends JDialog {
	private static final long serialVersionUID = 1L;

	private JComboBox<?> mazeMode;
	private JSlider sizeSlider;
	private JSlider numDragonsSlider;
	private JComboBox<?> behaviorSelector;

	/**
	 * Class constructor.
	 * 
	 * @param frame
	 * @param gamePanel
	 * @param gameConfig
	 */
	public OptionsDialog() {
		
		setTitle("Options");
		getContentPane().setLayout(new GridLayout(11, 1));

		// Setting up dialog content
		SetUpMazeSettingsSection();
		SetUpDragonSettingsSection();
		SetUpGameControlsSection();
		SetUpButtonsSection();

		// Dialog Details
		pack();
		/*Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height
				/ 2 - this.getSize().height / 2);*/
	}

	
	/**
	 * Sets up the Maze Settings section.
	 */
	public void SetUpMazeSettingsSection() {
		
		JLabel lblMazeSettings = new JLabel("Maze Settings");
		lblMazeSettings.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblMazeSettings);

		JPanel mazeModePanel = new JPanel();
		getContentPane().add(mazeModePanel);
		
		JLabel lblMazeMode = new JLabel("Mode");
		lblMazeMode.setHorizontalAlignment(SwingConstants.LEFT);
		mazeModePanel.add(lblMazeMode);
		String[] mazeStrings = { "Default Maze", "Random Maze" };
		mazeMode = new JComboBox<Object>(mazeStrings);
		mazeMode.setSelectedIndex(0);
		mazeModePanel.add(mazeMode);

		JPanel mazeW = new JPanel();
		getContentPane().add(mazeW);
		JLabel lblWidth = new JLabel("Size");
		mazeW.add(lblWidth);
		sizeSlider = new JSlider();
		sizeSlider.setMinorTickSpacing(1);
		sizeSlider.setPaintLabels(true);
		sizeSlider.setPaintTicks(true);
		sizeSlider.setSnapToTicks(true);
		sizeSlider.setMajorTickSpacing(5);
		sizeSlider.setMaximum(30);
		sizeSlider.setMinimum(10);
		sizeSlider.setValue(10);
		mazeW.add(sizeSlider);
		
	}
	
	/**
	 * Sets up the number of Dragons section.
	 */
	public void SetUpDragonSettingsSection() {
		JLabel lblDragonsSettings = new JLabel("Dragon Settings");
		lblDragonsSettings.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblDragonsSettings);

		JPanel dragonsBehavior = new JPanel();
		getContentPane().add(dragonsBehavior);
		
		JLabel lblDragons = new JLabel("Behavior");
		lblDragons.setHorizontalAlignment(SwingConstants.LEFT);
		dragonsBehavior.add(lblDragons);
		String[] behaviorStrings = { "Not Moving", "Moving",
				"Moving and Sleeping" };
		behaviorSelector = new JComboBox<Object>(behaviorStrings);
		behaviorSelector.setSelectedIndex(0);
		dragonsBehavior.add(behaviorSelector);

		JPanel mazeW = new JPanel();
		getContentPane().add(mazeW);
		JLabel lblWidth = new JLabel("Number of Dragons");
		mazeW.add(lblWidth);
		numDragonsSlider = new JSlider();
		numDragonsSlider.setMinorTickSpacing(1);
		numDragonsSlider.setPaintLabels(true);
		numDragonsSlider.setPaintTicks(true);
		numDragonsSlider.setSnapToTicks(true);
		numDragonsSlider.setMajorTickSpacing(4);
		numDragonsSlider.setMaximum(9);
		numDragonsSlider.setMinimum(1);
		numDragonsSlider.setValue(1);
		mazeW.add(numDragonsSlider);
	}
	
	private void SetUpGameControlsSection() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Sets up the buttons section
	 */
	public void SetUpButtonsSection() {
		JPanel buttons = new JPanel();
		getContentPane().add(buttons);

		// Submit Button
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// updating game controls
				/*panel.setUpKey(Utilities.getKeyFromString(txtUp.getText()));
				panel.setDownKey(Utilities.getKeyFromString(txtDown.getText()));
				panel.setLeftKey(Utilities.getKeyFromString(txtLeft.getText()));
				panel.setRightKey(Utilities.getKeyFromString(txtRight.getText()));
				panel.setSendEagleKey(Utilities.getKeyFromString(txtSendEagle
						.getText()));*/

				// closing options dialog
				GameConfig.defaultMaze = (mazeMode.getSelectedIndex() == 0);
				GameConfig.size = sizeSlider.getValue();
				GameConfig.numberDragons = numDragonsSlider.getValue();
				GameConfig.dragonMovementState = behaviorSelector.getSelectedIndex()+1;
				setVisible(false);
			}
		});
		buttons.add(btnSubmit);

		// Cancel button
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});
		buttons.add(btnCancel);
	}

}
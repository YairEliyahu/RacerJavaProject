/**
 * A GUI class that describes a race arena and gives the end user the option to manage a race according to his personal choice, 
 * defining arena participants and receiving race data in real time
 * 
 *In an update, We have added two additional buttons that create a competition in the default form
 * @version 09 May 2023
 * @update 10/06/2023
 * @author 1:  Yair Eliyahu
 * @see     
 */

package GUI;

import factory.ArenaFactory;
import factory.RaceBuilder;
import game.arenas.Arena;
import game.racers.Racer;
import utilities.EnumContainer;
import game.arenas.exceptions.RacerTypeException;

import javax.swing.*;

import State.Active;
import State.Broken;
import State.Failed;

import java.awt.*;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//implements ActionListener 
public class ArenaGUI extends JFrame implements ActionListener {

	private static RaceBuilder builder = RaceBuilder.getInstance();
	private JComboBox<String> arenaComboBox, colorComboBox, racerComboBox, racerComboBoxProtoType,
			colorComboBoxProtoType;
	private static ArrayList<Racer> racers;
	private JTextField txtLength, txtMaxRacersNumber, txtNameRacer, txtSpeed, txtAcceleration;
	private Arena arena = null;
	private String chooseArena = null;
	private int arenaPanelLength = 1000;
	private int arenaPanelHeight = 700;
	private boolean raceStarted = false;
	private boolean raceFinished = false;
	private ImageIcon racersImages[] = null;
	private int maxRacers = 8;
	private int racersNumber = 0;
	private JFrame infoTable = null;
	private JPanel controlPanel;

	/**
	 * Constructs a new instance of the ArenaGUI class.
	 * 
	 * The ArenaGUI is a graphical user interface for a racing game. It contains a
	 * control panel for the user to select options for the game and a content panel
	 * that displays the race track and the progress of the race. This method
	 * initializes the GUI and sets its default properties.
	 *
	 */

	public ArenaGUI() {
		super("Race");
		setControlPanel();
		this.setContentPane(CreatePanel());
		this.pack();
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - getHeight()) / 2);
		this.setLocation(x, y);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	/*********************************************************************************************************
	 **********************************************************************************************************
	 **********************************************************************************************************
	 **********************************************************************************************************
	 */

	/*
	 * This code defines a method named setControlPanel() that creates a Swing
	 * JPanel with various components for controlling the arena and racers in a
	 * racing game. The components include labels, combo boxes, text boxes, and
	 * buttons.
	 * 
	 * The first section of the method sets up controls for selecting an arena type
	 * and creating an arena with a specified length and maximum number of racers.
	 * The second section sets up controls for adding a racer with a specified type,
	 * color, name, maximum speed, and acceleration.
	 * 
	 * The second part shows various action buttons such as adding a competitor to a
	 * race, starting a race and getting race data
	 */

	private void setControlPanel() {
		
		

		// Create control panel
		controlPanel = new JPanel();
		controlPanel.setLayout(null);
		controlPanel.setPreferredSize(new Dimension(140, arenaPanelHeight));

		// **************************************************************//

		// Create "Choose Arena" label
		JLabel chooseArenaLabel = new JLabel("Choose Arena:");
		controlPanel.add(chooseArenaLabel);
		chooseArenaLabel.setLocation(10, 5);
		chooseArenaLabel.setSize(100, 10);

		// Create arena combo box
		String[] arenaOptions = { "AerialArena", "NavalArena", "LandArena" };
		arenaComboBox = new JComboBox<String>(arenaOptions);
		controlPanel.add(arenaComboBox);
		arenaComboBox.setLocation(10, 20);
		arenaComboBox.setSize(100, 20);

		// Create "Arena length" label
		JLabel ArenaLengthLabel = new JLabel("Arena length:");
		controlPanel.add(ArenaLengthLabel);
		ArenaLengthLabel.setLocation(10, 45);
		ArenaLengthLabel.setSize(100, 15);

		// Create text box for "Arena length"
		txtLength = new JTextField();
		controlPanel.add(txtLength);
		txtLength.setLocation(10, 65);
		txtLength.setSize(100, 25);

		// Create "Max racers number" label
		JLabel MaxRacersNumberLabel = new JLabel("Max racers number:");
		controlPanel.add(MaxRacersNumberLabel);
		MaxRacersNumberLabel.setLocation(10, 95);
		MaxRacersNumberLabel.setSize(150, 15);

		// Create text box for "Max racers number"
		txtMaxRacersNumber = new JTextField();
		controlPanel.add(txtMaxRacersNumber);
		txtMaxRacersNumber.setLocation(10, 115);
		txtMaxRacersNumber.setSize(100, 25);

		// Button for build arena
		JButton buttonBuildArena = new JButton("Build arena");
		controlPanel.add(buttonBuildArena);
		buttonBuildArena.setLocation(10, 145);
		buttonBuildArena.setSize(100, 30);
		buttonBuildArena.addActionListener(this);
		
		int i = 0;
		for (String string : ArenaFactory.getInstance().getArenasNamesList()) {
			//arenaComboBox.addItem(string);
			if (i == 0)
				arenaComboBox.setSelectedItem(string);
			i++;
		}

		// **************************************************************//
		JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
		controlPanel.add(sep);
		sep.setLocation(0, 180);
		sep.setSize(150, 10);

		// **************************************************************//

		// Create "Choose racer" label
		JLabel chooseRacerLabel = new JLabel("Choose racer:");
		controlPanel.add(chooseRacerLabel);
		chooseRacerLabel.setLocation(10, 185);
		chooseRacerLabel.setSize(100, 10);

		// Create racer combo box
		String[] racerOptions = { "AirPlane", "Helicopter", "Bicycle", "Car", "Horse", "SpeedBoat", "RowBoat" };
		racerComboBox = new JComboBox<String>(racerOptions);
		controlPanel.add(racerComboBox);
		racerComboBox.setLocation(10, 205);
		racerComboBox.setSize(100, 20);

		// Create "Choose color" label
		JLabel ChooseColorLabel = new JLabel("Choose color:");
		controlPanel.add(ChooseColorLabel);
		ChooseColorLabel.setLocation(10, 235);
		ChooseColorLabel.setSize(100, 10);

		// Create racer combo box
		String[] colorOptions = { "Black", "Red", "Yellow", "Green", "Blue" };
		colorComboBox = new JComboBox<String>(colorOptions);
		controlPanel.add(colorComboBox);
		colorComboBox.setLocation(10, 250);
		colorComboBox.setSize(100, 20);

		// Create "Racer Name" label
		JLabel RacerNameLabel = new JLabel("Racer name:");
		controlPanel.add(RacerNameLabel);
		RacerNameLabel.setLocation(10, 275);
		RacerNameLabel.setSize(150, 15);

		// Create text box for "Racer name"
		txtNameRacer = new JTextField();
		controlPanel.add(txtNameRacer);
		txtNameRacer.setLocation(10, 290);
		txtNameRacer.setSize(100, 20);

		// Create "Max speed" label
		JLabel MaxSpeedLabel = new JLabel("Max speed:");
		controlPanel.add(MaxSpeedLabel);
		MaxSpeedLabel.setLocation(10, 315);
		MaxSpeedLabel.setSize(150, 15);

		// Create text box for "Max speed"
		txtSpeed = new JTextField();
		controlPanel.add(txtSpeed);
		txtSpeed.setLocation(10, 335);
		txtSpeed.setSize(100, 25);

		// Create "Acceleration" label
		JLabel AccelerationLabel = new JLabel("Acceleration:");
		controlPanel.add(AccelerationLabel);
		AccelerationLabel.setLocation(10, 365);
		AccelerationLabel.setSize(150, 10);

		// Create text box for "Acceleration"
		txtAcceleration = new JTextField();
		controlPanel.add(txtAcceleration);
		txtAcceleration.setLocation(10, 385);
		txtAcceleration.setSize(100, 25);

		// Button for Add racer
		JButton buttonAddRacer = new JButton("Add racer");
		controlPanel.add(buttonAddRacer);
		buttonAddRacer.setLocation(10, 415);
		buttonAddRacer.setSize(100, 30);
		buttonAddRacer.addActionListener(this);

		// **************************************************************//
		JSeparator sep3 = new JSeparator(SwingConstants.HORIZONTAL);
		controlPanel.add(sep3);
		sep3.setLocation(0, 450);
		sep3.setSize(150, 10);

		// **************************************************************//

		// Create "Choose racer" label
		JLabel chooseRacerProtoType = new JLabel("Choose racer:");
		controlPanel.add(chooseRacerProtoType);
		chooseRacerProtoType.setLocation(10, 460);
		chooseRacerProtoType.setSize(100, 10);

		// Create racer combo box
		String[] racerOptionsProtoType = {};
		racerComboBoxProtoType = new JComboBox<String>(racerOptionsProtoType);
		controlPanel.add(racerComboBoxProtoType);
		racerComboBoxProtoType.setLocation(10, 480);
		racerComboBoxProtoType.setSize(100, 20);

		// Create "Choose color" label
		JLabel ChooseColorProtoType = new JLabel("Choose color:");
		controlPanel.add(ChooseColorProtoType);
		ChooseColorProtoType.setLocation(10, 505);
		ChooseColorProtoType.setSize(100, 10);

		// Create racer combo box
		String[] colorOptionsProtoType = { "Black", "Red", "Yellow", "Green", "Blue" };
		colorComboBoxProtoType = new JComboBox<String>(colorOptionsProtoType);
		controlPanel.add(colorComboBoxProtoType);
		colorComboBoxProtoType.setLocation(10, 520);
		colorComboBoxProtoType.setSize(100, 20);

		// Button for ProtoType
		JButton buttonProtoType = new JButton("ProtoType");
		controlPanel.add(buttonProtoType);
		buttonProtoType.setLocation(10, 550);
		buttonProtoType.setSize(100, 30);
		buttonProtoType.addActionListener(this);

		// Button for Builder
		JButton buttonRaceBuilder = new JButton("Builder");
		controlPanel.add(buttonRaceBuilder);
		buttonRaceBuilder.setLocation(10, 585);
		buttonRaceBuilder.setSize(100, 30);
		buttonRaceBuilder.addActionListener(this);

		// **************************************************************//
		JSeparator sep2 = new JSeparator(SwingConstants.HORIZONTAL);
		controlPanel.add(sep2);
		sep2.setLocation(0, 620);
		sep2.setSize(150, 10);

		// **************************************************************//

		// Button for Start race
		JButton buttonStartRace = new JButton("Start race");
		controlPanel.add(buttonStartRace);
		buttonStartRace.setLocation(10, 625);
		buttonStartRace.setSize(100, 30);
		buttonStartRace.addActionListener(this);

		// Button for Race info
		JButton buttonRaceInfo = new JButton("Show info");
		controlPanel.add(buttonRaceInfo);
		buttonRaceInfo.setLocation(10, 660);
		buttonRaceInfo.setSize(100, 30);
		buttonRaceInfo.addActionListener(this);

	}

	/*********************************************************************************************************
	 **********************************************************************************************************
	 **********************************************************************************************************
	 **********************************************************************************************************
	 */
	/*
	 * Method Functionality The actionPerformed(ActionEvent e) method performs the
	 * following actions based on the user's input:
	 * 
	 * If the action command is "Build arena", the method builds an arena for the
	 * race and updates the frame accordingly. If the race has already started, it
	 * displays a message saying that the race has already started. If the arena
	 * panel length or maximum number of racers are invalid, it displays a message
	 * saying that the input values are invalid and prompts the user to enter new
	 * values.
	 * 
	 * If the action command is "Add racer", the method adds a new racer to the
	 * race. If the race has already started, it displays a message saying that the
	 * racer was not added. If the race has already finished, it displays a message
	 * saying that the racer was not added. If the arena has not been built, it
	 * displays a message saying that the racer was not added. If the maximum number
	 * of racers has been reached, it displays a message saying that the arena is
	 * full and the racer was not added. If the racer's input values are invalid, it
	 * displays a message saying that the input values are invalid and prompts the
	 * user to enter new values. The method then creates a racer object based on the
	 * selected racer type, adds the racer to the race, and initializes the race. It
	 * also updates the racer's image icon and the number of racers in the race.
	 */

	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "Build arena": {
			if (!raceFinished && raceStarted) {
				JOptionPane.showMessageDialog(this, "Race Already Started!");
				break;
			}
			arenaPanelLength = Integer.parseInt(txtLength.getText());
			maxRacers = Integer.parseInt(txtMaxRacersNumber.getText());
			if (arenaPanelLength < 100 || arenaPanelLength > 3000 || maxRacers <= 0 || maxRacers > 20) {
				JOptionPane.showMessageDialog(this, "Invalid input values! Please try again.");
				break;
			}
			
			racersNumber = 0;
			raceStarted = raceFinished = false;
			int newHeight = (maxRacers + 1) * 60;
			if (newHeight > 700)
				this.arenaPanelHeight = newHeight;
			else
				this.arenaPanelHeight = 700;
			racers = new ArrayList<>();
			racersImages = new ImageIcon[maxRacers];
			chooseArena = (String) arenaComboBox.getSelectedItem();
			try {
				if (chooseArena.equals("AerialArena")) {
					arena = builder.buildArena("game.arenas.air.AerialArena", arenaPanelLength, maxRacers);
				} else if (chooseArena.equals("LandArena")) {
					arena = builder.buildArena("game.arenas.land.LandArena", arenaPanelLength, maxRacers);
				} else if (chooseArena.equals("NavalArena")) {
					arena = builder.buildArena("game.arenas.naval.NavalArena", arenaPanelLength, maxRacers);
				}
			} catch (Exception ex) {
				System.out.println(ex);
			}
			updateFrame();
			break;
		}
		// ---------------------------------------------------------------------------------------------------------

		
		
		// ---------------------------------------------------------------------------------------------------------

		
		/**
		 * This case is executed when the user selects the "ProtoType" option.
		 * It adds a new racer to the race based on a selected prototype racer and chosen color.
		 */
		
		
		case "ProtoType": {
			if (raceStarted) {
				JOptionPane.showMessageDialog(this, "The race already started racer was not added!");
				break;
			}
			if (raceFinished) {
				JOptionPane.showMessageDialog(this, "The race has finished racer was not added!");
				break;

			}
			if (arena == null) {
				JOptionPane.showMessageDialog(this, "Arena was not build, racer was not added!");
				break;
			}
			if (racersNumber == maxRacers) {
				JOptionPane.showMessageDialog(this, "The arena is full, racer was not added!");
				break;
			}
			try {
				String name = txtNameRacer.getText();
				if (name.isEmpty()) {
					throw new Exception();
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Invalid input values! Please try again, racer was not added!");
				break;
			}

			EnumContainer.Color clpro = null;
			switch ((String) colorComboBoxProtoType.getSelectedItem()) {
			case "Red":
				clpro = EnumContainer.Color.RED;
				break;
			case "Black":
				clpro = EnumContainer.Color.BLACK;
				break;
			case "Green":
				clpro = EnumContainer.Color.GREEN;
				break;
			case "Blue":
				clpro = EnumContainer.Color.BLUE;
				break;
			case "Yellow":
				clpro = EnumContainer.Color.YELLOW;
				break;
			}

			Racer newRacer = null;

			for (int i = 0; i < racersNumber; i++) {
				if (racerComboBoxProtoType.getSelectedItem().equals(racers.get(i).getName())) {
					newRacer = racers.get(i).clone();
					newRacer.upgrade(clpro);
				}
			}

			try {
				racers.add(newRacer);
				// racerComboBoxProtoType.addItem(racer.getName().toString());
				arena.addRacer(newRacer);
				arena.initRace();
			} catch (Exception ex) {
				break;
			}
			racersImages[racersNumber] = new ImageIcon(new ImageIcon("icons/" + newRacer.className() + clpro + ".png")
					.getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT));
			racersNumber++;
			updateFrame();
			break;
		}

		// ---------------------------------------------------------------------------------------------------------
		case "Add racer": {
			if (raceStarted) {
				JOptionPane.showMessageDialog(this, "The race already started racer was not added!");
				break;
			}
			if (raceFinished) {
				JOptionPane.showMessageDialog(this, "The race has finished racer was not added!");
				break;

			}
			if (arena == null) {
				JOptionPane.showMessageDialog(this, "Arena was not build, racer was not added!");
				break;
			}
			if (racersNumber == maxRacers) {
				JOptionPane.showMessageDialog(this, "The arena is full, racer was not added!");
				break;
			}
			try {
				String name = txtNameRacer.getText();
				double acceleration = Double.parseDouble(txtAcceleration.getText());
				double maxspeed = Double.parseDouble(txtSpeed.getText());
				if (name.isEmpty() || acceleration <= 0 || maxspeed <= 0) {
					throw new Exception();
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Invalid input values! Please try again, racer was not added!");
				break;
			}
			String Racertype = (String) racerComboBox.getSelectedItem();
			String Racerclass = null;
			switch (Racertype) {
			case "AirPlane":
				Racerclass = "game.racers.air.Airplane";
				break;
			case "Helicopter":
				Racerclass = "game.racers.air.Helicopter";
				break;
			case "Car":
				Racerclass = "game.racers.land.Car";
				break;
			case "Bicycle":
				Racerclass = "game.racers.land.Bicycle";
				break;
			case "Horse":
				Racerclass = "game.racers.land.Horse";
				break;
			case "RowBoat":
				Racerclass = "game.racers.naval.RowBoat";
				break;
			case "SpeedBoat":
				Racerclass = "game.racers.land.naval.SpeedBoat";
				break;
			}
			EnumContainer.Color cl = null;
			switch ((String) colorComboBox.getSelectedItem()) {
			case "Red":
				cl = EnumContainer.Color.RED;
				break;
			case "Black":
				cl = EnumContainer.Color.BLACK;
				break;
			case "Green":
				cl = EnumContainer.Color.GREEN;
				break;
			case "Blue":
				cl = EnumContainer.Color.BLUE;
				break;
			case "Yellow":
				cl = EnumContainer.Color.YELLOW;
				break;
			}

			if (Racertype.equals("AirPlane") || Racertype.equals("Car") || Racertype.equals("Bicycle")) {
				try {
					Racer racer = builder.buildWheeledRacer(Racerclass, txtNameRacer.getText(),
							Double.parseDouble(txtSpeed.getText()), Double.parseDouble(txtAcceleration.getText()), cl,
							3);
					racers.add(racer);
					racerComboBoxProtoType.addItem(racer.getName().toString());
					arena.addRacer(racer);
					arena.initRace();

				} catch (RacerTypeException ex) {
					JOptionPane.showMessageDialog(this, ex.getMessage());
					break;
				} catch (Exception ex) {
					break;
				}
			} else {
				try {
					Racer racer = builder.buildRacer(Racerclass, txtNameRacer.getText(),
							Double.parseDouble(txtSpeed.getText()), Double.parseDouble(txtAcceleration.getText()), cl);
					racers.add(racer);
					racerComboBoxProtoType.addItem(racer.getName().toString());
					arena.addRacer(racer);
					arena.initRace();
				} catch (RacerTypeException ex) {
					JOptionPane.showMessageDialog(this, ex.getMessage());
					break;
				} catch (Exception ex) {
					break;
				}
			}
			racersImages[racersNumber] = new ImageIcon(new ImageIcon("icons/" + Racertype + cl + ".png").getImage()
					.getScaledInstance(70, 70, Image.SCALE_DEFAULT));
			racersNumber++;
			updateFrame();
			break;
		}
		// ---------------------------------------------------------------------------------------------------------

		
		
		/**
		 * This case is executed when the user selects the "Builder" option.
		 * It adds a new racer to the race based on a builder pattern.
		 */
		
		
		
		case "Builder":
			if (raceStarted) {
				JOptionPane.showMessageDialog(this, "The race already started racer was not added!");
				break;
			}
			if (raceFinished) {
				JOptionPane.showMessageDialog(this, "The race has finished racer was not added!");
				break;

			}
			if (arena == null || txtMaxRacersNumber == null || txtLength == null) {
				JOptionPane.showMessageDialog(this, "Arena was not build, racer was not added!");
				break;
			}
			if (racersNumber == maxRacers) {
				JOptionPane.showMessageDialog(this, "The arena is full, racer was not added!");
				break;
			}
			if (!(arenaComboBox.getSelectedItem().equals("LandArena"))) {
				JOptionPane.showMessageDialog(this, "The arena must be Land Arena");
				break;
			}

			int N = Integer.parseInt(txtMaxRacersNumber.getText());
			String Racerclass = "game.racers.land.Car";
			String name = "Builder";
			double speed=80;
			double acc=8;
			EnumContainer.Color cl = EnumContainer.Color.BLACK;
			try {
				Racer racer = builder.buildWheeledRacer(Racerclass, name, speed, acc, cl, 4);
				racers.add(racer);
				racerComboBoxProtoType.addItem(racer.getName().toString());
				arena.addRacer(racer);
				arena.initRace();
				racersImages[racersNumber] = new ImageIcon(new ImageIcon("icons/" + racer.className() + cl + ".png")
						.getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT));
				racersNumber++;
				updateFrame();

			} catch (RacerTypeException ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage());
				break;
			} catch (Exception ex) {
				break;
			}

			

			for (int i = 0; i < N ; i++) {
				Racer newRacer = null;
				try {
					newRacer = racers.get(0).clone();
					newRacer.upgrade(cl);
					racers.add(newRacer);
					racerComboBoxProtoType.addItem(newRacer.getName().toString());
					arena.addRacer(newRacer);
					arena.initRace();
				} catch (Exception ex) {
					break;
				}
				racersImages[racersNumber] = new ImageIcon(new ImageIcon("icons/" + newRacer.className() + cl + ".png")
						.getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT));
				racersNumber++;
				updateFrame();
			}

			break;

		// ---------------------------------------------------------------------------------------------------------

		case "Start race":
			if (arena == null) {
				JOptionPane.showMessageDialog(this, "Build Arena first!");
				break;
			}
			if (racersNumber == 0) {
				JOptionPane.showMessageDialog(this, "There is no racers in the arena!");
				break;
			}
			if (raceStarted) {
				JOptionPane.showMessageDialog(this, "The race already started");
				break;
			}
			if (raceFinished) {
				JOptionPane.showMessageDialog(this, "The race finished");
				break;
			}

			raceStarted = true;
			// try {
			(new Thread() {
				public void run() {
					while (arena.hasActiveRacers()) {
						try {
							Thread.sleep(30);
						} catch (InterruptedException ex) {
							ex.printStackTrace();
						}
						updateFrame();
					}
					System.out.println("loop ended");
					updateFrame();
					raceFinished = true;
				}
			}).start();

			for (int i = 0; i < arena.getActiveRacers().size(); i++) {
				Thread thread = new Thread(arena.getActiveRacers().get(i));
				thread.start();
			}
			break;

		case "Show info":
			if (arena == null || racersNumber == 0) {
				JOptionPane.showMessageDialog(this, "cannot show info try again!");
				break;
			}
			int count = 0;
			String[] columnNames = { " Racer name", "Current speed", "Max speed", "Current x location", "Finished","State", "Broken Time" };
			String[][] info = new String[racersNumber][7];
			for (Racer r: arena.getCompletedRacers()) {
				info[count][0] = r.getName();
				info[count][1] = "" + r.getCurrentSpeed();
				info[count][2] = "" + r.getMaxSpeeed();
				info[count][3] = "" + r.getCurrentLocation().getX();
				info[count][4] = "Yes";
				info[count][5] = "Completed";
				info[count][6] = "X";
				
				if(r.getState() instanceof Active)
				{
					info[count][5] = "Active";
				}else if(r.getState() instanceof Broken)
				{
					info[count][5] = "Broken";
					info[count][6] = r.getBreakdownTime() + "ms";
				}else if(r.getState() instanceof Failed)
				{
					info[count][4] = "No";
					info[count][5] = "Failed";
					info[count][6] =String.valueOf(r.getBreakdownTime())+ "ms";
				}
				count++;
			}
			count = 0;
			for (Racer r: arena.getActiveRacers()) {
			
				info[count][0] = r.getName();
				info[count][1] = "" + r.getCurrentSpeed();
				info[count][2] = "" + r.getMaxSpeeed();
				info[count][3] = "" + r.getCurrentLocation().getX();
				info[count][4] = "No";
				
				info[count][6] = "X";
				
				if(r.getState() instanceof Active)
				{
					info[count][5] = "Active";
				}else if(r.getState() instanceof Broken)
				{
					info[count][5] = "Broken";
					info[count][6] = r.getBreakdownTime()+ "ms";
				}else if(r.getState() instanceof Failed)
				{
					info[count][5] = "Failed";
					
				}
				count++;
			}
			JTable table = new JTable(info, columnNames);
			table.setPreferredScrollableViewportSize(table.getPreferredSize());
			JScrollPane scrollPane = new JScrollPane(table);

			JPanel tabPan = new JPanel();
			tabPan.add(scrollPane);

			if (infoTable != null)
				infoTable.dispose();
			infoTable = new JFrame("Racers information");
			infoTable.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			infoTable.setContentPane(tabPan);
			infoTable.pack();
			infoTable.setVisible(true);
			break;
		}

	}

	/*********************************************************************************************************
	 **********************************************************************************************************
	 **********************************************************************************************************
	 **********************************************************************************************************
	 */

	/**
	 * 
	 * Sets the preferred height of the control panel to match the height of the
	 * arena panel.
	 */

	private void setControlPanelHeight() {
		controlPanel.setPreferredSize(new Dimension(140, arenaPanelHeight));
	}

	/*********************************************************************************************************
	 **********************************************************************************************************
	 **********************************************************************************************************
	 **********************************************************************************************************
	 */
	/*
	 * /**
	 * 
	 * This method creates the main panel of the application which includes the
	 * arena picture, the racers' icons,
	 * 
	 * and the control panel.
	 * 
	 * @return the main panel of the application
	 */

	public JPanel CreatePanel() {
		JPanel ArenasPanel = new JPanel();
		ArenasPanel.setLayout(null);
		ArenasPanel.setPreferredSize(new Dimension(arenaPanelLength + 80, arenaPanelHeight));
		/* set the Arena picture */
		ImageIcon imageIcon1 = new ImageIcon(new ImageIcon("icons/" + chooseArena + ".jpg").getImage()
				.getScaledInstance(arenaPanelLength + 80, arenaPanelHeight, Image.SCALE_DEFAULT));
		JLabel picLabel1 = new JLabel(imageIcon1);
		picLabel1.setLocation(0, 0);
		picLabel1.setSize(arenaPanelLength + 80, arenaPanelHeight);
		ArenasPanel.add(picLabel1);
		/* set the racers icons */
		for (int i = 0; i < racersNumber; i++) {
			JLabel picLabel2 = new JLabel(racersImages[i]);
			picLabel2.setLocation((int) racers.get(i).getCurrentLocation().getX() + 5,
					(int) racers.get(i).getCurrentLocation().getY() + i * 50);
			picLabel2.setSize(70, 70);
			picLabel1.add(picLabel2);
		}

		/* return the main panel */
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(ArenasPanel, BorderLayout.WEST);
		JSeparator sp3 = new JSeparator(SwingConstants.VERTICAL);
		sp3.setBackground(Color.BLUE);
		mainPanel.add(sp3, BorderLayout.CENTER);
		mainPanel.add(controlPanel, BorderLayout.EAST);
		return mainPanel;

	}

	/*********************************************************************************************************
	 **********************************************************************************************************
	 **********************************************************************************************************
	 **********************************************************************************************************
	 */

	/*
	 * Description:
	 * 
	 * This method is responsible for updating the frame of a GUI (Graphical User
	 * Interface) application. It sets the control panel height, creates a new
	 * content pane, packs the frame, centers it on the screen, sets the default
	 * close operation and makes it visible.
	 */

	private void updateFrame() {
		setControlPanelHeight();
		this.setContentPane(CreatePanel());
		this.pack();
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - getHeight()) / 2);
		this.setLocation(x, y);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);

	}

	/*********************************************************************************************************
	 **********************************************************************************************************
	 **********************************************************************************************************
	 **********************************************************************************************************
	 */

	/*
	 * Create main program
	 */

	public static void main(String[] args) {
		ArenaGUI gui = new ArenaGUI();
		gui.setVisible(true);
	}
}

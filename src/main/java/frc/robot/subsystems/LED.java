package frc.robot.subsystems;

import edu.wpi.first.wpilibj.util.Color;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;

public final class LED implements Subsystem {

	private static class ColorNode {
		ColorNode nextNode = null;
		ColorNode prevNode = null;
		Color value;

		public ColorNode(Color value) {
			this.value = value;
		}

		void setNext(ColorNode next) {
			this.nextNode = next;
			next.prevNode = this;
		}
	}

	private static class ColorCycle {
		private ColorNode off = new ColorNode(Color.kGray);
		private ColorNode red = new ColorNode(Color.kRed);
		private ColorNode orange = new ColorNode(Color.kOrange);
		private ColorNode yellow = new ColorNode(Color.kYellow);
		private ColorNode green = new ColorNode(Color.kGreen);
		private ColorNode blue = new ColorNode(Color.kBlue);
		private ColorNode purple = new ColorNode(Color.kPurple);
        private ColorNode magenta = new ColorNode(Color.kMagenta); 
		private ColorNode current;

		private ColorCycle() {
			off.setNext(orange);
			purple.setNext(yellow);
			yellow.setNext(magenta);
            magenta.setNext(off); 
			current = off;
		}

		
		public void cycle(boolean forward) {
			current = forward ? current.nextNode : current.prevNode;
		}

		public Color getColorCode() {
			return current.value;
		}
	}

	private static LED instance;

	private final AddressableLED ledStrip;
	private final AddressableLEDBuffer ledBuffer;
	// private final Spark blinkin;

	private ColorCycle colorCycle;
	private final int LENGTH = 39;

	private LED() {
		ledStrip = new AddressableLED(0);
		ledBuffer = new AddressableLEDBuffer(LENGTH);
		

		// blinkin = new Spark(0);

		ledStrip.setLength(ledBuffer.getLength());

		setStripColor(colorCycle.getColorCode());
		ledStrip.start();
		sendColorToShuffleboard();
	}

	public static LED getInstance() {
		if (instance == null) {
			instance = new LED();
		}
		return instance;
	}

	public void cycleColor(boolean forward) {
		colorCycle.cycle(forward);
		setStripColor(colorCycle.getColorCode());
	}

	public Color getColor() {
		return colorCycle.getColorCode();
	}
	
	
	public void sendColorToShuffleboard() {
		SmartDashboard.putData(new Sendable() {

			@Override
			public void initSendable(SendableBuilder builder) {
				builder.addStringProperty("ColorWidget", this::get, null);
			}

			String get() {
				Color color = getColor();
				return color.red + ", " + color.green + "," + color.blue;
			}

		});
	}

	

	public void setStripColor(Color color) {
		for (int i = 0; i < ledBuffer.getLength(); i++) {
			ledBuffer.setLED(i, color);
		}
		ledStrip.setData(ledBuffer);

		
	}
}

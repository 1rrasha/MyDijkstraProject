package application;

import javafx.geometry.Insets;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

public class Capitals {
	private static final double WIDTH = 1000;
	private static final double HEIGHT = 500;

	private String name;
	private double x;
	private double y;
	private double longitude;
	private double latitude;
	private Circle circle;

	// Default constructor
	public Capitals() {
		this.circle = new Circle();
	}

	// Constructor that parses data from a string line
	public Capitals(String line) {
		this(); // Call the default constructor to initialize the circle
		String[] arr = line.split(":");
		this.name = arr[0];
		this.latitude = Double.parseDouble(arr[1]);
		this.longitude = Double.parseDouble(arr[2]);

		configureCircle();
	}

	// Configures the Circle's properties and events
	private void configureCircle() {
		circle.setRadius(8); // Set the radius of the circle
		circle.setFill(Color.RED); // Set the color of the circle
		circle.setCenterX(getX()); // Set the x-coordinate
		circle.setCenterY(getY()); // Set the y-coordinate

		Tooltip tip = new Tooltip(this.getName());
		tip.setFont(new Font(16));
		tip.setStyle("-fx-background-color:grey;");
		Tooltip.install(circle, tip);

		circle.setOnMouseClicked(this::handleCircleClick);
	}

	// Handles the Circle's click event
	private void handleCircleClick(MouseEvent event) {
		if (Main.numOfP == 0) {
			circle.setFill(Color.BLUE); // Change color on first click
		} else if (Main.numOfP == 1) {
			circle.setFill(Color.GREEN); // Change color on second click
		}
		Main.numOfP += 1;

		if (Main.numOfP == 2) {
			Main.lock();
		}

	}

	// Getters and setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public Circle getCircle() {
		return circle;
	}

	public void setCircle(Circle circle) {
		this.circle = circle;
	}

	// method calculates the x-coordinate of the capital city on the map. It uses
	// the longitude of the capital city to determine its position relative to the
	// left edge of the map
	public double getX() {
		return ((longitude + 180) / 360 * WIDTH);
	}

	public void setX(double x) {
		this.x = x;
	}

	// calculates the y-coordinate of the capital city on the map.
	// It uses the latitude of the capital city to determine its position relative
	// to the top edge of the map
	public double getY() {
		return (HEIGHT - (latitude + 90) / 180 * HEIGHT);
	}

	public void setY(double y) {
		this.y = y;
	}
}

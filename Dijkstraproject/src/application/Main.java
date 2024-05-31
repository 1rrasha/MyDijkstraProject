package application;

/**
 * This application demonstrates a Dijkstra's algorithm implementation on a world map.
 * Users can select source and target capitals either by clicking on the map or using combo boxes.
 * The shortest path between the selected capitals is then displayed along with the distance.
 * BY:Rasha Mansour-1210773
 */
import java.io.File;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.shape.Circle;

public class Main extends Application {
	public static File file;
	static ComboBox<String> scourseText = new ComboBox<String>();
	static ComboBox<String> targetText = new ComboBox<String>();
	static int numOfP = 0;
	static Pane pane2 = new Pane();
	private Alert error = new Alert(AlertType.ERROR);

	ArrayList<Path> Tble = new ArrayList<Path>();
	ObservableList<Path> data = FXCollections.observableArrayList(Tble);
	static ArrayList<vertex> Capitals = new ArrayList<>();
	private boolean fileRead = false; // Flag to indicate whether the file has been read

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Rasha Project3");
		Image m = new Image("World-Map.jpg");
		ImageView image = new ImageView(m);
		image.setFitHeight(519);
		image.setFitWidth(1002);
		pane2.getChildren().add(image);

		primaryStage.getIcons().add(new Image("location.png"));
		Label title = new Label("DIJKSTRA WORLD MAP PEOJECT");
		title.setFont(Font.font("Arial", FontWeight.BOLD, 30)); // Change the font family and size
		title.setStyle("-fx-text-fill: white;"); // Set the text color to white
		title.setPadding(new Insets(15));
		file = new File("capitals.txt");
		// Check if the file has been read before reading it again
		if (!fileRead) {
			readFile(file);
			fileRead = true; // Set the flag to true after reading the file
		}

		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(10));
		pane.setTop(title);
		BorderPane.setAlignment(title, Pos.CENTER);

		pane.setStyle("-fx-background-color: linear-gradient(to right, #000000, #333333);");

		Label choose = new Label("Please Choose 2 Capitals:");
		choose.setPadding(new Insets(15));

		scourseText.setOnAction(e -> {
			String selectedSource = scourseText.getValue();
			String selectedTarget = targetText.getValue();

			for (vertex cap : Capitals) {
				Capitals capital = cap.getCapital();
				if (capital.getName().equals(selectedSource)) {
					capital.getCircle().setFill(Color.GREEN);
					if (selectedTarget != null && capital.getName().equals(selectedTarget)) {
						capital.getCircle().setFill(Color.BLUE);
					}
				} else {
					if (selectedTarget != null && capital.getName().equals(selectedTarget)) {
						capital.getCircle().setFill(Color.BLUE);
					} else {
						capital.getCircle().setFill(Color.RED);
					}
				}
			}
		});

		targetText.setOnAction(e -> {
			String selectedSource = scourseText.getValue();
			String selectedTarget = targetText.getValue();

			for (vertex country : Capitals) {
				Capitals capital = country.getCapital();
				if (capital.getName().equals(selectedTarget)) {
					capital.getCircle().setFill(Color.BLUE);
					if (selectedSource != null && capital.getName().equals(selectedSource)) {
						capital.getCircle().setFill(Color.GREEN);
					}
				} else {
					if (selectedSource != null && capital.getName().equals(selectedSource)) {
						capital.getCircle().setFill(Color.GREEN);
					} else {
						capital.getCircle().setFill(Color.RED);
					}
				}
			}
		});

		HBox hx = new HBox(10);
		hx.setAlignment(Pos.CENTER);
		hx.setPadding(new Insets(3));

		IconedTextFieled(choose, hx);
		HBox h = new HBox(choose, hx);
		h.setAlignment(Pos.CENTER);

		Label scourse = new Label("Sourse :");
		scourse.setPadding(new Insets(7));
		scourseText.setMinWidth(150);
		for (int i = 0; i < Capitals.size(); i++) {
			scourseText.getItems().add(Capitals.get(i).getCapital().getName());
		}

		IconedTextFieled(scourse, scourseText);
		HBox h1 = new HBox(scourse, scourseText);
		h1.setAlignment(Pos.CENTER);

		Label target = new Label("Target :");
		target.setPadding(new Insets(7));
		for (int i = 0; i < Capitals.size(); i++) {
			targetText.getItems().add(Capitals.get(i).getCapital().getName());
		}
		targetText.setMinWidth(150);
		IconedTextFieled(target, targetText);

		HBox h2 = new HBox(target, targetText);
		h2.setAlignment(Pos.CENTER);

		Button run = new Button("Run");
		Button reset = new Button("Clear");

		HBox butBox = new HBox(20, run, reset);
		butBox.setAlignment(Pos.CENTER);
		icons(reset);
		icons(run);
		butoonEffect(reset);
		butoonEffect(run);

		Label path = new Label("Path :");
		path.setPadding(new Insets(7));
		path.setMinHeight(200);
		path.setPadding(new Insets(7));
		TableView<Path> pathText = new TableView<Path>();

		TableColumn<Path, String> scource = new TableColumn<Path, String>("Source");
		scource.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getSource()));
		scource.setMaxWidth(80);
		TableColumn<Path, String> Target = new TableColumn<Path, String>("Target");
		Target.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getTarget()));
		Target.setMaxWidth(80);
		TableColumn<Path, Double> Distance = new TableColumn<Path, Double>("Distance");
		Distance.setCellValueFactory(p -> new SimpleDoubleProperty(p.getValue().getDistance()).asObject());

		Distance.setMaxWidth(90);

		pathText.getColumns().addAll(scource, Target, Distance);

		pathText.setMaxSize(250, 200);
		pathText.setItems(data);

		IconedTextFieled(path, new Node() {
		});

		HBox h3 = new HBox(path, pathText);
		h3.setAlignment(Pos.CENTER);

		Label distance = new Label("Distance :");
		distance.setPadding(new Insets(7));
		TextField distanceText = new TextField();

		IconedTextFieled(distance, distanceText);
		HBox h4 = new HBox(distance, distanceText);
		h4.setAlignment(Pos.CENTER);

		VBox v = new VBox(30, h, h1, h2, butBox);
		v.setAlignment(Pos.CENTER);
		v.setPadding(new Insets(10));
		icons(v);

		VBox v1 = new VBox(30, h3, h4);
		v1.setAlignment(Pos.CENTER);
		v1.setPadding(new Insets(10));
		icons(v1);

		VBox mix = new VBox(10, v, v1);
		mix.setAlignment(Pos.CENTER);

		VBox Vmap = new VBox(pane2);
		Vmap.setAlignment(Pos.CENTER);

		HBox mainBox = new HBox(20, mix, Vmap);
		mainBox.setAlignment(Pos.CENTER);

		pane.setCenter(mainBox);

		reset.setOnAction(l -> {
			pane2.getChildren().clear();
			targetText.getSelectionModel().select(null);
			scourseText.getSelectionModel().select(null);
			distanceText.setText("");
			data.clear();
			Tble.clear();
			numOfP = 0; // Reset the numOfP variable

			pane2.getChildren().add(image);
			for (vertex cou : Capitals) {
				cou.getCapital().getCircle().setFill(Color.RED);
				cou.getCapital().getCircle().setOnMouseClicked(null);
				free();
			}

			for (int i = 0; i < Capitals.size(); i++) {
				Capitals.get(i).visited = false;
				Capitals.get(i).distance = Double.MAX_VALUE; // Reset distance to maximum value
				Capitals.get(i).previous = null;
			}

			addPoint();
		});

		run.setOnAction(e -> {
			vertex vertx1 = null;
			vertex vertx2 = null;
			String s1 = scourseText.getValue();
			System.out.println(s1);
			String s2 = targetText.getValue();
			System.out.println(s2);

			for (int i = 0; i < Capitals.size(); i++) {
				if (Capitals.get(i).getCapital().getName().equals(s1)) {
					vertx1 = Capitals.get(i);
				}
				if (Capitals.get(i).getCapital().getName().equals(s2)) {
					vertx2 = Capitals.get(i);
				}
			}

			if (vertx1 != null && vertx2 != null) {
				// Clear previous path drawn on the map
				pane2.getChildren().removeIf(node -> node instanceof Line);

				int i = drowLine(Dijekstra(vertx1, vertx2));
				if (i == 0)
					distanceText.setText("0");
				else if (i == 1)
					distanceText.setText(String.valueOf(vertx2.distance));
				data = FXCollections.observableArrayList(Tble);
				pathText.setItems(data);

				// Reset the numOfP variable to allow re-selection of capitals
				numOfP = 0;
			}
		});

		addPoint();

		Scene scene = new Scene(pane, 1535, 800);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	// method draws lines on the map to represent the shortest path between
	// vertices.
	// Time complexity: O(n)

	private int drowLine(vertex Destination) {
		if (Destination == null) {
			error.setContentText("No path");
			error.show();
			return 0;
		} else {
			List<vertex> p = new ArrayList<>();
			for (vertex v = Destination; v != null; v = v.previous) {
				System.out.print("-->" + v.capital.getName() + " ");
				p.add(v);
			}
			System.out.println();

			// Reverse the path to get it from start to destination
			Collections.reverse(p);
			if (p.size() >= 1) {
				for (int i = 1; i < p.size(); i++) {
					double d = Distance(p.get(i - 1), p.get(i));
					Tble.add(new Path(d, p.get(i - 1).getCapital().getName(), p.get(i).getCapital().getName()));
				}
			}

			if (p.size() <= 1) {
				error.setContentText("No path");
				error.show();
			}

			for (int i = 0; i < p.size() - 1; i++) {
				vertex u = p.get(i);
				vertex v = p.get(i + 1);

				if (i != 0 && i != p.size() - 1) {
					u.getCapital().getCircle().setFill(Color.YELLOW); // Change the circle color for intermediate points
				}

				Line line = new Line(u.capital.getX(), u.capital.getY(), v.capital.getX(), v.capital.getY());
				line.setStroke(Color.RED); // Setting the line color to red
				pane2.getChildren().add(line);
			}
		}
		return 1;
	}

	// method iterates through each element in the Countries list, which contains
	// vertices.O(n) which n is number of vertices in the Countries list.
	private void addPoint() {
		for (int i = 0; i < Capitals.size(); i++) {
			Circle circle = Capitals.get(i).getCapital().getCircle();
			circle.setCenterX(Capitals.get(i).getCapital().getX());
			circle.setCenterY(Capitals.get(i).getCapital().getY());
			circle.setOnMouseClicked(e -> {
				circle.setFill(Color.GREEN);
				String capitalName = ((Label) pane2.getChildren().get(pane2.getChildren().indexOf(circle) + 1))
						.getText(); // Get the capital name associated with the clicked circle
				if (numOfP == 0) {
					scourseText.getSelectionModel().select(capitalName);
				} else if (numOfP == 1) {
					targetText.getSelectionModel().select(capitalName);
				}
				numOfP += 1;
				if (numOfP == 2) {
					lock();
				}
			});
			pane2.getChildren().add(circle);

			// Add label for capital name
			Label capitalLabel = new Label(Capitals.get(i).getCapital().getName());
			capitalLabel.setLayoutX(Capitals.get(i).getCapital().getX() + 10); // Adjust position as needed
			capitalLabel.setLayoutY(Capitals.get(i).getCapital().getY() - 10);
			capitalLabel.setTextFill(Color.WHITE); // Set label text color
			pane2.getChildren().add(capitalLabel);
		}
	}

	private void IconedTextFieled(javafx.scene.Node l, javafx.scene.Node t) {
		l.setStyle("-fx-border-color: #ffffff;" + "-fx-font-size: 14;\n" + "-fx-border-width: 2;"
				+ "-fx-border-radius: 10;" + "-fx-font-weight: Bold;\n" + "-fx-background-color:#f5f5f5;"
				+ "-fx-background-radius: 10");

		t.setStyle("-fx-border-radius: 10;\n" + "-fx-font-size: 14;\n" + "-fx-font-family: Arial;\n"
				+ "-fx-font-weight: Bold;\n" + "-fx-background-color: #ffffff;\n" + "-fx-border-color: #ffffff;\n"
				+ "-fx-border-width:  2;" + "-fx-text-fill: #000000;" + "-fx-background-radius: 10");
	}

	private void icons(Node l) {
		l.setStyle("-fx-border-radius: 50 50 50 50;\n" + "-fx-font-size: 14;\n" + "-fx-font-family: Arial;\n"
				+ "-fx-font-weight: Bold;\n" + "-fx-background-color: transparent;\n" + "-fx-border-color: #ffffff;\n"
				+ "-fx-border-width:  3.5;" + "-fx-background-color: linear-gradient(to right, #000000, #333333);\n"
				+ "-fx-background-radius: 50 50 50 50");
	}

	private void butoonEffect(Node b) {
		b.setOnMouseMoved(e -> {
			b.setStyle("-fx-border-radius: 25 25 25 25;\n" + "-fx-font-size: 15;\n"
					+ "-fx-font-family: Times New Roman;\n" + "-fx-font-weight: Bold;\n" + "-fx-text-fill: #CE2029;\n"
					+ "-fx-background-color: #d8d9e0;\n" + "-fx-border-color: #d8d9e0;\n" + "-fx-border-width:  3.5;"
					+ "-fx-background-radius: 25 25 25 25");
		});
		b.setOnMouseExited(e -> {
			b.setStyle("-fx-border-radius: 25 25 25 25;\n" + "-fx-font-size: 15;\n"
					+ "-fx-font-family: Times New Roman;\n" + "-fx-font-weight: Bold;\n" + "-fx-text-fill: #f2f3f4;\n"
					+ "-fx-background-color: transparent;\n" + "-fx-border-color: #d8d9e0;\n"
					+ "-fx-border-width:  3.5;" + "-fx-background-radius: 25 25 25 25");
		});
	}

	public static void ButtonTheme(Button button) {
		button.setStyle("-fx-border-radius: 25 25 25 25;\n" + "-fx-font-size: 14;\n"
				+ "-fx-font-family: Times New Roman;\n" + "-fx-font-weight: Bold;\n"
				+ "-fx-background-color: #d8d9e0;\n" + "-fx-border-color: #d8d9e0;\n" + "-fx-border-width:  3.5;\n"
				+ "-fx-background-radius: 25 25 25 25");
		button.setOnMouseMoved(e -> {
			button.setStyle("-fx-border-radius: 25 25 25 25;\n" + "-fx-font-size: 14;\n"
					+ "-fx-font-family: Times New Roman;\n" + "-fx-font-weight: Bold;\n" + "-fx-text-fill: #ff6800;\n"
					+ "-fx-background-color: #d8d9e0;\n" + "-fx-border-color: #d8d9e0;\n" + "-fx-border-width:  3.5;\n"
					+ "-fx-background-radius: 25 25 25 25");
		});
		button.setOnMouseExited(e -> {
			button.setStyle("-fx-border-radius: 25 25 25 25;\n" + "-fx-font-size: 14;\n"
					+ "-fx-font-family: Times New Roman;\n" + "-fx-font-weight: Bold;\n"
					+ "-fx-background-color: #f6f6f6;\n" + "-fx-border-color: #d8d9e0;\n" + "-fx-border-width:  3.5;\n"
					+ "-fx-background-radius: 25 25 25 25");
		});
	}

	public static void lock() {
		try {
			for (int i = 0; i < Capitals.size(); i++) {
				Capitals.get(i).getCapital().getCircle().setDisable(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void free() {
		try {
			for (int i = 0; i < Capitals.size(); i++) {
				Capitals.get(i).getCapital().getCircle().setDisable(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// This method implements Dijkstra's algorithm to find the shortest path between
	// a source and a destination vertex.
	// Time complexity: O((V+E)logV)
	public vertex Dijekstra(vertex Source, vertex Destination) {// O(n) = (V(logV+E))
		Source.distance = 0; // Set the distance of the source vertex to 0. This is because the distance from
								// the source vertex to itself is 0
		if (Source == Destination) {// source and destination vertices are the same
			return null;
		}

		MyPriorityQueue<vertex> pq = new MyPriorityQueue<>(new Comparator<vertex>() { // Log V
			@Override
			public int compare(vertex v1, vertex v2) {
				return Double.compare(v1.distance, v2.distance);
			}
		});

		pq.add(Source);

		while (!pq.isEmpty()) { // V
			// Continue until the priority queue is empty(all vertices have been processed)
			vertex u = pq.poll(); //// Log V

			u.visited = true;
			if (u.capital.getName().equals(Destination.getCapital().getName())) {// shortest path to the destination has
																					// been found
				break;
			}
			for (edges e : u.getE()) { // E
				vertex v = e.desination;
				if (!v.visited) {
					double weight = e.weight;
					double distanceThroughU = u.distance + weight;
					if (distanceThroughU < v.distance) {// if this distance is shorter than the current known distance
						v.distance = distanceThroughU;
						v.previous = u;
						pq.add(v);// Add v to the priority queue if it hasn't been visited yet.
					}
				}
			}
		}

		return Destination;
	}

	// method reads data from a file and populates the list of vertices and edges.
	// Time complexity: O(numCounter + numEdge)
	public void readFile(File file) {
		try {
			Scanner sc = new Scanner(file);
			String[] l = sc.nextLine().split(":");
			int numCounter = Integer.parseInt(l[0]);
			int numEdge = Integer.parseInt(l[1]);
			int count = 0;
			int num = 0;

			while (sc.hasNextLine() && count < numCounter - 1) {
				String line = sc.nextLine();
				System.out.println(line);
				vertex ver = new vertex(new Capitals(line), num++);
				Capitals.add(ver);
				count++;
			}

			count = 0;
			while (sc.hasNextLine() && count < numEdge) {
				String tokens[] = sc.nextLine().split(":");
				for (int i = 0; i < Capitals.size(); i++) {
					if (Capitals.get(i).getCapital().getName().compareToIgnoreCase(tokens[0]) == 0) {
						for (int j = 0; j < Capitals.size(); j++) {
							if (Capitals.get(j).getCapital().getName().compareToIgnoreCase(tokens[1]) == 0) {
								Capitals.get(i).e.add(new edges(Capitals.get(i), Capitals.get(j),
										Distance(Capitals.get(i), Capitals.get(j))));
							}
						}
					}
				}
				count++;
			}

			sc.close();
		} catch (FileNotFoundException t) {
			System.out.println(t);
		}
	}

	// method calculates the distance between two vertices using their latitude and
	// longitude.
	// O(1)
	public double Distance(vertex a, vertex b) {

		final int EARTH_RADIUS = 6371;
		double lat1Rad = Math.toRadians(a.getCapital().getLatitude());
		double lat2Rad = Math.toRadians(b.getCapital().getLatitude());
		double deltaLat = Math.toRadians(b.getCapital().getLatitude() - a.getCapital().getLatitude());
		double deltaLon = Math.toRadians(b.getCapital().getLongitude() - a.getCapital().getLongitude());

		double dis = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2)
				+ Math.cos(lat1Rad) * Math.cos(lat2Rad) * Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
		double c = 2 * Math.atan2(Math.sqrt(dis), Math.sqrt(1 - dis));

		return EARTH_RADIUS * c;

	}

	public static void main(String[] args) {
		launch(args);
	}
}

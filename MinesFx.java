package mines; /*mines package*/

//import IOException library
import java.io.IOException;

//import all javaFX libraries
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

//define MineFX class that extends from Application.
//The class have height, width, mines quantity, victories
//quantity, loses quantity, stage, game (mines), board,
//image for winning, image for losing, image for mine,
//image for flag, media for losing (Sarit Hadad - "Yala
//Lech Habaita Moti"), and media for winning (Sarit Hadad -
//"Ata Totach"). In addition, the class have main method,
//method for starting the game, method for making board to
//the game, method for handling pressing place situation,
//method for making banner, method for making reset of the
//game, and method for images of buttons
public class MinesFx extends Application {

	// define height, width, mines quantity, victories, loses quantity
	private int Height = 10, Width = 10, mines = 10, vic, los;
	// define the controller
	private Controller controller;
	// define the stage
	private Stage stage;
	// define the game (mines)
	private Mines game;
	// define and create the board of the game
	private GridPane board = new GridPane();
	// define and create winning image
	private final Image winImage = new Image(getClass().getResourceAsStream("winer.png"), 96, 96, false, false);
	// define and create losing image
	private final Image loseImage = new Image(getClass().getResourceAsStream("shark.png"), 96, 96, false, false);
	// define and create mine image
	private final Image mineImage = new Image(getClass().getResourceAsStream("mokesh.png"), 20, 20, false, false);
	// define and create flag image
	private final Image flagImage = new Image(getClass().getResourceAsStream("Flag.png"), 20, 20, false, false);
	// define and create losing sound
	private final Media loseSound = new Media(getClass().getResource("lech habita.mp3").toString());
	// define and create winning sound
	private final Media winSound = new Media(getClass().getResource("ata totach.mp3").toString());

	// define main method
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	// define method for starting the game
	public void start(Stage stage) {
		this.stage = stage;
		// define and create the loading
		FXMLLoader loading = new FXMLLoader();
		// define and create the
		// HBox for the fxml
		HBox BOX = new HBox();
		// load the fxml itself
		loading.setLocation(getClass().getResource("shulemokshim.fxml"));
		// try and catch exception
		try {
			BOX = loading.load();
		} catch (IOException e) {
			e.printStackTrace();
		} // עצרתי פה
			// activate the controller;
		controller = loading.getController();
		// set for the controller the height, the
		// width and the mines of the game
		controller.getHeight().setText(String.valueOf(Height));
		controller.getWidth().setText(String.valueOf(Width));
		controller.getMines().setText((String.valueOf(mines)));
		// create new game
		game = new Mines(Height, Width, mines);
		// make reset to the game
		MakeReset();
		// make board for the game
		MakeBoard();
		// initialize background size
		BackgroundSize backgroundSize = new BackgroundSize(140, 200, true, true, true, false);
		// initialize background picture
		BackgroundImage pic = new BackgroundImage(new Image("mines/back1.png"), BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
		// ser background to the HBox (BOX)
		BOX.setBackground(new Background(pic));
		// add the board
		BOX.getChildren().add(board);
		// create the first scene
		Scene first_scene = new Scene(BOX);
		// set the scene
		stage.setScene(first_scene);
		// set the title of the scene
		stage.setTitle("welcome to minesweeper game bodkim shelano");
		// show the stage
		stage.show();
	}

	// define method that make new board
	private void MakeBoard() {
		// delete the previous
		// data of the old board
		board.getChildren().clear();
		// set aligment of the board
		board.setAlignment(Pos.CENTER);
		// create buttons for the new board
		for (int i = 0; i < Height; i++) {
			for (int j = 0; j < Width; j++) {
				// create new button
				Button b = new Button();
				// set image for the button
				imageToButton(b, i, j);
				// set size of the button
				b.setPrefSize(55, 55);
				// set the music handle button
				b.setOnMouseClicked(new PlacePressed(i, j));
				// add the button to the board
				board.add(b, j, i);
			}
		}
	}

	// internal class for handling a button press
	class PlacePressed implements EventHandler<MouseEvent> {
		// coordinate for the place
		private int x;
		private int y;

		// PlacePressed constructor
		private PlacePressed(int i, int j) {
			this.x = i;
			this.y = j;
		}

		@Override
		// define method that handle
		// left side pressing situation
		public void handle(MouseEvent event) {
			// check the press itself
			if (event.getButton() == MouseButton.PRIMARY) {
				// handle mine situation
				if (!game.open(x, y)) {
					// show the whole board
					game.setShowAll(true);
					// increase the losing counter
					los++;
					// present the lose to the player
					// and the percents of victories
					// (and appropriate sound)
					String str = "lost: " + (int) (((double) vic / (double) (vic + los)) * 100)
							+ "% victories \n Please wait for the song to end \n before another game";
					MakeBanner(str, loseImage, loseSound);
				}
				// handle winning situation
				else if (game.isDone()) {
					// show the whole board
					game.setShowAll(true);
					// increase the victories counter
					vic++;
					// present the victory to the player
					// and the percents of victories
					// (and appropriate sound)
					String str = "winer: " + (int) (((double) vic / (double) (vic + los)) * 100)
							+ "% victories \n Please wait for the song to end \n before another game";
					MakeBanner(str, winImage, winSound);
				}
				// handle right side pressing
			} else if (event.getButton() == MouseButton.SECONDARY) {
				// put a flag on the specific place
				game.toggleFlag(x, y);
			}
			// updating the board and clear it
			MakeBoard();
		}
	}

	// define method which create banner
	private void MakeBanner(String msg, Image img, Media sound) {
		// define and create new stage
		Stage newStage = new Stage();
		// define and create new virtual box
		VBox ban = new VBox();
		// define and create new message
		Label msg1 = new Label();
		// define and create label for the picture
		Label pLabel = new Label();
		// define and create new media player
		MediaPlayer musica = new MediaPlayer(sound);
		// set width of the banner
		ban.setPrefWidth(230);
		// create message label
		msg1.setText(msg);
		// set picture for the label
		pLabel.setGraphic(new ImageView(img));
		// add picture label and children
		// label to the banner
		ban.getChildren().addAll(msg1, pLabel);
		// set alignment of the virtual box
		ban.setAlignment(Pos.CENTER);
		// create new scene with new banner
		Scene newScene = new Scene(ban);
		// set the new scene
		// to the new stage
		newStage.setScene(newScene);
		// play music
		musica.play();
		// show new stage
		newStage.show();
	}

	// define method that make reset of the game
	public void MakeReset() {
		// create reset button
		Button ResetButton = controller.getButton();

		// create inner event class
		// (the class have just handle method)
		class Event implements EventHandler<ActionEvent> {

			@Override
			// define Event constructor
			public void handle(ActionEvent event) {
				// create height
				Height = Integer.parseInt(controller.getHeight().getText());
				// create width
				Width = Integer.parseInt(controller.getWidth().getText());
				// create mines
				mines = Integer.parseInt(controller.getMines().getText());
				// create mines game (minimum
				// is for situation the mines
				// quantity bigger than multiply
				// between height and width)
				mines = Math.min(mines, Height * Width);
				// starting the stage
				start(stage);
			}
		}
		// create new event using the button
		ResetButton.setOnAction(new Event());
	}

	// define method that makes buttons (strings
	// of them) to pictures
	private void imageToButton(Button b, int x, int y) {
		// create the result
		String result = game.get(x, y);
		// create bomb picture from "X"
		if (result == "X")
			b.setGraphic(new ImageView(mineImage));
		// create flag picture from "F"
		else if (result == "F")
			b.setGraphic(new ImageView(flagImage));
		// don't create any picture
		else
			b.setText(game.get(x, y));
	}
}
package mines;//mains packege

//import JavaFX libraries
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

//define controller class
public class Controller {

	@FXML
	// define height
	private TextArea height;

	@FXML
	// define mines
	private TextArea mines;

	@FXML
	// define reset button
	private Button resetButton;

	@FXML
	// define width
	private TextArea width;

	// define method that
	// return the mines
	public TextArea getMines() {
		return mines;
	}

	// define method that
	// return the width
	public TextArea getWidth() {
		return width;
	}

	// define method that
	// return the height
	public TextArea getHeight() {
		return height;
	}

	// define method that
	// return the button
	public Button getButton() {
		return resetButton;
	}
}
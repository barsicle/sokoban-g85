package gui;

import java.io.IOException;
import domein.DomeinController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * De controller-klasse van de GUI. Start de applicatie en wisselt tussen de
 * verschillende schermen. Laad ook de nodige images.
 * 
 * @author g85
 */
public class GuiController extends Application {
	private static Scene scene;
	protected DomeinController dc;

	public GuiController() {
		dc = new DomeinController();
	}

	/**
	 * Start de GUI.
	 * 
	 * @param stage De stage waarop de scenes geladen worden.
	 * @throws IOException indien er problemen zijn met Input/Output.
	 */
	@Override
	public void start(Stage stage) throws IOException {
		stage.setMinHeight(800);
		stage.setMinWidth(1600);
		stage.setMaximized(true);
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TaalSelectScherm.fxml"));
		fxmlLoader.setController(new TaalSelectSchermController(this));
		scene = new Scene(fxmlLoader.load());
		stage.setScene(scene);
		stage.show();
	}

	protected void switchScherm(Scherm scherm) {
		FXMLLoader fxmlLoader = null;
		switch (scherm) {
		case Hoofdscherm:
			fxmlLoader = new FXMLLoader(getClass().getResource("Hoofdscherm.fxml"));
			fxmlLoader.setController(new HoofdSchermController(this));
			break;
		case SpelMenuScherm:
			fxmlLoader = new FXMLLoader(getClass().getResource("SpelMenuScherm.fxml"));
			fxmlLoader.setController(new SpelMenuSchermController(this));
			break;
		case LoginScherm:
			fxmlLoader = new FXMLLoader(getClass().getResource("LoginScherm.fxml"));
			fxmlLoader.setController(new LoginSchermController(this));
			break;
		case RegistreerScherm:
			fxmlLoader = new FXMLLoader(getClass().getResource("RegistreerScherm.fxml"));
			fxmlLoader.setController(new RegistreerSchermController(this));
			break;
		case SpelScherm:
			fxmlLoader = new FXMLLoader(getClass().getResource("SpelScherm.fxml"));
			fxmlLoader.setController(new SpelSchermController(this));
			break;
		case SpelSelectScherm:
			fxmlLoader = new FXMLLoader(getClass().getResource("SpelSelectScherm.fxml"));
			fxmlLoader.setController(new SpelSelectSchermController(this));
			break;
		case TaalSelectScherm:
			fxmlLoader = new FXMLLoader(getClass().getResource("TaalSelectScherm.fxml"));
			fxmlLoader.setController(new TaalSelectSchermController(this));
			break;
		case SpelCreatieScherm:
			fxmlLoader = new FXMLLoader(getClass().getResource("SpelCreatieScherm.fxml"));
			fxmlLoader.setController(new SpelCreatieSchermController(this));
			break;
		case SpelbordCreatieScherm:
			fxmlLoader = new FXMLLoader(getClass().getResource("SpelbordCreatieScherm.fxml"));
			fxmlLoader.setController(new SpelbordCreatieSchermController(this));
			break;
		default:
		}
		
		try {
			scene.setRoot(fxmlLoader.load());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	protected void afsluiten() {
		System.exit(0);
	}

}

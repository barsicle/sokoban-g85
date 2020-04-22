package gui;

import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.image.ImageView;
import static gui.ImageFactory.SoortImage.*;

public class AlertFactory {

	public static Alert createAlert(Alert.AlertType type, String title, String header, String content) {
		Alert alert = new Alert(type);
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().add(AlertFactory.class.getResource("/resources/css/alerts.css").toExternalForm());
		dialogPane.getStyleClass().add("myAlert");

		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		
		if (type == Alert.AlertType.ERROR) {
		final ImageView DIALOG_HEADER_ICON = new ImageView(ImageFactory.geefImage(BOWSER));
	    DIALOG_HEADER_ICON.setFitHeight(150);
	    DIALOG_HEADER_ICON.setFitWidth(150);
	    dialogPane.setGraphic(DIALOG_HEADER_ICON);
		}
		
		if (type == Alert.AlertType.INFORMATION || type == Alert.AlertType.CONFIRMATION) {
		final ImageView DIALOG_HEADER_ICON = new ImageView(ImageFactory.geefImage(MARIO));
	    DIALOG_HEADER_ICON.setFitHeight(100);
	    DIALOG_HEADER_ICON.setFitWidth(100);
	    dialogPane.setGraphic(DIALOG_HEADER_ICON);
		}
		return alert;
	}
	
	public static Alert createAlert(Alert.AlertType type, String title, String content) {
		return createAlert(type,title,null,content);
	}
}

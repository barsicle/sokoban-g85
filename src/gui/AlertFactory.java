package gui;

import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;

public class AlertFactory {

	public static Alert createAlert(Alert.AlertType type, String title, String header, String content) {
		Alert alert = new Alert(type);
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().add(AlertFactory.class.getClass().getResource("/resources/css/alerts.css").toExternalForm());
		dialogPane.getStyleClass().add("myAlert");
		
		alert.getDialogPane().setMinHeight(500);
		alert.getDialogPane().setMinWidth(500);
		
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		
		return alert;
	}
	
	public static Alert createAlert(Alert.AlertType type, String title, String content) {
		return createAlert(type,title,null,content);
	}
}

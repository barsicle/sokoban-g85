module Project1 {
	requires javafx.base;
	requires javafx.graphics;
	requires javafx.controls;
	requires javafx.fxml;
	requires java.sql;
	
	opens gui to javafx.graphics, javafx.fxml;
}
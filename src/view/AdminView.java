package view;

import controller.AdminController;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jxl.read.biff.BiffException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class AdminView {
	private Stage stage = new Stage();
	private AdminMainPane adminMainPane;
		
	public AdminView(AdminController controller) throws IOException, BiffException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		stage.setTitle("ADMIN VIEW");
		stage.initStyle(StageStyle.UTILITY);
		stage.setX(680);
		stage.setY(20);
		Group root = new Group();
		Scene scene = new Scene(root, 650, 400);
		adminMainPane = new AdminMainPane();
		adminMainPane.prefHeightProperty().bind(scene.heightProperty());
		adminMainPane.prefWidthProperty().bind(scene.widthProperty());
		root.getChildren().add(adminMainPane);
		stage.setScene(scene);
		stage.sizeToScene();			
		stage.show();
		controller.setView(this);
	}

	public AdminMainPane getAdminMainPane() {
		return this.adminMainPane;
	}

}

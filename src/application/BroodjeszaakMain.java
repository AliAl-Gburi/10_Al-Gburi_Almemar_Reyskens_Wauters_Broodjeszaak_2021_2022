package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import jxl.read.biff.BiffException;
import view.AdminView;
import view.KitchenView;
import view.OrderView;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;


public class BroodjeszaakMain extends Application {
	@Override
	public void start(Stage primaryStage) throws IOException, BiffException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		AdminView adminView = new AdminView();
		OrderView orderView = new OrderView();
		KitchenView kitchenView = new KitchenView();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

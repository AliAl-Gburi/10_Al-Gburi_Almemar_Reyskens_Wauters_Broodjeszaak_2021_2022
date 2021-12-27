package application;
	
import controller.AdminController;
import controller.OrderViewController;
import javafx.application.Application;
import javafx.stage.Stage;
import jxl.read.biff.BiffException;
import model.BestelFacade;
import view.AdminView;
import view.KitchenView;
import view.OrderView;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;


public class BroodjeszaakMain extends Application {
	@Override
	public void start(Stage primaryStage) throws IOException, BiffException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		BestelFacade facade = new BestelFacade();
		OrderViewController orderViewController = new OrderViewController(facade);
		AdminController adminController = new AdminController(facade);
		AdminView adminView = new AdminView(adminController);

		OrderView orderView = new OrderView(orderViewController);
		adminController.initialize();
		orderViewController.initialize();

		KitchenView kitchenView = new KitchenView();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

package view;


import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import jxl.read.biff.BiffException;
import view.panels.SandwichOverviewPane;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class AdminMainPane extends BorderPane {
	public AdminMainPane() throws IOException, BiffException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
	    TabPane tabPane = new TabPane(); 	    
        //Tab spelVerloopTab = new Tab("Spelverloop");
        SandwichOverviewPane sandwichOverviewPane = new SandwichOverviewPane();
        Tab broodjesTab = new Tab("Broodjes/Beleg",sandwichOverviewPane);
        Tab instellingTab = new Tab("Instellingen");
        Tab statistiekTab = new Tab("Statistieken");
        //tabPane.getTabs().add(spelVerloopTab);
        tabPane.getTabs().add(broodjesTab);
        tabPane.getTabs().add(statistiekTab);
        tabPane.getTabs().add(instellingTab);
        this.setCenter(tabPane);
	}
}

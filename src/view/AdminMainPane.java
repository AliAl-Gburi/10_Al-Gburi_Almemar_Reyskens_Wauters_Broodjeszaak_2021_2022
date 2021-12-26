package view;


import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import jxl.read.biff.BiffException;
import view.panels.SandwichOverviewPane;
import view.panels.SettingsPane;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class AdminMainPane extends BorderPane {
    private SandwichOverviewPane sandwichOverviewPane;
    private SettingsPane settingsPane;

	public AdminMainPane() throws IOException, BiffException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
	    TabPane tabPane = new TabPane(); 	    
        //Tab spelVerloopTab = new Tab("Spelverloop");
        sandwichOverviewPane = new SandwichOverviewPane();
        settingsPane = new SettingsPane();
        Tab broodjesTab = new Tab("Broodjes/Beleg",sandwichOverviewPane);
        Tab instellingTab = new Tab("Instellingen", settingsPane);
        Tab statistiekTab = new Tab("Statistieken");
        //tabPane.getTabs().add(spelVerloopTab);
        tabPane.getTabs().add(broodjesTab);
        tabPane.getTabs().add(statistiekTab);
        tabPane.getTabs().add(instellingTab);
        this.setCenter(tabPane);
	}

    public SandwichOverviewPane getSandwichOverviewPane() {
        return this.sandwichOverviewPane;
    }
    public SettingsPane getSettingsPane() {
        return this.settingsPane;
    }
}

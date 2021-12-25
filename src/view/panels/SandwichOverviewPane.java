package view.panels;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import jxl.read.biff.BiffException;
import model.BelegSoort;
import model.Broodje;
import model.database.BelegDatabase;
import model.database.BroodjesDatabase;
import model.database.loadSaveStrategies.LoadSaveStrategyEnum;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;


public class SandwichOverviewPane extends GridPane{
	//private TableView<Speler> table;
	private TableView<Broodje> broodjestable;
	private TableView<BelegSoort> belegtable;

	
	
	public SandwichOverviewPane() throws IOException, BiffException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5);
        this.setHgap(5);        
		this.add(new Label("Broodjes:"), 0, 0, 1, 1);
		this.add(new Label("Belegen:"), 1, 0, 1, 1);


		broodjestable = new TableView<Broodje>();
		belegtable = new TableView<BelegSoort>();
		this.add(broodjestable, 0, 1);
		this.add(belegtable, 1, 1);

		//broodje tableview maken

		TableColumn<Broodje, String> broodjeNaam = new TableColumn<Broodje, String>("Broodje naam");
		broodjeNaam.setMinWidth(100);
		broodjeNaam.setCellValueFactory(new PropertyValueFactory<Broodje, String>("broodjesnaam"));
		TableColumn<Broodje, Double> broodjePrijs = new TableColumn<Broodje, Double>("Prijs");
		broodjePrijs.setMinWidth(100);
		broodjePrijs.setCellValueFactory(new PropertyValueFactory<Broodje, Double>("verkoopprijs"));
		TableColumn<Broodje, Integer> broodjeVoorraad = new TableColumn<Broodje, Integer>("Voorraad");
		broodjeVoorraad.setMinWidth(100);
		broodjeVoorraad.setCellValueFactory(new PropertyValueFactory<Broodje, Integer>("voorraad"));
		broodjestable.getColumns().addAll(broodjeNaam, broodjePrijs, broodjeVoorraad);

		//beleg tablevire maken

		TableColumn<BelegSoort, String> belegNaam = new TableColumn<BelegSoort, String>("Beleg naam");
		belegNaam.setMinWidth(100);
		belegNaam.setCellValueFactory(new PropertyValueFactory<BelegSoort, String>("belegnaam"));
		TableColumn<BelegSoort, Double> belegPrijs = new TableColumn<BelegSoort, Double>("Prijs");
		belegPrijs.setMinWidth(100);
		belegPrijs.setCellValueFactory(new PropertyValueFactory<BelegSoort, Double>("verkoopprijs"));
		TableColumn<BelegSoort, Integer> belegVoorraad = new TableColumn<BelegSoort, Integer>("Voorraad");
		belegVoorraad.setMinWidth(100);
		belegVoorraad.setCellValueFactory(new PropertyValueFactory<BelegSoort, Integer>("voorraad"));
		belegtable.getColumns().addAll(belegNaam, belegPrijs, belegVoorraad);

	}

	public TableView<Broodje> getBroodjestable() {
		return this.broodjestable;
	}

	public TableView<BelegSoort> getBelegtable() {
		return this.belegtable;
	}


}

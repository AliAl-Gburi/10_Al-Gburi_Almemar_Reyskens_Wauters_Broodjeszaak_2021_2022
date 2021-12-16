package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.BelegSoort;
import model.database.BelegDatabase;
import model.database.BroodjesDatabase;
import model.Broodjes;


public class OrderView {
	private Stage stage = new Stage();
	private BroodjesDatabase broodjesDatabase = BroodjesDatabase.getBroodjesDatabase();
	private BelegDatabase belegDatabase = BelegDatabase.getDatabase();
		
	public OrderView(){			
		stage.setTitle("ORDER VIEW");
		stage.initStyle(StageStyle.UTILITY);
		stage.setX(20);
		stage.setY(20);
		Pane root = createNodeHierarchy();
		Scene scene = new Scene(root, 650, 650);			
		stage.setScene(scene);
		stage.sizeToScene();			
		stage.show();		
	}
	private Pane createNodeHierarchy () {
		VBox mainBox = new VBox(8);

		HBox broodjesknopen = new HBox(8);
		for (Broodjes broodjes: broodjesDatabase.getBroodjesList()
			 ) {
			Button broodjebtn = new Button(broodjes.getBroodjesnaam());
			broodjebtn.setDisable(true);
			broodjesknopen.getChildren().add(broodjebtn);
		}

		HBox belegKnopen = new HBox(8);
		for (BelegSoort beleg: belegDatabase.getBelegen()) {
			Button belegbtn = new Button(beleg.getBelegnaam());
			belegbtn.setDisable(true);
			belegKnopen.getChildren().add(belegbtn);
		}

		VBox buttons = new VBox(8);
		buttons.setPadding(new Insets(10));
		buttons.getChildren().addAll(broodjesknopen, belegKnopen);

		HBox topbox = new HBox(8);;
		topbox.setPadding(new Insets(10));

		Label volgnr = new Label("Volgnr: 0");
		Button nieuwbestelingbtn = new Button("Nieuwe Bestelling");

		ChoiceBox<String> kortings = new ChoiceBox<>();
		kortings.getItems().addAll("Goedkoopste broodje gratis", "50% aan alle bestellingen");
		topbox.getChildren().addAll(nieuwbestelingbtn, volgnr, kortings);

		Label aantalbroodjes = new Label("Aantal broodjes: 0");

		TableView<Broodjes> broodjesTableView = new TableView<>();
		broodjesTableView.setMaxWidth(500);
		broodjesTableView.setMaxHeight(400);
		TableColumn<Broodjes, String> broodjes = new TableColumn<Broodjes, String>("Broodjes");
		broodjes.setMinWidth(200);
		TableColumn<Broodjes, String> belegsoort = new TableColumn<Broodjes, String>("Beleg");
		belegsoort.setMinWidth(300);
		broodjesTableView.getColumns().addAll(broodjes, belegsoort);

		VBox rightBox = new VBox(8);
		Label selecteer = new Label("Selecteer lijn in lijst");
		Button voegzelf = new Button("Voeg zelfde broodje toe");
		Button verwijder = new Button("Verwijder broodje");

		VBox anbox = new VBox(8);
		anbox.setPadding(new Insets(184, 0, 0, 0));

		Button annuleer = new Button("Annuleer bestelling");
		anbox.getChildren().add(annuleer);
		rightBox.getChildren().addAll(selecteer, voegzelf, verwijder, anbox);

		HBox midsec = new HBox(8);
		midsec.setPadding(new Insets(10));
		midsec.getChildren().addAll(broodjesTableView, rightBox);

		HBox botsec = new HBox(8);
		botsec.setPadding(new Insets(10));
		Button afsluit = new Button("Afsluiten Bestelling");
		Label tebetalen = new Label("Te betalen: 0$");
		tebetalen.setPadding(new Insets(5));
		Button betaal = new Button("Betaal");
		Button naarKeuken = new Button("Naar Keuken");
		botsec.getChildren().addAll(afsluit, tebetalen, betaal, naarKeuken);


		mainBox.setPadding(new Insets(10));
		mainBox.getChildren().addAll(topbox, buttons, midsec, botsec);

		return mainBox;
	}
}

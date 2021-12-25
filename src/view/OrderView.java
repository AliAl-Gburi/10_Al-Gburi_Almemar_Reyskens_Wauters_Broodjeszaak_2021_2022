package view;

import controller.OrderViewController;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.BelegSoort;
import model.Bestellijn;
import model.Bestelling;
import model.database.BelegDatabase;
import model.database.BroodjesDatabase;
import model.Broodje;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class OrderView {
	private Stage stage = new Stage();
	private BroodjesDatabase broodjesDatabase = BroodjesDatabase.getBroodjesDatabase();
	private BelegDatabase belegDatabase = BelegDatabase.getDatabase();
	private Map<String, Button> broojebuttons;
	private Map<String, Button> belegButtons;
	private Button nieuwBestellingBtn;
	private TableView<Bestellijn> bestellijnTableView;
	private Label aantalBroodjes;
		
	public OrderView(OrderViewController controller){
		stage.setTitle("ORDER VIEW");
		stage.initStyle(StageStyle.UTILITY);
		stage.setX(20);
		stage.setY(20);
		Pane root = createNodeHierarchy();
		Scene scene = new Scene(root, 650, 650);			
		stage.setScene(scene);
		stage.sizeToScene();			
		stage.show();
		controller.setView(this);
	}
	private Pane createNodeHierarchy () {
		VBox mainBox = new VBox(8);
		broojebuttons = new TreeMap<>();
		belegButtons = new TreeMap<>();

		HBox broodjesknopen = new HBox(8);
		for (Broodje broodje : broodjesDatabase.getBroodjesList()
			 ) {
			Button broodjebtn = new Button(broodje.getBroodjesnaam());
			broojebuttons.put(broodje.getBroodjesnaam(), broodjebtn);
			broodjebtn.setDisable(true);
			broodjesknopen.getChildren().add(broodjebtn);
		}

		HBox belegKnopen = new HBox(8);
		for (BelegSoort beleg: belegDatabase.getBelegen()) {
			Button belegbtn = new Button(beleg.getBelegnaam());
			belegButtons.put(beleg.getBelegnaam(), belegbtn);
			belegbtn.setDisable(true);
			belegKnopen.getChildren().add(belegbtn);
		}

		VBox buttons = new VBox(8);
		buttons.setPadding(new Insets(10));
		buttons.getChildren().addAll(broodjesknopen, belegKnopen);

		HBox topbox = new HBox(8);;
		topbox.setPadding(new Insets(10));

		Label volgnr = new Label("Volgnr: 0");
		nieuwBestellingBtn = new Button("Nieuwe Bestelling");

		ChoiceBox<String> kortings = new ChoiceBox<>();
		kortings.getItems().addAll("Goedkoopste broodje gratis", "50% aan alle bestellingen");
		topbox.getChildren().addAll(nieuwBestellingBtn, volgnr, kortings);

		aantalBroodjes = new Label("Aantal broodjes: 0");
		aantalBroodjes.setPadding(new Insets(0, 0, 0, 12));

		bestellijnTableView = new TableView<Bestellijn>();
		bestellijnTableView.setMaxWidth(400);
		bestellijnTableView.setMaxHeight(400);
		TableColumn<Bestellijn, String> broodjes = new TableColumn<Bestellijn, String>("Broodjes");
		broodjes.setCellValueFactory(new PropertyValueFactory<Bestellijn, String>("naamBroodje"));
		broodjes.setMinWidth(100);
		TableColumn<Bestellijn, String> belegsoort = new TableColumn<Bestellijn, String>("Belegen");
		belegsoort.setCellValueFactory(new PropertyValueFactory<Bestellijn, String>("belegsoorten"));
		belegsoort.setMinWidth(300);
		bestellijnTableView.getColumns().addAll(broodjes, belegsoort);

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
		midsec.getChildren().addAll(bestellijnTableView, rightBox);

		HBox botsec = new HBox(8);
		botsec.setPadding(new Insets(10));
		Button afsluit = new Button("Afsluiten Bestelling");
		Label tebetalen = new Label("Te betalen: 0$");
		tebetalen.setPadding(new Insets(5));
		Button betaal = new Button("Betaal");
		betaal.setDisable(true);
		Button naarKeuken = new Button("Naar Keuken");
		naarKeuken.setDisable(true);
		botsec.getChildren().addAll(afsluit, tebetalen, betaal, naarKeuken);


		mainBox.setPadding(new Insets(10));
		mainBox.getChildren().addAll(topbox, buttons,aantalBroodjes, midsec, botsec);

		return mainBox;
	}
	public Button getNieuwBestellingBtn() {
		return this.nieuwBestellingBtn;
	}

	public TableView<Bestellijn> getBestellijnTableView() {
		return this.bestellijnTableView;
	}

	public Label getAantalBroodjes() {
		return this.aantalBroodjes;
	}

	public Map<String, Button> getBroojebuttons() {
		return this.broojebuttons;
	}
	public Map<String, Button> getBelegbuttons() {
		return this.belegButtons;
	}

}

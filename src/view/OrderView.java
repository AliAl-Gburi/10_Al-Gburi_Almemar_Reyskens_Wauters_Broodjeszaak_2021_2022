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
	private Button duplicateOrderBtn;
	private TableView<Bestellijn> bestellijnTableView;
	private Label aantalBroodjes;
	private Button verwijderBroodje;
	private Button annuleerBestelling;
	private HBox broodjesKnopen;
	private HBox belegenKnopen;
	private Label tebetalen;
	private Button afsluitBestelling;
	private Button betaal;
	private Button naarKeuken;
	ChoiceBox<String> kortings;

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

		broodjesKnopen = new HBox(8);

		belegenKnopen = new HBox(8);


		VBox buttons = new VBox(8);
		buttons.setPadding(new Insets(10));
		buttons.getChildren().addAll(broodjesKnopen, belegenKnopen);

		HBox topbox = new HBox(8);;
		topbox.setPadding(new Insets(10));

		Label volgnr = new Label("Volgnr: 0");
		nieuwBestellingBtn = new Button("Nieuwe Bestelling");

		kortings = new ChoiceBox<>();
		kortings.getItems().addAll("Geen korting","Goedkoopste broodje gratis", "10% korting op bestelling");
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
		duplicateOrderBtn = new Button("Voeg zelfde broodje toe");
		verwijderBroodje = new Button("Verwijder broodje");

		VBox anbox = new VBox(8);
		anbox.setPadding(new Insets(184, 0, 0, 0));

		annuleerBestelling = new Button("Annuleer bestelling");
		anbox.getChildren().add(annuleerBestelling);
		rightBox.getChildren().addAll(selecteer, duplicateOrderBtn, verwijderBroodje, anbox);

		HBox midsec = new HBox(8);
		midsec.setPadding(new Insets(10));
		midsec.getChildren().addAll(bestellijnTableView, rightBox);

		HBox botsec = new HBox(8);
		botsec.setPadding(new Insets(10));
		afsluitBestelling = new Button("Afsluiten Bestelling");
		tebetalen = new Label("Te betalen: €0");
		tebetalen.setPadding(new Insets(5));
		betaal = new Button("Betaal");
		betaal.setDisable(true);
		naarKeuken = new Button("Naar Keuken");
		naarKeuken.setDisable(true);
		botsec.getChildren().addAll(afsluitBestelling, tebetalen, betaal, naarKeuken);


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
	
	public Label getTeBetalen() {
		return this.tebetalen;
	}

	public Map<String, Button> getBroojebuttons() {
		return this.broojebuttons;
	}
	public Map<String, Button> getBelegbuttons() {
		return this.belegButtons;
	}
	public Button getDuplicateOrderBtn() {
		return this.duplicateOrderBtn;
	}
	public Button getVerwijderBroodje() {
		return this.verwijderBroodje;
	}
	public Button getAnnuleerBestelling() {
		return this.annuleerBestelling;
	}
	public HBox getBroodjesKnopen() {
		return this.broodjesKnopen;
	}
	public HBox getBelegenKnopen() {
		return this.belegenKnopen;
	}
	public Button getAfsluitBestelling() {
		return this.afsluitBestelling;
	}
	public Button getBetaal() {
		return this.betaal;
	}
	public Button getNaarKeuken() {
		return this.naarKeuken;
	}
	public String getKorting() {
		return kortings.getValue();
	}

}

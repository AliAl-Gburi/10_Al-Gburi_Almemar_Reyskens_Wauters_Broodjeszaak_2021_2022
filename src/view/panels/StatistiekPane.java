package view.panels;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.GridPane;

import java.util.Arrays;

public class StatistiekPane extends GridPane {
    private CategoryAxis xaxis;
    private NumberAxis yaxis;
    private XYChart.Series<String, Number> series;

    public StatistiekPane() {
        this.setPadding(new Insets(5, 5, 5, 5));
        this.setHgap(5);
        this.setVgap(5);

        xaxis = new CategoryAxis();


        yaxis = new NumberAxis();
        yaxis.setLabel("Aantal");

        series = new XYChart.Series<String, Number>();

        series.setName("Verkocht");

        BarChart<String, Number> barChart = new BarChart<String, Number>(xaxis, yaxis);

        barChart.getData().add(series);
        barChart.setPrefWidth(600);
        barChart.setTitle("Aantal Verkocht Broodjes en Belegen");
        this.add(barChart, 1, 1);

    }

    public CategoryAxis getXaxis() {
        return this.xaxis;
    }

    public NumberAxis getYaxis() {
        return this.yaxis;
    }

    public XYChart.Series<String, Number> getSeries() {
        return this.series;

    }
    
}

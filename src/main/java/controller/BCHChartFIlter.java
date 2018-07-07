/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.List;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import model.MarketCapModel;

/**
 *
 * @author Fajarrestuil69
 */
public class BCHChartFIlter extends Application{
    @Override 
    public void start(Stage stage) { 
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final LineChart<String,Number> bc = new LineChart<String,Number>(xAxis,yAxis);
        bc.setTitle("Bitcoin Cash Market Capitalization Filter");
        xAxis.setLabel("Date");       
        yAxis.setLabel("Market Cap");
 
        XYChart.Series series1 = new XYChart.Series();

        FilterBTCController bch = new FilterBTCController();
        List<MarketCapModel> subList;

        subList = bch.getList();
        if (!subList.isEmpty()) {
            int size;

            size = subList.size();

            for (int i = 0; i < size; i++) {
                String date = subList.get(i).getDate();
                long marketCap = subList.get(i).getMarketCap();
                series1.getData().add(new XYChart.Data(date, marketCap));
            }
            
            Scene scene  = new Scene(bc,800,600);
            bc.getData().addAll(series1);
            stage.setScene(scene);
            stage.show();
        }else{
            System.out.println("Empty List !");
        }
    }    
}

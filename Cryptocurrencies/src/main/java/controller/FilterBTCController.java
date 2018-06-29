/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.MarketCapModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FilterBTCController {

    int k = 5;
    private static final String FilterbitcoinData = "*/../src/main/java/excel/Bitcoin.xlsx";
    private List<MarketCapModel> FilterbitcoinMarketCap = new ArrayList<>();

    public void getFilterData() {
        try {
            FileInputStream excel = new FileInputStream(new File(FilterbitcoinData));
            Workbook workbook = new XSSFWorkbook(excel);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = sheet.iterator();
            iterator.next();
            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                
                Cell date = currentRow.getCell(2);
                Cell current = currentRow.getCell(k);
                
                MarketCapModel mrkt = new MarketCapModel();

                mrkt.setDate(date.toString());
                mrkt.setCurrent((long) current.getNumericCellValue());

                this.FilterbitcoinMarketCap.add(mrkt);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(FilterBTCController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FilterBTCController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<MarketCapModel> getList() {
        if (this.FilterbitcoinMarketCap.isEmpty()) {
            getFilterData();
        }
        return this.FilterbitcoinMarketCap;
    }
}

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

/**
 *
 * @author ACER
 */
public class BitcoinController {

    private static final String bitcoinData = "*/../src/main/java/excel/Bitcoin.xlsx";
    private List<MarketCapModel> bitcoinMarketCap = new ArrayList<>();
    private List<MarketCapModel> monthlyMarketCap = new ArrayList<>();

    public void getAllData() {
        try {
            FileInputStream excel = new FileInputStream(new File(bitcoinData));
            Workbook workbook = new XSSFWorkbook(excel);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = sheet.iterator();
            iterator.next();

            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                Cell date = currentRow.getCell(2);
                Cell open = currentRow.getCell(3);
                Cell high = currentRow.getCell(4);
                Cell low = currentRow.getCell(5);
                Cell close = currentRow.getCell(6);
                Cell volume = currentRow.getCell(7);
                Cell marketCap = currentRow.getCell(8);

                MarketCapModel mrkt = new MarketCapModel();

                mrkt.setDate(date.toString());
                mrkt.setOpen((double) open.getNumericCellValue());
                mrkt.setHigh((double) high.getNumericCellValue());
                mrkt.setLow((double) low.getNumericCellValue());
                mrkt.setClose((double) close.getNumericCellValue());
                mrkt.setVolume((int) volume.getNumericCellValue());
                mrkt.setMarketCap((long) marketCap.getNumericCellValue());

                this.bitcoinMarketCap.add(mrkt);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(BitcoinController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BitcoinController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<MarketCapModel> getList() {
        if (this.bitcoinMarketCap.isEmpty()) {
            getAllData();
        }
        return this.bitcoinMarketCap;
    }

    private void montlyChart(String key) {
        BitcoinController sub = new BitcoinController();
        List<MarketCapModel> subList;
        long Filter = 0;
        subList = sub.getList();

        if (!subList.isEmpty()) {

            int size = subList.size();
            long eachMonth = 0;

            String mm = subList.get(0).getDate().substring(3, 6);

            for (int i = 0; i < size; i++) {
                String currMonth = subList.get(i).getDate().substring(3, 6);
                if (null != key) {
                    switch (key) {
                        case "Open":
                            Filter = (long) subList.get(i).getOpen();
                            //       judul = "Open";
                            break;
                        case "MarketCap":
                            Filter = subList.get(i).getMarketCap();
                            //     judul = "MarketCap";
                            break;
                        case "Close":
                            Filter = (long) subList.get(i).getClose();
                            //   judul = "Close";
                            break;
                        case "Volume":
                            Filter = subList.get(i).getVolume();
                            // judul = "Volume";
                            break;
                        case "Low":
                            Filter = (long) subList.get(i).getLow();
                            //judul = "Low";
                            break;
                        case "High":
                            Filter = (long) subList.get(i).getHigh();
                            //judul = "High";
                            break;
                        default:
                            break;
                    }
                }
                if (currMonth.equals(mm)) {
                    eachMonth += Filter;
                } else {

                    MarketCapModel sum = new MarketCapModel();
                    String date = subList.get(i).getDate().substring(3);
                    sum.setDate(date);
                    if (null != key) {
                    switch (key) {
                        case "Open":
                            sum.setOpen(eachMonth);
                            break;
                        case "MarketCap":
                            sum.setMarketCap(eachMonth);
                            break;
                        case "Close":
                            sum.setClose(eachMonth);
                            break;
                        case "Volume":
                            sum.setVolume((int) eachMonth);
                            break;
                        case "Low":
                            sum.setLow(eachMonth);
                            break;
                        case "High":
                            sum.setHigh(eachMonth);
                            break;
                        default:
                            break;
                    }
                    }
                    sum.setMarketCap(eachMonth);
                    this.monthlyMarketCap.add(sum);

                    mm = subList.get(i).getDate().substring(3, 6);
                    eachMonth = 0 + Filter;
                }
            }
        }
    }

    public List<MarketCapModel> getListMonthly(String key) {
        if (this.monthlyMarketCap.isEmpty()) {
            montlyChart(key);
        }
        return this.monthlyMarketCap;
    }
}

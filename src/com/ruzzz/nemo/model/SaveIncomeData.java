/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ruzzz.nemo.model;

import com.ruzzz.nemo.connection.MySQL;
import static com.ruzzz.nemo.panel.IncomePanel.getMonthNameShort;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.File;
import java.sql.ResultSet;
import java.text.DecimalFormat;

/**
 *
 * @author Acer
 */
public class SaveIncomeData {

    public static void saveDataToXML(String year, File xmlFile) throws Exception {
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();

      
        Element root = document.createElement("IncomeData");
        root.setAttribute("year", year);
        document.appendChild(root);

        DecimalFormat df = new DecimalFormat("00");

        ResultSet rs = MySQL.execute("SELECT DISTINCT MONTH(reservation.date) AS month "
                + "FROM reservation "
                + "WHERE YEAR(reservation.date) = '" + year + "' "
                + "ORDER BY month");

        while (rs.next()) {
            String month = df.format(rs.getInt("month"));

            Element monthElement = document.createElement("Month");
            monthElement.setAttribute("name", getMonthNameShort(Integer.parseInt(month)));
            root.appendChild(monthElement);

            String incomeQuery = "SELECT SUM(total + service_charge) AS monthly_income "
                    + "FROM invoice WHERE date_time_issued LIKE '" + year + "-" + month + "%'";
            ResultSet income = MySQL.execute(incomeQuery);

            String profitQuery = "SELECT SUM(invoice_service.profit) AS monthly_profit "
                    + "FROM invoice "
                    + "INNER JOIN invoice_service ON invoice.invoice_id = invoice_service.invoice_invoice_id "
                    + "WHERE date_time_issued LIKE '" + year + "-" + month + "%'";
            ResultSet rs2 = MySQL.execute(profitQuery);

            double monthlyIncome = 0.0;
            double monthlyProfit = 0.0;

            if (income.next()) {
                String monthlyIncomeStr = income.getString("monthly_income");
                monthlyIncome = (monthlyIncomeStr != null) ? Double.parseDouble(monthlyIncomeStr) : 0.0;
            }

            if (rs2.next()) {
                String monthlyProfitStr = rs2.getString("monthly_profit");
                monthlyProfit = (monthlyProfitStr != null) ? Double.parseDouble(monthlyProfitStr) : 0.0;
            }

            Element dataElement = document.createElement("Data");
            dataElement.setAttribute("income", String.valueOf(monthlyIncome - monthlyProfit));
            dataElement.setAttribute("profit", String.valueOf(monthlyProfit));
            monthElement.appendChild(dataElement);

            income.close();
            rs2.close();
        }
        rs.close();

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(xmlFile);

        transformer.transform(source, result);
    }

}

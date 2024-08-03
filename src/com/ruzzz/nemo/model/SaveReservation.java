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
public class SaveReservation {

    public static void saveReservationDataToXML(String year, File xmlFile) throws Exception {
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();

        // Root element
        Element root = document.createElement("ReservationData");
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

            String reservationQuery = "SELECT COUNT(reservation.id) AS total_reservations "
                    + "FROM reservation "
                    + "WHERE reservation.cancel_status = 2 "
                    + "AND reservation.status_id = 2 "
                    + "AND reservation.date LIKE '" + year + "-" + month + "%'";
            ResultSet reservation = MySQL.execute(reservationQuery);

            String canceledreservationQuery = "SELECT COUNT(reservation.id) AS total_reservations "
                    + "FROM reservation "
                    + "WHERE reservation.cancel_status = 1 "
                    + "AND reservation.status_id = 2 "
                    + "AND reservation.date LIKE '" + year + "-" + month + "%'";
            ResultSet canceledreservation = MySQL.execute(canceledreservationQuery);

            double monthlyReservation = 0.0;
            double monthlyCanceledReservation = 0.0;
            double monthlyPendingReservation = 0.0;

            if (reservation.next()) {
                String monthlyReservationStr = reservation.getString("total_reservations");
                monthlyReservation = (monthlyReservationStr != null) ? Double.parseDouble(monthlyReservationStr) : 0.0;
            }

            if (canceledreservation.next()) {
                String monthlyCanceledReservationStr = canceledreservation.getString("total_reservations");
                monthlyCanceledReservation = (monthlyCanceledReservationStr != null) ? Double.parseDouble(monthlyCanceledReservationStr) : 0.0;
            }

            Element dataElement = document.createElement("Data");
            dataElement.setAttribute("reservations", String.valueOf(monthlyReservation));
            dataElement.setAttribute("canceled", String.valueOf(monthlyCanceledReservation));
            dataElement.setAttribute("pending", String.valueOf(monthlyPendingReservation));
            monthElement.appendChild(dataElement);

            reservation.close();
            canceledreservation.close();
        }
        rs.close();

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(xmlFile);

        transformer.transform(source, result);
    }

}

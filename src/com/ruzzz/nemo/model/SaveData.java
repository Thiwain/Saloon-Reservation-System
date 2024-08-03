/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ruzzz.nemo.model;

import com.ruzzz.nemo.connection.MySQL;
import static com.ruzzz.nemo.properties.LoggerConfig.errorLogger;
import java.io.File;
import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Acer
 */
public class SaveData {

    public static void SaveData() {
        try {
            ResultSet rs = MySQL.execute("SELECT DISTINCT YEAR(reservation.date) AS year\n"
                    + "FROM reservation\n"
                    + "ORDER BY year DESC");
            Vector<String> v = new Vector<>();

            while (rs.next()) {
                File xmlFile = new File("incomeData_" + rs.getString("year") + ".xml");
                File xmlFile2 = new File("reservationData_" + rs.getString("year") + ".xml");
                SaveIncomeData.saveDataToXML(rs.getString("year"), xmlFile);
                SaveReservation.saveReservationDataToXML(rs.getString("year"), xmlFile2);
            }

        } catch (Exception e) {
            errorLogger.warning("ERROR WHILELOADING YEAR COMBOBOX" + e);
        }
    }
    
  
}

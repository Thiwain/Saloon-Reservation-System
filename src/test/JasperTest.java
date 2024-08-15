/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;

import static com.ruzzz.nemo.properties.LoggerConfig.errorLogger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Acer
 */
public class JasperTest {



    public static void main(String[] args) {
        try {

            HashMap<String, Object> parameterMap = new HashMap<>();
            parameterMap.put("Parameter1", "2024-08-15");
            parameterMap.put("Parameter2", "2024-08-01-2024-08-02");
            parameterMap.put("Parameter3", "0.00");
            parameterMap.put("Parameter4", "0.00");
            String path = "src/com/ruzzz/nemo/report/income.jasper";
            JasperReport report = JasperCompileManager.compileReport(path);
            JasperPrint jprint = JasperFillManager.fillReport(path, parameterMap, new JREmptyDataSource());
            JasperViewer.viewReport(jprint, false);
        } catch (Exception e) {
            errorLogger.warning("TABLE EXPORT TO PDF ; Error: " + e);
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.ruzzz.nemo.panel;

import com.ruzzz.nemo.chart.ModelChart;
import com.ruzzz.nemo.connection.MySQL;
import com.ruzzz.nemo.connection.MySQLTwo;
import com.ruzzz.nemo.dialog.ReservationDeatailDialog;
import com.ruzzz.nemo.gui.ControlPanel;
import static com.ruzzz.nemo.panel.CustomerPanel.isDate1NotLater;
import static com.ruzzz.nemo.properties.LoggerConfig.errorLogger;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import raven.toast.Notifications;
import static test.JPanelToImage.exportPanelAsImage;

/**
 *
 * @author Acer
 */
public class IncomePanel extends javax.swing.JPanel {

    /**
     * Creates new form IncomePanel
     */
    private final ControlPanel cpanel;

    public IncomePanel(JFrame cp) {
        initComponents();
        loadYears();

        cpanel = (ControlPanel) cp;

        chart2.setBackground(new Color(250, 250, 250));
        chart2.addLegend("Completed", new Color(122, 89, 255));
        chart2.addLegend("Canceled", new Color(255, 69, 249));

        chart3.setBackground(new Color(250, 250, 250));
        chart3.addLegend("Income", new Color(66, 167, 245));
        chart3.addLegend("Profit", new Color(66, 245, 212));

        loadIncomeChartData();
        loadReservationChartData();
        calculateIncomeAndProfitCombo();
        calculateReservationsCombo();
        loadIncomeTable("");
    }

    private void loadIncomeChartData() {
        chart3.start();
        try {
//            chart3.removeAll();

            String year = jComboBox1.getSelectedItem().toString();
            DecimalFormat df = new DecimalFormat("00");

            ResultSet rs = MySQLTwo.execute("SELECT DISTINCT MONTH(reservation.date) AS month "
                    + "FROM reservation "
                    + "WHERE YEAR(reservation.date) = '" + year + "' "
                    + "ORDER BY month");

            while (rs.next()) {
                String month = df.format(rs.getInt("month"));

                String incomeQuery = "SELECT SUM(total + service_charge) AS monthly_income "
                        + "FROM invoice WHERE date_time_issued LIKE '" + year + "-" + month + "%'";

                ResultSet income = MySQLTwo.execute(incomeQuery);

                if (income.next()) {
                    String monthlyIncomeStr = income.getString("monthly_income");
                    double monthlyIncome = (monthlyIncomeStr != null) ? Double.parseDouble(monthlyIncomeStr) : 0.0;

                    String profitQuery = "SELECT SUM(invoice_service.profit) AS monthly_profit "
                            + "FROM invoice "
                            + "INNER JOIN invoice_service ON invoice.invoice_id = invoice_service.invoice_invoice_id "
                            + "WHERE date_time_issued LIKE '" + year + "-" + month + "%'";

                    ResultSet rs2 = MySQLTwo.execute(profitQuery);

                    if (rs2.next()) {
                        String monthlyProfitStr = rs2.getString("monthly_profit");
                        double monthlyProfit = (monthlyProfitStr != null) ? Double.parseDouble(monthlyProfitStr) : 0.0;

                        chart3.addData(new ModelChart(getMonthNameShort(Integer.parseInt(month)), new double[]{monthlyIncome - monthlyProfit, monthlyProfit}));
                    }

                    rs2.close();
                }

                income.close();
            }

            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            errorLogger.warning("ERROR WHILELOADING INCOME CHART" + e);
        }
    }

    private void loadReservationChartData() {

        chart2.start();

        try {

            String year = jComboBox1.getSelectedItem().toString();
            DecimalFormat df = new DecimalFormat("00");

            ResultSet rs = MySQLTwo.execute("SELECT DISTINCT MONTH(reservation.date) AS month "
                    + "FROM reservation "
                    + "WHERE YEAR(reservation.date) = '" + year + "' "
                    + "ORDER BY month");

            while (rs.next()) {
                String month = df.format(rs.getInt("month"));

                String reservationQuery = "SELECT COUNT(reservation.id) AS total_reservations "
                        + "FROM reservation "
                        + "WHERE reservation.cancel_status = 2 "
                        + "AND reservation.status_id = 2 "
                        + "AND reservation.date LIKE '" + year + "-" + month + "%'";

                String canceledreservationQuery = "SELECT COUNT(reservation.id) AS total_reservations "
                        + "FROM reservation "
                        + "WHERE reservation.cancel_status = 1 "
                        + "AND reservation.status_id = 2 "
                        + "AND reservation.date LIKE '" + year + "-" + month + "%'";

                ResultSet reservation = MySQLTwo.execute(reservationQuery);
                ResultSet canceledreservation = MySQLTwo.execute(canceledreservationQuery);

                double monthlyReservation = 0.0;
                double monthlyPendingReservation = 0.0;
                double monthlyCanceledReservation = 0.0;

                if (reservation.next()) {
                    String monthlyReservationStr = reservation.getString("total_reservations");
                    monthlyReservation = (monthlyReservationStr != null) ? Double.parseDouble(monthlyReservationStr) : 0.0;
                }

                if (canceledreservation.next()) {
                    String monthlyCanceledReservationStr = canceledreservation.getString("total_reservations");
                    monthlyCanceledReservation = (monthlyCanceledReservationStr != null) ? Double.parseDouble(monthlyCanceledReservationStr) : 0.0;
                }

                chart2.addData(new ModelChart(getMonthNameShort(Integer.parseInt(month)), new double[]{monthlyReservation, monthlyCanceledReservation, monthlyPendingReservation}));

                reservation.close();
                canceledreservation.close();
            }

            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            errorLogger.warning("ERROR WHILELOADING RESERVATION CHART" + e);
        }
    }

    private void loadYears() {
        try {
            ResultSet rs = MySQLTwo.execute("SELECT DISTINCT YEAR(reservation.date) AS year\n"
                    + "FROM reservation\n"
                    + "ORDER BY year DESC");
            Vector<String> v = new Vector<>();

            while (rs.next()) {
                v.add(rs.getString("year"));
            }
            DefaultComboBoxModel dcm = new DefaultComboBoxModel(v);
            jComboBox1.setModel(dcm);

        } catch (Exception e) {
            errorLogger.warning("ERROR WHILELOADING YEAR COMBOBOX" + e);
        }
    }

    public static String getMonthNameShort(int monthNumber) {
        if (monthNumber < 1 || monthNumber > 12) {
            throw new IllegalArgumentException("Month number must be between 1 and 12");
        }
        return Month.of(monthNumber).getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
    }

    private void calculateIncomeAndProfitCombo() {
        jDateChooser1.setDate(null);
        jDateChooser2.setDate(null);
        try {
            String query = "SELECT \n"
                    + "    SUM(itotal) AS total_sum,\n"
                    + "    SUM(profit) AS profit_sum\n"
                    + "FROM (\n"
                    + "    SELECT \n"
                    + "        (invoice.total + invoice.service_charge) AS itotal,\n"
                    + "        SUM(invoice_service.profit) AS profit\n"
                    + "    FROM \n"
                    + "        invoice\n"
                    + "    INNER JOIN \n"
                    + "        reservation ON invoice.reservation_id = reservation.id\n"
                    + "    INNER JOIN \n"
                    + "        invoice_service ON invoice_service.invoice_invoice_id = invoice.invoice_id\n"
                    + "    GROUP BY \n"
                    + "        invoice.invoice_id, invoice.reservation_id, reservation.date, invoice.total, invoice.service_charge\n"
                    + "    ORDER BY reservation.date DESC";

            if (jComboBox2.getSelectedIndex() != 7) {
                query += " LIMIT " + jComboBox2.getSelectedItem().toString();
            }

            query += ") AS subquery";

            ResultSet rs = MySQLTwo.execute(query);

            if (rs.next()) {
                double total = Double.parseDouble(rs.getString("total_sum"));
                double profit = Double.parseDouble(rs.getString("profit_sum"));
                jLabel10.setText(String.valueOf(total - profit));
                jLabel9.setText(rs.getString("profit_sum"));
                jLabel7.setText(rs.getString("total_sum"));
            }
            rs.close();
        } catch (Exception e) {
            errorLogger.warning("ERROR WHILE CALCULATE IMCOME TOTAL BY COMBOBOX" + e);
        }
    }

    private void calculateReservationsCombo() {
        jDateChooser3.setDate(null);
        jDateChooser4.setDate(null);
        try {
            String query = "SELECT \n"
                    + "    COUNT(*) AS total_count,\n"
                    + "    SUM(CASE WHEN status_id = 2 AND cancel_status = 2 THEN 1 ELSE 0 END) AS finished_count,\n"
                    + "    SUM(CASE WHEN status_id = 1 AND cancel_status = 0 THEN 1 ELSE 0 END) AS pending_count\n"
                    + "FROM \n"
                    + "    reservation \n"
                    + "WHERE \n"
                    + "    reservation.date >= CURDATE() - INTERVAL " + jComboBox5.getSelectedItem().toString() + " DAY";

            ResultSet rs = MySQLTwo.execute(query);

            if (rs.next()) {
                jLabel17.setText(rs.getString("total_count"));
                jLabel16.setText(rs.getString("finished_count"));
                jLabel15.setText(rs.getString("pending_count"));
            }

            rs.close();
        } catch (Exception e) {
            errorLogger.warning("ERROR WHILE CALCULATING RESERVATIONS BY COMBO BOX" + e);
        }
    }

    private static boolean querySelection = false;
    private static boolean querySelection2 = false;

    public static boolean isDate1NotLater(Date date1, Date date2) {
        if (date1 == null && date2 == null) {
            querySelection = true;
            return true;
        }
        if (date1 == null && date2 != null) {
            System.out.println("d1 is empty");
            return false;
        }

        LocalDate d2 = convertToLocalDate(date2);
        LocalDate d1 = convertToLocalDate(date1);

        return !(d1 != null && d1.isAfter(d2));
    }

    private static LocalDate convertToLocalDate(Date date) {
        if (date == null) {
            return null;
        }
        return new java.sql.Date(date.getTime()).toLocalDate();
    }

    private static String convertDateString(String dateStr) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date = inputFormat.parse(dateStr);
            return outputFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void calculateIncomeAndProfitBtn() {
        try {
            Date date1 = jDateChooser1.getDate();
            Date date2 = jDateChooser2.getDate();
            boolean isNotLater = isDate1NotLater(date1, date2);

            String query = "SELECT \n"
                    + "    SUM(itotal) AS total_sum,\n"
                    + "    SUM(profit) AS profit_sum\n"
                    + "FROM (\n"
                    + "    SELECT \n"
                    + "        (invoice.total + invoice.service_charge) AS itotal,\n"
                    + "        SUM(invoice_service.profit) AS profit\n"
                    + "    FROM \n"
                    + "        invoice\n"
                    + "    INNER JOIN \n"
                    + "        reservation ON invoice.reservation_id = reservation.id\n"
                    + "    INNER JOIN \n"
                    + "        invoice_service ON invoice_service.invoice_invoice_id = invoice.invoice_id\n";

            if (date1 != null || date2 != null) {
                if (isNotLater) {
                    if (date2 == null) {
                        query += " WHERE reservation.date >='" + convertDateString(jDateChooser1.getDate().toString()) + "'";
                    } else {
                        query += " WHERE reservation.date BETWEEN '" + convertDateString(jDateChooser1.getDate().toString()) + "' AND '" + convertDateString(jDateChooser2.getDate().toString()) + "'\n";
                    }
                } else {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Invalid Date Selection !");
                    return; 
                }
            } else {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Must Select a date !");
                return; 
            }

            query += "    GROUP BY \n"
                    + "        invoice.invoice_id, invoice.reservation_id, reservation.date, invoice.total, invoice.service_charge\n"
                    + "    ORDER BY reservation.date DESC\n"
                    + ") AS subquery";


            ResultSet rs = MySQLTwo.execute(query);

            if (rs.next()) {
                double total = rs.getDouble("total_sum");
                double profit = rs.getDouble("profit_sum");
                jLabel10.setText(String.valueOf(total - profit));
                jLabel9.setText(String.valueOf(profit));
                jLabel7.setText(String.valueOf(total));
            }

            rs.close(); // Close the ResultSet
        } catch (Exception e) {
            e.printStackTrace();
            errorLogger.warning("ERROR WHILE LOADING INCOME " + e);
        }
    }

    private void calculateReservationsBtn() {
        try {
            Date date1 = jDateChooser3.getDate();
            Date date2 = jDateChooser4.getDate();
            boolean isNotLater = isDate1NotLater(date1, date2);

            if (date1 == null || date2 == null) {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Must Select a date !");
                return;
            }

            if (!isNotLater) {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Invalid Date Selection !");
                return;
            }

            String query = "SELECT \n"
                    + "    COUNT(*) AS total_count, \n"
                    + "    SUM(CASE WHEN status_id = 2 AND cancel_status = 2 THEN 1 ELSE 0 END) AS finished_count, \n"
                    + "    SUM(CASE WHEN status_id = 1 AND cancel_status = 0 THEN 1 ELSE 0 END) AS pending_count \n"
                    + "FROM \n"
                    + "    reservation ";

            if (date1 != null || date2 != null) {
                if (isNotLater) {
                    if (date2 == null) {
                        query += " WHERE reservation.date >='" + convertDateString(jDateChooser3.getDate().toString()) + "'";
                    } else {
                        query += " WHERE reservation.date BETWEEN '" + convertDateString(jDateChooser3.getDate().toString()) + "' AND '" + convertDateString(jDateChooser4.getDate().toString()) + "'\n";
                    }
                } else {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Invalid Date Selection !");
                    return;
                }
            } else {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Must Select a date !");
                return;
            }

            ResultSet rs = MySQLTwo.execute(query);

            if (rs.next()) {
                jLabel17.setText(rs.getString("total_count"));
                jLabel16.setText(rs.getString("finished_count"));
                jLabel15.setText(rs.getString("pending_count"));
            }

            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void exportPanelAsImage(JPanel panel, String filePath) {
        int width = panel.getWidth();
        int height = panel.getHeight();

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = image.createGraphics();
        panel.paint(g2d);
        g2d.dispose();

        try {
            File file = new File(filePath);
            ImageIO.write(image, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String generateFileName(String prefix) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String currentDateAndTime = sdf.format(new Date());

        String uniqueId = UUID.randomUUID().toString();

        String fileName = prefix + "_" + currentDateAndTime + "_" + uniqueId;

        return fileName;
    }

    double totalIncomeLabel = 00.0;
    double totalProfitLabel = 00.0;

    private void loadIncomeTable(String searchTxt) {
        totalIncomeLabel = 00.0;
        totalProfitLabel = 00.0;
        jLabel28.setText("");
        try {
            Date date1 = jDateChooser5.getDate();
            Date date2 = jDateChooser6.getDate();
            boolean isNotLater = isDate1NotLater(date1, date2);

            String query = "SELECT \n"
                    + "    reservation.id AS rsid,\n"
                    + "    invoice.invoice_id AS inid,\n"
                    + "    CONCAT(customer.first_name, ' ', customer.last_name) AS cusname,\n"
                    + "    invoice.total AS intotal,\n"
                    + "    invoice.date_time_issued AS date,\n"
                    + "    (SELECT SUM(profit) FROM invoice_service WHERE invoice_service.invoice_invoice_id = invoice.invoice_id) AS total_profit\n"
                    + "FROM reservation \n"
                    + "INNER JOIN invoice ON invoice.reservation_id = reservation.id\n"
                    + "INNER JOIN customer ON reservation.customer_id = customer.id\n"
                    + "WHERE reservation.cancel_status != 1\n";

            if (date1 != null || date2 != null) {
                if (isNotLater) {
                    if (date2 == null) {
                        query += " AND reservation.date >='" + convertDateString(jDateChooser5.getDate().toString()) + "'";
                    } else {
                        query += " AND reservation.date BETWEEN '" + convertDateString(jDateChooser5.getDate().toString()) + "' AND '" + convertDateString(jDateChooser6.getDate().toString()) + "'\n";
                    }
                } else {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Invalid Date Selection !");
                    return;
                }
            }

            query += " AND (customer.first_name LIKE '%" + searchTxt + "%' OR customer.last_name LIKE '%" + searchTxt + "%'"
                    + " OR customer.mobile LIKE '%" + searchTxt + "%' OR customer.email LIKE '%" + searchTxt + "%')\n"
                    + "ORDER BY invoice.date_time_issued " + jComboBox4.getSelectedItem().toString() + "";

            ResultSet rs = MySQLTwo.execute(query);

            DefaultTableModel tableModel = (DefaultTableModel) jTable2.getModel();
            tableModel.setRowCount(0);
            int rowId = 0;
            while (rs.next()) {
                rowId++;
                Vector<String> v = new Vector<>();
                v.add(String.valueOf(rowId));
                v.add(rs.getString("rsid"));
                v.add(rs.getString("inid"));
                v.add(rs.getString("cusname"));
                v.add(rs.getString("intotal"));
                totalIncomeLabel += Double.parseDouble(rs.getString("intotal"));
                jLabel25.setText(String.valueOf(totalIncomeLabel));
                v.add(rs.getString("total_profit"));
                totalProfitLabel += Double.parseDouble(rs.getString("total_profit"));
                jLabel27.setText(String.valueOf(totalProfitLabel));
                v.add(rs.getString("date"));
                jLabel28.setText(String.valueOf(tableModel.getRowCount()));
                tableModel.addRow(v);
            }
            rs.close();
        } catch (Exception e) {
            errorLogger.warning("ERROR WHILE LOADING INCOME TABLE" + e);
        }
    }

    private static String getCurrentDate() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return currentDate.format(formatter);
    }

    private void printExcel() {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet spreadsheet = workbook.createSheet(" Customer Data ");
            XSSFRow row;

            Map<String, Object[]> studentData = new TreeMap<String, Object[]>();

            for (int i = 0; i < jTable2.getRowCount(); i++) {
                studentData.put(String.valueOf(i),
                        new Object[]{jTable2.getValueAt(i, 0),
                            jTable2.getValueAt(i, 1),
                            jTable2.getValueAt(i, 2),
                            jTable2.getValueAt(i, 3),
                            jTable2.getValueAt(i, 4),
                            jTable2.getValueAt(i, 5),
                            jTable2.getValueAt(i, 6),});
            }

            Set<String> keyid = studentData.keySet();

            int rowid = 0;
            for (String key : keyid) {
                row = spreadsheet.createRow(rowid++);
                Object[] objectArr = studentData.get(key);
                int cellid = 0;
                for (Object obj : objectArr) {
                    Cell cell = row.createCell(cellid++);
                    cell.setCellValue((String) obj);
                }
            }

            FileOutputStream out = new FileOutputStream(
                    new File("C:/Users/Acer/Documents/NetBeansProjects/SaloonNemo/excel/" + jTextField5.getText() + "-" + getCurrentDate() + "-" + String.valueOf(System.currentTimeMillis()) + jComboBox2.getSelectedItem().toString()));

            workbook.write(out);
            out.close();
        } catch (Exception e) {
            errorLogger.warning("TABLE EXPORT ERROR; Error: " + e);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        chart3 = new com.ruzzz.nemo.chart.Chart();
        chart2 = new com.ruzzz.nemo.chart.Chart();
        jButton1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel19 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox<>();
        jLabel23 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jDateChooser3 = new com.toedter.calendar.JDateChooser();
        jDateChooser4 = new com.toedter.calendar.JDateChooser();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel5 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jButton7 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jComboBox4 = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jDateChooser6 = new com.toedter.calendar.JDateChooser();
        jDateChooser5 = new com.toedter.calendar.JDateChooser();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jSeparator9 = new javax.swing.JSeparator();

        setLayout(new java.awt.BorderLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/ruzzz/nemo/img/1564501_business_chart_dashboard_graph_icon_1.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        jLabel2.setText("Income");

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/ruzzz/nemo/img/6127257_multimedia_music_refresh_repeat_song_icon (1).png"))); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4)
                .addContainerGap(830, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton4)
                        .addGap(8, 8, 8))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );

        add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jLabel3.setText("Income(LKR) & Reservations");

        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });

        jLabel11.setText("Year:");

        jPanel6.setLayout(new java.awt.GridLayout(1, 2, 15, 15));
        jPanel6.add(chart3);
        jPanel6.add(chart2);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/ruzzz/nemo/img/8665756_print_icon (2) (1).png"))); // NOI18N
        jButton1.setText("Export");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel4.setText("Income & Profit On Last");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "5", "10", "30", "60", "90", "365", "*" }));
        jComboBox2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox2ItemStateChanged(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Income");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Profit");

        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("0.00");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("Total:");

        jLabel9.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("0.00");

        jLabel10.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("0.00");

        jLabel12.setText("Reservations On Last");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setText("Fulfilled");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setText("Pending");

        jLabel15.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("0.00");

        jLabel16.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("0.00");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel17.setText("0.00");

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel18.setText("Total:");

        jSeparator8.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel19.setText("To");

        jButton2.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jButton2.setText(".");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel20.setText("To");

        jButton3.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jButton3.setText(".");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel22.setText("Days");

        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "5", "10", "30", "60", "90", "365" }));
        jComboBox5.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox5ItemStateChanged(evt);
            }
        });

        jLabel23.setText("Days");

        jDateChooser1.setDateFormatString("yyyy-MM-dd");

        jDateChooser2.setDateFormatString("yyyy-MM-dd");

        jDateChooser3.setDateFormatString("yyyy-MM-dd");

        jDateChooser4.setDateFormatString("yyyy-MM-dd");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jSeparator6, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel22))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox5, 0, 1, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel23))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jDateChooser3, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel20)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jDateChooser4, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel10, jLabel5, jLabel6, jLabel9});

        jPanel4Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jDateChooser3, jDateChooser4});

        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel19)
                        .addComponent(jButton2))
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                    .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel23))
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel20)
                    .addComponent(jButton3)
                    .addComponent(jDateChooser3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateChooser4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel5, jLabel6});

        jPanel4Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel10, jLabel9});

        jPanel4Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton2, jLabel19});

        jPanel4Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton3, jLabel20});

        jPanel4Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jComboBox2, jLabel22, jLabel4});

        jPanel4Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jDateChooser1, jDateChooser2, jDateChooser3, jDateChooser4});

        jPanel5.setLayout(new java.awt.BorderLayout());

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel24.setText("Total:");

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel25.setText("0.00");

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel26.setText("Profit:");

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel27.setText("0.00");

        jButton6.setText("Reset");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel28.setText("0");

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel29.setText("Reservations:");

        jLabel30.setText("File Name");

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { ".csv", ".xlsx" }));
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });

        jButton7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton7.setForeground(new java.awt.Color(51, 102, 255));
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/ruzzz/nemo/img/8665756_print_icon (2) (1).png"))); // NOI18N
        jButton7.setText("Print");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                                    .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(jButton6, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jButton7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(160, 160, 160)
                .addComponent(jLabel30)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel24, jLabel25, jLabel26, jLabel27});

        jPanel8Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton7, jComboBox3, jTextField5});

        jPanel5.add(jPanel8, java.awt.BorderLayout.LINE_END);

        jPanel9.setLayout(new java.awt.BorderLayout());

        jLabel21.setText("TO");

        jButton5.setText("Sort");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ASC", "DESC" }));
        jComboBox4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox4ItemStateChanged(evt);
            }
        });

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        jLabel31.setText("Search By Customer.....");

        jDateChooser6.setDateFormatString("yyyy-MM-dd");

        jDateChooser5.setDateFormatString("yyyy-MM-dd");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jDateChooser5, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDateChooser6, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                    .addComponent(jTextField1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel31)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDateChooser5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateChooser6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton5)
                            .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jLabel21)))
                .addContainerGap())
        );

        jPanel10Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton5, jLabel21});

        jPanel9.add(jPanel10, java.awt.BorderLayout.PAGE_START);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "Reser(Id)", "Invoice(Id)", "Customer", "Total", "Profit", "DateIssued"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        jPanel9.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel5.add(jPanel9, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jSeparator1)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator9)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 442, Short.MAX_VALUE))
        );

        add(jPanel3, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

    }//GEN-LAST:event_jButton4ActionPerformed

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        chart3.clear();
        chart2.clear();
        loadReservationChartData();
        loadIncomeChartData();
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        exportPanelAsImage(jPanel6, "C:\\Users\\Acer\\Documents\\NetBeansProjects\\SaloonNemo\\chart_img\\" + generateFileName("ChartImage") + ".png");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBox2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox2ItemStateChanged
        calculateIncomeAndProfitCombo();
    }//GEN-LAST:event_jComboBox2ItemStateChanged

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        calculateIncomeAndProfitBtn();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jComboBox5ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox5ItemStateChanged
        calculateReservationsCombo();
    }//GEN-LAST:event_jComboBox5ItemStateChanged

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        calculateReservationsBtn();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        if (!jTextField5.getText().isEmpty()) {
            printExcel();
        } else {
            JOptionPane.showMessageDialog(null, "File Name is Required!");
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        loadIncomeTable(jTextField1.getText());
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        loadIncomeTable(jTextField1.getText());
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jComboBox4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox4ItemStateChanged
        loadIncomeTable(jTextField1.getText());
    }//GEN-LAST:event_jComboBox4ItemStateChanged

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        DefaultTableModel dtm = (DefaultTableModel) jTable2.getModel();

        int row = jTable2.getSelectedRow();
        if (evt.getClickCount() == 2) {
            ReservationDeatailDialog rdd = new ReservationDeatailDialog(cpanel, false, dtm.getValueAt(row, 1).toString());
            rdd.setVisible(true);
        }
    }//GEN-LAST:event_jTable2MouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.ruzzz.nemo.chart.Chart chart2;
    private com.ruzzz.nemo.chart.Chart chart3;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private com.toedter.calendar.JDateChooser jDateChooser3;
    private com.toedter.calendar.JDateChooser jDateChooser4;
    private com.toedter.calendar.JDateChooser jDateChooser5;
    private com.toedter.calendar.JDateChooser jDateChooser6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField5;
    // End of variables declaration//GEN-END:variables
}

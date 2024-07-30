/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.ruzzz.nemo.panel;

import com.ruzzz.nemo.dialog.ReservationDeatailDialog;
import com.ruzzz.nemo.connection.MySQL;
import com.ruzzz.nemo.gui.ControlPanel;
import static com.ruzzz.nemo.panel.CustomerPanel.isDate1NotLater;
import static com.ruzzz.nemo.properties.LoggerConfig.errorLogger;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import raven.toast.Notifications;

/**
 *
 * @author Acer
 */
public class ReservationListPanel extends javax.swing.JPanel {

    private static ControlPanel cpanel;

    public ReservationListPanel(JFrame cp) {
        initComponents();
        loademployee();
        loadStatus();
        loadTF();
        loadReservations("");
        cpanel = (ControlPanel) cp;
    }

    HashMap<String, String> employeeMap = new HashMap<>();
    HashMap<String, String> statusMap = new HashMap<>();
    HashMap<String, String> tfMap = new HashMap<>();

    private void loademployee() {
        Vector<String> v = new Vector<>();
        try {
            v.add("Select Worker");
            v.add("**Someone Available**");
            employeeMap.put("**Someone Available**", "BA_10000");
            ResultSet rs = MySQL.execute("SELECT * FROM `employee` WHERE `role_id`='3' AND `user_id`!='BA_10000'");
            while (rs.next()) {
                v.add(rs.getString("first_name") + " " + rs.getString("last_name"));
                employeeMap.put(rs.getString("first_name") + " " + rs.getString("last_name"), rs.getString("user_id"));
            }
            DefaultComboBoxModel model = new DefaultComboBoxModel(v);
            jComboBox1.setModel(model);

            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            errorLogger.warning("Employee LOADING Exception; Error: " + e);
        }
    }

    private void loadStatus() {
        Vector<String> v = new Vector<>();
        try {
            v.add("Select Status");
            ResultSet rs = MySQL.execute("SELECT * FROM `status`");
            while (rs.next()) {
                v.add(rs.getString("status"));
                statusMap.put(rs.getString("status"), rs.getString("id"));
            }
            DefaultComboBoxModel model = new DefaultComboBoxModel(v);
            jComboBox4.setModel(model);

            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            errorLogger.warning("STATUS LOADING Exception; Error: " + e);
        }
    }

    private void loadTF() {
        Vector<String> v = new Vector<>();
        try {
            v.add("Select Status");
            ResultSet rs = MySQL.execute("SELECT * FROM `t_f_status`");
            while (rs.next()) {
                v.add(rs.getString("t_f_status"));
                tfMap.put(rs.getString("t_f_status"), rs.getString("id"));
            }
            DefaultComboBoxModel model = new DefaultComboBoxModel(v);
            jComboBox2.setModel(model);

            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            errorLogger.warning("STATUS LOADING Exception; Error: " + e);
        }
    }

    public static boolean isDate1NotLater(Date date1, Date date2) {
        if (date1 == null && date2 == null) {
//            querySelection = true;
            return true;
        }
        if (date1 == null && date2 != null) {
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

    private String convertDateString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    private void reset() {
        jDateChooser1.setDate(null);
        jDateChooser2.setDate(null);
        jComboBox1.setSelectedIndex(0);
        jComboBox2.setSelectedIndex(0);
        jComboBox3.setSelectedIndex(0);
        jComboBox4.setSelectedIndex(0);
        jComboBox5.setSelectedIndex(0);
        jTextField1.setText("");
        loadReservations("");
    }

    private void loadReservations(String searchText) {
        try {

            Date date1 = jDateChooser1.getDate();
            Date date2 = jDateChooser2.getDate();

            String query = "SELECT "
                    + "reservation.id AS resid, "
                    + "reservation.date AS date, "
                    + "reservation.start_time AS stime, "
                    + "reservation.end_time AS etime, "
                    + "`status`.`status` AS status, "
                    + "t_f_status.t_f_status AS cancel_status, "
                    + "customer.id AS cid, "
                    + "customer.first_name AS cfname, "
                    + "customer.last_name AS clname, "
                    + "customer.mobile AS cmobile, "
                    + "employee.user_id AS eid, "
                    + "employee.first_name AS efname, "
                    + "employee.last_name AS elname "
                    + "FROM reservation "
                    + "INNER JOIN customer ON reservation.customer_id=customer.id "
                    + "INNER JOIN `status` ON reservation.status_id=`status`.`id` "
                    + "INNER JOIN employee ON reservation.employee_user_id=employee.user_id "
                    + "INNER JOIN t_f_status ON reservation.cancel_status=t_f_status.id "
                    + "WHERE (customer.mobile LIKE '%" + searchText + "%' OR "
                    + "customer.first_name LIKE '%" + searchText + "%' OR "
                    + "employee.last_name LIKE '%" + searchText + "%' OR "
                    + "customer.id LIKE '%" + searchText + "%')";

            // Append filters based on combo box selections
            if (jComboBox2.getSelectedIndex() == 0) {

            } else {
                query += " AND `reservation`.`cancel_status`='" + jComboBox2.getSelectedIndex() + "'";
            }
            if (jComboBox4.getSelectedIndex() == 0) {
                query += " AND `reservation`.`status_id`='1'";
            } else {
                query += " AND `reservation`.`status_id`='" + jComboBox4.getSelectedIndex() + "'";
            }

            if (jComboBox1.getSelectedIndex() != 0) {
                query += " AND `reservation`.`employee_user_id`='" + employeeMap.get(jComboBox1.getSelectedItem().toString()) + "'";
            }

            if (date1 != null) {
                if (date2 != null) {
                    if (isDate1NotLater(date1, date2)) {
                        query += " AND reservation.date BETWEEN '" + convertDateString(date1) + "' AND '" + convertDateString(date2) + "'";
                    }
                } else {
                    query += " AND reservation.date >= '" + convertDateString(date1) + "'";
                }
            }

            query += " ORDER BY reservation.date " + jComboBox5.getSelectedItem().toString();

            ResultSet rs = MySQL.execute(query);

            DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
            tableModel.setRowCount(0);
            int rowNO = 0;
            while (rs.next()) {
                rowNO++;
                Vector<String> v = new Vector<>();
                v.add(String.valueOf(rowNO));
                v.add(rs.getString("resid"));
                v.add(rs.getString("date"));
                v.add(rs.getString("stime"));
                v.add(rs.getString("etime"));
                v.add(rs.getString("cmobile"));
                v.add(rs.getString("cfname") + " " + rs.getString("clname"));
                v.add(rs.getString("efname") + " " + rs.getString("elname"));
                v.add(rs.getString("cancel_status"));
                tableModel.addRow(v);
//                System.out.println("com.ruzzz.nemo.panel.CustomerPanel.loadCustomer()");
            }

            rs.close();
        } catch (Exception e) {
            errorLogger.warning("RESERVATION SEARCH ERROR; Error: " + e);
        }
    }

    private void printExcel() {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet spreadsheet = workbook.createSheet(" Employee Data ");
            XSSFRow row;

            Map<String, Object[]> studentData = new TreeMap<String, Object[]>();

            for (int i = 0; i < jTable1.getRowCount(); i++) {
                studentData.put(String.valueOf(i),
                        new Object[]{
                            jTable1.getValueAt(i, 0),
                            jTable1.getValueAt(i, 1),
                            jTable1.getValueAt(i, 2),
                            jTable1.getValueAt(i, 3),
                            jTable1.getValueAt(i, 4),
                            jTable1.getValueAt(i, 5),
                            jTable1.getValueAt(i, 6),
                            jTable1.getValueAt(i, 7),
                            jTable1.getValueAt(i, 8),
                            jTable1.getValueAt(i, 9)
                        });
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
                    new File("C:/Users/Acer/Documents/NetBeansProjects/SaloonNemo/excel/" + jTextField5.getText() + "-" + getCurrentDate() + "-" + String.valueOf(System.currentTimeMillis()) + jComboBox3.getSelectedItem().toString()));

            workbook.write(out);
            out.close();
        } catch (Exception e) {
            errorLogger.warning("TABLE EXPORT ERROR; Error: " + e);
        }
    }

    private static String getCurrentDate() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return currentDate.format(formatter);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        timePicker1 = new raven.datetime.component.time.TimePicker();
        timePicker2 = new raven.datetime.component.time.TimePicker();
        jPanel6 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jButton2 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jComboBox5 = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel11 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jComboBox3 = new javax.swing.JComboBox<>();
        jButton3 = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setText("Reservation Management");

        jPanel1.setLayout(new java.awt.GridLayout(2, 4, 20, 0));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel1.setText("From");
        jPanel1.add(jLabel1);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel2.setText("To");
        jPanel1.add(jLabel2);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jPanel1.add(jLabel3);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel4.setText("Barber/Hairdresser");
        jPanel1.add(jLabel4);
        jPanel1.add(jDateChooser1);
        jPanel1.add(jDateChooser2);

        jButton2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton2.setText("Sort");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2);

        jComboBox1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });
        jPanel1.add(jComboBox1);

        jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        jLabel5.setText("Search By Customer(Mobile, Name, ID)......");

        jComboBox2.setToolTipText("");
        jComboBox2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox2ItemStateChanged(evt);
            }
        });

        jLabel7.setText("Cancle Status");

        jComboBox4.setToolTipText("");
        jComboBox4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox4ItemStateChanged(evt);
            }
        });

        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ASC", "DESC" }));
        jComboBox5.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox5ItemStateChanged(evt);
            }
        });

        jLabel8.setText("Pending status");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField1)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 313, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jComboBox2, 0, 116, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox4, 0, 96, Short.MAX_VALUE))
                .addGap(20, 20, 20)
                .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jComboBox2, jComboBox4, jComboBox5});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jComboBox2, jComboBox4, jComboBox5, jTextField1});

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/ruzzz/nemo/img/6127257_multimedia_music_refresh_repeat_song_icon (1).png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 956, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator2))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addComponent(jLabel6))
                .addGap(5, 5, 5)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel6Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton1, jLabel6});

        add(jPanel6, java.awt.BorderLayout.PAGE_START);

        jLabel9.setText("File Name");

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { ".csv", ".xlsx" }));
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(51, 102, 255));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/ruzzz/nemo/img/8665756_print_icon (2) (1).png"))); // NOI18N
        jButton3.setText("Print");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(617, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField5, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );

        add(jPanel11, java.awt.BorderLayout.PAGE_END);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "Reservation(ID)", "Date", "Start Time", "End Time", "Customer(No.)", "Customer", "Employee", "Cancel(t,f)"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setMinWidth(50);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(50);
            jTable1.getColumnModel().getColumn(0).setMaxWidth(50);
            jTable1.getColumnModel().getColumn(8).setMinWidth(70);
            jTable1.getColumnModel().getColumn(8).setPreferredWidth(70);
            jTable1.getColumnModel().getColumn(8).setMaxWidth(70);
        }

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 997, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE))
        );

        add(jPanel12, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        loadReservations(jTextField1.getText());
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if (!jTextField5.getText().isEmpty()) {
            printExcel();
        } else {
            JOptionPane.showMessageDialog(null, "File Name is Required!");
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        loadReservations(jTextField1.getText());
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        loadReservations(jTextField1.getText());
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jComboBox2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox2ItemStateChanged
        if (jComboBox2.getSelectedIndex() == 1) {
            jComboBox4.setSelectedIndex(2);
            loadReservations(jTextField1.getText());
        } else {
            loadReservations(jTextField1.getText());
        }
    }//GEN-LAST:event_jComboBox2ItemStateChanged

    private void jComboBox5ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox5ItemStateChanged
        loadReservations(jTextField1.getText());
    }//GEN-LAST:event_jComboBox5ItemStateChanged

    private void jComboBox4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox4ItemStateChanged
        loadReservations(jTextField1.getText());
    }//GEN-LAST:event_jComboBox4ItemStateChanged

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        loadReservations(jTextField1.getText());
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        reset();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();

        int row = jTable1.getSelectedRow();
        if (evt.getClickCount() == 2) {
            ReservationDeatailDialog rdd = new ReservationDeatailDialog(cpanel, false, dtm.getValueAt(row, 1).toString());
            rdd.setVisible(true);
        }
    }//GEN-LAST:event_jTable1MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField5;
    private raven.datetime.component.time.TimePicker timePicker1;
    private raven.datetime.component.time.TimePicker timePicker2;
    // End of variables declaration//GEN-END:variables
}

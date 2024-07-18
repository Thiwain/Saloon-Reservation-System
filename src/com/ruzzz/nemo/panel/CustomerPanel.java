/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.ruzzz.nemo.panel;

import com.ruzzz.nemo.connection.MySQL;
import com.ruzzz.nemo.gui.ControlPanel;
import com.ruzzz.nemo.model.CustomerDataBean;
import com.ruzzz.nemo.model.LoggedUserData;
import com.ruzzz.nemo.model.Role;
import static com.ruzzz.nemo.properties.LoggerConfig.errorLogger;
import static com.ruzzz.nemo.properties.LoggerConfig.infoLogger;
import com.ruzzz.nemo.validation.ValidationProcess;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;
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

public class CustomerPanel extends javax.swing.JPanel {

    private static ControlPanel cP;

    private static ReservationPanel rp;

    public CustomerPanel(JFrame cp, ReservationPanel rP) {
        initComponents();
        loadGenders();
        customerSort();
        cP = (ControlPanel) cp;
        jButton4.setEnabled(false);
        rp = rP;
        try {
            if (!LoggedUserData.getUserRole().equals(Role.ADMIN.name())) {
                jPanel11.setVisible(false);
                jDateChooser1.setEnabled(false);
                jDateChooser2.setEnabled(false);
                jButton1.setEnabled(false);
                jComboBox1.setEnabled(false);
                jComboBox3.setEnabled(false);
                jButton3.setVisible(false);
            }
        } catch (Exception e) {
            errorLogger.warning("LOGGED USER DATA LOADING Exception; Error: " + e);
        }
    }

    public void hideForCaisher() {
        jPanel11.setVisible(false);
        jDateChooser1.setEnabled(false);
        jDateChooser2.setEnabled(false);
        jButton1.setEnabled(false);
        jComboBox1.setEnabled(false);
        jComboBox3.setEnabled(false);
        jButton3.setVisible(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jComboBox4 = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jComboBox2 = new javax.swing.JComboBox<>();
        jTextField5 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setLayout(new java.awt.BorderLayout());

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/ruzzz/nemo/img/6843045_customer_feedback_happy_performance_satisfaction_icon_1.png"))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        jLabel1.setText("Customer");

        jPanel1.setLayout(new java.awt.GridLayout(1, 4, 40, 20));

        jPanel4.setLayout(new java.awt.GridLayout(3, 1, 0, 5));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Mobile");
        jPanel4.add(jLabel2);

        jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });
        jPanel4.add(jTextField1);

        jPanel1.add(jPanel4);

        jPanel3.setLayout(new java.awt.GridLayout(3, 1, 0, 5));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("First Name");
        jPanel3.add(jLabel3);

        jTextField2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPanel3.add(jTextField2);

        jPanel3.add(jComboBox4);

        jPanel1.add(jPanel3);

        jPanel2.setLayout(new java.awt.GridLayout(3, 1, 0, 5));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Last Name");
        jPanel2.add(jLabel4);

        jTextField3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPanel2.add(jTextField3);

        jPanel1.add(jPanel2);

        jPanel6.setLayout(new java.awt.GridLayout(3, 1, 0, 5));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Email");
        jPanel6.add(jLabel5);

        jTextField4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPanel6.add(jTextField4);

        jPanel8.setLayout(new java.awt.GridLayout(1, 2, 10, 10));

        jButton4.setText("Update");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel8.add(jButton4);

        jButton5.setText("Add");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel8.add(jButton5);

        jPanel6.add(jPanel8);

        jPanel1.add(jPanel6);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/ruzzz/nemo/img/6127257_multimedia_music_refresh_repeat_song_icon (1).png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 944, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addGap(0, 27, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(jPanel10, java.awt.BorderLayout.PAGE_START);

        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(51, 102, 255));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/ruzzz/nemo/img/8665756_print_icon (2) (1).png"))); // NOI18N
        jButton2.setText("Print");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { ".csv", ".xlsx" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jLabel9.setText("File Name");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(564, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField5, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );

        add(jPanel11, java.awt.BorderLayout.PAGE_END);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ASC", "DESC" }));
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton1.setText("Sort");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("To");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("From");

        jComboBox3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox3ItemStateChanged(evt);
            }
        });

        jLabel10.setText("Sort by gender");

        jTextField6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField6KeyReleased(evt);
            }
        });

        jLabel11.setText("Search by Mobile, Name or Email");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                    .addComponent(jTextField6))
                .addContainerGap())
            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        jPanel7Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel6, jLabel7});

        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox1)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jDateChooser2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel7Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton1, jComboBox1, jComboBox3, jTextField6});

        jPanel7Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jDateChooser1, jLabel7});

        jTable1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Mobile", "First Name", "Last Name", "Gender", "Email", "Joined Dated"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
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
            jTable1.getColumnModel().getColumn(0).setMaxWidth(60);
        }

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(jPanel12, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    HashMap<String, String> genderMap = new HashMap<>();

    private void loadGenders() {
        Vector<String> v = new Vector<>();
        try {
            v.add("Select Gender");
            ResultSet rs = MySQL.execute("SELECT * FROM `gender`");
            while (rs.next()) {
                v.add(rs.getString("gender"));
                genderMap.put(rs.getString("gender"), rs.getString("id"));
            }
            DefaultComboBoxModel model = new DefaultComboBoxModel(v);
            DefaultComboBoxModel model2 = new DefaultComboBoxModel(v);
            jComboBox4.setModel(model);
            jComboBox3.setModel(model2);
        } catch (Exception e) {
            e.printStackTrace();
            errorLogger.warning("GENDER LOADING Exception; Error: " + e);
        }
    }

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private boolean validateCustomer() {
        if (!ValidationProcess.validateMobile(jTextField1.getText())) {
            return false;
        } else if (!ValidationProcess.names(jTextField2.getText())) {
            return false;
        } else if (!ValidationProcess.names(jTextField3.getText())) {
            return false;
        } else if (jComboBox4.getSelectedIndex() == 0) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Select a Gender");
            return false;
        } else if (!ValidationProcess.validateEmailCustomer(jTextField4.getText())) {
            return false;
        } else {
            return true;
        }
    }

    private void clearUserData() {
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField4.setText("");
        jComboBox4.setSelectedIndex(0);
        jComboBox3.setSelectedIndex(0);
        jComboBox1.setSelectedIndex(0);
        jDateChooser1.setDate(null);
        jDateChooser2.setDate(null);
        customerSort();
    }

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

        if (validateCustomer()) {
            try {

                ResultSet checkNo = MySQL.execute("SELECT * FROM `customer` WHERE `mobile`='" + jTextField1.getText() + "'");
                if (!checkNo.next()) {

                    int n = JOptionPane.showConfirmDialog(null, "Confirm Registration?", "", JOptionPane.YES_NO_OPTION);
                    if (n == JOptionPane.YES_NO_OPTION) {
                        MySQL.execute("INSERT INTO `saloon_nemo`.`customer` (`mobile`, `first_name`, `last_name`, `email`, `gender_id`, `time_stamp`) "
                                + "VALUES ('" + jTextField1.getText() + "', '" + jTextField2.getText() + "', '" + jTextField3.getText() + "', '" + jTextField4.getText() + "', " + genderMap.get(jComboBox4.getSelectedItem()) + ",  CURRENT_TIMESTAMP)");

                        infoLogger.info("Customer Registered by Name:" + LoggedUserData.getFirstName() + " " + LoggedUserData.getLastName() + "| Customer :" + "Mobile:" + jTextField1.getText() + "Name :" + jTextField2.getText() + " " + jTextField3.getText());
                        MySQL.execute("INSERT INTO `saloon_nemo`.`log_record` (`employee_user_id`, `date_time`, `description`) "
                                + "VALUES ('" + LoggedUserData.getUserId() + "', CURRENT_TIMESTAMP, "
                                + "'CUSTOMER:" + jTextField1.getText() + "," + jTextField2.getText() + " " + jTextField3.getText() + " "
                                + " -> Registered By" + LoggedUserData.getFirstName() + " " + LoggedUserData.getLastName() + "')");
                        clearUserData();
                        customerSort();
                        Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "User Registration success !");
                    }
                } else {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "This mobile number already taken");
                    jTextField1.setText("");
                }

            } catch (Exception e) {
                e.printStackTrace();
                errorLogger.warning("CUSTOMER REGISTRATION Exception; Error: " + e);
            }
        }

    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
//        cP.loadCustomer();
        clearUserData();
    }//GEN-LAST:event_jButton3ActionPerformed

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

    private static String getCurrentDate() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return currentDate.format(formatter);
    }

    private static boolean querySelection = false;
    private static boolean querySelection2 = false;

    public static boolean isDate1NotLater(Date date1, Date date2) {
        if (date1 == null && date2 == null) {
            querySelection = true;
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

    private void customerSort() {
        try {
            ResultSet rs;
            String query;

            Date date1 = jDateChooser1.getDate();
            Date date2 = jDateChooser2.getDate();

            boolean isNotLater = isDate1NotLater(date1, date2);

            if (!isNotLater) {
                rs = null;
                JOptionPane.showMessageDialog(null, "Invalid Date Selection");
            } else {
                if (querySelection) {
                    query = "SELECT * FROM `customer` INNER JOIN `gender` ON `customer`.`gender_id`=`gender`.`id`";
                    if (jComboBox3.getSelectedIndex() != 0) {
                        query += "AND customer.gender_id='" + genderMap.get(jComboBox3.getSelectedItem().toString()) + "' ";
                    }
                    query += "ORDER BY time_stamp " + jComboBox1.getSelectedItem().toString() + "";
                    rs = MySQL.execute(query);
                } else {
                    query = "SELECT * FROM customer INNER JOIN gender ON customer.gender_id = gender.id"
                            + " WHERE customer.time_stamp BETWEEN '" + convertDateString(jDateChooser1.getDate().toString()) + " 00:00:00' "
                            + "AND '" + getCurrentDate() + " 23:59:59' ";
                    if (jComboBox3.getSelectedIndex() != 0) {
                        query += "AND customer.gender_id='" + genderMap.get(jComboBox3.getSelectedItem().toString()) + "' ";
                    }
                    query += "ORDER BY time_stamp " + jComboBox1.getSelectedItem().toString() + "";
                    rs = MySQL.execute(query);
                }

            }

            DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
            tableModel.setRowCount(0);
            int rowNO = 0;
            while (rs.next()) {
                rowNO++;
                Vector<String> customerList = new Vector<>();
                customerList.add(String.valueOf(rowNO));
                customerList.add(rs.getString("mobile"));
                customerList.add(rs.getString("first_name"));
                customerList.add(rs.getString("last_name"));
                customerList.add(rs.getString("gender"));
                customerList.add(rs.getString("email"));
                customerList.add(rs.getString("time_stamp"));
                tableModel.addRow(customerList);
//                System.out.println("com.ruzzz.nemo.panel.CustomerPanel.loadCustomer()");
            }

        } catch (Exception e) {
            errorLogger.warning("CUSTOMER LOADING loadCustomer() Exception; Error: " + e);
        }
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        customerSort();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        customerSort();
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if (validateCustomer()) {
            try {
                int i = JOptionPane.showConfirmDialog(null, "Do You Want To Update This Customer?", "", JOptionPane.YES_NO_OPTION);
                if (i == JOptionPane.YES_OPTION) {
                    MySQL.execute("UPDATE `saloon_nemo`.`customer` "
                            + "SET `first_name`='" + jTextField2.getText() + "', `last_name`='" + jTextField3.getText() + "',  `email`='" + jTextField4.getText() + "' "
                            + "WHERE  `mobile`='" + jTextField1.getText() + "'");

                    MySQL.execute("INSERT INTO `saloon_nemo`.`log_record` (`employee_user_id`, `date_time`, `description`) "
                            + "VALUES ('" + LoggedUserData.getUserId() + "', CURRENT_TIMESTAMP, "
                            + "'CUSTOMER:" + jTextField1.getText() + "," + jTextField2.getText() + " " + jTextField3.getText() + " "
                            + " -> Updated By" + LoggedUserData.getFirstName() + " " + LoggedUserData.getLastName() + "')");

                    infoLogger.info("Customer Updated by Name:" + LoggedUserData.getFirstName() + " " + LoggedUserData.getLastName() + "| Customer :" + "Mobile:" + jTextField1.getText() + "Name :" + jTextField2.getText() + " " + jTextField3.getText());
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "User Updated !");
                    clearUserData();
                    jTextField1.setEditable(true);
                    jButton5.setEnabled(true);
                    jButton4.setEnabled(false);
                    jComboBox4.setEnabled(true);
                    customerSort();
                }
            } catch (Exception e) {
                errorLogger.warning("CUSTOMER DATA UPDATE Exception; Error: " + e);
            }
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

        if (evt.getClickCount() == 1) {

            jTextField1.setEditable(false);
            jButton5.setEnabled(false);
            jButton4.setEnabled(true);
            jComboBox4.setEnabled(false);

            int row = jTable1.getSelectedRow();

            String mobile = jTable1.getValueAt(row, 1).toString();
            jTextField1.setText(mobile);
            String fname = jTable1.getValueAt(row, 2).toString();
            jTextField2.setText(fname);
            String lname = jTable1.getValueAt(row, 3).toString();
            jTextField3.setText(lname);
            String gender = genderMap.get(jTable1.getValueAt(row, 4).toString());
            jComboBox4.setSelectedIndex(Integer.parseInt(gender));
            String email = jTable1.getValueAt(row, 5).toString();
            jTextField4.setText(email);

        }

        if (evt.getClickCount() == 2) {
            int row = jTable1.getSelectedRow();
            String mobile = jTable1.getValueAt(row, 1).toString();
            CustomerDataBean.setcMobile(mobile);
            String fname = jTable1.getValueAt(row, 2).toString();
            CustomerDataBean.setcFname(fname);
            String lname = jTable1.getValueAt(row, 3).toString();
            CustomerDataBean.setcLname(lname);
            String email = jTable1.getValueAt(row, 5).toString();
            CustomerDataBean.setcEmail(email);
            rp.loadCusData();
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Customer details passed !");
        }

    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (!jTextField5.getText().isEmpty()) {
            printExcel();
        } else {
            JOptionPane.showMessageDialog(null, "File Name is Required!");
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField6KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField6KeyReleased
        searchCustomer();
    }//GEN-LAST:event_jTextField6KeyReleased

    private void searchCustomer() {
        String searchText = jTextField6.getText();
        jComboBox3.setSelectedIndex(0);

        try {
            ResultSet rs = MySQL.execute("SELECT *\n"
                    + "FROM customer INNER JOIN gender ON customer.gender_id = gender.id\n"
                    + "WHERE mobile LIKE '" + searchText + "%'\n"
                    + "   OR first_name LIKE '%" + searchText + "%'\n"
                    + "   OR last_name LIKE '%" + searchText + "%'\n"
                    + "   OR email LIKE '%" + searchText + "%'");

            jComboBox3.setSelectedIndex(0);

            DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
            tableModel.setRowCount(0);
            int rowNO = 0;
            while (rs.next()) {
                rowNO++;
                Vector<String> customerList = new Vector<>();
                customerList.add(String.valueOf(rowNO));
                customerList.add(rs.getString("mobile"));
                customerList.add(rs.getString("first_name"));
                customerList.add(rs.getString("last_name"));
                customerList.add(rs.getString("gender"));
                customerList.add(rs.getString("email"));
                customerList.add(rs.getString("time_stamp"));
                tableModel.addRow(customerList);
//                System.out.println("com.ruzzz.nemo.panel.CustomerPanel.loadCustomer()");
            }

        } catch (Exception e) {
            errorLogger.warning("Customer Loading error from search; Error: " + e);
        }

    }

    private void jComboBox3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox3ItemStateChanged
        customerSort();
    }//GEN-LAST:event_jComboBox3ItemStateChanged

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        String searchText = jTextField1.getText();

        try {
            ResultSet rs = MySQL.execute("SELECT *\n"
                    + "FROM customer INNER JOIN gender ON customer.gender_id = gender.id\n"
                    + "WHERE mobile LIKE '" + searchText + "%'");

            jComboBox3.setSelectedIndex(0);

            DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
            tableModel.setRowCount(0);
            int rowNO = 0;
            while (rs.next()) {
                rowNO++;
                Vector<String> customerList = new Vector<>();
                customerList.add(String.valueOf(rowNO));
                customerList.add(rs.getString("mobile"));
                customerList.add(rs.getString("first_name"));
                customerList.add(rs.getString("last_name"));
                customerList.add(rs.getString("gender"));
                customerList.add(rs.getString("email"));
                customerList.add(rs.getString("time_stamp"));
                tableModel.addRow(customerList);
//                System.out.println("com.ruzzz.nemo.panel.CustomerPanel.loadCustomer()");
            }

        } catch (Exception e) {
            errorLogger.warning("Customer Loading error from search; Error: " + e);
        }
    }//GEN-LAST:event_jTextField1KeyReleased

    private void printExcel() {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet spreadsheet = workbook.createSheet(" Customer Data ");
            XSSFRow row;

            Map<String, Object[]> studentData = new TreeMap<String, Object[]>();

            for (int i = 0; i < jTable1.getRowCount(); i++) {
                studentData.put(String.valueOf(i),
                        new Object[]{jTable1.getValueAt(i, 0),
                            jTable1.getValueAt(i, 1),
                            jTable1.getValueAt(i, 2),
                            jTable1.getValueAt(i, 3),
                            jTable1.getValueAt(i, 4),
                            jTable1.getValueAt(i, 5),
                            jTable1.getValueAt(i, 6)});
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    // End of variables declaration//GEN-END:variables
}

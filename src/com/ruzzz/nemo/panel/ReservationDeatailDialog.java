/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/AWTForms/Dialog.java to edit this template
 */
package com.ruzzz.nemo.panel;

import com.ruzzz.nemo.connection.MySQL;
import com.ruzzz.nemo.model.EmailSender;
import com.ruzzz.nemo.model.Status;
import static com.ruzzz.nemo.properties.LoggerConfig.errorLogger;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import raven.toast.Notifications;

/**
 *
 * @author Acer
 */
public class ReservationDeatailDialog extends java.awt.Dialog {

    private static String reservationId;
    private String customerId;
    private Double serviceToal = 00.0;
    private final String invoiceId = generateID();

    public ReservationDeatailDialog(java.awt.Frame parent, boolean modal, String resId) {
        super(parent, modal);
        initComponents();
        this.setTitle("Reservation");
        reservationId = resId;
        loadData(resId);
    }

    private static String getCurrentDateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }

    private void loadData(String resId) {
        jLabel1.setText(resId);
        try {
            ResultSet rs = MySQL.execute("SELECT\n"
                    + "customer.id AS cid,\n"
                    + "customer.first_name AS cfname,\n"
                    + "customer.last_name AS clname,\n"
                    + "customer.mobile AS cmobile,\n"
                    + "customer.email AS cemail,\n"
                    + "reservation.id AS resid,\n"
                    + "reservation.date AS date,\n"
                    + "reservation.start_time AS stime,\n"
                    + "reservation.end_time AS etime,\n"
                    + "employee.user_id AS eid,\n"
                    + "employee.first_name AS efname,\n"
                    + "employee.last_name AS elname,\n"
                    + "t_f_status.t_f_status AS cancel_sts,\n"
                    + "`status`.`status` AS pending_sts\n"
                    + "FROM\n"
                    + "reservation\n"
                    + "INNER JOIN customer ON reservation.customer_id=customer.id\n"
                    + "INNER JOIN t_f_status ON reservation.cancel_status=t_f_status.id\n"
                    + "INNER JOIN `status` ON reservation.status_id=`status`.id\n"
                    + "INNER JOIN employee ON reservation.employee_user_id=employee.user_id\n"
                    + "WHERE reservation.id='" + resId + "'");

            if (rs.next()) {
                customerId = rs.getString("cid");
                jLabel6.setText(rs.getString("cfname") + " " + rs.getString("clname"));
                jLabel7.setText(rs.getString("cmobile"));
                jLabel8.setText(rs.getString("cemail"));
                jLabel9.setText(rs.getString("date"));
                jLabel12.setText(convertTo12HourFormat(rs.getString("stime")));
                jLabel13.setText(convertTo12HourFormat(rs.getString("etime")));
                jLabel20.setText(rs.getString("efname") + " " + rs.getString("elname"));
                loadServicesToTable(resId);

                if (rs.getString("cancel_sts").equals(Status.TRUE.name())) {
                    jButton1.setEnabled(false);
                    jButton3.setEnabled(false);
                    jButton4.setEnabled(false);
                }
                if (rs.getString("pending_sts").equals(Status.INACTIVE.name())) {
                    jButton1.setEnabled(false);
                    jButton2.setEnabled(false);
                    jButton3.setEnabled(true);
                    jButton4.setEnabled(true);
                    jFormattedTextField1.setEditable(false);
                }
                if (rs.getString("pending_sts").equals(Status.ACTIVE.name())) {
                    jButton2.setEnabled(false);
                    jButton3.setEnabled(false);
                    jButton4.setEnabled(false);
                }

            }

        } catch (Exception e) {
            errorLogger.warning("RESERVATION DETAIL LOADING ERROR ; Error: " + e);
        }
    }

    private void loadServicesToTable(String resId) {
        try {
            ResultSet rs = MySQL.execute("SELECT\n"
                    + "service.id AS sid,\n"
                    + "service.service_name AS title,\n"
                    + "service.`description` AS des,\n"
                    + "service.cost + service.profit AS cost \n"
                    + "FROM\n"
                    + "reservation_has_service\n"
                    + "INNER JOIN service ON reservation_has_service.service_id=service.id\n"
                    + "WHERE reservation_has_service.reservation_id='" + resId + "'");

            DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
            tableModel.setRowCount(0);

            double total = 0.00;

            while (rs.next()) {
                Vector<String> v = new Vector<>();
                v.add(rs.getString("sid"));
                v.add(rs.getString("title"));
                v.add(rs.getString("des"));
                v.add(rs.getString("cost"));
                total += Double.parseDouble(rs.getString("cost"));
                serviceToal += total;
                tableModel.addRow(v);
            }

            jLabel16.setText(String.valueOf(total));

        } catch (Exception e) {
            errorLogger.warning("RESERVATION SERVICES LOADING ERROR ; Error: " + e);
        }
    }

    private static String convertTo12HourFormat(String time24Hour) throws ParseException {
        SimpleDateFormat inputFormat = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("hh:mm a");
        Date date = inputFormat.parse(time24Hour);
        return outputFormat.format(date);
    }

    private static String generateID() {
        Random random = new Random();
        int randomNumber = random.nextInt(9000000) + 1000000;
        return "I" + randomNumber;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jLabel19 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();

        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Segoe UI Black", 1, 20)); // NOI18N
        jLabel18.setText("Reservation Details :");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel1.setText("000000");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel2.setText("Customer Details");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel3.setText("Name:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel4.setText("Mobile:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel5.setText("Email:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel10.setText("Date:");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel11.setText("Start TIme:");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel14.setText("End Time:");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel15.setText("Reservation Details");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "#", "Title ", "Description", "Coast(Rs.)"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setMinWidth(50);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(50);
            jTable1.getColumnModel().getColumn(0).setMaxWidth(50);
            jTable1.getColumnModel().getColumn(3).setMinWidth(80);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(80);
            jTable1.getColumnModel().getColumn(3).setMaxWidth(80);
        }

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel17.setText("Total:");

        jFormattedTextField1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jFormattedTextField1.setText("00.0");

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel19.setText("Service Charge:");

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/ruzzz/nemo/img/9004773_tick_check_mark_accept_icon (1).png"))); // NOI18N
        jButton1.setText("Mark as Fullfilled");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton2.setText("Cancel Reservation");

        jButton3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/ruzzz/nemo/img/8665756_print_icon (2) (1).png"))); // NOI18N
        jButton3.setText("Print Invoice");

        jButton4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/ruzzz/nemo/img/1564504_email_letter_mail_message_icon.png"))); // NOI18N
        jButton4.setText("Send Email");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel21.setText("Barber/Hairdresser:");

        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator4)
                    .addComponent(jSeparator3)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jFormattedTextField1))))
                    .addComponent(jSeparator2)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel21)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 412, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton2, jButton3});

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel12, jLabel13, jLabel9});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel3))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel6)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel7))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel8))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(jLabel13)))
                    .addComponent(jSeparator5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton2)
                            .addComponent(jButton3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4)))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel3, jLabel4, jLabel5, jLabel6, jLabel7, jLabel8});

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel10, jLabel11, jLabel12, jLabel13, jLabel14, jLabel9});

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton1, jButton2, jButton3, jButton4});

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel20, jLabel21});

        add(jPanel2, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Closes the dialog
     */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            MySQL.execute("UPDATE `saloon_nemo`.`reservation` SET `status_id`=2 WHERE  `id`='" + reservationId + "'");

            MySQL.execute("INSERT INTO"
                    + " `saloon_nemo`.`invoice`"
                    + " (`invoice_id`, `reservation_id`, `date_time_issued`, `total`, `service_charge`)"
                    + " VALUES "
                    + "('" + invoiceId + "', '" + reservationId + "', CURRENT_TIMESTAMP, '" + serviceToal + "', '" + jFormattedTextField1.getText() + "')");

            DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                MySQL.execute("INSERT INTO `saloon_nemo`.`invoice_service` (`invoice_invoice_id`, `service_id`, `price`) VALUES ('" + invoiceId + "', '" + dtm.getValueAt(i, 0) + "', '" + dtm.getValueAt(i, 3) + "');");
            }

            MySQL.execute("UPDATE `saloon_nemo`.`reservation_has_service` SET `status_id`=2 WHERE  `reservation_id`='" + reservationId + "'");

            jButton1.setEnabled(false);
            jButton3.setEnabled(true);
            jButton4.setEnabled(true);
        } catch (Exception e) {
            errorLogger.warning("RESERVATION STATUS UPDATE ERROR ; Error: " + e);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try {
            ResultSet rs = MySQL.execute("SELECT * FROM invoice WHERE invoice.reservation_id='" + reservationId + "'");

            if (rs.next()) {
                String htmlTableRow = "";

                ResultSet rs2 = MySQL.execute("SELECT\n"
                        + "invoice_service.invoice_invoice_id AS in_id,\n"
                        + "invoice_service.price AS price,\n"
                        + "service.service_name AS title\n"
                        + "FROM invoice_service\n"
                        + "INNER JOIN service ON invoice_service.service_id=service.id\n"
                        + "WHERE invoice_service.invoice_invoice_id='" + rs.getString("invoice_id") + "'");

                while (rs2.next()) {
                    htmlTableRow += "<tr>"
                            + "<td style=\"border: 1px solid #ddd; padding: 8px;\">" + rs2.getString("title") + "</td>\n"
                            + "<td style=\"border: 1px solid #ddd; padding: 8px;\">Rs." + rs2.getString("price") + "</td>"
                            + "</tr>";
                }

                double grandTotal = Double.parseDouble(rs.getString("total")) + Double.parseDouble(rs.getString("service_charge"));
                String htmlEmailBody = "<!DOCTYPE html>\n"
                        + "<html lang=\"en\">\n"
                        + "<head>\n"
                        + "    <meta charset=\"UTF-8\">\n"
                        + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                        + "    <title>Invoice</title>\n"
                        + "</head>\n"
                        + "<body style=\"font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f4f4;\">\n"
                        + "    <div style=\"width: 80%; margin: 0 auto; background-color: #ffffff; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\">\n"
                        + "        <div style=\"text-align: center; padding: 10px 0; color: #333333; border-bottom: 2px solid #f4f4f4;\">\n"
                        + "            <h1>Saloon Nemo</h1>\n"
                        + "            <p>583/8A Bambalapitiya Galle Road, Colombo</p>\n"
                        + "        </div>\n"
                        + "\n"
                        + "        <div style=\"margin: 20px 0;\">\n"
                        + "            <h2>Invoice</h2>\n"
                        + "            <p><strong>Date Issued:</strong> " + rs.getString("date_time_issued") + "</p>\n"
                        + "            <p><strong>Invoice ID:</strong> " + rs.getString("invoice_id") + "</p>\n"
                        + "            <table style=\"width: 100%; border-collapse: collapse;\">\n"
                        + "                <thead>\n"
                        + "                    <tr>\n"
                        + "                        <th style=\"border: 1px solid #ddd; padding: 8px; background-color: #f4f4f4;\">Service</th>\n"
                        + "                        <th style=\"border: 1px solid #ddd; padding: 8px; background-color: #f4f4f4;\">Price (LKR)</th>\n"
                        + "                    </tr>\n"
                        + "                </thead>\n"
                        + "                <tbody>\n"
                        + htmlTableRow
                        + "                    <tr>\n"
                        + "                        <td style=\"border: 1px solid #ddd; padding: 8px; font-size: 1.2em; font-weight: bold;\">Total</td>\n"
                        + "                        <td style=\"border: 1px solid #ddd; padding: 8px; font-size: 1.2em; font-weight: bold;\">Rs." + rs.getString("total") + "</td>\n"
                        + "                    </tr>\n"
                        + "                    <tr>\n"
                        + "                        <td style=\"border: 1px solid #ddd; padding: 8px;\">Service Charge</td>\n"
                        + "                        <td style=\"border: 1px solid #ddd; padding: 8px;\">Rs." + rs.getString("service_charge") + "</td>\n"
                        + "                    </tr>\n"
                        + "                    <tr>\n"
                        + "                        <td style=\"border: 1px solid #ddd; padding: 8px; font-size: 1.2em; font-weight: bold;\">Grand Total</td>\n"
                        + "                        <td style=\"border: 1px solid #ddd; padding: 8px; font-size: 1.2em; font-weight: bold;\">Rs." + grandTotal + "</td>\n"
                        + "                    </tr>\n"
                        + "                </tbody>\n"
                        + "            </table>\n"
                        + "        </div>\n"
                        + "\n"
                        + "        <div style=\"margin: 20px 0;\">\n"
                        + "            <h2>Reservation Details</h2>\n"
                        + "            <table style=\"width: 100%; border-collapse: collapse;\">\n"
                        + "                <thead>\n"
                        + "                    <tr>\n"
                        + "                        <th style=\"border: 1px solid #ddd; padding: 8px; background-color: #f4f4f4;\">Date</th>\n"
                        + "                        <th style=\"border: 1px solid #ddd; padding: 8px; background-color: #f4f4f4;\">Start Time</th>\n"
                        + "                        <th style=\"border: 1px solid #ddd; padding: 8px; background-color: #f4f4f4;\">End Time</th>\n"
                        + "                    </tr>\n"
                        + "                </thead>\n"
                        + "                <tbody>\n"
                        + "                    <tr>\n"
                        + "                        <td style=\"border: 1px solid #ddd; padding: 8px;\">" + jLabel9.getText() + "</td>\n"
                        + "                        <td style=\"border: 1px solid #ddd; padding: 8px;\">" + jLabel12.getText() + "</td>\n"
                        + "                        <td style=\"border: 1px solid #ddd; padding: 8px;\">" + jLabel13.getText() + "</td>\n"
                        + "                    </tr>\n"
                        + "                </tbody>\n"
                        + "            </table>\n"
                        + "        </div>\n"
                        + "\n"
                        + "        <div style=\"margin: 20px 0; text-align: center; font-size: 1.2em; color: #333333;\">\n"
                        + "            <p>Thank you for choosing our saloon! We look forward to serving you again.</p>\n"
                        + "        </div>\n"
                        + "\n"
                        + "        <div style=\"text-align: center; padding: 10px 0; color: #333333; border-top: 2px solid #f4f4f4;\">\n"
                        + "            <p>&copy; 2024 Saloon Nemo. All rights reserved.</p>\n"
                        + "        </div>\n"
                        + "    </div>\n"
                        + "</body>\n"
                        + "</html>";

                EmailSender es = new EmailSender();
                if (es.sendEmail("Saloon Nemo INVOICE:" + rs.getString("invoice_id"), htmlEmailBody, jLabel8.getText()).equals("OK")) {
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Email sent successfully !");
                } else {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Email sending failed !");
                }
            }

        } catch (Exception e) {
            errorLogger.warning("INVOICE UPDATE ERROR ; Error: " + e);
        }
    }//GEN-LAST:event_jButton4ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JFormattedTextField jFormattedTextField1;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}

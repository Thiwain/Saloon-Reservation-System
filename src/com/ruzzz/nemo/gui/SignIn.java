/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.ruzzz.nemo.gui;

import com.ruzzz.nemo.connection.MySQL;
import com.ruzzz.nemo.model.EmailSender;
import com.ruzzz.nemo.model.LoggedUserData;
import com.ruzzz.nemo.model.PasswordUtil;
import com.ruzzz.nemo.model.Status;
import static com.ruzzz.nemo.properties.LoggerConfig.errorLogger;
import static com.ruzzz.nemo.properties.LoggerConfig.infoLogger;
import com.ruzzz.nemo.properties.ThemeManager;
import static com.ruzzz.nemo.properties.ThemeManager.applyTheme;
import com.ruzzz.nemo.validation.ValidationProcess;
import java.net.URL;
import javax.swing.ImageIcon;
import java.sql.ResultSet;
import java.util.Random;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import raven.toast.Notifications;

public class SignIn extends javax.swing.JFrame {

    public SignIn() {
        initComponents();

        this.setTitle("Log In");

        URL location = getClass().getResource("com/ruzzz/nemo/img/scissors_icon.png");
        if (location != null) {
            ImageIcon icon = new ImageIcon(location);
            // Use the icon as needed, for example:
            JLabel label = new JLabel(icon);
            add(label);
        } else {
            System.err.println("Resource not found: /resources/yourImage.png");
        }

        applyTheme();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jPasswordField1 = new javax.swing.JPasswordField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jCheckBox1 = new javax.swing.JCheckBox();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("ScriptC", 1, 36)); // NOI18N
        jLabel1.setText("Salon");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel3.setFont(new java.awt.Font("Segoe UI Black", 3, 36)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText(" NEmo");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setText("Log In");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel4.setText("Email");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel5.setText("Password");

        jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        jPasswordField1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jButton1.setText("Forgot Password ?");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton2.setText("Log In");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jCheckBox1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jCheckBox1.setText("Show");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(0, 102, 255));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/ruzzz/nemo/img/light-ico-sun-moon_s.png"))); // NOI18N
        jButton3.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/com/ruzzz/nemo/img/dark-ico-sun-moon_s.png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCheckBox1))
                            .addComponent(jTextField1))
                        .addContainerGap(30, Short.MAX_VALUE))))
            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton3)
                .addGap(79, 79, 79)
                .addComponent(jLabel1)
                .addGap(0, 0, 0)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGap(30, 30, 30))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton1, jLabel4, jLabel5, jPasswordField1, jTextField1});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public static int generateSixDigitNumber() {
        Random random = new Random();
        return 100000 + random.nextInt(900000);
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        String email = JOptionPane.showInputDialog(this, "Type Your Email");

        if (!email.isEmpty()) {
            if (ValidationProcess.validateEmail(email)) {
                try {
                    ResultSet rs = MySQL.execute("SELECT * FROM `login_data` WHERE `email`='" + email + "'");
                    if (rs.next()) {
                        if (rs.getString("status_id").equals("1")) {

                            int code = generateSixDigitNumber();

                            MySQL.execute("UPDATE `saloon_nemo`.`login_data` SET `vcode`='" + code + "' WHERE  `email`='" + email + "'");

                            EmailSender es = new EmailSender();
                            if (es.sendEmail("Saloon-Nemo(Your Password:" + String.valueOf(code) + ")", "<body style=\"font-family: 'Arial', sans-serif; color: #333; background-color: #f4f4f4; margin: 0; padding: 20px; text-align: center;\">\n"
                                    + "  <div style=\"max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ddd; border-radius: 10px; background-color: #fff;\">\n"
                                    + "    <h1 style=\"font-size: 24px; color: #2c3e50;\">Your Verification Is:</h1>\n"
                                    + "    <h3 style=\"font-size: 18px; color: #e74c3c; margin-top: 10px;\">" + String.valueOf(code) + "</h3>\n"
                                    + "  </div>\n"
                                    + "</body>", email).equals("OK")) {
                                Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Email Sent");
                                String vcode = JOptionPane.showInputDialog(this, "Type Your Verification Code Here ");
                                if (!vcode.isEmpty()) {
                                    ResultSet rs2 = MySQL.execute("SELECT * FROM `login_data` WHERE `email`='" + email + "' AND `vcode`='" + vcode + "'");
                                    if (rs2.next()) {
                                        Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Verification Success");
                                        ResultSet userData = MySQL.execute("SELECT * FROM `employee` INNER JOIN `role` ON `employee`.`role_id`=`role`.`id` WHERE `user_id`='" + rs.getString("employee_user_id") + "'");

                                        if (userData.next()) {

                                            LoggedUserData.setUserId(userData.getString("user_id"));
                                            LoggedUserData.setUserEmail(jTextField1.getText());
                                            LoggedUserData.setFirstName(userData.getString("first_name"));
                                            LoggedUserData.setLastName(userData.getString("last_name"));
                                            LoggedUserData.setMobile(userData.getString("mobile"));
                                            LoggedUserData.setGenderId(userData.getString("gender_id"));
                                            LoggedUserData.setUserRole(userData.getString("role"));

                                            infoLogger.info("LOGIN WITH RECOVARY MODE -> SUCCESS; Name:" + LoggedUserData.getFirstName() + " " + LoggedUserData.getLastName() + "| " + "Email:" + email);

                                            MySQL.execute("INSERT INTO `saloon_nemo`.`log_record` (`employee_user_id`, `date_time`, `description`) VALUES ('" + userData.getString("user_id") + "', CURRENT_TIMESTAMP, '" + userData.getString("first_name") + " " + userData.getString("last_name") + " Logged in into the system with recovary mode')");

                                            new ProgressToControlePanel().setVisible(true);
                                            this.dispose();

                                        }
                                    }
                                } else {
                                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Code is required");
                                }
                            } else {
                                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Process Failed !");
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "This email was deactivated!!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "This email does not exist!");
                    }
                } catch (Exception e) {
                    errorLogger.warning("PASSWORD RECOVARY Exception; Error: " + e);
                }
            }
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        if (jCheckBox1.isSelected()) {
            jPasswordField1.setEchoChar('\u0000');
        } else {
            jPasswordField1.setEchoChar('\u2022');
        }
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private boolean loginValidation() {
        String email = jTextField1.getText();
        String password = String.valueOf(jPasswordField1.getPassword());
        if (!ValidationProcess.validateEmail(email)) {
            return false;
        } else if (!ValidationProcess.validatePassword(password)) {
            return false;
        } else {
            return true;
        }
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        String email = jTextField1.getText();
        String password = String.valueOf(jPasswordField1.getPassword());

        if (loginValidation()) {
            try {
                ResultSet rs = MySQL.execute("SELECT * FROM `login_data` INNER JOIN `status` ON `login_data`.`status_id`=`status`.`id` WHERE `email`='" + email + "'");
                if (rs.next()) {

                    PasswordUtil pwu = new PasswordUtil();

                    if (pwu.checkPassword(password, rs.getString("password"))) {

                        if (rs.getString("status").equals(Status.ACTIVE.name())) {
                            ResultSet userData = MySQL.execute("SELECT * FROM `employee` INNER JOIN `role` ON `employee`.`role_id`=`role`.`id` WHERE `user_id`='" + rs.getString("employee_user_id") + "'");

                            if (userData.next()) {

                                LoggedUserData.setUserId(userData.getString("user_id"));
                                LoggedUserData.setUserEmail(jTextField1.getText());
                                LoggedUserData.setFirstName(userData.getString("first_name"));
                                LoggedUserData.setLastName(userData.getString("last_name"));
                                LoggedUserData.setMobile(userData.getString("mobile"));
                                LoggedUserData.setGenderId(userData.getString("gender_id"));
                                LoggedUserData.setUserPassword(password);
                                LoggedUserData.setUserRole(userData.getString("role"));

                                infoLogger.info("LOGIN SUCCESS; Name:" + LoggedUserData.getFirstName() + " " + LoggedUserData.getLastName() + "| " + "Email:" + email);

                                MySQL.execute("INSERT INTO `saloon_nemo`.`log_record` (`employee_user_id`, `date_time`, `description`) VALUES ('" + userData.getString("user_id") + "', CURRENT_TIMESTAMP, '" + userData.getString("first_name") + " " + userData.getString("last_name") + " Logged in into the system')");

                                new ProgressToControlePanel().setVisible(true);
                                userData.close();
                                this.dispose();
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Deactivated Account !", "", JOptionPane.WARNING_MESSAGE);
                            infoLogger.info("DEACTIVATED ACCOUNT TRIED TO LOGIN: LOGIN FAILED;" + "| " + "Email:" + email);
                            MySQL.execute("INSERT INTO `saloon_nemo`.`log_record` (`employee_user_id`, `date_time`, `description`) VALUES ('" + rs.getString("employee_user_id") + "', CURRENT_TIMESTAMP, 'Deativated Email:" + email + " tried to logged in into the system')");

                        }
                    } else {
                        Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Invalid Password !");
                    }
                } else {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Invalid Email !");
                }
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
                errorLogger.warning("Login Exception; Error: " + e);
            }
        }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        ThemeManager.toggleTheme();
    }//GEN-LAST:event_jButton3ActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SignIn().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.ruzzz.nemo.panel;

import com.ruzzz.nemo.connection.MySQL;
import com.ruzzz.nemo.gui.ControlPanel;
import com.ruzzz.nemo.model.EmailSender;
import com.ruzzz.nemo.model.LoggedUserData;
import com.ruzzz.nemo.model.PasswordUtil;
import com.ruzzz.nemo.model.Role;
import static com.ruzzz.nemo.properties.LoggerConfig.errorLogger;
import static com.ruzzz.nemo.properties.LoggerConfig.infoLogger;
import com.ruzzz.nemo.validation.ValidationProcess;
import java.awt.Frame;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
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
public class EmployeePanel extends javax.swing.JPanel {
    
    private static ControlPanel controlPanel;
    
    HashMap<String, String> genderMap = new HashMap<>();
    HashMap<String, String> roleMap = new HashMap<>();
    HashMap<String, String> statusMap = new HashMap<>();
    
    public EmployeePanel(Frame cp) {
        initComponents();
        controlPanel = (ControlPanel) cp;
        
        loadGenders();
        loadRole();
        loadStatus();
        
        jPanel23.setVisible(false);
        
        loadEmployees("");
        
        reset();
    }
    
    private Boolean validatedUpdate() {
        if (!ValidationProcess.validateEmail(jTextField2.getText())) {
            return false;
        } else if (!ValidationProcess.names(jTextField3.getText())) {
            return false;
        } else if (!ValidationProcess.names(jTextField4.getText())) {
            return false;
        } else if (jComboBox3.getSelectedIndex() == 0) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Role is required!");
            return false;
        } else if (jComboBox7.getSelectedIndex() == 0) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Status is required!");
            return false;
        } else {
            return true;
        }
    }
    
    private void reset() {
        jButton3.setEnabled(false);
        jComboBox7.setEnabled(false);
        jComboBox3.setEnabled(false);
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField4.setText("");
        jTextField6.setText("");
        jTextField7.setText("");
        jTextField8.setText("");
        jTextField9.setText("");
        jTextField10.setText("");
        jComboBox1.setSelectedIndex(0);
        jComboBox9.setSelectedIndex(0);
        jComboBox4.setSelectedIndex(0);
        jComboBox5.setSelectedIndex(0);
        jComboBox8.setSelectedIndex(0);
        jLabel17.setText("");
        jButton5.setEnabled(false);
        loadEmployees("");
    }
    
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
            
            jComboBox4.setModel(model2);
            jComboBox1.setModel(model);
        } catch (Exception e) {
            e.printStackTrace();
            errorLogger.warning("GENDER LOADING Exception; Error: " + e);
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
            DefaultComboBoxModel model2 = new DefaultComboBoxModel(v);
            jComboBox7.setModel(model);
            jComboBox8.setModel(model2);
        } catch (Exception e) {
            e.printStackTrace();
            errorLogger.warning("STATUS LOADING Exception; Error: " + e);
        }
    }
    
    private void loadRole() {
        Vector<String> v = new Vector<>();
        try {
            v.add("Select Role");
            ResultSet rs = MySQL.execute("SELECT * FROM `role`");
            while (rs.next()) {
                v.add(rs.getString("role"));
                roleMap.put(rs.getString("role"), rs.getString("id"));
            }
            DefaultComboBoxModel model = new DefaultComboBoxModel(v);
            DefaultComboBoxModel model2 = new DefaultComboBoxModel(v);
            DefaultComboBoxModel model3 = new DefaultComboBoxModel(v);
            jComboBox5.setModel(model);
            jComboBox3.setModel(model2);
            jComboBox9.setModel(model3);
            
        } catch (Exception e) {
            e.printStackTrace();
            errorLogger.warning("ROLE LOADING Exception; Error: " + e);
        }
    }
    
    private void loadEmployees(String searchText) {
        try {
            
            String query;
            
            if (jComboBox4.getSelectedIndex() == 0 && jComboBox5.getSelectedIndex() == 0 && jComboBox8.getSelectedIndex() == 0) {
                query = "SELECT * FROM `employee` \n"
                        + "INNER JOIN `gender` ON `employee`.`gender_id`=`gender`.`id`\n"
                        + "INNER JOIN `login_data` ON `login_data`.`employee_user_id`=`employee`.`user_id` \n"
                        + "INNER JOIN `role` ON `employee`.`role_id`=`role`.`id`\n"
                        + "INNER JOIN `status` ON `login_data`.`status_id`=`status`.`id`\n"
                        + "WHERE \n"
                        + "`first_name` LIKE '%" + searchText + "%' \n"
                        + "OR \n"
                        + "`last_name` LIKE '%" + searchText + "%' \n"
                        + "OR \n"
                        + "`mobile` LIKE '%" + searchText + "%'\n"
                        + "OR\n"
                        + "`login_data`.`email` LIKE '%" + searchText + "%' ";
            } else {
                query = "SELECT * FROM `employee` \n"
                        + "INNER JOIN `gender` ON `employee`.`gender_id`=`gender`.`id`\n"
                        + "INNER JOIN `login_data` ON `login_data`.`employee_user_id`=`employee`.`user_id` \n"
                        + "INNER JOIN `role` ON `employee`.`role_id`=`role`.`id`\n"
                        + "INNER JOIN `status` ON `login_data`.`status_id`=`status`.`id`\n"
                        + "WHERE ";
            }
            
            if (jComboBox4.getSelectedIndex() != 0) {
                query += "`employee`.`gender_id`='" + genderMap.get(jComboBox4.getSelectedItem().toString()) + "'";
            }
            if (jComboBox5.getSelectedIndex() != 0) {
                query += "`employee`.`role_id`='" + roleMap.get(jComboBox5.getSelectedItem().toString()) + "'";
            }
            
            if (jComboBox8.getSelectedIndex() != 0) {
                query = "SELECT * FROM login_data \n"
                        + "INNER JOIN `employee` ON `login_data`.`employee_user_id`=`employee`.`user_id` \n"
                        + "INNER JOIN `gender` ON `employee`.`gender_id`=`gender`.`id`\n"
                        + "INNER JOIN `role` ON `employee`.`role_id`=`role`.`id`\n"
                        + "INNER JOIN `status` ON `login_data`.`status_id`=`status`.`id`\n"
                        + "WHERE\n"
                        + "login_data.status_id='" + statusMap.get(jComboBox8.getSelectedItem().toString()) + "'";
            }
            
            ResultSet rs = MySQL.execute(query);
            
            DefaultTableModel tableModel = (DefaultTableModel) jTable2.getModel();
            tableModel.setRowCount(0);
            int rowNO = 0;
            while (rs.next()) {
                rowNO++;
                Vector<String> employeeList = new Vector<>();
                employeeList.add(String.valueOf(rowNO));
                employeeList.add(rs.getString("user_id"));
                employeeList.add(rs.getString("first_name"));
                employeeList.add(rs.getString("last_name"));
                employeeList.add(rs.getString("mobile"));
                employeeList.add(rs.getString("email"));
                employeeList.add(rs.getString("gender"));
                employeeList.add(rs.getString("role"));
                employeeList.add(rs.getString("status"));
                tableModel.addRow(employeeList);
//                System.out.println("com.ruzzz.nemo.panel.CustomerPanel.loadCustomer()");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            errorLogger.warning("EMPLOYEE LOADING TABLE Exception; Error: " + e);
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
            XSSFSheet spreadsheet = workbook.createSheet(" Employee Data ");
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
                            jTable2.getValueAt(i, 6),
                            jTable2.getValueAt(i, 7),
                            jTable2.getValueAt(i, 8)});
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
                    new File("C:/Users/Acer/Documents/NetBeansProjects/SaloonNemo/excel/" + jTextField5.getText() + "-" + getCurrentDate() + "-" + String.valueOf(System.currentTimeMillis()) + jComboBox6.getSelectedItem().toString()));
            
            workbook.write(out);
            out.close();
        } catch (Exception e) {
            errorLogger.warning("TABLE EXPORT ERROR; Error: " + e);
        }
    }
    
    private Boolean validateInsert() {
        if (!ValidationProcess.validateMobile(jTextField6.getText())) {
            return false;
        } else if (!ValidationProcess.names(jTextField7.getText())) {
            return false;
        } else if (!ValidationProcess.names(jTextField8.getText())) {
            return false;
        } else if (!ValidationProcess.validateEmail(jTextField9.getText())) {
            return false;
        } else if (jComboBox1.getSelectedIndex() == 0) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Gender is required!");
            return false;
        } else if (!ValidationProcess.validatePassword(jTextField10.getText())) {
            return false;
        } else if (jComboBox9.getSelectedIndex() == 0) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Role is required!");
            return false;
        } else {
            return true;
        }
    }
    
    public static int generateRandomFiveDigitNumber() {
        Random random = new Random();
        int randomNumber = 10000 + random.nextInt(90000);
        return randomNumber;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jComboBox4 = new javax.swing.JComboBox<>();
        jButton3 = new javax.swing.JButton();
        jComboBox5 = new javax.swing.JComboBox<>();
        jComboBox7 = new javax.swing.JComboBox<>();
        jComboBox8 = new javax.swing.JComboBox<>();
        jButton5 = new javax.swing.JButton();
        jPanel17 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jComboBox6 = new javax.swing.JComboBox<>();
        jButton2 = new javax.swing.JButton();
        jPanel19 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jPanel9 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jComboBox9 = new javax.swing.JComboBox<>();
        jPanel12 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jPanel23 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();
        jLabel12 = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/ruzzz/nemo/img/7791667_necktie_businessman_suit_manager_employee_icon_1.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        jLabel2.setText("Employee");

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/ruzzz/nemo/img/6127257_multimedia_music_refresh_repeat_song_icon (1).png"))); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 619, Short.MAX_VALUE)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator1)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(2, 2, 2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setLayout(new java.awt.BorderLayout());

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N

        jPanel18.setLayout(new java.awt.GridLayout(2, 4, 20, 1));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel18.setText("Email");
        jPanel18.add(jLabel18);

        jLabel20.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel20.setText("First Name");
        jPanel18.add(jLabel20);

        jLabel21.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel21.setText("Last Name");
        jPanel18.add(jLabel21);

        jLabel22.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel22.setText("Role");
        jPanel18.add(jLabel22);

        jTextField2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPanel18.add(jTextField2);

        jTextField3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPanel18.add(jTextField3);

        jTextField4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPanel18.add(jTextField4);

        jComboBox3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPanel18.add(jComboBox3);

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel19.setText("Search By......");

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        jComboBox4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox4ItemStateChanged(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton3.setText("Update");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jComboBox5.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox5ItemStateChanged(evt);
            }
        });

        jComboBox8.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox8ItemStateChanged(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton5.setText("Reset Password");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel16Layout.createSequentialGroup()
                                .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel16Layout.createSequentialGroup()
                                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel16Layout.createSequentialGroup()
                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 183, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel16Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jComboBox4, jComboBox5, jComboBox8});

        jPanel16Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton3, jComboBox7});

        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton3)
                        .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(4, 4, 4)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel16Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton3, jButton5, jComboBox4, jComboBox5, jComboBox7, jComboBox8, jTextField1});

        jPanel3.add(jPanel16, java.awt.BorderLayout.PAGE_START);

        jPanel17.setLayout(new java.awt.BorderLayout());

        jLabel23.setText("File Name");

        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { ".csv", ".xlsx" }));
        jComboBox6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox6ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(51, 102, 255));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/ruzzz/nemo/img/8665756_print_icon (2) (1).png"))); // NOI18N
        jButton2.setText("Print");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addContainerGap(562, Short.MAX_VALUE)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel23)
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(jComboBox6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField5, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );

        jPanel17.add(jPanel21, java.awt.BorderLayout.PAGE_END);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "User_ID", "First Name", "Last Name", "Moble", "Email", "Gender", "Role", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
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
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(0).setMaxWidth(50);
        }

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 942, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel17.add(jPanel19, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel17, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Employee", jPanel3);

        jPanel4.setLayout(new java.awt.BorderLayout());

        jLabel24.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel24.setText("Employee Information");

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 589, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(359, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel4.add(jPanel20, java.awt.BorderLayout.PAGE_START);

        jPanel22.setLayout(new java.awt.GridLayout(6, 1, 5, 5));

        jPanel10.setLayout(new java.awt.GridLayout(2, 1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        jLabel3.setText("Mobile");
        jPanel10.add(jLabel3);

        jTextField6.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        jPanel10.add(jTextField6);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel22.add(jPanel5);

        jPanel11.setLayout(new java.awt.GridLayout(2, 1, 20, 5));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        jLabel4.setText("First Name");
        jPanel11.add(jLabel4);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        jLabel5.setText("Last Name");
        jPanel11.add(jLabel5);

        jTextField7.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        jPanel11.add(jTextField7);

        jTextField8.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        jPanel11.add(jTextField8);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel22.add(jPanel6);

        jPanel13.setLayout(new java.awt.GridLayout(2, 1, 5, 5));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        jLabel6.setText("Email");
        jPanel13.add(jLabel6);

        jTextField9.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        jPanel13.add(jTextField9);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel22.add(jPanel7);

        jPanel14.setLayout(new java.awt.GridLayout(2, 4, 20, 5));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        jLabel7.setText("Gender");
        jPanel14.add(jLabel7);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        jLabel8.setText("Status");
        jPanel14.add(jLabel8);

        jComboBox1.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        jPanel14.add(jComboBox1);

        jComboBox2.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ACTIVE" }));
        jComboBox2.setEnabled(false);
        jPanel14.add(jComboBox2);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel22.add(jPanel8);

        jPanel15.setLayout(new java.awt.GridLayout(2, 4, 20, 5));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        jLabel9.setText("Password");
        jPanel15.add(jLabel9);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        jLabel10.setText("Role");
        jPanel15.add(jLabel10);

        jTextField10.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        jPanel15.add(jTextField10);

        jComboBox9.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        jPanel15.add(jComboBox9);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel22.add(jPanel9);

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jButton1.setText("Register");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 13, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(303, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jPanel22.add(jPanel12);

        jPanel4.add(jPanel22, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Register", jPanel4);

        jPanel2.add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox6ActionPerformed

    }//GEN-LAST:event_jComboBox6ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (!jTextField5.getText().isEmpty()) {
            printExcel();
        } else {
            JOptionPane.showMessageDialog(null, "File Name is Required!");
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        loadEmployees(jTextField1.getText().toString());
        jComboBox4.setSelectedIndex(0);
        jComboBox5.setSelectedIndex(0);
        jComboBox8.setSelectedIndex(0);
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jComboBox4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox4ItemStateChanged
        jTextField1.setText("");
        jComboBox5.setSelectedIndex(0);
        jComboBox8.setSelectedIndex(0);
        loadEmployees("");
    }//GEN-LAST:event_jComboBox4ItemStateChanged

    private void jComboBox5ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox5ItemStateChanged
        jTextField1.setText("");
        jComboBox4.setSelectedIndex(0);
        jComboBox8.setSelectedIndex(0);
        loadEmployees("");
    }//GEN-LAST:event_jComboBox5ItemStateChanged

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
//        controlPanel.loadEmployee();
        reset();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jComboBox8ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox8ItemStateChanged
        jTextField1.setText("");
        jComboBox4.setSelectedIndex(0);
        jComboBox5.setSelectedIndex(0);
        loadEmployees("");
    }//GEN-LAST:event_jComboBox8ItemStateChanged
    
    private String userIdNo = String.valueOf(generateRandomFiveDigitNumber());
    private String userIDStart = "";

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (validateInsert()) {
            
            if (jComboBox9.getSelectedItem().equals(Role.ADMIN.name())) {
                userIDStart = "AD_";
            } else if (jComboBox9.getSelectedItem().equals(Role.CAISHER.name())) {
                userIDStart = "CA_";
            } else if (jComboBox9.getSelectedItem().equals(Role.BARBER.name())) {
                userIDStart = "BA_";
            }
            
            PasswordUtil pwu = new PasswordUtil();
            
            try {
                ResultSet rs = MySQL.execute("SELECT `email` FROM `login_data` WHERE `email`='" + jTextField9.getText() + "'");
                
                if (!rs.next()) {
                    jPanel23.setVisible(true);
                    
                    EmailSender es = new EmailSender();
                    
                    new Thread(() -> {
                        for (int i = 0; i <= 100; i++) {
                            jProgressBar1.setValue(i);
                            
                            jLabel12.setText(i + "%");
                            
                            switch (i) {
                                case 1:
                                    jLabel11.setText("Processing...");
                                    break;
                                case 20:
                                    jLabel11.setText("Processing........");
                                    break;
                                case 30:
                                    jLabel11.setText("Processing...");
                                    break;
                                case 40:
                                    jLabel11.setText("Verifying Data........");
                                    break;
                                case 50:
                                    jLabel11.setText("Sending Email...");
                                    String mailBody = "<body style=\"font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0;\">\n"
                                            + "    <div style=\"width: 100%; padding: 20px; background-color: #ffffff; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); margin: 50px auto; max-width: 600px; border-radius: 8px;\">\n"
                                            + "        <div style=\"background-color: #007BFF; color: #ffffff; padding: 10px 20px; text-align: center; border-radius: 8px 8px 0 0;\">\n"
                                            + "            <h2>System Login Credentials</h2>\n"
                                            + "        </div>\n"
                                            + "        <div style=\"padding: 20px;\">\n"
                                            + "            <p>Dear User,</p>\n"
                                            + "            <p>Your Account Registration Process Successful. Below are your login credentials:</p>\n"
                                            + "            <div style=\"background-color: #f9f9f9; padding: 15px; border-radius: 5px; margin: 20px 0;\">\n"
                                            + "                <p><strong>Email:</strong> <span id=\"user-email\">" + jTextField9.getText() + "</span></p>\n"
                                            + "                <p><strong>Password:</strong> <span id=\"user-password\">" + jTextField10.getText() + "</span></p>\n"
                                            + "            </div>\n"
                                            + "            <p>Please keep this information secure and do not share it with anyone.</p>\n"
                                            + "            <p>Best regards,<br>Saloon Nemo.</p>\n"
                                            + "        </div>\n"
                                            + "        <div style=\"text-align: center; color: #777777; font-size: 12px; padding: 10px 0;\">\n"
                                            + "            <p>&copy; 2024 Ruzzz. All rights reserved.</p>\n"
                                            + "        </div>\n"
                                            + "    </div>\n"
                                            + "</body>";
                                    String Respone = es.sendEmail("System Registration Successful", mailBody, jTextField9.getText());
                                    if (Respone.equals("OK")) {
                                        try {
                                            MySQL.execute("INSERT INTO `saloon_nemo`.`employee` "
                                                    + "(`user_id`, `first_name`, `last_name`, `mobile`, `gender_id`, `role_id`) "
                                                    + "VALUES"
                                                    + " ('" + userIDStart + userIdNo + "', '" + jTextField7.getText() + "', '" + jTextField8.getText() + "', '" + jTextField6.getText() + "', '" + genderMap.get(jComboBox1.getSelectedItem().toString()) + "', '" + roleMap.get(jComboBox9.getSelectedItem().toString()) + "')");
                                            MySQL.execute("INSERT INTO `saloon_nemo`.`login_data` "
                                                    + "(`employee_user_id`, `email`, `password`, `status_id`) "
                                                    + "VALUES "
                                                    + "('" + userIDStart + userIdNo + "', '" + jTextField9.getText() + "', '" + pwu.hashPassword(jTextField10.getText()) + "', 1)");
                                            
                                        } catch (Exception e) {
                                            errorLogger.warning("EMPLOYEE REGISTRATION ERROR; Error: " + e);
                                        }
                                        Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Registratin Successful");
                                    } else {
                                        Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Invalid Email");
                                    }
                                    break;
                                case 60:
                                    jLabel11.setText("Loading........");
                                    break;
                                case 70:
                                    jLabel11.setText("Loading...");
                                    break;
                                case 80:
                                    jLabel11.setText("Loading........");
                                    break;
                                case 90:
                                    jLabel11.setText("Loading...");
                                    break;
                                case 100:
                                    jLabel11.setText("Loading......");
                                    break;
                                default:
                                    break;
                            }
                            try {
                                Thread.sleep(30);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
//                        this.dispose();
                        reset();
                        jPanel23.setVisible(false);
                        Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "User Registered");
                    }).start();
                    
                } else {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "This Email is already taken");
                }
                
                infoLogger.info("EMPLOYEE(" + jTextField9.getText() + ", " + jTextField7.getText() + " " + jTextField8.getText() + ") REGISTERED BY " + LoggedUserData.getUserEmail() + "");
            } catch (Exception e) {
                errorLogger.warning("EMPLOYEE REGISTRATION ERROR; Error: " + e);
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if (validatedUpdate()) {
            try {
                
                MySQL.execute("UPDATE `saloon_nemo`.`employee` SET `first_name`='" + jTextField3.getText() + "', `last_name`='" + jTextField4.getText() + "',`role_id`='" + roleMap.get(jComboBox3.getSelectedItem().toString()) + "' WHERE  `user_id`='" + jLabel17.getText() + "'");
                
                ResultSet rs = MySQL.execute("SELECT * FROM login_data WHERE email='" + jTextField2.getText() + "' AND employee_user_id != '" + jLabel17.getText() + "'");
                if (!rs.next()) {
                    MySQL.execute("UPDATE `saloon_nemo`.`login_data` SET `email`='" + jTextField2.getText() + "', status_id='" + statusMap.get(jComboBox7.getSelectedItem().toString()) + "' WHERE  `employee_user_id`='" + jLabel17.getText() + "'");
                } else {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "This Email was already taken!");
                }
                
                Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "User Updated!");
                infoLogger.info("EMPLYOYEE DETAILS UPDATED" + "BY " + "Email:" + LoggedUserData.getUserEmail() + " OF " + "EMPLOYEE:" + jLabel17.getText());
                MySQL.execute("INSERT INTO `saloon_nemo`.`log_record` "
                        + "(`employee_user_id`, `date_time`, `description`)"
                        + " VALUES "
                        + "('" + LoggedUserData.getUserId() + "', CURRENT_TIMESTAMP, '" + "EMPLYOYEE DETAILS UPDATED" + "BY " + "Email:" + LoggedUserData.getUserEmail() + " OF " + "EMPLOYEE:" + jLabel17.getText() + "')");
                loadEmployees("");
                reset();
            } catch (Exception e) {
                errorLogger.warning("EMPLOYEE DATA UPDATE Exception; Error: " + e);
            }
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        if (evt.getClickCount() == 1) {
            jButton3.setEnabled(true);
            jComboBox7.setEnabled(true);
            jComboBox3.setEnabled(true);
            jButton5.setEnabled(true);
            int row = jTable2.getSelectedRow();
            
            jLabel17.setText(jTable2.getValueAt(row, 1).toString());
            
            jTextField2.setText(jTable2.getValueAt(row, 5).toString());
            jTextField3.setText(jTable2.getValueAt(row, 2).toString());
            jTextField4.setText(jTable2.getValueAt(row, 3).toString());
            jComboBox3.setSelectedItem(jTable2.getValueAt(row, 7).toString());
            jComboBox7.setSelectedItem(jTable2.getValueAt(row, 8).toString());
        }
    }//GEN-LAST:event_jTable2MouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        String password = JOptionPane.showInputDialog(this, "Type Your New Password");
        if (ValidationProcess.validatePassword(password)) {
            PasswordUtil pwu = new PasswordUtil();
            try {
                MySQL.execute("UPDATE `saloon_nemo`.`login_data` SET `password`='" + pwu.hashPassword(password) + "' WHERE  `employee_user_id`='" + jLabel17.getText() + "'");
                infoLogger.info("EMPLYOYEE PASSWORD UPDATED" + "BY " + "Email:" + LoggedUserData.getUserEmail() + " OF " + "EMPLOYEE:" + jLabel17.getText());
                Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Password Updated!");
            } catch (Exception e) {
                errorLogger.warning("NEW PASSWORD ASSIGN ERROR; Error: " + e);
            }
        }
    }//GEN-LAST:event_jButton5ActionPerformed

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
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JComboBox<String> jComboBox6;
    private javax.swing.JComboBox<String> jComboBox7;
    private javax.swing.JComboBox<String> jComboBox8;
    private javax.swing.JComboBox<String> jComboBox9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
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
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration//GEN-END:variables
}

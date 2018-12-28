/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csedudigitalassistant;

import com.bulenkov.darcula.DarculaLaf;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import javax.swing.JFrame;

import com.itextpdf.text.*;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FileDialog;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.Session;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.basic.BasicLookAndFeel;

/**
 *
 * @author farhan
 */
class HintTextArea extends javax.swing.JTextArea implements java.awt.event.FocusListener {
    
    private final String hint;
    private boolean showingHint;
    
    public HintTextArea(final String hint) {        
        super(hint);
        super.setForeground(Color.GRAY);
        this.hint = hint;
        this.showingHint = true;
        super.addFocusListener(this);
    }
    
    @Override
    public void focusGained(FocusEvent e) {
        if (this.getText().isEmpty()) {
            super.setForeground(Color.WHITE);
            super.setText("");
            showingHint = false;
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (this.getText().isEmpty()) {
            super.setForeground(Color.GRAY);
            super.setText(hint);
            showingHint = true;
        }
    }
    
    @Override
    public String getText() {
        return showingHint ? "" : super.getText();
    }
}

public class ApplicationWriting extends javax.swing.JFrame {

    /**
     * Creates new form ApplicationWriting
     */    
    
    public ApplicationWriting() {        
        initComponents();
    }
    
    String types[] = {"Leave of absence", "Make up incourse", "Rescheduling thesis presentation", "Custom"};    
    String years[] = {"1st", "2nd", "3rd", "4th"};
    String semesters[] = {"1st", "2nd"};
    String teachers[] = {"Rezaul sir", "Tomal sir", "Asif sir", "Farhan sir", "Zaber sir"};
    String target_addresses[] = {"rkarim@du.ac.bd", "tamal@cse.du.ac.bd", "asif@du.ac.bd", "farhan@cse.univdhaka.edu", "zaber@du.ac.bd"};
    
    public void sendMail(String target_address, String app_type, String name, String roll, String year, String semester) {
        final String username = "csedudigitalassistant@gmail.com";
        final String password = "csedudigital";
        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("csedudigitalassistant@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(target_address));
            message.setSubject("An application for "+app_type);
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("Dear sir,\nI am "+name+",roll no: "+roll+",a student of "+year+" Year "+semester+" Semester. Please find the application of mine provided as an attachment.\nThank you, sir.");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            messageBodyPart = new MimeBodyPart();
            String filename = "Application.pdf";
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName("Application.pdf");
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);
            Transport.send(message);            
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void sendMail(String target_address, String app_type, int val) {
        final String username = "csedudigitalassistant@gmail.com";
        final String password = "csedudigital";
        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("csedudigitalassistant@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(target_address));
            message.setSubject("An application for "+app_type);
            BodyPart messageBodyPart = new MimeBodyPart();
            if (val == 0) {
                messageBodyPart.setText("Dear sir,\nI am a student of your department. Please find the application of mine provided as an attachment.\nThank you, sir.");
            } else if (val == 1) {
                messageBodyPart.setText("Dear sir,\nI am the Class Representative of 4th year students of your department. Please find the application provided as an attachment.\nThank you, sir.");
            }
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            messageBodyPart = new MimeBodyPart();
            String filename = "Application.pdf";
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName("Application.pdf");
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);
            Transport.send(message);            
        } catch (MessagingException e) {
            throw new RuntimeException(e);
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

        jFrame1 = new javax.swing.JFrame();
        jLabel1 = new javax.swing.JLabel();
        type_selection = new javax.swing.JComboBox<>();
        mainPanel = new javax.swing.JPanel();
        panelOne = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        name_field = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        roll_field = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        year_selection = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        semester_selection = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        day_from = new javax.swing.JSpinner();
        month_from = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        day_to = new javax.swing.JSpinner();
        month_to = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        browseFileButton = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        destination = new javax.swing.JComboBox<>();
        panelTwo = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        name_field_make_up_incourse = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        roll_field_make_up_incourse = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        year_selection_make_up_incourse = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        semester_selection_make_up_incourse = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        makeup_incourses = new javax.swing.JList<>();
        jLabel15 = new javax.swing.JLabel();
        browseFileButton_make_up_incourse = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        day_from_make_up_incourse = new javax.swing.JSpinner();
        month_from_make_up_incourse = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        day_to_make_up_incourse = new javax.swing.JSpinner();
        month_to_make_up_incourse = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        destination_make_up_incourse = new javax.swing.JComboBox<>();
        panelThree = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        application_space = new HintTextArea("Write your application here ...");
        jLabel19 = new javax.swing.JLabel();
        browseFileButton_custom_application = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        destination_custom_application = new javax.swing.JComboBox<>();
        jLabel21 = new javax.swing.JLabel();
        application_subject = new javax.swing.JTextField();
        panelFour = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        student_names = new HintTextArea("Add student names line by line");
        jLabel23 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        student_rolls = new HintTextArea("Add line by line");
        jLabel24 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        superviser_names = new HintTextArea("Add names line by line");
        jLabel25 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        superviser_emails = new HintTextArea("Add line by line");
        jLabel26 = new javax.swing.JLabel();
        presentation_phase = new javax.swing.JComboBox<>();
        jLabel27 = new javax.swing.JLabel();
        reason = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        current_presentation_day = new javax.swing.JSpinner();
        current_presentation_month = new javax.swing.JComboBox<>();
        jLabel29 = new javax.swing.JLabel();
        postpone_for_week = new javax.swing.JSpinner();
        createButton = new javax.swing.JButton();
        submitButton = new javax.swing.JButton();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon("dulogo.jpg").getImage());

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setText("Application type:");

        type_selection.setModel(new javax.swing.DefaultComboBoxModel<>(types));
        type_selection.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                type_selectionItemStateChanged(evt);
            }
        });

        mainPanel.setLayout(new java.awt.CardLayout());

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel2.setText("Name:");

        name_field.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel3.setText("Roll:");

        roll_field.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel4.setText("Year:");

        year_selection.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        year_selection.setModel(new javax.swing.DefaultComboBoxModel<>(years)
        );

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel5.setText("Semester:");

        semester_selection.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        semester_selection.setModel(new javax.swing.DefaultComboBoxModel<>(semesters)
        );

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel6.setText("From:");

        day_from.setModel(new javax.swing.SpinnerNumberModel(1, 1, 31, 1));

        month_from.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        month_from.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel7.setText("To:");

        day_to.setModel(new javax.swing.SpinnerNumberModel(1, 1, 31, 1));

        month_to.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        month_to.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel8.setText("Attachments:");

        browseFileButton.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        browseFileButton.setText("Browse File");
        browseFileButton.setToolTipText("Browse for image files to attach with the application");
        browseFileButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                browseFileButtonMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                browseFileButtonMouseEntered(evt);
            }
        });
        browseFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseFileButtonActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel9.setText("Destination:");

        destination.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        destination.setModel(new javax.swing.DefaultComboBoxModel<>(teachers)
        );

        javax.swing.GroupLayout panelOneLayout = new javax.swing.GroupLayout(panelOne);
        panelOne.setLayout(panelOneLayout);
        panelOneLayout.setHorizontalGroup(
            panelOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOneLayout.createSequentialGroup()
                .addGroup(panelOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelOneLayout.createSequentialGroup()
                        .addGroup(panelOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(148, 148, 148))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelOneLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(day_from, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(month_from, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 124, Short.MAX_VALUE)
                .addGroup(panelOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelOneLayout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(day_to, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(month_to, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(name_field, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(roll_field, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(year_selection, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(semester_selection, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(browseFileButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(panelOneLayout.createSequentialGroup()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(destination, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelOneLayout.setVerticalGroup(
            panelOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOneLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(panelOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(name_field, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(panelOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(roll_field, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47)
                .addGroup(panelOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(year_selection, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(panelOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(semester_selection, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(65, 65, 65)
                .addGroup(panelOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(day_from, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(month_from, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(day_to, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(month_to, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(57, 57, 57)
                .addGroup(panelOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(browseFileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 79, Short.MAX_VALUE)
                .addGroup(panelOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(destination, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(35, 35, 35))
        );

        mainPanel.add(panelOne, "panelOne");

        jLabel10.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel10.setText("Name:");

        name_field_make_up_incourse.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel11.setText("Roll:");

        roll_field_make_up_incourse.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel12.setText("Year:");

        year_selection_make_up_incourse.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        year_selection_make_up_incourse.setModel(new javax.swing.DefaultComboBoxModel<>(years)
        );

        jLabel13.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel13.setText("Semester:");

        semester_selection_make_up_incourse.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        semester_selection_make_up_incourse.setModel(new javax.swing.DefaultComboBoxModel<>(semesters)
        );

        jLabel14.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel14.setText("Incourse subjects:");

        makeup_incourses.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        makeup_incourses.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(makeup_incourses);

        jLabel15.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel15.setText("Attachments:");

        browseFileButton_make_up_incourse.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        browseFileButton_make_up_incourse.setText("Browse File");
        browseFileButton_make_up_incourse.setToolTipText("Browse for image files to attach with the application");
        browseFileButton_make_up_incourse.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        browseFileButton_make_up_incourse.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                browseFileButton_make_up_incourseMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                browseFileButton_make_up_incourseMouseEntered(evt);
            }
        });
        browseFileButton_make_up_incourse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseFileButton_make_up_incourseActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel16.setText("Absent from:");

        day_from_make_up_incourse.setModel(new javax.swing.SpinnerNumberModel(1, 1, 31, 1));

        month_from_make_up_incourse.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));

        jLabel17.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel17.setText("Absent to:");

        day_to_make_up_incourse.setModel(new javax.swing.SpinnerNumberModel(1, 1, 31, 1));

        month_to_make_up_incourse.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));

        jLabel18.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel18.setText("Destination:");

        destination_make_up_incourse.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        destination_make_up_incourse.setModel(new javax.swing.DefaultComboBoxModel<>(teachers)
        );

        javax.swing.GroupLayout panelTwoLayout = new javax.swing.GroupLayout(panelTwo);
        panelTwo.setLayout(panelTwoLayout);
        panelTwoLayout.setHorizontalGroup(
            panelTwoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTwoLayout.createSequentialGroup()
                .addGroup(panelTwoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelTwoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(name_field_make_up_incourse)
                    .addComponent(roll_field_make_up_incourse, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)))
            .addGroup(panelTwoLayout.createSequentialGroup()
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(day_from_make_up_incourse, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(month_from_make_up_incourse, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(day_to_make_up_incourse, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(month_to_make_up_incourse, 0, 125, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTwoLayout.createSequentialGroup()
                .addGroup(panelTwoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelTwoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTwoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane1)
                        .addComponent(year_selection_make_up_incourse, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(semester_selection_make_up_incourse, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(browseFileButton_make_up_incourse, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTwoLayout.createSequentialGroup()
                        .addComponent(destination_make_up_incourse, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        panelTwoLayout.setVerticalGroup(
            panelTwoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTwoLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(panelTwoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(name_field_make_up_incourse, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(panelTwoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(roll_field_make_up_incourse, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addGap(48, 48, 48)
                .addGroup(panelTwoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(year_selection_make_up_incourse, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(panelTwoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(semester_selection_make_up_incourse, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(panelTwoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelTwoLayout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addGroup(panelTwoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(day_from_make_up_incourse, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(month_from_make_up_incourse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17)
                            .addComponent(day_to_make_up_incourse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(month_to_make_up_incourse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(55, 55, 55)
                        .addComponent(jLabel14)
                        .addGap(50, 50, 50))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTwoLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)))
                .addGroup(panelTwoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(browseFileButton_make_up_incourse, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addGap(18, 18, 18)
                .addGroup(panelTwoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(destination_make_up_incourse, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        mainPanel.add(panelTwo, "panelTwo");

        application_space.setColumns(20);
        application_space.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        application_space.setLineWrap(true);
        application_space.setRows(5);
        jScrollPane2.setViewportView(application_space);

        jLabel19.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel19.setText("Attachments:");

        browseFileButton_custom_application.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        browseFileButton_custom_application.setText("Browse File");
        browseFileButton_custom_application.setToolTipText("Browse for image files to attach with the application");
        browseFileButton_custom_application.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        browseFileButton_custom_application.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                browseFileButton_custom_applicationMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                browseFileButton_custom_applicationMouseEntered(evt);
            }
        });
        browseFileButton_custom_application.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseFileButton_custom_applicationActionPerformed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel20.setText("Destination:");

        destination_custom_application.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        destination_custom_application.setModel(new javax.swing.DefaultComboBoxModel<>(teachers)
        );

        jLabel21.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel21.setText("Application Subject:");

        application_subject.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        javax.swing.GroupLayout panelThreeLayout = new javax.swing.GroupLayout(panelThree);
        panelThree.setLayout(panelThreeLayout);
        panelThreeLayout.setHorizontalGroup(
            panelThreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelThreeLayout.createSequentialGroup()
                .addGroup(panelThreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelThreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(browseFileButton_custom_application, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
                    .addComponent(destination_custom_application, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(panelThreeLayout.createSequentialGroup()
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(124, 124, 124)
                .addComponent(application_subject, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE))
        );
        panelThreeLayout.setVerticalGroup(
            panelThreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelThreeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelThreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(application_subject, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelThreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(browseFileButton_custom_application, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(panelThreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(destination_custom_application, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43))
        );

        mainPanel.add(panelThree, "panelThree");

        jLabel22.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel22.setText("Student names:");

        student_names.setColumns(20);
        student_names.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        student_names.setRows(5);
        jScrollPane3.setViewportView(student_names);

        jLabel23.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel23.setText("Rolls:");

        student_rolls.setColumns(20);
        student_rolls.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        student_rolls.setRows(5);
        jScrollPane4.setViewportView(student_rolls);

        jLabel24.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel24.setText("Superviser names:");

        superviser_names.setColumns(20);
        superviser_names.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        superviser_names.setRows(5);
        jScrollPane5.setViewportView(superviser_names);

        jLabel25.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel25.setText("Emails:");

        superviser_emails.setColumns(20);
        superviser_emails.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        superviser_emails.setRows(5);
        jScrollPane6.setViewportView(superviser_emails);

        jLabel26.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel26.setText("Presentation phase:");

        presentation_phase.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        presentation_phase.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1st", "2nd", "3rd", "4th" }));

        jLabel27.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel27.setText("Reason for rescheduling:");

        reason.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        reason.setToolTipText("");

        jLabel28.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel28.setText("Current presentation date:");

        current_presentation_day.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        current_presentation_day.setModel(new javax.swing.SpinnerNumberModel(1, 1, 31, 1));

        current_presentation_month.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        current_presentation_month.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));

        jLabel29.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel29.setText("Postpone for(Week):");

        postpone_for_week.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        postpone_for_week.setModel(new javax.swing.SpinnerNumberModel(1, 1, 5, 1));

        javax.swing.GroupLayout panelFourLayout = new javax.swing.GroupLayout(panelFour);
        panelFour.setLayout(panelFourLayout);
        panelFourLayout.setHorizontalGroup(
            panelFourLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFourLayout.createSequentialGroup()
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel23)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
            .addGroup(panelFourLayout.createSequentialGroup()
                .addGroup(panelFourLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFourLayout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel25))
                    .addComponent(jLabel26))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelFourLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(presentation_phase, 0, 167, Short.MAX_VALUE)))
            .addGroup(panelFourLayout.createSequentialGroup()
                .addComponent(jLabel27)
                .addGap(98, 98, 98)
                .addComponent(reason))
            .addGroup(panelFourLayout.createSequentialGroup()
                .addGroup(panelFourLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel29, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelFourLayout.createSequentialGroup()
                        .addComponent(jLabel28)
                        .addGap(138, 138, 138)
                        .addComponent(current_presentation_day, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(panelFourLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(current_presentation_month, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelFourLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(postpone_for_week, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        panelFourLayout.setVerticalGroup(
            panelFourLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFourLayout.createSequentialGroup()
                .addGroup(panelFourLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFourLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jLabel22))
                    .addGroup(panelFourLayout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jLabel23))
                    .addGroup(panelFourLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(panelFourLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane3)
                            .addComponent(jScrollPane4))))
                .addGroup(panelFourLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFourLayout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addComponent(jLabel24))
                    .addGroup(panelFourLayout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addComponent(jLabel25))
                    .addGroup(panelFourLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(panelFourLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                            .addComponent(jScrollPane6))))
                .addGap(66, 66, 66)
                .addGroup(panelFourLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(presentation_phase, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(panelFourLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(reason, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addGap(64, 64, 64)
                .addGroup(panelFourLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(current_presentation_day, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(current_presentation_month, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(panelFourLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(postpone_for_week, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        mainPanel.add(panelFour, "panelFour");

        createButton.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        createButton.setText("Create");
        createButton.setToolTipText("Only creates the file in the current directory");
        createButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                createButtonMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                createButtonMouseEntered(evt);
            }
        });
        createButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createButtonActionPerformed(evt);
            }
        });

        submitButton.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        submitButton.setText("Submit");
        submitButton.setToolTipText("Submit the application to the destination");
        submitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                submitButtonMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                submitButtonMouseEntered(evt);
            }
        });
        submitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(type_selection, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(14, 14, 14))
            .addGroup(layout.createSequentialGroup()
                .addGap(172, 172, 172)
                .addComponent(createButton, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(83, 83, 83)
                .addComponent(submitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(type_selection, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 603, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 124, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(createButton, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                    .addComponent(submitButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


//    public String returnEditedBodyString(String str, String infos[]) {
//        String arr[] = str.split("\\?");
//        int c = arr.length;
//        String ret = arr[0];
//        for (int i = 1; i < c; i++) {
//            ret += infos[i - 1] + arr[i];
//        }
//
//        return ret;
//    }
    
    private void makeCustomApplicationPdf() {
        Document document = new Document();
        try {            
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Application.pdf"));
            images = new Image[attachments.size()];
            int len = images.length;
            float scaler;
            for (int i = 0; i < len; i++) {
                images[i] = Image.getInstance(attachments.get(i));
                scaler = ((document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin()) / images[i].getWidth()) * 100;
                images[i].scalePercent(scaler);
            }
            document.open();
            document.add(new Paragraph(application_space.getText()));
            for (int i = 0; i < len; i++) {
                document.newPage();
                document.add(images[i]);
            }            
            document.close();
            writer.close();
        } catch (DocumentException documentException) {
            System.out.println("DocumentException occured");
        } catch (IOException iOException) {
            System.out.println("IOException occured");
        }
    }
    
    private void makeReschedulingThesisPresentationApplicationPdf() {
        infos_rtp[0] = csedudigitalassistant.ApplicationWriting.this.student_names.getText();
        String studnt_names[] = infos_rtp[0].split("\\r?\\n");
        infos_rtp[1] = csedudigitalassistant.ApplicationWriting.this.student_rolls.getText();
        String rolls[] = infos_rtp[1].split("\\r?\\n");
        infos_rtp[2] = csedudigitalassistant.ApplicationWriting.this.superviser_names.getText();
        String suprviser_names[] = infos_rtp[2].split("\\r?\\n");
        infos_rtp[3] = csedudigitalassistant.ApplicationWriting.this.superviser_emails.getText();
        String superviser_mails[] = infos_rtp[3].split("\\r?\\n");
        infos_rtp[4] = (String) csedudigitalassistant.ApplicationWriting.this.presentation_phase.getSelectedItem();
        infos_rtp[5] = csedudigitalassistant.ApplicationWriting.this.reason.getText();
        infos_rtp[6] = String.valueOf(csedudigitalassistant.ApplicationWriting.this.current_presentation_day.getValue());
        infos_rtp[7] = (String) csedudigitalassistant.ApplicationWriting.this.current_presentation_month.getSelectedItem();                
        infos_rtp[8] = String.valueOf(csedudigitalassistant.ApplicationWriting.this.postpone_for_week.getValue());
        Document document = new Document();
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String dNow = simpleDateFormat.format(date);
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        try {            
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Application.pdf"));            
            document.open();
            document.add(new Paragraph(dNow));
            document.add(Chunk.NEWLINE);
            document.add(new Paragraph("To"));
            document.add(new Paragraph("The Head of 4th Year Exam Committee"));
            document.add(new Paragraph("Department of Computer Science and Engineering,"));
            document.add(new Paragraph("University of Dhaka"));
            document.add(Chunk.NEWLINE);
            document.add(new Paragraph("Subject: An application for rescheduling thesis presentation."));
            document.add(Chunk.NEWLINE);
            document.add(new Paragraph("Sir,"));
            document.add(new Paragraph("With due respect, We are the students of 4th year of your department. Sir, "
                    + "the "+infos_rtp[4]+" phase of our 4th year thesis presentation is currently "
                    + "scheduled in "+infos_rtp[7]+" "+infos_rtp[6]+", "+year+". But most of the "
                    + "students are not willing to attend the presentation at the mentioned date "
                    + "because of "+infos_rtp[5]+". We have talk to some of our supervisers also. They"
                    + " are also agreed with us after hearing the reasons from us. We want to postpone"
                    + " the presentation for at least "+infos_rtp[8]+" weeks. The interested students"
                    + " and teachers list is attached with the application."
                    ));            
            document.add(Chunk.NEWLINE);
            document.add(new Paragraph("In the circumstances, we request you earnestly to "
                    + "realize our situation and take necessary steps to postpone"
                    + " the thesis presentation for at least "+infos_rtp[8]+" weeks."));
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            document.add(new Paragraph("Yours obediently,"));
            document.add(new Paragraph("Class Representative of 4th Year students"));
            document.add(new Paragraph("On behalf of 4th Year students,"));
            document.add(new Paragraph("Department of Computer Science and Engineering,"));
            document.add(new Paragraph("University of Dhaka"));  
            document.newPage();
            Paragraph str = new Paragraph("Students List", new Font(FontFamily.HELVETICA, 12, Font.BOLD));
            str.setAlignment(Element.ALIGN_CENTER);
            document.add(str);
            document.add(Chunk.NEWLINE);
            PdfPTable table = new PdfPTable(2);
            PdfPCell cell = new PdfPCell();            
            Paragraph data = new Paragraph("Names");
            data.setAlignment(Element.ALIGN_CENTER);
            cell.setMinimumHeight(10);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.addElement(data);
            table.addCell(cell);
            cell = new PdfPCell();
            data = new Paragraph("Rolls");
            data.setAlignment(Element.ALIGN_CENTER);
            cell.setMinimumHeight(10);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.addElement(data);
            table.addCell(cell);
            for(int aw = 0; aw < studnt_names.length; aw++){
                cell = new PdfPCell();            
                data = new Paragraph(studnt_names[aw]);
                data.setAlignment(Element.ALIGN_CENTER);
                cell.setMinimumHeight(10);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.addElement(data);
                table.addCell(cell);
                cell = new PdfPCell();            
                data = new Paragraph(rolls[aw]);
                data.setAlignment(Element.ALIGN_CENTER);
                cell.setMinimumHeight(10);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.addElement(data);
                table.addCell(cell);
            }
            document.add(table);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            Paragraph s = new Paragraph("Supervisers List", new Font(FontFamily.HELVETICA, 12, Font.BOLD));
            s.setAlignment(Element.ALIGN_CENTER);
            document.add(s);
            document.add(Chunk.NEWLINE);
            PdfPTable tble = new PdfPTable(2);
            PdfPCell pCell = new PdfPCell();            
            data = new Paragraph("Names");
            data.setAlignment(Element.ALIGN_CENTER);
            pCell.setMinimumHeight(10);
            pCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            pCell.addElement(data);
            tble.addCell(pCell);
            pCell = new PdfPCell();            
            data = new Paragraph("Email Addresses");
            data.setAlignment(Element.ALIGN_CENTER);
            pCell.setMinimumHeight(10);
            pCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            pCell.addElement(data);
            tble.addCell(pCell);
            for(int aw = 0; aw < suprviser_names.length; aw++){
                pCell = new PdfPCell();            
                data = new Paragraph(suprviser_names[aw]);
                data.setAlignment(Element.ALIGN_CENTER);
                pCell.setMinimumHeight(10);
                pCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                pCell.addElement(data);
                tble.addCell(pCell);
                pCell = new PdfPCell();            
                data = new Paragraph(superviser_mails[aw]);
                data.setAlignment(Element.ALIGN_CENTER);
                pCell.setMinimumHeight(10);
                pCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                pCell.addElement(data);
                tble.addCell(pCell);
            }
            document.add(tble);
            document.close();
            writer.close();            
        } catch (IOException ioException) {
            System.out.println("IOException occured");
        } catch (DocumentException documentException) {
            System.out.println("DocumentException occured");
        }
    }
    
    private void makeMakeUpIncourseApplicationPdf() {
        infos_mui[0] = csedudigitalassistant.ApplicationWriting.this.name_field_make_up_incourse.getText();
        infos_mui[1] = csedudigitalassistant.ApplicationWriting.this.roll_field_make_up_incourse.getText();
        infos_mui[2] = (String) csedudigitalassistant.ApplicationWriting.this.year_selection_make_up_incourse.getSelectedItem();
        infos_mui[3] = (String) csedudigitalassistant.ApplicationWriting.this.semester_selection_make_up_incourse.getSelectedItem();
        infos_mui[4] = String.valueOf(csedudigitalassistant.ApplicationWriting.this.day_from_make_up_incourse.getValue());
        infos_mui[5] = (String) csedudigitalassistant.ApplicationWriting.this.month_from_make_up_incourse.getSelectedItem();
        infos_mui[6] = String.valueOf(csedudigitalassistant.ApplicationWriting.this.day_to_make_up_incourse.getValue());
        infos_mui[7] = (String) csedudigitalassistant.ApplicationWriting.this.month_to_make_up_incourse.getSelectedItem();                
        java.util.List<String> selectedValuesList = csedudigitalassistant.ApplicationWriting.this.makeup_incourses.getSelectedValuesList();
        infos_mui[8] = selectedValuesList.get(0);
        for (int i = 1; i < selectedValuesList.size(); i++) {
            infos_mui[8] += ", "+selectedValuesList.get(i);
        }
        Document document = new Document();
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String dNow = simpleDateFormat.format(date);
        try {            
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Application.pdf"));
            images = new Image[attachments.size()];
            int len = images.length;
            float scaler;
            for (int i = 0; i < len; i++) {
                images[i] = Image.getInstance(attachments.get(i));
                scaler = ((document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin()) / images[i].getWidth()) * 100;
                images[i].scalePercent(scaler);
            }
            document.open();
            document.add(new Paragraph(dNow));
            document.add(Chunk.NEWLINE);
            document.add(new Paragraph("To"));
            document.add(new Paragraph("The Student Advisor"));
            document.add(new Paragraph("Department of Computer Science and Engineering,"));
            document.add(new Paragraph("University of Dhaka"));
            document.add(Chunk.NEWLINE);
            document.add(new Paragraph("Subject: An application for make up incourse exam."));
            document.add(Chunk.NEWLINE);
            document.add(new Paragraph("Sir,"));
            document.add(new Paragraph("With due respect, I am "+infos_mui[0]+", roll no: "+infos_mui[1]+", a student of "+infos_mui[2]+" year "+infos_mui[3]+" semester. Sir, I could not attend the classes from "+infos_mui[5]+" "+infos_mui[4]+" to "+infos_mui[7]+" "+infos_mui[6]+" on account of "
                    + "my illness. As a reason, I was unable to attend to some of my incourse exams. My missed exams are: "+infos_mui[8]+". Now I want to make up for those missed incourse exams."));            
            document.add(Chunk.NEWLINE);
            document.add(new Paragraph("In the circumstances, I request you earnestly to grant me leave of absence for those days and permission to sit for make up incourse exams for the mentioned courses in a suitable date."));
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            document.add(new Paragraph("Yours obediently,"));
            document.add(new Paragraph(infos_mui[0]));
            document.add(new Paragraph("Roll no: "+infos_mui[1]));
            document.add(new Paragraph(infos_mui[2]+" Year "+infos_mui[3]+" Semester"));
            document.add(new Paragraph("Department of Computer Science and Engineering,"));
            document.add(new Paragraph("University of Dhaka"));            
            for (int i = 0; i < len; i++) {
                document.newPage();
                document.add(images[i]);
            }            
            document.close();
            writer.close();            
        } catch (IOException ioException) {
            System.out.println("IOException occured");
        } catch (DocumentException documentException) {
            System.out.println("DocumentException occured");
        }
    }
    
    private void makeLeaveOfAbsenceApplicationPdf() {
        infos_loa[0] = csedudigitalassistant.ApplicationWriting.this.name_field.getText();
        infos_loa[1] = csedudigitalassistant.ApplicationWriting.this.roll_field.getText();
        infos_loa[2] = (String) csedudigitalassistant.ApplicationWriting.this.year_selection.getSelectedItem();
        infos_loa[3] = (String) csedudigitalassistant.ApplicationWriting.this.semester_selection.getSelectedItem();
        infos_loa[4] = String.valueOf(csedudigitalassistant.ApplicationWriting.this.day_from.getValue());
        infos_loa[5] = (String) csedudigitalassistant.ApplicationWriting.this.month_from.getSelectedItem();
        infos_loa[6] = String.valueOf(csedudigitalassistant.ApplicationWriting.this.day_to.getValue());
        infos_loa[7] = (String) csedudigitalassistant.ApplicationWriting.this.month_to.getSelectedItem();        
        Document document = new Document();
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String dNow = simpleDateFormat.format(date);
        try {            
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Application.pdf"));
            images = new Image[attachments.size()];
            int len = images.length;
            float scaler;
            for (int i = 0; i < len; i++) {
                images[i] = Image.getInstance(attachments.get(i));
                scaler = ((document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin()) / images[i].getWidth()) * 100;
                images[i].scalePercent(scaler);
            }
            document.open();
            document.add(new Paragraph(dNow));
            document.add(Chunk.NEWLINE);
            document.add(new Paragraph("To"));
            document.add(new Paragraph("The Student Advisor"));
            document.add(new Paragraph("Department of Computer Science and Engineering,"));
            document.add(new Paragraph("University of Dhaka"));
            document.add(Chunk.NEWLINE);
            document.add(new Paragraph("Subject: An application for leave of absence."));
            document.add(Chunk.NEWLINE);
            document.add(new Paragraph("Sir,"));
            document.add(new Paragraph("With due respect, I am "+infos_loa[0]+", roll no: "+infos_loa[1]+", a student of "+infos_loa[2]+" year "+infos_loa[3]+" semester. Sir, I could not attend the classes from "+infos_loa[5]+" "+infos_loa[4]+" to "+infos_loa[7]+" "+infos_loa[6]+" on account of my illness."));
            document.add(Chunk.NEWLINE);
            document.add(new Paragraph("In the circumstances, I request you earnestly to grant me leave of absence for those days."));
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            document.add(new Paragraph("Yours obediently,"));
            document.add(new Paragraph(infos_loa[0]));
            document.add(new Paragraph("Roll no: "+infos_loa[1]));
            document.add(new Paragraph(infos_loa[2]+" Year "+infos_loa[3]+" Semester"));
            document.add(new Paragraph("Department of Computer Science and Engineering,"));
            document.add(new Paragraph("University of Dhaka"));            
            for (int i = 0; i < len; i++) {
                document.newPage();
                document.add(images[i]);
            }            
            document.close();
            writer.close();            
        } catch (IOException ioException) {
            System.out.println("IOException occured");
        } catch (DocumentException documentException) {
            System.out.println("DocumentException occured");
        }
    }
    
    private boolean isNamesValid(String names[]) {
        for (String name : names) {
            if (name.matches(".*\\d+.*")) {
                return false;
            }
        }
        return true;
    }
    
    private boolean isRollsValid(String[] rolls) {
        for (String roll : rolls) {
            if (!roll.matches("^.+?\\d$")) {
                return false;
            }
        }
        return true;
    }
    
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validate(String emailStr) {
            Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
            return matcher.find();
    }
    
    private boolean isMailsValid(String[] mails) {
        for (String mail : mails) {
            if (!validate(mail)) {
                return false;
            }
        }
        return true;
    }
    
    private boolean isRangeValid(String d_from, String m_from, String d_to, String m_to) {
        String months[] = {"January", "February", "March", "April", "May",
                           "June", "July", "August", "September", "October",
                            "November", "December"};
        int index1 = 0, index2 = 0;
        for (int i = 0; i < months.length; i++) {
            if (months[i].equals(m_from)) {
                index1 = i;
                break;
            }
        }
        for (int i = 0; i < months.length; i++) {
            if (months[i].equals(m_to)) {
                index2 = i;
                break;
            }
        }
        if (index2 > index1) {
            return true;
        } else if (index2 < index1) {
            return false;
        } else {
            int val1 = Integer.parseInt(d_from);
            int val2 = Integer.parseInt(d_to);
            return val1 <= val2;
        }
    }
    
    private boolean checkInput(String type) {
        if (type.equals("Leave of absence")) {
            String name = name_field.getText();
            String roll = roll_field.getText();
            if (name.isEmpty()) {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "Incomplete input", "Error", JOptionPane.ERROR_MESSAGE);                    
                name_field.requestFocus();
                return false;
            } else if (roll.isEmpty()) {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "Incomplete input", "Error", JOptionPane.ERROR_MESSAGE);                    
                roll_field.requestFocus();
                return false;
            } else if (name.matches(".*\\d+.*")) {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "Invalid name", "Error", JOptionPane.ERROR_MESSAGE);                    
                name_field.requestFocus();
                return false;
            } else if (!roll.matches("^.+?\\d$")) {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "Invalid roll", "Error", JOptionPane.ERROR_MESSAGE);                    
                roll_field.requestFocus();
                return false;
            } else if (!isRangeValid(String.valueOf(day_from.getValue()), (String)month_from.getSelectedItem(), String.valueOf(day_to.getValue()), (String)month_to.getSelectedItem())) {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "Invalid date range", "Error", JOptionPane.ERROR_MESSAGE);                    
                day_from.requestFocus();
                return false;
            }
            return true;
        } else if (type.equals("Make up incourse")) {
            String name = name_field_make_up_incourse.getText();
            String roll = roll_field_make_up_incourse.getText();
            java.util.List<String> selectedValuesList = csedudigitalassistant.ApplicationWriting.this.makeup_incourses.getSelectedValuesList();
            if (name.isEmpty()) {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "Incomplete input", "Error", JOptionPane.ERROR_MESSAGE);                    
                name_field_make_up_incourse.requestFocus();
                return false;
            } else if (roll.isEmpty()) {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "Incomplete input", "Error", JOptionPane.ERROR_MESSAGE);                    
                roll_field_make_up_incourse.requestFocus();
                return false;
            } else if (selectedValuesList.isEmpty()) {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "Incomplete input", "Error", JOptionPane.ERROR_MESSAGE);                    
                makeup_incourses.requestFocus();
                return false;
            } else if (name.matches(".*\\d+.*")) {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "Invalid name", "Error", JOptionPane.ERROR_MESSAGE);                    
                name_field_make_up_incourse.requestFocus();
                return false;
            } else if (!roll.matches("^.+?\\d$")) {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "Invalid roll", "Error", JOptionPane.ERROR_MESSAGE);                    
                roll_field_make_up_incourse.requestFocus();
                return false;
            } else if (!isRangeValid(String.valueOf(day_from_make_up_incourse.getValue()), (String)month_from_make_up_incourse.getSelectedItem(), String.valueOf(day_to_make_up_incourse.getValue()), (String)month_to_make_up_incourse.getSelectedItem())) {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "Invalid date range", "Error", JOptionPane.ERROR_MESSAGE);                    
                day_from_make_up_incourse.requestFocus();
                return false;
            }
            return true;
        } else if (type.equals("Custom")) {
            String app_subject = this.application_subject.getText();
            if (app_subject.isEmpty()) {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "Incomplete input", "Error", JOptionPane.ERROR_MESSAGE);                    
                application_subject.requestFocus();
                return false;
            } else if (application_space.getText().isEmpty()) {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "Incomplete input", "Error", JOptionPane.ERROR_MESSAGE);                    
                application_space.requestFocus();
                return false;
            } else if (app_subject.matches(".*\\d+.*")) {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "Invalid application subject format", "Error", JOptionPane.ERROR_MESSAGE);                
                application_subject.requestFocus();
                return false;
            }
            return true;
        } else if (type.equals("Rescheduling thesis presentation")) {
            String studnt_names = student_names.getText();  
            String stdnt_names[] = studnt_names.split("\\r?\\n");
            String studnt_rolls = student_rolls.getText();
            String stdnt_rolls[] = studnt_rolls.split("\\r?\\n");
            String suprviser_names = superviser_names.getText();
            String suprvisr_names[] = suprviser_names.split("\\r?\\n");
            String superviser_mails = superviser_emails.getText();
            String mails[] = superviser_mails.split("\\r?\\n");
            String reasn = reason.getText();
            if (studnt_names.isEmpty()) {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "Incomplete input", "Error", JOptionPane.ERROR_MESSAGE);                    
                student_names.requestFocus();
                return false;
            } else if (studnt_rolls.isEmpty()) {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "Incomplete input", "Error", JOptionPane.ERROR_MESSAGE);                    
                student_rolls.requestFocus();
                return false;
            } else if (suprviser_names.isEmpty()) {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "Incomplete input", "Error", JOptionPane.ERROR_MESSAGE);                    
                superviser_names.requestFocus();
                return false;
            } else if (superviser_mails.isEmpty()) {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "Incomplete input", "Error", JOptionPane.ERROR_MESSAGE);                    
                superviser_emails.requestFocus();
                return false;
            } else if (reasn.isEmpty()) {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "Incomplete input", "Error", JOptionPane.ERROR_MESSAGE);                    
                reason.requestFocus();
                return false;
            } else if ((!studnt_names.contains("\n")) || (studnt_names.contains(","))) {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "Invalid name entry format", "Error", JOptionPane.ERROR_MESSAGE);                
                student_names.requestFocus();
                return false;
            } else if (!isNamesValid(stdnt_names)) {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "Invalid student name", "Error", JOptionPane.ERROR_MESSAGE);                
                student_names.requestFocus();
                return false;
            } else if ((!studnt_rolls.contains("\n")) || (studnt_rolls.contains(","))) {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "Invalid roll entry format", "Error", JOptionPane.ERROR_MESSAGE);                
                student_rolls.requestFocus();
                return false;
            } else if (!isRollsValid(stdnt_rolls)) {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "Invalid student roll", "Error", JOptionPane.ERROR_MESSAGE);                
                student_rolls.requestFocus();
                return false;
            } else if ((!suprviser_names.contains("\n")) || (suprviser_names.contains(","))) {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "Invalid name entry format", "Error", JOptionPane.ERROR_MESSAGE);                
                superviser_names.requestFocus();
                return false;
            } else if (!isNamesValid(suprvisr_names)) {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "Invalid superviser name", "Error", JOptionPane.ERROR_MESSAGE);                
                superviser_names.requestFocus();
                return false;
            } else if ((!superviser_mails.contains("\n")) || (superviser_mails.contains(","))) {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "Invalid mail entry format", "Error", JOptionPane.ERROR_MESSAGE);                
                superviser_emails.requestFocus();
                return false;
            } else if (!isMailsValid(mails)) {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "Invalid superviser mail", "Error", JOptionPane.ERROR_MESSAGE);                
                superviser_emails.requestFocus();
                return false;
            } else if (reasn.matches(".*\\d+.*")) {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "Invalid reason format", "Error", JOptionPane.ERROR_MESSAGE);                
                reason.requestFocus();
                return false;
            }
            return true;
        }
        return true;
    }

    private void createButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_createButtonMouseEntered
        this.createButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.createButton.setBackground(Color.YELLOW);
        this.createButton.setContentAreaFilled(false);
        this.createButton.setOpaque(true);        
    }//GEN-LAST:event_createButtonMouseEntered

    private void createButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createButtonActionPerformed
        String type = (String) type_selection.getSelectedItem();
        if (type.equals("Leave of absence")) {
            if (checkInput(type)) {
                makeLeaveOfAbsenceApplicationPdf();
            } else {
                return;
            }
        } else if (type.equals("Make up incourse")) {
            if (checkInput(type)) {
                makeMakeUpIncourseApplicationPdf();
            } else {
                return;
            }
        } else if (type.equals("Custom")) {
            if (checkInput(type)) {
                makeCustomApplicationPdf();
            } else {
                return;
            }
        } else if (type.equals("Rescheduling thesis presentation")) {
            if (checkInput(type)) {
                makeReschedulingThesisPresentationApplicationPdf();
            } else {
                return;
            }
        }
        System.exit(0); //Should be frame.dispose here, so changes will be needed in the integration part of the classes.
    }//GEN-LAST:event_createButtonActionPerformed

    private void submitButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_submitButtonMouseEntered
        this.submitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.submitButton.setBackground(Color.GREEN);
        this.submitButton.setContentAreaFilled(false);
        this.submitButton.setOpaque(true);        
    }//GEN-LAST:event_submitButtonMouseEntered

    private void submitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitButtonActionPerformed
        String type = (String) type_selection.getSelectedItem();
        if (type.equals("Leave of absence")) {
            if (checkInput(type)) {
                makeLeaveOfAbsenceApplicationPdf();
            } else {
                return;
            }
            int index = csedudigitalassistant.ApplicationWriting.this.destination.getSelectedIndex();
            sendMail(target_addresses[index], (String) type_selection.getSelectedItem(), infos_loa[0], infos_loa[1], infos_loa[2], infos_loa[3]);
        } else if (type.equals("Make up incourse")) {
            if (checkInput(type)) {
                makeMakeUpIncourseApplicationPdf();
            } else {
                return;
            }
            int index = csedudigitalassistant.ApplicationWriting.this.destination_make_up_incourse.getSelectedIndex();
            sendMail(target_addresses[index], (String) type_selection.getSelectedItem(), infos_mui[0], infos_mui[1], infos_mui[2], infos_mui[3]);
        } else if (type.equals("Custom")) {
            if (checkInput(type)) {
                makeCustomApplicationPdf();
            } else {
                return;
            }
            int index = csedudigitalassistant.ApplicationWriting.this.destination_custom_application.getSelectedIndex();
            sendMail(target_addresses[index], application_subject.getText(), 0);
        } else if (type.equals("Rescheduling thesis presentation")) {
            if (checkInput(type)) {
                makeReschedulingThesisPresentationApplicationPdf();
            } else {
                return;
            }            
            sendMail(target_addresses[0], (String) type_selection.getSelectedItem(), 1);
        }      
        System.exit(0); //Should be frame.dispose here, so changes will be needed in the integration part of the classes.
    }//GEN-LAST:event_submitButtonActionPerformed

    private void browseFileButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_browseFileButtonMouseEntered
        this.browseFileButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.browseFileButton.setBackground(Color.WHITE);
        this.browseFileButton.setContentAreaFilled(false);
        this.browseFileButton.setOpaque(true);        
    }//GEN-LAST:event_browseFileButtonMouseEntered

    private void browseFileAction() {
        while (true) {            
            FileDialog fileDialog = new FileDialog((JFrame)null, "Select File");
            fileDialog.setMode(FileDialog.LOAD);         
            fileDialog.setVisible(true);
            String dir = fileDialog.getDirectory();
            String file = fileDialog.getFile();
            if (dir != null) {
                if (file.endsWith("jpg") || file.endsWith("png") || file.endsWith("tiff") || file.endsWith("gif")) {
                    attachments.add(dir+file);
                    break;
                } else {
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(null, "Invalid file type", "Warning", JOptionPane.WARNING_MESSAGE);                    
                }
            } else {
                break;
            }
        }
    }
    
    private void browseFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseFileButtonActionPerformed
        browseFileAction();
    }//GEN-LAST:event_browseFileButtonActionPerformed

    private void browseFileButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_browseFileButtonMouseExited
        this.browseFileButton.setBackground(new javax.swing.JButton().getBackground());
    }//GEN-LAST:event_browseFileButtonMouseExited

    private void submitButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_submitButtonMouseExited
        this.submitButton.setBackground(new javax.swing.JButton().getBackground());
    }//GEN-LAST:event_submitButtonMouseExited

    private void createButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_createButtonMouseExited
        this.createButton.setBackground(new javax.swing.JButton().getBackground());
    }//GEN-LAST:event_createButtonMouseExited

    private void browseFileButton_make_up_incourseMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_browseFileButton_make_up_incourseMouseEntered
        this.browseFileButton_make_up_incourse.setBackground(Color.WHITE);
        this.browseFileButton_make_up_incourse.setContentAreaFilled(false);
        this.browseFileButton_make_up_incourse.setOpaque(true);
    }//GEN-LAST:event_browseFileButton_make_up_incourseMouseEntered

    private void browseFileButton_make_up_incourseMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_browseFileButton_make_up_incourseMouseExited
        this.browseFileButton_make_up_incourse.setBackground(new javax.swing.JButton().getBackground());
    }//GEN-LAST:event_browseFileButton_make_up_incourseMouseExited

    private void browseFileButton_make_up_incourseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseFileButton_make_up_incourseActionPerformed
        browseFileAction();
    }//GEN-LAST:event_browseFileButton_make_up_incourseActionPerformed

    private void type_selectionItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_type_selectionItemStateChanged
        String item = (String) type_selection.getSelectedItem();
        if (item.equals("Leave of absence")) {
            java.awt.CardLayout card = (java.awt.CardLayout)mainPanel.getLayout();
            card.show(mainPanel, "panelOne");
        } else if (item.equals("Make up incourse")) {
            java.awt.CardLayout card = (java.awt.CardLayout)mainPanel.getLayout();
            card.show(mainPanel, "panelTwo");
        } else if (item.equals("Custom")) {
            java.awt.CardLayout card = (java.awt.CardLayout)mainPanel.getLayout();
            card.show(mainPanel, "panelThree");
        } else if (item.equals("Rescheduling thesis presentation")) {
            java.awt.CardLayout card = (java.awt.CardLayout)mainPanel.getLayout();
            card.show(mainPanel, "panelFour");
        }
    }//GEN-LAST:event_type_selectionItemStateChanged

    private void browseFileButton_custom_applicationMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_browseFileButton_custom_applicationMouseEntered
        this.browseFileButton_custom_application.setBackground(Color.WHITE);
        this.browseFileButton_custom_application.setContentAreaFilled(false);
        this.browseFileButton_custom_application.setOpaque(true);
    }//GEN-LAST:event_browseFileButton_custom_applicationMouseEntered

    private void browseFileButton_custom_applicationMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_browseFileButton_custom_applicationMouseExited
        this.browseFileButton_custom_application.setBackground(new javax.swing.JButton().getBackground());
    }//GEN-LAST:event_browseFileButton_custom_applicationMouseExited

    private void browseFileButton_custom_applicationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseFileButton_custom_applicationActionPerformed
        browseFileAction();
    }//GEN-LAST:event_browseFileButton_custom_applicationActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        BasicLookAndFeel darcula = new DarculaLaf();
        try {
            UIManager.setLookAndFeel(darcula);
            //</editor-fold>
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(ApplicationWriting.class.getName()).log(Level.SEVERE, null, ex);
        }

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new ApplicationWriting().setVisible(true);
        });
    }

    JFrame myJFrame = new JFrame("Application Writing");

    ArrayList<String> attachments = new ArrayList<>();

    private String infos_loa[] = new String[8];
    private String infos_mui[] = new String[9];
    private String infos_rtp[] = new String[9];

    private static Image images[];

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea application_space;
    private javax.swing.JTextField application_subject;
    private javax.swing.JButton browseFileButton;
    private javax.swing.JButton browseFileButton_custom_application;
    private javax.swing.JButton browseFileButton_make_up_incourse;
    private javax.swing.JButton createButton;
    private javax.swing.JSpinner current_presentation_day;
    private javax.swing.JComboBox<String> current_presentation_month;
    private javax.swing.JSpinner day_from;
    private javax.swing.JSpinner day_from_make_up_incourse;
    private javax.swing.JSpinner day_to;
    private javax.swing.JSpinner day_to_make_up_incourse;
    private javax.swing.JComboBox<String> destination;
    private javax.swing.JComboBox<String> destination_custom_application;
    private javax.swing.JComboBox<String> destination_make_up_incourse;
    private javax.swing.JFrame jFrame1;
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
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JList<String> makeup_incourses;
    private javax.swing.JComboBox<String> month_from;
    private javax.swing.JComboBox<String> month_from_make_up_incourse;
    private javax.swing.JComboBox<String> month_to;
    private javax.swing.JComboBox<String> month_to_make_up_incourse;
    private javax.swing.JTextField name_field;
    private javax.swing.JTextField name_field_make_up_incourse;
    private javax.swing.JPanel panelFour;
    private javax.swing.JPanel panelOne;
    private javax.swing.JPanel panelThree;
    private javax.swing.JPanel panelTwo;
    private javax.swing.JSpinner postpone_for_week;
    private javax.swing.JComboBox<String> presentation_phase;
    private javax.swing.JTextField reason;
    private javax.swing.JTextField roll_field;
    private javax.swing.JTextField roll_field_make_up_incourse;
    private javax.swing.JComboBox<String> semester_selection;
    private javax.swing.JComboBox<String> semester_selection_make_up_incourse;
    private javax.swing.JTextArea student_names;
    private javax.swing.JTextArea student_rolls;
    private javax.swing.JButton submitButton;
    private javax.swing.JTextArea superviser_emails;
    private javax.swing.JTextArea superviser_names;
    private javax.swing.JComboBox<String> type_selection;
    private javax.swing.JComboBox<String> year_selection;
    private javax.swing.JComboBox<String> year_selection_make_up_incourse;
    // End of variables declaration//GEN-END:variables

}

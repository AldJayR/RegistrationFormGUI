package view;

import model.User;
import controller.RegistrationController;
import com.formdev.flatlaf.FlatLaf;

import java.util.List;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;

public class RegistrationForm extends javax.swing.JFrame {

    /**
     * Creates new form RegistrationForm
     */
    private DefaultTableModel model;
    private RegistrationController controller;

    public RegistrationForm() {
        initComponents();
        setupTable();
        disableControls();
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
    }

    public void setController(RegistrationController controller) {
        this.controller = controller;
        addRowSelectionListener();
        loadTableData();
    }

    private void setupTable() {
        model = new DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Student No.", "Last Name", "First Name", "Middle Name",
                    "Email", "Course", "Address", "Sex", "Age", "Section"
                }
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };
        studentListTable.setModel(model); // Corrected: Use studentListTable
        studentListTable.setRowHeight(25); // Set consistent row height
        studentListTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        studentListTable.setIntercellSpacing(new Dimension(0, 0)); // Remove cell spacing
        studentListTable.setShowGrid(false); // Hide grid lines
        studentListTable.setFillsViewportHeight(true); // Fill the viewport height
        JScrollPane scrollPane = (JScrollPane) studentListTable.getParent().getParent(); // Get the scroll pane
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Remove scroll pane border
        scrollPane.getViewport().setBackground(Color.WHITE);  // Set viewport background to white

        // Customize table header
        JTableHeader header = studentListTable.getTableHeader();
        header.setBackground(Color.decode("#f1f5f9")); // Set header background
        header.setForeground(Color.BLACK);          // Set header text color (optional)
        header.setFont(header.getFont().deriveFont(Font.BOLD)); // Make header text bold (optional)
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.decode("#cbd5e1"))); // Add a bottom border

        // Alternating row colors
        studentListTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(javax.swing.JTable table, Object value,
                    boolean isSelected, boolean hasFocus,
                    int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Set background color based on row index and selection state
                if (isSelected) {
                    c.setBackground(table.getSelectionBackground()); // Use default selection color
                    c.setForeground(table.getSelectionForeground());
                } else {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : Color.decode("#cbd5e1"));
                    c.setForeground(table.getForeground()); // Use default text color
                }
                return c;
            }
        });
    }

    private void addRowSelectionListener() {
        studentListTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = studentListTable.getSelectedRow();
                if (selectedRow != -1) {
                    studentNumberTextField.setText(model.getValueAt(selectedRow, 0).toString());
                    lastNameTextField.setText(model.getValueAt(selectedRow, 1).toString());
                    firstNameTextField.setText(model.getValueAt(selectedRow, 2).toString());
                    middleNameTextField.setText(model.getValueAt(selectedRow, 3).toString());
                    emailAddressTextField.setText(model.getValueAt(selectedRow, 4).toString());
                    courseComboBox.setSelectedItem(model.getValueAt(selectedRow, 5).toString()); // Use setSelectedItem for JComboBox
                    addressTextArea.setText(model.getValueAt(selectedRow, 6).toString());
                    // Handle radio buttons for sex
                    String sex = model.getValueAt(selectedRow, 7).toString();
                    if ("Male".equalsIgnoreCase(sex)) {
                        maleRadioBtn.setSelected(true);
                    } else if ("Female".equalsIgnoreCase(sex)) {
                        femaleRadioBtn.setSelected(true);
                    } else {
                        sexBtnGroup.clearSelection(); // Clear selection if sex is neither Male nor Female
                    }
                    ageTextField.setText(model.getValueAt(selectedRow, 8).toString());
                    sectionTextField.setText(model.getValueAt(selectedRow, 9).toString());
                }
            }
        });
    }

    public void loadTableData() {
        if (controller != null) {
            List<User> users = controller.getAllUsers();
            model.setRowCount(0);

            for (User user : users) {
                List<String> row = new ArrayList<>();
                row.add(user.getStudentNo());
                row.add(user.getLastName());
                row.add(user.getFirstName());
                row.add(user.getMiddleName());
                row.add(user.getEmailAddress());
                row.add(user.getCourse());
                row.add(user.getAddress());
                row.add(user.getSex());
                row.add(String.valueOf(user.getAge()));
                row.add(user.getSection());
                model.addRow(row.toArray(new String[0]));
            }
        }
    }

    private void closeForm() {
        System.exit(0);
    }

    public void setSaveButtonText(String text) {
        saveBtn.setText(text);
    }

    public void enableControls() {
        emailAddressTextField.setEnabled(true);
        courseComboBox.setEnabled(true);
        addressTextArea.setEnabled(true);
        searchTextField.setEnabled(true);
        ageTextField.setEnabled(true);
        sectionTextField.setEnabled(true);

        addBtn.setEnabled(false);
        saveBtn.setEnabled(true);
        clearBtn.setEnabled(true);
        closeBtn.setEnabled(true);
        editBtn.setEnabled(false);
        deleteBtn.setEnabled(false);

    }

    public void disableControls() {
        studentNumberTextField.setEnabled(false);
        lastNameTextField.setEnabled(false);
        firstNameTextField.setEnabled(false);
        middleNameTextField.setEnabled(false);
        emailAddressTextField.setEnabled(false);
        courseComboBox.setEnabled(false);
        addressTextArea.setEnabled(false);
        maleRadioBtn.setEnabled(false);
        femaleRadioBtn.setEnabled(false);
        ageTextField.setEnabled(false);
        sectionTextField.setEnabled(false);

        addBtn.setEnabled(true);
        saveBtn.setEnabled(false);
        clearBtn.setEnabled(false);
        closeBtn.setEnabled(true);
        editBtn.setEnabled(true);
        deleteBtn.setEnabled(true);
        closeBtn.setText("Close");
    }

    public void clearForm() {
        studentNumberTextField.setText("");
        lastNameTextField.setText("");
        firstNameTextField.setText("");
        middleNameTextField.setText("");
        emailAddressTextField.setText("");
        courseComboBox.setSelectedIndex(0);
        addressTextArea.setText("");
        sexBtnGroup.clearSelection();
        searchTextField.setText("");
        ageTextField.setText("");
        sectionTextField.setText("");
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private User getCurrentUserFromForm() {
        // Get selected sex from radio buttons
        String selectedSex = "";
        if (maleRadioBtn.isSelected()) {
            selectedSex = "Male";
        } else if (femaleRadioBtn.isSelected()) {
            selectedSex = "Female";
        }
        // No ID needed at all in the view
        return new User(
                "", // Empty string for ID (never used in the view)
                studentNumberTextField.getText(),
                lastNameTextField.getText(),
                firstNameTextField.getText(),
                middleNameTextField.getText(),
                emailAddressTextField.getText(),
                (String) courseComboBox.getSelectedItem(), // Cast to String
                addressTextArea.getText(),
                selectedSex, // Use the determined sex
                "",
                parseAge(ageTextField.getText()), // Use helper method
                sectionTextField.getText()
        );
    }

    private int parseAge(String ageText) {
        try {
            return Integer.valueOf(ageText);
        } catch (NumberFormatException e) {
            showError("Invalid age entered. Please enter a valid number.");
            return 0; // Return 0 or a suitable default
        }
    }

    private void updateTable(List<User> users) {
        DefaultTableModel model = (DefaultTableModel) studentListTable.getModel();
        model.setRowCount(0);
        for (User user : users) {
            List<String> row = new ArrayList<>();
            row.add(user.getStudentNo());
            row.add(user.getLastName());
            row.add(user.getFirstName());
            row.add(user.getMiddleName());
            row.add(user.getEmailAddress());
            row.add(user.getCourse());
            row.add(user.getAddress());
            row.add(user.getSex());
            row.add(String.valueOf(user.getAge()));
            row.add(user.getSection());
            model.addRow(row.toArray(new String[0]));
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

        sexBtnGroup = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        mainPanel = new javax.swing.JPanel();
        headerPanel = new RoundedPanel(30, 30);
        jLabel1 = new javax.swing.JLabel();
        studentDetailsPanel = new RoundedPanel(10, 10);
        jLabel3 = new javax.swing.JLabel();
        studentNumberTextField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        lastNameTextField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        firstNameTextField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        middleNameTextField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        emailAddressTextField = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        courseComboBox = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        addressTextArea = new javax.swing.JTextArea();
        jLabel11 = new javax.swing.JLabel();
        sectionTextField = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        ageTextField = new javax.swing.JTextField();
        maleRadioBtn = new javax.swing.JRadioButton();
        femaleRadioBtn = new javax.swing.JRadioButton();
        jLabel13 = new javax.swing.JLabel();
        addBtn = new javax.swing.JButton();
        saveBtn = new javax.swing.JButton();
        editBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();
        clearBtn = new javax.swing.JButton();
        closeBtn = new javax.swing.JButton();
        tablePanel = new RoundedPanel(10, 10);
        jLabel2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        studentListTable = new javax.swing.JTable();
        searchTextField = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        searchBtn = new javax.swing.JButton();
        printBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Student Information System");
        setPreferredSize(new java.awt.Dimension(1600, 800));

        jScrollPane1.setPreferredSize(new java.awt.Dimension(3866, 800));

        mainPanel.setBackground(new java.awt.Color(248, 250, 252));

        headerPanel.setBackground(new java.awt.Color(99, 102, 241));
        headerPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Student Information System");
        headerPanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        studentDetailsPanel.setBackground(new java.awt.Color(255, 255, 255));
        studentDetailsPanel.setBorder(new RoundedBorder(30, new java.awt.Color(0xCB, 0xD5, 0xE1), 2));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(100, 116, 139));
        jLabel3.setText("Student Number");

        studentNumberTextField.setBackground(new java.awt.Color(241, 245, 249));
        studentNumberTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentNumberTextFieldActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(100, 116, 139));
        jLabel4.setText("Last Name");

        lastNameTextField.setBackground(new java.awt.Color(241, 245, 249));
        lastNameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lastNameTextFieldActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(100, 116, 139));
        jLabel5.setText("First Name");

        firstNameTextField.setBackground(new java.awt.Color(241, 245, 249));
        firstNameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                firstNameTextFieldActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(100, 116, 139));
        jLabel6.setText("Middle Name");

        middleNameTextField.setBackground(new java.awt.Color(241, 245, 249));
        middleNameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                middleNameTextFieldActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(100, 116, 139));
        jLabel7.setText("Sex");

        emailAddressTextField.setBackground(new java.awt.Color(241, 245, 249));
        emailAddressTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailAddressTextFieldActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(100, 116, 139));
        jLabel8.setText("Email Address");

        courseComboBox.setBackground(new java.awt.Color(241, 245, 249));
        courseComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "College of Information and Communications Technology", "College of Engineering", "College of Education", "College of Management and Business Technology", "College of Criminology", "College of Public Administration and Disaster Management", "College of Arts and Sciences", "College of Industrial Technology", "College of Nursing" }));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(100, 116, 139));
        jLabel9.setText("Program");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(100, 116, 139));
        jLabel10.setText("Address");

        addressTextArea.setColumns(20);
        addressTextArea.setRows(5);
        jScrollPane2.setViewportView(addressTextArea);

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(100, 116, 139));
        jLabel11.setText("Section");

        sectionTextField.setBackground(new java.awt.Color(241, 245, 249));
        sectionTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sectionTextFieldActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(100, 116, 139));
        jLabel12.setText("Age");

        ageTextField.setBackground(new java.awt.Color(241, 245, 249));
        ageTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ageTextFieldActionPerformed(evt);
            }
        });

        sexBtnGroup.add(maleRadioBtn);
        maleRadioBtn.setText("Male");
        maleRadioBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                maleRadioBtnActionPerformed(evt);
            }
        });

        sexBtnGroup.add(femaleRadioBtn);
        femaleRadioBtn.setText("Female");

        jLabel13.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(30, 41, 59));
        jLabel13.setText("Student Details");

        addBtn.setBackground(new java.awt.Color(99, 102, 241));
        addBtn.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        addBtn.setForeground(new java.awt.Color(255, 255, 255));
        addBtn.setText("Add New");
        addBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBtnActionPerformed(evt);
            }
        });

        saveBtn.setBackground(new java.awt.Color(14, 165, 233));
        saveBtn.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        saveBtn.setForeground(new java.awt.Color(255, 255, 255));
        saveBtn.setText("Save");
        saveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBtnActionPerformed(evt);
            }
        });

        editBtn.setBackground(new java.awt.Color(245, 158, 11));
        editBtn.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        editBtn.setForeground(new java.awt.Color(255, 255, 255));
        editBtn.setText("Edit");
        editBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editBtnActionPerformed(evt);
            }
        });

        deleteBtn.setBackground(new java.awt.Color(239, 68, 68));
        deleteBtn.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        deleteBtn.setForeground(new java.awt.Color(255, 255, 255));
        deleteBtn.setText("Delete");
        deleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnActionPerformed(evt);
            }
        });

        clearBtn.setBackground(new java.awt.Color(203, 213, 225));
        clearBtn.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        clearBtn.setForeground(new java.awt.Color(255, 255, 255));
        clearBtn.setText("Clear");
        clearBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearBtnActionPerformed(evt);
            }
        });

        closeBtn.setBackground(new java.awt.Color(71, 85, 105));
        closeBtn.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        closeBtn.setForeground(new java.awt.Color(255, 255, 255));
        closeBtn.setText("Close");
        closeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout studentDetailsPanelLayout = new javax.swing.GroupLayout(studentDetailsPanel);
        studentDetailsPanel.setLayout(studentDetailsPanelLayout);
        studentDetailsPanelLayout.setHorizontalGroup(
            studentDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, studentDetailsPanelLayout.createSequentialGroup()
                .addGroup(studentDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(studentDetailsPanelLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(studentDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8)
                            .addGroup(studentDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel11)
                                .addComponent(jLabel7)
                                .addComponent(jLabel12)
                                .addComponent(jLabel9)
                                .addComponent(jLabel6)
                                .addGroup(studentDetailsPanelLayout.createSequentialGroup()
                                    .addGap(1, 1, 1)
                                    .addGroup(studentDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel5)
                                        .addComponent(jLabel4)))))
                        .addGap(21, 21, 21))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, studentDetailsPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(studentDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(courseComboBox, 0, 1, Short.MAX_VALUE)
                    .addComponent(ageTextField)
                    .addComponent(studentNumberTextField)
                    .addComponent(lastNameTextField)
                    .addComponent(firstNameTextField)
                    .addComponent(middleNameTextField)
                    .addComponent(emailAddressTextField)
                    .addComponent(sectionTextField)
                    .addGroup(studentDetailsPanelLayout.createSequentialGroup()
                        .addComponent(maleRadioBtn)
                        .addGap(36, 36, 36)
                        .addComponent(femaleRadioBtn)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(studentDetailsPanelLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel13)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(studentDetailsPanelLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(studentDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(studentDetailsPanelLayout.createSequentialGroup()
                        .addComponent(deleteBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(clearBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(closeBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(studentDetailsPanelLayout.createSequentialGroup()
                        .addComponent(addBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(saveBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(editBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel10)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 33, Short.MAX_VALUE))
        );
        studentDetailsPanelLayout.setVerticalGroup(
            studentDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(studentDetailsPanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel13)
                .addGap(18, 18, 18)
                .addGroup(studentDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(studentNumberTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(studentDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lastNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(studentDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(firstNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(17, 17, 17)
                .addGroup(studentDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(middleNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(28, 28, 28)
                .addGroup(studentDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(emailAddressTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(studentDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(ageTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(studentDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(courseComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(19, 19, 19)
                .addGroup(studentDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(sectionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(studentDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(maleRadioBtn)
                    .addComponent(femaleRadioBtn))
                .addGap(18, 18, 18)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(studentDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(saveBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(studentDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(deleteBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clearBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(closeBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tablePanel.setBackground(new java.awt.Color(255, 255, 255));
        tablePanel.setBorder(new RoundedBorder(30, new java.awt.Color(0xCB, 0xD5, 0xE1), 2));

        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(30, 41, 59));
        jLabel2.setText("Student List");

        studentListTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Student Number", "Last Name", "First Name", "Middle Name", "Email Address", "Age", "Program", "Section", "Sex", "Address"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        studentListTable.setRowHeight(25);
        jScrollPane3.setViewportView(studentListTable);

        searchTextField.setBackground(new java.awt.Color(241, 245, 249));
        searchTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchTextFieldActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(100, 116, 139));
        jLabel14.setText("Search");

        searchBtn.setBackground(new java.awt.Color(14, 165, 233));
        searchBtn.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        searchBtn.setForeground(new java.awt.Color(255, 255, 255));
        searchBtn.setText("Search");
        searchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBtnActionPerformed(evt);
            }
        });

        printBtn.setBackground(new java.awt.Color(99, 102, 241));
        printBtn.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        printBtn.setForeground(new java.awt.Color(255, 255, 255));
        printBtn.setText("Print");
        printBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout tablePanelLayout = new javax.swing.GroupLayout(tablePanel);
        tablePanel.setLayout(tablePanelLayout);
        tablePanelLayout.setHorizontalGroup(
            tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tablePanelLayout.createSequentialGroup()
                .addGroup(tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tablePanelLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel2))
                    .addGroup(tablePanelLayout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(searchBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(tablePanelLayout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(printBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 799, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(211, Short.MAX_VALUE))
        );
        tablePanelLayout.setVerticalGroup(
            tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tablePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(8, 8, 8)
                .addGroup(tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 599, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(printBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(studentDetailsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(tablePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(headerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 1366, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(2339, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(headerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(studentDetailsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tablePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(400, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(mainPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1600, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 868, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void searchTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchTextFieldActionPerformed

    private void maleRadioBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_maleRadioBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_maleRadioBtnActionPerformed

    private void ageTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ageTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ageTextFieldActionPerformed

    private void sectionTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sectionTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sectionTextFieldActionPerformed

    private void emailAddressTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailAddressTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emailAddressTextFieldActionPerformed

    private void middleNameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_middleNameTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_middleNameTextFieldActionPerformed

    private void firstNameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_firstNameTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_firstNameTextFieldActionPerformed

    private void lastNameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lastNameTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lastNameTextFieldActionPerformed

    private void studentNumberTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentNumberTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_studentNumberTextFieldActionPerformed

    private void searchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBtnActionPerformed
        // TODO add your handling code here:
        String search = searchTextField.getText().trim();
        if (!search.isEmpty()) {
            List<User> searchResults = controller.searchUsers(search);
            updateTable(searchResults);
        } else {
            loadTableData();
        }
    }//GEN-LAST:event_searchBtnActionPerformed

    private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnActionPerformed
        // TODO add your handling code here:
        enableControls();
        clearForm();
        lastNameTextField.setEnabled(true);
        firstNameTextField.setEnabled(true);
        middleNameTextField.setEnabled(true);
        courseComboBox.setEnabled(true);
        maleRadioBtn.setEnabled(true);
        femaleRadioBtn.setEnabled(true);
        editBtn.setEnabled(false);
        deleteBtn.setEnabled(false);
        closeBtn.setText("Cancel");
        studentListTable.setEnabled(false);
        // Generate and set the *student number*, not the ID
        String nextStudentNo = controller.generateNextStudentNo();
        if (nextStudentNo != null) {
            studentNumberTextField.setText(nextStudentNo);
        }
    }//GEN-LAST:event_addBtnActionPerformed

    private void editBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editBtnActionPerformed
        // TODO add your handling code here:
        if (studentNumberTextField.getText().isEmpty()) {
            showMessage("Please select a record to edit.");
            return;
        }

        try {
            User existingUser = controller.getUserByStudentNo(studentNumberTextField.getText());
            if (existingUser == null) {
                showMessage("No record found with the given student number.");
                return; // Exit the method if no user found
            }
        } catch (Exception ex) {
            showError("Error checking student number: " + ex.getMessage());
            return;
        }

        enableControls();
        studentNumberTextField.setEnabled(false); // Keep student number disabled during edit
        saveBtn.setText("Update");
        studentListTable.setEnabled(false);
        closeBtn.setText("Cancel");
    }//GEN-LAST:event_editBtnActionPerformed

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
        // TODO add your handling code here:
        String studentNo = studentNumberTextField.getText(); // Get Student Number
        if (studentNo.isEmpty()) {
            showMessage("Please select a record to delete.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the record with student number: " + studentNo + "?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            // Get the user by student number and then delete by ID
            try {
                User userToDelete = controller.getUserByStudentNo(studentNo);
                if (userToDelete != null) {
                    controller.deleteUser(userToDelete.getId()); // Delete by ID
                } else {
                    showError("User with student number " + studentNo + " not found.");
                }
            } catch (Exception ex) {
                showError("Error deleting user: " + ex.getMessage());
            }
        }
    }//GEN-LAST:event_deleteBtnActionPerformed

    private void closeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeBtnActionPerformed
        // TODO add your handling code here:
        if (closeBtn.getText().equals("Cancel")) {
            clearForm();
            disableControls();
            return;
        }
        closeForm();
    }//GEN-LAST:event_closeBtnActionPerformed

    private void clearBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_clearBtnActionPerformed

    private void saveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBtnActionPerformed
        User user = getCurrentUserFromForm();
        if (saveBtn.getText().equals("Save")) {
            controller.registerUser(user);
        } else {
            try {
                User existingUser = controller.getUserByStudentNo(studentNumberTextField.getText());
                if (existingUser != null) {
                    existingUser.setStudentNo(studentNumberTextField.getText());
                    existingUser.setLastName(lastNameTextField.getText());
                    existingUser.setFirstName(firstNameTextField.getText());
                    existingUser.setMiddleName(middleNameTextField.getText());
                    existingUser.setEmailAddress(emailAddressTextField.getText());
                    existingUser.setCourse((String) courseComboBox.getSelectedItem());
                    existingUser.setAddress(addressTextArea.getText());
                    String selectedSex = "";
                    if (maleRadioBtn.isSelected()) {
                        selectedSex = "Male";
                    } else if (femaleRadioBtn.isSelected()) {
                        selectedSex = "Female";
                    }
                    existingUser.setSex(selectedSex);
                    existingUser.setAge(parseAge(ageTextField.getText()));
                    existingUser.setSection(sectionTextField.getText());

                    controller.updateUser(existingUser);
                    studentListTable.setEnabled(true);
                } else {
                    showError("User with student number " + studentNumberTextField.getText() + " not found.");
                }
            } catch (Exception ex) {
                showError("Error updating user: " + ex.getMessage());
            }
        }
    }//GEN-LAST:event_saveBtnActionPerformed

    private void printBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printBtnActionPerformed
        // TODO add your handling code here:
        try {
            boolean complete = studentListTable.print();
            if (complete) {
                showMessage("Printing Completed");
            } else {
                showMessage("Printing Canceled");
            }
        } catch (Exception ex) {
            showError("Error printing: " + ex.getMessage());
        }
    }//GEN-LAST:event_printBtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(RegistrationForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegistrationForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegistrationForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegistrationForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegistrationForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBtn;
    private javax.swing.JTextArea addressTextArea;
    private javax.swing.JTextField ageTextField;
    private javax.swing.JButton clearBtn;
    private javax.swing.JButton closeBtn;
    private javax.swing.JComboBox<String> courseComboBox;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JButton editBtn;
    private javax.swing.JTextField emailAddressTextField;
    private javax.swing.JRadioButton femaleRadioBtn;
    private javax.swing.JTextField firstNameTextField;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JTextField lastNameTextField;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JRadioButton maleRadioBtn;
    private javax.swing.JTextField middleNameTextField;
    private javax.swing.JButton printBtn;
    private javax.swing.JButton saveBtn;
    private javax.swing.JButton searchBtn;
    private javax.swing.JTextField searchTextField;
    private javax.swing.JTextField sectionTextField;
    private javax.swing.ButtonGroup sexBtnGroup;
    private javax.swing.JPanel studentDetailsPanel;
    private javax.swing.JTable studentListTable;
    private javax.swing.JTextField studentNumberTextField;
    private javax.swing.JPanel tablePanel;
    // End of variables declaration//GEN-END:variables
}

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.StringJoiner;

public class StudentRegistrationForm extends JFrame implements ActionListener {

    private final JTextField rollField = new JTextField(15);
    private final JTextField nameField = new JTextField(15);
    private final JRadioButton maleButton = new JRadioButton("Male");
    private final JRadioButton femaleButton = new JRadioButton("Female");
    private final ButtonGroup genderGroup = new ButtonGroup();
    private final JCheckBox javaBox = new JCheckBox("Java");
    private final JCheckBox pythonBox = new JCheckBox("Python");
    private final JCheckBox dbmsBox = new JCheckBox("DBMS");
    private final JComboBox<String> branchBox = new JComboBox<>(new String[]{
            "-- Select Branch --", "CSE", "CSE (AI & ML)", "ECE", "EEE", "Mechanical", "Civil"});
    private final JComboBox<String> cityBox = new JComboBox<>(new String[]{
            "-- Select City --", "Hyderabad", "Warangal", "Karimnagar", "Nizamabad", "Khammam"});
    private final JTextArea addressArea = new JTextArea(3, 20);
    private final JTextArea outputArea = new JTextArea(8, 40);
    private final JButton submitButton = new JButton("Submit");
    private final JButton resetButton = new JButton("Reset");
    private final JButton exitButton = new JButton("Exit");
    private final DefaultTableModel tableModel = new DefaultTableModel(
            new String[]{"Roll No", "Name", "Gender", "Subjects", "Branch", "City"}, 0);

    public StudentRegistrationForm() {
        super("Student Registration Form");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(5, 5));

        JLabel header = new JLabel("Student Registration", createLogoIcon(), SwingConstants.LEFT);
        header.setFont(new Font("SansSerif", Font.BOLD, 20));
        header.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        add(header, BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Registration", buildRegistrationTab());
        tabs.addTab("Submitted Records", new JScrollPane(new JTable(tableModel)));
        add(tabs, BorderLayout.CENTER);

        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);

        outputArea.setEditable(false);
        submitButton.addActionListener(this);
        resetButton.addActionListener(this);
                exitButton.addActionListener(this);

                pack();
                setLocationRelativeTo(null);
        }

        private JPanel buildRegistrationTab() {
                JPanel form = new JPanel(new GridBagLayout());
                form.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                GridBagConstraints constraints = new GridBagConstraints();
                constraints.insets = new Insets(5, 5, 5, 5);
                constraints.anchor = GridBagConstraints.WEST;

                addFormRow(form, constraints, 0, "Roll No:", rollField);
                addFormRow(form, constraints, 1, "Name:", nameField);

                JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
                genderPanel.add(maleButton);
                genderPanel.add(femaleButton);
                addFormRow(form, constraints, 2, "Gender:", genderPanel);

                JPanel subjectsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
                subjectsPanel.add(javaBox);
                subjectsPanel.add(pythonBox);
                subjectsPanel.add(dbmsBox);
                addFormRow(form, constraints, 3, "Subjects:", subjectsPanel);
                addFormRow(form, constraints, 4, "Branch:", branchBox);
                addFormRow(form, constraints, 5, "City:", cityBox);

                constraints.gridx = 0;
                constraints.gridy = 6;
                form.add(new JLabel("Address:"), constraints);
                constraints.gridx = 1;
                form.add(new JScrollPane(addressArea), constraints);

                JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER));
                buttons.add(submitButton);
                buttons.add(resetButton);
                buttons.add(exitButton);
                constraints.gridx = 0;
                constraints.gridy = 7;
                constraints.gridwidth = 2;
                constraints.anchor = GridBagConstraints.CENTER;
                form.add(buttons, constraints);

                constraints.gridy = 8;
                constraints.fill = GridBagConstraints.BOTH;
                constraints.weightx = 1;
                constraints.weighty = 1;
                form.add(new JScrollPane(outputArea), constraints);
                return form;
        }

        private void addFormRow(JPanel panel, GridBagConstraints constraints, int row,
                                                        String label, Component input) {
                constraints.gridy = row;
                constraints.gridx = 0;
                constraints.gridwidth = 1;
                constraints.fill = GridBagConstraints.NONE;
                constraints.weightx = 0;
                constraints.weighty = 0;
                panel.add(new JLabel(label), constraints);
                constraints.gridx = 1;
                panel.add(input, constraints);
        }

        private ImageIcon createLogoIcon() {
                BufferedImage image = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
                Graphics2D graphics = image.createGraphics();
                graphics.setColor(new Color(40, 100, 180));
                graphics.fillRoundRect(1, 1, 30, 30, 8, 8);
                graphics.setColor(Color.WHITE);
                graphics.setFont(new Font("SansSerif", Font.BOLD, 18));
                graphics.drawString("S", 10, 23);
                graphics.dispose();
                return new ImageIcon(image);
        }

        @Override
        public void actionPerformed(ActionEvent event) {
                Object source = event.getSource();
                if (source == exitButton) {
                        dispose();
                } else if (source == resetButton) {
                        resetForm();
                } else if (source == submitButton) {
                        submitForm();
                }
        }

        private void submitForm() {
                String roll = rollField.getText().trim();
                String name = nameField.getText().trim();
                String gender = maleButton.isSelected() ? "Male"
                                : femaleButton.isSelected() ? "Female" : "";
                String branch = (String) branchBox.getSelectedItem();
                String city = (String) cityBox.getSelectedItem();

                if (roll.isEmpty() || name.isEmpty() || gender.isEmpty()
                                || branchBox.getSelectedIndex() == 0 || cityBox.getSelectedIndex() == 0) {
                        JOptionPane.showMessageDialog(this, "Please complete all required fields.",
                                        "Incomplete Form", JOptionPane.WARNING_MESSAGE);
                        return;
                }

                StringJoiner subjects = new StringJoiner(", ");
                if (javaBox.isSelected()) subjects.add("Java");
                if (pythonBox.isSelected()) subjects.add("Python");
                if (dbmsBox.isSelected()) subjects.add("DBMS");
                String subjectText = subjects.length() == 0 ? "None" : subjects.toString();

                tableModel.addRow(new Object[]{roll, name, gender, subjectText, branch, city});
                outputArea.append("Roll No: " + roll + "\nName: " + name + "\nGender: " + gender
                                + "\nSubjects: " + subjectText + "\nBranch: " + branch + "\nCity: " + city
                                + "\nAddress: " + addressArea.getText().trim() + "\n\n");
                resetForm();
        }

        private void resetForm() {
                rollField.setText("");
                nameField.setText("");
                genderGroup.clearSelection();
                javaBox.setSelected(false);
                pythonBox.setSelected(false);
                dbmsBox.setSelected(false);
                branchBox.setSelectedIndex(0);
                cityBox.setSelectedIndex(0);
                addressArea.setText("");
        }

        public static void main(String[] args) {
                SwingUtilities.invokeLater(() -> new StudentRegistrationForm().setVisible(true));
        }
}

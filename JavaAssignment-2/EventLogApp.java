import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;

public class EventLogApp extends JFrame {

    private final JTextArea logArea = new JTextArea(16, 55);
    private final JTextField inputField = new JTextField(30);
    private final JLabel wordCountLabel = new JLabel("Typed text: (nothing yet)   |   Words: 0");
    private final JPopupMenu popupMenu = new JPopupMenu();
    private int actionClickCount = 0;

    public EventLogApp() {
        super("Swing Event Handling Demo");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(5, 5));

        logArea.setEditable(false);
        logArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        buildToolBar();
        buildLogArea();
        buildInputPanel();
        buildPopupMenu();
        buildMenuBar();

        pack();
        setLocationRelativeTo(null);
    }

    private void log(String message) {
        logArea.append(message + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }

    private void buildToolBar() {
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        ActionListener buttonListener = e -> {
            actionClickCount++;
            log("ActionEvent: " + e.getActionCommand() + " clicked (Count = " + actionClickCount
                    + "), command = '" + e.getActionCommand() + "'");
        };
        for (String name : new String[]{"Start", "Pause", "Stop"}) {
            JButton button = new JButton(name);
            button.addActionListener(buttonListener);
            toolBar.add(button);
        }
        add(toolBar, BorderLayout.NORTH);
    }

    private void buildLogArea() {
        MouseAdapter mouseHandler = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String extra = (e.getClickCount() == 2) ? " (double click)" : "";
                log("MouseEvent: Clicked at (" + e.getX() + ", " + e.getY() + ") with button"
                        + e.getButton() + ", clicks = " + e.getClickCount() + extra);
            }
            @Override
            public void mouseDragged(MouseEvent e) {
                log("MouseEvent: Dragged to (" + e.getX() + ", " + e.getY() + ")");
            }
            @Override
            public void mousePressed(MouseEvent e)  { showPopupIfTrigger(e); }
            @Override
            public void mouseReleased(MouseEvent e) { showPopupIfTrigger(e); }
        };
        logArea.addMouseListener(mouseHandler);
        logArea.addMouseMotionListener(mouseHandler);
        add(new JScrollPane(logArea), BorderLayout.CENTER);
    }

    private void showPopupIfTrigger(MouseEvent e) {
        if (e.isPopupTrigger()) {
            popupMenu.show(e.getComponent(), e.getX(), e.getY());
        }
    }

    private void buildInputPanel() {
        inputField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                String shown = Character.isISOControl(c) ? "<control " + (int) c + ">" : "'" + c + "'";
                log("KeyEvent: Typed " + shown);
            }
            @Override
            public void keyPressed(KeyEvent e) {
                log("KeyEvent: Pressed " + KeyEvent.getKeyText(e.getKeyCode())
                        + " (code = " + e.getKeyCode() + ")");
            }
            @Override
            public void keyReleased(KeyEvent e) {
                log("KeyEvent: Released " + KeyEvent.getKeyText(e.getKeyCode())
                        + " (code = " + e.getKeyCode() + ")");
                updateWordLabel();
            }
        });

        JPanel south = new JPanel(new GridLayout(2, 1));
        JPanel inputRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inputRow.add(new JLabel("Type here:"));
        inputRow.add(inputField);
        south.add(inputRow);
        south.add(wordCountLabel);
        add(south, BorderLayout.SOUTH);
    }

    private void updateWordLabel() {
        String text = inputField.getText().trim();
        int words = text.isEmpty() ? 0 : text.split("\\s+").length;
        wordCountLabel.setText("Typed text: " + inputField.getText() + "   |   Words: " + words);
    }

    private void buildPopupMenu() {
        JMenuItem clearItem = new JMenuItem("Clear Log");
        JMenuItem copyItem = new JMenuItem("Copy");
        clearItem.addActionListener(e -> {
            logArea.setText("");
            log("ActionEvent: Clear Log selected (log was cleared)");
        });
        copyItem.addActionListener(e -> {
            String selected = logArea.getSelectedText();
            String toCopy = (selected != null) ? selected : logArea.getText();
            try {
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(toCopy), null);
                log("ActionEvent: Copy selected (" + toCopy.length() + " characters copied)");
            } catch (IllegalStateException | HeadlessException | SecurityException ex) {
                log("ActionEvent: Copy failed - " + ex.getMessage());
            }
        });
        popupMenu.add(clearItem);
        popupMenu.add(copyItem);
    }

    private void buildMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem newItem = new JMenuItem("New");
        JMenuItem exitItem = new JMenuItem("Exit");
        newItem.addActionListener(e -> {
            logArea.setText("");
            inputField.setText("");
            updateWordLabel();
            log("ActionEvent: File > New clicked");
        });
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(newItem);
        fileMenu.add(exitItem);

        JMenu editMenu = new JMenu("Edit");
        JCheckBoxMenuItem editableItem = new JCheckBoxMenuItem("Editable");
        JRadioButtonMenuItem smallItem = new JRadioButtonMenuItem("Small", true);
        JRadioButtonMenuItem largeItem = new JRadioButtonMenuItem("Large");
        ButtonGroup sizeGroup = new ButtonGroup();
        sizeGroup.add(smallItem);
        sizeGroup.add(largeItem);

        editableItem.addItemListener(e -> {
            boolean on = (e.getStateChange() == ItemEvent.SELECTED);
            logArea.setEditable(on);
            log("ItemEvent: Editable " + (on ? "checked" : "unchecked"));
        });
        smallItem.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                setLogFontSize(12);
                log("ItemEvent: Small font selected");
            }
        });
        largeItem.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                setLogFontSize(20);
                log("ItemEvent: Large font selected");
            }
        });
        editMenu.add(editableItem);
        editMenu.add(new JSeparator());
        editMenu.add(smallItem);
        editMenu.add(largeItem);

        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Swing event demo: ActionEvent, MouseEvent, KeyEvent, ItemEvent", "About",
                JOptionPane.INFORMATION_MESSAGE));
        helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);
    }

    private void setLogFontSize(int size) {
        logArea.setFont(logArea.getFont().deriveFont((float) size));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EventLogApp().setVisible(true));
    }
}

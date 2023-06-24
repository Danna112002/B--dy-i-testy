import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;

public class GUIExample extends JFrame {
    private JPanel simpleSoundPanel;
    private JButton rampButton;
    private JButton squareButton;
    private JButton sineButton;
    private SoundOptionsPanel soundOptionsPanel;

    public GUIExample() {
    setTitle("Sound Parameters");
    setSize(1200, 900);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
    tabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);

    simpleSoundPanel = new JPanel();
    tabbedPane.addTab("Simple Sound", simpleSoundPanel);

    JPanel compositeSoundPanel = new JPanel();
    tabbedPane.addTab("Composite Sound", compositeSoundPanel);

    // Set custom tab renderer
    tabbedPane.setUI(new CustomTabbedPaneUI());

    // Create sound options panel
    soundOptionsPanel = new SoundOptionsPanel();

    // Create buttons for sound options
    rampButton = new JButton("Ramp");
    squareButton = new JButton("Square");
    sineButton = new JButton("Sine");

    // Add action listeners to the buttons
    rampButton.addActionListener(new SoundOptionButtonListener(SoundOption.RAMP));
    squareButton.addActionListener(new SoundOptionButtonListener(SoundOption.SQUARE));
    sineButton.addActionListener(new SoundOptionButtonListener(SoundOption.SINE));

    // Set layout for the simple sound panel
    simpleSoundPanel.setLayout(new GridBagLayout());

    // Create a panel for the sound choice buttons
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new GridLayout(3, 1));
    buttonPanel.add(rampButton);
    buttonPanel.add(squareButton);
    buttonPanel.add(sineButton);

    // Set constraints for the buttonPanel to fill the height and align on the left
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.fill = GridBagConstraints.VERTICAL;
    gbc.anchor = GridBagConstraints.WEST; // Align on the left
    //gbc.weighty = 2;
    simpleSoundPanel.add(buttonPanel, gbc);

    // Add the soundOptionsPanel to grid gbc1
    GridBagConstraints gbc1 = new GridBagConstraints();
    gbc1.gridx = 0;
    gbc1.gridy = 0;
    //gbc.fill = GridBagConstraints.BOTH;
    gbc1.anchor = GridBagConstraints.NORTHEAST; // Reset anchor to center
    gbc1.weightx = 1.0;
    gbc1.weighty = 1.0;
    simpleSoundPanel.add(soundOptionsPanel, gbc1);

    add(tabbedPane);
}


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GUIExample example = new GUIExample();
            example.setVisible(true);
        });
    }

    // Custom TabbedPaneUI class
    private static class CustomTabbedPaneUI extends javax.swing.plaf.basic.BasicTabbedPaneUI {
         private static final int TAB_WIDTH = 596;  // Adjust this value to widen or narrow the tab buttons
        private static final int TAB_HEIGHT = 30;  // Adjust this value to change the tab button height
        
        @Override
        protected int calculateTabWidth(int tabPlacement, int tabIndex, FontMetrics metrics) {
            return TAB_WIDTH;
        }
        
        @Override
        protected int calculateTabHeight(int tabPlacement, int tabIndex, int fontHeight) {
            return TAB_HEIGHT;
        }


        @Override
        protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int width, int height, boolean isSelected) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(Color.LIGHT_GRAY);

            Rectangle2D tabRect = new Rectangle2D.Double(x, y, width, height);
            g2d.fill(tabRect);

            g2d.dispose();
        }

        @Override
        protected void paintText(Graphics g, int tabPlacement, Font font, FontMetrics metrics, int tabIndex, String title, Rectangle textRect, boolean isSelected) {
            super.paintText(g, tabPlacement, font, metrics, tabIndex, title, textRect, isSelected);

            if (isSelected) {
                g.setColor(Color.BLACK);
            } else {
                g.setColor(Color.GRAY);
            }

            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setFont(font);
            g2d.drawString(title, textRect.x, textRect.y + metrics.getAscent());
            g2d.dispose();
        }
    }

private enum SoundOption {
        RAMP("Ramp", "Frequency:", "Amplitude:", "Proportion:"),
        SQUARE("Square", "Frequency:", "Amplitude: ", "Duty factor:"),
        SINE("Sine", "Frequency:", "Amplitude: ", "Phaze: ");

        private String name;
        private String property1Label;
        private String property2Label;
        private String property3Label;

        SoundOption(String name, String property1Label, String property2Label, String property3Label) {
            this.name = name;
            this.property1Label = property1Label;
            this.property2Label = property2Label;
            this.property3Label = property3Label;
        }

        public String getName() {
            return name;
        }

        public String getProperty1Label() {
            return property1Label;
        }

        public String getProperty2Label() {
            return property2Label;
        }

        public String getProperty3Label() {
            return property3Label;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    // Sound option button listener
    private class SoundOptionButtonListener implements ActionListener {
        private SoundOption soundOption;

        public SoundOptionButtonListener(SoundOption soundOption) {
            this.soundOption = soundOption;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // Update the sound option in the sound options panel
            soundOptionsPanel.setSoundOption(soundOption);
        }
    }

    // Sound options panel
    private class SoundOptionsPanel extends JPanel {
        private JLabel optionLabel;
        private JLabel property1Label;
        private JTextField property1TextField;
        private JLabel property2Label;
        private JTextField property2TextField;
        private JLabel property3Label;
        private JTextField property3TextField;

        /**
         * 
         */
        public SoundOptionsPanel() {
            setLayout(new GridBagLayout());
            GridBagConstraints gbc2 = new GridBagConstraints();
            gbc2.gridx = 0;
            gbc2.gridy = 0;
            gbc2.anchor = GridBagConstraints.NORTH;
            gbc2.insets = new Insets(10, 10, 10, 10);

            optionLabel = new JLabel("Selected Sound Option: None");
            property1Label = new JLabel();
            property1TextField = new JTextField();
            property2Label = new JLabel();
            property2TextField = new JTextField();
            property3Label = new JLabel();
            property3TextField = new JTextField();

                // Set preferred size for text fields
            property1TextField.setPreferredSize(new Dimension(200, 30));
            property2TextField.setPreferredSize(new Dimension(200, 30));
            property3TextField.setPreferredSize(new Dimension(200, 30));

            add(optionLabel, gbc2);

            gbc2.gridy++;
            add(new JLabel(), gbc2); // Empty label for spacing
            //gbc2.gridx = 2;
            //gbc2.gridy = 1;
            gbc2.anchor = GridBagConstraints.WEST;
            add(property1Label, gbc2);
            gbc2.gridy++;
            //gbc2.gridx = 2;
            //gbc2.gridy = 1;
            gbc2.anchor = GridBagConstraints.WEST;
            add(property1TextField, gbc2);
            
            gbc2.gridy++;
            add(property2Label, gbc2);
            gbc2.gridy++;
            add(property2TextField, gbc2);
            
            gbc2.gridy++;
            add(property3Label, gbc2);
            gbc2.gridy++;
            add(property3TextField, gbc2);
            
        }

        public void setSoundOption(SoundOption soundOption) {
            optionLabel.setText("Selected Sound Option: " + soundOption.getName());
            property1Label.setText(soundOption.getProperty1Label());
            property2Label.setText(soundOption.getProperty2Label());
            property3Label.setText(soundOption.getProperty3Label());

            // Add your code here to update the text fields and labels based on the selected sound option
        }
    }
}

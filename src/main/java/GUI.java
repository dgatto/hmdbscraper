import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI extends Panel {
    Frame mainFrame; // hueh hueh gotta hack the mainframe
    String filename;
    String range;
    Label statusLbl = new Label("status");
    Label rangeLbl = new Label("<html>Use comma separated values to specify a range of Metabolites.<br />Use semicolons to specify multiple ranges.<br />Example: 1,50;200,220;500,700</html>");
    JTextField rangeField = new JTextField(20);
    ParseHandler parser = new ParseHandler();
    ExcelWriter writer = ExcelWriter.getSharedApplication();

    private GUI() { } // make your constructor private, so the only war
    // to access "application" is through singleton pattern

    private static GUI _app;

    public static GUI getSharedApplication()
    {
        if (_app == null)
            _app = new GUI();
        return _app;
    }

    public void generate() {
        mainFrame = new Frame("Generate Metabolites");
        mainFrame.setSize(500,500);//frame size 300 width and 300 height
        mainFrame.setVisible(true);//now frame will be visible, by default not visible
        mainFrame.setLayout(null);//no layout manager

        Button generateBtn = new Button("Generate Metabolite Excel File");
        generateBtn.setBounds(100,200,300,45);// setting button position
        mainFrame.add(generateBtn);
        mainFrame.add(statusLbl);
        mainFrame.add(rangeLbl);
        mainFrame.add(rangeField);
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        statusLbl.setBounds(5, 300, 485, 50);
        rangeLbl.setBounds(5, 40, 485, 50);

        Font font1 = new Font("SansSerif", Font.BOLD, 10);
        rangeField.setBounds(5,90,200,50);
        rangeField.setFont(font1);

        rangeField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = rangeField.getText();
                rangeField.setText(text += "\n");
                rangeField.selectAll();
            }
        });

        generateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                range = rangeField.getText();
                parser.kickOff(getFilename(), getRange());
            }
        });

        final FileDialog fd = new FileDialog(mainFrame, "Select file");
        Button fileDialogButton = new Button("Select 'hmdb_metabolites.xml' file location.");
        fileDialogButton.setBounds(100, 150, 300, 30);
        mainFrame.add(fileDialogButton);

        fileDialogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fd.setVisible(true);
                if (fd.getFile() != null) {
                    filename = fd.getDirectory() + fd.getFile();
                    statusLbl.setText("File selected: " + fd.getDirectory() + fd.getFile());
                } else {
                    filename = "hmdb_metabolites.xml";
                    statusLbl.setText("No file selected, default is hmdb_metabolites.xml in current directory.");
                }
            }
        });
    }

    public void setStatusLbl(String message) {
        statusLbl.setText(message);
    }

    public String getFilename() {
        return filename;
    }

    public String getRange() {
        return range;
    }
}

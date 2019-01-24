import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class GUI extends Panel {
    Frame mainFrame; // hueh hueh hack the mainframe
    String filename;
    Label statusLbl = new Label("status");

    public void generate() {
        mainFrame = new Frame("Generate Metabolites");
        mainFrame.setSize(500,250);//frame size 300 width and 300 height
        mainFrame.setVisible(true);//now frame will be visible, by default not visible
        mainFrame.setLayout(null);//no layout manager

        Button generateBtn = new Button("Generate Metabolite Excel File");
        generateBtn.setBounds(100,200,300,45);// setting button position
        mainFrame.add(generateBtn);//adding button into frame
        mainFrame.add(statusLbl);
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        statusLbl.setBounds(5, 50, 485, 50);

        generateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                new ParseHandler(getFilename());
                statusLbl.setText("File written to metabolites.xlsx");
            }
        });

        final FileDialog fd = new FileDialog(mainFrame, "Select file");
        Button fileDialogButton = new Button("Open file");
        fileDialogButton.setBounds(100, 150, 150, 30);
        mainFrame.add(fileDialogButton);

        fileDialogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fd.setVisible(true);
                if (fd.getFile() != null) {
                    filename = fd.getFile();
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
        if (filename == null) {
            return "hmdb_metabolites.xml";
        } else {
            return filename;
        }
    }
}

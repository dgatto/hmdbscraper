import java.awt.*;
import java.awt.event.*;

public class GUI extends Frame {
    String filename;

    public void generate() {
        Button generateBtn = new Button("Generate Metabolite Excel File");
        generateBtn.setBounds(30,100,300,30);// setting button position
        this.add(generateBtn);//adding button into frame
        this.setSize(350,300);//frame size 300 width and 300 height
        this.setLayout(null);//no layout manager
        this.setVisible(true);//now frame will be visible, by default not visible
        generateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                new ParseHandler("hmdb_metabolites.xml");
            }
        });

//        //Create a file chooser
//        FileDialog fd = new FileDialog(this);
//        fd.setFile("*.xml");
//        fd.setVisible(true);
//        String filename = fd.getFile();
//        System.out.println(filename);
    }

    public String getFilename() {
        return filename;
    }
}


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Properties;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class GUI extends Panel {
    Frame mainFrame; // hueh hueh gotta hack the mainframe
    String filename;
    String range;
    String queryType = "hmdb_id";
    JTextField rangeField = new JTextField(20);
    ParseHandler parser = new ParseHandler();
    ArrayList<String> categories = new ArrayList();
    QueryHandler queryHandler = QueryHandler.getSharedApplication();

    private GUI() { } // make constructor private, so the only way to access "application" is through singleton pattern

    private static GUI _app;

    public static GUI getSharedApplication() {
        if (_app == null)
            _app = new GUI();
        return _app;
    }

    public void generate() {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("HMDB Metabolite Scraper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set up the content pane.
        addComponentsToPane(frame.getContentPane());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    final static boolean shouldFill = true;
    final static boolean shouldWeightX = true;
    final static boolean RIGHT_TO_LEFT = false;

    public void addComponentsToPane(Container pane) {
        if (RIGHT_TO_LEFT) {
            pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        }

        // TODO: just a huge refactor of this whole 'checkbox' business

        JLabel categoryInfoLabel;
        JLabel rangeInfoLabel;
        JButton generateButton;
        JButton openFileDialogButton;
        JTextField rangeTextField;
        JScrollPane categoryScrollPane;
        JPanel categoryCheckboxPanel;
        JCheckBox recordInfoTreeCheckBox;
        JCheckBox metaboliteIdenTreeCheckbox;
        JCheckBox hmdbIdCheckBox;
        JCheckBox secondaryAccessionNumbersCheckbox;
        JCheckBox commonNameCheckBox;
        JCheckBox structureCheckBox;
        JCheckBox chemicalFormulaCheckBox;
        JCheckBox monoMolecularWeightCheckBox;
        JCheckBox casRegNumberCheckBox;
        JCheckBox smilesCheckBox;
        JCheckBox inChlKeyCheckBox;
        JCheckBox chemicalTaxonomyTreeCheckBox;
        JCheckBox kingdomCheckBox;
        JCheckBox superClassCheckBox;
        JCheckBox classCheckBox;
        JCheckBox subclassCheckBox;
        JCheckBox physicalPropsTreeCheckBox;
        JCheckBox experimentalPropsSubTreeCheckBox;
        JCheckBox meltingPointCheckBox;
        JCheckBox boilingPointCheckBox;
        JCheckBox solubilityCheckBox;
        JCheckBox predictedPropsSubTreeCheckBox;
        JCheckBox waterSolubilityCheckBox;
        JCheckBox logPCheckBox;
        JCheckBox biologicalPropsTreeCheckBox;
        JCheckBox cellularLocationsCheckBox;
        JCheckBox biospecimenLocationsCheckBox;
        JCheckBox tissueLocationsCheckBox;
        JCheckBox normalConcentrationsCheckBox;

        FileDialog fileDialog = new FileDialog(mainFrame, "Select file");

        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        if (shouldFill) {
            //natural height, maximum width
            c.fill = GridBagConstraints.HORIZONTAL;
        }

        categoryInfoLabel = new JLabel();

        // Set up checkboxes
        categoryCheckboxPanel = new JPanel();
        categoryCheckboxPanel.setLayout(new GridBagLayout());

        recordInfoTreeCheckBox = new JCheckBox("Record Information");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        categoryCheckboxPanel.add(recordInfoTreeCheckBox, c);
//
//        hmdbIdCheckBox = new JCheckBox("HMDB Accession ID");
//        c.fill = GridBagConstraints.HORIZONTAL;
//        c.insets = new Insets(0,10,0,0);
//        c.gridx = 0;
//        c.gridy = 1;
//        categoryCheckboxPanel.add(hmdbIdCheckBox, c);

        secondaryAccessionNumbersCheckbox = new JCheckBox("Secondary Accession Numbers");
        c.insets = new Insets(0,10,0,0);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        categoryCheckboxPanel.add(secondaryAccessionNumbersCheckbox, c);

        metaboliteIdenTreeCheckbox = new JCheckBox("Metabolite Identification");
        c.insets = new Insets(0,0,0,0);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 3;
        categoryCheckboxPanel.add(metaboliteIdenTreeCheckbox, c);

        commonNameCheckBox = new JCheckBox("Common Name");
        c.insets = new Insets(0,10,0,0);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 5;
        categoryCheckboxPanel.add(commonNameCheckBox, c);

        structureCheckBox = new JCheckBox("Structure");
        c.insets = new Insets(0,10,0,0);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 6;
        categoryCheckboxPanel.add(structureCheckBox, c);

        chemicalFormulaCheckBox = new JCheckBox("Chemical Formula");
        c.insets = new Insets(0,10,0,0);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 7;
        categoryCheckboxPanel.add(chemicalFormulaCheckBox, c);

        monoMolecularWeightCheckBox = new JCheckBox("Monoisotopic Molecular Weight");
        c.insets = new Insets(0,10,0,0);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 8;
        categoryCheckboxPanel.add(monoMolecularWeightCheckBox, c);

        casRegNumberCheckBox = new JCheckBox("CAS Registry Number");
        c.insets = new Insets(0,10,0,0);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 9;
        categoryCheckboxPanel.add(casRegNumberCheckBox, c);

        smilesCheckBox = new JCheckBox("SMILES");
        c.insets = new Insets(0,10,0,0);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 10;
        categoryCheckboxPanel.add(smilesCheckBox, c);

        inChlKeyCheckBox = new JCheckBox("inChl Key");
        c.insets = new Insets(0,10,0,0);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 11;
        categoryCheckboxPanel.add(inChlKeyCheckBox, c);

        chemicalTaxonomyTreeCheckBox = new JCheckBox("Chemical Taxonomy");
        c.insets = new Insets(0,0,0,0);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 12;
        categoryCheckboxPanel.add(chemicalTaxonomyTreeCheckBox, c);

        kingdomCheckBox = new JCheckBox("Kingdom");
        c.insets = new Insets(0,10,0,0);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 13;
        categoryCheckboxPanel.add(kingdomCheckBox, c);

        superClassCheckBox = new JCheckBox("Super Class");
        c.insets = new Insets(0,10,0,0);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 14;
        categoryCheckboxPanel.add(superClassCheckBox, c);

        classCheckBox = new JCheckBox("Class");
        c.insets = new Insets(0,10,0,0);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 15;
        categoryCheckboxPanel.add(classCheckBox, c);

        subclassCheckBox = new JCheckBox("Sub Class");
        c.insets = new Insets(0,10,0,0);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 16;
        categoryCheckboxPanel.add(subclassCheckBox, c);

        physicalPropsTreeCheckBox = new JCheckBox("Physical Properties");
        c.insets = new Insets(0,0,0,0);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 17;
        categoryCheckboxPanel.add(physicalPropsTreeCheckBox, c);

        experimentalPropsSubTreeCheckBox = new JCheckBox("Experimental Properties");
        c.insets = new Insets(0,10,0,0);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 18;
        categoryCheckboxPanel.add(experimentalPropsSubTreeCheckBox, c);

        meltingPointCheckBox = new JCheckBox("Melting Point");
        c.insets = new Insets(0,20,0,0);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 19;
        categoryCheckboxPanel.add(meltingPointCheckBox, c);

        boilingPointCheckBox = new JCheckBox("Boiling Point");
        c.insets = new Insets(0,20,0,0);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 20;
        categoryCheckboxPanel.add(boilingPointCheckBox, c);

        solubilityCheckBox = new JCheckBox("Solubility");
        c.insets = new Insets(0,20,0,0);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 21;
        categoryCheckboxPanel.add(solubilityCheckBox, c);

        predictedPropsSubTreeCheckBox = new JCheckBox("Predicted Properties");
        c.insets = new Insets(0,10,0,0);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 22;
        categoryCheckboxPanel.add(predictedPropsSubTreeCheckBox, c);

        waterSolubilityCheckBox = new JCheckBox("Water Solubility");
        c.insets = new Insets(0,20,0,0);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 23;
        categoryCheckboxPanel.add(waterSolubilityCheckBox, c);

        logPCheckBox = new JCheckBox("Log P");
        c.insets = new Insets(0,20,0,0);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 24;
        categoryCheckboxPanel.add(logPCheckBox, c);

        biologicalPropsTreeCheckBox = new JCheckBox("Biological Properties");
        c.insets = new Insets(0,0,0,0);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 25;
        categoryCheckboxPanel.add(biologicalPropsTreeCheckBox, c);

        cellularLocationsCheckBox = new JCheckBox("Cellular Locations");
        c.insets = new Insets(0,10,0,0);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 26;
        categoryCheckboxPanel.add(cellularLocationsCheckBox, c);

        biospecimenLocationsCheckBox = new JCheckBox("Biospecimen Locations");
        c.insets = new Insets(0,10,0,0);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 27;
        categoryCheckboxPanel.add(biospecimenLocationsCheckBox, c);

        tissueLocationsCheckBox = new JCheckBox("Tissue Locations");
        c.insets = new Insets(0,10,0,0);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 28;
        categoryCheckboxPanel.add(tissueLocationsCheckBox, c);

        normalConcentrationsCheckBox = new JCheckBox("Normal Concentrations");
        c.insets = new Insets(0,10,0,0);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 29;
        categoryCheckboxPanel.add(normalConcentrationsCheckBox, c);

//        hmdbIdCheckBox.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (hmdbIdCheckBox.isSelected() && !categories.contains("accession")) {
//                    categories.add("accession");
//                } else {
//                    categories.remove("accession");
//                }
//
//            }
//        });

        secondaryAccessionNumbersCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (secondaryAccessionNumbersCheckbox.isSelected() && !categories.contains("secondaryAccessionNumbers")) {
                    categories.add("secondary_accessions");
                } else {
                    categories.remove("secondary_accessions");
                }
            }
        });

        commonNameCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (commonNameCheckBox.isSelected() && !categories.contains("name")) {
                    categories.add("name");
                } else {
                    categories.remove("name");
                }
            }
        });

        structureCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (structureCheckBox.isSelected() && !categories.contains("structure")) {
                    categories.add("structure");
                } else {
                    categories.remove("structure");
                }
            }
        });

        chemicalFormulaCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (chemicalFormulaCheckBox.isSelected() && !categories.contains("chemical_formula")) {
                    categories.add("chemical_formula");
                } else {
                    categories.remove("chemical_formula");
                }
            }
        });

        monoMolecularWeightCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (monoMolecularWeightCheckBox.isSelected() && !categories.contains("monisotopic_molecular_weight")) {
                    categories.add("monisotopic_molecular_weight");
                } else {
                    categories.remove("monisotopic_molecular_weight");
                }
            }
        });

        casRegNumberCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (casRegNumberCheckBox.isSelected() && !categories.contains("cas_registry_number")) {
                    categories.add("cas_registry_number");
                } else {
                    categories.remove("cas_registry_number");
                }
            }
        });

        smilesCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (smilesCheckBox.isSelected() && !categories.contains("smiles")) {
                    categories.add("smiles");
                } else {
                    categories.remove("smiles");
                }
            }
        });

        inChlKeyCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (inChlKeyCheckBox.isSelected() && !categories.contains("inchikey")) {
                    categories.add("inchikey");
                } else {
                    categories.remove("inchikey");
                }
            }
        });

        kingdomCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (kingdomCheckBox.isSelected() && !categories.contains("kingdom")) {
                    categories.add("kingdom");
                } else {
                    categories.remove("kingdom");
                }
            }
        });

        superClassCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (superClassCheckBox.isSelected() && !categories.contains("super_class")) {
                    categories.add("super_class");
                } else {
                    categories.remove("super_class");
                }
            }
        });

        classCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (classCheckBox.isSelected() && !categories.contains("class")) {
                    categories.add("class");
                } else {
                    categories.remove("class");
                }
            }
        });

        subclassCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (subclassCheckBox.isSelected() && !categories.contains("sub_class")) {
                    categories.add("sub_class");
                } else {
                    categories.remove("sub_class");
                }
            }
        });

        meltingPointCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (meltingPointCheckBox.isSelected() && !categories.contains("melting_point")) {
                    categories.add("melting_point");
                } else {
                    categories.remove("melting_point");
                }
            }
        });

        boilingPointCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (boilingPointCheckBox.isSelected() && !categories.contains("boiling_point")) {
                    categories.add("boiling_point");
                } else {
                    categories.remove("boiling_point");
                }
            }
        });

        solubilityCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (solubilityCheckBox.isSelected() && !categories.contains("solubility")) {
                    categories.add("solubility");
                } else {
                    categories.remove("solubility");
                }
            }
        });

        waterSolubilityCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (waterSolubilityCheckBox.isSelected() && !categories.contains("water_solubility")) {
                    categories.add("water_solubility");
                } else {
                    categories.remove("water_solubility");
                }
            }
        });

        logPCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (logPCheckBox.isSelected() && !categories.contains("logp")) {
                    categories.add("logp");
                } else {
                    categories.remove("logp");
                }
            }
        });

        cellularLocationsCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cellularLocationsCheckBox.isSelected() && !categories.contains("cellular_locations")) {
                    categories.add("cellular_locations");
                } else {
                    categories.remove("cellular_locations");
                }
            }
        });

        biospecimenLocationsCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (biospecimenLocationsCheckBox.isSelected() && !categories.contains("biospecimen_locations")) {
                    categories.add("biospecimen_locations");
                } else {
                    categories.remove("biospecimen_locations");
                }
            }
        });

        tissueLocationsCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tissueLocationsCheckBox.isSelected() && !categories.contains("tissue_locations")) {
                    categories.add("tissue_locations");
                } else {
                    categories.remove("tissue_locations");
                }
            }
        });

        normalConcentrationsCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (normalConcentrationsCheckBox.isSelected() && !categories.contains("normal_concentrations")) {
                    categories.add("normal_concentrations");
                } else {
                    categories.remove("normal_concentrations");
                }
            }
        });

        // Select all checkbox listeners
        recordInfoTreeCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (recordInfoTreeCheckBox.isSelected()) {
//                    hmdbIdCheckBox.setSelected(true);
                    secondaryAccessionNumbersCheckbox.setSelected(true);
                    categories.add("secondary_accessions");
                } else {
//                    hmdbIdCheckBox.setSelected(false);
                    secondaryAccessionNumbersCheckbox.setSelected(false);
                    categories.remove("secondary_accessions");
                }

            }
        });

        metaboliteIdenTreeCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (metaboliteIdenTreeCheckbox.isSelected()) {
                    commonNameCheckBox.setSelected(true);
                    structureCheckBox.setSelected(true);
                    chemicalFormulaCheckBox.setSelected(true);
                    monoMolecularWeightCheckBox.setSelected(true);
                    casRegNumberCheckBox.setSelected(true);
                    smilesCheckBox.setSelected(true);
                    inChlKeyCheckBox.setSelected(true);
                    categories.add("name");
                    categories.add("structure");
                    categories.add("chemical_formula");
                    categories.add("monisotopic_molecular_weight");
                    categories.add("cas_registry_number");
                    categories.add("smiles");
                    categories.add("inchikey");
                } else {
                    commonNameCheckBox.setSelected(false);
                    structureCheckBox.setSelected(false);
                    chemicalFormulaCheckBox.setSelected(false);
                    monoMolecularWeightCheckBox.setSelected(false);
                    casRegNumberCheckBox.setSelected(false);
                    smilesCheckBox.setSelected(false);
                    inChlKeyCheckBox.setSelected(false);
                    categories.remove("name");
                    categories.remove("structure");
                    categories.remove("chemical_formula");
                    categories.remove("monisotopic_molecular_weight");
                    categories.remove("cas_registry_number");
                    categories.remove("smiles");
                    categories.remove("inchikey");
                }

            }
        });

        chemicalTaxonomyTreeCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (chemicalTaxonomyTreeCheckBox.isSelected()) {
                    kingdomCheckBox.setSelected(true);
                    superClassCheckBox.setSelected(true);
                    classCheckBox.setSelected(true);
                    subclassCheckBox.setSelected(true);
                    categories.add("kingdom");
                    categories.add("super_class");
                    categories.add("class");
                    categories.add("sub_class");
                } else {
                    kingdomCheckBox.setSelected(false);
                    superClassCheckBox.setSelected(false);
                    classCheckBox.setSelected(false);
                    subclassCheckBox.setSelected(false);
                    categories.remove("kingdom");
                    categories.remove("super_class");
                    categories.remove("class");
                    categories.remove("sub_class");
                }
            }
        });

        physicalPropsTreeCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (physicalPropsTreeCheckBox.isSelected()) {
                    experimentalPropsSubTreeCheckBox.setSelected(true);
                    predictedPropsSubTreeCheckBox.setSelected(true);
                    meltingPointCheckBox.setSelected(true);
                    boilingPointCheckBox.setSelected(true);
                    solubilityCheckBox.setSelected(true);
                    categories.add("melting_point");
                    categories.add("boiling_point");
                    categories.add("solubility");
                    categories.add("water_solubility");
                    categories.add("logp");
                } else {
                    experimentalPropsSubTreeCheckBox.setSelected(false);
                    predictedPropsSubTreeCheckBox.setSelected(false);
                    meltingPointCheckBox.setSelected(false);
                    boilingPointCheckBox.setSelected(false);
                    solubilityCheckBox.setSelected(false);
                    categories.remove("melting_point");
                    categories.remove("boiling_point");
                    categories.remove("solubility");
                    categories.remove("water_solubility");
                    categories.remove("logp");
                }
            }
        });

        experimentalPropsSubTreeCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (experimentalPropsSubTreeCheckBox.isSelected()) {
                    meltingPointCheckBox.setSelected(true);
                    boilingPointCheckBox.setSelected(true);
                    solubilityCheckBox.setSelected(true);
                    categories.add("melting_point");
                    categories.add("boiling_point");
                    categories.add("solubility");
                } else {
                    meltingPointCheckBox.setSelected(false);
                    boilingPointCheckBox.setSelected(false);
                    solubilityCheckBox.setSelected(false);
                    categories.remove("melting_point");
                    categories.remove("boiling_point");
                    categories.remove("solubility");
                }
            }
        });

        predictedPropsSubTreeCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (predictedPropsSubTreeCheckBox.isSelected()) {
                    waterSolubilityCheckBox.setSelected(true);
                    logPCheckBox.setSelected(true);
                    categories.add("water_solubility");
                    categories.add("logp");
                } else {
                    waterSolubilityCheckBox.setSelected(false);
                    logPCheckBox.setSelected(false);
                    categories.remove("water_solubility");
                    categories.remove("logp");
                }
            }
        });

        biologicalPropsTreeCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (biologicalPropsTreeCheckBox.isSelected()) {
                    cellularLocationsCheckBox.setSelected(true);
                    biospecimenLocationsCheckBox.setSelected(true);
                    tissueLocationsCheckBox.setSelected(true);
                    normalConcentrationsCheckBox.setSelected(true);
                    categories.add("cellular_locations");
                    categories.add("biospecimen_locations");
                    categories.add("tissue_locations");
                    categories.add("normal_concentrations");
                } else {
                    cellularLocationsCheckBox.setSelected(false);
                    biospecimenLocationsCheckBox.setSelected(false);
                    tissueLocationsCheckBox.setSelected(false);
                    normalConcentrationsCheckBox.setSelected(false);
                    categories.remove("cellular_locations");
                    categories.remove("biospecimen_locations");
                    categories.remove("tissue_locations");
                    categories.remove("normal_concentrations");
                }
            }
        });

        // Set up scroll pane
        categoryScrollPane = new JScrollPane(categoryInfoLabel);
        categoryScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        categoryScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        categoryScrollPane.setViewportBorder(new LineBorder(Color.RED));
        pane.add(categoryScrollPane, c);

        rangeInfoLabel = new JLabel("<html>Use comma or space separated values to specify which Metabolites to query.<br />Use semicolons to specify multiple ranges.<br />Examples:<br /> 1,50;200,220;500,700 <br /> HMDB0000001 HMDB0000002 HMDB0000003</html>");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 0;      //height
        c.weightx = 0.0;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 0;
        pane.add(rangeInfoLabel, c);

        rangeTextField = new JTextField(20);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 1;
        pane.add(rangeTextField, c);

        categoryScrollPane.setViewportView(categoryCheckboxPanel);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        categoryScrollPane.setPreferredSize(new Dimension(200,200));

        openFileDialogButton = new JButton("Select 'hmdb_metabolites' File");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 0;       //reset to default
        c.weighty = 1.0;   //request any extra vertical space
        c.insets = new Insets(10,0,0,0);  //top padding
        c.gridy = 3;       //third row
        c.gridx = 0;
        pane.add(openFileDialogButton, c);

        generateButton = new JButton("Generate Metabolites");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 0;       //reset to default
        c.weighty = 1.0;   //request any extra vertical space
        c.insets = new Insets(10,0,0,0);  //top padding
        c.gridwidth = 1;   //2 columns wide
        c.gridy = 4;       //third row
        c.gridx = 0;
        pane.add(generateButton, c);



        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                range = rangeTextField.getText();
                Properties query = queryHandler.main(range, categories, queryType);
                parser.main(filename, query);
            }
        });

        openFileDialogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileDialog.setVisible(true);
                if (fileDialog.getFile() != null) {
                    filename = fileDialog.getDirectory() + fileDialog.getFile();
//                    statusLbl.setText("File selected: " + fileDialog.getDirectory() + fileDialog.getFile());
                } else {
                    filename = "hmdb_metabolites.xml";
//                    statusLbl.setText("No file selected, default is hmdb_metabolites.xml in current directory.");
                }
            }
        });

        rangeTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String range = rangeField.getText();
                rangeField.setText(range += "\n");
                rangeField.selectAll();
            }
        });
    }

//    public void setStatusLbl(String message) {
//        System.out.println("doing it");
////        errorLabel.setText(message);
//    }

}
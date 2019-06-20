import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ParseHandler extends DefaultHandler {
    String bookXmlFileName;
    String tmpValue;
    String accessions = "";
    String kind = "";
    String cellularLocations = "";
    String bioLocations = "";
    String tissueLocations = "";
    String solubility = "";
    String logp = "";
    String normalConcentrations = "";

    List<String> categories;

    List<Integer> lowerLimits = new ArrayList<>();
    List<Integer> upperLimits = new ArrayList<>();

    Metabolite metabolite = new Metabolite();
    ExcelWriter writer = ExcelWriter.getSharedApplication();

    int previousMetaboliteNumber = 0;

    boolean singleInputQuery;
    boolean getEverything = false;
    boolean errors;
    boolean bMetabolite = false;
    boolean bAccession = false;
    boolean bName = false;
    boolean bFormula = false;
    boolean bMonoisotopicWeight = false;
    boolean bSecondaryAccessionTree = false;
    boolean bCASNumber = false;
    boolean bSmiles = false;
    boolean bInChiKey = false;
    boolean bKingdom = false;
    boolean bSuperClass = false;
    boolean bClass = false;
    boolean bSubClass = false;
    boolean bKind = false;
    boolean bValue = false;
    boolean bCellular = false;
    boolean bBiospecimen = false;
    boolean bTissue = false;
    boolean bSource = false;
    boolean bNormalConcentrations = false;

    public ParseHandler() {}

    // Constructor for Parsing the XML document
    // Calls for the ExcelWriter to generate the file to be written to
    // Parses the XML document
    // Finishes up the Excel doc writing
    protected void kickOff(String bookXmlFileName, String range, List<String> cats) {
        categories = cats;
        errors = false;
        writeRanges(range);
        this.bookXmlFileName = bookXmlFileName;
        if (!errors) {
            writer.generateFile();
            parseDocument(); // Parses XML document and writes to the Excel file one line at a time
            try {
                writer.finish(); // Writes out file
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected void writeRanges(String ranges) {
        // Handle empty input (which means that the user wants everything)
        if (ranges.isEmpty()) {
            getEverything = true;
        } else {
            // Split multiple ranges by the semicolons and make into an array (also just makes a single-input query into an array)
            String[] splitRanges = ranges.split(";");
            for (int i = 0; i < splitRanges.length; i++) {
                splitRanges[i].trim();

            }
            getEverything = false;
            assignRanges(splitRanges);
        }
    }

    public void assignRanges(String[] ranges) {
        // Boolean set up for if only a single input query
        boolean singleInputQuery = ranges.length == 1 && ranges[0].split(",").length == 1;

        if (singleInputQuery) {
            if (Integer.parseInt(ranges[0]) >= 0) {
                lowerLimits.add(Integer.parseInt(ranges[0]));
                upperLimits.add(Integer.parseInt(ranges[0]));
            } else if (Integer.parseInt(ranges[0]) < 0 && singleInputQuery) {
                errors = true;
                return;
            }
        }
        // Parse by the commas and add the upper and lower limits to their lists
        else {
            for (int i = 0; i < ranges.length; i++) {
                String[] splitRanges = ranges[i].split(",");
                // Check for negative input
                if (Integer.parseInt(splitRanges[0]) < 0 || Integer.parseInt(splitRanges[1]) < 0) {
                    errors = true;
                    return;
                } else {
                    lowerLimits.add(Integer.parseInt(splitRanges[0]));
                    upperLimits.add(Integer.parseInt(splitRanges[1]));
                }
            }
        }
    }

    // Parses document through the SAXParser. Overrides startElement(), endElement(), and characters().
    public void parseDocument() {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();
            parser.parse(bookXmlFileName, this);
        } catch (ParserConfigurationException e) {
//            g.setStatusLbl("Parse Config Error");
            e.printStackTrace();
        } catch (SAXException e) {
//            g.setStatusLbl("XML Not Well Formed");
            e.printStackTrace();
        } catch (IOException e) {
//            g.setStatusLbl("IO Error");
            e.printStackTrace();
        }
    }

    @Override
    public void startElement(String s, String s1, String element, Attributes attributes) {
        if (element.equalsIgnoreCase("metabolite")) {
            bMetabolite = true;
            bAccession = false;
            bName = false;
            bFormula = false;
            bMonoisotopicWeight = false;
            bSecondaryAccessionTree = false;
            bCASNumber = false;
            bSubClass = false;
            bSuperClass = false;
            bClass = false;
            bKind = false;
            bValue = false;
            bCellular = false;
            bBiospecimen = false;
            bTissue = false;
            bSource = false;
            bNormalConcentrations = false;
            accessions = "";
            cellularLocations = "";
            tissueLocations = "";
            bioLocations = "";
            solubility = "";
            logp = "";
            normalConcentrations = "";
        } else if (element.equalsIgnoreCase("secondary_accessions") && (categories.isEmpty() || categories.contains("secondary_accessions"))) {
            bSecondaryAccessionTree = true;
        } else if (element.equalsIgnoreCase("value")) {
            bValue = true;
        } else if (element.equalsIgnoreCase("kind")) {
            bKind = true;
        } else if (element.equalsIgnoreCase("normal_concentrations")) {
            bNormalConcentrations = true;
        }
    }

    @Override
    public void endElement(String s, String s1, String element) {

        if (bMetabolite) {
            if (element.equals("accession") && !bAccession) {
                int currentAccessionNumber = Integer.parseInt(tmpValue.substring(4)); // cut "HMDB" off front of accession number
                // if user wants everything
                if (getEverything) {
                    previousMetaboliteNumber = 0; // sets starting point for counter
                    if (currentAccessionNumber >= previousMetaboliteNumber) {
                        bAccession = true;
                        metabolite.setAccession(tmpValue);
                        previousMetaboliteNumber++; // update range counter
                    }
                } else {
                    previousMetaboliteNumber = lowerLimits.get(0); // sets starting point for counter
                    // Check if in range
                    for (int i = 0; i < lowerLimits.size(); i++) {
                        if (currentAccessionNumber >= lowerLimits.get(i) && currentAccessionNumber <= upperLimits.get(i)) {
                            // Tracks current position in sheet so as to not go over range (query would have erroneous data points; ex: when maximum was 400, would return specifically 427)
                            // TODO: Figure out why this is happening instead of just covering it up
                            if (currentAccessionNumber >= previousMetaboliteNumber) {
                                bAccession = true;
                                metabolite.setAccession(tmpValue);
                                previousMetaboliteNumber++; // update range counter
                            }
                        }
                    }
                }
            } else if (element.equalsIgnoreCase("accession") && bSecondaryAccessionTree) {
                accessions = accessions + tmpValue + ",\n";
            } else if (element.equalsIgnoreCase("name") && !bName && (categories.isEmpty() || categories.contains("name"))) {
                metabolite.setName(tmpValue);
                bName = true;
            } else if (element.equalsIgnoreCase("chemical_formula") && !bFormula && (categories.isEmpty() || categories.contains("chemical_formula"))) {
                metabolite.setFormula(tmpValue);
                bFormula = true;
            } else if (element.equalsIgnoreCase("monisotopic_molecular_weight") && !bMonoisotopicWeight && (categories.isEmpty() || categories.contains("monisotopic_molecular_weight"))) {
                metabolite.setMonoisotopicMass(tmpValue);
            } else if (element.equalsIgnoreCase("cas_registry_number") && !bCASNumber && (categories.isEmpty() || categories.contains("cas_registry_number"))) {
                metabolite.setCasNumber(tmpValue);
            } else if (element.equalsIgnoreCase("smiles") && !bSmiles && (categories.isEmpty() || categories.contains("smiles"))) {
                metabolite.setSmiles(tmpValue);
            } else if (element.equalsIgnoreCase("inchikey") && !bInChiKey && (categories.isEmpty() || categories.contains("inchikey"))) {
                metabolite.setInchikey(tmpValue);
            } else if (element.equalsIgnoreCase("kingdom") && !bKingdom && (categories.isEmpty() || categories.contains("kingdom"))) {
                metabolite.setKingdom(tmpValue);
            } else if (element.equalsIgnoreCase("super_class") && !bSuperClass && (categories.isEmpty() || categories.contains("super_class"))) {
                metabolite.setMetaboliteSuperClass(tmpValue);
            } else if (element.equalsIgnoreCase("class") && !bClass && (categories.isEmpty() || categories.contains("class"))) {
                metabolite.setMetaboliteClass(tmpValue);
            } else if (element.equalsIgnoreCase("sub_class") && !bSubClass && (categories.isEmpty() || categories.contains("sub_class"))) {
                metabolite.setSubClass(tmpValue);
            } else if (element.equalsIgnoreCase("kind") && bKind) {
                kind = tmpValue;
                bKind = false;
            } else if (element.equalsIgnoreCase("value") && bValue && !kind.isEmpty()) {
                if (kind.equalsIgnoreCase("melting_point") && (categories.isEmpty() || categories.contains("melting_point"))) {
                    metabolite.setMeltingPoint(tmpValue);
                } else if (kind.equalsIgnoreCase("boiling_point") && (categories.isEmpty() || categories.contains("boiling_point"))) {
                    metabolite.setBoilingPoint(tmpValue);
                } else if (kind.equalsIgnoreCase("water_solubility") && (categories.isEmpty() || categories.contains("water_solubility"))) {
                    metabolite.setWaterSolubility(tmpValue);
                } else if (kind.equalsIgnoreCase("solubility") && (categories.isEmpty() || categories.contains("solubility"))) {
                    solubility = solubility + tmpValue + ",\n";
                } else if (kind.equals("logp") && (categories.isEmpty() || categories.contains("logp"))) {
                    metabolite.setLogp(tmpValue);
                }
                bValue = false;
            } else if (element.equalsIgnoreCase("source") && !bSource && !kind.isEmpty()) {
                if (kind.equalsIgnoreCase("solubility") && (categories.isEmpty() || categories.contains("solubility"))) {
                    solubility = solubility + tmpValue + ",\n";
                } else if (kind.equals("logp") && (categories.isEmpty() || categories.contains("logp"))) {
                    logp = logp + tmpValue + ",\n";
                }
                bValue = false;
            } else if (element.equalsIgnoreCase("cellular") && !bCellular && (categories.isEmpty() || categories.contains("cellular_locations"))) {
                cellularLocations = cellularLocations + tmpValue + ",\n";
            } else if (element.equalsIgnoreCase("biospecimen") && !bBiospecimen && (categories.isEmpty() || categories.contains("biospecimen_locations"))) {
                if (!bioLocations.contains(tmpValue)) {
                    bioLocations = bioLocations + tmpValue + ",\n";
                }
            } else if (element.equalsIgnoreCase("tissue") && !bTissue && (categories.isEmpty() || categories.contains("tissue_locations"))) {
                tissueLocations = tissueLocations + tmpValue + ",\n";
            } else if (element.equalsIgnoreCase("normal_concentrations") && bNormalConcentrations && (categories.isEmpty() || categories.contains("normal_concentrations"))) {
                bNormalConcentrations = false;
            } else {
                if (bNormalConcentrations && ((tmpValue.trim()).length() > 0)) {
                    normalConcentrations = normalConcentrations + element + ": " + tmpValue + ",\n";
                }
            }

            // bAccession must be TRUE for now so that the counter works
            // TODO: find a way for the counter to still work but for the user to uncheck "Accessions" (why tf would you not want those tho)
            if (element.equalsIgnoreCase("metabolite") && bAccession) {
                metabolite.setSecondaryAccessions(accessions);
                metabolite.setCellularLocations(cellularLocations);
                metabolite.setBiospecimenLocations(bioLocations);
                metabolite.setTissueLocations(tissueLocations);
                metabolite.setLogp(logp);
                metabolite.setSolubility(solubility);
                metabolite.setNormalConcentrations(normalConcentrations);
                writer.write(metabolite);
                metabolite.setNull(); // Forces the Metabolite object to be set null for GC
                tmpValue = null;
                bMetabolite = false;
            }
        }
    }
    @Override
    public void characters(char[] ac, int i, int j) {
        tmpValue = new String (ac, i, j);
    }
}
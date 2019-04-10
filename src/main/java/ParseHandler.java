import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

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

    Properties query = new Properties();

    Metabolite metabolite = Metabolite.getSharedApplication();
    ExcelWriter writer = ExcelWriter.getSharedApplication();

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
    boolean writeCharacters = false;

    public ParseHandler() {}

    // Constructor for Parsing the XML document
    // Calls for the ExcelWriter to generate the file to be written to
    // Parses the XML document
    // Finishes up the Excel doc writing
    protected void main(String bookXmlFileName, Properties queryInput) {
        query = queryInput;
        this.bookXmlFileName = bookXmlFileName;
        writer.generateFile(); // generates initial XLSX document to be written on
        parseDocument(); // Parses XML document and writes to the Excel file one line at a time
        try {
            writer.finish(); // Writes out file
        } catch (IOException e) {
            e.printStackTrace();
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
        } else if (element.equalsIgnoreCase("accession")) {
            writeCharacters = true;
        } else if (element.equalsIgnoreCase("secondary_accessions")) {
            bSecondaryAccessionTree = true;
        } else if (element.equalsIgnoreCase("value")) {
            writeCharacters = true;
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
                bAccession = true;
                metabolite.set_hmdb_id(tmpValue);
            } else if (element.equalsIgnoreCase("accession") && bSecondaryAccessionTree) {
                accessions = accessions + tmpValue + ",\n";
            } else if (element.equalsIgnoreCase("name") && !bName) {
                metabolite.set_name(tmpValue);
                bName = true;
            } else if (element.equalsIgnoreCase("chemical_formula") && !bFormula) {
                metabolite.set_formula(tmpValue);
                bFormula = true;
            } else if (element.equalsIgnoreCase("monisotopic_molecular_weight") && !bMonoisotopicWeight) {
                metabolite.set_monoisotopic_mass(tmpValue);
            } else if (element.equalsIgnoreCase("cas_registry_number") && !bCASNumber) {
                metabolite.set_cas_registry_number(tmpValue);
            } else if (element.equalsIgnoreCase("smiles") && !bSmiles) {
                metabolite.set_smiles(tmpValue);
            } else if (element.equalsIgnoreCase("inchikey") && !bInChiKey) {
                metabolite.set_inchikey(tmpValue);
            } else if (element.equalsIgnoreCase("kingdom") && !bKingdom) {
                metabolite.set_kingdom(tmpValue);
            } else if (element.equalsIgnoreCase("super_class") && !bSuperClass) {
                metabolite.set_super_class(tmpValue);
            } else if (element.equalsIgnoreCase("class") && !bClass) {
                metabolite.set_class(tmpValue);
            } else if (element.equalsIgnoreCase("sub_class") && !bSubClass) {
                metabolite.set_sub_class(tmpValue);
            } else if (element.equalsIgnoreCase("kind") && bKind) {
                kind = tmpValue;
                bKind = false;
            } else if (element.equalsIgnoreCase("value") && bValue && !kind.isEmpty()) {
                if (kind.equalsIgnoreCase("melting_point")) {
                    metabolite.set_melting_point(tmpValue);
                } else if (kind.equalsIgnoreCase("boiling_point")) {
                    metabolite.set_boiling_point(tmpValue);
                } else if (kind.equalsIgnoreCase("water_solubility")) {
                    metabolite.set_water_solubility(tmpValue);
                } else if (kind.equalsIgnoreCase("solubility")) {
                    solubility = solubility + tmpValue + ",\n";
                } else if (kind.equals("logp")) {
                    metabolite.set_logp(tmpValue);
                }
                bValue = false;
            } else if (element.equalsIgnoreCase("source") && !bSource && !kind.isEmpty()) {
                if (kind.equalsIgnoreCase("solubility")) {
                    solubility = solubility + tmpValue + ",\n";
                } else if (kind.equals("logp")) {
                    logp = logp + tmpValue + ",\n";
                }
                bValue = false;
            } else if (element.equalsIgnoreCase("cellular") && !bCellular) {
                cellularLocations = cellularLocations + tmpValue + ",\n";
            } else if (element.equalsIgnoreCase("biospecimen") && !bBiospecimen) {
                if (!bioLocations.contains(tmpValue)) {
                    bioLocations = bioLocations + tmpValue + ",\n";
                }
            } else if (element.equalsIgnoreCase("tissue") && !bTissue) {
                tissueLocations = tissueLocations + tmpValue + ",\n";
            } else if (element.equalsIgnoreCase("normal_concentrations") && bNormalConcentrations) {
                bNormalConcentrations = false;
            } else {
                if (bNormalConcentrations && ((tmpValue.trim()).length() > 0)) {
                    normalConcentrations = normalConcentrations + element + ": " + tmpValue + ",\n";
                }
            }

            // bAccession must be TRUE for now so that the counter works
            // TODO: find a way for the counter to still work but for the user to uncheck "Accessions" (why tf would you not want those tho)
            if (element.equalsIgnoreCase("metabolite") && bAccession) {
                metabolite.set_secondary_accessions(accessions);
                metabolite.set_cellular_locations(cellularLocations);
                metabolite.set_biospecimen_locations(bioLocations);
                metabolite.set_tissue_locations(tissueLocations);
                metabolite.set_logp(logp);
                metabolite.set_solubility(solubility);
                metabolite.set_normal_concentrations(normalConcentrations);
                if (checkMetaboliteValidity()) {
                    writer.write(metabolite);
                }
                metabolite.setNull(); // Forces the Metabolite object to be set null for GC
                tmpValue = null;
                bMetabolite = false;
            }
        }
    }

    @Override
    public void characters(char[] ac, int i, int j) {
        tmpValue = new String(ac, i, j);
    }

    private boolean checkMetaboliteValidity() {
        String queryType = query.getProperty("queryType");
        String queryContent = query.getProperty("queryContent");

        boolean validity = false;
        Object value = new Object();

        // Use reflection to build method name for accessing proper Metabolite attribute
        try {
            // Build method name by metabolite accessor
            Method metaboliteMethod = Metabolite.class.getDeclaredMethod("get_" + queryType);
            try {
                value = metaboliteMethod.invoke(Metabolite.getSharedApplication());
            } catch (IllegalAccessException e) {
                throw new IllegalArgumentException();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            String metaboliteReturn = (String) value;

            // List of query types to check against
            List<String> queryTypeChecks = Arrays.asList("hmdb_id", "common_name");

            for (int i = 0; i < queryTypeChecks.size(); i++) {
                if (queryType.equalsIgnoreCase(queryTypeChecks.get(i)) && queryContent.equals(metaboliteReturn)) {
                    validity = true;
                } else {
                    validity = false;
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return validity;
    }
}
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
    boolean bMetabolite = false;
    boolean bAccession = false;
    boolean bName = false;
    boolean bFormula = false;
    boolean bMonoisotopicWeight = false;
    List<Integer> lowerLimits = new ArrayList<>();
    List<Integer> upperLimits = new ArrayList<>();
    Metabolite metabolite = new Metabolite();
    ExcelWriter writer = new ExcelWriter();
    int previousMetaboliteNumber = 1;
    GUI g = GUI.getSharedApplication();
    boolean errors;

    // Constructor for Parsing the XML document
    // Calls for the ExcelWriter to generate the file to be written to
    // Parses the XML document
    // Finishes up the Excel doc writing
    public ParseHandler(String bookXmlFileName, String range) {
        errors = false;
        writeRanges(range);
        this.bookXmlFileName = bookXmlFileName;
        if (!errors) {
            writer.generateFile(); // Generates the base .xlsx file to be written on
            parseDocument(); // Parses XML document and writes to the Excel file one line at a time
            try {
                writer.finish(); // Writes out file
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected void writeRanges(String ranges) {
        List<String> totalRanges = new ArrayList<>();
        String[] splitRanges = ranges.split(";");
        for (String string : splitRanges) {
            string = string.trim();
            totalRanges.add(string);
        }
        assignRanges(totalRanges, ",");
    }

    public void assignRanges(List<String> input, String delimiter) {
        String[] ranges;
        for (int i = 0; i < input.size(); i++) {
            String str = input.get(i);
            ranges = str.split(delimiter);

            // Check for negative input
            if (Integer.parseInt(ranges[0]) < 0 || Integer.parseInt(ranges[1]) < 0) {
                errors = true;
                g.setStatusLbl("ERROR: Negative numbers not allowed!");
                return;
            } else {
                lowerLimits.add(Integer.parseInt(ranges[0]));
                upperLimits.add(Integer.parseInt(ranges[1]));
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
            g.setStatusLbl("Parse Config Error");
            e.printStackTrace();
        } catch (SAXException e) {
            g.setStatusLbl("XML Not Well Formed");
            e.printStackTrace();
        } catch (IOException e) {
            g.setStatusLbl("IO Error");
            e.printStackTrace();
        }
    }

    @Override
    public void startElement(String s, String s1, String elementName, Attributes attributes) {
        if (elementName.equalsIgnoreCase("metabolite")) {
            bMetabolite = true;
            bAccession = false;
            bName = false;
            bFormula = false;
            bMonoisotopicWeight = false;
        }
    }
    @Override
    public void endElement(String s, String s1, String element) {

        if (bMetabolite) {
            if (element.equals("accession") && !bAccession) {
                int currentAccessionNumber = Integer.parseInt(tmpValue.substring(4)); // cut "HMDB" off front of accession number
                // Check if in range
                for(int i = 0; i < lowerLimits.size(); i++) {
                    if (currentAccessionNumber >= lowerLimits.get(i) && currentAccessionNumber <= upperLimits.get(i)) {
                        // Tracks current position in sheet so as to not go over range (query would have erraneous data points; ex: when maximum was 400, would return specifically 427)
                        // TODO: Figure out why this is happening instead of just covering it up
                        if (currentAccessionNumber >= previousMetaboliteNumber) {
                            bAccession = true;
                            metabolite.setAccession(tmpValue);
                            previousMetaboliteNumber = currentAccessionNumber; // update range counter
                        }
                    }
                }
            } else if (element.equalsIgnoreCase("name") && !bName && bAccession) {
                metabolite.setName(tmpValue);
                bName = true;
            } else if (element.equalsIgnoreCase("chemical_formula") && !bFormula && bAccession) {
                metabolite.setFormula(tmpValue);
                bFormula = true;
            } else if (element.equalsIgnoreCase("monisotopic_molecular_weight") && !bMonoisotopicWeight && bAccession) {
                metabolite.setMonoisotopicMass(tmpValue);
            } else {
                if (metabolite.getName() != null
                    && metabolite.getFormula() != null
                    && metabolite.getAccession() != null
                    && metabolite.getMonoisotopicMass() != null)
                {
                    writer.write(metabolite);
                    metabolite.setNull(); // Forces the Metabolite object to be set null for GC
                }
                tmpValue = null;
            }
        }
    }
    @Override
    public void characters(char[] ac, int i, int j) {
        tmpValue = new String(ac, i ,j);
    }
}
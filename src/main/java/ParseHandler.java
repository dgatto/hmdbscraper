import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.poi.sl.draw.geom.Guide;
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
    Metabolite metabolite = new Metabolite();
    ExcelWriter writer = new ExcelWriter();
    int gcCounter = 1;
    Runtime r = Runtime.getRuntime();
    GUI g = new GUI();

    // Constructor for Parsing the XML document
    // Calls for the ExcelWriter to generate the file to be written to
    // Parses the XML document
    // Finishes up the Excel doc writing
    public ParseHandler(String bookXmlFileName) {
        this.bookXmlFileName = bookXmlFileName;
        writer.generateFile(); // Generates the base .xlsx file to be written on
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
            g.setStatusLbl("Parse Config Error");
            System.out.println("ParserConfig error");
        } catch (SAXException e) {
            g.setStatusLbl("XML Not Well Formed");
            System.out.println("SAXException : xml not well formed");
        } catch (IOException e) {
            g.setStatusLbl("IO Error");
            System.out.println("IO error");
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
                bAccession = true;
                metabolite.setAccession(tmpValue);
            } else if (element.equalsIgnoreCase("name") && !bName) {
                metabolite.setName(tmpValue);
                bName = true;
            } else if (element.equalsIgnoreCase("chemical_formula") && !bFormula) {
                metabolite.setFormula(tmpValue);
                bFormula = true;
            } else if (element.equalsIgnoreCase("monisotopic_molecular_weight") && !bMonoisotopicWeight) {
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

                // some piece of magic that keeps the GC Overhead Limit from freaking out
                if (gcCounter != 1 && gcCounter % 50 == 0) {
                    r.gc();
                    gcCounter++;
                }
            }
        }
    }
    @Override
    public void characters(char[] ac, int i, int j) {
        tmpValue = new String(ac, i ,j);
    }
}
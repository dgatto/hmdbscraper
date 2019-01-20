public class Metabolite {
    public String name, formula, monoisotopicMass, accession;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getAccession() {
        return accession;
    }

    public void setAccession(String accession) {
        this.accession = accession;
    }

    public String getMonoisotopicMass() {
        return monoisotopicMass;
    }

    public void setMonoisotopicMass(String monoisotopicMass) {
        this.monoisotopicMass = monoisotopicMass;
    }

    public Metabolite() {
    }

    public void setNull() {
        this.name = null;
        this.formula = null;
        this.monoisotopicMass = null;
        this.accession = null;
    }

    @Override
    public String toString() {
        return "Metabolite{" +
                "name='" + name + '\'' +
                ", formula='" + formula + '\'' +
                ", monoisotopicMass='" + monoisotopicMass + '\'' +
                ", accession='" + accession + '\'' +
                '}';
    }
}

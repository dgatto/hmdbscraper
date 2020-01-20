public class Metabolite {
    public String name, formula, monoisotopicMass, accession, secondaryAccessions, casNumber, smiles, inchikey, kingdom,
            metaboliteSuperClass, metaboliteClass, subClass, meltingPoint, boilingPoint, waterSolubility, logp,
            solubility, cellularLocations, biospecimenLocations, tissueLocations, normalConcentrations;

    public String getNormalConcentrations() {
        return normalConcentrations;
    }

    public void setNormalConcentrations(String normalConcentrations) {
        this.normalConcentrations = normalConcentrations;
    }

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

    public String getSecondaryAccessions() {
        return secondaryAccessions;
    }

    public void setSecondaryAccessions(String accessions) {
        this.secondaryAccessions = accessions;
    }

    public String getCasNumber() {
        return casNumber;
    }

    public void setCasNumber(String cas) {
        this.casNumber = cas;
    }

    public String getSmiles() {
        return smiles;
    }

    public void setSmiles(String smiles) {
        this.smiles = smiles;
    }

    public String getInchikey() {
        return inchikey;
    }

    public void setInchikey(String inchikey) {
        this.inchikey = inchikey;
    }

    public String getKingdom() {
        return kingdom;
    }

    public void setKingdom(String kingdom) {
        this.kingdom = kingdom;
    }

    public String getMetaboliteSuperClass() {
        return metaboliteSuperClass;
    }

    public void setMetaboliteSuperClass(String superClassVar) {
        this.metaboliteSuperClass = superClassVar;
    }

    public String getMetaboliteClass() {
        return metaboliteClass;
    }

    public void setMetaboliteClass(String classVar) {
        this.metaboliteClass = classVar;
    }

    public String getSubClass() {
        return subClass;
    }

    public void setSubClass(String subClass) {
        this.subClass = subClass;
    }

    public String getMeltingPoint() {
        return meltingPoint;
    }

    public void setMeltingPoint(String meltingPoint) {
        this.meltingPoint = meltingPoint;
    }

    public String getBoilingPoint() {
        return boilingPoint;
    }

    public void setBoilingPoint(String boilingPoint) {
        this.boilingPoint = boilingPoint;
    }

    public String getSolubility() {
        return solubility;
    }

    public void setSolubility(String solubility) {
        this.solubility = solubility;
    }

    public String getWaterSolubility() {
        return waterSolubility;
    }

    public void setWaterSolubility(String waterSolubility) {
        this.waterSolubility = waterSolubility;
    }

    public String getCellularLocations() {
        return cellularLocations;
    }

    public void setCellularLocations(String cellularLocations) {
        this.cellularLocations = cellularLocations;
    }

    public String getBiospecimenLocations() {
        return biospecimenLocations;
    }

    public void setBiospecimenLocations(String biospecimenLocations) {
        this.biospecimenLocations = biospecimenLocations;
    }

    public String getTissueLocations() {
        return tissueLocations;
    }

    public void setTissueLocations(String tissueLocations) {
        this.tissueLocations = tissueLocations;
    }

    public String getLogp() {
        return logp;
    }

    public void setLogp(String logp) {
        this.logp = logp;
    }

    public Metabolite() {
    }

    public void setNull() {
        this.name = null;
        this.formula = null;
        this.monoisotopicMass = null;
        this.accession = null;
        this.secondaryAccessions = null;
        this.casNumber = null;
        this.smiles = null;
        this.inchikey = null;
        this.kingdom = null;
        this.metaboliteSuperClass = null;
        this.metaboliteClass = null;
        this.subClass = null;
        this.meltingPoint = null;
        this.boilingPoint = null;
        this.waterSolubility = null;
        this.logp = null;
        this.solubility = null;
        this.cellularLocations = null;
        this.biospecimenLocations = null;
        this.tissueLocations = null;
        this.normalConcentrations = null;
    }

    @Override
    public String toString() {
        return "Metabolite{" + "name='" + name + '\'' + ", formula='" + formula + '\'' + ", monoisotopicMass='"
                + monoisotopicMass + '\'' + ", accession='" + accession + '\'' + ", secondaryAccessions='"
                + secondaryAccessions + '\'' + ", casNumber='" + casNumber + '\'' + ", smiles='" + smiles + '\''
                + ", inchikey='" + inchikey + '\'' + ", kingdom='" + kingdom + '\'' + ", metaboliteSuperClass='"
                + metaboliteSuperClass + '\'' + ", metaboliteClass='" + metaboliteClass + '\'' + ", subClass='"
                + subClass + '\'' + ", meltingPoint='" + meltingPoint + '\'' + ", boilingPoint='" + boilingPoint + '\''
                + ", waterSolubility='" + waterSolubility + '\'' + ", logp='" + logp + '\'' + ", solubility='"
                + solubility + '\'' + ", cellularLocations='" + cellularLocations + '\'' + ", biospecimenLocations='"
                + biospecimenLocations + '\'' + ", tissueLocations='" + tissueLocations + '\''
                + ", normalConcentrations='" + normalConcentrations + '\'' + '}';
    }
}

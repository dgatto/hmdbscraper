public class Metabolite {
    public String name, formula, monoisotopicMass, hmdb_id, secondaryAccessions, casNumber, smiles, inchikey, kingdom, metaboliteSuperClass, metaboliteClass,
                    subClass, meltingPoint, boilingPoint, waterSolubility, logp, solubility, cellularLocations, biospecimenLocations, tissueLocations, normalConcentrations;

    public String get_normal_concentrations() {
        return normalConcentrations;
    }

    public void set_normal_concentrations(String normalConcentrations) {
        this.normalConcentrations = normalConcentrations;
    }

    public String get_name() {
        return name;
    }

    public void set_name(String name) {
        this.name = name;
    }

    public String get_formula() {
        return formula;
    }

    public void set_formula(String formula) {
        this.formula = formula;
    }

    public String get_hmdb_id() {
        return hmdb_id;
    }

    public void set_hmdb_id(String hmdb_id) {
        this.hmdb_id = hmdb_id;
    }

    public String get_monoisotopic_mass() {
        return monoisotopicMass;
    }

    public void set_monoisotopic_mass(String monoisotopicMass) {
        this.monoisotopicMass = monoisotopicMass;
    }

    public String get_secondary_accessions() {return secondaryAccessions;}

    public void set_secondary_accessions(String secondary_accessions) {this.secondaryAccessions = secondary_accessions; }

    public String get_cas_registry_number() { return casNumber; }

    public void set_cas_registry_number(String cas) { this.casNumber = cas; }

    public String get_smiles() { return smiles; }

    public void set_smiles(String smiles) { this.smiles = smiles; }

    public String get_inchikey() { return inchikey; }

    public void set_inchikey(String inchikey) { this.inchikey = inchikey; }

    public String get_kingdom() { return kingdom; }

    public void set_kingdom(String kingdom) { this.kingdom = kingdom; }

    public String get_super_class() { return metaboliteSuperClass; }

    public void set_super_class(String superClassVar) { this.metaboliteSuperClass = superClassVar; }

    public String get_class() { return metaboliteClass; }

    public void set_class(String classVar) { this.metaboliteClass = classVar; }

    public String get_sub_class() { return subClass; }

    public void set_sub_class(String subClass) { this.subClass = subClass; }

    public String get_melting_point() {
        return meltingPoint;
    }

    public void set_melting_point(String meltingPoint) {
        this.meltingPoint = meltingPoint;
    }

    public String get_boiling_point() {
        return boilingPoint;
    }

    public void set_boiling_point(String boilingPoint) {
        this.boilingPoint = boilingPoint;
    }

    public String get_solubility() {
        return solubility;
    }

    public void set_solubility(String solubility) {
        this.solubility = solubility;
    }

    public String get_water_solubility() {
        return waterSolubility;
    }

    public void set_water_solubility(String waterSolubility) {
        this.waterSolubility = waterSolubility;
    }

    public String get_cellular_locations() {
        return cellularLocations;
    }

    public void set_cellular_locations(String cellularLocations) {
        this.cellularLocations = cellularLocations;
    }

    public String get_biospecimen_locations() {
        return biospecimenLocations;
    }

    public void set_biospecimen_locations(String biospecimenLocations) {
        this.biospecimenLocations = biospecimenLocations;
    }

    public String get_tissue_locations() {
        return tissueLocations;
    }

    public void set_tissue_locations(String tissueLocations) {
        this.tissueLocations = tissueLocations;
    }

    public String get_logp() {
        return logp;
    }

    public void set_logp(String logp) {
        this.logp = logp;
    }

    public void setNull() {
        this.name = null;
        this.formula = null;
        this.monoisotopicMass = null;
        this.hmdb_id = null;
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
        return "Metabolite{" +
                "name='" + name + '\'' +
                ", formula='" + formula + '\'' +
                ", monoisotopicMass='" + monoisotopicMass + '\'' +
                ", accession='" + hmdb_id + '\'' +
                ", secondaryAccessions='" + secondaryAccessions + '\'' +
                ", casNumber='" + casNumber + '\'' +
                ", smiles='" + smiles + '\'' +
                ", inchikey='" + inchikey + '\'' +
                ", kingdom='" + kingdom + '\'' +
                ", metaboliteSuperClass='" + metaboliteSuperClass + '\'' +
                ", metaboliteClass='" + metaboliteClass + '\'' +
                ", subClass='" + subClass + '\'' +
                ", meltingPoint='" + meltingPoint + '\'' +
                ", boilingPoint='" + boilingPoint + '\'' +
                ", waterSolubility='" + waterSolubility + '\'' +
                ", logp='" + logp + '\'' +
                ", solubility='" + solubility + '\'' +
                ", cellularLocations='" + cellularLocations + '\'' +
                ", biospecimenLocations='" + biospecimenLocations + '\'' +
                ", tissueLocations='" + tissueLocations + '\'' +
                ", normalConcentrations='" + normalConcentrations + '\'' +
                '}';
    }

    private Metabolite() { } // make your constructor private, so the only war
    // to access "application" is through singleton pattern

    private static Metabolite _app;

    public static Metabolite getSharedApplication()
    {
        if (_app == null)
            _app = new Metabolite();
        return _app;
    }

}

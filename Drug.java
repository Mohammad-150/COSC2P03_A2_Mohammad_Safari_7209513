import java.util.Scanner;

public class Drug {

    //data attributes of Drug class
    private String genericName;
    private String SMILES;
    private String drugBankID;
    private String url;
    private String drugGroups;
    private String score;

    public Drug(String data){
        Scanner fetch = new Scanner(data);  //scanner reads the whole line
        fetch.useDelimiter("\t");           //read between tabs

        while (fetch.hasNext()) {           //while there is more text to be read
            setGenericName(fetch.next());
            setSMILES(fetch.next());
            setDrugBankID(fetch.next());
            setUrl(fetch.next());
            setDrugGroups(fetch.next());
            setScore(fetch.next());
        }
    }   //constructor

    //This method returns the info about the drug
    public String displayDrug(){
        return (genericName +"\t"+ SMILES +"\t"+ drugBankID +"\t"+ url +"\t"+ drugGroups +"\t"+ score);
    }

    //Setters
    private void setGenericName(String n) {
        genericName = n;
    }
    private void setSMILES(String s) {
        SMILES = s;
    }
    private void setDrugBankID(String d) {
        drugBankID = d;
    }
    private void setUrl(String u) {
        url = u;
    }
    private void setDrugGroups(String g) {
        drugGroups = g;
    }
    private void setScore(String sc) {
        score = sc;
    }

    //Getters
    public String getGenericName() {
        return genericName;
    }
    public String getSMILES() {
        return SMILES;
    }
    public int getDrugBankID() {
        return Integer.parseInt(drugBankID.substring(2));
    }
    public String getUrl() {
        return url;
    }
    public String getDrugGroups() {
        return drugGroups;
    }
    public String getScore() {
        return score;
    }


}

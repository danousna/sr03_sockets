public class Compte {
    public String titulaire;
    public float solde;

    public Compte(String titulaire, int solde) {
        this.titulaire=titulaire;
        this.solde = solde;
    }

    public void Afficher() {
        System.out.println("le titulaire : " + titulaire + "solde : "+solde);
    }
}
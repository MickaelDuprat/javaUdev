package rodthedev.exo.udev;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

public class Etudiant extends SauvegardeEtudiant {
	private String code;
	private String nom;
	private Diplome diplome;
	private LocalDate dateNaissance;
	private Exception Exception;

	public Etudiant() {
	}

	public Etudiant(String nom, String code, Diplome diplome) {
		this.code = code;
		this.nom = nom;
		this.diplome = diplome;
	}

	public Etudiant(Diplome diplome) {
		this.diplome = diplome;
	}
	
	// Méthode qui enregistre en BDD les informations d'un étudiant
	public void sauvegarderEtudiant(int numerodiplome) throws SQLException {
			Scanner saisieClavier = new Scanner(System.in);
			System.out.println("Veuillez renseigner les informations concernant l'étudiant ");
			System.out.println("Veuillez saisir le nom de l'étudiant :");
			String code = saisieClavier.nextLine();
			System.out.println(code);
			setNom(code);
			System.out.println("Veuillez saisir le code de l'étudiant :");
			String nom = saisieClavier.nextLine();
			System.out.println(nom);
			setCode(nom);
			System.out.println("Veuillez saisir la date de naissance de l'étudiant (jj/mm/yyyy) :");
			String datenaissance = saisieClavier.nextLine();
			System.out.println(datenaissance);
			setDateNaissance(datenaissance);
			int diplome_etudiant = numerodiplome;
			System.out.println(diplome_etudiant);
			
			String requeteSql = "Insert into etudiant (code_etudiant, nom, datenaissance, diplome_etudiant) values (?, ?, ?, ?);";
			try (PreparedStatement pstmt = BDDconnection.getInstance().prepareStatement(requeteSql)) {
				pstmt.setString(1, code);
				pstmt.setString(2, nom);
				pstmt.setString(3, datenaissance);
				pstmt.setInt(4, diplome_etudiant);
				pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}	
	
	
	// Méthode qui récupère les informations d'un étudiant à partir de son code étudiant récupéré au scanner
	public int accederEtudiant(String code_etudiant) throws Exception {

		String requeteSql = "Select diplome_etudiant, nom, datenaissance from etudiant where code_etudiant = ?;";
		int diplome_etudiant = 0;
		try (PreparedStatement pstmt = BDDconnection.getInstance().prepareStatement(requeteSql)) {
			pstmt.setString(1, code_etudiant);
			try (java.sql.ResultSet resultSet = pstmt.executeQuery()) {
				while (resultSet.next()) {
				diplome_etudiant = resultSet.getInt("diplome_etudiant");
				setCode(code_etudiant);
				String nom = resultSet.getString("nom");
				setNom(nom);
				String datenaissance = resultSet.getString("datenaissance");
				setDateNaissance(datenaissance); 
				}
			} 
		} 
		return diplome_etudiant;		
}

	

	public String getCode() {
		return code;
	}

	public String getNom() {
		return nom;
	}

	public Diplome getDiplome() {
		return diplome;
	}

	public LocalDate getDateNaissance() {
		return dateNaissance;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public void setDiplome(Diplome diplome) {
		this.diplome = diplome;
	}

	public void setDateNaissance(String date) {
		DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		formater = formater.withLocale(Locale.FRANCE);
		LocalDate dateSoumise = LocalDate.parse(date, formater);
		if (dateSoumise.isBefore(LocalDate.of(1970, 1, 01))) {
			throw new IllegalArgumentException("Impossible, rien n'existait avant le 01/01/1970 :-)");
		} else if (dateSoumise.isAfter(LocalDate.of(2017, 12, 15))) {
			throw new IllegalArgumentException("Impossible, sauf si on est dans Retour vers le futur!");
		} else if (dateSoumise.isBefore(LocalDate.of(2017, 12, 15))
				&& dateSoumise.isAfter(LocalDate.of(2002, 12, 31))) {
			throw new IllegalArgumentException("Wouah, voilà un étudiant précoce !");
		} else {
			this.dateNaissance = dateSoumise;
		}
	}

}

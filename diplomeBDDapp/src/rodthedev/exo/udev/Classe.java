package rodthedev.exo.udev;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Classe {
	private List<Etudiant> listeEleve;

	public Classe() {
		this.listeEleve = new ArrayList<Etudiant>();
	}

	public List<Etudiant> getListeEleve() {
		return listeEleve;
	}
	
	public void addEtudiant(Etudiant etudiant) {
		listeEleve.add(etudiant);
	}
	
	public List<Etudiant> addEtudiant(int compteur) throws SQLException {
		DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		String url = "jdbc:mysql://localhost/exodiplomejava?useSSL=false";
		String login = "root";
		String password = "root";
		try (Connection connection = DriverManager.getConnection(url, login, password)) {
		List<Etudiant> listeEleve = new ArrayList<>();
		String requeteSql = "select code_etudiant, nom_etudiant, datenaissance from Etudiant where id_etudiant = ?";
		try (PreparedStatement stmt = connection.prepareStatement(requeteSql)) {
			stmt.setInt(1, compteur);
			try (ResultSet resultSet = stmt.executeQuery()) {
				while (resultSet.next()) {
					Etudiant etudiant = new Etudiant();
					etudiant.setCode(resultSet.getString("code_etudiant"));
					etudiant.setNom(resultSet.getString("nom_etudiant"));
					etudiant.setDateNaissance(resultSet.getString("datenaissance"));
					listeEleve.add(etudiant);
				}
			}catch (SQLException e) {
					e.printStackTrace();
			}
				return listeEleve;
		}
		}
	}

	// Méthode qui calcule la moyenne de la classe
	public float calculeMoyenneClasse() {
		float moyenne;
		float noteAdditionnee = 0;
		for (int i = 0; i < listeEleve.size(); i++) {
			noteAdditionnee += listeEleve.get(i).getDiplome().calculeMoyenne();
		}
		moyenne = (noteAdditionnee / listeEleve.size());
		return moyenne;
	}

}

package rodthedev.exo.udev;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Atrier {

	public static void main(String[] args) throws SQLException {
		DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		String url = "jdbc:mysql://localhost/exodiplomejava?useSSL=false";
		String login = "root";
		String password = "formationudev2017";
		try (Connection connection = DriverManager.getConnection(url, login, password)) {
			// sauvegardeEtudiant(connection, "eleve01", "Duprat", "1990-08-31");
			// accederEtudiant(connection, "eleve01");
			// sauvegarderQCM(connection, "qcm01", "2017-09-01", 100, 80);
			// sauvegarderProjet(connection, "projet01", "2017-09-01", 7.0f, 7.0f);
			// sauvegarderControle(connection, "controle01", "2017-09-01", 17.0f);
			// sauvegarderDiplome(connection, "diplome eleve 01", 1, 1, 1, 1);
			// accederInfoEtudiant(connection, "eleve01");
			sauvegarderDiplome(connection, "diplome1");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/*
	 * private static void sauvegarderDiplome(Connection connection, String
	 * code_diplome) throws SQLException {
	 * 
	 * String requeteSql = "Insert into diplome (code_diplome) values (?);"; try
	 * (PreparedStatement pstmt = connection.prepareStatement(requeteSql)) {
	 * pstmt.setString(1, code_diplome); pstmt.executeUpdate(); } }
	 */

	private static void accederInfoEtudiant(Connection connection, String code_etudiant) throws SQLException {

		String requeteSql = "Select * from diplome left join etudiant on etudiant.id_etudiant = diplome.etudiant_diplome left join qcm on qcm.id_qcm = diplome.qcm_diplome left join projet on projet.id_projet= diplome.projet_diplome	left join controle on controle.id_controle = diplome.controle_diplome	where code_etudiant = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(requeteSql)) {
			pstmt.setString(1, code_etudiant);
			try (java.sql.ResultSet resultSet = pstmt.executeQuery()) {
				while (resultSet.next()) {
					System.out.println(resultSet.getString("code_etudiant"));
					System.out.println(resultSet.getString("nom"));
					System.out.println(resultSet.getString("code_qcm"));
					System.out.println(resultSet.getString("nbre_reponses_correctes"));
					System.out.println(resultSet.getString("code_projet"));
					System.out.println(resultSet.getFloat("noteorale"));
					System.out.println(resultSet.getFloat("noteecrite"));
					System.out.println(resultSet.getString("code_controle"));
					System.out.println(resultSet.getFloat("note_controle"));
				}
			}
		}
	}

	private static void sauvegarderDiplome(Connection connection, String code_diplome) throws SQLException {

		String requeteSql = "Insert into diplome (code_diplome) values (?);";
		try (PreparedStatement pstmt = connection.prepareStatement(requeteSql)) {
			pstmt.setString(1, code_diplome);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

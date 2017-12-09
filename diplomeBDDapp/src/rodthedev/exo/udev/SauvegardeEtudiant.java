package rodthedev.exo.udev;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

abstract class SauvegardeEtudiant implements EtudiantDao {

	@Override
	public void sauvegarderEtudiant(Connection connection, String code_etudiant, String nom, String datenaissance,
			int diplome_etudiant) throws SQLException {

		String requeteSql = "Insert into etudiant (code_etudiant, nom, datenaissance, diplome_etudiant) values (?, ?, ?, ?);";
		try (PreparedStatement pstmt = connection.prepareStatement(requeteSql)) {
			pstmt.setString(1, code_etudiant);
			pstmt.setString(2, nom);
			pstmt.setString(3, datenaissance);
			pstmt.setInt(4, diplome_etudiant);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void accederEtudiant(Connection connection, String code_etudiant) throws SQLException {

		String requeteSql = "Select * from etudiant left join diplome on diplome.id_diplome = etudiant.diplome_etudiant";
		requeteSql += "left join qcm on qcm.diplome_qcm = diplome.id_diplome";
		requeteSql += "left join projet on projet.diplome_projet = diplome.id_diplome";
		requeteSql += "left join controle on controle.diplome_controle = diplome.id_diplome where code_etudiant = ?;";
		System.out.println(requeteSql);
		try (PreparedStatement pstmt = connection.prepareStatement(requeteSql)) {
			pstmt.setString(1, code_etudiant);
			try (java.sql.ResultSet resultSet = pstmt.executeQuery()) {
				while (resultSet.next()) {
					System.out.println(resultSet.getString("code_etudiant"));
					System.out.println(resultSet.getString("nom"));
					System.out.println(resultSet.getString("datenaissance"));
					System.out.println(resultSet.getString("diplome_etudiant"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

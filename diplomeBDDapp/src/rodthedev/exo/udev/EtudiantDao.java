package rodthedev.exo.udev;

import java.sql.Connection;
import java.sql.SQLException;

public interface EtudiantDao {

	void accederEtudiant(Connection connection, String code_etudiant) throws SQLException;

	void sauvegarderEtudiant(Connection connection, String code_etudiant, String nom, String datenaissance,
			int diplome_etudiant) throws SQLException;

}

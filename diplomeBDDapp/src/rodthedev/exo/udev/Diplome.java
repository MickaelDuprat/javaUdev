package rodthedev.exo.udev;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import exception.rodthedev.exo.udev.IncorrectValueforGoodAnswersException;
import exception.rodthedev.exo.udev.IncorrectValueforNoteException;
import exception.rodthedev.exo.udev.IncorrectValueforNumberQuestionException;
import exception.rodthedev.exo.udev.IncorrectValueforOralNoteException;
import exception.rodthedev.exo.udev.IncorrectValueforWrittenNoteException;

public class Diplome {
	private List<Examen> listeExamen;
	private Examen examen;
	private String code;

	public Diplome(List<Examen> listExamen) {
		this.listeExamen = listExamen;
	}

	public Diplome() {
	}

	public List<Examen> getTableau(Comparator<Examen> comparateur) {
		CompByNote comparateurNote = new CompByNote();
		CompByDate comparateurDate = new CompByDate();
		CompByCode comparateurCode = new CompByCode();
		if (comparateur.equals(comparateurNote)) {
			Collections.sort(listeExamen, comparateurNote);
		} else if (comparateur.equals(comparateurCode)) {
			Collections.sort(listeExamen, comparateurCode);
		} else if (comparateur.equals(comparateurDate)) {
			Collections.sort(listeExamen, comparateurDate);
		}
		return listeExamen;
	}
	
	// M�thode qui enregistre en BDD les informations d'un dipl�me 
	public static int creerDiplome() throws SQLException {
			Scanner saisieClavier = new Scanner(System.in);
			int generatedID = 0;
			
			System.out.println("Veuillez renseigner le code du dipl�me :");
			String code_diplome = saisieClavier.nextLine();
			
			String requeteSql = "Insert into diplome (code_diplome) values (?);";
			try (PreparedStatement pstmt = BDDconnection.getInstance().prepareStatement(requeteSql, Statement.RETURN_GENERATED_KEYS)) {
				pstmt.setString(1, code_diplome);
				pstmt.executeUpdate();
				
				// on r�cup�re la derni�re valeur de l'auto_increment pour l'utiliser comme cl� et l'attribuer ensuite � l'�tudiant et aux examens
				ResultSet rs = pstmt.getGeneratedKeys();
				while (rs.next()) {
					generatedID = rs.getInt(1);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return generatedID;
			}
	
//m�thode pour r�cup�rer les infos des contr�les pour un �tudiant en connaissant son num�ro de dipl�me
	public void accederControle(int diplome_controle) throws SQLException, IncorrectValueforNoteException {

		String requeteSql = "Select code_controle, date_controle, note_controle from controle where diplome_controle = ?;";
		
		try (PreparedStatement pstmt = BDDconnection.getInstance().prepareStatement(requeteSql)) {
			pstmt.setInt(1, diplome_controle);
			try (java.sql.ResultSet resultSet = pstmt.executeQuery()) {
				while (resultSet.next()) {
				Controle controle = new Controle();
				String code_controle = resultSet.getString("code_controle");
				controle.setCode(code_controle);
				String date_controle = resultSet.getString("date_controle");
				controle.setDateExamen(date_controle);
				float note_controle = resultSet.getFloat("note_controle");
				controle.setNote(note_controle);
				this.addExamen(controle);
			}
		} 			
	}
}
		
//m�thode pour r�cup�rer les infos des QCM pour un �tudiant en connaissant son num�ro de dipl�me
	public void accederQCM(int diplome_qcm) throws SQLException, IncorrectValueforNoteException, IncorrectValueforNumberQuestionException, IncorrectValueforGoodAnswersException {

		String requeteSql = "Select code_qcm, date_qcm, nbre_questions, nbre_reponses_correctes from qcm where diplome_qcm = ?;";
		
		try (PreparedStatement pstmt = BDDconnection.getInstance().prepareStatement(requeteSql)) {
			pstmt.setInt(1, diplome_qcm);
			try (java.sql.ResultSet resultSet = pstmt.executeQuery()) {
				while (resultSet.next()) {
				Qcm qcm = new Qcm();
				String code_qcm = resultSet.getString("code_qcm");
				qcm.setCode(code_qcm);
				String date_qcm = resultSet.getString("date_qcm");
				qcm.setDateExamen(date_qcm);
				int nbre_questions = resultSet.getInt("nbre_questions");
				qcm.setNbquestions(nbre_questions);
				int nbre_reponses_correctes = resultSet.getInt("nbre_reponses_correctes");
				qcm.setNbReponseCorrectes(nbre_reponses_correctes);
				this.addExamen(qcm);
			}
		} 			
	}
}
		
//m�thode pour r�cup�rer les infos des contr�les pour un �tudiant en connaissant son num�ro de dipl�me
	public void accederProjet(int diplome_projet) throws SQLException, IncorrectValueforNoteException, IncorrectValueforWrittenNoteException, IncorrectValueforOralNoteException {

		String requeteSql = "Select code_projet, date_projet, noteorale, noteecrite from projet where diplome_projet = ?;";
		
		try (PreparedStatement pstmt = BDDconnection.getInstance().prepareStatement(requeteSql)) {
			pstmt.setInt(1, diplome_projet);
			try (java.sql.ResultSet resultSet = pstmt.executeQuery()) {
				while (resultSet.next()) {
				Projet projet = new Projet();
				String code_projet = resultSet.getString("code_projet");
				projet.setCode(code_projet);
				String date_projet = resultSet.getString("date_projet");
				projet.setDateExamen(date_projet);
				float noteorale = resultSet.getFloat("noteorale");
				projet.setNoteOrale(noteorale);
				float noteecrite = resultSet.getFloat("noteecrite");
				projet.setNoteEcrite(noteecrite);
				this.addExamen(projet);
			}
		} 			
	}
}
		
	// M�thode qui calcule la moyenne du diplome
	public float calculeMoyenne() {
		float moyenne;
		float noteAdditionnee = 0;
		for (int i = 0; i < listeExamen.size(); i++) {
			noteAdditionnee += listeExamen.get(i).getNote();
		}
		moyenne = (noteAdditionnee / listeExamen.size());
		return moyenne;
	}

	// M�thode qui v�rifie si le diplome est valid� car la moyenne est sup�rieure
	// �10
	public boolean isValide() {
		return this.calculeMoyenne() >= 10;
	}

	public void delivrer() {
		if (this.isValide() == false) {
			System.out.println("Diplome non obtenu !");
		} else {
			System.out.println("Diplome obtenu !!");
		}
	}

	public MentionEnum getMention() {

		float moyenne = calculeMoyenne();

		if (moyenne >= 10 && moyenne < 12) {
			return MentionEnum.PASSABLE;
		} else if (moyenne >= 12 && moyenne < 14) {
			return MentionEnum.BIEN;
		} else if (moyenne >= 14 && moyenne < 16) {
			return MentionEnum.TRESBIEN;
		} else if (moyenne >= 16) {
			return MentionEnum.EXCELLENT;
		} else {
			return MentionEnum.PASDEMENTION;
		}
	}

	public void addExamen(Examen examen) {
		this.setExamen(examen);
		listeExamen.add(examen);
	}

	public void getTableauByNote(float note) {
		for (Iterator<Examen> noteIterator = listeExamen.iterator(); noteIterator.hasNext();) {
			Examen examen = noteIterator.next();
			if (examen.getNote() > note) {
				noteIterator.remove();
			}
		}
	}

	public void deleteExamen(String code) {
		for (Iterator<Examen> codeIterator = listeExamen.iterator(); codeIterator.hasNext();) {
			Examen examen = codeIterator.next();
			if (examen.getCode() == code) {
				codeIterator.remove();
			}
		}
	}

	public List<Examen> getListExamen() {
		return listeExamen;
	}

	public Examen getExamen() {
		return examen;
	}

	public void setExamen(Examen examen) {
		this.examen = examen;
	}

	public String getCode() {
		return code;
	}

}

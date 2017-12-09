package rodthedev.exo.udev;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import exception.rodthedev.exo.udev.IncorrectValueforGoodAnswersException;
import exception.rodthedev.exo.udev.IncorrectValueforNoteException;
import exception.rodthedev.exo.udev.IncorrectValueforNumberQuestionException;

public class Qcm extends Examen {

	private int nbReponseCorrectes;
	private int nbQuestions;
	private String date;

	public Qcm() {
	}

	public Qcm(int nbQuestions) throws IncorrectValueforNumberQuestionException {
		if (nbQuestions <= 0) {
			throw new IncorrectValueforNumberQuestionException();
		}
		this.nbQuestions = nbQuestions;
	}

	public void setNbReponseCorrectes(int nbReponseCorrectes)
			throws IncorrectValueforGoodAnswersException, IncorrectValueforNoteException {
		if (nbReponseCorrectes > nbQuestions || nbReponseCorrectes < 0) {
			throw new IncorrectValueforGoodAnswersException();
		}
		this.nbReponseCorrectes = nbReponseCorrectes;
		this.setNote(((float) this.nbReponseCorrectes / this.nbQuestions) * 20);
	}

	// Méthode qui permet d'instancier un QCM en base de donnée et en tant qu'objet
	// au travers d'une saisie par Scanner
	public void ajouterQcm(int numerodiplome) throws IncorrectValueforGoodAnswersException, IncorrectValueforNoteException, IncorrectValueforNumberQuestionException {
		
			Scanner saisieClavier = new Scanner(System.in);
			System.out.println("Veuillez saisir le code du QCM : ");
			String code_qcm = saisieClavier.nextLine();
			System.out.println("Veuillez saisir la date du QCM au format (jj/mm/yyyy) comprise entre le 01/09/2017 et 30/06/2018 : ");
			String date_qcm = saisieClavier.nextLine();
			setDateExamen(date_qcm);
			System.out.println("Veuillez saisir le nombre de questions du QCM (supérieur à 0) : ");
			int nbre_questions = saisieClavier.nextInt();
			setNbquestions(nbre_questions);
			System.out.println("Veuillez saisir le nombre de réponses correctes (< au nombre de questions) : ");
			int nbre_reponses_correctes = saisieClavier.nextInt();
			setNbReponseCorrectes(nbre_reponses_correctes);
			int diplome_qcm = numerodiplome;

			String requeteSql = "Insert into qcm (code_qcm, date_qcm, nbre_questions, nbre_reponses_correctes, diplome_qcm) values (?, ?, ?, ?, ?);";
			try (PreparedStatement pstmt = BDDconnection.getInstance().prepareStatement(requeteSql)) {
			pstmt.setString(1, code_qcm);
			pstmt.setString(2, date_qcm);
			pstmt.setInt(3, nbre_questions);
			pstmt.setInt(4, nbre_reponses_correctes);
			pstmt.setInt(5, diplome_qcm);
			pstmt.executeUpdate();
			System.out.println("le qcm a bien été ajouté");
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}


	public void afficher() {
		System.out.println("le qcm a pour appreciation : " + this.getAppreciation() + " et la note  : " + this.getNote());
	}

	public void setAppreciation() {
		if (this.getNote() < 8) {
			this.setAppreciation("insuffisant");
		} else if (this.getNote() >= 8 && this.getNote() < 12) {
			this.setAppreciation("passable");
		} else if (this.getNote() >= 12 && this.getNote() <= 15) {
			this.setAppreciation("bien");
		} else {
			this.setAppreciation("très bien");
		}
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setNbquestions(int nbQuestions) throws IncorrectValueforNumberQuestionException {
		if (nbQuestions <= 0) {
			throw new IncorrectValueforNumberQuestionException();
		}
		this.nbQuestions = nbQuestions;
	}

}

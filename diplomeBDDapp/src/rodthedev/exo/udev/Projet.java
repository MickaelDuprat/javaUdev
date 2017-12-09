package rodthedev.exo.udev;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import exception.rodthedev.exo.udev.IncorrectValueforOralNoteException;
import exception.rodthedev.exo.udev.IncorrectValueforWrittenNoteException;

public class Projet extends Examen {
	private float noteOrale;
	private float noteEcrite;
	private String appreciationEcrite;
	private String appreciationOrale;
	private String date;

	public Projet() {
	}

	public Projet(float noteOrale, float noteEcrite)
			throws IncorrectValueforWrittenNoteException, IncorrectValueforOralNoteException {
		if (noteOrale < 0.0f || noteOrale > 10.0f) {
			throw new IncorrectValueforOralNoteException();
		} else if (noteEcrite < 0.0f || noteEcrite > 10.0f) {
			throw new IncorrectValueforWrittenNoteException();
		} else {
			this.noteOrale = noteOrale;
			this.noteEcrite = noteEcrite;
		}
	}

	public void afficher() {
		System.out.println("la note de l'orale est de : " + this.noteOrale + "son appreciation est : "
				+ this.getAppreciationOrale());
		System.out.println("la note de l'ecrit est de : " + this.noteEcrite + "son appreciation est : "
				+ this.getAppreciationEcrite());
	}

	// Méthode qui permet d'instancier un projet en base de donnée et en tant
	// qu'objet au travers d'une saisie par Scanner
	public void ajouterProjet(int numerodiplome) throws IncorrectValueforWrittenNoteException, IncorrectValueforOralNoteException {
		
			Scanner saisieClavier = new Scanner(System.in);
			System.out.println("Veuillez saisir le code du projet : ");
			String code_projet = saisieClavier.nextLine();
			setCode(code_projet);
			System.out.println("Veuillez saisir la date du projet au format (jj/mm/yyyy) comprise entre le 01/09/2017 et 30/06/2018 : ");
			String date_projet = saisieClavier.nextLine();
			setDateExamen(date_projet);
			System.out.println("Veuillez saisir la note de l'ecrit (comprise entre 0 et 10) : ");
			float noteorale = saisieClavier.nextFloat();
			setNoteOrale(noteorale);
			System.out.println("Veuillez saisir la note de l'oral (comprise entre 0 et 10) : ");
			float noteecrite = saisieClavier.nextFloat();
			setNoteEcrite(noteecrite);
			int diplome_projet = numerodiplome;

			String requeteSql = "Insert into projet (code_projet, date_projet, noteorale, noteecrite, diplome_projet) values (?, ?, ?, ?, ?);";
			try (PreparedStatement pstmt = BDDconnection.getInstance().prepareStatement(requeteSql)) {
			pstmt.setString(1, code_projet);
			pstmt.setString(2, date_projet);
			pstmt.setFloat(3, noteorale);
			pstmt.setFloat(4, noteecrite);
			pstmt.setFloat(5, diplome_projet);
			pstmt.executeUpdate();
			System.out.println("le projet a bien été ajouté");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	public void setAppreciationOrale(String appreciationOrale) {
		this.appreciationOrale = appreciationOrale;
	}

	public void setAppreciationEcrite(String appreciationEcrite) {
		this.appreciationEcrite = appreciationEcrite;
	}

	public String getAppreciationEcrite() {
		return appreciationEcrite;
	}

	public String getAppreciationOrale() {
		return appreciationOrale;
	}

	public float getNoteOrale() {
		return noteOrale;
	}

	public void setNoteOrale(float noteOrale) throws IncorrectValueforOralNoteException {
		if (noteOrale < 0.0f || noteOrale > 10.0f) {
			throw new IncorrectValueforOralNoteException();
		}
		this.noteOrale = noteOrale;
	}

	public void setNoteEcrite(float noteEcrite) throws IncorrectValueforWrittenNoteException {
		if (noteEcrite < 0.0f || noteEcrite > 10.0f) {
			throw new IncorrectValueforWrittenNoteException();
		}
		this.noteEcrite = noteEcrite;
	}

	public float getNoteEcrite() {
		return noteEcrite;
	}

	public float getNote() {
		float notecumulee = this.noteEcrite + this.noteOrale;
		return notecumulee;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
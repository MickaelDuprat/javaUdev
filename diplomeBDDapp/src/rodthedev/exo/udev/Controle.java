package rodthedev.exo.udev;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import exception.rodthedev.exo.udev.IncorrectValueforNoteException;

public class Controle extends Examen {

	private float note;
	private String appreciation;
	private String date;

	public Controle() {
	}

	public Controle(float noteOrale, float noteEcrite) {
	}

	//Méthode qui permet d'instancier un contrôle en base de donnée et en tant qu'objet au travers d'une saisie par Scanner
	public void ajouterControle(int numerodiplome) throws IncorrectValueforNoteException {	
		Scanner saisieClavier = new Scanner(System.in);
		System.out.println("Veuillez saisir le code du controle : ");
		String code_controle = saisieClavier.nextLine();
		setCode(code_controle);
		System.out.println("Veuillez saisir la date du controle au format (jj/mm/yyyy) comprise entre le 01/09/2017 et 30/06/2018 : ");
		String date_controle = saisieClavier.nextLine();
		setDateExamen(date_controle);
		System.out.println("Veuillez saisir le note du controle (comprise entre 0 et 20) : ");
		Float note_controle = saisieClavier.nextFloat();
		setNote(note_controle);
		int diplome_controle = numerodiplome;
		
		String requeteSql = "Insert into controle (code_controle, date_controle, note_controle, diplome_controle) values (?, ?, ?, ?);";
		try (PreparedStatement pstmt = BDDconnection.getInstance().prepareStatement(requeteSql)) {
            pstmt.setString(1, code_controle);
            pstmt.setString(2, date_controle);
            pstmt.setFloat(3, note_controle);
            pstmt.setFloat(4, diplome_controle);
            pstmt.executeUpdate();
		System.out.println("le controle a bien été ajouté");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public float getNote() {
		return this.note;
	}

	public void setNote(float note) throws IncorrectValueforNoteException {
			if (note < 0 || note > 20) {
				throw new IncorrectValueforNoteException();
			}
			this.note = note;
	}

	public String getAppreciation() {
		return appreciation;
	}

	public void setAppreciation(String appreciation) {
		this.appreciation = appreciation;
	}

	public void afficher() {
		System.out.println("l'examen : " + this.getAppreciation() + " et la note : " + this.getNote());
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
package rodthedev.exo.udev;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import exception.rodthedev.exo.udev.IncorrectValueforNoteException;
import exception.rodthedev.exo.udev.IncorrectValueforNumberQuestionException;

abstract public class Examen {
	private float note;
	private String appreciation;
	private String code;
	LocalDate dateExamen;
	LocalDate dateDebut = LocalDate.of(2017, 8, 31);
	LocalDate dateFin = LocalDate.of(2018, 7, 01);

	public boolean isValideDate(String date) {
		DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		formater = formater.withLocale(Locale.FRANCE);
		LocalDate dateSoumise = LocalDate.parse(date, formater);
			if (dateSoumise.isAfter(dateDebut) && dateSoumise.isBefore(dateFin)) {
				if (dateSoumise.getDayOfWeek() != DayOfWeek.SATURDAY && dateSoumise.getDayOfWeek() != DayOfWeek.SUNDAY) {
					return true;
				} else {
					throw new IllegalArgumentException("la date ne peut pas être un samedi ou un dimanche");
				}
			} else {
				throw new IllegalArgumentException("La date ne peut pas être en dehors de l'année scolaire en cours");
			}
		}


	public String toString() {
		DateTimeFormatter formater = DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy").withLocale(Locale.FRANCE);
		String dateFormate = dateExamen.format(formater);
		String noteFormater = String.format("%5.2f", this.getNote());
		return noteFormater + "/20 à  l'examen du " + dateFormate + " ayant pour code : "+ getCode() +"\n";
	}

	public void setDateExamen(String date) {
			if (this.isValideDate(date)) {
			DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			formater = formater.withLocale(Locale.FRANCE);
			LocalDate dateSoumise = LocalDate.parse(date, formater);
			this.dateExamen = dateSoumise;
			} else {
				System.out.println("Veuillez saisir une date valide!");
			}
}

	public Examen() {
	}
	
	public Examen(String appreciation) {
	}

	public Examen(int nbQuestions) throws IncorrectValueforNumberQuestionException {
			if (nbQuestions <= 0) 
			throw new IncorrectValueforNumberQuestionException();
	}

	public Examen(float noteOrale, float noteEcrite) {
	}

	public LocalDate getDateExamen() {
		return dateExamen;
	}

	public float getNote() {
		return note;
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
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}

	
	
}
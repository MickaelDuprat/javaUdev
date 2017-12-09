package rodthedev.exo.udev;

import exception.rodthedev.exo.udev.IncorrectValueforAppreciationException;

public class Appreciation extends Examen {

	private String appreciation;

	public Appreciation() {

	}

	public void setAppreciation(String appreciation) {
		this.appreciation = appreciation;
		try {
			if (appreciation.equalsIgnoreCase("acquis")) {
				appreciation = ("acquis");
				setNote(15);
			} else if (appreciation.equalsIgnoreCase("en cours d'acquisition")) {
				appreciation = ("en cours d'acquisition");
				setNote(10);
			} else if (appreciation.equalsIgnoreCase("non acquis")) {
				appreciation = ("non acquis");
				setNote(5);
			} else {
				throw new IncorrectValueforAppreciationException();
			}
		} catch (IncorrectValueforAppreciationException e) {
		}
	}

	public void setNote(float note) {

	}

	public String getAppreciation() {
		return appreciation;
	}

}

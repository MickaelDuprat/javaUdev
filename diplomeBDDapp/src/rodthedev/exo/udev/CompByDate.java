package rodthedev.exo.udev;

import java.util.Comparator;

public class CompByDate implements Comparator<Examen> {

	@Override
	public int compare(Examen date1, Examen date2) {
		return date1.getDateExamen().compareTo(date2.getDateExamen());
	}

}
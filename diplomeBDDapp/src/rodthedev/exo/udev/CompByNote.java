package rodthedev.exo.udev;

import java.util.Comparator;

public class CompByNote implements Comparator<Examen> {

	@Override
	public int compare(Examen note1, Examen note2) {
		return Float.compare(note1.getNote(), note2.getNote());
	}
	


}
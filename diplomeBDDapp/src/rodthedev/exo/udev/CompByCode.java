package rodthedev.exo.udev;

import java.util.Comparator;

public class CompByCode implements Comparator<Examen> {

	@Override
	public int compare(Examen code1, Examen code2) {
		return code1.getCode().compareTo(code2.getCode());
	}

}

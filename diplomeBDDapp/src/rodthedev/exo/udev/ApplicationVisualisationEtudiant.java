package rodthedev.exo.udev;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import exception.rodthedev.exo.udev.IncorrectValueforGoodAnswersException;
import exception.rodthedev.exo.udev.IncorrectValueforNoteException;
import exception.rodthedev.exo.udev.IncorrectValueforNumberQuestionException;
import exception.rodthedev.exo.udev.IncorrectValueforOralNoteException;
import exception.rodthedev.exo.udev.IncorrectValueforWrittenNoteException;

public class ApplicationVisualisationEtudiant {

	public static void main(String[] args) throws IncorrectValueforNoteException, IncorrectValueforGoodAnswersException, IncorrectValueforNumberQuestionException, IncorrectValueforWrittenNoteException, IncorrectValueforOralNoteException, SQLException {
		
		char tmp = ' ';
		
		try {
			do {
				// On instancie un nouvel �tudiant
				Etudiant etudiant = new Etudiant();
				Scanner saisieClavier = new Scanner(System.in);
				System.out.println("Bonjour, veuillez saisir le code de l'�tudiant : ");
				String code_etudiant = saisieClavier.nextLine();
				
				// On r�cup�re les informations de la BDD sur un �tudiant avec la m�thode "accederEtudiant" � partir de son code �tudiant
				// saisi par l'utilisateur. Puis on r�cup�re le num�ro du dipl�me en BDD correspondant � cet �tudiant 
				//pour pouvoir aller r�cup�rer apr�s les examens correspondant � ce num�ro de dipl�me et donc � cet �tudiant
				int diplome_controle = etudiant.accederEtudiant(code_etudiant);
				int diplome_qcm = etudiant.accederEtudiant(code_etudiant);
				int diplome_projet = etudiant.accederEtudiant(code_etudiant);
				
				// On instancie un nouvel dipl�me que l'on affecte au nouvel �tudiant
				List<Examen> examenList = new ArrayList<Examen>();
				Diplome diplome = new Diplome(examenList);
				etudiant.setDiplome(diplome);
				
				//On r�cup�re les diff�rents examens (QCM, Projet, Contr�le) li�s � l'�tudiant
				diplome.accederControle(diplome_controle);
				diplome.accederQCM(diplome_qcm);
				diplome.accederProjet(diplome_projet);
				
				//Affichage des informations de l'�tudiant et sur le dipl�me
				System.out.println(" ");
				String moyenneFormater = String.format("%5.2f", diplome.calculeMoyenne());
				System.out.println(
						"L'�l�ve " + etudiant.getNom() + " a une moyenne de " + moyenneFormater);
				System.out.print("Statut du diplome : ");
				diplome.delivrer();
				System.out.println("avec : " + diplome.getMention());
				System.out.println("");
				CompByNote comparateurNote = new CompByNote();
				CompByDate comparateurDate = new CompByDate();
				CompByCode comparateurCode = new CompByCode();
				
				//for(int i = 0;i<diplome.getListExamen().size();i++) {
				System.out.println("Tableau tri� par Note : ");
				System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
				diplome.getListExamen().sort(comparateurNote);
				System.out.println(diplome.getTableau(comparateurNote));
				System.out.println("Tableau tri� par Date : ");
				System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
				diplome.getListExamen().sort(comparateurDate);
				System.out.println(diplome.getTableau(comparateurDate));
				System.out.println("Tableau tri� par Code : ");
				System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
				diplome.getListExamen().sort(comparateurCode);
				System.out.println(diplome.getTableau(comparateurCode));
			//	}
				do {
				System.out.println(" ");
				System.out.println("Voulez-vous consulter un nouvel �tudiant ?");
				System.out.println(" 'O' pour oui  / 'N' pour non");
				tmp = saisieClavier.next().charAt(0);
				} while (tmp != 'O' && tmp != 'N');
			} while (tmp == 'O');
			System.out.println("Merci d'avoir utilis� notre application dernier cri !!");
			System.out.println("Au revoir et � bientot");
			if (BDDconnection.getInstance() != null) {
				BDDconnection.getInstance().close();}
			System.exit(0);
		} catch (Exception e) {
		  System.out.println("Error404! Veuillez recommencer car la visualisation n'a pas fonctionn� !");
			   e.printStackTrace();
			  if (BDDconnection.getInstance() != null) {
			  BDDconnection.getInstance().close();}
			  System.exit(0);
		  }
}
}
	

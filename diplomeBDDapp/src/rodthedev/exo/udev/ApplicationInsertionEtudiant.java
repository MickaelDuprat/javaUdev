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

public class ApplicationInsertionEtudiant {

	public static void main(String[] args) throws IncorrectValueforNoteException, IncorrectValueforGoodAnswersException, IncorrectValueforNumberQuestionException, IncorrectValueforWrittenNoteException, IncorrectValueforOralNoteException, SQLException {
		
		char tmp = ' ';
		
		 /* on met l'autocommit de la connection sur faux */
		BDDconnection.getInstance().setAutoCommit(false);
		
		try {
			do {
			Scanner saisieClavier = new Scanner(System.in);
			List<Examen> examenList = new ArrayList<Examen>();
			Diplome d = new Diplome(examenList);
			
			/* m�thode creerdiplome va cr�er un nouveau dipl�me tout en r�cup�rant la clef du diplome en BDD et l'affecte � la variable numerodiplome qui servira � bien affecter en BDD 
			 * les nouveaux examens que va cr�er l'utilisateur au nouveau dipl�me que nous venons d'instancier */
			int numerodiplome = d.creerDiplome();
			Etudiant e = new Etudiant(d);
			e.sauvegarderEtudiant(numerodiplome);
			do {
				do {
					System.out.print("Quels types d'examens voulez-vous ajouter : ");
					System.out.print("Q (qcm) , P (projet), C (controle) :");
					tmp = saisieClavier.next().charAt(0);
				} while (tmp != 'P' && tmp != 'Q' && tmp != 'C');

				switch (tmp) {
				case 'C':
					//On enregistre en BDD les informations sur les contr�les de l'�tudiant saisi par l'utilisateur
					Controle cont = new Controle();
					cont.ajouterControle(numerodiplome);
					d.addExamen(cont);
					break;
				case 'Q':
					//On enregistre en BDD les informations sur les qcm de l'�tudiant saisi par l'utilisateur
					Qcm qcm = new Qcm();
					qcm.ajouterQcm(numerodiplome);
					d.addExamen(qcm);
					break;
				case 'P':
					//On enregistre en BDD les informations sur les projets de l'�tudiant saisi par l'utilisateur
					Projet projet = new Projet();
					projet.ajouterProjet(numerodiplome);
					d.addExamen(projet);
					break;
				}
				do {
				System.out.println("Voulez-vous rajouter un nouveau examen?");
				System.out.println(" 'O' pour oui  / 'N' pour non");
				tmp = saisieClavier.next().charAt(0);
				} while (tmp != 'O' && tmp != 'N');
			} while (tmp == 'O');
			
			do {
				System.out.println("Voulez-vous rajouter un nouvel �tudiant ?");
				System.out.println(" 'O' pour oui  / 'N' pour non");
				tmp = saisieClavier.next().charAt(0);
			} while (tmp != 'O' && tmp != 'N');
		} while (tmp == 'O');
			
			System.out.println("L'insertion des donn�es a �t� valid�e !");
			System.out.println("Merci d'avoir utilis� notre application dernier cri !!");
			System.out.println("Au revoir et � bientot");
			BDDconnection.getInstance().commit();
			
			if (BDDconnection.getInstance() != null) {
				BDDconnection.getInstance().close();
			}
			System.exit(0);	
			
		} catch (Exception e) {
			// Sinon, on efface les nouvelles entr�es avant leur insertion dans la base de donn�e
				System.out.println("Error404! Veuillez recommencer car l'insertion des donn�es n'a pas pu �tre valid�e !");
				e.printStackTrace();
				BDDconnection.getInstance().rollback();
				  
				if (BDDconnection.getInstance() != null) {
					BDDconnection.getInstance().close();
				}
				System.exit(0);
			  }
		}
	}

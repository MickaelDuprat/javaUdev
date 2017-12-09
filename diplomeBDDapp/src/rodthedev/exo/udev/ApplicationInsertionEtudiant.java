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
			
			/* méthode creerdiplome va créer un nouveau diplôme tout en récupèrant la clef du diplome en BDD et l'affecte à la variable numerodiplome qui servira à bien affecter en BDD 
			 * les nouveaux examens que va créer l'utilisateur au nouveau diplôme que nous venons d'instancier */
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
					//On enregistre en BDD les informations sur les contrôles de l'étudiant saisi par l'utilisateur
					Controle cont = new Controle();
					cont.ajouterControle(numerodiplome);
					d.addExamen(cont);
					break;
				case 'Q':
					//On enregistre en BDD les informations sur les qcm de l'étudiant saisi par l'utilisateur
					Qcm qcm = new Qcm();
					qcm.ajouterQcm(numerodiplome);
					d.addExamen(qcm);
					break;
				case 'P':
					//On enregistre en BDD les informations sur les projets de l'étudiant saisi par l'utilisateur
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
				System.out.println("Voulez-vous rajouter un nouvel étudiant ?");
				System.out.println(" 'O' pour oui  / 'N' pour non");
				tmp = saisieClavier.next().charAt(0);
			} while (tmp != 'O' && tmp != 'N');
		} while (tmp == 'O');
			
			System.out.println("L'insertion des données a été validée !");
			System.out.println("Merci d'avoir utilisé notre application dernier cri !!");
			System.out.println("Au revoir et à bientot");
			BDDconnection.getInstance().commit();
			
			if (BDDconnection.getInstance() != null) {
				BDDconnection.getInstance().close();
			}
			System.exit(0);	
			
		} catch (Exception e) {
			// Sinon, on efface les nouvelles entrées avant leur insertion dans la base de donnée
				System.out.println("Error404! Veuillez recommencer car l'insertion des données n'a pas pu être validée !");
				e.printStackTrace();
				BDDconnection.getInstance().rollback();
				  
				if (BDDconnection.getInstance() != null) {
					BDDconnection.getInstance().close();
				}
				System.exit(0);
			  }
		}
	}

package rodthedev.exo.udev;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BDDconnection {
	//URL de connexion
	  private String url = "jdbc:mysql://localhost/exodiplomejava?useSSL=false";

	  //Nom du user
	  private String user = "root";

	  //Mot de passe de l'utilisateur
	  private String passwd = "root";

	  //Objet Connection
	  private static Connection connect;  

	  //Constructeur privé
	  private BDDconnection() throws SQLException{
	    	connect = DriverManager.getConnection(url, user, passwd);
	  }


	  //Méthode qui va nous retourner notre instance et la créer si elle n'existe pas
	   public static Connection getInstance() throws SQLException{
	    if(connect == null){
	      new BDDconnection();
	    }
	    return connect;   
	  }   
	}

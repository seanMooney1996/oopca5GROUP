package Databases.BusinessObjects;

/** OOP Feb 2022
 * This App demonstrates the use of a Data Access Object (DAO)
 * to separate Business logic from Database specific logic.
 * It uses Data Access Objects (DAOs),
 * Data Transfer Objects (DTOs), and  a DAO Interface to define
 * a contract between Business Objects and DAOs.
 *
 * "Use a Data Access Object (DAO) to abstract and encapsulate all
 * access to the data source. The DAO manages the connection with
 * the data source to obtain and store data" Ref: oracle.com
 *
 * Here, we use one DAO per database table.
 *
 * Use the SQL script "CreateUsers.sql" included with this project
 * to create the required MySQL user_database and User table.
 */

import Databases.DTOs.Movie;
import Databases.Daos.MovieDAOInterface;
import Databases.Daos.MySqlMovieDao;
import Databases.Exceptions.DaoException;

import java.util.List;
import java.util.Scanner;

public class App
{
    public static void main(String[] args) throws DaoException {

        runTheApplication();
//        UserDAOInterface IUserDao = new MySqlUserDao();  //"IUserDao" -> "I" stands for for
//
////        // Notice that the userDao reference is an Interface type.
////        // This allows for the use of different concrete implementations.
////        // e.g. we could replace the MySqlUserDao with an OracleUserDao
////        // (accessing an Oracle Database)
////        // without changing anything in the Interface.
////        // If the Interface doesn't change, then none of the
////        // code in this file that uses the interface needs to change.
////        // The 'contract' defined by the interface will not be broken.
////        // This means that this code is 'independent' of the code
////        // used to access the database. (Reduced coupling).
////
////        // The Business Objects require that all User DAOs implement
////        // the interface called "UserDaoInterface", as the code uses
////        // only references of the interface type to access the DAO methods.
//
//        try
//        {
//            System.out.println("\nCall findAllUsers()");
//            List<User> users = IUserDao.findAllUsers();     // call a method in the DAO
//
//            if( users.isEmpty() )
//                System.out.println("There are no Users");
//            else {
//                for (User user : users)
//                    System.out.println("User: " + user.toString());
//            }
//
//            // test dao - with username and password that we know are present in the database
//
//            Scanner userInput = new Scanner(System.in);
//            System.out.println("Input username to check");
//            String surnameCheckString = userInput.next();
//            List<User> usersSurnameCheck = IUserDao.findAllUsersLastNameContains(surnameCheckString);
//
//
//            Scanner userInput3 = new Scanner(System.in);
//            System.out.println("Add new  user");
////            preparedStatement.setString(1,firstname);
////            preparedStatement.setString(2,lastname);
////            preparedStatement.setString(3,username);
////            preparedStatement.setString(4,password);
//            System.out.println("Input firstname to add");
//            String firstname1 = userInput.next();
//
//            System.out.println("Input lastname to add");
//            String lastname1 = userInput.next();
//
//            System.out.println("Input username to add");
//            String username1 = userInput.next();
//
//            System.out.println("Input password to add");
//            String password1 = userInput.next();
//            User newUser =  IUserDao.registerUser(firstname1,lastname1,username1,password1);
//
//            System.out.println("New user");
//            System.out.println(newUser);
//
//            Scanner userInput2 = new Scanner(System.in);
//            System.out.println("Input userId to delete");
//            int userId = userInput.nextInt();
//            int deletedRows = IUserDao.deleteUserById(userId);
//
//            System.out.println("Deleted rows = "+deletedRows);
//
//
//            System.out.println("\nCall: findUserByUsernamePassword()");
//            String username = "smithj";
//            String password = "password";
//            User user = IUserDao.findUserByUsernamePassword(username, password);
//
//            if( usersSurnameCheck.isEmpty() )
//                System.out.println("There are no Users");
//            else {
//                for (User user1 : usersSurnameCheck)
//                    System.out.println("User: " + user1.toString());
//            }
//
//            if( user != null ) // null returned if userid and password not valid
//                System.out.println("User found: " + user);
//            else
//                System.out.println("Username with that password not found");
//
//            // test dao - with an invalid username (i.e. not in database)
//            username = "madmax";
//            password = "thunderdome";
//            user = IUserDao.findUserByUsernamePassword(username, password);
//            if(user != null)
//                System.out.println("Username: " + username + " was found: " + user);
//            else
//                System.out.println("Username: " + username + ", password: " + password +" is not valid.");
//        }
//        catch( DaoException e )
//        {
//            e.printStackTrace();
//        }
    }




    public static void runTheApplication() throws DaoException {
        System.out.println("Enter a number to run an action:");
        System.out.println("\t(1) Get All Movies");
        System.out.println("\t(2) Get Movies By Id");
        System.out.println("\t(3) Delete Movies By Id");
        System.out.println("\t(4) Add a player");
        System.out.println("\t(5) Update a player by Id");
        System.out.println("\t(6) Find players using filter");

        int choice = validInt();
        String message = "";
        MovieDAOInterface movieDao = new MySqlMovieDao();
        Scanner key = new Scanner (System.in);
        switch (choice){
            case 1:{
                message = "1";
                List<Movie> movies = movieDao.getAllMovies();
                System.out.println("movies: " + movies);
                break;
            }
            case 2:{
                message = "2";
                System.out.println("Please, enter movie name: ");
                String input = key.next();
                Movie usersMovie = movieDao.findMovieByName(input);
                System.out.println("Movie you searched: " + usersMovie.toString());
                break;
            }
            case 3:{
                message = "3";
                System.out.println("Please, enter movie name: ");
                String input = key.next();
                int numberOfDeletedRows = movieDao.deleteMovieByName(input);
                System.out.println("Number of rows you've deleted: " + numberOfDeletedRows);
                break;
            }
            case 4:{
                message = "4";

                System.out.println("Please, enter movie name: ");
                String name = key.next();
                System.out.println("Please, enter director name: ");
                String directorName = key.next();
                System.out.println("Please, enter movie genre: ");
                String genre = key.next();
                System.out.println("Please, enter movie studio: ");
                String studio = key.next();
                System.out.println("Please, enter movie year: ");
                int year = validInt();
                System.out.println("Please, enter movie box office gain: ");
                float boxOfficeGain = validFloat();

                Movie usersMovie = movieDao.addMovie(name, directorName, genre, studio, year, boxOfficeGain);
                System.out.println("Movie you searched: " + usersMovie.toString());
                break;
            }
            case 5:{
                System.out.println("5");
            };
            case 6:{
                System.out.println("6");
            };
        }
    }

    public static int validInt(){
        Scanner keyValid = new Scanner(System.in);
        boolean runWhile= true;
        int choice = 0;

        while(runWhile){
            System.out.println("\nEnter your choice:");

            if(keyValid.hasNextInt() ){
                choice = keyValid.nextInt();

                if(choice<7 && choice> 0){
                    runWhile= false;
                }else{
                    System.out.println("Please, enter a number between 1 and 7.");
                }
            }else{
                System.out.println("Please, enter an integer value.");
                keyValid.next();
            }
        }
        return choice;
    }

    public static float validFloat(){
        Scanner keyValid2 = new Scanner(System.in);
        boolean runWhile= true;
        float choice = 0;

        while(runWhile){
            System.out.println("\nEnter your choice:");

            if(keyValid2.hasNextFloat() ){
                choice = keyValid2.nextFloat();

                if(choice<7 && choice> 0){
                    runWhile= false;
                }else{
                    System.out.println("Please, enter a number between 1 and 7.");
                }
            }else{
                System.out.println("Please, enter an integer value.");
                keyValid2.nextFloat();
            }
        }
        return choice;
    }
}
package Server_Client;

import Databases.DTOs.Movie;
import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

//-- Main Author: Mariela Machuca Palmeros
//-- Edit: Sean Mooney
public class Client {
    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }

    public static void start() {
        try (
                Socket socket = new Socket("localhost", 1090);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            System.out.println("Client message: The Client is running and has connected to the server");
            Scanner consoleInput = new Scanner(System.in);

            while (true) {
                System.out.println("Select an option:");
                System.out.println("1. Add Movie");
                System.out.println("2. Get Poster List");
                System.out.println("3. Get Movie by ID");
                System.out.println("4. Get All Movies");
                System.out.println("5. Delete Movie");
                System.out.println("6. Quit");

                int choice = consoleInput.nextInt();
                consoleInput.nextLine();

                switch (choice) {
                    case 1: addMovie(out, in, consoleInput);
                        break;
                    case 2: sendPosterRequest(out, in);
                        break;
                    case 3: sendRequest(out, "getMovieByID", in);
                        break;
                    case 4: sendRequest(out, "getAllMovies", in);
                        break;
                    case 5: sendRequest(out, "deleteMovie", in);
                        break;
                    case 6: sendRequest(out, "quit", in);
                        System.out.println("Exiting client, but server may still be running.");
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        } catch (IOException e) {
            System.out.println("Client message: IOException: " + e);
        }
    }

    private static void addMovie(PrintWriter out, BufferedReader in, Scanner consoleInput) throws IOException {
        Gson gsonParser = new Gson();
        System.out.println("What is the name of the movie?");
        String movieName = consoleInput.nextLine();
        System.out.println("What is the name of the director?");
        String directorName = consoleInput.nextLine();
        System.out.println("What Genre is the movie?");
        String genre = consoleInput.nextLine();
        System.out.println("What is the name of the studio?");
        String studioName = consoleInput.nextLine();
        System.out.println("What year did the movie come out?");
        int year = consoleInput.nextInt();
        System.out.println("What was its box office gain?");
        float boxOffice = consoleInput.nextFloat();

        Movie newMovie = new Movie(movieName, directorName, genre, studioName, year, boxOffice);
        String jsonMovie = gsonParser.toJson(newMovie);

        sendRequest(out, "addMovie " + jsonMovie, in);
    }

    private static void sendRequest(PrintWriter out, String request, BufferedReader in) throws IOException {
        out.println(request);
        String response = in.readLine();
        System.out.println(response);
    }
    private static void sendPosterRequest(PrintWriter out, BufferedReader in) throws IOException {
        out.println("getPosterList");
        String response = in.readLine();
        System.out.println(response);
        System.out.println("Select an option from the poster list.");
        Scanner consoleInput = new Scanner(System.in);
        String posterNumber = consoleInput.next();
        out.println("getPosterImage" +posterNumber);
        System.out.println(response);
    }
}
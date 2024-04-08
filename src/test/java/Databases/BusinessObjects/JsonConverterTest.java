package Databases.BusinessObjects;

import Databases.DTOs.Movie;
import Databases.Daos.MovieDAOInterface;
import Databases.Daos.MySqlMovieDao;
import Databases.Exceptions.DaoException;
import com.google.gson.Gson;
import junit.framework.TestCase;

import java.io.ByteArrayOutputStream;
import Databases.Daos.MySqlMovieDao;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.mockito.Mockito.when;


//author: Mariela Machuca Palmeros
public class JsonConverterTest extends TestCase {
    @Mock
    private MovieDAOInterface movieDao;
    private final MySqlMovieDao mySqlMovieDao = new MySqlMovieDao();
    public void testConverteAllMoviesToJSON() throws DaoException {
        // Arrange
        Gson gsonParser = new Gson();
        List<Movie> mockMovies = new ArrayList<>();
        mockMovies.add(new Movie("Movie1"));
        mockMovies.add(new Movie("Movie2"));

        // Mocking behavior of MovieDao
        when(movieDao.getAllMovies()).thenReturn(mockMovies);

        // Redirecting System.out to capture output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Act
        try {
            JsonConverter jsonConverter = new JsonConverter();
            jsonConverter.converteAllMoviesToJSON();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        String expectedOutput = "All movies JSON: \n" + gsonParser.toJson(mockMovies) + "\n";

        // Assert
        assertEquals(expectedOutput, outContent.toString());

    }

    public void testConverteSingleToJSON() throws DaoException {

        //We'll ask the system to return the movie "Poor things" as a json using only its name

        Movie movie = mySqlMovieDao.findMovieByName("Dune: Part One");
        Movie newMovie = new Movie("Dune: Part One");
        assertEquals(movie, newMovie);
        /*
        Gson gsonParser = new Gson();
        Movie mockMovie = new Movie(3,"Poor Things", "Yorgos Lanthimos", "Comedy", "Searchlight Pictures", 2023, 104);
        String movieName = mockMovie.getMovieName();

        // Mocking behavior of MovieDao
        when(movieDao.findMovieByName(movieName)).thenReturn(mockMovie);

        // Redirecting System.out to capture output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Simulating user input
        String simulatedInput = movieName + "\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        try {
            JsonConverter jsonConverter = new JsonConverter();
            jsonConverter.converteSingleToJSON();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        System.out.println(outContent);

        String expectedOutput = "Single movie JSON: \n" + gsonParser.toJson(mockMovie);

        // Assert
        assertEquals(expectedOutput, outContent.toString());
*/
    }
}
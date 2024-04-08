package Databases.BusinessObjects;

import Databases.DTOs.Movie;
import Databases.Daos.MovieDAOInterface;
import Databases.Daos.MySqlMovieDao;
import Databases.Exceptions.DaoException;
import com.google.gson.Gson;
import junit.framework.TestCase;

import java.io.ByteArrayOutputStream;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.mockito.Mockito.when;


//author: Mariela Machuca Palmeros
public class JsonConverterTest extends TestCase {
    @Mock
    private MovieDAOInterface movieDao;


    public void testConverteSingleToJSON() throws DaoException {

        //We'll ask the system to return the movie "Poor things" as a json using only its name
        Gson gsonParser = new Gson();
        Movie mockMovie = new Movie("Poor Things", "Yorgos Lanthimos", "Comedy", "Searchlight Pictures", 2023, 104);
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

        String expectedOutput = "Single movie JSON: \n" + gsonParser.toJson(mockMovie);

        // Assert
        assertEquals(expectedOutput, outContent.toString());

    }
}
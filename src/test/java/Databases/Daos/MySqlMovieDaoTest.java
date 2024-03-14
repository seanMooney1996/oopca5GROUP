package Databases.Daos;

import Databases.DTOs.Movie;
import Databases.Exceptions.DaoException;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;


//-- Main Author: Sean Mooney
public class MySqlMovieDaoTest extends TestCase {
    private final MySqlMovieDao mySqlMovieDao = new MySqlMovieDao();
    

    public void testGetAllMovies() throws DaoException {
        List<Movie> movieList = new ArrayList<>();
        movieList =   mySqlMovieDao.getAllMovies();
        assertFalse(movieList.isEmpty());
    }

    public void testCreateMovie() throws DaoException {
       Movie m = new Movie("Dunkirk");
        Movie mReturn = mySqlMovieDao.createMovie(m);
        mySqlMovieDao.deleteMovieByName("Dunkirk"); // delete afterwards
        assertEquals(mReturn.getMovieName(),m.getMovieName());
    }

    public void testFindMovieByNameFindDune() throws DaoException {
        Movie movie = mySqlMovieDao.findMovieByName("Dune: Part One");
        Movie newMovie = new Movie("Dune: Part One");
        assertEquals(movie,newMovie);
    }


    public void testDeleteMovieByNameDeleteDune() throws DaoException {
        int numberOfDeletedRows = mySqlMovieDao.deleteMovieByName("Dune: Part One");
        System.out.println(numberOfDeletedRows);
        // to repopulate db after using test!!!
        mySqlMovieDao.addMovie("Dune: Part One","Denis Villenueve","Sci-Fi","Warner Bros. Picture",2021,434.8f);
        assertTrue(numberOfDeletedRows >0);
    }

    public void testDeleteMovieByNameDeleteReturn0() throws DaoException {
        int numberOfDeletedRows = mySqlMovieDao.deleteMovieByName("Movie doesn't exit");
        System.out.println(numberOfDeletedRows);
        assertEquals(0,numberOfDeletedRows);
    }

    public void testAddMovie() throws DaoException {
        Movie movie = mySqlMovieDao.addMovie("Dune: Part One","Denis Villenueve","Sci-Fi","Warner Bros. Picture",2021,434.8f);
        Movie newMovie = new Movie("Dune: Part One");
        mySqlMovieDao.deleteMovieByName("Dune: Part One");
        assertEquals(movie,newMovie);
    }
}
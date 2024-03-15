package Databases.Daos;

import Databases.DTOs.Movie;
import Databases.Exceptions.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//-- Main Author: Sean Mooney
public class MySqlMovieDao extends MySqlDao implements MovieDAOInterface {

    @Override
    public Movie updateMovie(int id, Movie movie) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        Movie updatedMovie = null;

        try {
            connection = this.getConnection();
            String query = "UPDATE movies SET MOVIE_NAME = ?, DIRECTOR_NAME = ?," +
                    " GENRE = ?, STUDIO = ?, YEAR = ?, " +
                    "BOXOFFICE_GAIN = ? WHERE MOVIE_ID = ?";

            statement = connection.prepareStatement(query);
            statement.setString(1, movie.getMovieName());
            statement.setString(2, movie.getDirectorName());
            statement.setString(3, movie.getGenre());
            statement.setString(4, movie.getStudio());
            statement.setInt(5, movie.getYear());
            statement.setFloat(6, movie.getBoxOfficeGain());
            statement.setInt(7, id);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                updatedMovie = findMovieByName(movie.getMovieName());
            }
        } catch (SQLException e) {
            throw new DaoException(" updateMovie() Error updating movie: " + e.getMessage());
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return updatedMovie;
    }







    @Override
    public Movie createMovie(Movie movie) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        Movie createdMovie = null;

        try {
            connection = this.getConnection();
            String query = "INSERT INTO movies (MOVIE_NAME, DIRECTOR_NAME, GENRE, STUDIO, YEAR, BOXOFFICE_GAIN) VALUES (?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(query);
            statement.setString(1, movie.getMovieName());
            statement.setString(2, movie.getDirectorName());
            statement.setString(3, movie.getGenre());
            statement.setString(4, movie.getStudio());
            statement.setInt(5, movie.getYear());
            statement.setFloat(6, movie.getBoxOfficeGain());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                createdMovie = findMovieByName(movie.getMovieName());
            }
        } catch (SQLException e) {
            throw new DaoException(" createMovie() Error creating movie: " + e.getMessage());
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return createdMovie; // returns null if there is a failure in creating the entry
    }

    @Override
    public List<Movie> getAllMovies() throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Movie> movieList = new ArrayList<>();

        try {
            connection = this.getConnection();

            String query = "SELECT * FROM MOVIES";
            preparedStatement = connection.prepareStatement(query);

            //Using a PreparedStatement to execute SQL...
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int movieId = resultSet.getInt("MOVIE_ID");
                String movieName = resultSet.getString("MOVIE_NAME");
                String directorName = resultSet.getString("DIRECTOR_NAME");
                String genre = resultSet.getString("GENRE");
                String studio = resultSet.getString("STUDIO");
                int year = resultSet.getInt("YEAR");
                float boxOfficeGain = resultSet.getFloat("BOXOFFICE_GAIN");

                Movie m = new Movie(movieId, movieName, directorName, genre, studio, year, boxOfficeGain);
                movieList.add(m);
            }
        } catch (SQLException e) {
            throw new DaoException("findAllMovies() exception " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    freeConnection(connection);
                }
            } catch (SQLException e) {
                throw new DaoException("findAllUsers() " + e.getMessage());
            }
        }
        return movieList;     // may be empty
    }


    @Override
    public Movie findMovieByName(String name) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Movie movie = null;
        try {
            connection = this.getConnection();

            String query = "SELECT * FROM MOVIES WHERE MOVIE_NAME = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int movieId = resultSet.getInt("MOVIE_ID");
                String movieName = resultSet.getString("MOVIE_NAME");
                String directorName = resultSet.getString("DIRECTOR_NAME");
                String genre = resultSet.getString("GENRE");
                String studio = resultSet.getString("STUDIO");
                int year = resultSet.getInt("YEAR");
                float boxOfficeGain = resultSet.getFloat("BOXOFFICE_GAIN");

                movie = new Movie(movieId, movieName, directorName, genre, studio, year, boxOfficeGain);
            }
        } catch (SQLException e) {
            throw new DaoException("findUserByUsernamePassword() " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    freeConnection(connection);
                }
            } catch (SQLException e) {
                throw new DaoException("findMovieByName() " + e.getMessage());
            }
        }
        return movie;     // reference to User object, or null value
    }

    @Override
    public int deleteMovieByName(String name) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        int numberOfDeletedRows = -1;
        try {
            //Get connection object using the getConnection() method inherited
            // from the super class (MySqlDao.java)
            connection = this.getConnection();

            String query = "DELETE FROM MOVIES WHERE MOVIE_NAME = ? ";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);

            //Using a PreparedStatement to execute SQL...
            numberOfDeletedRows = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("deleteMovieByName() " + e.getMessage());
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    freeConnection(connection);
                }
            } catch (SQLException e) {
                throw new DaoException("findAllUsers() " + e.getMessage());
            }
        }
        return numberOfDeletedRows;
    }

}

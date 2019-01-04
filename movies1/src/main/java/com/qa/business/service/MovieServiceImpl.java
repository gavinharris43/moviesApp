package com.qa.business.service;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.qa.persistence.domain.Movie;
import com.qa.persistence.repository.MovieRepository;

public class MovieServiceImpl implements MovieService {

	@Inject
	private MovieRepository repo;

	public String getAllMovies() {
		return repo.getAllMovies();
	}

//	@Override
	public String addMovie(String movie) {
		String movieName= new Gson().fromJson(movie, Movie.class).getName();
		String[] banned = {"titanic","greenland","motherfucher","Mortal Engines"};
		for (String i: banned) {
			if (movieName.equals(i)){
			return "{\"message\": \"We don't like "+movieName+", Movie Not Added.\"}";
		} 
		}
		
		return repo.createMovie(movie);
	}

//	@Override
	public String deleteMovie(Long id) {
		return repo.deleteMovie(id);
	}

	public void setRepo(MovieRepository repo) {
		this.repo = repo;
	}

}

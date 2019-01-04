package com.qa.persistence.repository;

import static javax.transaction.Transactional.TxType.REQUIRED;
import static javax.transaction.Transactional.TxType.SUPPORTS;

import java.util.Collection;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;

import com.qa.persistence.domain.Movie;
import com.qa.business.service.MovieService;
import com.qa.util.JSONUtil;

@Transactional(SUPPORTS)
@Default
public class MovieDBRepository implements MovieRepository {
	
	@PersistenceContext(unitName = "primary")
	private EntityManager manager;
	
	@Inject
	private JSONUtil util;
	
//	@Override
	public String getAllMovies() {
		Query query = manager.createQuery("Select m FROM Movie m");
		Collection<Movie> movies = (Collection<Movie>) query.getResultList();
		return util.getJSONForObject(movies);
	}
	
//	@Override
	@Transactional(REQUIRED)
	public String createMovie(String movie) {
		Movie anMovie = util.getObjectForJSON(movie, Movie.class);
		manager.persist(anMovie);
		return "{\"message\": \"movie has been sucessfully added\"}";
	}
	
//	@Override
	@Transactional(REQUIRED)
	public String deleteMovie(Long id) {
		Movie movieInDB = findMovie(id);
		if (movieInDB != null) {
			manager.remove(movieInDB);
		}
		return "{\"message\": \"movie sucessfully deleted\"}";
	}

	private Movie findMovie(Long id) {
		return manager.find(Movie.class, id);
	}

	public void setManager(EntityManager manager) {
		this.manager = manager;
	}

	public void setUtil(JSONUtil util) {
		this.util = util;
	}


}

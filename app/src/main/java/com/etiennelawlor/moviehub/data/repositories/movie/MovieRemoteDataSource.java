package com.etiennelawlor.moviehub.data.repositories.movie;

import android.text.TextUtils;

import com.etiennelawlor.moviehub.data.network.MovieHubService;
import com.etiennelawlor.moviehub.data.network.response.Movie;
import com.etiennelawlor.moviehub.data.network.response.MovieCredit;
import com.etiennelawlor.moviehub.data.network.response.MovieReleaseDate;
import com.etiennelawlor.moviehub.data.network.response.MovieReleaseDateEnvelope;
import com.etiennelawlor.moviehub.data.network.response.MoviesEnvelope;
import com.etiennelawlor.moviehub.data.repositories.movie.models.MovieDetailsWrapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by etiennelawlor on 2/13/17.
 */

public class MovieRemoteDataSource implements MovieDataSourceContract.RemoteDateSource {

    // region Constants
    private static final String ISO_31661 = "US";
    // endregion

    // region Member Variables
    private MovieHubService movieHubService;
    // endregion

    // region Constructors
    @Inject
    public MovieRemoteDataSource(MovieHubService movieHubService) {
        this.movieHubService = movieHubService;
    }
    // endregion

    // region MovieDataSourceContract.RemoteDateSource Methods
    @Override
    public Single<MoviesEnvelope> getPopularMovies(final int currentPage) {
        return movieHubService.getPopularMovies(currentPage);
    }

    // one or more repositor(ies) -> map response to single domain model
    // MovieDetailsWrapper to MovieDetailsDomainModel

    @Override
    public Single<MovieDetailsWrapper> getMovieDetails(int movieId) {
        return Single.zip(
                movieHubService.getMovie(movieId),
                movieHubService.getMovieCredits(movieId),
                movieHubService.getSimilarMovies(movieId),
                movieHubService.getMovieReleaseDates(movieId),
                (movie, movieCreditsEnvelope, moviesEnvelope, movieReleaseDatesEnvelope) -> {
                    List<MovieCredit> cast = new ArrayList<>();
                    List<MovieCredit> crew = new ArrayList<>();
                    List<Movie> similarMovies = new ArrayList<>();
                    String rating = "";

                    if (movieCreditsEnvelope != null) {
                        cast = movieCreditsEnvelope.getCast();
                    }

                    if (movieCreditsEnvelope != null) {
                        crew = movieCreditsEnvelope.getCrew();
                    }

                    if (moviesEnvelope != null) {
                        similarMovies = moviesEnvelope.getMovies();
                    }

                    if (movieReleaseDatesEnvelope != null) {
                        List<MovieReleaseDateEnvelope> movieReleaseDateEnvelopes = movieReleaseDatesEnvelope.getMovieReleaseDateEnvelopes();
                        if (movieReleaseDateEnvelopes != null && movieReleaseDateEnvelopes.size() > 0) {
                            for (MovieReleaseDateEnvelope movieReleaseDateEnvelope : movieReleaseDateEnvelopes) {
                                if (movieReleaseDateEnvelope != null) {
                                    String iso31661 = movieReleaseDateEnvelope.getIso31661();
                                    if (iso31661.equals(ISO_31661)) {
                                        List<MovieReleaseDate> movieReleaseDates = movieReleaseDateEnvelope.getMovieReleaseDates();
                                        if (movieReleaseDates != null && movieReleaseDates.size() > 0) {
                                            for (MovieReleaseDate movieReleaseDate : movieReleaseDates) {
                                                if (!TextUtils.isEmpty(movieReleaseDate.getCertification())) {
                                                    rating = movieReleaseDate.getCertification();
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    return new MovieDetailsWrapper(movie, cast, crew, similarMovies, rating);
                });
    }
    // endregion
}

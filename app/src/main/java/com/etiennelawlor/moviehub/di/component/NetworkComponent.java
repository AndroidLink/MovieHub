package com.etiennelawlor.moviehub.di.component;

import com.etiennelawlor.moviehub.data.repositories.movie.MovieRemoteDataSource;
import com.etiennelawlor.moviehub.data.repositories.person.PersonRemoteDataSource;
import com.etiennelawlor.moviehub.data.repositories.tv.TelevisionShowRemoteDataSource;
import com.etiennelawlor.moviehub.di.module.NetworkModule;
import com.etiennelawlor.moviehub.di.scope.NetworkScope;

import dagger.Subcomponent;

/**
 * Created by etiennelawlor on 2/9/17.
 */

@NetworkScope
@Subcomponent(modules = {NetworkModule.class})
public interface NetworkComponent {
    void inject(MovieRemoteDataSource target);
    void inject(TelevisionShowRemoteDataSource target);
    void inject(PersonRemoteDataSource target);

}

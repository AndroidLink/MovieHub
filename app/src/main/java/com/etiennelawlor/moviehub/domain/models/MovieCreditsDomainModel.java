package com.etiennelawlor.moviehub.domain.models;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by etiennelawlor on 12/30/17.
 */

public class MovieCreditsDomainModel {

    // region Fields
    public int id;
    public List<MovieCreditDomainModel> cast = null;
    public List<MovieCreditDomainModel> crew = null;
    private Date expiredAt;
    // endregion

    // region Constructors
    public MovieCreditsDomainModel(int id, List<MovieCreditDomainModel> cast, List<MovieCreditDomainModel> crew, Date expiredAt) {
        this.id = id;
        this.cast = cast;
        this.crew = crew;
        this.expiredAt = expiredAt;
    }

    public MovieCreditsDomainModel() {
    }
    // endregion

    // region Getters

    public int getId() {
        return id;
    }

    public List<MovieCreditDomainModel> getCast() {
        return cast;
    }

    public List<MovieCreditDomainModel> getCrew() {
        return crew;
    }

    public Date getExpiredAt() {
        return expiredAt;
    }

    // endregion

    // region Setters

    public void setId(int id) {
        this.id = id;
    }

    public void setCast(List<MovieCreditDomainModel> cast) {
        this.cast = cast;
    }

    public void setCrew(List<MovieCreditDomainModel> crew) {
        this.crew = crew;
    }

    public void setExpiredAt(Date expiredAt) {
        this.expiredAt = expiredAt;
    }

    // endregion

    // Helper Methods
    public boolean isExpired() {
        return Calendar.getInstance().getTime().getTime() > expiredAt.getTime();
    }
    // endregion

    @Override
    public String toString() {
        return "MovieCreditsPresentationModel{" +
                "id=" + id +
                ", cast=" + cast +
                ", crew=" + crew +
                ", expiredAt=" + expiredAt +
                '}';
    }
}

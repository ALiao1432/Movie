package study.ian.movie.model.people.credit.movie;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.util.List;

class Crew {

    @SerializedName("id")
    private int id;

    @SerializedName("department")
    private String department;

    @SerializedName("original_language")
    private String original_language;

    @SerializedName("original_title")
    private String original_title;

    @SerializedName("job")
    private String job;

    @SerializedName("overview")
    private String overview;

    @SerializedName("vote_count")
    private int vote_count;

    @SerializedName("video")
    private boolean video;

    @Nullable
    @SerializedName("poster_path")
    private String poster_path;

    @Nullable
    @SerializedName("backdrop_path")
    private String backdrop_path;

    @SerializedName("title")
    private String title;

    @SerializedName("popularity")
    private double popularity;

    @SerializedName("genre_ids")
    private List<Integer> genre_ids;

    @SerializedName("vote_average")
    private double vote_average;

    @SerializedName("adult")
    private boolean adult;

    @SerializedName("release_date")
    private String release_date;

    @SerializedName("credit_id")
    private String credit_id;

    public int getId() {
        return id;
    }

    public String getDepartment() {
        return department;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getJob() {
        return job;
    }

    public String getOverview() {
        return overview;
    }

    public int getVote_count() {
        return vote_count;
    }

    public boolean isVideo() {
        return video;
    }

    @Nullable
    public String getPoster_path() {
        return poster_path;
    }

    @Nullable
    public String getBackdrop_path() {
        return backdrop_path;
    }

    public String getTitle() {
        return title;
    }

    public double getPopularity() {
        return popularity;
    }

    public List<Integer> getGenre_ids() {
        return genre_ids;
    }

    public double getVote_average() {
        return vote_average;
    }

    public boolean isAdult() {
        return adult;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getCredit_id() {
        return credit_id;
    }

    @NotNull
    @Override
    public String toString() {
        return "Crew{" +
                "id=" + id +
                ", department='" + department + '\'' +
                ", original_language='" + original_language + '\'' +
                ", original_title='" + original_title + '\'' +
                ", job='" + job + '\'' +
                ", overview='" + overview + '\'' +
                ", vote_count=" + vote_count +
                ", video=" + video +
                ", poster_path='" + poster_path + '\'' +
                ", backdrop_path='" + backdrop_path + '\'' +
                ", title='" + title + '\'' +
                ", popularity=" + popularity +
                ", genre_ids=" + genre_ids +
                ", vote_average=" + vote_average +
                ", adult=" + adult +
                ", release_date='" + release_date + '\'' +
                ", credit_id='" + credit_id + '\'' +
                '}';
    }

}

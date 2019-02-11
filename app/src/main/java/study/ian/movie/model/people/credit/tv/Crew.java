package study.ian.movie.model.people.credit.tv;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Crew {

    @SerializedName("id")
    private int id;

    @SerializedName("department")
    private String department;

    @SerializedName("original_language")
    private String original_language;

    @SerializedName("episode_count")
    private int episode_count;

    @SerializedName("job")
    private String job;

    @SerializedName("overview")
    private String overview;

    @SerializedName("origin_country")
    private List<String> origin_country;

    @SerializedName("original_name")
    private String original_name;

    @SerializedName("genre_ids")
    private List<Integer> genre_ids;

    @SerializedName("name")
    private String name;

    @SerializedName("first_air_date")
    private String first_air_date;

    @Nullable
    @SerializedName("backdrop_path")
    private String backdrop_path;

    @SerializedName("popularity")
    private double popularity;

    @SerializedName("vote_count")
    private int vote_count;

    @SerializedName("vote_average")
    private double vote_average;

    @Nullable
    @SerializedName("poster_path")
    private String poster_path;

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

    public int getEpisode_count() {
        return episode_count;
    }

    public String getJob() {
        return job;
    }

    public String getOverview() {
        return overview;
    }

    public List<String> getOrigin_country() {
        return origin_country;
    }

    public String getOriginal_name() {
        return original_name;
    }

    public List<Integer> getGenre_ids() {
        return genre_ids;
    }

    public String getName() {
        return name;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    @Nullable
    public String getBackdrop_path() {
        return backdrop_path;
    }

    public double getPopularity() {
        return popularity;
    }

    public int getVote_count() {
        return vote_count;
    }

    public double getVote_average() {
        return vote_average;
    }

    @Nullable
    public String getPoster_path() {
        return poster_path;
    }

    public String getCredit_id() {
        return credit_id;
    }

    @Override
    public String toString() {
        return "Crew{" +
                "id=" + id +
                ", department='" + department + '\'' +
                ", original_language='" + original_language + '\'' +
                ", episode_count=" + episode_count +
                ", job='" + job + '\'' +
                ", overview='" + overview + '\'' +
                ", origin_country=" + origin_country +
                ", original_name='" + original_name + '\'' +
                ", genre_ids=" + genre_ids +
                ", name='" + name + '\'' +
                ", first_air_date='" + first_air_date + '\'' +
                ", backdrop_path='" + backdrop_path + '\'' +
                ", popularity=" + popularity +
                ", vote_count=" + vote_count +
                ", vote_average=" + vote_average +
                ", poster_path='" + poster_path + '\'' +
                ", credit_id='" + credit_id + '\'' +
                '}';
    }
}

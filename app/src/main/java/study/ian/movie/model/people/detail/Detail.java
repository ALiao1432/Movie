package study.ian.movie.model.people.detail;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Detail {

    @Nullable
    @SerializedName("birthday")
    private String birthday;

    @SerializedName("known_for_department")
    private String known_for_department;

    @Nullable
    @SerializedName("deathday")
    private String deathday;

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("also_known_as")
    private List<String> also_known_as;

    @SerializedName("gender")
    private int gender;

    @SerializedName("biography")
    private String biography;

    @SerializedName("popularity")
    private double popularity;

    @Nullable
    @SerializedName("place_of_birth")
    private String place_of_birth;

    @Nullable
    @SerializedName("profile_path")
    private String profile_path;

    @SerializedName("adult")
    private boolean adult;

    @SerializedName("imdb_id")
    private String imdb_id;

    @Nullable
    @SerializedName("homepage")
    private String homepage;

    @Nullable
    public String getBirthday() {
        return birthday;
    }

    public String getKnown_for_department() {
        return known_for_department;
    }

    @Nullable
    public String getDeathday() {
        return deathday;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getAlso_known_as() {
        return also_known_as;
    }

    public int getGender() {
        return gender;
    }

    public String getBiography() {
        return biography;
    }

    public double getPopularity() {
        return popularity;
    }

    @Nullable
    public String getPlace_of_birth() {
        return place_of_birth;
    }

    @Nullable
    public String getProfile_path() {
        return profile_path;
    }

    public boolean isAdult() {
        return adult;
    }

    public String getImdb_id() {
        return imdb_id;
    }

    @Nullable
    public String getHomepage() {
        return homepage;
    }

    @Override
    public String toString() {
        return "Detail{" +
                "birthday='" + birthday + '\'' +
                ", known_for_department='" + known_for_department + '\'' +
                ", deathday='" + deathday + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", also_known_as=" + also_known_as +
                ", gender=" + gender +
                ", biography='" + biography + '\'' +
                ", popularity=" + popularity +
                ", place_of_birth='" + place_of_birth + '\'' +
                ", profile_path='" + profile_path + '\'' +
                ", adult=" + adult +
                ", imdb_id='" + imdb_id + '\'' +
                ", homepage='" + homepage + '\'' +
                '}';
    }
}

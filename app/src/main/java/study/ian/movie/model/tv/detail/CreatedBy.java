package study.ian.movie.model.tv.detail;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

class CreatedBy {

    @SerializedName("id")
    private int id;

    @SerializedName("credit_id")
    private String credit_id;

    @SerializedName("name")
    private String name;

    @SerializedName("gender")
    private int gender;

    @SerializedName("profile_path")
    private String profile_path;

    public int getId() {
        return id;
    }

    public String getCredit_id() {
        return credit_id;
    }

    public String getName() {
        return name;
    }

    public int getGender() {
        return gender;
    }

    public String getProfile_path() {
        return profile_path;
    }

    @NotNull
    @Override
    public String toString() {
        return "CreatedBy{" +
                "id=" + id +
                ", credit_id='" + credit_id + '\'' +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", profile_path='" + profile_path + '\'' +
                '}';
    }
}

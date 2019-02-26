package study.ian.movie.model.movie.credit;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

class Crew {

    @SerializedName("credit_id")
    private String credit_id;

    @SerializedName("department")
    private String department;

    @Nullable
    @SerializedName("gender")
    private Integer gender;

    @SerializedName("id")
    private int id;

    @SerializedName("job")
    private String job;

    @SerializedName("name")
    private String name;

    @Nullable
    @SerializedName("profile_path")
    private String profile_path;

    public String getCredit_id() {
        return credit_id;
    }

    public String getDepartment() {
        return department;
    }

    @Nullable
    public Integer getGender() {
        return gender;
    }

    public int getId() {
        return id;
    }

    public String getJob() {
        return job;
    }

    public String getName() {
        return name;
    }

    @Nullable
    public String getProfile_path() {
        return profile_path;
    }
}

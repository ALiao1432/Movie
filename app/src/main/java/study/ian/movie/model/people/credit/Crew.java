package study.ian.movie.model.people.credit;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class Crew {

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

    public void setCredit_id(String credit_id) {
        this.credit_id = credit_id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Nullable
    public Integer getGender() {
        return gender;
    }

    public void setGender(@Nullable Integer gender) {
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Nullable
    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(@Nullable String profile_path) {
        this.profile_path = profile_path;
    }
}

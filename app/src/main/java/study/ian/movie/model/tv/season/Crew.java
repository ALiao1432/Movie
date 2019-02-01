package study.ian.movie.model.tv.season;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class Crew {

    @SerializedName("id")
    private int id;

    @SerializedName("credit_id")
    private String credit_id;

    @SerializedName("name")
    private String name;

    @SerializedName("department")
    private String department;

    @SerializedName("job")
    private String job;

    @Nullable
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

    public String getDepartment() {
        return department;
    }

    public String getJob() {
        return job;
    }

    @Nullable
    public String getProfile_path() {
        return profile_path;
    }

    @Override
    public String toString() {
        return "Crew{" +
                "id=" + id +
                ", credit_id='" + credit_id + '\'' +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", job='" + job + '\'' +
                ", profile_path='" + profile_path + '\'' +
                '}';
    }
}

package study.ian.movie.model.people.tv.credit;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class Cast {

    @SerializedName("character")
    private String character;

    @SerializedName("credit_id")
    private String credit_id;

    @Nullable
    @SerializedName("gender")
    private Integer gender;

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("order")
    private int order;

    @Nullable
    @SerializedName("profile_path")
    private String profile_path;

    public String getCharacter() {
        return character;
    }

    public String getCredit_id() {
        return credit_id;
    }

    @Nullable
    public Integer getGender() {
        return gender;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getOrder() {
        return order;
    }

    @Nullable
    public String getProfile_path() {
        return profile_path;
    }

    @Override
    public String toString() {
        return "Cast{" +
                ", character='" + character + '\'' +
                ", credit_id='" + credit_id + '\'' +
                ", gender=" + gender +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", order=" + order +
                ", profile_path='" + profile_path + '\'' +
                '}';
    }
}

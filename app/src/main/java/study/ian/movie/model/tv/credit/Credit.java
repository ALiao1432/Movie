package study.ian.movie.model.tv.credit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Credit {

    @SerializedName("id")
    private int id;

    @SerializedName("cast")
    private List<Cast> cast;

    @SerializedName("crew")
    private List<Crew> crew;

    public int getId() {
        return id;
    }

    public List<Cast> getCast() {
        return cast;
    }

    public List<Crew> getCrew() {
        return crew;
    }

    @Override
    public String toString() {
        return "Credit{" +
                "id=" + id +
                ", cast=" + cast +
                ", crew=" + crew +
                '}';
    }
}

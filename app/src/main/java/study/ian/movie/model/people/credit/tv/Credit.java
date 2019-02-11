package study.ian.movie.model.people.credit.tv;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Credit {

    @SerializedName("cast")
    private List<study.ian.movie.model.people.credit.tv.Cast> Cast;

    @SerializedName("crew")
    private List<study.ian.movie.model.people.credit.tv.Crew> Crew;

    @SerializedName("id")
    private int id;

    public List<study.ian.movie.model.people.credit.tv.Cast> getCast() {
        return Cast;
    }

    public List<study.ian.movie.model.people.credit.tv.Crew> getCrew() {
        return Crew;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Credit{" +
                "Cast=" + Cast +
                ", Crew=" + Crew +
                ", id=" + id +
                '}';
    }
}

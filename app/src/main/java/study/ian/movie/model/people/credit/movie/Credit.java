package study.ian.movie.model.people.credit.movie;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Credit {

    @SerializedName("cast")
    private List<Cast> Cast;

    @SerializedName("crew")
    private List<Crew> Crew;

    @SerializedName("id")
    private int id;

    public List<study.ian.movie.model.people.credit.movie.Cast> getCast() {
        return Cast;
    }

    public List<study.ian.movie.model.people.credit.movie.Crew> getCrew() {
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

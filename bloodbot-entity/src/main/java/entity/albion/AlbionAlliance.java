package entity.albion;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AlbionAlliance {

    @SerializedName("AllianceId")
    private String id;

    @SerializedName("AllianceName")
    private String name;

    @SerializedName("AllianceTag")
    private String tag;

    @SerializedName("Founded")
    private Timestamp founded;

    @SerializedName("Guilds")
    private List<AlbionGuild> guilds = new ArrayList<>();

    @SerializedName("NumPlayers")
    private Integer playerCount;

}

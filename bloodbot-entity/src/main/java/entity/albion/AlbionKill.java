package entity.albion;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;
import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AlbionKill {

    @SerializedName("EventId")
    private long id;

    @SerializedName("numberOfParticipants")
    private long numberOfParticipants;

    @SerializedName("Killer")
    private AlbionPlayer killer;

    @SerializedName("Victim")
    private AlbionPlayer victim;

    @SerializedName("Participants")
    private List<AlbionPlayer> participants;

    @SerializedName("TimeStamp")
    private Instant timestamp;

}

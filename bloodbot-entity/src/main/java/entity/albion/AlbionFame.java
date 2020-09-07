package entity.albion;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AlbionFame {

    @SerializedName("Total")
    private long total;

    @SerializedName("Royal")
    private long royal;

    @SerializedName("Outlands")
    private long outlands;

}

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
public class AlbionEquipment {

    @SerializedName("MainHand")
    private AlbionItem mainHand;

    @SerializedName("OffHand")
    private AlbionItem offHand;

    @SerializedName("Head")
    private AlbionItem head;

    @SerializedName("Armor")
    private AlbionItem armor;

    @SerializedName("Shoes")
    private AlbionItem shoes;

    @SerializedName("Bag")
    private AlbionItem bag;

    @SerializedName("Cape")
    private AlbionItem cape;

    @SerializedName("Mount")
    private AlbionItem mount;

    @SerializedName("Potion")
    private AlbionItem potion;

    @SerializedName("Food")
    private AlbionItem food;

}

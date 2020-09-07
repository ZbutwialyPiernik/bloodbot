package entity.translation;

import lombok.Getter;

@Getter
public class Language {

    private String isoCode = "EN";

    // KILLBOARD FILTER

    private String showKills = "Show kills";
    private String showDeaths = "Show deaths";
    private String minimumFame = "Minimum Fame";

    private String enterMinimumFame = "Enter minimum amount of fame, maximum limit is 1,000,000";
    private String errorHigherThanLimit = "Parameter minimum fame cannot be higher than 1,000,000";
    private String errorNegative = "Parameter minimum fame cannot be negative";
    private String errorWrongInput = "Provided input is not number or is out of range";

    // MEMBERSHIP NOTIFICATION

    private String joinedTheGuild = "%s joined the guild %s";
    private String leftTheGuild = "%s left the guild %s";
    private String membersInGuild = "There are now %s members in the guild";

    // BATTLE NOTIFICATION

    private String totalPlayers = "Total of %d players";
    private String fame = "Fame";
    private String bestGuilds = "Best Guilds";
    private String bestPlayers = "Best Players";
    private String kills = "Kills";
    private String deaths = "Deaths";
    private String alliances = "Alliances";

    // KILL NOTIFICATION

    private String name = "Name";
    private String killed = "killed";
    private String killerGuild = "Killer Guild";
    private String victimGuild = "Victim Guild";
    private String guild = "Guild";
    private String killerFame = "Killer Fame";
    private String totalFame = "Total Fame";
    private String founderName = "Founder name";
    private String founderTime = "Founded time";
    private String membersCount = "Members count";

    private String soloKill = "Solo Kill!";
    private String killerItemPower = "Killer Item Power";
    private String victimItemPower = "Victim Item Power";
    private String player = "Player";
    private String alliance = "Alliance";
    private String killFame = "Kill Fame";
    private String deathFame = "Death Fame";
    private String pageOf = "Page %s of %s";
    private String assistedBy = "Assisted by %d player(s)";

    // SHARED

    private String yes = "Yes";
    private String no = "No";

    private String players = "Players";
    private String guilds = "Guilds";
    private String battlesOfGuilds = "Battles of guilds";
    private String battlesOfAlliances = "Battles of alliances";
    private String membershipGuilds = "Membership guilds";
    private String membershipAlliances = "Membership alliances";

    private String information = "Information";
    private String error = "Error";
    private String gatheringFame = "Gathering Fame";
    private String craftingFame = "Crafting Fame";
    private String fameRatio = "Fame Ratio";
    private String pveFame = "PVE Fame";

    private String playerFilterAdded = "A new filter has been added for player %s";
    private String playerFilterRemoved = "Filter associated with player %s has been removed";
    private String playerHasNoFilter = "There is no filter associated with player %s";
    private String playerHasFilterAlready = "There is already a filter associated with player %s";

    private String guildFilterAdded = "A new filter has been added for guild %s";
    private String guildFilterRemoved = "Filter associated with guild %s has been removed";
    private String guildHasNoFilter = "There is no filter associated with guild %s";
    private String guildHasFilterAlready = "There is already a filter associated with guild %s";

    private String allianceFilterAdded = "A new filter has been added for alliance %s";
    private String allianceFilterRemoved = "Filter associated with alliance %s has been removed";
    private String allianceHasNoFilter = "There is no filter associated with alliance %s";
    private String allianceHasFilterAlready = "There is already a filter associated with alliance %s";

}

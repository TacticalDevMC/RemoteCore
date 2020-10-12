<a href="https://tacticaldevmc.me"><h1>TacticalDevMC</h1></a>
<img width="200px" align="right" src="https://i.imgur.com/p90vMhe.png">
<h3>Portfolio ───────</h3>

| RemoteCore API | | |
| --- | --- | --- |
<br>Gebruik van de RemoteCore-API.<br>
Voor meer informatie zie [Waarom RemoteCore?](https://github.com/TacticalDevMC/RemoteCore/wiki/Why-RemoteCore)
# RemoteCore-API
Wat doet de RemoteCore-API? Nou, de API zorgt ervoor dat jij in jou eigen plugin dingen van RemoteCore kunt gebruiken.<br>

| Functie                            | Methode      |
| :--------------------------------- | :----------- |
| getPlayer                          | RemoteCommon.getPlayer(player);|
| getOfflinePlayer                   | RemoteCommon.getPlayer(offlinePlayer);|
| setRank                            | RemoteCommon.getPlayer(player).setRank(rank);|
| Setprefix                          | RemoteCommon.getPlayer(player).Setprefix(prefix);|
| setSuffix                          | RemoteCommon.getPlayer(player).setPrefix(suffix);|
| getRank                            | RemoteCommon.getPlayer(player).getRank();|
| getPrefix                          | RemoteCommon.getPlayer(player).getPrefix();|
| getSuffix                          | RemoteCommon.getPlayer(player).getSuffix();|

<br><br>
##Initializing API
```java
public class InitAPI {
    private RemoteAPI api;

    public void initAPI() {
        if (api.isInit()) {
            LogHandler.getHandler().logMessage("The api is already initialized.");
            return;
        }

        api = new RemoteAPI(RemoteCoreSpigot.getInstance());

        api.init();
    }

    public RemoteAPI getAPI() {
        if (!api.isInit()) {
            LogHandler.getHandler().logMessage("The api is'nt initialized.");
            return null;
        }
        return api;
    }
}
```
[![Discord](https://img.shields.io/discord/725988308752007169.svg?logo=discord&color=red&label=Discord)](https://discord.gg/JATdrn7)

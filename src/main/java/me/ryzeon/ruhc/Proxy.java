package me.ryzeon.ruhc;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

public class Proxy extends Plugin implements Listener {
    public void onEnable() {
        getProxy().registerChannel("rUHC");
        getProxy().getPluginManager().registerListener(this, this);
        getLogger().info("§e+-------------------------+");
        getLogger().info("§erUHCProxy §7| §aEnabled");
        getLogger().info("§eAuthor: §fRyzeon");
        getLogger().info("§e+-------------------------+");
    }

    @EventHandler
    public void onPluginMessageEvent(PluginMessageEvent event) {
        String message, broadcast;
        if (!event.getTag().equals("rUHC"))
            return;
        Server server = (Server)event.getSender();
        ByteArrayInputStream stream = new ByteArrayInputStream(event.getData());
        DataInputStream in = new DataInputStream(stream);
        try {
            message = in.readUTF();
        } catch (IOException e) {
            getLogger().severe("Couldn't read message");
            return;
        }
        String[] strings = message.split(";");
        String alert = strings[0];
        int seconds = Integer.parseInt(strings[1]);
        int minutes = seconds / 60;
        String s = (seconds > 60) ? (minutes + " §aminutes") : (seconds + " §aseconds");
            if (alert.equals("WHITELIST")){
                TextComponent ala = new TextComponent("");
                TextComponent json = new TextComponent("");
                json = new TextComponent("§d"+server.getInfo().getName()+ " §eUHC Game on §d"+server.getInfo().getName() + " §eopens in §d§l"+ s + "§7[§eClick to join!§7]");
                json.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/server "+server.getInfo().getName()));
                json.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder("§7[§e§lJoin!§7]")).create()));
                ala.addExtra((BaseComponent) json);
                getProxy().getPlayers().forEach(proxiedPlayer -> proxiedPlayer.sendMessage((BaseComponent) ala));
            } else {
                TextComponent ala = new TextComponent("");
                TextComponent json = new TextComponent("");
                json = new TextComponent("§d"+server.getInfo().getName()+ " §eUHC Game on §d"+server.getInfo().getName() + " §estarts in §d§l"+ s + "§7[§eClick to join!§7]");
                json.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/server "+server.getInfo().getName()));
                json.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder("§7[§e§lJoin!§7]")).create()));
                ala.addExtra((BaseComponent) json);
                getProxy().getPlayers().forEach(proxiedPlayer -> proxiedPlayer.sendMessage((BaseComponent) ala));
            }
    }
}

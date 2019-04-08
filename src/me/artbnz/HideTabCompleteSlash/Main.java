package me.artbnz.HideTabCompleteSlash;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.FieldAccessException;

import protocolsupport.api.ProtocolSupportAPI;
import protocolsupport.api.ProtocolVersion;

import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

public class Main extends org.bukkit.plugin.java.JavaPlugin implements org.bukkit.event.Listener
{
  public static Main instance;
  PluginManager manager = Bukkit.getServer().getPluginManager();
  ProtocolManager protocolManager;
  
  public void onEnable() { protocolManager = com.comphenix.protocol.ProtocolLibrary.getProtocolManager();
    protocolManager.addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL, new PacketType[] { PacketType.Play.Client.TAB_COMPLETE })
    {
      public void onPacketReceiving(PacketEvent event)
      {
		Player player = event.getPlayer();
		if (event.getPacketType() == PacketType.Play.Client.TAB_COMPLETE && ProtocolSupportAPI.getProtocolVersion(player).isBefore(ProtocolVersion.MINECRAFT_1_13)) {
          try
          {
            PacketContainer packet = event.getPacket();
            String message = ((String)packet.getSpecificModifier(String.class).read(0)).toLowerCase();
            if ((message.startsWith(""))) {
              event.setCancelled(true);
            }
          }
          catch (FieldAccessException e)
          {
            getLogger().log(Level.SEVERE, "Couldn't access field.", e);
          }
        }
      }
    });
    Bukkit.getServer().getPluginManager().registerEvents(this, this);
  }
  
  public void onDisable() {
    instance = null;
  }
}
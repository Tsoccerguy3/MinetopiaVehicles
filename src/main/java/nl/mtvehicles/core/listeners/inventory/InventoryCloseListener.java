package nl.mtvehicles.core.listeners.inventory;

import nl.mtvehicles.core.events.inventory.InventoryCloseEvent;
import nl.mtvehicles.core.infrastructure.helpers.LanguageUtils;
import nl.mtvehicles.core.infrastructure.helpers.TextUtils;
import nl.mtvehicles.core.infrastructure.models.MTVListener;
import nl.mtvehicles.core.infrastructure.modules.ConfigModule;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

public class InventoryCloseListener extends MTVListener {
    public static HashMap<String, Double> speed = new HashMap<>();

    public InventoryCloseListener(){
        super(new InventoryCloseEvent());
    }

    @EventHandler
    public void onInventoryClose(org.bukkit.event.inventory.InventoryCloseEvent e) {
        if (e.getView().getTitle().contains("Kofferbak Vehicle: ")) {
            String ken = e.getView().getTitle().replace("Kofferbak Vehicle: ", "");
            List<ItemStack> chest = (List<ItemStack>) ConfigModule.vehicleDataConfig.getConfig().getList("vehicle." + ken + ".kofferbakData");
            chest.removeAll(chest);
            for (ItemStack item : e.getInventory().getContents()) {
                chest.add(item);
                ConfigModule.vehicleDataConfig.getConfig().set("vehicle." + ken + ".kofferbakData", chest);
                ConfigModule.vehicleDataConfig.save();
            }
        }
        if (e.getView().getTitle().contains("Choose your language")) {
            Player p = (Player) e.getPlayer();
            if (LanguageUtils.languageCheck.get(p.getUniqueId())) {
                p.sendMessage(TextUtils.colorize("&cThe language settings have not changed because the menu is closed. Do you want to change this anyway? Execute /vehicle language"));
            }
        }
    }
}
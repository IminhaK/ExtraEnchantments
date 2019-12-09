package net.bloop.extraenchants;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = ExtraEnchantments.MODID)
@Config.LangKey("extraenchants.config.title")
public class EnchantmentConfigs {

    @Config.Comment("At what % of HP will the predator enchantment kill an enemy?")
    public static float predatorEffectiveness = 0.2f;

    @Config.Comment("Can predator kill players?")
    public static boolean predatorPlayers = false;

    @Mod.EventBusSubscriber(modid = ExtraEnchantments.MODID)
    private static class EventHandler {
        @SubscribeEvent
        public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(ExtraEnchantments.MODID)) {
                ConfigManager.sync(ExtraEnchantments.MODID, Config.Type.INSTANCE);
            }
        }
    }
}

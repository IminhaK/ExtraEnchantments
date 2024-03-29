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
    @Config.RangeInt(min = 0, max = 100)
    public static double predatorEffectiveness = 20;

    @Config.Comment("Can predator kill players?")
    public static boolean predatorPlayers = false;

    @Config.Comment("Can predator kill bosses?")
    public static boolean predatorBosses = false;

    @Mod.EventBusSubscriber(modid = ExtraEnchantments.MODID)
    private static class ConfigEventHandler {
        @SubscribeEvent
        public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(ExtraEnchantments.MODID)) {
                ConfigManager.sync(ExtraEnchantments.MODID, Config.Type.INSTANCE);
            }
        }
    }
}

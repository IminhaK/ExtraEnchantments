package net.bloop.extraenchants;

import net.bloop.extraenchants.enchants.BaseEnchant;
import net.bloop.extraenchants.enchants.EnchantPredator;
import net.bloop.extraenchants.enchants.EnchantSmelt;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;

@Mod.EventBusSubscriber
public class EnchantmentRegistry {

    public static EnchantSmelt enchantSmelt = new EnchantSmelt();
    public static EnchantPredator enchantPredator = new EnchantPredator();

    private static ArrayList<BaseEnchant> enchants = new ArrayList<>();

    private static void register(BaseEnchant ench) {
        ResourceLocation resourceLocation = new ResourceLocation(ExtraEnchantments.MODID, ench.getName());
        ench.setRegistryName(resourceLocation);
        enchants.add(ench);
    }

    @SubscribeEvent
    public static void registryEvent(RegistryEvent.Register<Enchantment> event) {
        register(enchantSmelt);
        register(enchantPredator);

        for(Enchantment e : enchants) {
            event.getRegistry().register(e);
        }
    }
}

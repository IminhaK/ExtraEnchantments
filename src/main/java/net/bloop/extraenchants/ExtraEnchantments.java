package net.bloop.extraenchants;

import net.minecraftforge.fml.common.Mod;

@Mod(modid = ExtraEnchantments.MODID, name = ExtraEnchantments.NAME, version = ExtraEnchantments.VERSION)
public class ExtraEnchantments {
    public static final String MODID = "extraenchants";
    public static final String NAME = "Extra Enchantments";
    public static final String VERSION = "1.0";

    @Mod.Instance(ExtraEnchantments.MODID)
    public static ExtraEnchantments instance;
}

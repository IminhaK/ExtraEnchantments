package net.bloop.extraenchants.enchants;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class BaseEnchant extends Enchantment {

    protected BaseEnchant(String name, Rarity rarityIn, EnumEnchantmentType enchTypeIn, EntityEquipmentSlot[] slots) {
        super(rarityIn, enchTypeIn, slots);
        this.setName(name);
    }

    @Override
    public String getName() {
        return this.name;
    }

    public int getCurrentLevel(ItemStack stack) {
        if (!stack.isEmpty() && EnchantmentHelper.getEnchantments(stack).containsKey(this))
            return EnchantmentHelper.getEnchantments(stack).get(this);
        return 0;
    }
}

package net.bloop.extraenchants.enchants;

import net.bloop.extraenchants.EnchantmentConfigs;
import net.bloop.extraenchants.EnchantmentRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class EnchantPredator extends BaseEnchant {

    public EnchantPredator() {
        super("Predator", Rarity.VERY_RARE, EnumEnchantmentType.WEAPON, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
    }

    @Override
    public int getMaxLevel(){
        return 1;
    }

    @SubscribeEvent
    public static void onHit(AttackEntityEvent e) {
        if(e.getEntityPlayer() == null)
            return;
        if(!(e.getTarget() instanceof EntityLivingBase))
            return;
        if(!EnchantmentHelper.getEnchantments(e.getEntityPlayer().getHeldItemMainhand()).containsKey(EnchantmentRegistry.enchantPredator))
            return;
        EntityLivingBase entity = (EntityLivingBase)e.getTarget();
        if(!EnchantmentConfigs.predatorPlayers && entity instanceof EntityPlayer)
            return;
        if((entity.getHealth() / entity.getMaxHealth()) <= EnchantmentConfigs.predatorEffectiveness) //check if youre hitting an entity at less than config % hp
            entity.attackEntityFrom(DamageSource.causePlayerDamage(e.getEntityPlayer()), Float.MAX_VALUE); //"Now thats allota damage!"
    }
}

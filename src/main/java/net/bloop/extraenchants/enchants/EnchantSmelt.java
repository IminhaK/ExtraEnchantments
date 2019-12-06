package net.bloop.extraenchants.enchants;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Mod.EventBusSubscriber
public class EnchantSmelt extends BaseEnchant {

    public EnchantSmelt() {
        super("Smelting", Rarity.COMMON, EnumEnchantmentType.DIGGER, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});

    }

    @Override
    public boolean canApplyTogether(Enchantment ench) {
        return ench != Enchantments.SILK_TOUCH && super.canApplyTogether(ench);
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onHarvestDrops(BlockEvent.HarvestDropsEvent e) { //add spawning of xp
        if (e.getHarvester() == null)
            return;
        if (e.isSilkTouching())
            return;
        List<ItemStack> drops = e.getDrops(); //items that are dropped on the floor
        List<ItemStack> dropsCopy = new ArrayList<>();
        for (ItemStack i : drops)
            dropsCopy.add(i.copy());
        drops.clear();
        for (ItemStack i : dropsCopy) {
            ItemStack smelted = FurnaceRecipes.instance().getSmeltingResult(i).copy();
            int xp = dropXp(FurnaceRecipes.instance().getSmeltingExperience(smelted)); //need to pass in smelting result
            if(!smelted.isEmpty()) {
                smelted.setCount(fortuneDrops(e.getHarvester()));
                drops.add(smelted);
                if(xp != 0) {
                    e.getWorld().spawnEntity(new EntityXPOrb(e.getWorld(), e.getPos().getX(), e.getPos().getY(), e.getPos().getZ(), xp));
                }
            } else {
                drops.add(i);
            }
        }
    }

    private static int fortuneDrops(EntityPlayer p) { //Averages obtained from https://minecraft.gamepedia.com/Fortune
        Map<Enchantment, Integer> enchants = EnchantmentHelper.getEnchantments(p.getHeldItemMainhand());
        Random r = new Random();
        if(!enchants.containsKey(Enchantments.FORTUNE))
            return 1;
        else if(enchants.get(Enchantments.FORTUNE) == 1) //+33%
            return r.nextInt(4) == 3 ? 2 : 1; //if r is 3, double. if not dont.
        else if(enchants.get(Enchantments.FORTUNE) == 2) { //+75%
            int f2 = r.nextInt(5);
            return f2 == 4 ? 3 : (f2 == 3 ? 2 : 1); //if f2 is 4, triple. if f2 is 3, double. if not, dont.
        } else if(enchants.get(Enchantments.FORTUNE) >= 2) { //+120%
            int f3 = r.nextInt(6);
            return f3 == 5 ? 4 : (f3 == 4 ? 3 : (f3 == 3 ? 2 : 1)); //if f3 is 5, quadruple. if f3 is 4, triple. if f3 is 3, double. if not, dont.
        }
        return 1;
    }

    private static int dropXp(float chance){
        Random r = new Random();
        int iChance = (int)(chance * 100f);
        System.out.println(chance + " " + iChance);
        if(chance == 1.0f)
            return 1;
        else if(chance > 1.0f){ //no vanilla recipes have more than 1.0
            int extra = (int)(chance - (chance % 1.0f));
            chance = chance % 1.0f;
            return r.nextInt(101) <= (int)(chance*100.0f) ? 1 + extra : extra;
        } else
            return r.nextInt(101) <= (int)(chance*100.0f) ? 1 : 0; //simulating furnace xp dropping
    }
}

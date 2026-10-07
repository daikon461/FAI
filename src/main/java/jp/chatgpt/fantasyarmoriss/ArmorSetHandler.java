package jp.chatgpt.fantasyarmoriss;

import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.Holder;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.*;

public final class ArmorSetHandler {
    private static final Map<UUID,String> ACTIVE=new HashMap<>();

    @SubscribeEvent
    public static void onTick(PlayerTickEvent.Post e){
        Player p=e.getEntity();
        if(p.level().isClientSide() || p.tickCount%10!=0)return;
        String found=ArmorSetRegistry.SETS.stream().filter(s->isFull(p,s.id())).map(ArmorSetRegistry.SetDef::id).findFirst().orElse("");
        String old=ACTIVE.getOrDefault(p.getUUID(),"");
        if(!old.equals(found)){
            clear(p);
            if(!found.isEmpty()) ArmorSetRegistry.SETS.stream().filter(s->s.id().equals(found)).findFirst().ifPresent(s->apply(p,s));
            ACTIVE.put(p.getUUID(),found);
        }
    }
    public static boolean isFull(Player p,String id){
        return matches(p,EquipmentSlot.HEAD,id,"helmet") && matches(p,EquipmentSlot.CHEST,id,"chestplate") && matches(p,EquipmentSlot.LEGS,id,"leggings") && matches(p,EquipmentSlot.FEET,id,"boots");
    }
    public static int count(Player p,String id){
        int n=0; if(matches(p,EquipmentSlot.HEAD,id,"helmet"))n++; if(matches(p,EquipmentSlot.CHEST,id,"chestplate"))n++; if(matches(p,EquipmentSlot.LEGS,id,"leggings"))n++; if(matches(p,EquipmentSlot.FEET,id,"boots"))n++; return n;
    }
    private static boolean matches(Player p,EquipmentSlot slot,String id,String suffix){
        var key=net.minecraft.core.registries.BuiltInRegistries.ITEM.getKey(p.getItemBySlot(slot).getItem());
        return "fantasy_armor".equals(key.getNamespace()) && (id+"_"+suffix).equals(key.getPath());
    }
    private static void apply(Player p,ArmorSetRegistry.SetDef set){
        for(var b:set.bonuses()){
            Holder<Attribute> attr=attribute(b.attribute()); if(attr==null)continue;
            AttributeInstance inst=p.getAttribute(attr); if(inst==null)continue;
            ResourceLocation id=ResourceLocation.fromNamespaceAndPath(FantasyArmorISS.MOD_ID,"set_"+b.attribute());
            inst.removeModifier(id);
            inst.addPermanentModifier(new AttributeModifier(id,b.amount(),AttributeModifier.Operation.ADD_VALUE));
        }
    }
    private static void clear(Player p){
        for(var s:ArmorSetRegistry.SETS) for(var b:s.bonuses()){
            Holder<Attribute> attr=attribute(b.attribute()); if(attr==null)continue;
            AttributeInstance inst=p.getAttribute(attr); if(inst!=null) inst.removeModifier(ResourceLocation.fromNamespaceAndPath(FantasyArmorISS.MOD_ID,"set_"+b.attribute()));
        }
    }
    private static Holder<Attribute> attribute(String id){
        return switch(id){
            case "max_mana"->AttributeRegistry.MAX_MANA;
            case "mana_regen"->AttributeRegistry.MANA_REGEN;
            case "cooldown_reduction"->AttributeRegistry.COOLDOWN_REDUCTION;
            case "spell_power"->AttributeRegistry.SPELL_POWER;
            case "spell_resist"->AttributeRegistry.SPELL_RESIST;
            case "cast_time_reduction"->AttributeRegistry.CAST_TIME_REDUCTION;
            case "casting_movespeed"->AttributeRegistry.CASTING_MOVESPEED;
            case "fire_magic_resist"->AttributeRegistry.FIRE_MAGIC_RESIST;
            case "lightning_magic_resist"->AttributeRegistry.LIGHTNING_MAGIC_RESIST;
            case "fire_spell_power"->AttributeRegistry.FIRE_SPELL_POWER;
            case "lightning_spell_power"->AttributeRegistry.LIGHTNING_SPELL_POWER;
            case "holy_spell_power"->AttributeRegistry.HOLY_SPELL_POWER;
            case "ender_spell_power"->AttributeRegistry.ENDER_SPELL_POWER;
            case "blood_spell_power"->AttributeRegistry.BLOOD_SPELL_POWER;
            case "nature_spell_power"->AttributeRegistry.NATURE_SPELL_POWER;
            case "eldritch_spell_power"->AttributeRegistry.ELDRITCH_SPELL_POWER;
            default->null;
        };
    }
    private ArmorSetHandler(){}
}

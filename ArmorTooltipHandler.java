package jp.chatgpt.fantasyarmoriss;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

@EventBusSubscriber(modid=FantasyArmorISS.MOD_ID,value=Dist.CLIENT)
public final class ArmorTooltipHandler {
 @SubscribeEvent
 public static void tooltip(ItemTooltipEvent e){
   var key=BuiltInRegistries.ITEM.getKey(e.getItemStack().getItem());
   if(!"fantasy_armor".equals(key.getNamespace()))return;
   ArmorSetRegistry.byItemPath(key.getPath()).ifPresent(set->{
     e.getToolTip().add(Component.empty());
     e.getToolTip().add(Component.literal("◆ セット効果："+set.name()).withStyle(ChatFormatting.GOLD));
     e.getToolTip().add(Component.literal("4部位装備時").withStyle(ChatFormatting.GRAY));
     for(var b:set.bonuses()){
       String value=b.percent()?((b.amount()>=0?"+":"")+Math.round(b.amount()*100)+"%"):((b.amount()>=0?"+":"")+(int)b.amount());
       e.getToolTip().add(Component.literal("  ✦ "+b.label()+" "+value).withStyle(ChatFormatting.AQUA));
     }
     var p=Minecraft.getInstance().player;
     if(p!=null){int n=ArmorSetHandler.count(p,set.id()); boolean on=n==4;
       e.getToolTip().add(Component.literal(n+"/4  "+(on?"セット効果発動中":"あと"+(4-n)+"部位")).withStyle(on?ChatFormatting.GREEN:ChatFormatting.DARK_GRAY));
     }
   });
 }
 private ArmorTooltipHandler(){}
}

package jp.chatgpt.fantasyarmoriss;

import java.util.*;

public final class ArmorSetRegistry {
    public record Bonus(String attribute, double amount, String label, boolean percent) {}
    public record SetDef(String id, String name, List<Bonus> bonuses) {}
    private static Bonus flat(String a,double v,String l){return new Bonus(a,v,l,false);}
    private static Bonus pct(String a,double v,String l){return new Bonus(a,v,l,true);}
    private static SetDef s(String id,String name,Bonus... b){return new SetDef(id,name,List.of(b));}
    public static final List<SetDef> SETS=List.of(
      s("eclipse_soldier","覆面兵士",pct("spell_power",.10,"魔法威力"),pct("spell_resist",.15,"魔法耐性"),flat("max_mana",75,"最大マナ")),
      s("dragonslayer","竜殺し",pct("fire_spell_power",.25,"炎属性魔法威力"),pct("spell_power",.10,"魔法威力"),pct("fire_magic_resist",.25,"炎属性魔法耐性"),flat("max_mana",75,"最大マナ")),
      s("hero","英雄",flat("max_mana",100,"最大マナ"),pct("mana_regen",.15,"マナ回復速度"),pct("spell_resist",.10,"魔法耐性")),
      s("golden_horns","黄金の角",pct("cast_time_reduction",.12,"詠唱時間短縮"),pct("cooldown_reduction",.10,"クールダウン短縮"),flat("max_mana",75,"最大マナ")),
      s("thief","盗賊",pct("cast_time_reduction",.10,"詠唱時間短縮"),pct("cooldown_reduction",.15,"クールダウン短縮"),pct("casting_movespeed",.08,"詠唱中移動速度")),
      s("wandering_wizard","放浪魔術師",flat("max_mana",250,"最大マナ"),pct("mana_regen",.20,"マナ回復速度"),pct("spell_power",.15,"魔法威力"),pct("cast_time_reduction",.10,"詠唱時間短縮")),
      s("chess_board_knight","盤上の騎士",pct("spell_power",.08,"魔法威力"),pct("spell_resist",.15,"魔法耐性"),flat("max_mana",50,"最大マナ")),
      s("dark_lord","闇の王",pct("eldritch_spell_power",.25,"エルドリッチ魔法威力"),pct("blood_spell_power",.15,"血属性魔法威力"),pct("spell_resist",.15,"魔法耐性"),flat("max_mana",100,"最大マナ")),
      s("sunset_wings","黄昏の翼",pct("casting_movespeed",.15,"詠唱中移動速度"),pct("cast_time_reduction",.10,"詠唱時間短縮"),flat("max_mana",100,"最大マナ")),
      s("fog_guard","霧の衛兵",pct("spell_resist",.25,"魔法耐性"),pct("fire_magic_resist",.30,"炎属性魔法耐性"),flat("max_mana",75,"最大マナ")),
      s("dark_cover","闇の覆い",pct("spell_power",.15,"魔法威力"),pct("spell_resist",.10,"魔法耐性"),pct("cooldown_reduction",.10,"クールダウン短縮")),
      s("spark_of_dawn","暁の火花",pct("holy_spell_power",.25,"聖属性魔法威力"),pct("spell_power",.10,"魔法威力"),pct("fire_magic_resist",.15,"炎属性魔法耐性"),flat("max_mana",150,"最大マナ")),
      s("golden_execution","黄金の処刑人",pct("spell_power",.12,"魔法威力"),pct("cooldown_reduction",.15,"クールダウン短縮"),flat("max_mana",75,"最大マナ")),
      s("forgotten_trace","忘れられた痕跡",pct("mana_regen",.20,"マナ回復速度"),pct("cooldown_reduction",.15,"クールダウン短縮"),pct("spell_power",.10,"魔法威力")),
      s("redeemer","贖罪者",pct("holy_spell_power",.20,"聖属性魔法威力"),pct("spell_resist",.20,"魔法耐性"),flat("max_mana",100,"最大マナ"),pct("mana_regen",.10,"マナ回復速度")),
      s("twinned","双生",flat("max_mana",150,"最大マナ"),pct("mana_regen",.15,"マナ回復速度"),pct("cooldown_reduction",.15,"クールダウン短縮"),pct("cast_time_reduction",.10,"詠唱時間短縮")),
      s("gilded_hunt","金色の狩人",pct("casting_movespeed",.12,"詠唱中移動速度"),pct("cast_time_reduction",.08,"詠唱時間短縮"),pct("spell_power",.10,"魔法威力")),
      s("lady_maria","マリア",pct("blood_spell_power",.20,"血属性魔法威力"),pct("spell_power",.10,"魔法威力"),pct("mana_regen",.10,"マナ回復速度"),flat("max_mana",75,"最大マナ")),
      s("crucible_knight","坩堝の騎士",pct("fire_spell_power",.15,"炎属性魔法威力"),pct("fire_magic_resist",.25,"炎属性魔法耐性"),pct("spell_resist",.15,"魔法耐性"),flat("max_mana",100,"最大マナ")),
      s("evening_ghost","宵の亡霊",pct("ender_spell_power",.15,"エンダー魔法威力"),pct("eldritch_spell_power",.15,"エルドリッチ魔法威力"),pct("cast_time_reduction",.12,"詠唱時間短縮"),flat("max_mana",100,"最大マナ")),
      s("ronin","浪人",pct("cast_time_reduction",.15,"詠唱時間短縮"),pct("cooldown_reduction",.12,"クールダウン短縮"),pct("lightning_spell_power",.10,"雷属性魔法威力")),
      s("malenia","マレニア",pct("nature_spell_power",.20,"自然魔法威力"),pct("mana_regen",.20,"マナ回復速度"),flat("max_mana",125,"最大マナ"),pct("spell_resist",.10,"魔法耐性")),
      s("old_knight","古騎士",pct("spell_resist",.25,"魔法耐性"),flat("max_mana",75,"最大マナ"),pct("spell_power",.05,"魔法威力")),
      s("silver_knight","銀騎士",pct("holy_spell_power",.15,"聖属性魔法威力"),pct("spell_resist",.20,"魔法耐性"),flat("max_mana",100,"最大マナ")),
      s("dead_gladiator","死せる剣闘士",pct("blood_spell_power",.15,"血属性魔法威力"),pct("spell_power",.10,"魔法威力"),pct("mana_regen",.10,"マナ回復速度"),flat("max_mana",100,"最大マナ")),
      s("flesh_of_the_feaster","肉の饗宴者",pct("blood_spell_power",.25,"血属性魔法威力"),pct("spell_resist",.15,"魔法耐性"),flat("max_mana",125,"最大マナ")),
      s("wind_worshipper","風の崇拝者",pct("nature_spell_power",.20,"自然魔法威力"),pct("casting_movespeed",.15,"詠唱中移動速度"),pct("cast_time_reduction",.10,"詠唱時間短縮"),flat("max_mana",100,"最大マナ")),
      s("grave_sentinel","墓守",pct("ender_spell_power",.15,"エンダー魔法威力"),pct("spell_resist",.25,"魔法耐性"),flat("max_mana",125,"最大マナ")),
      s("ornstein","オーンスタイン",pct("lightning_spell_power",.25,"雷属性魔法威力"),pct("lightning_magic_resist",.25,"雷属性魔法耐性"),pct("cast_time_reduction",.10,"詠唱時間短縮"),flat("max_mana",125,"最大マナ"))
    );
    public static Optional<SetDef> byItemPath(String path){return SETS.stream().filter(s->path.startsWith(s.id()+"_")).findFirst();}
    private ArmorSetRegistry(){}
}

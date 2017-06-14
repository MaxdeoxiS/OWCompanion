package com.example.maxime.overwatchstats;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import com.example.maxime.overwatchstats.adapters.HeroesAdapter;
import com.example.maxime.overwatchstats.model.AverageStats;
import com.example.maxime.overwatchstats.model.HeroItem;
import com.example.maxime.overwatchstats.model.HeroOutput;
import com.example.maxime.overwatchstats.model.HeroStats;
import com.example.maxime.overwatchstats.model.OverallStats;
import com.example.maxime.overwatchstats.model.SpecificStats;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GetHeroesStats extends AsyncTask<Void, Void, HeroOutput> {

    private final String LOG_TAG = GetHeroesStats.class.getSimpleName();
    private View myView;
    private Context context;
    private HeroesAdapter adapter;
    // Gamemode. Either quickplay or competitive
    private String mode;
    private OnAsyncRequestComplete caller;

    private static final Map<String, String> heroesHexa = createMap();

    private static Map<String, String> createMap()
    {
        Map<String,String> myMap = new HashMap<String,String>();
        myMap.put("genji", "029");
        myMap.put("hanzo", "005");
        myMap.put("mccree", "042");
        myMap.put("widowmaker", "00A");
        myMap.put("pharah", "008");
        myMap.put("soldier76", "06E");
        myMap.put("ana", "13B");
        myMap.put("mei", "0DD");
        myMap.put("reaper", "002");
        myMap.put("roadhog", "040");
        myMap.put("junkrat", "065");
        myMap.put("zarya", "068");
        myMap.put("sombra", "12E");
        myMap.put("zenyatta", "020");
        myMap.put("lucio", "079");
        myMap.put("winston", "009");
        myMap.put("torbjorn", "006");
        myMap.put("dva", "07A");
        myMap.put("tracer", "003");
        myMap.put("mercy", "004");
        myMap.put("bastion", "015");
        myMap.put("symmetra", "016");
        myMap.put("reinhardt", "007");
        myMap.put("orisa", "13E");
        return myMap;
    }

    public GetHeroesStats(View v, Context c, HeroesAdapter a, String m, OnAsyncRequestComplete caller)
    {
        myView = v;
        context = c;
        adapter = a;
        mode = m;
        this.caller = caller;
    }

    public  interface OnAsyncRequestComplete {
        public void asyncResponse(HashMap<String, HeroStats> response);
    }

    public GetHeroesStats(View v, Context c, HeroesAdapter a)
    {
        myView = v;
        context = c;
        adapter = a;
        mode = "quickplay";
    }

    private HeroOutput getStatsDataFromJson(String statsAsString) throws JSONException {

        JSONObject jsonObject = new JSONObject(statsAsString);

        ArrayList<HeroItem> p = new ArrayList<HeroItem>();
        HashMap<String, HeroStats> s = new HashMap<String, HeroStats>();

        JSONObject stats = jsonObject.getJSONObject("eu").getJSONObject("heroes").getJSONObject("playtime").getJSONObject(this.mode);
        JSONObject statsHero = jsonObject.getJSONObject("eu").getJSONObject("heroes").getJSONObject("stats").getJSONObject(this.mode);

        String heroImg = "https://blzgdapipro-a.akamaihd.net/game/heroes/small/0x02E0000000000";

        Iterator<String> names;

        for (names = stats.keys(); names.hasNext();) {
            String heroName = names.next();
            HeroItem h = new HeroItem(heroName, stats.getString(heroName), heroImg + heroesHexa.get(heroName) + ".png");
            p.add(h);

            JSONObject currentHeroStats = statsHero.getJSONObject(heroName);

            HeroStats heroStats = new HeroStats(heroName);

            AverageStats avg = parseAvg(currentHeroStats.getJSONObject("average_stats"));
            OverallStats ovl = parseOvl(currentHeroStats.getJSONObject("general_stats"));
            SpecificStats spc= parseSpc(currentHeroStats.getJSONObject("hero_stats"));

            heroStats.setAvg(avg);
            heroStats.setOvl(ovl);
            heroStats.setSpc(spc);

            s.put(heroName, heroStats);
        }

        HeroOutput heroOutput = new HeroOutput(p, s);

        return heroOutput;
    }


    private AverageStats parseAvg(JSONObject json)
    {
        AverageStats response = new AverageStats();

        try {

        if(json.has("defensive_assists_average"))
            response.setDefensive_assists_average(Float.parseFloat(json.getString("defensive_assists_average")));
        if(json.has("healing_done_average"))
            response.setHealing_done_average(Float.parseFloat(json.getString("healing_done_average")));
        if(json.has("earthshatter_kills_average"))
            response.setEarthshatter_kills_average(Float.parseFloat(json.getString("earthshatter_kills_average")));
        if(json.has("charge_kills_average"))
            response.setCharge_kills_average(Float.parseFloat(json.getString("charge_kills_average")));
        if(json.has("damage_blocked_average"))
            response.setDamage_blocked_average(Float.parseFloat(json.getString("damage_blocked_average")));
        if(json.has("fire_strike_kills_average"))
            response.setFire_strike_kills_average(Float.parseFloat(json.getString("fire_strike_kills_average")));
        if(json.has("offensive_assists_average"))
            response.setOffensive_assists_average(Float.parseFloat(json.getString("offensive_assists_average")));
        if(json.has("time_spent_on_fire_average"))
            response.setTime_spent_on_fire_average(Float.parseFloat(json.getString("time_spent_on_fire_average")));
        if(json.has("final_blows_average"))
            response.setFinal_blows_average(Float.parseFloat(json.getString("final_blows_average")));
        if(json.has("self_healing_average"))
            response.setSelf_healing_average(Float.parseFloat(json.getString("self_healing_average")));
        if(json.has("objective_kills_average"))
            response.setObjective_kills_average(Float.parseFloat(json.getString("objective_kills_average")));
        if(json.has("critical_hits_average"))
            response.setCritical_hits_average(Float.parseFloat(json.getString("critical_hits_average")));
        if(json.has("pulse_bombs_attached_average"))
            response.setPulse_bombs_attached_average(Float.parseFloat(json.getString("pulse_bombs_attached_average")));
        if(json.has("solo_kills_average"))
            response.setSolo_kills_average(Float.parseFloat(json.getString("solo_kills_average")));
        if(json.has("melee_final_blows_average"))
            response.setMelee_final_blows_average(Float.parseFloat(json.getString("melee_final_blows_average")));
        if(json.has("objective_time_average"))
            response.setObjective_time_average(Float.parseFloat(json.getString("objective_time_average")));
        if(json.has("damage_done_average"))
            response.setDamage_done_average(Float.parseFloat(json.getString("damage_done_average")));
        if(json.has("deaths_average"))
            response.setDeaths_average(Float.parseFloat(json.getString("deaths_average")));
        if(json.has("eliminations_average"))
            response.setEliminations_average(Float.parseFloat(json.getString("eliminations_average")));
        if(json.has("pulse_bomb_kills_average"))
            response.setPulse_bomb_kills_average(Float.parseFloat(json.getString("pulse_bomb_kills_average")));
        if(json.has("rip_tire_kills_average"))
            response.setRip_tire_kills_average(Float.parseFloat(json.getString("rip_tire_kills_average")));
        if(json.has("fan_the_hammer_kills_average"))
            response.setFan_the_hammer_kills_average(Float.parseFloat(json.getString("fan_the_hammer_kills_average")));
        if(json.has("deadeye_kills_average"))
            response.setDeadeye_kills_average(Float.parseFloat(json.getString("deadeye_kills_average")));
        if(json.has("jump_pack_kills_average"))
            response.setJump_pack_kills_average(Float.parseFloat(json.getString("jump_pack_kills_average")));
        if(json.has("players_knocked_back_average"))
            response.setPlayers_knocked_back_average(Float.parseFloat(json.getString("players_knocked_back_average")));
        if(json.has("primal_rage_kills_average"))
            response.setPrimal_rage_kills_average(Float.parseFloat(json.getString("primal_rage_kills_average")));
        if(json.has("scatter_arrow_kills_average"))
            response.setScatter_arrow_kills_average(Float.parseFloat(json.getString("scatter_arrow_kills_average")));
        if(json.has("dragonstrike_kills_average"))
            response.setDragonstrike_kills_average(Float.parseFloat(json.getString("dragonstrike_kills_average")));
        if(json.has("barrage_kills_average"))
            response.setBarrage_kills_average(Float.parseFloat(json.getString("barrage_kills_average")));
        if(json.has("rocket_direct_hits_average"))
            response.setRocket_direct_hits_average(Float.parseFloat(json.getString("rocket_direct_hits_average")));
        if(json.has("enemies_hooked_average"))
            response.setEnemies_hooked_average(Float.parseFloat(json.getString("enemies_hooked_average")));
        if(json.has("whole_hog_kills_average"))
            response.setWhole_hog_kills_average(Float.parseFloat(json.getString("whole_hog_kills_average")));
        if(json.has("graviton_surge_kills_average"))
            response.setGraviton_surge_kills_average(Float.parseFloat(json.getString("graviton_surge_kills_average")));
        if(json.has("projected_barriers_applied_average"))
            response.setProjected_barriers_applied_average(Float.parseFloat(json.getString("projected_barriers_applied_average")));
        if(json.has("high_energy_kills_average"))
            response.setHigh_energy_kills_average(Float.parseFloat(json.getString("high_energy_kills_average")));
        if(json.has("average_energy_best_in_game"))
            response.setAverage_energy_best_in_game(Float.parseFloat(json.getString("average_energy_best_in_game")));
        if(json.has("turret_kills_average"))
            response.setTurret_kills_average(Float.parseFloat(json.getString("turret_kills_average")));
        if(json.has("torbjorn_kills_average"))
            response.setTorbjorn_kills_average(Float.parseFloat(json.getString("torbjorn_kills_average")));
        if(json.has("armor_packs_created_average"))
            response.setArmor_packs_created_average(Float.parseFloat(json.getString("armor_packs_created_average")));
        if(json.has("molten_core_kills_average"))
            response.setMolten_core_kills_average(Float.parseFloat(json.getString("molten_core_kills_average")));
        if(json.has("players_resurrected_average"))
            response.setPlayers_resurrected_average(Float.parseFloat(json.getString("players_resurrected_average")));
        if(json.has("blaster_kills_average"))
            response.setBlaster_kills_average(Float.parseFloat(json.getString("blaster_kills_average")));
        if(json.has("damage_amplified_average"))
            response.setDamage_amplified_average(Float.parseFloat(json.getString("damage_amplified_average")));
        if(json.has("helix_rockets_kills_average"))
            response.setHelix_rockets_kills_average(Float.parseFloat(json.getString("helix_rockets_kills_average")));
        if(json.has("tactical_visor_kills_average"))
            response.setTactical_visor_kills_average(Float.parseFloat(json.getString("tactical_visor_kills_average")));
        if(json.has("nano_boosts_applied_average"))
            response.setNano_boosts_applied_average(Float.parseFloat(json.getString("nano_boosts_applied_average")));
        if(json.has("enemies_slept_average"))
            response.setEnemies_slept_average(Float.parseFloat(json.getString("enemies_slept_average")));
        if(json.has("nano_boost_assists_average"))
            response.setNano_boost_assists_average(Float.parseFloat(json.getString("nano_boost_assists_average")));
        if(json.has("venom_mine_kills_average"))
            response.setVenom_mine_kills_average(Float.parseFloat(json.getString("venom_mine_kills_average")));
        if(json.has("scoped_critical_hits_average"))
            response.setScoped_critical_hits_average(Float.parseFloat(json.getString("scoped_critical_hits_average")));
        if(json.has("dragonblade_kills_average"))
            response.setDragonblade_kills_average(Float.parseFloat(json.getString("dragonblade_kills_average")));
        if(json.has("damage_reflected_average"))
            response.setDamage_reflected_average(Float.parseFloat(json.getString("damage_reflected_average")));
        if(json.has("death_blossom_kills_average"))
            response.setDeath_blossom_kills_average(Float.parseFloat(json.getString("death_blossom_kills_average")));
        if(json.has("blizzard_kills_average"))
            response.setBlizzard_kills_average(Float.parseFloat(json.getString("blizzard_kills_average")));
        if(json.has("enemies_frozen_average"))
            response.setEnemies_frozen_average(Float.parseFloat(json.getString("enemies_frozen_average")));
        if(json.has("tank_kills_average"))
            response.setTank_kills_average(Float.parseFloat(json.getString("tank_kills_average")));
        if(json.has("recon_kills_average"))
            response.setRecon_kills_average(Float.parseFloat(json.getString("recon_kills_average")));
        if(json.has("sentry_kills_average"))
            response.setSentry_kills_average(Float.parseFloat(json.getString("sentry_kills_average")));
        if(json.has("players_teleported_average"))
            response.setPlayers_teleported_average(Float.parseFloat(json.getString("players_teleported_average")));
        if(json.has("shields_provided_average"))
            response.setShields_provided_average(Float.parseFloat(json.getString("shields_provided_average")));
        if(json.has("teleporter_uptime_average"))
            response.setTeleporter_uptime_average(Float.parseFloat(json.getString("teleporter_uptime_average")));
        if(json.has("mechs_called_average"))
            response.setMechs_called_average(Float.parseFloat(json.getString("mechs_called_average")));
        if(json.has("self_destruct_kills_average"))
            response.setSelf_destruct_kills_average(Float.parseFloat(json.getString("self_destruct_kills_average")));
        if(json.has("enemies_hacked_average"))
            response.setEnemies_hacked_average(Float.parseFloat(json.getString("enemies_hacked_average")));
        if(json.has("enemies_empd_average"))
            response.setEnemies_empd_average(Float.parseFloat(json.getString("enemies_empd_average")));
        if(json.has("sound_barriers_provided_average"))
            response.setSound_barriers_provided_average(Float.parseFloat(json.getString("sound_barriers_provided_average")));


        } catch (JSONException e) {
            Log.e("MYAPP", "unexpected JSON exception", e);
        }
        return response;
    }

    private OverallStats parseOvl(JSONObject json)
    {
        OverallStats response = new OverallStats();

        try {

        if(json.has("transcendence_healing"))
            response.setTranscendence_healing(Float.parseFloat(json.getString("transcendence_healing")));
        if(json.has("defensive_assists_most_in_game"))
            response.setDefensive_assists_most_in_game(Float.parseFloat(json.getString("defensive_assists_most_in_game")));
        if(json.has("healing_done"))
            response.setHealing_done(Float.parseFloat(json.getString("healing_done")));
        if(json.has("healing_done_most_in_life"))
            response.setHealing_done_most_in_life(Float.parseFloat(json.getString("healing_done_most_in_life")));
        if(json.has("healing_done_most_in_game"))
            response.setHealing_done_most_in_game(Float.parseFloat(json.getString("healing_done_most_in_game")));
        if(json.has("defensive_assists"))
            response.setDefensive_assists(Float.parseFloat(json.getString("defensive_assists")));
        if(json.has("weapon_accuracy_best_in_game"))
            response.setWeapon_accuracy_best_in_game(Float.parseFloat(json.getString("weapon_accuracy_best_in_game")));
        if(json.has("shots_hit"))
            response.setShots_hit(Float.parseFloat(json.getString("shots_hit")));
        if(json.has("self_healing_most_in_game"))
            response.setSelf_healing_most_in_game(Float.parseFloat(json.getString("self_healing_most_in_game")));
        if(json.has("self_healing"))
            response.setSelf_healing(Float.parseFloat(json.getString("self_healing")));
        if(json.has("shots_fired"))
            response.setShots_fired(Float.parseFloat(json.getString("shots_fired")));
        if(json.has("critical_hits_most_in_life"))
            response.setCritical_hits_most_in_life(Float.parseFloat(json.getString("critical_hits_most_in_life")));
        if(json.has("weapon_accuracy"))
            response.setWeapon_accuracy(Float.parseFloat(json.getString("weapon_accuracy")));
        if(json.has("critical_hits"))
            response.setCritical_hits(Float.parseFloat(json.getString("critical_hits")));
        if(json.has("critical_hit_accuracy"))
            response.setCritical_hit_accuracy(Float.parseFloat(json.getString("critical_hit_accuracy")));
        if(json.has("critical_hits_most_in_game"))
            response.setCritical_hits_most_in_game(Float.parseFloat(json.getString("critical_hits_most_in_game")));
        if(json.has("melee_final_blows"))
            response.setMelee_final_blows(Float.parseFloat(json.getString("melee_final_blows")));
        if(json.has("teleporter_pads_destroyed"))
            response.setTeleporter_pads_destroyed(Float.parseFloat(json.getString("teleporter_pads_destroyed")));
        if(json.has("damage_done_most_in_game"))
            response.setDamage_done_most_in_game(Float.parseFloat(json.getString("damage_done_most_in_game")));
        if(json.has("objective_kills"))
            response.setObjective_kills(Float.parseFloat(json.getString("objective_kills")));
        if(json.has("time_spent_on_fire"))
            response.setTime_spent_on_fire(Float.parseFloat(json.getString("time_spent_on_fire")));
        if(json.has("medals_bronze"))
            response.setMedals_bronze(Float.parseFloat(json.getString("medals_bronze")));
        if(json.has("objective_kills_most_in_game"))
            response.setObjective_kills_most_in_game(Float.parseFloat(json.getString("objective_kills_most_in_game")));
        if(json.has("games_won"))
            response.setGames_won(Float.parseFloat(json.getString("games_won")));
        if(json.has("offensive_assists"))
            response.setOffensive_assists(Float.parseFloat(json.getString("offensive_assists")));
        if(json.has("eliminations_per_life"))
            response.setEliminations_per_life(Float.parseFloat(json.getString("eliminations_per_life")));
        if(json.has("eliminations_most_in_game"))
            response.setEliminations_most_in_game(Float.parseFloat(json.getString("eliminations_most_in_game")));
        if(json.has("kill_streak_best"))
            response.setKill_streak_best(Float.parseFloat(json.getString("kill_streak_best")));
        if(json.has("multikill"))
            response.setMultikill(Float.parseFloat(json.getString("multikill")));
        if(json.has("environmental_kill"))
            response.setEnvironmental_kill(Float.parseFloat(json.getString("environmental_kill")));
        if(json.has("time_played"))
            response.setTime_played(Float.parseFloat(json.getString("time_played")));
        if(json.has("final_blows"))
            response.setFinal_blows(Float.parseFloat(json.getString("final_blows")));
        if(json.has("eliminations_most_in_life"))
            response.setEliminations_most_in_life(Float.parseFloat(json.getString("eliminations_most_in_life")));
        if(json.has("deaths"))
            response.setDeaths(Float.parseFloat(json.getString("deaths")));
        if(json.has("time_spent_on_fire_most_in_game"))
            response.setTime_spent_on_fire_most_in_game(Float.parseFloat(json.getString("time_spent_on_fire_most_in_game")));
        if(json.has("medals_gold"))
            response.setMedals_gold(Float.parseFloat(json.getString("medals_gold")));
        if(json.has("offensive_assists_most_in_game"))
            response.setOffensive_assists_most_in_game(Float.parseFloat(json.getString("offensive_assists_most_in_game")));
        if(json.has("environmental_deaths"))
            response.setEnvironmental_deaths(Float.parseFloat(json.getString("environmental_deaths")));
        if(json.has("objective_time_most_in_game"))
            response.setObjective_time_most_in_game(Float.parseFloat(json.getString("objective_time_most_in_game")));
        if(json.has("multikill_best"))
            response.setMultikill_best(Float.parseFloat(json.getString("multikill_best")));
        if(json.has("damage_done_most_in_life"))
            response.setDamage_done_most_in_life(Float.parseFloat(json.getString("damage_done_most_in_life")));
        if(json.has("solo_kills"))
            response.setSolo_kills(Float.parseFloat(json.getString("solo_kills")));
        if(json.has("final_blows_most_in_game"))
            response.setFinal_blows_most_in_game(Float.parseFloat(json.getString("final_blows_most_in_game")));
        if(json.has("eliminations"))
            response.setEliminations(Float.parseFloat(json.getString("eliminations")));
        if(json.has("turrets_destroyed"))
            response.setTurrets_destroyed(Float.parseFloat(json.getString("turrets_destroyed")));
        if(json.has("cards"))
            response.setCards(Float.parseFloat(json.getString("cards")));
        if(json.has("medals_silver"))
            response.setMedals_silver(Float.parseFloat(json.getString("medals_silver")));
        if(json.has("objective_time"))
            response.setObjective_time(Float.parseFloat(json.getString("objective_time")));
        if(json.has("damage_done"))
            response.setDamage_done(Float.parseFloat(json.getString("damage_done")));
        if(json.has("medals"))
            response.setMedals(Float.parseFloat(json.getString("medals")));
        if(json.has("solo_kills_most_in_game"))
            response.setSolo_kills_most_in_game(Float.parseFloat(json.getString("solo_kills_most_in_game")));
        if(json.has("shield_generator_destroyed"))
            response.setShield_generator_destroyed(Float.parseFloat(json.getString("shield_generator_destroyed")));
        if(json.has("fan_the_hammer_kills_most_in_game"))
            response.setFan_the_hammer_kills_most_in_game(Float.parseFloat(json.getString("fan_the_hammer_kills_most_in_game")));
        if(json.has("primal_rage_kills_most_in_game"))
            response.setPrimal_rage_kills_most_in_game(Float.parseFloat(json.getString("primal_rage_kills_most_in_game")));
        if(json.has("primal_rage_kills"))
            response.setPrimal_rage_kills(Float.parseFloat(json.getString("primal_rage_kills")));
        if(json.has("projected_barriers_applied_most_in_game"))
            response.setProjected_barriers_applied_most_in_game(Float.parseFloat(json.getString("projected_barriers_applied_most_in_game")));
        if(json.has("blaster_kills_most_in_game"))
            response.setBlaster_kills_most_in_game(Float.parseFloat(json.getString("blaster_kills_most_in_game")));
        if(json.has("unscoped_hits"))
            response.setUnscoped_hits(Float.parseFloat(json.getString("unscoped_hits")));
        if(json.has("unscoped_accuracy_best_in_game"))
            response.setUnscoped_accuracy_best_in_game(Float.parseFloat(json.getString("unscoped_accuracy_best_in_game")));
        if(json.has("nano_boosts_applied_most_in_game"))
            response.setNano_boosts_applied_most_in_game(Float.parseFloat(json.getString("nano_boosts_applied_most_in_game")));
        if(json.has("unscoped_shots"))
            response.setUnscoped_shots(Float.parseFloat(json.getString("unscoped_shots")));
        if(json.has("enemies_slept_most_in_game"))
            response.setEnemies_slept_most_in_game(Float.parseFloat(json.getString("enemies_slept_most_in_game")));
        if(json.has("enemies_slept"))
            response.setEnemies_slept(Float.parseFloat(json.getString("enemies_slept")));
        if(json.has("self_destruct_kills"))
            response.setSelf_destruct_kills(Float.parseFloat(json.getString("self_destruct_kills")));
        if(json.has("enemies_empd_most_in_game"))
            response.setEnemies_empd_most_in_game(Float.parseFloat(json.getString("enemies_empd_most_in_game")));
        if(json.has("enemies_hacked_most_in_game"))
            response.setEnemies_hacked_most_in_game(Float.parseFloat(json.getString("enemies_hacked_most_in_game")));
        if(json.has("enemies_hacked"))
            response.setEnemies_hacked(Float.parseFloat(json.getString("enemies_hacked")));

    } catch (JSONException e) {
        Log.e("MYAPP", "unexpected JSON exception", e);
    }

        return response;
    }

    private SpecificStats parseSpc(JSONObject json)
    {
        SpecificStats response = new SpecificStats();

        try {

        if(json.has("charge_kills"))
            response.setCharge_kills(Float.parseFloat(json.getString("float charge_kills")));
        if(json.has("earthshatter_kills"))
            response.setEarthshatter_kills(Float.parseFloat(json.getString("float earthshatter_kills")));
        if(json.has("damage_blocked_most_in_game"))
            response.setDamage_blocked_most_in_game(Float.parseFloat(json.getString("float damage_blocked_most_in_game")));
        if(json.has("fire_strike_kills_most_in_game"))
            response.setFire_strike_kills_most_in_game(Float.parseFloat(json.getString("float fire_strike_kills_most_in_game")));
        if(json.has("earthshatter_kills_most_in_game"))
            response.setEarthshatter_kills_most_in_game(Float.parseFloat(json.getString("float earthshatter_kills_most_in_game")));
        if(json.has("damage_blocked"))
            response.setDamage_blocked(Float.parseFloat(json.getString("float damage_blocked")));
        if(json.has("charge_kills_most_in_game"))
            response.setCharge_kills_most_in_game(Float.parseFloat(json.getString("float charge_kills_most_in_game")));
        if(json.has("fire_strike_kills"))
            response.setFire_strike_kills(Float.parseFloat(json.getString("float fire_strike_kills")));
        if(json.has("pulse_bomb_kills_most_in_game"))
            response.setPulse_bomb_kills_most_in_game(Float.parseFloat(json.getString("float pulse_bomb_kills_most_in_game")));
        if(json.has("pulse_bombs_attached_most_in_game"))
            response.setPulse_bombs_attached_most_in_game(Float.parseFloat(json.getString("float pulse_bombs_attached_most_in_game")));
        if(json.has("pulse_bombs_attached"))
            response.setPulse_bombs_attached(Float.parseFloat(json.getString("float pulse_bombs_attached")));
        if(json.has("pulse_bomb_kills"))
            response.setPulse_bomb_kills(Float.parseFloat(json.getString("float pulse_bomb_kills")));
        if(json.has("melee_final_blows_most_in_game"))
            response.setMelee_final_blows_most_in_game(Float.parseFloat(json.getString("float melee_final_blows_most_in_game")));
        if(json.has("transcendence_healing_best"))
            response.setTranscendence_healing_best(Float.parseFloat(json.getString("float transcendence_healing_best")));
        if(json.has("rip_tire_kills_most_in_game"))
            response.setRip_tire_kills_most_in_game(Float.parseFloat(json.getString("float rip_tire_kills_most_in_game")));
        if(json.has("enemies_trapped_a_minute"))
            response.setEnemies_trapped_a_minute(Float.parseFloat(json.getString("float enemies_trapped_a_minute")));
        if(json.has("rip_tire_kills"))
            response.setRip_tire_kills(Float.parseFloat(json.getString("float rip_tire_kills")));
        if(json.has("enemies_trapped"))
            response.setEnemies_trapped(Float.parseFloat(json.getString("float enemies_trapped")));
        if(json.has("enemies_trapped_most_in_game"))
            response.setEnemies_trapped_most_in_game(Float.parseFloat(json.getString("float enemies_trapped_most_in_game")));
        if(json.has("deadeye_kills_most_in_game"))
            response.setDeadeye_kills_most_in_game(Float.parseFloat(json.getString("float deadeye_kills_most_in_game")));
        if(json.has("fan_the_hammer_kills"))
            response.setFan_the_hammer_kills(Float.parseFloat(json.getString("float fan_the_hammer_kills")));
        if(json.has("deadeye_kill"))
            response.setDeadeye_kill(Float.parseFloat(json.getString("float deadeye_kill")));
        if(json.has("melee_kills_most_in_game"))
            response.setMelee_kills_most_in_game(Float.parseFloat(json.getString("float melee_kills_most_in_game")));
        if(json.has("melee_kills"))
            response.setMelee_kills(Float.parseFloat(json.getString("float melee_kills")));
        if(json.has("players_knocked_back"))
            response.setPlayers_knocked_back(Float.parseFloat(json.getString("float players_knocked_back")));
        if(json.has("jump_pack_kills_most_in_game"))
            response.setJump_pack_kills_most_in_game(Float.parseFloat(json.getString("float jump_pack_kills_most_in_game")));
        if(json.has("melee_final_blow_most_in_game"))
            response.setMelee_final_blow_most_in_game(Float.parseFloat(json.getString("float melee_final_blow_most_in_game")));
        if(json.has("jump_pack_kills"))
            response.setJump_pack_kills(Float.parseFloat(json.getString("float jump_pack_kills")));
        if(json.has("players_knocked_back_most_in_game"))
            response.setPlayers_knocked_back_most_in_game(Float.parseFloat(json.getString("float players_knocked_back_most_in_game")));
        if(json.has("scatter_arrow_kills_most_in_game"))
            response.setScatter_arrow_kills_most_in_game(Float.parseFloat(json.getString("float scatter_arrow_kills_most_in_game")));
        if(json.has("scatter_arrow_kills"))
            response.setScatter_arrow_kills(Float.parseFloat(json.getString("float scatter_arrow_kills")));
        if(json.has("dragonstrike_kills"))
            response.setDragonstrike_kills(Float.parseFloat(json.getString("float dragonstrike_kills")));
        if(json.has("dragonstrike_kills_most_in_game"))
            response.setDragonstrike_kills_most_in_game(Float.parseFloat(json.getString("float dragonstrike_kills_most_in_game")));
        if(json.has("rocket_direct_hits"))
            response.setRocket_direct_hits(Float.parseFloat(json.getString("float rocket_direct_hits")));
        if(json.has("barrage_kills_most_in_game"))
            response.setBarrage_kills_most_in_game(Float.parseFloat(json.getString("float barrage_kills_most_in_game")));
        if(json.has("barrage_kills"))
            response.setBarrage_kills(Float.parseFloat(json.getString("float barrage_kills")));
        if(json.has("rocket_direct_hits_most_in_gam"))
            response.setRocket_direct_hits_most_in_gam(Float.parseFloat(json.getString("float rocket_direct_hits_most_in_gam")));
        if(json.has("whole_hog_kills"))
            response.setWhole_hog_kills(Float.parseFloat(json.getString("float whole_hog_kills")));
        if(json.has("hook_accuracy_best_in_game"))
            response.setHook_accuracy_best_in_game(Float.parseFloat(json.getString("float hook_accuracy_best_in_game")));
        if(json.has("enemies_hooked_most_in_game"))
            response.setEnemies_hooked_most_in_game(Float.parseFloat(json.getString("float enemies_hooked_most_in_game")));
        if(json.has("hook_accuracy"))
            response.setHook_accuracy(Float.parseFloat(json.getString("float hook_accuracy")));
        if(json.has("enemies_hooked"))
            response.setEnemies_hooked(Float.parseFloat(json.getString("float enemies_hooked")));
        if(json.has("whole_hog_kills_most_in_game"))
            response.setWhole_hog_kills_most_in_game(Float.parseFloat(json.getString("float whole_hog_kills_most_in_game")));
        if(json.has("hooks_attempted"))
            response.setHooks_attempted(Float.parseFloat(json.getString("float hooks_attempted")));
        if(json.has("lifetime_graviton_surge_kills"))
            response.setLifetime_graviton_surge_kills(Float.parseFloat(json.getString("float lifetime_graviton_surge_kills")));
        if(json.has("graviton_surge_kills_most_in_game"))
            response.setGraviton_surge_kills_most_in_game(Float.parseFloat(json.getString("float graviton_surge_kills_most_in_game")));
        if(json.has("lifetime_energy_accumulation"))
            response.setLifetime_energy_accumulation(Float.parseFloat(json.getString("float lifetime_energy_accumulation")));
        if(json.has("energy_maximum"))
            response.setEnergy_maximum(Float.parseFloat(json.getString("float energy_maximum")));
        if(json.has("projected_barriers_applied"))
            response.setProjected_barriers_applied(Float.parseFloat(json.getString("float projected_barriers_applied")));
        if(json.has("high_energy_kills_most_in_game"))
            response.setHigh_energy_kills_most_in_game(Float.parseFloat(json.getString("float high_energy_kills_most_in_game")));
        if(json.has("high_energy_kill"))
            response.setHigh_energy_kill(Float.parseFloat(json.getString("float high_energy_kill")));
        if(json.has("turret_kills"))
            response.setTurret_kills(Float.parseFloat(json.getString("float turret_kills")));
        if(json.has("molten_core_kills"))
            response.setMolten_core_kills(Float.parseFloat(json.getString("float molten_core_kills")));
        if(json.has("torbjorn_kills"))
            response.setTorbjorn_kills(Float.parseFloat(json.getString("float torbjorn_kills")));
        if(json.has("torbjorn_kills_most_in_game"))
            response.setTorbjorn_kills_most_in_game(Float.parseFloat(json.getString("float torbjorn_kills_most_in_game")));
        if(json.has("armor_packs_created"))
            response.setArmor_packs_created(Float.parseFloat(json.getString("float armor_packs_created")));
        if(json.has("molten_core_kills_most_in_gam"))
            response.setMolten_core_kills_most_in_gam(Float.parseFloat(json.getString("float molten_core_kills_most_in_gam")));
        if(json.has("players_resurrected"))
            response.setPlayers_resurrected(Float.parseFloat(json.getString("float players_resurrected")));
        if(json.has("players_resurrected_most_in_game"))
            response.setPlayers_resurrected_most_in_game(Float.parseFloat(json.getString("float players_resurrected_most_in_game")));
        if(json.has("tactical_visor_kills"))
            response.setTactical_visor_kills(Float.parseFloat(json.getString("float tactical_visor_kills")));
        if(json.has("helix_rockets_kills"))
            response.setHelix_rockets_kills(Float.parseFloat(json.getString("float helix_rockets_kills")));
        if(json.has("tactical_visor_kills_most_in_game"))
            response.setTactical_visor_kills_most_in_game(Float.parseFloat(json.getString("float tactical_visor_kills_most_in_game")));
        if(json.has("helix_rockets_kills_most_in_game"))
            response.setHelix_rockets_kills_most_in_game(Float.parseFloat(json.getString("float helix_rockets_kills_most_in_game")));
        if(json.has("biotic_field_healing_done"))
            response.setBiotic_field_healing_done(Float.parseFloat(json.getString("float biotic_field_healing_done")));
        if(json.has("biotic_fields_deploye"))
            response.setBiotic_fields_deploye(Float.parseFloat(json.getString("float biotic_fields_deploye")));
        if(json.has("nano_boost_assists"))
            response.setNano_boost_assists(Float.parseFloat(json.getString("float nano_boost_assists")));
        if(json.has("unscoped_accuracy"))
            response.setUnscoped_accuracy(Float.parseFloat(json.getString("float unscoped_accuracy")));
        if(json.has("nano_boosts_applied"))
            response.setNano_boosts_applied(Float.parseFloat(json.getString("float nano_boosts_applied")));
        if(json.has("nano_boost_assists_most_in_game"))
            response.setNano_boost_assists_most_in_game(Float.parseFloat(json.getString("float nano_boost_assists_most_in_game")));
        if(json.has("venom_mine_kills"))
            response.setVenom_mine_kills(Float.parseFloat(json.getString("float venom_mine_kills")));
        if(json.has("scoped_accuracy_best_in_game"))
            response.setScoped_accuracy_best_in_game(Float.parseFloat(json.getString("float scoped_accuracy_best_in_game")));
        if(json.has("scoped_hits"))
            response.setScoped_hits(Float.parseFloat(json.getString("float scoped_hits")));
        if(json.has("scoped_critical_hits_most_in_game"))
            response.setScoped_critical_hits_most_in_game(Float.parseFloat(json.getString("float scoped_critical_hits_most_in_game")));
        if(json.has("venom_mine_kills_most_in_game"))
            response.setVenom_mine_kills_most_in_game(Float.parseFloat(json.getString("float venom_mine_kills_most_in_game")));
        if(json.has("recon_assists_most_in_game"))
            response.setRecon_assists_most_in_game(Float.parseFloat(json.getString("float recon_assists_most_in_game")));
        if(json.has("scoped_accuracy"))
            response.setScoped_accuracy(Float.parseFloat(json.getString("float scoped_accuracy")));
        if(json.has("scoped_critical_hits"))
            response.setScoped_critical_hits(Float.parseFloat(json.getString("float scoped_critical_hits")));
        if(json.has("scoped_shots"))
            response.setScoped_shots(Float.parseFloat(json.getString("float scoped_shots")));
        if(json.has("dragonblades"))
            response.setDragonblades(Float.parseFloat(json.getString("float dragonblades")));
        if(json.has("damage_reflected"))
            response.setDamage_reflected(Float.parseFloat(json.getString("float damage_reflected")));
        if(json.has("damage_reflected_most_in_game"))
            response.setDamage_reflected_most_in_game(Float.parseFloat(json.getString("float damage_reflected_most_in_game")));
        if(json.has("dragonblade_kills_most_in_game"))
            response.setDragonblade_kills_most_in_game(Float.parseFloat(json.getString("float dragonblade_kills_most_in_game")));
        if(json.has("dragonblade_kills"))
            response.setDragonblade_kills(Float.parseFloat(json.getString("float dragonblade_kills")));
        if(json.has("death_blossom_kills"))
            response.setDeath_blossom_kills(Float.parseFloat(json.getString("float death_blossom_kills")));
        if(json.has("death_blossom_kills_most_in_game"))
            response.setDeath_blossom_kills_most_in_game(Float.parseFloat(json.getString("float death_blossom_kills_most_in_game")));
        if(json.has("enemies_frozen_most_in_game"))
            response.setEnemies_frozen_most_in_game(Float.parseFloat(json.getString("float enemies_frozen_most_in_game")));
        if(json.has("enemies_frozen"))
            response.setEnemies_frozen(Float.parseFloat(json.getString("float enemies_frozen")));
        if(json.has("blizzard_kills"))
            response.setBlizzard_kills(Float.parseFloat(json.getString("float blizzard_kills")));
        if(json.has("blizzard_kills_most_in_game"))
            response.setBlizzard_kills_most_in_game(Float.parseFloat(json.getString("float blizzard_kills_most_in_game")));
        if(json.has("recon_kills_most_in_game"))
            response.setRecon_kills_most_in_game(Float.parseFloat(json.getString("float recon_kills_most_in_game")));
        if(json.has("tank_kills_most_in_game"))
            response.setTank_kills_most_in_game(Float.parseFloat(json.getString("float tank_kills_most_in_game")));
        if(json.has("sentry_kills"))
            response.setSentry_kills(Float.parseFloat(json.getString("float sentry_kills")));
        if(json.has("tank_kills"))
            response.setTank_kills(Float.parseFloat(json.getString("float tank_kills")));
        if(json.has("recon_kills"))
            response.setRecon_kills(Float.parseFloat(json.getString("float recon_kills")));
        if(json.has("sentry_kills_most_in_game"))
            response.setSentry_kills_most_in_game(Float.parseFloat(json.getString("float sentry_kills_most_in_game")));
        if(json.has("teleporter_uptime"))
            response.setTeleporter_uptime(Float.parseFloat(json.getString("float teleporter_uptime")));
        if(json.has("shields_provided_most_in_game"))
            response.setShields_provided_most_in_game(Float.parseFloat(json.getString("float shields_provided_most_in_game")));
        if(json.has("players_teleported_most_in_game"))
            response.setPlayers_teleported_most_in_game(Float.parseFloat(json.getString("float players_teleported_most_in_game")));
        if(json.has("players_teleported"))
            response.setPlayers_teleported(Float.parseFloat(json.getString("float players_teleported")));
        if(json.has("shields_provided"))
            response.setShields_provided(Float.parseFloat(json.getString("float shields_provided")));
        if(json.has("teleporter_uptime_best_in_game"))
            response.setTeleporter_uptime_best_in_game(Float.parseFloat(json.getString("float teleporter_uptime_best_in_game")));
        if(json.has("sentry_turret_kills_most_in_game"))
            response.setSentry_turret_kills_most_in_game(Float.parseFloat(json.getString("float sentry_turret_kills_most_in_game")));
        if(json.has("sentry_turret_kills"))
            response.setSentry_turret_kills(Float.parseFloat(json.getString("float sentry_turret_kills")));
        if(json.has("mechs_called_most_in_game"))
            response.setMechs_called_most_in_game(Float.parseFloat(json.getString("float mechs_called_most_in_game")));
        if(json.has("mech_deaths"))
            response.setMech_deaths(Float.parseFloat(json.getString("float mech_deaths")));
        if(json.has("mechs_called"))
            response.setMechs_called(Float.parseFloat(json.getString("float mechs_called")));
        if(json.has("sound_barriers_provided_most_in_game"))
            response.setSound_barriers_provided_most_in_game(Float.parseFloat(json.getString("float sound_barriers_provided_most_in_game")));
        if(json.has("sound_barriers_provided"))
            response.setSound_barriers_provided(Float.parseFloat(json.getString("float sound_barriers_provided")));
        } catch (JSONException e) {
            Log.e("MYAPP", "unexpected JSON exception", e);
        }


        return response;
    }

    @Override
    protected HeroOutput doInBackground(Void... Params) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String statsJsonStr = null;

        try {
            final String BASE_URL = "https://owapi.net/api/v3/u/";

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            String pref_battleTag = preferences.getString("currentBattleTag", "");

            final String BATTLE_TAG = pref_battleTag;
            final String DATATYPE = "heroes";


            Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendPath(BATTLE_TAG)
                    .appendPath(DATATYPE)
                    .build();

            URL url = new URL(builtUri.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
            urlConnection.setRequestProperty("Accept","*/*");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }
            statsJsonStr = buffer.toString();

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);

            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        try {
            return getStatsDataFromJson(statsJsonStr);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(HeroOutput heroOutput) {
        if (heroOutput != null) {
            ArrayList<HeroItem> heroesItems = heroOutput.getHeroesItem();
            HashMap<String, HeroStats> heroesStats = heroOutput.getHeroesStats();

        Collections.sort(heroesItems, new Comparator<HeroItem>() {
            @Override
            public int compare(HeroItem heroItem, HeroItem t1) {
                float diff = t1.getPlayTimeAsFloat() - heroItem.getPlayTimeAsFloat();
                if (diff < 0)
                    return -1;
                else if (diff == 0)
                    return 0;
                else
                    return 1;
            }
        });
            adapter.addAll(heroesItems);
            adapter.notifyDataSetChanged();

            caller.asyncResponse(heroesStats);
        }
    }
}
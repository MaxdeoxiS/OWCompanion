package com.example.maxime.overwatchstats.model;

import android.util.Log;

import com.example.maxime.overwatchstats.tools.HeroXmlParser;

import org.xmlpull.v1.XmlPullParserException;

import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

public class Heroes implements Serializable {

    private List<Hero> heroes;

    public Heroes(InputStream in) {

        HeroXmlParser parser = new HeroXmlParser();

        try {

            this.heroes = parser.parse(in);

        } catch (XmlPullParserException x) {
            Log.e("error", "Parser cant parse this file");
        } catch (java.io.IOException io) {
            Log.e("erreor", "fichier non trouvé");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (java.io.IOException io) {
                    Log.e("erreor", "fichier non trouvé");
                }
            }
        }
    }

    public List<Hero> getHeroes() {
        return heroes;
    }

    public String toString() {
       return this.getHeroes().size() + " heroes found.";
    }

    public void setHeroes(List<Hero> heroes) {
        this.heroes = heroes;
    }

    public Hero findHeroByName(String name) {
        Hero hero = null;

        for (Hero h : this.getHeroes()) {
            if(h.getNickname().toLowerCase().equals(name)) {
                hero = h;
                break;
            }
        }
        return hero;
    }
}

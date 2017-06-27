package com.example.maxime.overwatchstats.tools;


import android.util.Xml;

import com.example.maxime.overwatchstats.model.Ability;
import com.example.maxime.overwatchstats.model.Hero;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class HeroXmlParser {
    private static final String ns = null;

    public List<Hero> parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            in.close();
        }
    }

    private List<Hero> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<Hero> heroes = new ArrayList<Hero>();

        parser.require(XmlPullParser.START_TAG, ns, "heroes");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("hero")) {
                heroes.add(readEntry(parser));
            } else {
                skip(parser);
            }
        }
        return heroes;
    }

    // Parses the contents of an entry. If it encounters a title, summary, or link tag, hands them off
    // to their respective "read" methods for processing. Otherwise, skips the tag.
    private Hero readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "hero");
        String nickname = null;
        String firstName = null;
        String lastName = null;
        String x = null;
        String y = null;
        String age = null;
        String desc = null;
        List<Ability> abilities = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("surname")) {
                nickname = readData(parser, "surname");
            } else if (name.equals("firstname")) {
                firstName = readData(parser, "firstname");
            } else if (name.equals("lastname")) {
                lastName = readData(parser, "lastname");
            } else if (name.equals("x")) {
                x = readData(parser, "x");
            } else if (name.equals("y")) {
                y = readData(parser, "y");
            } else if (name.equals("age")) {
                age = readData(parser, "age");
            } else if (name.equals("abilities")) {
                abilities = readAbilities(parser);
            } else if (name.equals("description")) {
                desc = readData(parser, "description");
            } else {
                skip(parser);
            }
        }

        //TODO change 1-before-last parameter
        return new Hero(nickname, firstName, lastName, Double.parseDouble(x), Double.parseDouble(y), Integer.parseInt(age), desc, abilities);
    }

    private String readData(XmlPullParser parser, String nodeName) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, nodeName);
        String result = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, nodeName);
        return result;
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private List<Ability> readAbilities(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "abilities");
        List<Ability> result = new ArrayList<Ability>();

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("ability")) {
                result.add(readAbility(parser));
            } else {
                skip(parser);
            }
        }
        return result;
    }


    private Ability readAbility(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "ability");

        Ability result = new Ability();

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();

            if (name.equals("name")) {
                result.setName(readData(parser, "name"));
            } else if (name.equals("hero")) {
                result.setHero(readData(parser, "hero"));
            } else if (name.equals("type")) {
                result.setType(readData(parser, "type"));
            } else if (name.equals("speed")) {
                result.setSpeed(readData(parser, "speed"));
            } else if (name.equals("ammo")) {
                result.setAmmo(readData(parser, "ammo"));
            } else if (name.equals("heal")) {
                result.setHeal(readData(parser, "heal"));
            } else if (name.equals("health")) {
                result.setHealth(readData(parser, "health"));
            } else if (name.equals("ammoUse")) {
                result.setAmmoUse(readData(parser, "ammoUse"));
            } else if (name.equals("duration")) {
                result.setDuration(readData(parser, "duration"));
            } else if (name.equals("charge")) {
                result.setCharge(readData(parser, "charge"));
            } else if (name.equals("rate")) {
                result.setRate(readData(parser, "rate"));
            } else if (name.equals("reload")) {
                result.setReload(readData(parser, "reload"));
            } else if (name.equals("damage")) {
                result.setDamage(readData(parser, "damage"));
            } else if (name.equals("cooldown")) {
                result.setCooldown(readData(parser, "cooldown"));
            } else if (name.equals("range")) {
                result.setRange(readData(parser, "range"));
            } else if (name.equals("headshot")) {
                result.setHeadshot(readData(parser, "headshot"));
            } else if (name.equals("more")) {
                result.setMore(readData(parser, "more"));
            } else {
                skip(parser);
            }
        }
        return result;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

}
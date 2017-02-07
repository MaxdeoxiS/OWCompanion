package com.example.maxime.overwatchstats.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Heroes implements Serializable {

    private List<Hero> heroes;

    public Heroes() {
        this.heroes = new ArrayList<Hero>();
        
        Hero h = new Hero("Widowmaker");
        h.setFirstName("Amélie");
        h.setLastName("Lacroix");
        h.setX(45.89924700);
        h.setY(6.12938400);
        heroes.add(h);

        h = new Hero("Ana");
        h.setFirstName("Ana");
        h.setLastName("Amari");
        h.setX(30.048786);
        h.setY(31.235461);
        h.setAge(60);

        heroes.add(h);

        h = new Hero("Bastion");
        h.setFirstName("SST Laboratories Siege Automaton E54");
        h.setLastName("");
        h.setX(0);
        h.setY(0);
        h.setAge(30);

        heroes.add(h);

        h = new Hero("D.Va");
        h.setNickname("Dva");
        h.setFirstName("Hana");
        h.setLastName("Song");
        h.setX(35.161623);
        h.setY(129.046736);
        h.setAge(19);

        heroes.add(h);

        h = new Hero("Genji");
        h.setFirstName("Genji");
        h.setLastName("Shimada");
        h.setX(28.668664);
        h.setY(84.039920);
        h.setAge(35);

        heroes.add(h);

        h = new Hero("Hanzo");
        h.setFirstName("Hanzo");
        h.setLastName("Shimada");
        h.setX(35.612765);
        h.setY(138.527966);
        h.setAge(38);

        heroes.add(h);

        h = new Hero("Junkrat");
        h.setFirstName("Junkrat");
        h.setLastName("Junkrat");
        h.setX(35.612765);
        h.setY(138.527966);
        h.setAge(38);

        heroes.add(h);

        h = new Hero("Lucio");
        h.setFirstName("Lucio Correia");
        h.setLastName("dos Santos");
        h.setX(-22.902317);
        h.setY(-43.180550);
        h.setAge(26);

        heroes.add(h);

        h = new Hero("McCree");
        h.setFirstName("Jesse");
        h.setLastName("McCree");
        h.setX(35.687214);
        h.setY(-105.937033);
        h.setAge(37);

        heroes.add(h);

        h = new Hero("Mei");
        h.setFirstName("Mei-Ling");
        h.setLastName("Zhou");
        h.setX(34.357751);
        h.setY(108.943833);
        h.setAge(31);

        heroes.add(h);

//        h = new Hero("Mercy");
//        h.setFirstName("Angela");
//        h.setLastName("Ziegler");
//        h.setX(47.377201);
//        h.setY(8.540833);
//        h.setAge(37);
//
//        heroes.add(h);
//
//        h = new Hero("Pharah");
//        h.setFirstName("Fareeha");
//        h.setLastName("Amari");
//        h.setX(30.013960);
//        h.setY(31.211787);
//        h.setAge(32);
//
//        heroes.add(h);
//
//        h = new Hero("Reaper");
//        h.setFirstName("Gabriel");
//        h.setLastName("Reyes");
//        h.setX(34.055459);
//        h.setY(-118.273060);
//        h.setAge(56);
//
//        heroes.add(h);
//
//        h = new Hero("Reinhardt");
//        h.setFirstName("Reinhardt");
//        h.setLastName("Wilhelm");
//        h.setX(48.777152);
//        h.setY(9.181677);
//        h.setAge(61);
//
//        heroes.add(h);
//
//        h = new Hero("Soldier: 76");
//        h.setNickname("soldier76");
//        h.setFirstName("Jack");
//        h.setLastName("Morrison");
//        h.setX(39.165618);
//        h.setY(-86.527555);
//        h.setAge(55);
//
//        heroes.add(h);

//        h = new Hero("Symmetra");
//        h.setFirstName("Satya");
//        h.setLastName("Vaswani");
//        h.setX(22.505421);
//        h.setY(88.303980);
//        h.setAge(28);
//
//        heroes.add(h);
//
//        h = new Hero("Sombra");
//        h.setFirstName("???");
//        h.setLastName("???");
//        h.setX(17.531448);
//        h.setY(-98.579759);
//        h.setAge(30);
//
//        heroes.add(h);
//
//        h = new Hero("Torbjorn");
//        h.setFirstName("Torjborn");
//        h.setLastName("Lindholm");
//        h.setX(57.709584);
//        h.setY(11.970582);
//        h.setAge(57);
//
//        heroes.add(h);
//
//        h = new Hero("Tracer");
//        h.setFirstName("Lena");
//        h.setLastName("Oxton");
//        h.setX(51.508049);
//        h.setY(-0.118084);
//        h.setAge(26);
//
//        heroes.add(h);
//
//        h = new Hero("Winston");
//        h.setFirstName("Winston");
//        h.setLastName("");
//        h.setX(36.140779);
//        h.setY(-5.354291);
//        h.setAge(29);
//
//        heroes.add(h);
//
//        h = new Hero("Zarya");
//        h.setFirstName("Aleksandra");
//        h.setLastName("Zaryanova");
//        h.setX(56.017824);
//        h.setY(92.892508);
//        h.setAge(28);
//
//        heroes.add(h);
//
//        h = new Hero("Zenyatta");
//        h.setFirstName("Tekhartha");
//        h.setLastName("Zenyatta");
//        h.setX(28.649383);
//        h.setY(82.677616);
//        h.setAge(20);
//
//        heroes.add(h);
    }

    public List<Hero> getHeroes() {
        return heroes;
    }

    public void setHeroes(List<Hero> heroes) {
        this.heroes = heroes;
    }
}

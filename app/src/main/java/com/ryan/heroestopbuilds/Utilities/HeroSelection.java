package com.ryan.heroestopbuilds.Utilities;

import com.ryan.heroestopbuilds.R;

/**
 * Heroes for the expandable list view
 *
 * Created by Ryan on 11/30/2016.
 */

public enum HeroSelection {

        ABATHUR("Abathur", R.drawable.abathur),
        ALARAK("Alarak", R.drawable.alarak),
        ANUBARAK("Anub'arak", R.drawable.anubarak),
        AURIEL("Auriel", R.drawable.auriel),
        ARTANIS("Artanis", R.drawable.artanis),
        ARTHAS("Arthas", R.drawable.arthas),
        AZMODAN("Azmodan", R.drawable.azmodan),
        BRIGHTWING("Brightwing", R.drawable.brightwing),
        CHEN("Chen", R.drawable.chen),
        CHO("Cho", R.drawable.cho),
        CHROMIE("Chromie", R.drawable.chromie),
        DEHAKA("Dehaka", R.drawable.dehaka),
        DIABLO("Diablo", R.drawable.diablo),
        ETC("E.T.C.", R.drawable.elite_tauren_chieftain),
        FALSTAD("Falstad", R.drawable.falstad),
        GALL("Gall", R.drawable.gall),
        GAZLOWE("Gazlowe", R.drawable.gazlowe),
        GREYMANE("Greymane", R.drawable.greymane),
        GULDAN("Gul'dan", R.drawable.guldan),
        ILLIDAN("Illidan", R.drawable.illidan),
        JAINA("Jaina", R.drawable.jaina),
        JOHANNA("Johanna", R.drawable.johanna),
        KAELTHAS("Kael'thas", R.drawable.kaelthas),
        KERRIGAN("Kerrigan", R.drawable.kerrigan),
        KHARAZIM("Kharazim", R.drawable.kharazim),
        LEORIC("Leoric", R.drawable.leoric),
        LILI("Li Li", R.drawable.li_li),
        LIMING("Li-Ming", R.drawable.li_ming),
        LTMORALES("Lt. Morales", R.drawable.morales),
        LUNARA("Lunara", R.drawable.lunara),
        MALFURION("Malfurion", R.drawable.malfurion),
        MEDIVH("Medivh", R.drawable.medivh),
        MURADIN("Muradin", R.drawable.muradin),
        MURKY("Murky", R.drawable.murky),
        NAZEEBO("Nazeebo", R.drawable.nazeebo),
        NOVA("Nova", R.drawable.nova),
        RAYNOR("Raynor", R.drawable.raynor),
        REHGAR("Rehgar", R.drawable.rehgar),
        REXXAR("Rexxar", R.drawable.rexxar),
        SAMURO("Samuro", R.drawable.samuro),
        SGTHAMMER("Sgt. Hammer", R.drawable.sergeant_hammer),
        SONYA("Sonya", R.drawable.sonya),
        STITCHES("Stitches", R.drawable.stitches),
        SYLVANAS("Sylvanas", R.drawable.sylvanas),
        TASSADAR("Tassadar", R.drawable.tassadar),
        THEBUTCHER("The Butcher", R.drawable.the_butcher),
        THELOSTVIKINGS("The Lost Vikings", R.drawable.the_lost_vikings),
        THRALL("Thrall", R.drawable.thrall),
        TRACER("Tracer", R.drawable.tracer),
        TYCHUS("Tychus", R.drawable.tychus),
        TYRAEL("Tyrael", R.drawable.tyrael),
        TYRANDE("Tyrande", R.drawable.tyrande),
        UTHER("Uther", R.drawable.uther),
        VALLA("Valla", R.drawable.valla),
        VARIAN("Varian", R.drawable.varian),
        XUL("Xul", R.drawable.xul),
        ZAGARA("Zagara", R.drawable.zagara),
        ZARYA("Zarya", R.drawable.zarya),
        ZERATUL("Zeratul", R.drawable.zeratul);

        private final String name;
        private final int portrait;
        HeroSelection(String name, int portrait) {
            this.name = name;
            this.portrait = portrait;
        }
        public String getName() {return name;}
        public int getPortrait() {return portrait;}
}

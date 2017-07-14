package com.ryan.heroestopbuilds.Utilities;

import com.ryan.heroestopbuilds.R;

public enum HeroSelection {
    ABATHUR("Abathur", R.drawable.abathur),
    ALARAK("Alarak", R.drawable.alarak),
    ANUBARAK("Anub'arak", R.drawable.anubarak),
    AURIEL("Auriel", R.drawable.auriel),
    ARTANIS("Artanis", R.drawable.artanis),
    ARTHAS("Arthas", R.drawable.arthas),
    AZMODAN("Azmodan", R.drawable.azmodan),
    BRIGHTWING("Brightwing", R.drawable.brightwing),
    CASSIA("Cassia", R.drawable.cassia),
    CHEN("Chen", R.drawable.chen),
    CHO("Cho", R.drawable.cho),
    CHROMIE("Chromie", R.drawable.chromie),
    DVA("D.Va", R.drawable.dva),
    DEHAKA("Dehaka", R.drawable.dehaka),
    DIABLO("Diablo", R.drawable.diablo),
    ETC("E.T.C.", R.drawable.elite_tauren_chieftain),
    FALSTAD("Falstad", R.drawable.falstad),
    GALL("Gall", R.drawable.gall),
    GAZLOWE("Gazlowe", R.drawable.gazlowe),
    GENJI("Genji", R.drawable.genji),
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
    LUCIO("Lúcio", R.drawable.lucio),
    LUNARA("Lunara", R.drawable.lunara),
    MALFURION("Malfurion", R.drawable.malfurion),
    MALTHAEL("Malthael", R.drawable.malthael),
    MEDIVH("Medivh", R.drawable.medivh),
    MURADIN("Muradin", R.drawable.muradin),
    MURKY("Murky", R.drawable.murky),
    NAZEEBO("Nazeebo", R.drawable.nazeebo),
    NOVA("Nova", R.drawable.nova),
    PROBIUS("Probius", R.drawable.probius),
    RAGNAROS("Ragnaros", R.drawable.ragnaros),
    RAYNOR("Raynor", R.drawable.raynor),
    REHGAR("Rehgar", R.drawable.rehgar),
    REXXAR("Rexxar", R.drawable.rexxar),
    SAMURO("Samuro", R.drawable.samuro),
    SGTHAMMER("Sgt. Hammer", R.drawable.sergeant_hammer),
    SONYA("Sonya", R.drawable.sonya),
    STITCHES("Stitches", R.drawable.stitches),
    STUKOV("Stukov", R.drawable.stukov),
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
    VALEERA("Valeera", R.drawable.valeera),
    VALLA("Valla", R.drawable.valla),
    VARIAN("Varian", R.drawable.varian),
    XUL("Xul", R.drawable.xul),
    ZAGARA("Zagara", R.drawable.zagara),
    ZARYA("Zarya", R.drawable.zarya),
    ZERATUL("Zeratul", R.drawable.zeratul),
    ZULJIN("Zul'jin", R.drawable.zuljin);

    private final String name;
    private final int portrait;
    HeroSelection(String name, int portrait) {
        this.name = name;
        this.portrait = portrait;
    }
    public String getName() {return name;}
    public int getPortrait() {return portrait;}
}

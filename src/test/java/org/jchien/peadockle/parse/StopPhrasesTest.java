package org.jchien.peadockle.parse;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author jchien
 */
public class StopPhrasesTest {
    @Test
    public void allowed_returnTrue() {
        String[] allowed = {
                // from 705_valve.txt
                "7.05:",
                "* Enabled Treant Protector in Captains Mode",
                "* Towers base armor reduced by 2",
                "* Towers now gain 1 more armor per nearby enemy hero (3 total per enemy now)",
                "* Towers bonus armor based on nearby enemy heroes AoE increased from 1200 to 1400",
                "* Shrines base health regen reduced from 100 to 90",
                "* Intelligence based Spell Amplification increased from 1% per 16 Intelligence to 1% per 15",
                "* XP required to go from level 20 to 25 reduced from 10895 to 10000 (1600/1900/2200/2500/2695 to 1500/1750/2000/2250/2500)",
                "* Hand of Midas: XP bonus reduced from 2.5x to 1.75x",
                "* Hand of Midas: Gold bonus increased from 190 to 220",
                "* Orb of Venom: Slow duration reduced from 4 to 3",
                "* Orb of Venom: DPS increased from 3 to 5",
                "* Tranquil Boots: Requires Wind Lace instead of Ring of Protection",
                "* Tranquil Boots: No longer provides armor",
                "* Tranquil Boots: Active state regeneration increased from 12 to 14",
                "* Tranquil Boots: Active state movement speed increased from 85 to 90",
                "* Tranquil Boots: Disabled state movement speed increased from 55 to 65",
                "* Power Treads: Attribute bonus increased from 9 to 10",
                "* Quelling Blade: Bonus damage no longer stacks with Iron Talon",
                "* Abaddon: Strength gain reduced from 2.5 to 2.3",
                "* Ancient Apparition: Cold Feet damage per tick rescaled from 37.5/50/62.5/75 to 30/50/70/90",
                "* Ancient Apparition: Cold Feet cooldown from 13/11/9/7 to 10/9/8/7",
        };

        StopPhrases sp = new StopPhrases();
        for (String input : allowed) {
            Assert.assertTrue(sp.allowed(input));
        }
    }

    @Test
    public void allowed_returnFalse() {
        String[] disallowed = {
                // from 705_valve.txt
                "=====",
                "",
                "--",
                "General:",
                "Items:",
                "Heroes:",

                // from 704_reddit.txt
                "**Items**",
                "**Heroes**",

                // from 702_valve.txt
                "General Changes:",
                "Hero Changes:",
                // not in 702 but tested anyway
                "Item Changes:",
        };

        StopPhrases sp = new StopPhrases();
        for (String input : disallowed) {
            Assert.assertFalse(sp.allowed(input));
        }
    }
}

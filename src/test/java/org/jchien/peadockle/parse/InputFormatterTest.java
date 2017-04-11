package org.jchien.peadockle.parse;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author jchien
 */
public class InputFormatterTest {
    @Test
    public void format_705Examples() {
        // each subarray is a tuple of (input, expected output)
        String[][] tests = {
                {"7.05:", "7 05"}, // may want to consider keeping periods, but DotaDictionary is looking for word boundaries
                                   // using whitespace or end of line so grammatical periods (as opposed to decimals) are problematic until that changes
                {"=====", ""},
                {"", ""},
                {"General:", "general"},
                {"--", ""},
                {"* Enabled Treant Protector in Captains Mode", "enabled treant protector in captains mode"},
                {"* Towers base armor reduced by 2", "towers base armor reduced by 2"},
                {"* Towers now gain 1 more armor per nearby enemy hero (3 total per enemy now)", "towers now gain 1 more armor per nearby enemy hero 3 total per enemy now"},
                {"* Towers bonus armor based on nearby enemy heroes AoE increased from 1200 to 1400", "towers bonus armor based on nearby enemy heroes aoe increased from 1200 to 1400"},
                {"* Shrines base health regen reduced from 100 to 90", "shrines base health regen reduced from 100 to 90"},
                {"* Intelligence based Spell Amplification increased from 1% per 16 Intelligence to 1% per 15", "intelligence based spell amplification increased from 1 per 16 intelligence to 1 per 15"},
                {"* XP required to go from level 20 to 25 reduced from 10895 to 10000 (1600/1900/2200/2500/2695 to 1500/1750/2000/2250/2500)", "xp required to go from level 20 to 25 reduced from 10895 to 10000 1600 1900 2200 2500 2695 to 1500 1750 2000 2250 2500"},

                {"Items:",  "items"},
                {"--",  ""},
                {"* Hand of Midas: XP bonus reduced from 2.5x to 1.75x",  "hand of midas xp bonus reduced from 2 5x to 1 75x"},
                {"* Hand of Midas: Gold bonus increased from 190 to 220",  "hand of midas gold bonus increased from 190 to 220"},
                {"* Orb of Venom: Slow duration reduced from 4 to 3",  "orb of venom slow duration reduced from 4 to 3"},
                {"* Orb of Venom: DPS increased from 3 to 5",  "orb of venom dps increased from 3 to 5"},
                {"* Tranquil Boots: Requires Wind Lace instead of Ring of Protection",  "tranquil boots requires wind lace instead of ring of protection"},
                {"* Tranquil Boots: No longer provides armor",  "tranquil boots no longer provides armor"},
                {"* Tranquil Boots: Active state regeneration increased from 12 to 14",  "tranquil boots active state regeneration increased from 12 to 14"},
                {"* Tranquil Boots: Active state movement speed increased from 85 to 90",  "tranquil boots active state movement speed increased from 85 to 90"},
                {"* Tranquil Boots: Disabled state movement speed increased from 55 to 65",  "tranquil boots disabled state movement speed increased from 55 to 65"},
                {"* Power Treads: Attribute bonus increased from 9 to 10",  "power treads attribute bonus increased from 9 to 10"},
                {"* Quelling Blade: Bonus damage no longer stacks with Iron Talon",  "quelling blade bonus damage no longer stacks with iron talon"},

                {"Heroes:", "heroes"},
                {"--", ""},
                {"* Abaddon: Strength gain reduced from 2.5 to 2.3", "abaddon strength gain reduced from 2 5 to 2 3"},
                {"* Ancient Apparition: Cold Feet damage per tick rescaled from 37.5/50/62.5/75 to 30/50/70/90", "ancient apparition cold feet damage per tick rescaled from 37 5 50 62 5 75 to 30 50 70 90"},
                {"* Ancient Apparition: Cold Feet cooldown from 13/11/9/7 to 10/9/8/7", "ancient apparition cold feet cooldown from 13 11 9 7 to 10 9 8 7"},
                {"* Alchemist: Acid Spray radius rescaled from 625 to 400/475/550/625", "alchemist acid spray radius rescaled from 625 to 400 475 550 625"},
                {"* Alchemist: Chemical Rage cooldown increased from 45 to 55", "alchemist chemical rage cooldown increased from 45 to 55"},
                {"* Bane: Level 15 Talent increased from +25% XP Gain to +30%", "bane level 15 talent increased from 25 xp gain to 30"},
                {"* Bane: Level 20 Talent increased from +75 Enfeeble Damage Reduction to +90", "bane level 20 talent increased from 75 enfeeble damage reduction to 90"},
                {"* Brewmaster: Earth Primal Unit's Demolish now gives +90/180/270 damage vs buildings (effectively 300% of base damage, instead of 300% of total damage)", "brewmaster earth primal unit s demolish now gives 90 180 270 damage vs buildings effectively 300 of base damage instead of 300 of total damage"},
                {"* Brewmaster: Earth Primal Unit's BAT reduced from 1.35 to 1.25", "brewmaster earth primal unit s bat reduced from 1 35 to 1 25"},
                {"* Brewmaster: Level 15 Talent increased from +12% Magic Resistance to +15%", "brewmaster level 15 talent increased from 12 magic resistance to 15"},
                {"* Brewmaster: Level 20 Talent increased from -35s Respawn Time to -40s", "brewmaster level 20 talent increased from 35s respawn time to 40s"},
                {"* Brewmaster: Level 25 Talent increased from +75 Damage to +120", "brewmaster level 25 talent increased from 75 damage to 120"},
                {"* Brewmaster: Level 25 Talent changed from +20 Primal Split Unit Armor to +2000 Health to Primal Split Units", "brewmaster level 25 talent changed from 20 primal split unit armor to 2000 health to primal split units"},
        };

        for (String[] test : tests) {
            Assert.assertEquals(test[1], InputFormatter.format(test[0]));
        }
    }
}

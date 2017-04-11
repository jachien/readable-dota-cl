package org.jchien.peadockle.parse;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author jchien
 */
public class VersionParserTest {
    @Test
    public void parseVersion_found() {
        // each subarray is a tuple of (input, expected output)
        String[][] tests = {
                {"7.05", "7.05"},
                {"7.05a", "7.05a"},
                {"7.05b", "7.05b"},
                {"7.05c", "7.05c"},
                {"6.99", "6.99"},
                {"6.99a", "6.99a"},

                {"7.05:", "7.05"},
                {"7.05a:", "7.05a"},
                {"7.05b:", "7.05b"},
                {"7.05c:", "7.05c"},
                {"6.99:", "6.99"},
                {"6.99a:", "6.99a"},

                {"  7.05", "7.05"},
                {"  7.05a", "7.05a"},
                {"\t7.05", "7.05"},
                {"\t7.05a", "7.05a"},
                {"* 7.05", "7.05"},
                {"* 7.05a", "7.05a"},
                {"###7.05", "7.05"},
                {"###7.05a", "7.05a"},
                {"=== 7.05 ===", "7.05"},
                {"=== 7.05a ===", "7.05a"},
                {"===7.05===", "7.05"},
                {"===7.05a===", "7.05a"},
        };

        VersionParser p = new VersionParser();
        for (String[] test : tests) {
            Assert.assertEquals("failed for input \"" + test[0] + "\"", test[1], p.parseVersion(test[0]));
        }
    }

    @Test
    public void parseVersion_notFound_returnNull() {
        String[] noVersionInputs = {
                // from 705_valve.txt
                "",
                "=====",
                "General:",
                "--",
                "* Enabled Treant Protector in Captains Mode",
                "* Towers base armor reduced by 2",
                "* Towers now gain 1 more armor per nearby enemy hero (3 total per enemy now)",
                "* Towers bonus armor based on nearby enemy heroes AoE increased from 1200 to 1400",
                "* Shrines base health regen reduced from 100 to 90",
                "* Intelligence based Spell Amplification increased from 1% per 16 Intelligence to 1% per 15",
                "* XP required to go from level 20 to 25 reduced from 10895 to 10000 (1600/1900/2200/2500/2695 to 1500/1750/2000/2250/2500)",
                "Items:",
                "* Hand of Midas: XP bonus reduced from 2.5x to 1.75x", // 1.75x has the format a very liberal matcher would find, but we're stricter

                // now other stuff
                "6.88 Gameplay Update", // this style has not used in patch notes starting with 7.00 so we won't handle it
                "7.05A", // minor versions must be lowercase!
        };

        VersionParser p = new VersionParser();
        for (String input : noVersionInputs) {
            Assert.assertNull("failed for input \"" + input + "\"", p.parseVersion(input));
        }
    }
}

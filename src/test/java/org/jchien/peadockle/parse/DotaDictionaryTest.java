package org.jchien.peadockle.parse;

import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

/**
 * @author jchien
 */
public class DotaDictionaryTest {
    @Test
    public void parseEntryIds_general() {
        Entry[] entries = {
                new Entry(0, "fuu", "Foo", new String[]{"Foobar"}),
                new Entry(1, "blim_blam", "Blim Blam", null),
        };

        DotaDictionary d = DotaDictionary.load(entries);
        Set<Integer> ids;

        ids = d.parseEntryIds("Foo: New hero added.");
        Assert.assertEquals(1, ids.size());
        Assert.assertEquals(true, ids.contains(0));

        // test alternate name
        ids = d.parseEntryIds("Foobar: New hero added.");
        Assert.assertEquals(1, ids.size());
        Assert.assertEquals(true, ids.contains(0));

        ids = d.parseEntryIds("Blim Blam: This is a real hero.");
        Assert.assertEquals(1, ids.size());
        Assert.assertEquals(true, ids.contains(1));

        ids = d.parseEntryIds("This is a line about nothing.");
        Assert.assertEquals(0, ids.size());

        ids = d.parseEntryIds("Here we talk about Foo in the middle of the line.");
        Assert.assertEquals(1, ids.size());
        Assert.assertEquals(true, ids.contains(0));

        ids = d.parseEntryIds("Here we talk about foobar in the middle of the line with no capitalization.");
        Assert.assertEquals(1, ids.size());
        Assert.assertEquals(true, ids.contains(0));

        ids = d.parseEntryIds("Here the line ends with Foo");
        Assert.assertEquals(1, ids.size());
        Assert.assertEquals(true, ids.contains(0));

        ids = d.parseEntryIds("Here the line ends with punctuation after Foo.");
        Assert.assertEquals(1, ids.size());
        Assert.assertEquals(true, ids.contains(0));

        ids = d.parseEntryIds("Here the line ends with Foobar");
        Assert.assertEquals(1, ids.size());
        Assert.assertEquals(true, ids.contains(0));

        ids = d.parseEntryIds("Here the line ends with punctuation after Foobar.");
        Assert.assertEquals(1, ids.size());
        Assert.assertEquals(true, ids.contains(0));

        ids = d.parseEntryIds("Here the line ends with blim blam");
        Assert.assertEquals(1, ids.size());
        Assert.assertEquals(true, ids.contains(1));

        ids = d.parseEntryIds("Here the line ends with punctuation after blim blam.");
        Assert.assertEquals(1, ids.size());
        Assert.assertEquals(true, ids.contains(1));

        ids = d.parseEntryIds("Foo: Also known as Foobar.");
        Assert.assertEquals(1, ids.size());
        Assert.assertEquals(true, ids.contains(0));

        ids = d.parseEntryIds("Foo: Can't be picked when Blim Blam is picked.");
        Assert.assertEquals(2, ids.size());
        Assert.assertEquals(true, ids.contains(0));
        Assert.assertEquals(true, ids.contains(1));
    }
}

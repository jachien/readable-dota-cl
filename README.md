Readable-Dota-Cl

The goal is to generate pretty, readable changelogs from either Reddit posts or Steam news with minimal human interaction.

In order to generate a changelog the steps are

1. Copy the patch notes text to src/resources (e.g. src/resources/705_valve.txt)
2. Edit org.jchien.peadockle.Driver to parse the new patch and tell it to write to your path of choice.
3. Review the generated html and manually fix anything wrong.

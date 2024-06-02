package net.orangejewce.pmmo_xp_bottles.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.orangejewce.pmmo_xp_bottles.init.PmmoXpBottlesModItems;
import net.orangejewce.pmmo_xp_bottles.pmmobottlesMod;

public class ItemModelGen extends ItemModelProvider {
    public ItemModelGen(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator.getPackOutput(), pmmobottlesMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        for (RegistryObject<Item> item : PmmoXpBottlesModItems.ALL_BOTTLES.values()) {
            generateBottleModel(item.get());
        }
    }

    private void generateBottleModel(Item item) {
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(item);
        String itemPath = id.getPath();

        // Extract the skill and tier from the item path
        String[] parts = itemPath.split("_");
        if (parts.length >= 3) {
            String skill = parts[0];
            String tier = parts[1];

            // Generate the model for the empty bottle
            var emptyModel = this.getBuilder(skill + "_" + tier + "_bottle_e")
                    .parent(this.getExistingFile(this.mcLoc("item/generated")))
                    .texture("layer0", new ResourceLocation(pmmobottlesMod.MOD_ID, "item/" + skill + "_" + tier + "_bottle_e"));

            // Generate the model for the filled bottle with override
            this.getBuilder(skill + "_" + tier + "_bottle")
                    .parent(this.getExistingFile(this.mcLoc("item/generated")))
                    .texture("layer0", new ResourceLocation(pmmobottlesMod.MOD_ID, "item/" + skill + "_" + tier + "_bottle"))
                    .override()
                    .predicate(new ResourceLocation(pmmobottlesMod.MOD_ID, "empty"), 1f)
                    .model(emptyModel)
                    .end();
        }
    }
}

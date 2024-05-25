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
            this.generate(item.get());
        }
    }

    private void generate(Item item) {
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(item);
        var empty = this.getBuilder(id.getPath() + "_e")
                .parent(this.getExistingFile(this.mcLoc("item/generated")))
                .texture("layer0", texture(id.getPath(), "_e"));

        this.getBuilder(id.getPath())
                .parent(this.getExistingFile(this.mcLoc("item/generated")))
                .texture("layer0", texture(id.getPath(), ""))
                .override()
                .predicate(new ResourceLocation("empty"), 1f)
                .model(empty)
                .end();
    }

    private ResourceLocation texture(String path, String append) {
        return new ResourceLocation(pmmobottlesMod.MOD_ID, "items/" + path + append);
    }
}

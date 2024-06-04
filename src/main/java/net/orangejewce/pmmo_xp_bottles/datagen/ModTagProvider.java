package net.orangejewce.pmmo_xp_bottles.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import net.orangejewce.pmmo_xp_bottles.init.PmmoXpBottlesModItems;
import net.orangejewce.pmmo_xp_bottles.pmmobottlesMod;

import java.util.concurrent.CompletableFuture;

public class ModTagProvider extends ItemTagsProvider {
    public static final TagKey<Item> ALL_BOTTLES = TagKey.create(Registries.ITEM, new ResourceLocation(pmmobottlesMod.MOD_ID, "all_bottles"));
    public ModTagProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> pBlockTags, ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pBlockTags, pmmobottlesMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        var tag = tag(ALL_BOTTLES);
        PmmoXpBottlesModItems.ALL_BOTTLES.values().stream().map(RegistryObject::get).forEach(tag::add);
    }
}
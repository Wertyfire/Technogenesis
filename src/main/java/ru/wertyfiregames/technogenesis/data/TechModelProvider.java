package ru.wertyfiregames.technogenesis.data;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.data.PackOutput;
import ru.wertyfiregames.technogenesis.Technogenesis;
import ru.wertyfiregames.technogenesis.init.TechBlocks;
import ru.wertyfiregames.technogenesis.init.TechItems;

public class TechModelProvider extends ModelProvider {
    public TechModelProvider(PackOutput output) {
        super(output, Technogenesis.MODID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        //Blocks
        blockModels.createTrivialCube(TechBlocks.CASSITERITE_ORE.get());
        blockModels.createTrivialCube(TechBlocks.DEEPSLATE_CASSITERITE_ORE.get());
        blockModels.createTrivialCube(TechBlocks.CASSITERITE_BLOCK.get());
        blockModels.createNonTemplateModelBlock(TechBlocks.BURNER_PRESS.get());

        //Items
        itemModels.generateFlatItem(TechItems.CASSITERITE.get(), ModelTemplates.FLAT_ITEM);
    }
}
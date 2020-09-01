package com.someguyssoftware.treasure2.gui.render.tileentity;

import com.someguyssoftware.treasure2.Treasure;
import com.someguyssoftware.treasure2.gui.model.StandardChestModel;

import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ResourceLocation;

public class WoodChestTileEntityRenderer extends TreasureChestTileEntityRenderer {

	public WoodChestTileEntityRenderer(TileEntityRendererDispatcher tileEntityRendererDispatcher) {
		super(tileEntityRendererDispatcher);
		Treasure.LOGGER.info("Creating Wood CHest TileEntity Renderer.");
		setTexture(new ResourceLocation(Treasure.MODID + ":textures/entity/chest/wood-chest.png"));
		setModel(new StandardChestModel());
	}

}
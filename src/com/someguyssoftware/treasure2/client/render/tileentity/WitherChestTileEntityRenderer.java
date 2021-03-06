/**
 * 
 */
package com.someguyssoftware.treasure2.client.render.tileentity;

import com.someguyssoftware.treasure2.client.model.ITreasureChestModel;
import com.someguyssoftware.treasure2.client.model.WitherChestModel;
import com.someguyssoftware.treasure2.tileentity.AbstractTreasureChestTileEntity;
import com.someguyssoftware.treasure2.tileentity.WitherChestTileEntity;

import net.minecraft.client.renderer.GlStateManager;

/**
 * 
 * @author Mark Gottschling on Jun 17, 2018
 *
 */
public class WitherChestTileEntityRenderer extends TreasureChestTileEntityRenderer {
	 /**
	  * 
	  * @param texture
	  */
	 public WitherChestTileEntityRenderer(String texture, ITreasureChestModel model) {
		 super(texture, model);
	 }
	 
	/**
	 * 
	 * @param te
	 * @param x
	 * @param y
	 * @param z
	 * @param partialTicks
	 * @param destroyStage
	 * @param alpha
	 */
	 @Override
    public void render(AbstractTreasureChestTileEntity te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
    	
    	if (!(te instanceof WitherChestTileEntity)) return; // should never happen

    	// add the destory textures
        if (destroyStage >= 0) {
            this.bindTexture(DESTROY_STAGES[destroyStage]);
            GlStateManager.matrixMode(5890);
            GlStateManager.pushMatrix();
            GlStateManager.scale(4.0F, 4.0F, 1.0F);
            GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
            GlStateManager.matrixMode(5888);
        }
    	
    	// get the model    	
    	WitherChestModel model = (WitherChestModel) getModel();   
    	
    	// bind the texture
        this.bindTexture(getTexture());
        // get the chest rotation
    	int meta = 0;
        if (te.hasWorld()) {
        	meta = te.getBlockMetadata();
        }
        int rotation = getRotation(meta);        
        
        // start render matrix
        GlStateManager.pushMatrix();
        // initial position (centered moved up)
        // (chest entity were created in Techne and need different translations than vanilla tile entities)
        GlStateManager.translate((float)x + 0.5F, (float)y + 1.5F, (float)z + 0.5F);
        
		// This rotation part is very important! Without it, your model will render upside-down.
        // (rotate 180 degrees around the z-axis)
        GlStateManager.rotate(180F, 0F, 0F, 1.0F);
        // rotate block to the correct direction that it is facing.
        GlStateManager.rotate((float)rotation, 0.0F, 1.0F, 0.0F);
    	
        float lidRotation = te.prevLidAngle + (te.lidAngle - te.prevLidAngle) * partialTicks;
        lidRotation = 1.0F - lidRotation;
        lidRotation = 1.0F - lidRotation * lidRotation * lidRotation;
        model.getRightFrontDoor().rotateAngleY = -(lidRotation * (float)Math.PI / 2.0F);      
        
        model.renderAll(te);

        GlStateManager.popMatrix();
        // end of rendering chest entity ////        
        
        // pop the destroy stage matrix
        if (destroyStage >= 0) {
            GlStateManager.matrixMode(5890);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
        }
        
        ////////////// render the locks //////////////////////////////////////
        if (!te.getLockStates().isEmpty()) {
        	renderLocks(te, x, y, z);
        }
        ////////////// end of render the locks //////////////////////////////////////
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }
}


package com.vetpetmon.wyrmsofnyrus.block;

import com.vetpetmon.wyrmsofnyrus.AutoReg;
import com.vetpetmon.wyrmsofnyrus.creativetab.TabWyrms;
import com.vetpetmon.wyrmsofnyrus.synapselib.RegHelper;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Random;

@AutoReg.ModElement.Tag
public class BlockHiveCreepedGrass extends AutoReg.ModElement {
	@GameRegistry.ObjectHolder("wyrmsofnyrus:creepedgrass")
	public static final Block block = null;
	public BlockHiveCreepedGrass(AutoReg instance) {
		super(instance, 205);
	}

	@Override
	public void initElements() {
		elements.blocks.add(() -> new creepedGrass().setRegistryName("creepedgrass"));
		elements.items.add(() -> new ItemBlock(block).setRegistryName(block.getRegistryName()));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModels(ModelRegistryEvent event) {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0,
				new ModelResourceLocation(RegHelper.resName("creepedgrass"), "inventory"));
	}
	public static class creepedGrass extends creepStagedGrass {

		public creepedGrass() {
			setUnlocalizedName("creepedgrass");
			setSoundType(SoundType.GROUND);
			setHarvestLevel("shovel", 1);
			setHardness(0.75F);
			setResistance(0.75F);
			setLightLevel(0F);
			setLightOpacity(255);
			setCreativeTab(TabWyrms.tab);
		}

		@Nonnull
		@SideOnly(Side.CLIENT)
		public BlockRenderLayer getBlockLayer()
		{
			return BlockRenderLayer.CUTOUT_MIPPED;
		}

		public void convert(World world, BlockPos pos, Block convertTo, int activeFlag) {
			world.setBlockState(pos, convertTo.getDefaultState().withProperty(BlockHivecreepBase.ACTIVE, activeFlag), 3);
		}

		@Override
		public void updateTick(World world, BlockPos pos, IBlockState state, Random random) {
			super.updateTick(world, pos, state, random);
			int stage = state.getValue(STAGE) + 1;
			switch (stage) {
				case(4):
				case(5):
					assert false;
					convert(world, pos, AllBlocks.hivecreeptop, 1);
					break;
				case(10):
					assert false;
					convert(world, pos, AllBlocks.hivecreeptop, 0);
					break;
				default:
					world.setBlockState(pos, state.withProperty(STAGE, stage), 2);
			}
		}
	}
}

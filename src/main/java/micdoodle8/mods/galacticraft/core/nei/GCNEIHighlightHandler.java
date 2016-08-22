package micdoodle8.mods.galacticraft.core.nei;

import codechicken.nei.api.IHighlightHandler;
import codechicken.nei.api.ItemInfo;
import codechicken.nei.guihook.GuiContainerManager;
import micdoodle8.mods.galacticraft.core.blocks.GCBlocks;
import micdoodle8.mods.galacticraft.core.tile.TileEntityFluidTank;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidTankInfo;

import java.util.List;

public class GCNEIHighlightHandler implements IHighlightHandler
{
    @Override
    public List<String> handleTextData(ItemStack stack, World world, EntityPlayer player, MovingObjectPosition mop, List<String> currenttip, ItemInfo.Layout layout)
    {
        if (stack.getItem() == Item.getItemFromBlock(GCBlocks.fluidTank))
        {
            if (layout == ItemInfo.Layout.BODY)
            {
                TileEntity tile = world.getTileEntity(mop.getBlockPos());
                if (tile instanceof TileEntityFluidTank)
                {
                    TileEntityFluidTank tank = (TileEntityFluidTank) tile;
                    FluidTankInfo[] infos = tank.getTankInfo(EnumFacing.DOWN);
                    if (infos.length == 1)
                    {
                        FluidTankInfo info = infos[0];
                        currenttip.add(info.fluid != null ? info.fluid.getLocalizedName() : "Empty");
                        currenttip.add((info.fluid != null ? info.fluid.amount : 0) + " / " + info.capacity);
                    }
                }
            }
        }

        return currenttip;
    }

    @Override
    public ItemStack identifyHighlight(World world, EntityPlayer player, MovingObjectPosition mop)
    {
        BlockPos pos = mop.getBlockPos();
        IBlockState state = world.getBlockState(pos);
        Block b = state.getBlock();
        int meta = b.getMetaFromState(state);
        if (meta == 8 && b == GCBlocks.basicBlock)
        {
            return new ItemStack(GCBlocks.basicBlock, 1, 8);
        }
        if (meta == 2 && b == GCBlocks.blockMoon)
        {
            return new ItemStack(GCBlocks.blockMoon, 1, 2);
        }
        if (b == GCBlocks.fakeBlock && meta == 1)
        {
            return new ItemStack(GCBlocks.spaceStationBase, 1, 0);
        }
        if (b == GCBlocks.fakeBlock && meta == 2)
        {
            return new ItemStack(GCBlocks.landingPad, 1, 0);
        }
        if (b == GCBlocks.fakeBlock && meta == 6)
        {
            return new ItemStack(GCBlocks.landingPad, 1, 1);
        }
        return null;
    }
}

package uk.co.qmunity.lib.block;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import uk.co.qmunity.lib.tile.TileBase;

public abstract class BlockTileBase extends BlockBase implements ITileEntityProvider {

    private Class<? extends TileBase> tileClass;
    private boolean canProvidePower;

    public BlockTileBase(Material material, Class<? extends TileBase> tileClass) {

        super(material);
        this.tileClass = tileClass;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {

        try {
            return tileClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private TileBase get(IBlockAccess world, int x, int y, int z) {

        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileBase)
            return (TileBase) tile;
        return null;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {

        TileBase te = get(world, x, y, z);
        if (te == null)
            return false;

        return te.onActivated(player, new MovingObjectPosition(x, y, z, side, Vec3.createVectorHelper(x + hitX, y + hitY, z + hitZ)),
                player.getCurrentEquippedItem());
    }

    @Override
    public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player) {

        TileBase te = get(world, x, y, z);
        if (te == null)
            return;

        te.onClicked(player, player.getCurrentEquippedItem());
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {

        TileBase te = get(world, x, y, z);
        if (te == null)
            return;

        te.onPlacedBy(entity, stack);
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {

        TileBase te = get(world, x, y, z);
        if (te == null)
            return;

        te.onNeighborChange(block);
    }

    @Override
    public void onNeighborChange(IBlockAccess world, int x, int y, int z, int tileX, int tileY, int tileZ) {

        TileBase te = get(world, x, y, z);
        if (te == null)
            return;

        te.onNeighborTileChange(world.getTileEntity(tileX, tileY, tileZ));
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {

        TileBase te = get(world, x, y, z);
        if (te == null)
            return new ArrayList<ItemStack>();

        return te.getDrops();
    }

    @Override
    public boolean canProvidePower() {

        return canProvidePower;
    }

    @Override
    public boolean canConnectRedstone(IBlockAccess world, int x, int y, int z, int side) {

        if (!canProvidePower())
            return false;

        TileBase te = get(world, x, y, z);
        if (te != null)
            return te.canConnectRedstone(ForgeDirection.getOrientation(side >= 0 && side < 4 ? Direction.directionToFacing[side] ^ 1 : 0));

        return false;
    }

    @Override
    public int isProvidingWeakPower(IBlockAccess world, int x, int y, int z, int side) {

        if (!canProvidePower())
            return 0;

        TileBase te = get(world, x, y, z);
        if (te != null)
            return te.getWeakRedstoneOutput(ForgeDirection.getOrientation(side ^ 1));

        return 0;
    }

    @Override
    public int isProvidingStrongPower(IBlockAccess world, int x, int y, int z, int side) {

        if (!canProvidePower())
            return 0;

        TileBase te = get(world, x, y, z);
        if (te != null)
            return te.getStrongRedstoneOutput(ForgeDirection.getOrientation(side ^ 1));

        return 0;
    }

    public void setCanProvidePower(boolean canProvidePower) {

        this.canProvidePower = canProvidePower;
    }

}

package uk.co.qmunity.lib.part;

import java.util.List;

import uk.co.qmunity.lib.raytrace.QMovingObjectPosition;
import uk.co.qmunity.lib.vec.IWorldLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface IPart extends IWorldLocation {

    public ITilePartHolder getParent();

    public void setParent(ITilePartHolder parent);

    public String getType();

    public void writeToNBT(NBTTagCompound tag);

    public void readFromNBT(NBTTagCompound tag);

    public void writeUpdateToNBT(NBTTagCompound tag);

    public void readUpdateFromNBT(NBTTagCompound tag);

    public void sendUpdatePacket();

    public ItemStack getItem();

    public ItemStack getPickedItem(QMovingObjectPosition mop);

    public List<ItemStack> getDrops();

    public void breakAndDrop(boolean creative);

}
package net.minecraft.server;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class BlockPressurePlate extends Block {

    private EnumMobType a;

    protected BlockPressurePlate(int i, int j, EnumMobType enummobtype, Material material) {
        super(i, j, material);
        this.a = enummobtype;
        this.a(CreativeModeTab.d);
        this.b(true);
        float f = 0.0625F;

        this.a(f, 0.0F, f, 1.0F - f, 0.03125F, 1.0F - f);
    }

    public int r_() {
        return 20;
    }

    public AxisAlignedBB e(World world, int i, int j, int k) {
        return null;
    }

    public boolean c() {
        return false;
    }

    public boolean b() {
        return false;
    }

    public boolean c(IBlockAccess iblockaccess, int i, int j, int k) {
        return true;
    }

    public boolean canPlace(World world, int i, int j, int k) {
        return world.v(i, j - 1, k) || BlockFence.c(world.getTypeId(i, j - 1, k));
    }

    public void doPhysics(World world, int i, int j, int k, int l) {
        boolean flag = false;

        if (!world.v(i, j - 1, k) && !BlockFence.c(world.getTypeId(i, j - 1, k))) {
            flag = true;
        }

        if (flag) {
            this.c(world, i, j, k, world.getData(i, j, k), 0);
            world.setTypeId(i, j, k, 0);
        }
    }

    public void b(World world, int i, int j, int k, Random random) {
        if (!world.isStatic) {
            if (world.getData(i, j, k) != 0) {
                this.l(world, i, j, k);
            }
        }
    }

    public void a(World world, int i, int j, int k, Entity entity) {
        if (!world.isStatic) {
            if (world.getData(i, j, k) != 1) {
                this.l(world, i, j, k);
            }
        }
    }

    private void l(World world, int i, int j, int k) {
        boolean flag = world.getData(i, j, k) == 1;
        boolean flag1 = false;
        float f = 0.125F;
        List list = null;

        if (this.a == EnumMobType.EVERYTHING) {
            list = world.getEntities((Entity) null, AxisAlignedBB.a().a((double) ((float) i + f), (double) j, (double) ((float) k + f), (double) ((float) (i + 1) - f), (double) j + 0.25D, (double) ((float) (k + 1) - f)));
        }

        if (this.a == EnumMobType.MOBS) {
            list = world.a(EntityLiving.class, AxisAlignedBB.a().a((double) ((float) i + f), (double) j, (double) ((float) k + f), (double) ((float) (i + 1) - f), (double) j + 0.25D, (double) ((float) (k + 1) - f)));
        }

        if (this.a == EnumMobType.PLAYERS) {
            list = world.a(EntityHuman.class, AxisAlignedBB.a().a((double) ((float) i + f), (double) j, (double) ((float) k + f), (double) ((float) (i + 1) - f), (double) j + 0.25D, (double) ((float) (k + 1) - f)));
        }

        if (!list.isEmpty()) {
            Iterator iterator = list.iterator();

            while (iterator.hasNext()) {
                Entity entity = (Entity) iterator.next();

                if (!entity.au()) {
                    flag1 = true;
                    break;
                }
            }
        }

        if (flag1 && !flag) {
            world.setData(i, j, k, 1);
            world.applyPhysics(i, j, k, this.id);
            world.applyPhysics(i, j - 1, k, this.id);
            world.e(i, j, k, i, j, k);
            world.makeSound((double) i + 0.5D, (double) j + 0.1D, (double) k + 0.5D, "random.click", 0.3F, 0.6F);
        }

        if (!flag1 && flag) {
            world.setData(i, j, k, 0);
            world.applyPhysics(i, j, k, this.id);
            world.applyPhysics(i, j - 1, k, this.id);
            world.e(i, j, k, i, j, k);
            world.makeSound((double) i + 0.5D, (double) j + 0.1D, (double) k + 0.5D, "random.click", 0.3F, 0.5F);
        }

        if (flag1) {
            world.a(i, j, k, this.id, this.r_());
        }
    }

    public void remove(World world, int i, int j, int k, int l, int i1) {
        if (i1 > 0) {
            world.applyPhysics(i, j, k, this.id);
            world.applyPhysics(i, j - 1, k, this.id);
        }

        super.remove(world, i, j, k, l, i1);
    }

    public void updateShape(IBlockAccess iblockaccess, int i, int j, int k) {
        boolean flag = iblockaccess.getData(i, j, k) == 1;
        float f = 0.0625F;

        if (flag) {
            this.a(f, 0.0F, f, 1.0F - f, 0.03125F, 1.0F - f);
        } else {
            this.a(f, 0.0F, f, 1.0F - f, 0.0625F, 1.0F - f);
        }
    }

    public boolean b(IBlockAccess iblockaccess, int i, int j, int k, int l) {
        return iblockaccess.getData(i, j, k) > 0;
    }

    public boolean c(IBlockAccess iblockaccess, int i, int j, int k, int l) {
        return iblockaccess.getData(i, j, k) == 0 ? false : l == 1;
    }

    public boolean isPowerSource() {
        return true;
    }

    public void f() {
        float f = 0.5F;
        float f1 = 0.125F;
        float f2 = 0.5F;

        this.a(0.5F - f, 0.5F - f1, 0.5F - f2, 0.5F + f, 0.5F + f1, 0.5F + f2);
    }

    public int q_() {
        return 1;
    }
}

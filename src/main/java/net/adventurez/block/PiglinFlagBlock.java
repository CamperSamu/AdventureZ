package net.adventurez.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

import java.util.List;

import net.adventurez.block.entity.PiglinFlagEntity;
import net.adventurez.entity.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class PiglinFlagBlock extends Block implements BlockEntityProvider {
  public static final DirectionProperty FACING;
  private static final VoxelShape POLE;
  private static final VoxelShape BAR_1;
  private static final VoxelShape BAR_2;
  private static final VoxelShape BAR_3;
  private static final VoxelShape BAR_4;
  private static final VoxelShape NORTH_FLAG;
  private static final VoxelShape EAST_FLAG;
  private static final VoxelShape SOUTH_FLAG;
  private static final VoxelShape WEST_FLAG;

  public PiglinFlagBlock(Settings settings) {
    super(settings);
    this.setDefaultState((BlockState) ((BlockState) ((BlockState) ((BlockState) this.stateManager.getDefaultState())
        .with(FACING, Direction.NORTH))));
  }

  @Override
  public BlockEntity createBlockEntity(BlockView view) {
    return new PiglinFlagEntity();
  }

  @Override
  @Environment(EnvType.CLIENT)
  public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
    tooltip.add(new TranslatableText("item.adventurez.moreinfo.tooltip"));
    if (InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 340)) {
      tooltip.remove(new TranslatableText("item.adventurez.moreinfo.tooltip"));
      tooltip.add(new TranslatableText("block.adventurez.piglin_flag_block.tooltip"));
      tooltip.add(new TranslatableText("block.adventurez.piglin_flag_block.tooltip2"));
      tooltip.add(new TranslatableText("block.adventurez.piglin_flag_block.tooltip3"));
    }
  }

  @Override
  public BlockState getPlacementState(ItemPlacementContext ctx) {
    return (BlockState) this.getDefaultState().with(FACING, ctx.getPlayerFacing().rotateYClockwise());
  }

  @Override
  public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    builder.add(FACING);
  }

  @Override
  public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
    return this.getShape(state);
  }

  @Override
  public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
    return this.getShape(state);
  }

  @Override
  public BlockRenderType getRenderType(BlockState state) {
    return BlockRenderType.MODEL;
  }

  @Override
  public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
    return false;
  }

  private VoxelShape getShape(BlockState state) {
    Direction direction = (Direction) state.get(FACING);
    if (direction == Direction.NORTH) {
      return NORTH_FLAG;
    } else if (direction == Direction.EAST) {
      return EAST_FLAG;
    } else if (direction == Direction.SOUTH) {
      return SOUTH_FLAG;
    } else if (direction == Direction.WEST) {
      return WEST_FLAG;
    } else
      return NORTH_FLAG;
  }

  static {
    FACING = HorizontalFacingBlock.FACING;
    POLE = VoxelShapes.union(createCuboidShape(6D, 0D, 6D, 10D, 11D, 10D), createCuboidShape(7D, 11D, 7D, 9D, 40D, 9D));
    BAR_1 = Block.createCuboidShape(6D, 40D, -8D, 10D, 44D, 10D);
    BAR_2 = Block.createCuboidShape(6D, 40D, 6D, 24D, 44D, 10D);
    BAR_3 = Block.createCuboidShape(6D, 40D, 6D, 10D, 44D, 24D);
    BAR_4 = Block.createCuboidShape(-8D, 40D, 6D, 10D, 44D, 10D);
    NORTH_FLAG = VoxelShapes.union(POLE, BAR_1);
    EAST_FLAG = VoxelShapes.union(POLE, BAR_2);
    SOUTH_FLAG = VoxelShapes.union(POLE, BAR_3);
    WEST_FLAG = VoxelShapes.union(POLE, BAR_4);
  }

}
package net.darkhax.botanypots.events;

import net.darkhax.botanypots.block.BlockEntityBotanyPot;
import net.darkhax.botanypots.data.recipes.crop.Crop;
import net.darkhax.botanypots.data.recipes.fertilizer.Fertilizer;
import net.darkhax.botanypots.data.recipes.potinteraction.PotInteraction;
import net.darkhax.botanypots.data.recipes.soil.Soil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BotanyPotEventDispatcherForge implements BotanyPotEventDispatcher {

    @Nullable
    @Override
    public Soil postSoilLookup(Level level, BlockPos pos, BlockEntityBotanyPot pot, ItemStack stack, @Nullable Soil found) {

        final LookupSoilEvent event = new LookupSoilEvent(level, pos, pot, stack, found);
        return MinecraftForge.EVENT_BUS.post(event) ? null : event.getLookupResult();
    }

    @Override
    public void listenSoilLookup(ILookupSoilListener listener) {

        MinecraftForge.EVENT_BUS.addListener(EventPriority.NORMAL, false, LookupSoilEvent.class, (event) -> {

            event.setLookupResult(listener.lookup(event.getLevel(), event.getPos(), event.getPot(), event.getStack(), event.getOriginal()));
        });
    }

    @Nullable
    @Override
    public Crop postCropLookup(Level level, BlockPos pos, BlockEntityBotanyPot pot, ItemStack stack, @Nullable Crop found) {

        final LookupCropEvent event = new LookupCropEvent(level, pos, pot, stack, found);
        return MinecraftForge.EVENT_BUS.post(event) ? null : event.getLookupResult();
    }

    @Override
    public void listenCropLookup(ILookupCropListener listener) {

        MinecraftForge.EVENT_BUS.addListener(EventPriority.NORMAL, false, LookupCropEvent.class, (event) -> {

            event.setLookupResult(listener.lookup(event.getLevel(), event.getPos(), event.getPot(), event.getStack(), event.getOriginal()));
        });
    }

    @Nullable
    @Override
    public PotInteraction postInteractionLookup(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack heldStack, BlockEntityBotanyPot pot, PotInteraction found) {

        final LookupInteractionEvent event = new LookupInteractionEvent(state, level, pos, player, hand, heldStack, pot, found);
        return MinecraftForge.EVENT_BUS.post(event) ? null : event.getLookupResult();
    }

    @Override
    public void listenInteractionLookup(ILookupInteractionListener listener) {

        MinecraftForge.EVENT_BUS.addListener(EventPriority.NORMAL, false, LookupInteractionEvent.class, (event) -> {

            event.setLookupResult(listener.lookup(event.getBlockState(), event.getLevel(), event.getPos(), event.getPlayer(), event.getHand(), event.getStack(), event.getPot(), event.getOriginal()));
        });
    }

    @Nullable
    @Override
    public Fertilizer postFertilizerLookup(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack heldStack, BlockEntityBotanyPot pot, Fertilizer found) {

        final LookupFertilizerEvent event = new LookupFertilizerEvent(state, level, pos, player, hand, heldStack, pot, found);
        return MinecraftForge.EVENT_BUS.post(event) ? null : event.getLookupResult();
    }

    @Override
    public void listenFertilizerLookup(ILookupFertilizerListener listener) {

        MinecraftForge.EVENT_BUS.addListener(EventPriority.NORMAL, false, LookupFertilizerEvent.class, (event) -> {

            event.setLookupResult(listener.lookup(event.getBlockState(), event.getLevel(), event.getPos(), event.getPlayer(), event.getHand(), event.getStack(), event.getPot(), event.getOriginal()));
        });
    }

    @Override
    public List<ItemStack> postCropDrops(Random rng, Level level, BlockPos pos, BlockEntityBotanyPot pot, Crop crop, List<ItemStack> originalDrops) {

        final CropDropEvent event = new CropDropEvent(rng, level, pos, pot, crop, originalDrops);
        return MinecraftForge.EVENT_BUS.post(event) ? Collections.emptyList() : event.getDrops();
    }

    @Override
    public void listenCropDrops(ICropDropListener listener) {

        MinecraftForge.EVENT_BUS.addListener(EventPriority.NORMAL, false, CropDropEvent.class, (event) -> {

            listener.generateDrop(event.getRandom(), event.getLevel(), event.getPos(), event.getPot(), event.getCrop(), event.getOriginalDrops(), event.getDrops());
        });
    }
}
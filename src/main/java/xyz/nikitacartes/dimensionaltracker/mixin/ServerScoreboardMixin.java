package xyz.nikitacartes.dimensionaltracker.mixin;

import net.minecraft.registry.RegistryKey;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.text.MutableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import static net.minecraft.world.World.*;


@Mixin(value = ServerScoreboard.class)
public abstract class ServerScoreboardMixin {

    @ModifyArg(method = "getDisplayName()Lnet/minecraft/text/Text;", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;addTellClickEvent(Lnet/minecraft/text/MutableText;)Lnet/minecraft/text/MutableText;"))
    private MutableText postGetTabListDisplayName(MutableText component) {
        if (component.getStyle().getColor() != null) {
            return component;
        }

        RegistryKey<World> registryKey = this.getWorld().getRegistryKey();
        if (registryKey.equals(OVERWORLD)) {
            return component.formatted(Formatting.DARK_GREEN);
        } else if (registryKey.equals(NETHER)) {
            return component.formatted(Formatting.DARK_RED);
        } else if (registryKey.equals(END)) {
            return component.formatted(Formatting.DARK_PURPLE);
        }
        return component;
    }
}
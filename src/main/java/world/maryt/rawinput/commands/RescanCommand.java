package world.maryt.rawinput.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import world.maryt.rawinput.RawInputHandler;

public class RescanCommand extends CommandBase {
	@Override
	public String getCommandName() {
		return "rescan";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "Rescans input devices: /rescan";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		RawInputHandler.getMouse("Rescan Command Fired");
		Minecraft.getMinecraft().thePlayer.sendChatMessage("Rescanned!");
	}
	@Override
	public int getRequiredPermissionLevel() {
		return -1;
	}
}

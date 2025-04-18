package dev.hez.meowsense.commands.impl;

import dev.hez.meowsense.Client;
import dev.hez.meowsense.commands.Command;
import net.minecraft.util.Formatting;

public class HelpCommand extends Command {
    public HelpCommand() {
        super("help");
    }

    @Override
    public void execute(String[] commands) {
        sendMessage("Commands: ");
        for (Command command : Client.INSTANCE.getCommandManager().getCommands()) {
            StringBuilder builder = new StringBuilder(Formatting.GRAY.toString());
            builder.append(Client.INSTANCE.getCommandManager().getPrefix());
            builder.append(command.getName());
            builder.append(" ");
            for (String cmd : command.getCommands()) {
                builder.append(cmd);
                builder.append(" ");
            }
            HelpCommand.sendMessage(builder.toString());
        }
    }
}

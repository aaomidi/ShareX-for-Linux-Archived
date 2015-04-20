package io.sharex.managers;

import io.sharex.ShareX;
import io.sharex.utils.Logger;
import io.sharex.commands.Command;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class CommandManager {

    private final ShareX instance;
    private final Map<String, Command> commandMap = new HashMap<>();

    public CommandManager(ShareX instance) {
        this.instance = instance;
    }

    /**
     * You must register your command using this method in order for it to be accessable through the system.
     *
     * @param command The command object that you want to register.
     */
    public void registerCommand(Command command) {
        if (!commandMap.containsKey(command.getName().toLowerCase())) {
            commandMap.put(command.getName().toLowerCase(), command);

            if (command.getAliases() != null) {
                for (String alias : command.getAliases()) {
                    if (!commandMap.containsKey(alias.toLowerCase())) {
                        commandMap.put(alias.toLowerCase(), command);
                    } else {
                        instance.logger().log(Logger.LoggerLevel.WARNING,
                                String.format(
                                        "A class with name %s tried to register an alias that has already been registered!",
                                        command.getActualName()));
                    }
                }
            }
        } else {
            instance.logger().log(Logger.LoggerLevel.WARNING,
                    String.format(
                            "A class with name %s tried to register a command name that has already been registered!",
                            command.getActualName()));
        }
    }

    /**
     * Used to get the Command object from the command name.
     *
     * @param name The name or an alias of the command that is required.
     * @return The Command object associated with that name or alias. Will return null if command name is not found.
     */
    @Nullable
    public Command getCommandWithName(String name) {
        return commandMap.get(name.toLowerCase());
    }
}

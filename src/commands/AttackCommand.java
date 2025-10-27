package commands;

import entities.Enemies.Enemy;
import gameexceptions.InsufficientCommandArgsException;
import gameexceptions.InvalidCommandArgsException;
import maps.Place;
import mvc.views.commandviews.CommandEventListener;

import java.util.List;
import java.util.NoSuchElementException;

public class AttackCommand extends Command {
    public AttackCommand(CommandParameters commandParams) {
        super(commandParams);
        validateArgs();
    }

    public AttackCommand(CommandParameters commandParams, List<CommandEventListener> commandViews) {
        super(commandParams, commandViews);
        validateArgs();
    }

    protected void validateArgs() {
        if (args.length == 0) {
            throw new InsufficientCommandArgsException();
        }
    }

    @Override
    public void execute() {
        synchronized (lock) {
            String enemyName = getSubjectNameFromCommandArgs(args);
            Place currentPlace = character.getCurrentPlace(map);
            try {
                Enemy enemy = currentPlace.getEnemies().stream()
                        .filter(x -> x.getName().equalsIgnoreCase(enemyName))
                        .findFirst()
                        .orElseThrow(NoSuchElementException::new);
                character.attack(enemy);
                if (enemy.isDead()) {
                    character.addXP(enemy.getXP());
                    currentPlace.getEnemies().remove(enemy);
                } else {
                    enemy.attackCharacter(character);
                }
            } catch (NoSuchElementException e) {
                commandEventListeners.forEach(CommandEventListener::showNoSuchEnemyMessage);
            }
        }
    }
}

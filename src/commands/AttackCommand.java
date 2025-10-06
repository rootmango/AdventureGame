package commands;

import entities.Enemies.Enemy;
import gameexceptions.InsufficientCommandArgsException;
import maps.Place;
import mvc.views.commandviews.CommandView;
import mvc.views.commandviews.CommandViewInterface;

import java.util.List;
import java.util.NoSuchElementException;

public class AttackCommand extends Command {
    public AttackCommand(CommandParameters commandParams) {
        super(commandParams);
    }

    public AttackCommand(CommandParameters commandParams, List<CommandViewInterface> commandViews) {
        super(commandParams, commandViews);
    }

    @Override
    public void execute() {
        if (args.length == 0) {
            throw new InsufficientCommandArgsException();
        } else {
            synchronized (lock) {
                String enemyName = getSubjectNameFromCommandArgs(args);
                Place currentPlace = character.getCurrentPlace(map);
                try {
                    Enemy enemy = currentPlace.getEnemies().stream()
                            .filter(x -> x.getName().equalsIgnoreCase(enemyName))
                            .findFirst()
                            .orElseThrow(NoSuchElementException::new);
                    enemy.getAttacked(character);
                    if (enemy.isDead()) {
                        character.addXP(enemy.getXP());
                        currentPlace.getEnemies().remove(enemy);
                    }
                } catch (NoSuchElementException e) {
                    commandViews.forEach(CommandViewInterface::showNoSuchEnemyMessage);
                }
            }
        }
    }
}

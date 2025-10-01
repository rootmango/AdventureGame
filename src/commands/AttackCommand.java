package commands;

import entities.Enemies.Enemy;
import maps.Place;

import java.util.NoSuchElementException;

public class AttackCommand extends Command {
    public AttackCommand(CommandParameters commandParams) {
        super(commandParams);
    }

    @Override
    public void execute() {
        if (args.length == 0) {
            commandObserver.requestedHelp();
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
                    commandObserver.requestedNonExistingEnemy();
                }
            }
        }
    }
}

package mvc.views.enemyviews;

import entities.Enemies.Enemy;

public interface EnemyViewInterface {
    void onDied(Enemy enemy);

    void onGotAttacked(Enemy enemy, int attackAmount);

    void onAttacked(Enemy enemy, int attackAmount);
}

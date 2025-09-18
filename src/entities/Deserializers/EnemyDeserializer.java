package entities.Deserializers;

import entities.Enemies.BossEnemies.GoblinKing;
import entities.Enemies.CommonEnemies.Goblin;
import entities.Enemies.CommonEnemies.Ogre;
import entities.Enemies.CommonEnemies.Thief;
import entities.Enemies.CommonEnemies.Undead;
import entities.Enemies.Enemy;
import com.google.gson.*;

import java.lang.reflect.Type;

public class EnemyDeserializer implements JsonDeserializer<Enemy> {
    // gson's deserialization works by creating an instance of a class
    // and then filling its fields. When attempting to deserialize an abstract
    // class (for example, members of List<Enemy>), it will result in an error,
    // since abstract classes can't be instantiated. To avoid this error, this
    // deserializer is provided to "guide" gson into which concrete class to
    // instantiate once encountering this abstract class.
    @Override
    public Enemy deserialize(JsonElement json, Type typeOfT,
                             JsonDeserializationContext context)
            throws JsonParseException {

        JsonObject jsonObject = json.getAsJsonObject();
        String name = json.getAsJsonObject().get("name").getAsString();
        if (name.equals("Goblin")) {
            return context.deserialize(jsonObject, Goblin.class);
        } else if (name.equals("Thief")) {
            return context.deserialize(jsonObject, Thief.class);
        } else if (name.equals("Ogre")) {
            return context.deserialize(jsonObject, Ogre.class);
        } else if (name.equals("Undead")) {
            return context.deserialize(jsonObject, Undead.class);
        } else if (name.equals("Goblin King")) {
            return context.deserialize(jsonObject, GoblinKing.class);
        } else {
            throw new RuntimeException();
        }
    }
}

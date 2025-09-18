package maps;

import entities.Deserializers.*;
import entities.Enemies.Enemy;
import entities.ItemContainers.ItemContainer;
import items.Consumables.Consumable;
import items.Deserializers.ConsumableDeserializer;
import items.Deserializers.EquipableDeserializer;
import items.Deserializers.ItemDeserializer;
import items.Equipables.Equipable;
import items.Item;
import com.google.gson.*;

import java.lang.reflect.Type;

public class GameMapDeserializer implements JsonDeserializer<GameMap> {
    @Override
    public GameMap deserialize(JsonElement jsonElement, Type type,
                               JsonDeserializationContext jsonDeserializationContext)
            throws JsonParseException {

        var gson = new GsonBuilder()
                .registerTypeAdapter(Enemy.class, new EnemyDeserializer())
                .registerTypeAdapter(ItemContainer.class, new ItemContainerDeserializer())
                .registerTypeAdapter(Item.class, new ItemDeserializer())
                .registerTypeAdapter(Consumable.class, new ConsumableDeserializer())
                .registerTypeAdapter(Equipable.class, new EquipableDeserializer())
                .create();
        JsonArray placesArrayJson = jsonElement.getAsJsonObject().get("placesArray")
                                                .getAsJsonArray();
        int length = placesArrayJson.size();
        int width = placesArrayJson.get(0).getAsJsonArray().size();
        Place[][] array = new Place[length][width];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                var place = placesArrayJson.get(i).getAsJsonArray().get(j);
                array[i][j] = gson.fromJson(place, Place.class);
            }
        }

        return new GameMap(array);
    }
}

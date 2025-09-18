package items.Deserializers;

import items.Consumables.HealthPotion;
import items.Item;
import com.google.gson.*;

import java.lang.reflect.Type;

public class ConsumableDeserializer implements JsonDeserializer<Item> {
    // gson's deserialization works by creating an instance of a class
    // and then filling its fields. When attempting to deserialize an abstract
    // class (for example, members of List<Consumable>), it will result in an error,
    // since abstract classes can't be instantiated. To avoid this error, this
    // deserializer is provided to "guide" gson into which concrete class to
    // instantiate once encountering this abstract class.
    @Override
    public Item deserialize(JsonElement json, Type typeOfT,
                            JsonDeserializationContext context)
            throws JsonParseException {

        JsonObject jsonObject = json.getAsJsonObject();
        String name = json.getAsJsonObject().get("name").getAsString();
        Item item = null;
        if (name.equals("Health Potion")) {
            item = context.deserialize(jsonObject, HealthPotion.class);
        } else {
            throw new RuntimeException();
        }
        return item;
    }
}
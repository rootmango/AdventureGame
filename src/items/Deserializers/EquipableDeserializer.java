package items.Deserializers;

import items.Equipables.IronDagger;
import items.Item;
import items.Equipables.Staff;
import com.google.gson.*;

import java.lang.reflect.Type;

public class EquipableDeserializer implements JsonDeserializer<Item> {
    // gson's deserialization works by creating an instance of a class
    // and then filling its fields. When attempting to deserialize an abstract
    // class (for example, members of List<Equipable>), it will result in an error,
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
        if (name.equals("Iron Dagger")) {
            item = context.deserialize(jsonObject, IronDagger.class);
        } else if (name.equals("Staff")) {
            item = context.deserialize(jsonObject, Staff.class);
        } else {
            throw new RuntimeException();
        }
        return item;
    }
}
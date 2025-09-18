package entities.Deserializers;

import entities.ItemContainers.Chest;
import entities.ItemContainers.ItemContainer;
import entities.ItemContainers.WanderingMerchant;
import com.google.gson.*;

import java.lang.reflect.Type;

public class ItemContainerDeserializer implements JsonDeserializer<ItemContainer> {
    // gson's deserialization works by creating an instance of a class
    // and then filling its fields. When attempting to deserialize an abstract
    // class (for example, members of List<ItemContainer>), it will result in an error,
    // since abstract classes can't be instantiated. To avoid this error, this
    // deserializer is provided to "guide" gson into which concrete class to
    // instantiate once encountering this abstract class.
    @Override
    public ItemContainer deserialize(JsonElement json, Type typeOfT,
                                     JsonDeserializationContext context)
            throws JsonParseException {

        JsonObject jsonObject = json.getAsJsonObject();
        String name = json.getAsJsonObject().get("name").getAsString();
        if (name.equals("Chest")) {
            return context.deserialize(jsonObject, Chest.class);
        } else if (name.equals("Wandering Merchant")) {
            return context.deserialize(jsonObject, WanderingMerchant.class);
        } else {
            throw new RuntimeException();
        }
    }
}

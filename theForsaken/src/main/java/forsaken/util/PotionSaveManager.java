package forsaken.util;

import basemod.abstracts.CustomSavableRaw;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;

public class PotionSaveManager implements CustomSavableRaw {

    @Override
    public JsonElement onSaveRaw() {
        JsonArray arr = new JsonArray();
        for (AbstractPotion p : AbstractDungeon.player.potions) {
            JsonObject obj = new JsonObject();
            obj.addProperty("ID", p.ID);
            if (p instanceof CustomSavableRaw) {
                CustomSavableRaw asSavable = (CustomSavableRaw)p;
                obj.add("SAVEDATA", asSavable.onSaveRaw());
            }
            arr.add(obj);
        }
        return arr;
    }

    @Override
    public void onLoadRaw(JsonElement jsonElement) {
        if (jsonElement == null || !jsonElement.isJsonArray()) {
            return; // nothing to load
        }

        JsonArray arr = jsonElement.getAsJsonArray();
        for (int i = 0; i < arr.size(); i++) {
            try {
                JsonElement e = arr.get(i);
                AbstractPotion p = AbstractDungeon.player.potions.get(i);
                JsonObject asObj = e.getAsJsonObject();
                String savedId = asObj.get("ID").getAsString();
                if (savedId.equals(p.ID)) {
                    // found matching potion. Is it a CustomSavable?
                    if (p instanceof CustomSavableRaw) {
                        CustomSavableRaw asSavable = (CustomSavableRaw) p;
                        asSavable.onLoadRaw(asObj.get("SAVEDATA"));
                    }
                }
            } catch (IllegalArgumentException exception) {
                // ignore malformed entry
            }
        };
    }
}

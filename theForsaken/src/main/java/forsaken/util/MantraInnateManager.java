package forsaken.util;

import basemod.abstracts.CustomSavableRaw;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class MantraInnateManager implements CustomSavableRaw {
    private final ArrayList<AbstractCard> innateCards;
    private List<UUID> uuidList;

    public MantraInnateManager() {
        innateCards = new ArrayList<>();
    }

    public void addToInnateList(AbstractCard c) {
        // Apply immediately to the deck
        player.masterDeck.group.forEach(card -> {
            if (card.uuid.equals(c.uuid)) {
                innateCards.add(card);
            }
        });
        buildUUIDList();
    }

    private void buildUUIDList() {
        uuidList = AbstractDungeon.player.masterDeck.group.stream()
                .filter(innateCards::contains)
                .map(card -> card.uuid)
                .collect(Collectors.toList());
    }

    public boolean cardIsMantraInnate(AbstractCard c) {
        return uuidList.contains(c.uuid);
    }

    @Override
    public JsonElement onSaveRaw() {
        CardGroup cardGroup = player.masterDeck;
        JsonArray arr = new JsonArray();
        for (int i = 0; i < cardGroup.group.size(); i++) {
            AbstractCard candidate = cardGroup.group.get(i);
            if (innateCards.contains(candidate)) {
                arr.add(String.format("%d:%s", i, cardGroup.group.get(i).cardID));
            }
        }
        return arr;
    }

    @Override
    public void onLoadRaw(JsonElement jsonElement) {
        innateCards.clear();

        if (jsonElement == null || !jsonElement.isJsonArray()) {
            return; // nothing to load
        }

        JsonArray arr = jsonElement.getAsJsonArray();
        arr.forEach(e -> {
            try {
                String raw = e.getAsString();

                // split into index and ID
                int colon = raw.indexOf(':');
                if (colon < 0) {
                    return; // malformed, skip
                }

                int index = Integer.parseInt(raw.substring(0, colon));
                String id = raw.substring(colon + 1);
                AbstractCard candidate = player.masterDeck.group.get(index);
                if (candidate.cardID.equals(id)) {
                    innateCards.add(candidate);
                }
            } catch (IllegalArgumentException ignored) {
                // ignore malformed entry
            }
        });
        buildUUIDList();
    }

}

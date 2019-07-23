package theForsaken;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import theForsaken.cards.*;

import java.util.ArrayList;

public class CardLibrary {

    public CardGroup cardGroup;

    public CardLibrary(final boolean includeBasic, final boolean includeLocked, final boolean includeUnseen, String excludeSpecificId) {
        CardGroup group = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
        if (includeBasic) {
            group.addToBottom(new TheForsaken_Strike());
            group.addToBottom(new TheForsaken_Defend());
            group.addToBottom(new Smite());
            group.addToBottom(new Penitence());
        }
        ArrayList<AbstractCard> list = new ArrayList<>();
        list.add(new BattleHymn());
        list.add(new BlessedWeapon());
        list.add(new BountifulSunlight());
        list.add(new CleansingLight());
        list.add(new CorruptedForm());
        list.add(new CorruptedWord());
        list.add(new CowardsBrand());
        list.add(new DarkBarrier());
        list.add(new DarkRift());
        list.add(new DivineGuidance());
        list.add(new Eulogy());
        list.add(new FatRoll());
        list.add(new FuryStrikes());
        list.add(new HorrifyingStrike());
        list.add(new HymnOfPatience());
        list.add(new HymnOfRest());
        list.add(new InspiringBlow());
        list.add(new Purification());
        list.add(new RainingSunlight());
        list.add(new Retribution());
        list.add(new Riposte());
        list.add(new SacredOath());
        list.add(new SacrificeSoul());
        list.add(new ShieldBash());
        list.add(new SpinAttack());
        list.add(new TearsOfSunlight());
        list.add(new Temperance());
        list.add(new Terrorize());
        list.add(new WardingHymn());
        list.add(new WordsOfMight());
        list.add(new WordsOfPestilence());
        list.add(new WrathOfGod());
        for (AbstractCard card : list) {
            if ((includeLocked || !UnlockTracker.isCardLocked(card.cardID))
                    && (includeUnseen || UnlockTracker.isCardSeen(card.cardID))
                    && (!card.cardID.equals(excludeSpecificId))
            ) {
                group.addToBottom(card);
            }
        }

        this.cardGroup = group;
    }

    public CardLibrary() {
        this(true, true, true, null);
    }
}

package forsaken.cards.skills;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.FleetingField;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import forsaken.TheForsakenMod;
import forsaken.actions.BiFunctionalAction;
import forsaken.cards.AbstractForsakenCard;

import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class Mantra extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(Mantra.class.getSimpleName());

    public Mantra() {
        super(ID, 4, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        FleetingField.fleeting.set(this, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded) {
            Consumer<List<AbstractCard>> callback = list -> {
                if (list.isEmpty()) {
                    return;
                }
                AbstractCard card = list.get(0);
                qAction(new BiFunctionalAction<>(this::applyMantra, card));
            };
            qAction(new SelectCardsAction(p.masterDeck.group, 1, EXTENDED_DESCRIPTION[0], false, Mantra::cardIsNotInnate, callback));
            return;
        }
        Consumer<List<AbstractCard>> callback = list -> {
            if (list.isEmpty()) {
                return;
            }
            AbstractCard card = list.get(0);
            qAction(new BiFunctionalAction<>(this::applyMantra, card));
        };
        qAction(new SelectCardsInHandAction(1, EXTENDED_DESCRIPTION[0], Mantra::cardIsNotInnate, callback));
    }

    private static boolean cardIsNotInnate(AbstractCard card) {
        if (card.isInnate) {
            return false;
        } else {
            return !TheForsakenMod.mantraInnateManager.cardIsMantraInnate(card);
        }
    }

    private boolean applyMantra(boolean firstUpdate, AbstractCard card) {
        TheForsakenMod.mantraInnateManager.addToInnateList(card);
        qEffect(new ShowCardBrieflyEffect(card.makeStatEquivalentCopy()));
        return true;
    }
}
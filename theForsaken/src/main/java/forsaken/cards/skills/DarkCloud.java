package forsaken.cards.skills;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.actions.FunctionalAction;
import forsaken.actions.XCostAction;
import forsaken.cards.AbstractForsakenCard;
import forsaken.cards.AbstractQuickdrawCard;
import forsaken.characters.TheForsaken;

import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class DarkCloud extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(DarkCloud.class.getSimpleName());

    public DarkCloud() {
        super(ID, -1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF, TheForsaken.Enums.COLOR_GOLD, "DarkBarrier");
        cardsToPreview = new DarkFog();
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        BiFunction<Integer, Boolean, Boolean> action = (x, isUpgraded) -> {
            shuffleIn(new DarkFog(), x);
            if (isUpgraded) {
                qAction(new FunctionalAction(firstUpdate -> {
                    List<AbstractCard> quickdrawCards = p.drawPile.group.stream()
                            .filter(AbstractQuickdrawCard::isQuickdraw)
                            .limit(x)
                            .collect(Collectors.toList());
                    if (!quickdrawCards.isEmpty()) {
                        quickdrawCards.forEach(card -> {
                            p.drawPile.removeCard(card);
                            p.drawPile.addToTop(card);
                            addToTop(new DrawCardAction(1));
                        });
                    }
                    return true;
                }));
            }
            return true;
        };
        qAction(new XCostAction<>(this, action, upgraded));
    }
}
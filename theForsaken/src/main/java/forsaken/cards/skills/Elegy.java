package forsaken.cards.skills;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.actions.BiFunctionalAction;
import forsaken.cardmods.BonusDamageMod;
import forsaken.cards.AbstractForsakenCard;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

@SuppressWarnings("unused")
public class Elegy extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(Elegy.class.getSimpleName());

    public Elegy() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        isEthereal = true;
        magicNumber = baseMagicNumber = 4;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Consumer<List<AbstractCard>> callback = (cards) -> {
            // cards holds the list selected by the user
            for(AbstractCard c : cards) {
                qAction(new BiFunctionalAction<>(this::discardOrShuffle, c));
            }
        };
        Predicate<AbstractCard> attacksOnly = card -> card.type == CardType.ATTACK;
        qAction(new SelectCardsInHandAction(1, EXTENDED_DESCRIPTION[0], attacksOnly, callback));
    }

    private boolean discardOrShuffle (boolean first, AbstractCard c) {
        AbstractPlayer p = AbstractDungeon.player;
        BonusDamageMod.applyToCard(c, magicNumber);
        if (upgraded) {
            p.hand.moveToDeck(c, true);
        } else {
            p.hand.moveToDiscardPile(c);
        }
        return true;
    }
}

package forsaken.cards.skills;

import com.evacipated.cardcrawl.mod.stslib.actions.common.MultiGroupSelectAction;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.actions.XCostAction;
import forsaken.cardmods.BonusDamageMod;
import forsaken.cards.AbstractForsakenCard;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class BlessedWeapon extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(BlessedWeapon.class.getSimpleName());

    public BlessedWeapon() {
        super(ID, -1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        magicNumber = baseMagicNumber = 3;
        upgradeMagicNumberBy = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        qAction(new XCostAction<>(this, this::selectAttacks, p));
    }

    @SuppressWarnings("SameReturnValue")
    private boolean selectAttacks(int x, AbstractPlayer p) {
        if (x <= 0) {
            return true;
        }
        Predicate<AbstractCard> attacksOnly = card -> card.type == CardType.ATTACK;
        BiConsumer<List<AbstractCard>, Map<AbstractCard, CardGroup>> callback = (list, map) -> {
            for(AbstractCard card : list) {
                BonusDamageMod.applyToCard(card, magicNumber);
            }
        };
        qAction(new MultiGroupSelectAction(
                EXTENDED_DESCRIPTION[0] + x + EXTENDED_DESCRIPTION[1],
                callback,
                x,
                true,
                attacksOnly,
                CardGroup.CardGroupType.DRAW_PILE,
                CardGroup.CardGroupType.HAND,
                CardGroup.CardGroupType.DISCARD_PILE
                ));
        return true;
    }
}
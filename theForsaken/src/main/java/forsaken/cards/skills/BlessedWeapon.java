package forsaken.cards.skills;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.actions.XCostAction;
import forsaken.cardmods.BonusDamageMod;
import forsaken.cards.AbstractForsakenCard;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class BlessedWeapon extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(BlessedWeapon.class.getSimpleName());

    public BlessedWeapon() {
        super(ID, -1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        magicNumber = baseMagicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Predicate<AbstractCard> attacksOnly = card -> card.type == CardType.ATTACK;
        Consumer<List<AbstractCard>> callback = list -> {
            if (!list.isEmpty()) {
                qAction(new XCostAction<AbstractCard>(this, this::increaseDamage, list.get(0)));
            }
        };
        qAction(new SelectCardsInHandAction(1, EXTENDED_DESCRIPTION[0], attacksOnly, callback));
    }

    private boolean increaseDamage(int x, AbstractCard attackCard) {
        if (upgraded) {
            x += 1;
        }
        if (attackCard != null) {
            BonusDamageMod.applyToCard(attackCard, x);
        }
        return true;
    }
}
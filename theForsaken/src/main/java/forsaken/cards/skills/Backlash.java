package forsaken.cards.skills;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import forsaken.TheForsakenMod;
import forsaken.cards.AbstractForsakenCard;

public class Backlash extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(Backlash.class.getSimpleName());

    public Backlash() {
        super(ID, -2, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        magicNumber = baseMagicNumber = 8;
        upgradeMagicNumberBy = 3;
        selfRetain = true;
    }

    @Override
    public int triggerOnPlayerDamaged(int damage, DamageInfo info) {
        addToTop(new DamageAction(info.owner, makeDamageInfo(magicNumber, DamageInfo.DamageType.THORNS)));
        addToTop(new DiscardSpecificCardAction(this));
        // TODO VFX
        return damage;
    }

    @Override
    public boolean canPlay(AbstractCard card) {
        this.cantUseMessage = EXTENDED_DESCRIPTION[0];
        return !card.equals(this);
    }
}

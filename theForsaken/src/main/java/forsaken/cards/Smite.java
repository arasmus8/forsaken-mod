package forsaken.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import forsaken.TheForsakenMod;
import forsaken.characters.TheForsaken;
import forsaken.powers.SunlightPower;

public class Smite extends AbstractQuickdrawCard {
    public static final String ID = TheForsakenMod.makeID(Smite.class.getSimpleName());

    public Smite() {
        super(ID, -2, CardType.ATTACK, CardRarity.BASIC, CardTarget.NONE, TheForsaken.Enums.COLOR_GOLD);
        baseDamage = damage = 4;
        upgradeDamageBy = 2;
        isMultiDamage = true;
        baseMagicNumber = magicNumber = 1;
        upgradeMagicNumberBy = 1;
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        applyPowers();
        dealAoeDamage(AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        AbstractPlayer p = AbstractDungeon.player;
        qAction(new ApplyPowerAction(p, p, new SunlightPower(magicNumber), magicNumber));
    }

    @Override
    public boolean canPlay(AbstractCard card) {
        this.cantUseMessage = EXTENDED_DESCRIPTION[0];
        return !card.equals(this);
    }
}

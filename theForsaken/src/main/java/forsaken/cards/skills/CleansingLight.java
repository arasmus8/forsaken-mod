package forsaken.cards.skills;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import forsaken.TheForsakenMod;
import forsaken.cards.AbstractForsakenCard;

public class CleansingLight extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(CleansingLight.class.getSimpleName());

    public CleansingLight() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        isEthereal = true;
        block = baseBlock = 12;
        upgradeBlockBy = 4;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        gainBlock();
        qAction(new RemoveSpecificPowerAction(p, p, PoisonPower.POWER_ID));
        qAction(new RemoveSpecificPowerAction(p, p, VulnerablePower.POWER_ID));
    }
}
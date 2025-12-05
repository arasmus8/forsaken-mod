package forsaken.cards.skills;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import forsaken.TheForsakenMod;
import forsaken.cards.AbstractForsakenCard;
import forsaken.powers.SunlightPower;

import java.util.Optional;

@SuppressWarnings("unused")
public class DesperatePlea extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(DesperatePlea.class.getSimpleName());

    public DesperatePlea() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        upgradeCostTo = 0;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Optional<AbstractPower> maybeSunlight = Optional.ofNullable(p.getPower(SunlightPower.POWER_ID));
        maybeSunlight.ifPresent(sunlight -> {
            int stacks = sunlight.amount;
            qAction(new RemoveSpecificPowerAction(p, p, sunlight));
            applyToSelf(new StrengthPower(p, stacks));
        });
    }
}
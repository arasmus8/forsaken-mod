package forsaken.cards.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import forsaken.TheForsakenMod;
import forsaken.cards.AbstractForsakenCard;
import forsaken.powers.CorruptedFormPower;

import java.util.Optional;

public class CorruptedForm extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(CorruptedForm.class.getSimpleName());

    public CorruptedForm() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.NONE);
        magicNumber = baseMagicNumber = 3;
        upgradeMagicNumberBy = -1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Optional<AbstractPower> current = Optional.ofNullable(p.getPower(CorruptedFormPower.POWER_ID));
        current.ifPresent(pow -> qAction(new RemoveSpecificPowerAction(p, p, pow)));
        applyToSelf(new CorruptedFormPower(p, magicNumber));
    }
}
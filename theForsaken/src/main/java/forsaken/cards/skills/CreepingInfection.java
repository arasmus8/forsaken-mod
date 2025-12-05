package forsaken.cards.skills;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import forsaken.TheForsakenMod;
import forsaken.cards.AbstractForsakenCard;

import java.util.Optional;

@SuppressWarnings("unused")
public class CreepingInfection extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(CreepingInfection.class.getSimpleName());

    public CreepingInfection() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        magicNumber = baseMagicNumber = 3;
        upgradeMagicNumberBy = 2;
        unplayedEffect = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (dontTriggerOnUseCard) {
            applyToSelf(new PoisonPower(p, p, magicNumber));
            monsterList().forEach(mon -> applyToEnemy(mon, new PoisonPower(mon, p, magicNumber)));
            return;
        }
        Optional<PoisonPower> poison = p.powers.stream()
                .filter(pow -> pow instanceof PoisonPower)
                .map(pow -> (PoisonPower)pow)
                .findFirst();
        poison.ifPresent(pow -> {
            int a = Math.min(magicNumber, pow.amount);
            qAction(new ReducePowerAction(p, p, PoisonPower.POWER_ID, a));
            qAction(new HealAction(p, p, a));
        });
    }

}

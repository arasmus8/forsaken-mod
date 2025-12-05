package forsaken.cards.skills;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import forsaken.TheForsakenMod;
import forsaken.actions.FunctionalAction;
import forsaken.cards.AbstractForsakenCard;
import forsaken.powers.SunlightPower;

import java.util.Optional;

@SuppressWarnings("unused")
public class PowerUp extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(PowerUp.class.getSimpleName());

    public PowerUp() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        magicNumber = baseMagicNumber = 3;
        upgradeMagicNumberBy = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new SunlightPower(magicNumber));
        qAction(new FunctionalAction(firstUpdate -> {
            Optional<AbstractPower> sunlightPower = Optional.ofNullable(p.getPower(SunlightPower.POWER_ID));
            sunlightPower.ifPresent(pow -> {
                int damage = pow.amount;
                monsterList().forEach(mon -> qAction(new LoseHPAction(mon, p, damage, AbstractGameAction.AttackEffect.FIRE)));
            });
            return true;
        }));
    }
}
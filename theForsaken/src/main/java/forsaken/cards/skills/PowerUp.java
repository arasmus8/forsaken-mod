package forsaken.cards.skills;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import forsaken.TheForsakenMod;
import forsaken.actions.FunctionalAction;
import forsaken.cards.AbstractForsakenCard;
import forsaken.powers.SunlightPower;

import java.util.Optional;

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
                monsterList().forEach(mon -> {
                    DamageInfo info = makeDamageInfo(damage, DamageInfo.DamageType.THORNS);
                    qAction(new DamageAction(mon, info, AbstractGameAction.AttackEffect.FIRE));
                });
            });
            return true;
        }));
    }
}
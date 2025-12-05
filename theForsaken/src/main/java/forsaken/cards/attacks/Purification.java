package forsaken.cards.attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;
import forsaken.TheForsakenMod;
import forsaken.actions.FunctionalAction;
import forsaken.cards.AbstractForsakenCard;
import forsaken.powers.FearPower;

@SuppressWarnings("unused")
public class Purification extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(Purification.class.getSimpleName());

    public Purification() {
        super(ID, 3, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        damage = baseDamage = 24;
        upgradeDamageBy = 11;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            qEffect(new WeightyImpactEffect(m.drawX, m.drawY));
            for (AbstractPower pow : p.powers) {
                qAction(new FunctionalAction(firstUpdate -> {
                    transferDebuff(pow, p, m);
                    return true;
                }));
            }
            dealDamage(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        }
    }

    private void transferDebuff(AbstractPower power, AbstractPlayer p, AbstractMonster m) {
        if (power instanceof PoisonPower) {
            applyToEnemy(m, new PoisonPower(m, p, power.amount));
        } else if (power instanceof VulnerablePower) {
            applyToEnemy(m, new VulnerablePower(m, power.amount, false));
        } else if (power instanceof WeakPower) {
            applyToEnemy(m, new WeakPower(m, power.amount, false));
        } else if (power instanceof FearPower) {
            applyToEnemy(m, new FearPower(m, power.amount));
        }
        qAction(new RemoveSpecificPowerAction(p, p, power));
    }
}
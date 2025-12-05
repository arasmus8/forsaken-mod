package forsaken.cards.skills;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.HealEffect;
import forsaken.TheForsakenMod;
import forsaken.cards.AbstractForsakenCard;

@SuppressWarnings("unused")
public class BlightOfFamine extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(BlightOfFamine.class.getSimpleName());

    public BlightOfFamine() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
        magicNumber = baseMagicNumber = 3;
        upgradeCostTo = 0;
        unplayedEffect = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (dontTriggerOnUseCard) {
            // unplayed trigger
            monsterList().forEach(mon -> applyToEnemy(mon, new WeakPower(mon, magicNumber, false)));
            applyToSelf(new WeakPower(p, magicNumber, false));
            return;
        }

        AbstractPower power = p.getPower(WeakPower.POWER_ID);
        if (power != null) {
            int amount = power.amount;
            qAction(new VFXAction(new HealEffect(p.hb.x - p.animX, p.hb.cY, 20)));
            qAction(new RemoveSpecificPowerAction(p, p, power));
            applyToEnemy(m, new WeakPower(m, amount, false));
        }
    }
}
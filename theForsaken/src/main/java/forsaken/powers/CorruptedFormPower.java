package forsaken.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import forsaken.TheForsakenMod;
import forsaken.relics.PlagueMask;


//Reduce hp lost by 1/3 (1/2) and convert to poison instead.

public class CorruptedFormPower extends AbstractForsakenPower implements CloneablePowerInterface {
    public static final String POWER_ID = TheForsakenMod.makeID(CorruptedFormPower.class.getSimpleName());

    private final int reduceBy;

    public CorruptedFormPower(final AbstractCreature owner, final int reduceBy) {
        super(POWER_ID, owner, -1);

        this.reduceBy = reduceBy;

        type = PowerType.BUFF;
        priority = 4;

        loadRegion("corrupted_form");
        updateDescription();
    }

    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        if (damageAmount > 0 && info.type != DamageType.HP_LOSS) {
            this.flash();
            applyToSelf(new PoisonPower(owner, owner, damageAmount));
            AbstractPlayer p = AbstractDungeon.player;
            if(owner == p && p.hasRelic(PlagueMask.ID)) {
                p.getRelic(PlagueMask.ID).flash();
                AbstractMonster mo = AbstractDungeon.getRandomMonster();
                if (mo != null) {
                    applyToEnemy(mo, new PoisonPower(mo, p, damageAmount));
                }
            }
            return 0;
        }
        return damageAmount;
    }

    @Override
    public float atDamageReceive(float damage, DamageType damageType) {
        if (damageType != DamageType.HP_LOSS) {
            return damage - damage * (1f / reduceBy);
        }
        return super.atDamageReceive(damage, damageType);
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
        if (reduceBy == 2) {
            description += DESCRIPTIONS[2];
        } else {
            description += DESCRIPTIONS[1];
        }
        description += DESCRIPTIONS[3];
    }

    @Override
    public AbstractPower makeCopy() {
        return new CorruptedFormPower(owner, reduceBy);
    }
}

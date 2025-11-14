package forsaken.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import forsaken.TheForsakenMod;
import forsaken.relics.PlagueMask;


//Reduce hp lost by 1/3 (1/2) and convert to poison instead.

public class CorruptedFormPower extends AbstractPower implements CloneablePowerInterface {
    private static final String POWER_ID = TheForsakenMod.makeID(CorruptedFormPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private int reduceBy;
    private boolean isUpgraded;

    public CorruptedFormPower(final AbstractCreature owner, final int reduceBy, final boolean isUpgraded) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.reduceBy = reduceBy;
        this.isUpgraded = isUpgraded;

        type = PowerType.BUFF;
        priority = 4;

        loadRegion("corrupted_form");
        updateDescription();
    }

    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        if (damageAmount > 0 && info.type != DamageType.HP_LOSS) {
            this.flash();
            float reduceAmount = damageAmount * (1.0F / this.reduceBy);
            int poisonToApply = damageAmount  - MathUtils.floor(reduceAmount);
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(this.owner, this.owner, new PoisonPower(this.owner, this.owner, poisonToApply), poisonToApply)
            );
            AbstractPlayer p = AbstractDungeon.player;
            if(owner == p && p.hasRelic(PlagueMask.ID)) {
                p.getRelic(PlagueMask.ID).flash();
                AbstractMonster mo = AbstractDungeon.getRandomMonster();
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p, new PoisonPower(mo, p, poisonToApply), poisonToApply, AttackEffect.POISON));
            }
            return 0;
        }
        return damageAmount;
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        if (this.isUpgraded) {
            description = DESCRIPTIONS[1];
        } else {
            description = DESCRIPTIONS[0];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new CorruptedFormPower(owner, reduceBy, isUpgraded);
    }
}

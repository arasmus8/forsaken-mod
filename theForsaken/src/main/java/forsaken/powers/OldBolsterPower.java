package forsaken.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import forsaken.TheForsakenMod;


//Reduce damage dealt to 1

public class OldBolsterPower extends AbstractForsakenPower implements CloneablePowerInterface {
    private static final String POWER_ID = TheForsakenMod.makeID(OldBolsterPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private boolean usedThisTurn;

    public OldBolsterPower(final AbstractCreature owner, final int amount) {
        super(POWER_ID, owner, amount);

        type = PowerType.BUFF;
        isTurnBased = false;
        usedThisTurn = true;

        this.loadRegion("bolster");

        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        usedThisTurn = false;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (!card.purgeOnUse && card.type == AbstractCard.CardType.ATTACK && !usedThisTurn) {
            this.flash();
            usedThisTurn = true;
            if (card.target.equals(AbstractCard.CardTarget.ALL) || card.target.equals(AbstractCard.CardTarget.ALL_ENEMY)) {
                for(AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {

                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, owner, new WeakPower(m, 1, false), 1));
                }
            } else {
                AbstractCreature m = action.target;
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, owner, new WeakPower(m, 1, false), 1));
            }
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(owner, owner, amount));
        }
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new OldBolsterPower(owner, amount);
    }
}

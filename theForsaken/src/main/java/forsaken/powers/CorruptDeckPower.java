package forsaken.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import forsaken.TheForsakenMod;

import java.util.Optional;


public class CorruptDeckPower extends AbstractForsakenPower implements CloneablePowerInterface {
    public static final String POWER_ID = TheForsakenMod.makeID(CorruptDeckPower.class.getSimpleName());

    private static final int DRAW_AMOUNT = 2;
    public int maxCardsEachTurn;

    public CorruptDeckPower(final AbstractCreature owner, final int maxCardsEachTurn) {
        super(POWER_ID, owner, DRAW_AMOUNT);

        this.maxCardsEachTurn = maxCardsEachTurn;

        type = PowerType.BUFF;
        isTurnBased = false;

        this.loadRegion("corrupt_deck");
        updateDescription();
    }

    public CorruptDeckPower(AbstractCreature owner, int maxCardsEachTurn, int amount) {
        this(owner, maxCardsEachTurn);
        this.amount = amount;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        updateDescription();
    }

    @Override
    public void atStartOfTurnPostDraw() {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(this.owner, this.amount));
        updateDescription();
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        Optional<AbstractPower> existing = Optional.ofNullable(AbstractDungeon.player.getPower(POWER_ID));
        if (existing.isPresent() && maxCardsEachTurn > ((CorruptDeckPower)existing.get()).maxCardsEachTurn){
            ((CorruptDeckPower)existing.get()).maxCardsEachTurn = maxCardsEachTurn;
        }
    }

    @Override
    public void updateDescription() {
        int cardsPlayedThisTurn = AbstractDungeon.actionManager.cardsPlayedThisTurn.size();
        int i = maxCardsEachTurn - cardsPlayedThisTurn;
        String extraDraw = DESCRIPTIONS[0] + amount;
        if (i == 0) {
            description = extraDraw + DESCRIPTIONS[4];
        } else if (i == 1) {
            description = extraDraw + DESCRIPTIONS[1] + i + DESCRIPTIONS[3];
        } else {
            description = extraDraw + DESCRIPTIONS[1] + i + DESCRIPTIONS[2];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new CorruptDeckPower(owner, maxCardsEachTurn, amount);
    }
}

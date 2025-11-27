package forsaken.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import forsaken.TheForsakenMod;
import forsaken.cards.skills.CreepingInfection;

public class SpreadingPlaguePower extends AbstractForsakenPower implements CloneablePowerInterface {
    public static final String POWER_ID = TheForsakenMod.makeID(SpreadingPlaguePower.class.getSimpleName());

    public SpreadingPlaguePower(AbstractCreature owner) {
        super(POWER_ID, owner, -1);
        type = PowerType.BUFF;

        loadRegion("spreading_plague");
        description = DESCRIPTIONS[0];
    }

    @Override
    public void onInitialApplication() {
        AbstractDungeon.player.hand.group.forEach(card -> {
            if (card instanceof CreepingInfection) {
                card.freeToPlayOnce = true;
            }
        });
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        if (card instanceof CreepingInfection) {
            card.freeToPlayOnce = true;
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new SpreadingPlaguePower(owner);
    }
}

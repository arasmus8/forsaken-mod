package forsaken.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import forsaken.TheForsakenMod;
import forsaken.cards.AbstractForsakenCard;

public class PrudencePower extends AbstractForsakenPower implements CloneablePowerInterface {
    public static final String POWER_ID = TheForsakenMod.makeID(PrudencePower.class.getSimpleName());

    public PrudencePower(AbstractCreature owner, int amount) {
        super(POWER_ID, owner, amount);
        type = PowerType.BUFF;
        isTurnBased = false;

        loadRegion("prudence");
        updateDescription();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            AbstractPlayer p = AbstractDungeon.player;
            int count = AbstractForsakenCard.unplayableCards(p.hand).size();
            for (int i = 0; i < count; i++) {
                gainBlock();
            }
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new PrudencePower(owner, amount);
    }
}

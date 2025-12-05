package forsaken.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import forsaken.TheForsakenMod;
import forsaken.cards.skills.Parry;

public class RipostePower extends AbstractForsakenPower implements CloneablePowerInterface {
    public static final String POWER_ID = TheForsakenMod.makeID(RipostePower.class.getSimpleName());

    public RipostePower(AbstractCreature owner, int amount) {
        super(POWER_ID, owner, amount);
        type = PowerType.BUFF;

        loadRegion("riposte");
        updateDescription();
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        // When we draw a Parry, apply vulnerable
        if (card.cardID.equals(Parry.ID)) {
            flash();
            AbstractMonster target = AbstractDungeon.getRandomMonster();
            if (target != null) {
                applyToEnemy(target, new VulnerablePower(target, amount, false));
            }
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new RipostePower(owner, amount);
    }
}

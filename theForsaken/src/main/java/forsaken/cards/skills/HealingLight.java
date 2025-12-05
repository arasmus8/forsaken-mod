package forsaken.cards.skills;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import forsaken.TheForsakenMod;
import forsaken.actions.BiFunctionalAction;
import forsaken.cards.AbstractForsakenCard;
import forsaken.powers.FearPower;
import forsaken.powers.SunlightPower;

public class HealingLight extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(HealingLight.class.getSimpleName());

    public HealingLight() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        magicNumber = baseMagicNumber = 2;
        upgradeMagicNumberBy = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) {
            qAction(new BiFunctionalAction<>(this::triggerSunlightAndApplyFear, p));
        }
    }

    private boolean triggerSunlightAndApplyFear(boolean firstUpdate, AbstractPlayer p) {
        AbstractPower power = p.getPower(SunlightPower.POWER_ID);
        if (power != null && power.amount > 0) {
            power.onSpecificTrigger();
            monsterList().forEach(m -> {
                applyToEnemy(m, new FearPower(m, 2));
            });
        }
        return true;
    }
}
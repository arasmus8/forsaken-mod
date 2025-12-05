package forsaken.cards.skills;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.actions.XCostAction;
import forsaken.cards.AbstractForsakenCard;
import forsaken.powers.FearPower;

@SuppressWarnings("unused")
public class Horror extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(Horror.class.getSimpleName());

    public Horror() {
        super(ID, -1, CardType.SKILL, CardRarity.RARE, CardTarget.ENEMY);
        exhaust = true;
        magicNumber = baseMagicNumber = 4;
        upgradeMagicNumberBy = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        qAction(new XCostAction<>(this, this::doEffect, m));
    }

    public boolean doEffect(Integer energyCount, AbstractMonster m) {
        if (m != null && energyCount > 0) {
            for (int i = 0; i < energyCount; i++) {
                applyToEnemy(m, new FearPower(m, magicNumber));
            }
        }
        return true;
    }
}
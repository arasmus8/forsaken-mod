package forsaken.cards.skills;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import forsaken.TheForsakenMod;
import forsaken.cards.AbstractQuickdrawCard;

public class DarkFog extends AbstractQuickdrawCard {
    public static final String ID = TheForsakenMod.makeID(DarkFog.class.getSimpleName());

    public DarkFog() {
        super(ID, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        magicNumber = baseMagicNumber = 3;
        upgradeMagicNumberBy = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new PoisonPower(p, p, magicNumber));
        monsterList().forEach(mon -> {
            applyToEnemy(mon, new PoisonPower(mon, p, magicNumber));
        });
    }
}
package forsaken.cards.skills;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.cards.AbstractQuickdrawCard;
import forsaken.powers.FearPower;

public class LightRoll extends AbstractQuickdrawCard {
    public static final String ID = TheForsakenMod.makeID(LightRoll.class.getSimpleName());

    public LightRoll() {
        super(ID, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        block = baseBlock = 2;
        upgradeBlockBy = 1;
        magicNumber = baseMagicNumber = 1;
        upgradeMagicNumberBy = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyPowersToBlock();
        gainBlock();
        monsterList().forEach(mon -> applyToEnemy(mon, new FearPower(mon, magicNumber)) );
    }
}
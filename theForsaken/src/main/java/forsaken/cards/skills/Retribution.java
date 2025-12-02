package forsaken.cards.skills;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ThornsPower;
import forsaken.TheForsakenMod;
import forsaken.cards.AbstractQuickdrawCard;

public class Retribution extends AbstractQuickdrawCard {
    public static final String ID = TheForsakenMod.makeID(Retribution.class.getSimpleName());

    public Retribution() {
        super(ID, CardType.SKILL, CardRarity.RARE, CardTarget.NONE);
        magicNumber = baseMagicNumber = 2;
        upgradeMagicNumberBy = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new ThornsPower(AbstractDungeon.player, magicNumber));
    }

}
package forsaken.cards.skills;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import forsaken.TheForsakenMod;
import forsaken.cards.AbstractForsakenCard;

public class FatRoll extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(FatRoll.class.getSimpleName());

    public FatRoll() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        block = baseBlock = 6;
        upgradeBlockBy = 2;
        magicNumber = baseMagicNumber = 1;
        unplayedEffect = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (dontTriggerOnUseCard) {
            applyToSelf(new DrawCardNextTurnPower(p, magicNumber));
            return;
        }
        gainBlock();
    }
}

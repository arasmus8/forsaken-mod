package forsaken.oldCards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.characters.TheForsaken;
import forsaken.powers.FountainOfLifePower;

@SuppressWarnings("unused")
public class FountainOfLife extends AbstractOldForsakenCard {
    public static final String ID = TheForsakenMod.makeOldID(FountainOfLife.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheForsaken.Enums.COLOR_GOLD;

    private static final int COST = 3;

    private static final int LIFE_GAIN_AMT = 2;
    private static final int UPGRADE_LIFE_GAIN_AMT = 1;

    public FountainOfLife() {
        super(ID, COST, TYPE, RARITY, TARGET, COLOR, CardTags.HEALING);
        baseMagicNumber = LIFE_GAIN_AMT;
        magicNumber = LIFE_GAIN_AMT;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FountainOfLifePower(p, magicNumber), magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_LIFE_GAIN_AMT);
            initializeDescription();
        }
    }
}
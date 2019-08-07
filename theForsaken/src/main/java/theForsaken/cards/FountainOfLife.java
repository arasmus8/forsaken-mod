package theForsaken.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theForsaken.TheForsakenMod;
import theForsaken.characters.TheForsaken;
import theForsaken.powers.FountainOfLifePower;

import static theForsaken.TheForsakenMod.makeCardPath;

public class FountainOfLife extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = TheForsakenMod.makeID(FountainOfLife.class.getSimpleName());
    public static final String IMG = makeCardPath("FountainOfLife.png");
    // Must have an image with the same NAME as the card in your image folder!


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheForsaken.Enums.COLOR_GRAY;

    private static final int COST = 1;

    private static final int LIFE_GAIN_AMT = 3;
    private static final int UPGRADE_LIFE_GAIN_AMT = 1;

    // /STAT DECLARATION/


    public FountainOfLife() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = LIFE_GAIN_AMT;
        magicNumber = LIFE_GAIN_AMT;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FountainOfLifePower(p, magicNumber), magicNumber));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_LIFE_GAIN_AMT);
            initializeDescription();
        }
    }
}
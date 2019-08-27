package theForsaken.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import theForsaken.TheForsakenMod;
import theForsaken.characters.TheForsaken;
import theForsaken.powers.TearsOfSunlightPower;

import static theForsaken.TheForsakenMod.makeCardPath;

public class TearsOfSunlight extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = TheForsakenMod.makeID(TearsOfSunlight.class.getSimpleName());
    public static final String IMG = makeCardPath("TearsOfSunlight.png");
    // Must have an image with the same NAME as the card in your image folder!


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheForsaken.Enums.COLOR_GOLD;

    private static final int COST = 2;

    private static final int REGEN_AMT = 1;
    private static final int UPGRADE_REGEN_AMT = 1;

    private static final int DEX_LOSS = 3;
    private static final int UPGRADE_DEX_LOSS = -1;

    // /STAT DECLARATION/


    public TearsOfSunlight() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = REGEN_AMT;
        magicNumber = REGEN_AMT;
        defaultBaseSecondMagicNumber = DEX_LOSS;
        defaultSecondMagicNumber = DEX_LOSS;
        tags.add(CardTags.HEALING);
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new TearsOfSunlightPower(p, this.magicNumber)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DexterityPower(p, -this.defaultSecondMagicNumber), -this.defaultSecondMagicNumber));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_REGEN_AMT);
            upgradeDefaultSecondMagicNumber(UPGRADE_DEX_LOSS);
            initializeDescription();
        }
    }
}
package theForsaken.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theForsaken.TheForsakenMod;
import theForsaken.actions.NobleSacrificeAction;
import theForsaken.characters.TheForsaken;

import static theForsaken.TheForsakenMod.makeCardPath;

public class NobleSacrifice extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = TheForsakenMod.makeID(NobleSacrifice.class.getSimpleName());
    public static final String IMG = makeCardPath("NobleSacrifice.png");
    // Must have an image with the same NAME as the card in your image folder!
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESC = CARD_STRINGS.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheForsaken.Enums.COLOR_GRAY;

    private static final int COST = 0;
    // /STAT DECLARATION/


    public NobleSacrifice() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int extraDraw = 0;
        if (upgraded) {
            extraDraw += 1;
        }
        AbstractDungeon.actionManager.addToBottom(new NobleSacrificeAction(extraDraw));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESC;
            initializeDescription();
        }
    }
}
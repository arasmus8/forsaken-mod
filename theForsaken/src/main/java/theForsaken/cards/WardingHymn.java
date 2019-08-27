package theForsaken.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theForsaken.TheForsakenMod;
import theForsaken.characters.TheForsaken;

import static theForsaken.TheForsakenMod.makeCardPath;

public class WardingHymn extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = TheForsakenMod.makeID(WardingHymn.class.getSimpleName());
    public static final String IMG = makeCardPath("WardingHymn.png");
    // Must have an image with the same NAME as the card in your image folder!
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String[] EXTENDED_DESCRIPTION = CARD_STRINGS.EXTENDED_DESCRIPTION;
    private static final String UPGRADE_DESC = CARD_STRINGS.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheForsaken.Enums.COLOR_GOLD;

    private static final int COST = -2;
    private static final int BLOCK = 12;
    private static final int UPGRADE_BLOCK_AMT = 4;

    private int actualBaseBlock;

    private static final int MAGIC = 3;
    private static final int UPGRADE_MAGIC_AMOUNT = 1;

    private int otherCardsPlayed;

    // /STAT DECLARATION/


    public WardingHymn() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = BLOCK;
        this.actualBaseBlock = BLOCK;
        this.baseMagicNumber = MAGIC;
        this.magicNumber = MAGIC;
        this.otherCardsPlayed = 0;
    }

    public void calculateBlock() {
        // this.baseBlock = Math.max(this.actualBaseBlock - ((this.actualBaseBlock * this.otherCardsPlayed) / this.magicNumber), 0);
        this.baseBlock = this.actualBaseBlock;
        this.applyPowers();
        this.baseBlock = Math.max(this.baseBlock - ((this.baseBlock * this.otherCardsPlayed) / this.magicNumber), 0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.dontTriggerOnUseCard) {
            if (this.block > 0) {
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
            }
            this.otherCardsPlayed = 0;
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        this.cantUseMessage = EXTENDED_DESCRIPTION[0];
        return false;
    }

    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
        this.dontTriggerOnUseCard = true;
        AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(this, true));
    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard card) {
        if (!card.dontTriggerOnUseCard) {
            this.otherCardsPlayed += 1;
            this.calculateBlock();
        }
    }

    @Override
    public void atTurnStart() {
        this.otherCardsPlayed = 0;
        this.calculateBlock();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_BLOCK_AMT);
            this.actualBaseBlock += UPGRADE_BLOCK_AMT;
            upgradeMagicNumber(UPGRADE_MAGIC_AMOUNT);
            rawDescription = UPGRADE_DESC;
            initializeDescription();
        }
    }
}
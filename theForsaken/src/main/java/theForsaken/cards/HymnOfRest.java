package theForsaken.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BlurPower;
import theForsaken.TheForsakenMod;
import theForsaken.characters.TheForsaken;

import static theForsaken.TheForsakenMod.makeCardPath;

public class HymnOfRest extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = TheForsakenMod.makeID(HymnOfRest.class.getSimpleName());
    public static final String IMG = makeCardPath("HymnOfRest.png");
    // Must have an image with the same NAME as the card in your image folder!
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String[] EXTENDED_DESCRIPTION = CARD_STRINGS.EXTENDED_DESCRIPTION;

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheForsaken.Enums.COLOR_GRAY;

    private static final int COST = -2;

    private static final int MAGIC = 4;
    private static final int UPGRADE_MAGIC_AMT = 2;

    private int otherCardsPlayed;

    // /STAT DECLARATION/


    public HymnOfRest() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.isEthereal = true;
        this.baseMagicNumber = MAGIC;
        this.magicNumber = MAGIC;
        this.otherCardsPlayed = 0;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (dontTriggerOnUseCard) {
            int adjustedAmount = magicNumber - otherCardsPlayed;
            if (adjustedAmount > 0) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new BlurPower(p, adjustedAmount), adjustedAmount));
            }
            otherCardsPlayed = 0;
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
        }
    }

    @Override
    public void atTurnStart() {
        this.otherCardsPlayed = 0;
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC_AMT);
            initializeDescription();
        }
    }
}
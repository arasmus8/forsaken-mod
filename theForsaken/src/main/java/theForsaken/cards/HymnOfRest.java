package theForsaken.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theForsaken.TheForsakenMod;
import theForsaken.characters.TheForsaken;
import theForsaken.powers.HymnOfRestPower;

import static theForsaken.TheForsakenMod.makeCardPath;

public class HymnOfRest extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = TheForsakenMod.makeID(HymnOfRest.class.getSimpleName());
    public static final String IMG = makeCardPath("HymnOfRest.png");
    // Must have an image with the same NAME as the card in your image folder!
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION;
    private static final String[] EXTENDED_DESCRIPTION = CARD_STRINGS.EXTENDED_DESCRIPTION;

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheForsaken.Enums.COLOR_GOLD;

    private static final int COST = -2;

    private static final int MAGIC = 1;
    private static final int UPGRADE_MAGIC_AMT = 1;

    private int otherCardsPlayed;
    private static final int BONUS_THRESHOLD = 4;

    // /STAT DECLARATION/

    public HymnOfRest() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = MAGIC;
        this.magicNumber = MAGIC;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (dontTriggerOnUseCard) {
            AbstractPower pwr = new HymnOfRestPower(p, magicNumber, otherCardsPlayed < BONUS_THRESHOLD);
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, pwr, magicNumber));
        }
        if (p.hasPower(HymnOfRestPower.POWER_ID) && otherCardsPlayed < BONUS_THRESHOLD) {
            HymnOfRestPower pow = (HymnOfRestPower) p.getPower(HymnOfRestPower.POWER_ID);
            pow.retainAll();
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        this.cantUseMessage = EXTENDED_DESCRIPTION[0];
        return false;
    }

    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
        this.otherCardsPlayed = AbstractDungeon.player.cardsPlayedThisTurn;
        this.dontTriggerOnUseCard = true;
        AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(this, true));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC_AMT);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
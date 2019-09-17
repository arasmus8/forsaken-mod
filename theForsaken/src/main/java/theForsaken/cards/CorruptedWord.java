package theForsaken.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import theForsaken.CustomTags;
import theForsaken.TheForsakenMod;
import theForsaken.characters.TheForsaken;
import theForsaken.relics.PlagueMask;

import static theForsaken.TheForsakenMod.makeCardPath;

public class CorruptedWord extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = TheForsakenMod.makeID(CorruptedWord.class.getSimpleName());
    public static final String IMG = makeCardPath("CorruptedWord.png");
    // Must have an image with the same NAME as the card in your image folder!\
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheForsaken.Enums.COLOR_GOLD;

    private static final int COST = 1;

    private static final int MAGIC = 8;
    private static final int UPGRADED_MAGIC = -2;

    // /STAT DECLARATION/


    public CorruptedWord() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = MAGIC;
        magicNumber = baseMagicNumber;
        this.tags.add(CustomTags.WORD_CARD);
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.dontTriggerOnUseCard) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PoisonPower(p, p, magicNumber), magicNumber, AttackEffect.POISON));
            int amount = magicNumber;
            if (upgraded) {
                amount *= 2;
            }
            for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, p, new PoisonPower(monster, p, amount), amount, AttackEffect.POISON));
            }
            if (p.hasRelic(PlagueMask.ID)) {
                p.getRelic(PlagueMask.ID).flash();
                AbstractMonster mo = AbstractDungeon.getRandomMonster();
                if (mo != null) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p, new PoisonPower(mo, p, magicNumber), magicNumber, AttackEffect.POISON));
                }
            }
        } else if (p != null) {
            if (p.hasPower(PoisonPower.POWER_ID)) {
                int amount = p.getPower(PoisonPower.POWER_ID).amount;
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, PoisonPower.POWER_ID));
                AbstractMonster mo = AbstractDungeon.getRandomMonster();
                if (mo != null) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p, new PoisonPower(mo, p, amount), amount, AttackEffect.POISON));
                }
            }
        }
    }

    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
        this.dontTriggerOnUseCard = true;
        this.freeToPlayOnce = true;
        AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(this, true));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADED_MAGIC);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
package theForsaken.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import theForsaken.CustomTags;
import theForsaken.TheForsakenMod;
import theForsaken.characters.TheForsaken;
import theForsaken.powers.BonusDamagePower;

import static theForsaken.TheForsakenMod.makeCardPath;

public class Eulogy extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = TheForsakenMod.makeID(Eulogy.class.getSimpleName());
    public static final String IMG = makeCardPath("Eulogy.png");
    // Must have an image with the same NAME as the card in your image folder!

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheForsaken.Enums.COLOR_GOLD;

    private static final int COST = 0;

    private static final int VULNERABILITY_AMT = 1;
    private static final int UPGRADE_PLUS_VULNERABILITY = 1;

    // /STAT DECLARATION/


    public Eulogy() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = VULNERABILITY_AMT;
        magicNumber = VULNERABILITY_AMT;
        this.isEthereal = true;
        this.tags.add(CustomTags.WORD_CARD);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster target) {
        if(this.dontTriggerOnUseCard) {
            this.exhaust = true;
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new BonusDamagePower(p, 3), 3));
        } else {
            for(AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new VulnerablePower(m, magicNumber, false), magicNumber));
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
            upgradeMagicNumber(UPGRADE_PLUS_VULNERABILITY);
            initializeDescription();
        }
    }
}
package theForsaken.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import theForsaken.TheForsakenMod;
import theForsaken.characters.TheForsaken;

import static theForsaken.TheForsakenMod.makeCardPath;

public class BlightOfFamine extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = TheForsakenMod.makeID(BlightOfFamine.class.getSimpleName());
    public static final String IMG = makeCardPath("BlightOfFamine.png");
    // Must have an image with the same NAME as the card in your image folder!

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheForsaken.Enums.COLOR_GOLD;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 0;

    private static final int MAGIC = 3;
    private static final int UPGRADED_MAGIC = 2;

    // /STAT DECLARATION/


    public BlightOfFamine() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = MAGIC;
        magicNumber = baseMagicNumber;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster mon) {
        if (dontTriggerOnUseCard) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new VulnerablePower(p, magicNumber, false), magicNumber));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new WeakPower(p, magicNumber, false), magicNumber));
            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new VulnerablePower(m, magicNumber, false), magicNumber));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new WeakPower(m, magicNumber, false), magicNumber));
            }
        } else {
            if (p.hasPower(VulnerablePower.POWER_ID)) {
                AbstractPower v = p.getPower(VulnerablePower.POWER_ID);
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mon, p, new VulnerablePower(mon, v.amount, false), v.amount));
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, v));
            }
            if (p.hasPower(WeakPower.POWER_ID)) {
                AbstractPower w = p.getPower(WeakPower.POWER_ID);
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mon, p, new WeakPower(mon, w.amount, false), w.amount));
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, w));
            }
        }
    }

    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
        AbstractMonster mo = AbstractDungeon.getRandomMonster();
        if (mo != null) {
            this.dontTriggerOnUseCard = true;
            this.freeToPlayOnce = true;
            CardQueueItem queueItem = new CardQueueItem(this, mo, EnergyPanel.getCurrentEnergy(), false);
            queueItem.isEndTurnAutoPlay = true;
            AbstractDungeon.actionManager.cardQueue.add(queueItem);
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            upgradeMagicNumber(UPGRADED_MAGIC);
            initializeDescription();
        }
    }
}
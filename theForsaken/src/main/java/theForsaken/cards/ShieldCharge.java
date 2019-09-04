package theForsaken.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import theForsaken.TheForsakenMod;
import theForsaken.characters.TheForsaken;

import static theForsaken.TheForsakenMod.makeCardPath;

public class ShieldCharge extends AbstractDynamicCard {
    // TEXT DECLARATION

    public static final String ID = TheForsakenMod.makeID(ShieldCharge.class.getSimpleName());
    public static final String IMG = makeCardPath("ShieldCharge.png");
    // Must have an image with the same NAME as the card in your image folder!.

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheForsaken.Enums.COLOR_GOLD;

    private static final int COST = 0;

    private static final int DAMAGE = 5;
    private static final int UPGRADE_PLUS_DMG = 2;

    private static final int MAGIC = 3;
    private static final int UPGRADED_MAGIC = 1;
    // /STAT DECLARATION/

    public ShieldCharge() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = MAGIC;
        magicNumber = baseMagicNumber;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.dontTriggerOnUseCard) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new NextTurnBlockPower(p, magicNumber), magicNumber));
            return;
        }
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
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
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeMagicNumber(UPGRADED_MAGIC);
            initializeDescription();
        }
    }
}
package theForsaken.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theForsaken.TheForsakenMod;
import theForsaken.characters.TheForsaken;

public class BattleHymn extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(BattleHymn.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheForsaken.Enums.COLOR_GOLD;

    private static final int COST = -2;

    private static final int DAMAGE = 15;
    private static final int UPGRADE_PLUS_DMG = 5;

    private static final int MAGIC = 3;
    private static final int UPGRADE_MAGIC = 1;

    private int otherCardsPlayed;
    private int actualBaseDamage;

    public BattleHymn() {
        super(ID, COST, TYPE, RARITY, TARGET, COLOR);
        baseDamage = DAMAGE;
        actualBaseDamage = DAMAGE;
        baseMagicNumber = MAGIC;
        magicNumber = baseMagicNumber;
        otherCardsPlayed = 0;
    }

    private void calculateDamage() {
        // this.baseDamage = Math.max(this.actualBaseDamage - ((this.actualBaseDamage * this.otherCardsPlayed) / this.magicNumber), 0);
        this.baseDamage = this.actualBaseDamage;
        this.applyPowers();
        this.baseDamage = Math.max(this.baseDamage - ((this.baseDamage * this.otherCardsPlayed) / this.magicNumber), 0);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.dontTriggerOnUseCard) {
            if (damage > 0) {
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.getRandomMonster(), new DamageInfo(p, damage, damageTypeForTurn), AttackEffect.BLUNT_HEAVY));
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
            this.calculateDamage();
        }
    }

    @Override
    public void atTurnStart() {
        this.otherCardsPlayed = 0;
        this.calculateDamage();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            actualBaseDamage += UPGRADE_PLUS_DMG;
            upgradeMagicNumber(UPGRADE_MAGIC);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
package forsaken.oldCards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import forsaken.CustomTags;
import forsaken.TheForsakenMod;
import forsaken.characters.TheForsaken;
import forsaken.relics.PlagueMask;

public class CorruptedWord extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(CorruptedWord.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheForsaken.Enums.COLOR_GOLD;

    private static final int COST = 1;

    private static final int MAGIC = 8;
    private static final int UPGRADED_MAGIC = -2;

    public CorruptedWord() {
        super(ID, COST, TYPE, RARITY, TARGET, COLOR, CustomTags.WORD_CARD);
        baseMagicNumber = MAGIC;
        magicNumber = baseMagicNumber;
    }

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
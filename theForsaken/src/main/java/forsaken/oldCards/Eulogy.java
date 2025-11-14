package forsaken.oldCards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import forsaken.CustomTags;
import forsaken.TheForsakenMod;
import forsaken.characters.TheForsaken;
import forsaken.powers.BonusDamagePower;

public class Eulogy extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(Eulogy.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheForsaken.Enums.COLOR_GOLD;

    private static final int COST = 0;

    private static final int VULNERABILITY_AMT = 1;
    private static final int UPGRADE_PLUS_VULNERABILITY = 1;

    public Eulogy() {
        super(ID, COST, TYPE, RARITY, TARGET, COLOR, CustomTags.WORD_CARD);
        baseMagicNumber = VULNERABILITY_AMT;
        magicNumber = VULNERABILITY_AMT;
        this.isEthereal = true;
    }

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

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_VULNERABILITY);
            initializeDescription();
        }
    }
}
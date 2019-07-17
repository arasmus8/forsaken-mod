package theForsaken.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theForsaken.TheForsakenMod;
import theForsaken.actions.WrathOfGodAction;
import theForsaken.characters.TheForsaken;

import static theForsaken.TheForsakenMod.makeCardPath;

public class WrathOfGod extends AbstractDynamicCard {
    // TEXT DECLARATION

    public static final String ID = TheForsakenMod.makeID(WrathOfGod.class.getSimpleName());
    public static final String IMG = makeCardPath("WrathOfGod.png");
    // Must have an image with the same NAME as the card in your image folder!.
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = CARD_STRINGS.NAME;

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheForsaken.Enums.COLOR_GRAY;

    private static final int COST = -1;

    private static final int DAMAGE = 3;
    private static final int BLOCK = 3;
    private static final int UPGRADE_PLUS_DMG = 2;
    private static final int UPGRADE_PLUS_BLK = 2;

    // /STAT DECLARATION/

    public WrathOfGod() {
        this(0);
    }

    public WrathOfGod(int upgrades) {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        timesUpgraded = upgrades;
        baseDamage = DAMAGE + timesUpgraded;
        baseBlock = BLOCK + timesUpgraded;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new WrathOfGodAction(p, m, damage, block, damageTypeForTurn, freeToPlayOnce, energyOnUse));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        this.upgradeDamage(UPGRADE_PLUS_DMG);
        this.upgradeBlock(UPGRADE_PLUS_BLK);
        ++this.timesUpgraded;
        this.upgraded = true;
        this.name = NAME + "+" + this.timesUpgraded;
        this.initializeTitle();
    }

    // Can upgrade any number of times
    @Override
    public boolean canUpgrade() {
        return true;
    }
}
package theForsaken.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theForsaken.TheForsakenMod;
import theForsaken.characters.TheForsaken;

import static theForsaken.TheForsakenMod.makeCardPath;

public class WordsOfPestilence extends AbstractDynamicCard {
    // TEXT DECLARATION

    public static final String ID = TheForsakenMod.makeID(WordsOfPestilence.class.getSimpleName());
    public static final String IMG = makeCardPath("WordsOfPestilence.png");
    // Must have an image with the same NAME as the card in your image folder!.

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheForsaken.Enums.COLOR_GRAY;

    private static final int COST = 0;

    private static final int DAMAGE = 13;
    private static final int UPGRADE_PLUS_DMG = 4;

    private static final int SELF_DAMAGE = 8;
    private static final int UPGRADE_SELF_DAMAGE = -3;
    // /STAT DECLARATION/

    public WordsOfPestilence() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.isMultiDamage = true;
        baseDamage = DAMAGE;
        baseMagicNumber = SELF_DAMAGE;
        magicNumber = SELF_DAMAGE;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(p, new DamageInfo(p, magicNumber, damageTypeForTurn), AttackEffect.POISON));
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, damageTypeForTurn, AttackEffect.POISON));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeMagicNumber(UPGRADE_SELF_DAMAGE);
            initializeDescription();
        }
    }
}
package forsaken.oldCards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.CustomTags;
import forsaken.TheForsakenMod;
import forsaken.characters.TheForsaken;

public class WordsOfPestilence extends AbstractOldForsakenCard {
    public static final String ID = TheForsakenMod.makeOldID(WordsOfPestilence.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheForsaken.Enums.COLOR_GOLD;

    private static final int COST = 0;

    private static final int DAMAGE = 13;
    private static final int UPGRADE_PLUS_DMG = 4;

    private static final int SELF_DAMAGE = 8;
    private static final int UPGRADE_SELF_DAMAGE = -3;

    public WordsOfPestilence() {
        super(ID, COST, TYPE, RARITY, TARGET, COLOR, CustomTags.WORD_CARD);
        this.isMultiDamage = true;
        baseDamage = DAMAGE;
        baseMagicNumber = SELF_DAMAGE;
        magicNumber = SELF_DAMAGE;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(p, new DamageInfo(p, magicNumber, damageTypeForTurn), AttackEffect.POISON));
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, damageTypeForTurn, AttackEffect.POISON));
    }

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
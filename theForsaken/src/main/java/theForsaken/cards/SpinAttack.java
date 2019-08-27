package theForsaken.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theForsaken.TheForsakenMod;
import theForsaken.characters.TheForsaken;

import static theForsaken.TheForsakenMod.makeCardPath;

public class SpinAttack extends AbstractDynamicCard {
    // TEXT DECLARATION

    public static final String ID = TheForsakenMod.makeID(SpinAttack.class.getSimpleName());
    public static final String IMG = makeCardPath("SpinAttack.png");
    // Must have an image with the same NAME as the card in your image folder!.

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheForsaken.Enums.COLOR_GOLD;

    private static final int COST = 1;

    private static final int DAMAGE = 6;

    private static final int DEX_PER_SPIN = 3;
    private static final int UPGRADE_DEX_PER_SPIN = -1;
    // /STAT DECLARATION/

    public SpinAttack() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = DEX_PER_SPIN;
        magicNumber = DEX_PER_SPIN;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int amount = 1;
        if (p.hasPower("Dexterity")) {
            int dex = p.getPower("Dexterity").amount;
            while (dex >= magicNumber) {
                amount += 1;
                dex -= magicNumber;
            }
        }
        for (int i = 0; i < amount; ++i) {// 41
            AbstractDungeon.actionManager.addToBottom(new AttackDamageRandomEnemyAction(this, AttackEffect.SLASH_HORIZONTAL));// 42
        }
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_DEX_PER_SPIN);
            initializeDescription();
        }
    }
}
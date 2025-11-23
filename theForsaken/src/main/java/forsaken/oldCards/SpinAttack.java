package forsaken.oldCards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.characters.TheForsaken;

public class SpinAttack extends AbstractOldForsakenCard {
    public static final String ID = TheForsakenMod.makeOldID(SpinAttack.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheForsaken.Enums.COLOR_GOLD;

    private static final int COST = 1;

    private static final int DAMAGE = 6;

    private static final int DEX_PER_SPIN = 3;
    private static final int UPGRADE_DEX_PER_SPIN = -1;

    public SpinAttack() {
        super(ID, COST, TYPE, RARITY, TARGET, COLOR);
        baseDamage = DAMAGE;
        baseMagicNumber = DEX_PER_SPIN;
        magicNumber = DEX_PER_SPIN;
    }

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

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_DEX_PER_SPIN);
            initializeDescription();
        }
    }
}
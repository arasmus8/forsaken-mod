package theForsaken.cards;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theForsaken.TheForsakenMod;
import theForsaken.actions.SpinAttackAction;
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
    public static final CardColor COLOR = TheForsaken.Enums.COLOR_GRAY;

    private static final int COST = 1;

    private static final int DAMAGE = 7;
    private static final int UPGRADE_PLUS_DMG = 3;
    // /STAT DECLARATION/

    public SpinAttack() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int amount = 1 + (p.hasPower("Dexterity") ? p.getPower("Dexterity").amount : 0);
        AbstractDungeon.actionManager.addToBottom(new SpinAttackAction(AbstractDungeon.getRandomMonster(), new DamageInfo(p, damage, damageTypeForTurn), amount));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }
}
package theForsaken.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theForsaken.TheForsakenMod;
import theForsaken.characters.TheForsaken;

import java.util.UUID;

import static theForsaken.TheForsakenMod.makeCardPath;

public class Temperance extends AbstractDynamicCard {
    // TEXT DECLARATION

    public static final String ID = TheForsakenMod.makeID(Temperance.class.getSimpleName());
    public static final String IMG = makeCardPath("Temperance.png");
    // Must have an image with the same NAME as the card in your image folder!.

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheForsaken.Enums.COLOR_GRAY;

    private static final int COST = 2;

    private static final int DAMAGE = 6;

    private static final int MAGIC = 3;
    private static final int UPGRADE_MAGIC_AMT = 4;
    // /STAT DECLARATION/

    public Temperance() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = MAGIC;
        magicNumber = baseMagicNumber;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        damage = baseDamage;
        for (AbstractCard c : p.discardPile.group) {
            UUID uuid = c.uuid;
            if (!TheForsakenMod.usedCards.contains(uuid)) {
                damage += magicNumber;
            }
        }
        this.applyPowers();
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AttackEffect.SMASH));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC_AMT);
            initializeDescription();
        }
    }
}
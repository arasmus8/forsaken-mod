package forsaken.oldCards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RegenPower;
import forsaken.TheForsakenMod;
import forsaken.characters.TheForsaken;

@SuppressWarnings("unused")
public class Smite extends AbstractOldForsakenCard {
    public static final String ID = TheForsakenMod.makeOldID(Smite.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;

    private static final CardRarity RARITY = CardRarity.BASIC; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = TheForsaken.Enums.COLOR_GOLD;

    private static final int COST = 2;

    private static final int DAMAGE = 12;
    private static final int UPGRADE_PLUS_DMG = 4;

    private static final int REGEN_AMOUNT = 2;
    private static final int UPGRADE_REGEN = 1;

    public Smite() {
        super(ID, COST, TYPE, RARITY, TARGET, COLOR, CardTags.HEALING);
        baseDamage = DAMAGE;
        baseMagicNumber = REGEN_AMOUNT;
        magicNumber = REGEN_AMOUNT;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new RegenPower(p, this.magicNumber), this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeMagicNumber(UPGRADE_REGEN);
            initializeDescription();
        }
    }
}
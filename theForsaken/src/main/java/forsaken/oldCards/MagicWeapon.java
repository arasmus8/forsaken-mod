package forsaken.oldCards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.characters.TheForsaken;
import forsaken.powers.OldMagicWeaponPower;

@SuppressWarnings("unused")
public class MagicWeapon extends AbstractOldForsakenCard {
    public static final String ID = TheForsakenMod.makeOldID(MagicWeapon.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheForsaken.Enums.COLOR_GOLD;

    private static final int COST = 1;

    private static final int BONUS_DMG = 2;
    private static final int UPGRADE_BONUS_DMG = 1;

    public MagicWeapon() {
        super(ID, COST, TYPE, RARITY, TARGET, COLOR);
        baseMagicNumber = BONUS_DMG;
        magicNumber = BONUS_DMG;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new OldMagicWeaponPower(p, magicNumber), magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_BONUS_DMG);
            initializeDescription();
        }
    }
}
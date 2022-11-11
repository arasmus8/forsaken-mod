package theForsaken.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import theForsaken.TheForsakenMod;
import theForsaken.characters.TheForsaken;
import theForsaken.powers.TearsOfSunlightPower;

public class TearsOfSunlight extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(TearsOfSunlight.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheForsaken.Enums.COLOR_GOLD;

    private static final int COST = 2;

    private static final int REGEN_AMT = 1;
    private static final int UPGRADE_REGEN_AMT = 1;

    private static final int DEX_LOSS = -3;
    private static final int UPGRADE_DEX_LOSS = -2;

    public TearsOfSunlight() {
        super(ID, COST, TYPE, RARITY, TARGET, COLOR);
        baseMagicNumber = REGEN_AMT;
        magicNumber = REGEN_AMT;
        tags.add(CardTags.HEALING);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new TearsOfSunlightPower(p, this.magicNumber)));
        if (upgraded) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DexterityPower(p, UPGRADE_DEX_LOSS), UPGRADE_DEX_LOSS));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DexterityPower(p, DEX_LOSS), DEX_LOSS));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_REGEN_AMT);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
package forsaken.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import forsaken.TheForsakenMod;
import forsaken.util.ActionUnit;

@SuppressWarnings("unused")
public class AbstractForsakenPower extends AbstractPower implements ActionUnit {
    protected PowerStrings powerStrings;
    protected String[] DESCRIPTIONS;
    private final TextureAtlas powerAtlas = TheForsakenMod.assets.loadAtlas(TheForsakenMod.assetPath("images/powers/powers.atlas"));

    public AbstractForsakenPower(final String ID, final AbstractCreature owner) {
        this.ID = ID;
        this.owner = owner;
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID);
        name = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }

    public AbstractForsakenPower(final String ID, final AbstractCreature owner, final int amount) {
        this(ID, owner);
        this.amount = amount;
    }

    @Override
    protected void loadRegion(String fileName) {
        region48 = powerAtlas.findRegion("32/" + fileName);
        region128 = powerAtlas.findRegion("128/" + fileName);

        if (region48 == null && region128 == null) {
            super.loadRegion(fileName);
        }
    }

    @Override
    public DamageInfo makeDamageInfo(int amount, DamageInfo.DamageType type) {
        return new DamageInfo(owner, amount, type);
    }

    @Override
    public void dealDamage(AbstractMonster m, AbstractGameAction.AttackEffect fx) {
        qAction(new DamageAction(m, makeDamageInfo(amount, DamageInfo.DamageType.THORNS)));
    }

    @Override
    public void dealAoeDamage(AbstractGameAction.AttackEffect fx) {
        qAction(new DamageAllEnemiesAction(owner, DamageInfo.createDamageMatrix(amount, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.NONE, true));
    }

    @Override
    public void gainBlock() {
        qAction(new GainBlockAction(owner, amount, true));
    }
}

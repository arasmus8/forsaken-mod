package theForsaken.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theForsaken.TheForsakenMod;
import theForsaken.util.TextureLoader;

import static theForsaken.TheForsakenMod.makePowerPath;


//Double damage next turn when block is broken.

public class RipostePower extends AbstractPower implements CloneablePowerInterface {
    private static final String POWER_ID = TheForsakenMod.makeID(RipostePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("riposte84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("riposte32.png"));

    private boolean hasBlock = false;

    public RipostePower(final AbstractCreature owner) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = -1;

        type = PowerType.BUFF;

        if (owner.currentBlock > 0) {
            hasBlock = true;
        }

        // We load those textures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void onGainedBlock(float blockAmount) {
        if (MathUtils.floor(blockAmount) > 0) {
            hasBlock = true;
        }
    }

    @Override
    public void atStartOfTurnPostDraw() {
        int block = owner.currentBlock;
        if (block < 1) {
            hasBlock = false;
        }
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        // return super.onAttacked(info, damageAmount);
        if (!(info.type == DamageType.THORNS) && !(info.type == DamageType.HP_LOSS) && info.output > 0 && hasBlock) {
            int block = owner.currentBlock;
            if (block <= 0) {
                this.flash();
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner, new PercentageBonusDamagePower(owner, 1), 1));
                hasBlock = false;
            }
        }
        return damageAmount;
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new RipostePower(owner);
    }
}

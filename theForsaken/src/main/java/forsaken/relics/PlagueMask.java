package forsaken.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.OnReceivePowerRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import forsaken.TheForsakenMod;
import forsaken.actions.FunctionalAction;
import forsaken.util.TextureLoader;

import static forsaken.TheForsakenMod.relicOutlineResourcePath;
import static forsaken.TheForsakenMod.relicResourcePath;


public class PlagueMask extends CustomRelic implements OnReceivePowerRelic {

    public static final String ID = TheForsakenMod.makeID(PlagueMask.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(relicResourcePath("PlagueMask.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(relicOutlineResourcePath("PlagueMask.png"));

    public PlagueMask() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.FLAT);
    }


    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public boolean onReceivePower(AbstractPower abstractPower, AbstractCreature abstractCreature) {
        return true;
    }

    @Override
    public int onReceivePowerStacks(AbstractPower power, AbstractCreature source, int stackAmount) {
        if (power.ID.equals(PoisonPower.POWER_ID) && stackAmount > 0) {
            // Also apply poison to a random enemy
            addToTop(new FunctionalAction(firstUpdate -> {
                AbstractMonster target = AbstractDungeon.getRandomMonster();
                if (target != null) {
                    addToTop(new ApplyPowerAction(target, source, new PoisonPower(target, source, stackAmount), stackAmount));
                }
                return true;
            }));
        }
        return stackAmount;
    }
}
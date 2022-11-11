package theForsaken.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theForsaken.TheForsakenMod;

public class AbstractForsakenPower extends AbstractPower {
    private final TextureAtlas powerAtlas = TheForsakenMod.assets.loadAtlas(TheForsakenMod.assetPath("images/powers/powers.atlas"));

    @Override
    protected void loadRegion(String fileName) {
        region48 = powerAtlas.findRegion("32/" + fileName);
        region128 = powerAtlas.findRegion("128/" + fileName);

        if (region48 == null && region128 == null) {
            super.loadRegion(fileName);
        }
    }
}

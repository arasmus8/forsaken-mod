package forsaken.characters;

import basemod.abstracts.CustomEnergyOrb;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;
import forsaken.TheForsakenMod;
import forsaken.util.TextureHelper;

import java.util.HashMap;
import java.util.Map;

public class ForsakenEnergyOrb extends CustomEnergyOrb {
    private static final float ORB_IMG_SCALE = 1.45F * Settings.scale;
    private static final float PULSE_SPEED = 0.6f;
    private static final float RUNE_FADE_DURATION = 0.5f;
    private static final float RUNE_PULSE_SPEED = 1.2f;

    private int runeCount = 0;

    private final Map<String, Texture> textures;
    private float tick = 0f;
    private final float[] runefade;

    public ForsakenEnergyOrb() {
        super(null, null, null);
        runefade = new float[]{0f, 0f, 0f, 0f, 0f};
        textures = new HashMap<>();
        textures.put("PitOuterRing", TextureHelper.getTexture(TheForsakenMod.imageResourcePath("char/forsaken/orb/EnergyOrbPitOuterRing.png")));
        textures.put("PitInnerRing", TextureHelper.getTexture(TheForsakenMod.imageResourcePath("char/forsaken/orb/EnergyOrbPitInnerRing.png")));
        textures.put("RuneFrame", TextureHelper.getTexture(TheForsakenMod.imageResourcePath("char/forsaken/orb/EnergyOrbRuneFrame.png")));
        textures.put("RightRune", TextureHelper.getTexture(TheForsakenMod.imageResourcePath("char/forsaken/orb/EnergyOrbRightRune.png")));
        textures.put("MiddleRune", TextureHelper.getTexture(TheForsakenMod.imageResourcePath("char/forsaken/orb/EnergyOrbMiddleRune.png")));
        textures.put("LeftRune", TextureHelper.getTexture(TheForsakenMod.imageResourcePath("char/forsaken/orb/EnergyOrbLeftRune.png")));
        textures.put("FireGlow", TextureHelper.getTexture(TheForsakenMod.imageResourcePath("char/forsaken/orb/EnergyOrbFireGlow.png")));
        textures.put("Ember", TextureHelper.getTexture(TheForsakenMod.imageResourcePath("char/forsaken/orb/EnergyOrbEmber.png")));
        textures.put("EmberNoEnergy", TextureHelper.getTexture(TheForsakenMod.imageResourcePath("char/forsaken/orb/EnergyOrbEmberNoEnergy.png")));
    }

    public void resetRuneCount() {
        setRuneCount(0, false);
    }

    public void setRuneCount(int c, boolean shouldPulse) {
        if (runeCount > 0 && c == 0) {
            runefade[4] = RUNE_FADE_DURATION;
        }
        if (runeCount == 0 && c > 0) {
            runefade[1] = RUNE_FADE_DURATION; // fade in the first rune
        }
        if (runeCount == 1 && c > 1) {
            runefade[2] = RUNE_FADE_DURATION; // fade in the second rune
        }
        if (runeCount == 2 && c > 2) {
            runefade[3] = RUNE_FADE_DURATION; // fade in the third rune
        }
        if (shouldPulse) {
            // pulse the runes when quickdraw is about to trigger
            runefade[0] = 1f;
        } else {
            runefade[0] = -1f;
        }
        runeCount = c;
    }

    @Override
    public void updateOrb(int energyCount) {
        float delta = Gdx.graphics.getDeltaTime();
        tick += delta;
        if (runefade[0] >= 0f) {
            runefade[0] = (float) (Math.cos(tick * Math.PI * RUNE_PULSE_SPEED) * 0.2f + 0.8f);
        }
        for (int i = 1; i < runefade.length; i++) {
            runefade[i] = Math.max(0f, runefade[i] - delta);
        }
    }

    @Override
    public void renderOrb(SpriteBatch sb, boolean enabled, float x, float y) {
        float alpha;
        sb.setColor(Color.WHITE);
        TextureHelper.drawScaled(sb, textures.get("PitOuterRing"), x, y, ORB_IMG_SCALE);
        TextureHelper.drawScaled(sb, textures.get("RuneFrame"), x, y, ORB_IMG_SCALE);
        TextureHelper.drawScaled(sb, textures.get("EmberNoEnergy"), x, y, ORB_IMG_SCALE);
        if (enabled) {
            alpha = (float) (Math.sin(tick * Math.PI * PULSE_SPEED) * 0.2f + 0.8f);
            sb.setColor(1f, 1f, 1f, alpha);
            TextureHelper.drawScaled(sb, textures.get("Ember"), x, y, ORB_IMG_SCALE);
            sb.setColor(Color.WHITE);
            TextureHelper.drawScaled(sb, textures.get("PitInnerRing"), x, y, ORB_IMG_SCALE);
            sb.setColor(1f, 1f, 1f, alpha);
            TextureHelper.drawScaled(sb, textures.get("FireGlow"), x, y, ORB_IMG_SCALE);
            sb.setColor(Color.WHITE);
        } else {
            TextureHelper.drawScaled(sb, textures.get("PitInnerRing"), x, y, ORB_IMG_SCALE);
        }
        float runepulsefade = runefade[0];
        if (runepulsefade < 0f) {
            runepulsefade = 1f;
        }
        float runeresetfade = 1 - Interpolation.exp5In.apply(1 - runefade[4] / RUNE_FADE_DURATION);
        switch(runeCount) {
            case 3:
                alpha = Interpolation.exp5Out.apply(1 - runefade[3] / RUNE_FADE_DURATION) * runepulsefade;
                alpha = Math.max(alpha, runeresetfade);
                sb.setColor(1f, 1f, 1f, alpha);
                TextureHelper.drawScaled(sb, textures.get("RightRune"), x, y, ORB_IMG_SCALE);
                //fallthrough
            case 2:
                alpha = Interpolation.exp5Out.apply(1 - runefade[2] / RUNE_FADE_DURATION) * runepulsefade;
                alpha = Math.max(alpha, runeresetfade);
                sb.setColor(1f, 1f, 1f, alpha);
                TextureHelper.drawScaled(sb, textures.get("MiddleRune"), x, y, ORB_IMG_SCALE);
                //fallthrough
            case 1:
                alpha = Interpolation.exp5Out.apply(1 - runefade[1] / RUNE_FADE_DURATION) * runepulsefade;
                alpha = Math.max(alpha, runeresetfade);
                sb.setColor(1f, 1f, 1f, alpha);
                TextureHelper.drawScaled(sb, textures.get("LeftRune"), x, y, ORB_IMG_SCALE);
        }
        sb.setColor(Color.WHITE);
    }

}

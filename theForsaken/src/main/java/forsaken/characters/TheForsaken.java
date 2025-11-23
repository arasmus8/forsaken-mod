package forsaken.characters;

import basemod.abstracts.CustomPlayer;
import basemod.animations.SpriterAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import forsaken.cards.Defend;
import forsaken.cards.Strike;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import forsaken.TheForsakenMod;
import forsaken.oldCards.Penitence;
import forsaken.oldCards.Smite;
import forsaken.oldCards.TheForsaken_Defend;
import forsaken.oldCards.TheForsaken_Strike;
import forsaken.relics.JudgementScales;

import java.util.ArrayList;
import java.util.List;

import static forsaken.TheForsakenMod.imageResourcePath;
import static forsaken.TheForsakenMod.makeID;
import static forsaken.characters.TheForsaken.Enums.COLOR_GOLD;

public class TheForsaken extends CustomPlayer {
    public static final Logger logger = LogManager.getLogger(TheForsaken.class.getName());

    public static class Enums {
        @SpireEnum
        public static AbstractPlayer.PlayerClass THE_FORSAKEN;
        @SpireEnum(name = "FORSAKEN_GOLD_COLOR") // These two HAVE to have the same absolutely identical name.
        public static AbstractCard.CardColor COLOR_GOLD;
        @SpireEnum(name = "FORSAKEN_GOLD_COLOR")
        @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
    }

    public static final int ENERGY_PER_TURN = 3;
    public static final int STARTING_HP = 65;
    public static final int MAX_HP = 65;
    public static final int STARTING_GOLD = 99;
    public static final int CARD_DRAW = 5;
    public static final int ORB_SLOTS = 0;

    private static final String ID = makeID("TheForsaken");
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    private static final String[] NAMES = characterStrings.NAMES;
    private static final String[] TEXT = characterStrings.TEXT;

    // TODO: make a bonfire for energy instead
    public static final String[] orbTextures = {
            "forsakenResources/images/char/forsaken/orb/layer1.png",
            "forsakenResources/images/char/forsaken/orb/layer2.png",
            "forsakenResources/images/char/forsaken/orb/layer3.png",
            "forsakenResources/images/char/forsaken/orb/layer4.png",
            "forsakenResources/images/char/forsaken/orb/layer5.png",
            "forsakenResources/images/char/forsaken/orb/layer6.png",
            "forsakenResources/images/char/forsaken/orb/layer1d.png",
            "forsakenResources/images/char/forsaken/orb/layer2d.png",
            "forsakenResources/images/char/forsaken/orb/layer3d.png",
            "forsakenResources/images/char/forsaken/orb/layer4d.png",
            "forsakenResources/images/char/forsaken/orb/layer5d.png",};

    public TheForsaken(String name, PlayerClass setClass) {
        super(name, setClass, orbTextures,
                "forsakenResources/images/char/forsaken/orb/vfx.png", null,
                new SpriterAnimation(
                        "forsakenResources/images/char/forsaken/Spriter/forsaken.scml"));


        // =============== TEXTURES, ENERGY, LOADOUT =================  

        initializeClass(null, // required call to load textures and setup energy/loadout.
                imageResourcePath("char/forsaken/shoulder.png"),
                imageResourcePath("char/forsaken/shoulder2.png"),
                imageResourcePath("char/forsaken/corpse.png"),
                getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(ENERGY_PER_TURN)); // energy manager

        // =============== /TEXTURES, ENERGY, LOADOUT/ =================


        // =============== ANIMATIONS =================  

        loadAnimation(
                imageResourcePath("char/forsaken/skeleton.atlas"),
                imageResourcePath("char/forsaken/skeleton.json"),
                1.0f);
        AnimationState.TrackEntry e = state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());

        // =============== /ANIMATIONS/ =================


        // =============== TEXT BUBBLE LOCATION =================

        dialogX = (drawX + 0.0F * Settings.scale); // set location for text bubbles
        dialogY = (drawY + 220.0F * Settings.scale); // you can just copy these values

        // =============== /TEXT BUBBLE LOCATION/ =================

    }

    // =============== /CHARACTER CLASS END/ =================

    // Starting description and loadout
    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0],
                STARTING_HP, MAX_HP, ORB_SLOTS, STARTING_GOLD, CARD_DRAW, this, getStartingRelics(),
                getStartingDeck(), false);
    }

    // Starting Deck
    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();

        if (TheForsakenMod.enableOldCardList) {
            retVal.add(TheForsaken_Strike.ID);
            retVal.add(TheForsaken_Strike.ID);
            retVal.add(TheForsaken_Strike.ID);
            retVal.add(TheForsaken_Strike.ID);

            retVal.add(TheForsaken_Defend.ID);
            retVal.add(TheForsaken_Defend.ID);
            retVal.add(TheForsaken_Defend.ID);
            retVal.add(TheForsaken_Defend.ID);

            retVal.add(Smite.ID);
            retVal.add(Penitence.ID);
            return retVal;
        }

        // TODO new starting cards
        retVal.add(Strike.ID);
        retVal.add(Strike.ID);
        retVal.add(Strike.ID);
        retVal.add(Strike.ID);
        retVal.add(Defend.ID);
        retVal.add(Defend.ID);
        retVal.add(Defend.ID);
        retVal.add(Defend.ID);
        retVal.add(forsaken.cards.Smite.ID);
        retVal.add(forsaken.cards.Penitence.ID);
        return retVal;
    }

    // Starting Relics	
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();

        retVal.add(JudgementScales.ID);

        UnlockTracker.markRelicAsSeen(JudgementScales.ID);

        return retVal;
    }

    // character Select screen effect
    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("BUFF_3", 1.25f); // Sound Effect
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT,
                false); // Screen Effect
    }

    // character Select on-button-press sound effect
    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "BUFF_3";
    }

    // Should return how much HP your maximum HP reduces by when starting a run at
    // Ascension 14 or higher. (ironclad loses 5, defect and silent lose 4 hp respectively)
    @Override
    public int getAscensionMaxHPLoss() {
        return 4;
    }

    // Should return the card color enum to be associated with your character.
    @Override
    public AbstractCard.CardColor getCardColor() {
        return COLOR_GOLD;
    }

    // Should return a color object to be used to color the trail of moving cards
    @Override
    public Color getCardTrailColor() {
        return TheForsakenMod.FORSAKEN_GOLD;
    }

    // Should return a BitmapFont object that you can use to customize how your
    // energy is displayed from within the energy orb.
    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    // Should return class name as it appears in run history screen.
    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }

    //Which card should be obtainable from the Match and Keep event?
    @Override
    public AbstractCard getStartCardForEvent() {
        return new Smite();
    }

    // The class name as it appears next to your player name in-game
    @Override
    public String getTitle(AbstractPlayer.PlayerClass playerClass) {
        return NAMES[1];
    }

    // Should return a new instance of your character, sending name as its name parameter.
    @Override
    public AbstractPlayer newInstance() {
        return new TheForsaken(name, chosenClass);
    }

    // Should return a Color object to be used to color the miniature card images in run history.
    @Override
    public Color getCardRenderColor() {
        return TheForsakenMod.FORSAKEN_GOLD;
    }

    // Should return a Color object to be used as screen tint effect when your
    // character attacks the heart.
    @Override
    public Color getSlashAttackColor() {
        return TheForsakenMod.FORSAKEN_GOLD;
    }

    // Should return an AttackEffect array of any size greater than 0. These effects
    // will be played in sequence as your character's finishing combo on the heart.
    // Attack effects are the same as used in DamageAction and the like.
    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY};
    }

    // Should return a string containing what text is shown when your character is
    // about to attack the heart. For example, the defect is "NL You charge your
    // core to its maximum..."
    @Override
    public String getSpireHeartText() {
        return TEXT[1];
    }

    @Override
    public Texture getCutsceneBg() {
        return ImageMaster.loadImage("forsakenResources/images/goldbg.jpg");// 307
    }

    @Override
    public List<CutscenePanel> getCutscenePanels() {
        List<CutscenePanel> panels = new ArrayList();// 312
        panels.add(new CutscenePanel("forsakenResources/images/heart1.png", "ATTACK_HEAVY"));// 313
        panels.add(new CutscenePanel("forsakenResources/images/heart2.png"));// 314
        panels.add(new CutscenePanel("forsakenResources/images/heart3.png"));// 315
        return panels;// 316
    }

    // The vampire events refer to the base game characters as "brother", "sister",
    // and "broken one" respectively.This method should return a String containing
    // the full text that will be displayed as the first screen of the vampires event.
    @Override
    public String getVampireText() {
        return TEXT[2];
    }

    @Override
    public void damage(DamageInfo info) {
        if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.output - this.currentBlock > 0) {
            AnimationState.TrackEntry e = this.state.setAnimation(0, "Hit", false);
            this.state.addAnimation(0, "Idle", true, 0.4F);
            e.setTimeScale(0.6F);
        }

        super.damage(info);
    }

}

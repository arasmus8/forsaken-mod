package theForsaken;

import basemod.*;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theForsaken.cards.AbstractForsakenCard;
import theForsaken.characters.TheForsaken;
import theForsaken.events.PlagueDoctorEvent;
import theForsaken.potions.FearPotion;
import theForsaken.powers.HymnOfRestPower;
import theForsaken.relics.*;
import theForsaken.util.AssetLoader;
import theForsaken.util.TextureLoader;
import theForsaken.variables.SacrificeSoulVariable;
import theForsaken.variables.UnplayedCardsVariable;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Properties;
import java.util.UUID;

@SuppressWarnings("unused")
@SpireInitializer
public class TheForsakenMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        OnCardUseSubscriber,
        OnPlayerLoseBlockSubscriber,
        OnStartBattleSubscriber,
        PostInitializeSubscriber,
        PostDrawSubscriber,
        PreStartGameSubscriber {

    private static final Logger logger = LogManager.getLogger(TheForsakenMod.class.getName());

    private static final Properties modSettings = new Properties();
    private static final String ALL_RELICS_ARE_CHAR_SPECIFIC = "allRelicsCharSpecific";
    private static final String ENABLE_OLD_CARD_LIST = "enableOldCardList";
    private static boolean allRelicsCharSpecific = true;
    private static boolean enableOldCardlist = false;

    private static final String MODNAME = "The Forsaken";
    private static final String AUTHOR = "NotInTheFace";
    private static final String DESCRIPTION = ""; // TODO write a description

    public static final Color FORSAKEN_GOLD = CardHelper.getColor(227, 203, 43);

    public static final String modId = "TheForsaken";

    public static String assetPath(String path) {
        return "forsakenAssets/" + path;
    }

    public static AssetLoader assets = new AssetLoader();

    // =============== MAKE IMAGE PATHS =================

    public static String resourcePath(String path) {
        return modId + "Resources/" + path;
    }

    public static String imageResourcePath(String path) {
        return modId + "Resources/images/" + path;
    }

    public static String cardResourcePath(String resourcePath) {
        return modId + "Resources/images/cards/" + resourcePath;
    }

    public static String relicResourcePath(String resourcePath) {
        return modId + "Resources/images/relics/" + resourcePath;
    }

    public static String relicOutlineResourcePath(String resourcePath) {
        return modId + "Resources/images/relics/outline/" + resourcePath;
    }

    public static String powerResourcePath(String resourcePath) {
        return modId + "Resources/images/powers/" + resourcePath;
    }

    public static String eventResourcePath(String resourcePath) {
        return modId + "Resources/images/events/" + resourcePath;
    }

    public TheForsakenMod() {
        logger.info("Subscribe to BaseMod hooks");

        BaseMod.subscribe(this);

        logger.info("Done subscribing");

        logger.info("Creating the color " + TheForsaken.Enums.COLOR_GOLD.toString());

        BaseMod.addColor(TheForsaken.Enums.COLOR_GOLD, FORSAKEN_GOLD, FORSAKEN_GOLD, FORSAKEN_GOLD,
                FORSAKEN_GOLD, FORSAKEN_GOLD, FORSAKEN_GOLD, FORSAKEN_GOLD,
                imageResourcePath("512/bg_attack.png"),
                imageResourcePath("512/bg_skill.png"),
                imageResourcePath("512/bg_power.png"),
                imageResourcePath("512/card_orb.png"),
                imageResourcePath("1024/bg_attack.png"),
                imageResourcePath("1024/bg_skill.png"),
                imageResourcePath("1024/bg_power.png"),
                imageResourcePath("1024/card_orb.png"),
                imageResourcePath("512/card_small_orb.png"));

        logger.info("Done creating the color");


        logger.info("Adding mod settings");
        // This loads the mod settings.
        // The actual mod Button is added below in receivePostInitialize()
        modSettings.setProperty(ALL_RELICS_ARE_CHAR_SPECIFIC, "TRUE");
        modSettings.setProperty(ENABLE_OLD_CARD_LIST, "FALSE");
        try {
            SpireConfig config = new SpireConfig("forsakenMod", "theForsakenConfig", modSettings);
            config.load();
            allRelicsCharSpecific = config.getBool(ALL_RELICS_ARE_CHAR_SPECIFIC);
            enableOldCardlist = config.getBool(ENABLE_OLD_CARD_LIST);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("Done adding mod settings");

    }

    public static void initialize() {
        TheForsakenMod forsakenMod = new TheForsakenMod();
    }

    @Override
    public void receiveEditCharacters() {
        logger.info("Beginning to edit characters. " + "Add " + TheForsaken.Enums.THE_FORSAKEN.toString());

        BaseMod.addCharacter(new TheForsaken("the Original Forsaken", TheForsaken.Enums.THE_FORSAKEN),
                imageResourcePath("charSelect/ForsakenButton.png"),
                imageResourcePath("charSelect/ForsakenPortraitBG.png"),
                TheForsaken.Enums.THE_FORSAKEN);

        receiveEditPotions();
        logger.info("Added " + TheForsaken.Enums.THE_FORSAKEN.toString());
    }

    @Override
    public void receivePostInitialize() {
        logger.info("Loading badge image and mod options");

        // Load the Mod Badge
        Texture badgeTexture = TextureLoader.getTexture(imageResourcePath("Badge.png"));

        // Create the Mod Menu
        ModPanel settingsPanel = new ModPanel();

        // Create the on/off button:
        ModLabeledToggleButton enableNormalsButton = new ModLabeledToggleButton("All this mods relics are for this character only. (Requires restart)",
                350.0f, 700.0f, Settings.CREAM_COLOR, FontHelper.charDescFont,
                allRelicsCharSpecific,
                settingsPanel,
                (label) -> {
                },
                (button) -> {
                    allRelicsCharSpecific = button.enabled;
                    try {
                        SpireConfig config = new SpireConfig("forsakenMod", "theForsakenConfig", modSettings);
                        config.setBool(ALL_RELICS_ARE_CHAR_SPECIFIC, allRelicsCharSpecific);
                        config.save();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

        settingsPanel.addUIElement(enableNormalsButton);

        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);


        BaseMod.addEvent(PlagueDoctorEvent.ID, PlagueDoctorEvent.class);
        logger.info("Done loading badge Image and mod options");
    }

    private void receiveEditPotions() {
        BaseMod.addPotion(
                FearPotion.class,
                CardHelper.getColor(209, 53, 18),
                CardHelper.getColor(255, 230, 230),
                CardHelper.getColor(100, 25, 10),
                FearPotion.POTION_ID,
                TheForsaken.Enums.THE_FORSAKEN
        );
    }

    @Override
    public void receiveEditRelics() {
        logger.info("Adding relics");

        // This adds a character specific relic. Only when you play with the mentioned color, will you get this relic.
        BaseMod.addRelicToCustomPool(new ArmorOfThorns(), TheForsaken.Enums.COLOR_GOLD);
        BaseMod.addRelicToCustomPool(new JudgementScales(), TheForsaken.Enums.COLOR_GOLD);
        BaseMod.addRelicToCustomPool(new PlagueMask(), TheForsaken.Enums.COLOR_GOLD);
        BaseMod.addRelicToCustomPool(new ScalesOfTruth(), TheForsaken.Enums.COLOR_GOLD);

        if (allRelicsCharSpecific) {
            BaseMod.addRelicToCustomPool(new Gavel(), TheForsaken.Enums.COLOR_GOLD);
            BaseMod.addRelicToCustomPool(new HumbleEgg(), TheForsaken.Enums.COLOR_GOLD);
            BaseMod.addRelicToCustomPool(new Kindling(), TheForsaken.Enums.COLOR_GOLD);
            BaseMod.addRelicToCustomPool(new Lifeblossom(), TheForsaken.Enums.COLOR_GOLD);
            BaseMod.addRelicToCustomPool(new ScaryMask(), TheForsaken.Enums.COLOR_GOLD);
            BaseMod.addRelicToCustomPool(new PaleLantern(), TheForsaken.Enums.COLOR_GOLD);
        } else {
            // This adds a relic to the Shared pool. Every character can find this relic.
            BaseMod.addRelic(new Gavel(), RelicType.SHARED);
            BaseMod.addRelic(new HumbleEgg(), RelicType.SHARED);
            BaseMod.addRelic(new Kindling(), RelicType.SHARED);
            BaseMod.addRelic(new Lifeblossom(), RelicType.SHARED);
            BaseMod.addRelic(new ScaryMask(), RelicType.SHARED);
            BaseMod.addRelic(new PaleLantern(), RelicType.SHARED);
        }

        // Mark relics as seen (the others are all starters, so they're marked as seen in the character file
        UnlockTracker.markRelicAsSeen(ArmorOfThorns.ID);
        UnlockTracker.markRelicAsSeen(JudgementScales.ID);
        UnlockTracker.markRelicAsSeen(PlagueMask.ID);
        UnlockTracker.markRelicAsSeen(ScalesOfTruth.ID);
        UnlockTracker.markRelicAsSeen(Gavel.ID);
        UnlockTracker.markRelicAsSeen(HumbleEgg.ID);
        UnlockTracker.markRelicAsSeen(Kindling.ID);
        UnlockTracker.markRelicAsSeen(Lifeblossom.ID);
        UnlockTracker.markRelicAsSeen(PaleLantern.ID);
        UnlockTracker.markRelicAsSeen(ScaryMask.ID);
        logger.info("Done adding relics!");
    }

    @SuppressWarnings("LibGDXUnsafeIterator")
    @Override
    public void receiveEditCards() {
        TextureAtlas cardAtlas = ReflectionHacks.getPrivateStatic(AbstractCard.class, "cardAtlas");

        TextureAtlas myCardAtlas = assets.loadAtlas(assetPath("images/cards/cards.atlas"));
        for (TextureAtlas.AtlasRegion region : myCardAtlas.getRegions()) {
            cardAtlas.addRegion(modId + "/" + region.name, region);
        }

        logger.info("Adding variables");
        // Add the Custom Dynamic Variables
        logger.info("Add variables");
        // Add the Custom Dynamic variables
        BaseMod.addDynamicVariable(new SacrificeSoulVariable());
        BaseMod.addDynamicVariable(new UnplayedCardsVariable());

        logger.info("Adding cards");
        new AutoAdd(modId)
                .packageFilter(AbstractForsakenCard.class)
                .setDefaultSeen(true)
                .cards();
    }

    @Override
    public void receiveEditStrings() {
        logger.info("You seeing this?");
        logger.info("Beginning to edit strings for mod with ID: " + modId);

        String lang = "eng";

        if (Settings.language == Settings.GameLanguage.ZHS) {
            lang = "zhs";
        }

        logger.info("Loading strings for language: " + lang);

        // CardStrings
        BaseMod.loadCustomStringsFile(CardStrings.class,
                modId + "Resources/localization/" + lang + "/TheForsakenMod-Card-Strings.json");

        // PowerStrings
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                modId + "Resources/localization/" + lang + "/TheForsakenMod-Power-Strings.json");

        // RelicStrings
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                modId + "Resources/localization/" + lang + "/TheForsakenMod-Relic-Strings.json");

        // Event Strings
        BaseMod.loadCustomStringsFile(EventStrings.class,
                modId + "Resources/localization/" + lang + "/TheForsakenMod-Event-Strings.json");

        // PotionStrings
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                modId + "Resources/localization/" + lang + "/TheForsakenMod-Potion-Strings.json");

        // CharacterStrings
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                modId + "Resources/localization/" + lang + "/TheForsakenMod-Character-Strings.json");

        // UIStrings
        BaseMod.loadCustomStringsFile(UIStrings.class,
                modId + "Resources/localization/" + lang + "/TheForsakenMod-Ui-Strings.json");

        logger.info("Done editing strings");
    }

    // ================ /LOAD THE TEXT/ ===================

    // ================ LOAD THE KEYWORDS ===================

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String lang = "eng";

        if (Settings.language == Settings.GameLanguage.ZHS) {
            lang = "zhs";
        }

        String json = Gdx.files.internal(modId + "Resources/localization/" + lang + "/TheForsakenMod-Keyword-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(modId.toLowerCase(), keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }

    // ================ /LOAD THE KEYWORDS/ ===================

    @Override
    public void receivePreStartGame() {
    }

    // this adds "ModName:" before the ID of any card/relic/power etc.
    // in order to avoid conflicts if any other mod uses the same ID.
    public static String makeID(String idText) {
        return modId + ":" + idText;
    }

    public static ArrayList<UUID> usedCards = new ArrayList<>();

    @Override
    public void receiveCardUsed(AbstractCard abstractCard) {
        UUID uuid = abstractCard.uuid;
        if (!usedCards.contains(uuid)) {
            usedCards.add(uuid);
        }
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        usedCards.clear();
    }

    @Override
    public void receivePostDraw(AbstractCard abstractCard) {
        UUID uuid = abstractCard.uuid;
        usedCards.remove(uuid);
    }

    @Override
    public int receiveOnPlayerLoseBlock(int i) {
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasPower(HymnOfRestPower.POWER_ID)) {
            HymnOfRestPower p = (HymnOfRestPower) AbstractDungeon.player.getPower(HymnOfRestPower.POWER_ID);
            return p.modifiedBlock(i);
        }
        return i;
    }
}

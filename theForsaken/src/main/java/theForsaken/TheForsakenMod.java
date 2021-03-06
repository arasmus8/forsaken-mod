package theForsaken;

import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
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
import theForsaken.cards.PlagueCurse;
import theForsaken.cards.Recompense;
import theForsaken.characters.TheForsaken;
import theForsaken.events.PlagueDoctorEvent;
import theForsaken.potions.FearPotion;
import theForsaken.powers.HymnOfRestPower;
import theForsaken.relics.*;
import theForsaken.util.IDCheckDontTouchPls;
import theForsaken.util.TextureLoader;
import theForsaken.variables.DefaultSecondMagicNumber;
import theForsaken.variables.SacrificeSoulVariable;
import theForsaken.variables.UnplayedCardsVariable;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Properties;
import java.util.UUID;

// Please don't just mass replace "theDefault" with "yourMod" everywhere.
// It'll be a bigger pain for you. You only need to replace it in 3 places.
// I comment those places below, under the place where you set your ID.

// FIRST THINGS FIRST: RENAME YOUR PACKAGE AND ID NAMES FIRST-THING!!!
// Right click the package (Open the project pane on the left. Folder with black dot on it. The name's at the very top) -> Refactor -> Rename, and name it whatever you wanna call your mod.
// Scroll down in this file. Change the ID from "theDefault:" to "yourModName:" or whatever your heart desires (don't use spaces). Dw, you'll see it.
// In the JSON strings (resources>localization>eng>[all them files] make sure they all go "yourModName:" rather than "theDefault". You can ctrl+R to replace in 1 file, or ctrl+shift+r to mass replace in specific files/directories (Be careful.).
// Start with the DefaultCommon cards - they are the most commented cards since I don't feel it's necessary to put identical comments on every card.
// After you sorta get the hang of how to make cards, check out the card template which will make your life easier

/*
 * With that out of the way:
 * Welcome to this super over-commented Slay the Spire modding base.
 * Use it to make your own mod of any type. - If you want to add any standard in-game content (character,
 * cards, relics), this is a good starting point.
 * It features 1 character with a minimal set of things: 1 card of each type, 1 debuff, couple of relics, etc.
 * If you're new to modding, you basically *need* the BaseMod wiki for whatever you wish to add
 * https://github.com/daviscook477/BaseMod/wiki - work your way through with this base.
 * Feel free to use this in any way you like, of course. MIT licence applies. Happy modding!
 *
 * And pls. Read the comments.
 */

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
    // Make sure to implement the subscribers *you* are using (read basemod wiki). Editing cards? EditCardsSubscriber.
    // Making relics? EditRelicsSubscriber. etc., etc., for a full list and how to make your own, visit the basemod wiki.
    private static final Logger logger = LogManager.getLogger(TheForsakenMod.class.getName());
    private static String modID;

    // Mod-settings settings. This is if you want an on/off savable button
    private static Properties theForsakenDefaultSettings = new Properties();
    private static final String ENABLE_PLACEHOLDER_SETTINGS = "enablePlaceholder";
    private static boolean enablePlaceholder = true; // The boolean we'll be setting on/off (true/false)

    //This is for the in-game mod settings panel.
    private static final String MODNAME = "The Forsaken";
    private static final String AUTHOR = "NotInTheFace";
    private static final String DESCRIPTION = "A custom character centered around self buffs.";

    // =============== INPUT TEXTURE LOCATION =================

    // Colors (RGB)
    // Character Color
    public static final Color FORSAKEN_GOLD = CardHelper.getColor(227, 203, 43);

    // Potion Colors in RGB
    private static final Color PLACEHOLDER_POTION_LIQUID = CardHelper.getColor(209, 53, 18); // Orange-ish Red
    private static final Color PLACEHOLDER_POTION_HYBRID = CardHelper.getColor(255, 230, 230); // Near White
    private static final Color PLACEHOLDER_POTION_SPOTS = CardHelper.getColor(100, 25, 10); // Super Dark Red/Brown

    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!

    // Card backgrounds - The actual rectangular card.
    private static final String ATTACK_FORSAKEN_GOLD = "theForsakenResources/images/512/bg_attack.png";
    private static final String SKILL_FORSAKEN_GOLD = "theForsakenResources/images/512/bg_skill.png";
    private static final String POWER_FORSAKEN_GOLD = "theForsakenResources/images/512/bg_power.png";

    private static final String ENERGY_ORB_FORSAKEN_GOLD = "theForsakenResources/images/512/card_orb.png";
    private static final String CARD_ENERGY_ORB = "theForsakenResources/images/512/card_small_orb.png";

    private static final String ATTACK_FORSAKEN_GOLD_PORTRAIT = "theForsakenResources/images/1024/bg_attack.png";
    private static final String SKILL_FORSAKEN_GOLD_PORTRAIT = "theForsakenResources/images/1024/bg_skill.png";
    private static final String POWER_FORSAKEN_GOLD_PORTRAIT = "theForsakenResources/images/1024/bg_power.png";
    private static final String ENERGY_ORB_FORSAKEN_GOLD_PORTRAIT = "theForsakenResources/images/1024/card_orb.png";

    // Character assets
    private static final String THE_DEFAULT_BUTTON = "theForsakenResources/images/charSelect/ForsakenButton.png";
    private static final String THE_DEFAULT_PORTRAIT = "theForsakenResources/images/charSelect/ForsakenPortraitBG.png";
    public static final String THE_DEFAULT_SHOULDER_1 = "theForsakenResources/images/char/forsaken/shoulder.png";
    public static final String THE_DEFAULT_SHOULDER_2 = "theForsakenResources/images/char/forsaken/shoulder2.png";
    public static final String THE_DEFAULT_CORPSE = "theForsakenResources/images/char/forsaken/corpse.png";

    //Mod Badge - A small icon that appears in the mod settings menu next to your mod.
    private static final String BADGE_IMAGE = "theForsakenResources/images/Badge.png";

    // Atlas and JSON files for the Animations
    public static final String THE_DEFAULT_SKELETON_ATLAS = "theForsakenResources/images/char/forsaken/skeleton.atlas";
    public static final String THE_DEFAULT_SKELETON_JSON = "theForsakenResources/images/char/forsaken/skeleton.json";

    // =============== MAKE IMAGE PATHS =================

    public static String makeCardPath(String resourcePath) {
        return getModID() + "Resources/images/cards/" + resourcePath;
    }

    public static String makeRelicPath(String resourcePath) {
        return getModID() + "Resources/images/relics/" + resourcePath;
    }

    public static String makeRelicOutlinePath(String resourcePath) {
        return getModID() + "Resources/images/relics/outline/" + resourcePath;
    }

    public static String makeOrbPath(String resourcePath) {
        return getModID() + "Resources/orbs/" + resourcePath;
    }

    public static String makePowerPath(String resourcePath) {
        return getModID() + "Resources/images/powers/" + resourcePath;
    }

    public static String makeEventPath(String resourcePath) {
        return getModID() + "Resources/images/events/" + resourcePath;
    }

    // =============== /MAKE IMAGE PATHS/ =================

    // =============== /INPUT TEXTURE LOCATION/ =================


    // =============== SUBSCRIBE, CREATE THE COLOR_GRAY, INITIALIZE =================

    public TheForsakenMod() {
        logger.info("Subscribe to BaseMod hooks");

        BaseMod.subscribe(this);
        
      /*
           (   ( /(  (     ( /( (            (  `   ( /( )\ )    )\ ))\ )
           )\  )\()) )\    )\()))\ )   (     )\))(  )\()|()/(   (()/(()/(
         (((_)((_)((((_)( ((_)\(()/(   )\   ((_)()\((_)\ /(_))   /(_))(_))
         )\___ _((_)\ _ )\ _((_)/(_))_((_)  (_()((_) ((_|_))_  _(_))(_))_
        ((/ __| || (_)_\(_) \| |/ __| __| |  \/  |/ _ \|   \  |_ _||   (_)
         | (__| __ |/ _ \ | .` | (_ | _|  | |\/| | (_) | |) |  | | | |) |
          \___|_||_/_/ \_\|_|\_|\___|___| |_|  |_|\___/|___/  |___||___(_)
      */

        setModID("theForsaken");
        // cool
        // NOW READ THIS!!!!!!!!!!!!!!!:

        // 1. Go to your resources folder in the project panel, and refactor> rename theForsakenResources to
        // yourModIDResources.

        // 2. Click on the localization > eng folder and press ctrl+shift+r, then select "Directory" (rather than in Project)
        // replace all instances of theDefault with yourModID.
        // Because your mod ID isn't the default. Your cards (and everything else) should have Your mod id. Not mine.

        // 3. FINALLY and most importantly: Scroll up a bit. You may have noticed the image locations above don't use getModID()
        // Change their locations to reflect your actual ID rather than theDefault. They get loaded before getID is a thing.

        logger.info("Done subscribing");

        logger.info("Creating the color " + TheForsaken.Enums.COLOR_GOLD.toString());

        BaseMod.addColor(TheForsaken.Enums.COLOR_GOLD, FORSAKEN_GOLD, FORSAKEN_GOLD, FORSAKEN_GOLD,
                FORSAKEN_GOLD, FORSAKEN_GOLD, FORSAKEN_GOLD, FORSAKEN_GOLD,
                ATTACK_FORSAKEN_GOLD, SKILL_FORSAKEN_GOLD, POWER_FORSAKEN_GOLD, ENERGY_ORB_FORSAKEN_GOLD,
                ATTACK_FORSAKEN_GOLD_PORTRAIT, SKILL_FORSAKEN_GOLD_PORTRAIT, POWER_FORSAKEN_GOLD_PORTRAIT,
                ENERGY_ORB_FORSAKEN_GOLD_PORTRAIT, CARD_ENERGY_ORB);

        logger.info("Done creating the color");


        logger.info("Adding mod settings");
        // This loads the mod settings.
        // The actual mod Button is added below in receivePostInitialize()
        theForsakenDefaultSettings.setProperty(ENABLE_PLACEHOLDER_SETTINGS, "FALSE"); // This is the default setting. It's actually set...
        try {
            SpireConfig config = new SpireConfig("forsakenMod", "theForsakenConfig", theForsakenDefaultSettings); // ...right here
            // the "fileName" parameter is the name of the file MTS will create where it will save our setting.
            config.load(); // Load the setting and set the boolean to equal it
            enablePlaceholder = config.getBool(ENABLE_PLACEHOLDER_SETTINGS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("Done adding mod settings");

    }

    // ====== NO EDIT AREA ======
    // DON'T TOUCH THIS STUFF. IT IS HERE FOR STANDARDIZATION BETWEEN MODS AND TO ENSURE GOOD CODE PRACTICES.
    // IF YOU MODIFY THIS I WILL HUNT YOU DOWN AND DOWNVOTE YOUR MOD ON WORKSHOP

    private static void setModID(String ID) { // DON'T EDIT
        Gson coolG = new Gson(); // EY DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i hate u Gdx.files
        InputStream in = TheForsakenMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THIS ETHER
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // OR THIS, DON'T EDIT IT
        logger.info("You are attempting to set your mod ID as: " + ID); // NO WHY
        if (ID.equals(EXCEPTION_STRINGS.DEFAULTID)) { // DO *NOT* CHANGE THIS ESPECIALLY, TO EDIT YOUR MOD ID, SCROLL UP JUST A LITTLE, IT'S JUST ABOVE
            throw new RuntimeException(EXCEPTION_STRINGS.EXCEPTION); // THIS ALSO DON'T EDIT
        } else if (ID.equals(EXCEPTION_STRINGS.DEVID)) { // NO
            modID = EXCEPTION_STRINGS.DEFAULTID; // DON'T
        } else { // NO EDIT AREA
            modID = ID; // DON'T WRITE OR CHANGE THINGS HERE NOT EVEN A LITTLE
        } // NO
        logger.info("Success! ID is " + modID); // WHY WOULD U WANT IT NOT TO LOG?? DON'T EDIT THIS.
    } // NO

    private static String getModID() { // NO
        return modID; // DOUBLE NO
    } // NU-UH

    private static void pathCheck() { // ALSO NO
        Gson coolG = new Gson(); // NNOPE DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i still hate u btw Gdx.files
        InputStream in = TheForsakenMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THISSSSS
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // NAH, NO EDIT
        String packageName = TheForsakenMod.class.getPackage().getName(); // STILL NO EDIT ZONE
        FileHandle resourcePathExists = Gdx.files.internal(getModID() + "Resources"); // PLEASE DON'T EDIT THINGS HERE, THANKS
        if (!modID.equals(EXCEPTION_STRINGS.DEVID)) { // LEAVE THIS EDIT-LESS
            if (!packageName.equals(getModID())) { // NOT HERE ETHER
                throw new RuntimeException(EXCEPTION_STRINGS.PACKAGE_EXCEPTION + getModID()); // THIS IS A NO-NO
            } // WHY WOULD U EDIT THIS
            if (!resourcePathExists.exists()) { // DON'T CHANGE THIS
                throw new RuntimeException(EXCEPTION_STRINGS.RESOURCE_FOLDER_EXCEPTION + getModID() + "Resources"); // NOT THIS
            }// NO
        }// NO
    }// NO

    // ====== YOU CAN EDIT AGAIN ======


    @SuppressWarnings("unused")
    public static void initialize() {
        logger.info("========================= Initializing Default Mod. Hi. =========================");
        TheForsakenMod forsakenMod = new TheForsakenMod();
        logger.info("========================= /Default Mod Initialized. Hello World./ =========================");
    }

    // ============== /SUBSCRIBE, CREATE THE COLOR_GRAY, INITIALIZE/ =================


    // =============== LOAD THE CHARACTER =================

    @Override
    public void receiveEditCharacters() {
        logger.info("Beginning to edit characters. " + "Add " + TheForsaken.Enums.THE_FORSAKEN.toString());

        BaseMod.addCharacter(new TheForsaken("the Forsaken", TheForsaken.Enums.THE_FORSAKEN),
                THE_DEFAULT_BUTTON, THE_DEFAULT_PORTRAIT, TheForsaken.Enums.THE_FORSAKEN);

        receiveEditPotions();
        logger.info("Added " + TheForsaken.Enums.THE_FORSAKEN.toString());
    }

    // =============== /LOAD THE CHARACTER/ =================


    // =============== POST-INITIALIZE =================

    @Override
    public void receivePostInitialize() {
        logger.info("Loading badge image and mod options");

        // Load the Mod Badge
        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);

        // Create the Mod Menu
        ModPanel settingsPanel = new ModPanel();

        // Create the on/off button:
        ModLabeledToggleButton enableNormalsButton = new ModLabeledToggleButton("This is the text which goes next to the checkbox.",
                350.0f, 700.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, // Position (trial and error it), color, font
                enablePlaceholder, // Boolean it uses
                settingsPanel, // The mod panel in which this button will be in
                (label) -> {
                }, // thing??????? idk
                (button) -> { // The actual button:

                    enablePlaceholder = button.enabled; // The boolean true/false will be whether the button is enabled or not
                    try {
                        // And based on that boolean, set the settings and save them
                        SpireConfig config = new SpireConfig("forsakenMod", "theForsakenConfig", theForsakenDefaultSettings);
                        config.setBool(ENABLE_PLACEHOLDER_SETTINGS, enablePlaceholder);
                        config.save();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

        settingsPanel.addUIElement(enableNormalsButton); // Add the button to the settings panel. Button is a go.

        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);


        // =============== EVENTS =================

        // This event will be exclusive to the City (act 2). If you want an event that's present at any
        // part of the game, simply don't include the dungeon ID
        // If you want to have a character-specific event, look at slimebound (CityRemoveEventPatch).
        // Essentially, you need to patch the game and say "if a player is not playing my character class, remove the event from the pool"
        BaseMod.addEvent(PlagueDoctorEvent.ID, PlagueDoctorEvent.class);

        // =============== /EVENTS/ =================
        logger.info("Done loading badge Image and mod options");
    }

    // =============== / POST-INITIALIZE/ =================


    // ================ ADD POTIONS ===================

    private void receiveEditPotions() {
        logger.info("Beginning to edit potions");

        // Class Specific Potion. If you want your potion to not be class-specific,
        // just remove the player class at the end (in this case the "TheDefaultEnum.THE_FORSAKEN".
        // Remember, you can press ctrl+P inside parentheses like addPotions)
        BaseMod.addPotion(FearPotion.class, PLACEHOLDER_POTION_LIQUID, PLACEHOLDER_POTION_HYBRID, PLACEHOLDER_POTION_SPOTS, FearPotion.POTION_ID, TheForsaken.Enums.THE_FORSAKEN);

        logger.info("Done editing potions");
    }

    // ================ /ADD POTIONS/ ===================


    // ================ ADD RELICS ===================

    @Override
    public void receiveEditRelics() {
        logger.info("Adding relics");

        // This adds a character specific relic. Only when you play with the mentioned color, will you get this relic.
        BaseMod.addRelicToCustomPool(new ArmorOfThorns(), TheForsaken.Enums.COLOR_GOLD);
        BaseMod.addRelicToCustomPool(new JudgementScales(), TheForsaken.Enums.COLOR_GOLD);
        BaseMod.addRelicToCustomPool(new PlagueMask(), TheForsaken.Enums.COLOR_GOLD);
        BaseMod.addRelicToCustomPool(new ScalesOfTruth(), TheForsaken.Enums.COLOR_GOLD);

        // This adds a relic to the Shared pool. Every character can find this relic.
        BaseMod.addRelic(new Gavel(), RelicType.SHARED);
        BaseMod.addRelic(new HumbleEgg(), RelicType.SHARED);
        BaseMod.addRelic(new Kindling(), RelicType.SHARED);
        BaseMod.addRelic(new Lifeblossom(), RelicType.SHARED);
        BaseMod.addRelic(new ScaryMask(), RelicType.SHARED);
        BaseMod.addRelic(new PaleLantern(), RelicType.SHARED);

        // Mark relics as seen (the others are all starters so they're marked as seen in the character file
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

    // ================ /ADD RELICS/ ===================


    // ================ ADD CARDS ===================

    @Override
    public void receiveEditCards() {
        logger.info("Adding variables");
        //Ignore this
        pathCheck();
        // Add the Custom Dynamic Variables
        logger.info("Add variables");
        // Add the Custom Dynamic variables
        BaseMod.addDynamicVariable(new DefaultSecondMagicNumber());
        BaseMod.addDynamicVariable(new SacrificeSoulVariable());
        BaseMod.addDynamicVariable(new UnplayedCardsVariable());

        logger.info("Adding cards");
        // Add the cards
        // Don't comment out/delete these cards (yet). You need 1 of each type and rarity (technically) for your game not to crash
        // when generating card rewards/shop screen items.

        CardLibrary library = new CardLibrary();
        for (AbstractCard card : library.cardGroup.group) {
            BaseMod.addCard(card);
            UnlockTracker.markCardAsSeen(card.cardID);
        }

        BaseMod.addCard(new PlagueCurse());
        UnlockTracker.markCardAsSeen(PlagueCurse.ID);

        BaseMod.addCard(new Recompense());
        UnlockTracker.markCardAsSeen(Recompense.ID);

        logger.info("Making sure the cards are unlocked.");
        // Unlock the cards
        // This is so that they are all "seen" in the library, for people who like to look at the card list
        // before playing your mod.

        logger.info("Done adding cards!");
    }

    // There are better ways to do this than listing every single individual card, but I do not want to complicate things
    // in a "tutorial" mod. This will do and it's completely ok to use. If you ever want to clean up and
    // shorten all the imports, go look take a look at other mods, such as Hubris.

    // ================ /ADD CARDS/ ===================


    // ================ LOAD THE TEXT ===================

    @Override
    public void receiveEditStrings() {
        logger.info("You seeing this?");
        logger.info("Beginning to edit strings for mod with ID: " + getModID());

        String lang = "eng";

        if (Settings.language == Settings.GameLanguage.ZHS) {
            lang = "zhs";
        }

        logger.info("Loading strings for language: " + lang);

        // CardStrings
        BaseMod.loadCustomStringsFile(CardStrings.class,
                getModID() + "Resources/localization/" + lang + "/TheForsakenMod-Card-Strings.json");

        // PowerStrings
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                getModID() + "Resources/localization/" + lang + "/TheForsakenMod-Power-Strings.json");

        // RelicStrings
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                getModID() + "Resources/localization/" + lang + "/TheForsakenMod-Relic-Strings.json");

        // Event Strings
        BaseMod.loadCustomStringsFile(EventStrings.class,
                getModID() + "Resources/localization/" + lang + "/TheForsakenMod-Event-Strings.json");

        // PotionStrings
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                getModID() + "Resources/localization/" + lang + "/TheForsakenMod-Potion-Strings.json");

        // CharacterStrings
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                getModID() + "Resources/localization/" + lang + "/TheForsakenMod-Character-Strings.json");

        // OrbStrings
        BaseMod.loadCustomStringsFile(OrbStrings.class,
                getModID() + "Resources/localization/" + lang + "/TheForsakenMod-Orb-Strings.json");

        // UIStrings
        BaseMod.loadCustomStringsFile(UIStrings.class,
                getModID() + "Resources/localization/" + lang + "/TheForsakenMod-Ui-Strings.json");

        logger.info("Done editing strings");
    }

    // ================ /LOAD THE TEXT/ ===================

    // ================ LOAD THE KEYWORDS ===================

    @Override
    public void receiveEditKeywords() {
        // Keywords on cards are supposed to be Capitalized, while in Keyword-String.json they're lowercase
        //
        // Multiword keywords on cards are done With_Underscores
        //
        // If you're using multiword keywords, the first element in your NAMES array in your keywords-strings.json has to be the same as the PROPER_NAME.
        // That is, in Card-Strings.json you would have #yA_Long_Keyword (#y highlights the keyword in yellow).
        // In Keyword-Strings.json you would have PROPER_NAME as A Long Keyword and the first element in NAMES be a long keyword, and the second element be a_long_keyword

        Gson gson = new Gson();
        String lang = "eng";

        if (Settings.language == Settings.GameLanguage.ZHS) {
            lang = "zhs";
        }

        String json = Gdx.files.internal(getModID() + "Resources/localization/" + lang + "/TheForsakenMod-Keyword-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(getModID().toLowerCase(), keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
                //  getModID().toLowerCase() makes your keyword mod specific (it won't show up in other cards that use that word)
            }
        }
    }

    // ================ /LOAD THE KEYWORDS/ ===================

    // ------------- UNLOCKS -----------------


    /*
    @Override
    public void receiveSetUnlocks() {
        BaseMod.addUnlockBundle(new CustomUnlockBundle(
                AbstractUnlock.UnlockType.CARD,
                Terrorize.ID, CowardsBrand.ID, Horror.ID
        ), TheForsaken.Enums.THE_FORSAKEN, 0);

        BaseMod.addUnlockBundle(new CustomUnlockBundle(
                AbstractUnlock.UnlockType.RELIC,
                Lifeblossom.ID, Kindling.ID, ArmorOfThorns.ID
        ), TheForsaken.Enums.THE_FORSAKEN, 1);

        BaseMod.addUnlockBundle(new CustomUnlockBundle(
                AbstractUnlock.UnlockType.CARD,
                HymnOfRest.ID, BattleHymn.ID, WardingHymn.ID
        ), TheForsaken.Enums.THE_FORSAKEN, 2);

        BaseMod.addUnlockBundle(new CustomUnlockBundle(
                AbstractUnlock.UnlockType.RELIC,
                ScalesOfTruth.ID, Gavel.ID, ScaryMask.ID
        ), TheForsaken.Enums.THE_FORSAKEN, 3);

        BaseMod.addUnlockBundle(new CustomUnlockBundle(
                AbstractUnlock.UnlockType.CARD,
                CorruptedForm.ID, BottledPlague.ID, CorruptedWord.ID
        ), TheForsaken.Enums.THE_FORSAKEN, 4);
    }
    */

    @Override
    public void receivePreStartGame() {
    }

    // this adds "ModName:" before the ID of any card/relic/power etc.
    // in order to avoid conflicts if any other mod uses the same ID.
    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }

    public static ArrayList<UUID> usedCards;

    @Override
    public void receiveCardUsed(AbstractCard abstractCard) {
        UUID uuid = abstractCard.uuid;
        if (!usedCards.contains(uuid)) {
            usedCards.add(uuid);
        }
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        usedCards = new ArrayList<>();
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

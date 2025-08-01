package cn.nukkit.network.protocol;

import cn.nukkit.math.Vector3f;
import cn.nukkit.utils.Utils;
import lombok.ToString;

@ToString
public class LevelSoundEventPacket extends DataPacket {

    public static final byte NETWORK_ID = ProtocolInfo.LEVEL_SOUND_EVENT_PACKET;

    public static final int SOUND_ITEM_USE_ON = 0;
    public static final int SOUND_HIT = 1;
    public static final int SOUND_STEP = 2;
    public static final int SOUND_FLY = 3;
    public static final int SOUND_JUMP = 4;
    public static final int SOUND_BREAK = 5;
    public static final int SOUND_PLACE = 6;
    public static final int SOUND_HEAVY_STEP = 7;
    public static final int SOUND_GALLOP = 8;
    public static final int SOUND_FALL = 9;
    public static final int SOUND_AMBIENT = 10;
    public static final int SOUND_AMBIENT_BABY = 11;
    public static final int SOUND_AMBIENT_IN_WATER = 12;
    public static final int SOUND_BREATHE = 13;
    public static final int SOUND_DEATH = 14;
    public static final int SOUND_DEATH_IN_WATER = 15;
    public static final int SOUND_DEATH_TO_ZOMBIE = 16;
    public static final int SOUND_HURT = 17;
    public static final int SOUND_HURT_IN_WATER = 18;
    public static final int SOUND_MAD = 19;
    public static final int SOUND_BOOST = 20;
    public static final int SOUND_BOW = 21;
    public static final int SOUND_SQUISH_BIG = 22;
    public static final int SOUND_SQUISH_SMALL = 23;
    public static final int SOUND_FALL_BIG = 24;
    public static final int SOUND_FALL_SMALL = 25;
    public static final int SOUND_SPLASH = 26;
    public static final int SOUND_FIZZ = 27;
    public static final int SOUND_FLAP = 28;
    public static final int SOUND_SWIM = 29;
    public static final int SOUND_DRINK = 30;
    public static final int SOUND_EAT = 31;
    public static final int SOUND_TAKEOFF = 32;
    public static final int SOUND_SHAKE = 33;
    public static final int SOUND_PLOP = 34;
    public static final int SOUND_LAND = 35;
    public static final int SOUND_SADDLE = 36;
    public static final int SOUND_ARMOR = 37;
    public static final int SOUND_MOB_ARMOR_STAND_PLACE = 38;
    public static final int SOUND_ADD_CHEST = 39;
    public static final int SOUND_THROW = 40;
    public static final int SOUND_ATTACK = 41;
    public static final int SOUND_ATTACK_NODAMAGE = 42;
    public static final int SOUND_ATTACK_STRONG = 43;
    public static final int SOUND_WARN = 44;
    public static final int SOUND_SHEAR = 45;
    public static final int SOUND_MILK = 46;
    public static final int SOUND_THUNDER = 47;
    public static final int SOUND_EXPLODE = 48;
    public static final int SOUND_FIRE = 49;
    public static final int SOUND_IGNITE = 50;
    public static final int SOUND_FUSE = 51;
    public static final int SOUND_STARE = 52;
    public static final int SOUND_SPAWN = 53;
    public static final int SOUND_SHOOT = 54;
    public static final int SOUND_BREAK_BLOCK = 55;
    public static final int SOUND_LAUNCH = 56;
    public static final int SOUND_BLAST = 57;
    public static final int SOUND_LARGE_BLAST = 58;
    public static final int SOUND_TWINKLE = 59;
    public static final int SOUND_REMEDY = 60;
    public static final int SOUND_UNFECT = 61;
    public static final int SOUND_LEVELUP = 62;
    public static final int SOUND_BOW_HIT = 63;
    public static final int SOUND_BULLET_HIT = 64;
    public static final int SOUND_EXTINGUISH_FIRE = 65;
    public static final int SOUND_ITEM_FIZZ = 66;
    public static final int SOUND_CHEST_OPEN = 67;
    public static final int SOUND_CHEST_CLOSED = 68;
    public static final int SOUND_SHULKERBOX_OPEN = 69;
    public static final int SOUND_SHULKERBOX_CLOSED = 70;
    public static final int SOUND_ENDERCHEST_OPEN = 71;
    public static final int SOUND_ENDERCHEST_CLOSED = 72;
    public static final int SOUND_POWER_ON = 73;
    public static final int SOUND_POWER_OFF = 74;
    public static final int SOUND_ATTACH = 75;
    public static final int SOUND_DETACH = 76;
    public static final int SOUND_DENY = 77;
    public static final int SOUND_TRIPOD = 78;
    public static final int SOUND_POP = 79;
    public static final int SOUND_DROP_SLOT = 80;
    public static final int SOUND_NOTE = 81;
    public static final int SOUND_THORNS = 82;
    public static final int SOUND_PISTON_IN = 83;
    public static final int SOUND_PISTON_OUT = 84;
    public static final int SOUND_PORTAL = 85;
    public static final int SOUND_WATER = 86;
    public static final int SOUND_LAVA_POP = 87;
    public static final int SOUND_LAVA = 88;
    public static final int SOUND_BURP = 89;
    public static final int SOUND_BUCKET_FILL_WATER = 90;
    public static final int SOUND_BUCKET_FILL_LAVA = 91;
    public static final int SOUND_BUCKET_EMPTY_WATER = 92;
    public static final int SOUND_BUCKET_EMPTY_LAVA = 93;
    public static final int SOUND_ARMOR_EQUIP_CHAIN = 94;
    public static final int SOUND_ARMOR_EQUIP_DIAMOND = 95;
    public static final int SOUND_ARMOR_EQUIP_GENERIC = 96;
    public static final int SOUND_ARMOR_EQUIP_GOLD = 97;
    public static final int SOUND_ARMOR_EQUIP_IRON = 98;
    public static final int SOUND_ARMOR_EQUIP_LEATHER = 99;
    public static final int SOUND_ARMOR_EQUIP_ELYTRA = 100;
    public static final int SOUND_RECORD_13 = 101;
    public static final int SOUND_RECORD_CAT = 102;
    public static final int SOUND_RECORD_BLOCKS = 103;
    public static final int SOUND_RECORD_CHIRP = 104;
    public static final int SOUND_RECORD_FAR = 105;
    public static final int SOUND_RECORD_MALL = 106;
    public static final int SOUND_RECORD_MELLOHI = 107;
    public static final int SOUND_RECORD_STAL = 108;
    public static final int SOUND_RECORD_STRAD = 109;
    public static final int SOUND_RECORD_WARD = 110;
    public static final int SOUND_RECORD_11 = 111;
    public static final int SOUND_RECORD_WAIT = 112;
    public static final int SOUND_STOP_RECORD = 113;
    public static final int SOUND_GUARDIAN_FLOP = 114;
    public static final int SOUND_ELDERGUARDIAN_CURSE = 115;
    public static final int SOUND_MOB_WARNING = 116;
    public static final int SOUND_MOB_WARNING_BABY = 117;
    public static final int SOUND_TELEPORT = 118;
    public static final int SOUND_SHULKER_OPEN = 119;
    public static final int SOUND_SHULKER_CLOSE = 120;
    public static final int SOUND_HAGGLE = 121;
    public static final int SOUND_HAGGLE_YES = 122;
    public static final int SOUND_HAGGLE_NO = 123;
    public static final int SOUND_HAGGLE_IDLE = 124;
    public static final int SOUND_CHORUSGROW = 125;
    public static final int SOUND_CHORUSDEATH = 126;
    public static final int SOUND_GLASS = 127;
    public static final int SOUND_POTION_BREWED = 128;
    public static final int SOUND_CAST_SPELL = 129;
    public static final int SOUND_PREPARE_ATTACK = 130;
    public static final int SOUND_PREPARE_SUMMON = 131;
    public static final int SOUND_PREPARE_WOLOLO = 132;
    public static final int SOUND_FANG = 133;
    public static final int SOUND_CHARGE = 134;
    public static final int SOUND_CAMERA_TAKE_PICTURE = 135;
    public static final int SOUND_LEASHKNOT_PLACE = 136;
    public static final int SOUND_LEASHKNOT_BREAK = 137;
    public static final int SOUND_GROWL = 138;
    public static final int SOUND_WHINE = 139;
    public static final int SOUND_PANT = 140;
    public static final int SOUND_PURR = 141;
    public static final int SOUND_PURREOW = 142;
    public static final int SOUND_DEATH_MIN_VOLUME = 143;
    public static final int SOUND_DEATH_MID_VOLUME = 144;
    public static final int SOUND_IMITATE_BLAZE = 145;
    public static final int SOUND_IMITATE_CAVE_SPIDER = 146;
    public static final int SOUND_IMITATE_CREEPER = 147;
    public static final int SOUND_IMITATE_ELDER_GUARDIAN = 148;
    public static final int SOUND_IMITATE_ENDER_DRAGON = 149;
    public static final int SOUND_IMITATE_ENDERMAN = 150;
    public static final int SOUND_IMITATE_ENDERMITE = 151;
    public static final int SOUND_IMITATE_EVOCATION_ILLAGER = 152;
    public static final int SOUND_IMITATE_GHAST = 153;
    public static final int SOUND_IMITATE_HUSK = 154;
    public static final int SOUND_IMITATE_ILLUSION_ILLAGER = 155;
    public static final int SOUND_IMITATE_MAGMA_CUBE = 156;
    public static final int SOUND_IMITATE_POLAR_BEAR = 157;
    public static final int SOUND_IMITATE_SHULKER = 158;
    public static final int SOUND_IMITATE_SILVERFISH = 159;
    public static final int SOUND_IMITATE_SKELETON = 160;
    public static final int SOUND_IMITATE_SLIME = 161;
    public static final int SOUND_IMITATE_SPIDER = 162;
    public static final int SOUND_IMITATE_STRAY = 163;
    public static final int SOUND_IMITATE_VEX = 164;
    public static final int SOUND_IMITATE_VINDICATION_ILLAGER = 165;
    public static final int SOUND_IMITATE_WITCH = 166;
    public static final int SOUND_IMITATE_WITHER = 167;
    public static final int SOUND_IMITATE_WITHER_SKELETON = 168;
    public static final int SOUND_IMITATE_WOLF = 169;
    public static final int SOUND_IMITATE_ZOMBIE = 170;
    public static final int SOUND_IMITATE_ZOMBIE_PIGMAN = 171;
    public static final int SOUND_IMITATE_ZOMBIE_VILLAGER = 172;
    public static final int SOUND_BLOCK_END_PORTAL_FRAME_FILL = 173;
    public static final int SOUND_BLOCK_END_PORTAL_SPAWN = 174;
    public static final int SOUND_RANDOM_ANVIL_USE = 175;
    public static final int SOUND_BOTTLE_DRAGONBREATH = 176;
    public static final int SOUND_PORTAL_TRAVEL = 177;
    public static final int SOUND_ITEM_TRIDENT_HIT = 178;
    public static final int SOUND_ITEM_TRIDENT_RETURN = 179;
    public static final int SOUND_ITEM_TRIDENT_RIPTIDE_1 = 180;
    public static final int SOUND_ITEM_TRIDENT_RIPTIDE_2 = 181;
    public static final int SOUND_ITEM_TRIDENT_RIPTIDE_3 = 182;
    public static final int SOUND_ITEM_TRIDENT_THROW = 183;
    public static final int SOUND_ITEM_TRIDENT_THUNDER = 184;
    public static final int SOUND_ITEM_TRIDENT_HIT_GROUND = 185;
    public static final int SOUND_DEFAULT = 186;
    public static final int SOUND_BLOCK_FLETCHING_TABLE_USE = 187;
    public static final int SOUND_ELEMCONSTRUCT_OPEN = 188;
    public static final int SOUND_ICEBOMB_HIT = 189;
    public static final int SOUND_BALLOONPOP = 190;
    public static final int SOUND_LT_REACTION_ICEBOMB = 191;
    public static final int SOUND_LT_REACTION_BLEACH = 192;
    public static final int SOUND_LT_REACTION_EPASTE = 193;
    public static final int SOUND_LT_REACTION_EPASTE2 = 194;
    public static final int SOUND_LT_REACTION_GLOW_STICK = 195;
    public static final int SOUND_LT_REACTION_GLOW_STICK_2 = 196;
    public static final int SOUND_LT_REACTION_LUMINOL = 197;
    public static final int SOUND_LT_REACTION_SALT = 198;
    public static final int SOUND_LT_REACTION_FERTILIZER = 199;
    public static final int SOUND_LT_REACTION_FIREBALL = 200;
    public static final int SOUND_LT_REACTION_MGSALT = 201;
    public static final int SOUND_LT_REACTION_MISCFIRE = 202;
    public static final int SOUND_LT_REACTION_FIRE = 203;
    public static final int SOUND_LT_REACTION_MISCEXPLOSION = 204;
    public static final int SOUND_LT_REACTION_MISCMYSTICAL = 205;
    public static final int SOUND_LT_REACTION_MISCMYSTICAL2 = 206;
    public static final int SOUND_LT_REACTION_PRODUCT = 207;
    public static final int SOUND_SPARKLER_USE = 208;
    public static final int SOUND_GLOWSTICK_USE = 209;
    public static final int SOUND_SPARKLER_ACTIVE = 210;
    public static final int SOUND_CONVERT_TO_DROWNED = 211;
    public static final int SOUND_BUCKET_FILL_FISH = 212;
    public static final int SOUND_BUCKET_EMPTY_FISH = 213;
    public static final int SOUND_BUBBLE_UP = 214;
    public static final int SOUND_BUBBLE_DOWN = 215;
    public static final int SOUND_BUBBLE_POP = 216;
    public static final int SOUND_BUBBLE_UPINSIDE = 217;
    public static final int SOUND_BUBBLE_DOWNINSIDE = 218;
    public static final int SOUND_HURT_BABY = 219;
    public static final int SOUND_DEATH_BABY = 220;
    public static final int SOUND_STEP_BABY = 221;
    public static final int SOUND_SPAWN_BABY = 222;
    public static final int SOUND_BORN = 223;
    public static final int SOUND_BLOCK_TURTLE_EGG_BREAK = 224;
    public static final int SOUND_BLOCK_TURTLE_EGG_CRACK = 225;
    public static final int SOUND_BLOCK_TURTLE_EGG_HATCH = 226;
    public static final int SOUND_LAY_EGG = 227;
    public static final int SOUND_BLOCK_TURTLE_EGG_ATTACK = 228;
    public static final int SOUND_BEACON_ACTIVATE = 229;
    public static final int SOUND_BEACON_AMBIENT = 230;
    public static final int SOUND_BEACON_DEACTIVATE = 231;
    public static final int SOUND_BEACON_POWER = 232;
    public static final int SOUND_CONDUIT_ACTIVATE = 233;
    public static final int SOUND_CONDUIT_AMBIENT = 234;
    public static final int SOUND_CONDUIT_ATTACK = 235;
    public static final int SOUND_CONDUIT_DEACTIVATE = 236;
    public static final int SOUND_CONDUIT_SHORT = 237;
    public static final int SOUND_SWOOP = 238;
    public static final int SOUND_BLOCK_BAMBOO_SAPLING_PLACE = 239;
    public static final int SOUND_PRESNEEZE = 240;
    public static final int SOUND_SNEEZE = 241;
    public static final int SOUND_AMBIENT_TAME = 242;
    public static final int SOUND_SCARED = 243;
    public static final int SOUND_BLOCK_SCAFFOLDING_CLIMB = 244;
    public static final int SOUND_CROSSBOW_LOADING_START = 245;
    public static final int SOUND_CROSSBOW_LOADING_MIDDLE = 246;
    public static final int SOUND_CROSSBOW_LOADING_END = 247;
    public static final int SOUND_CROSSBOW_SHOOT = 248;
    public static final int SOUND_CROSSBOW_QUICK_CHARGE_START = 249;
    public static final int SOUND_CROSSBOW_QUICK_CHARGE_MIDDLE = 250;
    public static final int SOUND_CROSSBOW_QUICK_CHARGE_END = 251;
    public static final int SOUND_AMBIENT_AGGRESSIVE = 252;
    public static final int SOUND_AMBIENT_WORRIED = 253;
    public static final int SOUND_CANT_BREED = 254;
    public static final int SOUND_ITEM_SHIELD_BLOCK = 255;
    public static final int SOUND_ITEM_BOOK_PUT = 256;
    public static final int SOUND_BLOCK_GRINDSTONE_USE = 257;
    public static final int SOUND_BLOCK_BELL_HIT = 258;
    public static final int SOUND_BLOCK_CAMPFIRE_CRACKLE = 259;
    public static final int SOUND_ROAR = 260;
    public static final int SOUND_STUN = 261;
    public static final int SOUND_BLOCK_SWEET_BERRY_BUSH_HURT = 262;
    public static final int SOUND_BLOCK_SWEET_BERRY_BUSH_PICK = 263;
    public static final int SOUND_BLOCK_CARTOGRAPHY_TABLE_USE = 264;
    public static final int SOUND_BLOCK_STONECUTTER_USE = 265;
    public static final int SOUND_BLOCK_COMPOSTER_EMPTY = 266;
    public static final int SOUND_BLOCK_COMPOSTER_FILL = 267;
    public static final int SOUND_BLOCK_COMPOSTER_FILL_SUCCESS = 268;
    public static final int SOUND_BLOCK_COMPOSTER_READY = 269;
    public static final int SOUND_BLOCK_BARREL_OPEN = 270;
    public static final int SOUND_BLOCK_BARREL_CLOSE = 271;
    public static final int SOUND_RAID_HORN = 272;
    public static final int SOUND_BLOCK_LOOM_USE = 273;
    public static final int SOUND_AMBIENT_IN_RAID = 274;
    public static final int SOUND_UI_CARTOGRAPHY_TABLE_TAKE_RESULT = 275;
    public static final int SOUND_UI_STONECUTTER_TAKE_RESULT = 276;
    public static final int SOUND_UI_LOOM_TAKE_RESULT = 277;
    public static final int SOUND_BLOCK_SMOKER_SMOKE = 278;
    public static final int SOUND_BLOCK_BLASTFURNACE_FIRE_CRACKLE = 279;
    public static final int SOUND_BLOCK_SMITHING_TABLE_USE = 280;
    public static final int SOUND_SCREECH = 281;
    public static final int SOUND_SLEEP = 282;
    public static final int SOUND_BLOCK_FURNACE_LIT = 283;
    public static final int SOUND_CONVERT_MOOSHROOM = 284;
    public static final int SOUND_MILK_SUSPICIOUSLY = 285;
    public static final int SOUND_CELEBRATE = 286;
    public static final int SOUND_JUMP_PREVENT = 287;
    public static final int SOUND_AMBIENT_POLLINATE = 288;
    public static final int SOUND_BLOCK_BEEHIVE_DRIP = 289;
    public static final int SOUND_BLOCK_BEEHIVE_ENTER = 290;
    public static final int SOUND_BLOCK_BEEHIVE_EXIT = 291;
    public static final int SOUND_BLOCK_BEEHIVE_WORK = 292;
    public static final int SOUND_BLOCK_BEEHIVE_SHEAR = 293;
    public static final int SOUND_DRINK_HONEY = 294;
    public static final int SOUND_AMBIENT_CAVE = 295;
    public static final int SOUND_RETREAT = 296;
    public static final int SOUND_CONVERTED_TO_ZOMBIFIED = 297;
    public static final int SOUND_ADMIRE = 298;
    public static final int SOUND_STEP_LAVA = 299;
    public static final int SOUND_TEMPT = 300;
    public static final int SOUND_PANIC = 301;
    public static final int SOUND_ANGRY = 302;
    public static final int SOUND_AMBIENT_WARPED_FOREST_MOOD = 303;
    public static final int SOUND_AMBIENT_SOULSAND_VALLEY_MOOD = 304;
    public static final int SOUND_AMBIENT_NETHER_WASTES_MOOD = 305;
    public static final int SOUND_RESPAWN_ANCHOR_BASALT_DELTAS_MOOD = 306;
    public static final int SOUND_AMBIENT_CRIMSON_FOREST_MOOD = 307;
    public static final int SOUND_RESPAWN_ANCHOR_CHARGE = 308;
    public static final int SOUND_RESPAWN_ANCHOR_DEPLETE = 309;
    public static final int SOUND_RESPAWN_ANCHOR_SET_SPAWN = 310;
    public static final int SOUND_RESPAWN_ANCHOR_AMBIENT = 311;
    public static final int SOUND_PARTICLE_SOUL_ESCAPE_QUIET = 312;
    public static final int SOUND_PARTICLE_SOUL_ESCAPE_LOUD = 313;
    public static final int SOUND_RECORD_PIGSTEP = 314;
    public static final int SOUND_LODESTONE_COMPASS_LINK_COMPASS_TO_LODESTONE = 315;
    public static final int SOUND_SMITHING_TABLE_USE = 316;
    public static final int SOUND_ARMOR_EQUIP_NETHERITE = 317;
    public static final int SOUND_AMBIENT_WARPED_FOREST_LOOP = 318;
    public static final int SOUND_AMBIENT_SOULSAND_VALLEY_LOOP = 319;
    public static final int SOUND_AMBIENT_NETHER_WASTES_LOOP = 320;
    public static final int SOUND_AMBIENT_BASALT_DELTAS_LOOP = 321;
    public static final int SOUND_AMBIENT_CRIMSON_FOREST_LOOP = 322;
    public static final int SOUND_AMBIENT_WARPED_FOREST_ADDITIONS = 323;
    public static final int SOUND_AMBIENT_SOULSAND_VALLEY_ADDITIONS = 324;
    public static final int SOUND_AMBIENT_NETHER_WASTES_ADDITIONS = 325;
    public static final int SOUND_AMBIENT_BASALT_DELTAS_ADDITIONS = 326;
    public static final int SOUND_AMBIENT_CRIMSON_FOREST_ADDITIONS = 327;
    public static final int SOUND_SCULK_SENSOR_POWER_ON = 328;
    public static final int SOUND_SCULK_SENSOR_POWER_OFF = 329;
    public static final int SOUND_BUCKET_FILL_POWDER_SNOW = 330;
    public static final int SOUND_BUCKET_EMPTY_POWDER_SNOW = 331;
    public static final int SOUND_POINTED_DRIPSTONE_CAULDRON_DRIP_WATER = 332;
    public static final int SOUND_POINTED_DRIPSTONE_CAULDRON_DRIP_LAVA = 333;
    public static final int SOUND_POINTED_DRIPSTONE_DRIP_WATER = 334;
    public static final int SOUND_POINTED_DRIPSTONE_DRIP_LAVA = 335;
    public static final int SOUND_CAVE_VINES_PICK_BERRIES = 336;
    public static final int SOUND_BIG_DRIPLEAF_TILT_DOWN = 337;
    public static final int SOUND_BIG_DRIPLEAF_TILT_UP = 338;
    public static final int SOUND_COPPER_WAX_ON = 339;
    public static final int SOUND_COPPER_WAX_OFF = 340;
    public static final int SOUND_SCRAPE = 341;
    public static final int SOUND_PLAYER_HURT_DROWN = 342;
    public static final int SOUND_PLAYER_HURT_ON_FIRE = 343;
    public static final int SOUND_PLAYER_HURT_FREEZE = 344;
    public static final int SOUND_USE_SPYGLASS = 345;
    public static final int SOUND_STOP_USING_SPYGLASS = 346;
    public static final int SOUND_AMETHYST_BLOCK_CHIME = 347;
    public static final int SOUND_AMBIENT_SCREAMER = 348;
    public static final int SOUND_HURT_SCREAMER = 349;
    public static final int SOUND_DEATH_SCREAMER = 350;
    public static final int SOUND_MILK_SCREAMER = 351;
    public static final int SOUND_JUMP_TO_BLOCK = 352;
    public static final int SOUND_PRE_RAM = 353;
    public static final int SOUND_PRE_RAM_SCREAMER = 354;
    public static final int SOUND_RAM_IMPACT = 355;
    public static final int SOUND_RAM_IMPACT_SCREAMER = 356;
    public static final int SOUND_SQUID_INK_SQUIRT = 357;
    public static final int SOUND_GLOW_SQUID_INK_SQUIRT = 358;
    public static final int SOUND_CONVERT_TO_STRAY = 359;
    public static final int SOUND_CAKE_ADD_CANDLE = 360;
    public static final int SOUND_EXTINGUISH_CANDLE = 361;
    public static final int SOUND_AMBIENT_CANDLE = 362;
    public static final int SOUND_BLOCK_CLICK = 363;
    public static final int SOUND_BLOCK_CLICK_FAIL = 364;
    public static final int SOUND_SCULK_CATALYST_BLOOM = 365;
    public static final int SOUND_SCULK_SHRIEKER_SHRIEK = 366;
    public static final int SOUND_WARDEN_NEARBY_CLOSE = 367;
    public static final int SOUND_WARDEN_NEARBY_CLOSER = 368;
    public static final int SOUND_WARDEN_NEARBY_CLOSEST = 369;
    public static final int SOUND_WARDEN_SLIGHTLY_ANGRY = 370;
    public static final int SOUND_RECORD_OTHERSIDE = 371;
    public static final int SOUND_TONGUE = 372;
    public static final int SOUND_CRACK_IRON_GOLEM = 373;
    public static final int SOUND_REPAIR_IRON_GOLEM = 374;
    public static final int SOUND_LISTENING = 375;
    public static final int SOUND_HEARTBEAT = 376;
    public static final int SOUND_HORN_BREAK = 377;
    public static final int SOUND_SCULK_PLACE = 378;
    public static final int SOUND_SCULK_SPREAD = 379;
    public static final int SOUND_SCULK_CHARGE = 380;
    public static final int SOUND_SCULK_SENSOR_PLACE = 381;
    public static final int SOUND_SCULK_SHRIEKER_PLACE = 382;
    public static final int SOUND_GOAT_CALL_0 = 383;
    public static final int SOUND_GOAT_CALL_1 = 384;
    public static final int SOUND_GOAT_CALL_2 = 385;
    public static final int SOUND_GOAT_CALL_3 = 386;
    public static final int SOUND_GOAT_CALL_4 = 387;
    public static final int SOUND_GOAT_CALL_5 = 388;
    public static final int SOUND_GOAT_CALL_6 = 389;
    public static final int SOUND_GOAT_CALL_7 = 390;
    public static final int SOUND_GOAT_CALL_8 = 391;
    public static final int SOUND_GOAT_CALL_9 = 392;
    public static final int SOUND_GOAT_HARMONY_0 = 393;
    public static final int SOUND_GOAT_HARMONY_1 = 394;
    public static final int SOUND_GOAT_HARMONY_2 = 395;
    public static final int SOUND_GOAT_HARMONY_3 = 396;
    public static final int SOUND_GOAT_HARMONY_4 = 397;
    public static final int SOUND_GOAT_HARMONY_5 = 398;
    public static final int SOUND_GOAT_HARMONY_6 = 399;
    public static final int SOUND_GOAT_HARMONY_7 = 400;
    public static final int SOUND_GOAT_HARMONY_8 = 401;
    public static final int SOUND_GOAT_HARMONY_9 = 402;
    public static final int SOUND_GOAT_MELODY_0 = 403;
    public static final int SOUND_GOAT_MELODY_1 = 404;
    public static final int SOUND_GOAT_MELODY_2 = 405;
    public static final int SOUND_GOAT_MELODY_3 = 406;
    public static final int SOUND_GOAT_MELODY_4 = 407;
    public static final int SOUND_GOAT_MELODY_5 = 408;
    public static final int SOUND_GOAT_MELODY_6 = 409;
    public static final int SOUND_GOAT_MELODY_7 = 410;
    public static final int SOUND_GOAT_MELODY_8 = 411;
    public static final int SOUND_GOAT_MELODY_9 = 412;
    public static final int SOUND_GOAT_BASS_0 = 413;
    public static final int SOUND_GOAT_BASS_1 = 414;
    public static final int SOUND_GOAT_BASS_2 = 415;
    public static final int SOUND_GOAT_BASS_3 = 416;
    public static final int SOUND_GOAT_BASS_4 = 417;
    public static final int SOUND_GOAT_BASS_5 = 418;
    public static final int SOUND_GOAT_BASS_6 = 419;
    public static final int SOUND_GOAT_BASS_7 = 420;
    public static final int SOUND_GOAT_BASS_8 = 421;
    public static final int SOUND_GOAT_BASS_9 = 422;

    public static final int SOUND_IMITATE_WARDEN = 426;
    public static final int SOUND_LISTENING_ANGRY = 427;
    public static final int SOUND_ITEM_GIVEN = 428;
    public static final int SOUND_ITEM_TAKEN = 429;
    public static final int SOUND_DISAPPEARED = 430;
    public static final int SOUND_REAPPEARED = 431;
    public static final int SOUND_MILK_DRINK = 432;
    public static final int SOUND_FROGSPAWN_HATCHED = 433;
    public static final int SOUND_LAY_SPAWN = 434;
    public static final int SOUND_FROGSPAWN_BREAK = 435;
    public static final int SOUND_SONIC_BOOM = 436;
    public static final int SOUND_SONIC_CHARGE = 437;
    public static final int SOUND_ITEM_THROWN = 438;
    public static final int SOUND_RECORD_5 = 439;
    public static final int SOUND_CONVERT_TO_FROG = 440;
    public static final int SOUND_RECORD_PLAYING = 441;
    public static final int SOUND_ENCHANTING_TABLE_USE = 442;

    public static final int SOUND_PRESSURE_PLATE_CLICK_OFF = 448;
    public static final int SOUND_PRESSURE_PLATE_CLICK_ON = 449;
    public static final int SOUND_BUTTON_CLICK_OFF = 450;
    public static final int SOUND_BUTTON_CLICK_ON = 451;
    public static final int SOUND_DOOR_OPEN = 452;
    public static final int SOUND_DOOR_CLOSE = 453;
    public static final int SOUND_TRAPDOOR_OPEN = 454;
    public static final int SOUND_TRAPDOOR_CLOSE = 455;
    public static final int SOUND_FENCE_GATE_OPEN = 456;
    public static final int SOUND_FENCE_GATE_CLOSE = 457;
    public static final int SOUND_INSERT = 458;
    public static final int SOUND_PICKUP = 459;
    public static final int SOUND_INSERT_ENCHANTED = 460;
    public static final int SOUND_PICKUP_ENCHANTED = 461;
    public static final int SOUND_BRUSH = 462;
    public static final int SOUND_BRUSH_COMPLETED = 463;
    public static final int SOUND_SHATTER_DECORATED_POT = 464;
    public static final int SOUND_BREAK_DECORATED_POD = 465;
    public static final int SOUND_SNIFFER_EGG_CRACK = 466;
    public static final int SOUND_SNIFFER_EGG_HATCHED = 467;
    public static final int SOUND_WAXED_SIGN_INTERACT_FAIL = 468;
    public static final int SOUND_RECORD_RELIC = 469;
    public static final int SOUND_BUMP = 470;
    public static final int SOUND_PUMPKIN_CARVE = 471;
    public static final int SOUND_CONVERT_HUSK_TO_ZOMBIE = 472;
    public static final int SOUND_PIG_DEATH = 473;
    public static final int SOUND_HOGLIN_CONVERT_TO_ZOMBIE = 474;
    public static final int SOUND_AMBIENT_UNDERWATER_ENTER = 475;
    public static final int SOUND_AMBIENT_UNDERWATER_EXIT = 476;
    public static final int SOUND_BOTTLE_FILL = 477;
    public static final int SOUND_BOTTLE_EMPTY = 478;
    public static final int SOUND_CRAFTER_CRAFT = 479;
    public static final int SOUND_CRAFTER_FAILED = 480;
    public static final int SOUND_DECORATED_POT_INSERT = 481;
    public static final int SOUND_DECORATED_POT_INSERT_FAILED = 482;
    public static final int SOUND_CRAFTER_DISABLE_SLOT = 483;
    public static final int SOUND_TRIAL_SPAWNER_OPEN_SHUTTER = 484;
    public static final int SOUND_TRIAL_SPAWNER_EJECT_ITEM = 485;
    public static final int SOUND_TRIAL_SPAWNER_DETECT_PLAYER = 486;
    public static final int SOUND_TRIAL_SPAWNER_SPAWN_MOB = 487;
    public static final int SOUND_TRIAL_SPAWNER_CLOSE_SHUTTER = 488;
    public static final int SOUND_TRIAL_SPAWNER_AMBIENT = 489;
    public static final int SOUND_COPPER_BULB_ON = 490;
    public static final int SOUND_COPPER_BULB_OFF = 491;
    public static final int SOUND_AMBIENT_IN_AIR = 492;
    public static final int SOUND_WIND_BURST = 493;
    public static final int SOUND_IMITATE_BREEZE = 494;
    public static final int SOUND_ARMADILLO_BRUSH = 495;
    public static final int SOUND_ARMADILLO_SCUTE_DROP = 496;
    public static final int SOUND_EQUIP_WOLF = 497;
    public static final int SOUND_UNEQUIP_WOLF = 498;
    public static final int SOUND_REFLECT = 499;
    /**
     * @since v662
     */
    public static final int SOUND_VAULT_OPEN_SHUTTER = 500;
    /**
     * @since v662
     */
    public static final int SOUND_VAULT_CLOSE_SHUTTER = 501;
    /**
     * @since v662
     */
    public static final int SOUND_VAULT_EJECT_ITEM = 502;
    /**
     * @since v662
     */
    public static final int SOUND_VAULT_INSERT_ITEM = 503;
    /**
     * @since v662
     */
    public static final int SOUND_VAULT_INSERT_ITEM_FAIL = 504;
    /**
     * @since v662
     */
    public static final int SOUND_VAULT_AMBIENT = 505;
    /**
     * @since v662
     */
    public static final int SOUND_VAULT_ACTIVATE = 506;
    /**
     * @since v662
     */
    public static final int SOUND_VAULT_DEACTIVATE = 507;
    /**
     * @since v662
     */
    public static final int SOUND_HURT_REDUCED = 508;
    /**
     * @since v662
     */
    public static final int SOUND_WIND_CHARGE_BURST = 509;
    /**
     * @since v712
     */
    public static final int SOUND_IMITATE_BOGGED = 510;
    /**
     * @since v671
     */
    public static final int SOUND_ARMOR_CRACK_WOLF = 511;
    /**
     * @since v671
     */
    public static final int SOUND_ARMOR_BREAK_WOLF = 512;
    /**
     * @since v671
     */
    public static final int SOUND_ARMOR_REPAIR_WOLF = 513;
    /**
     * @since v671
     */
    public static final int SOUND_MACE_SMASH_AIR = 514;
    /**
     * @since v671
     */
    public static final int SOUND_MACE_SMASH_GROUND = 515;
    /**
     * @since v671
     */
    public static final int SOUND_MACE_SMASH_HEAVY_GROUND = 520;
    /**
     * @since v685
     */
    public static final int SOUND_TRAIL_SPAWNER_CHARGE_ACTIVATE = 516;
    /**
     * @since v685
     */
    public static final int SOUND_TRAIL_SPAWNER_AMBIENT_OMINOUS = 517;
    /**
     * @since v685
     */
    public static final int SOUND_OMINOUS_ITEM_SPAWNER_SPAWN_ITEM = 518;
    /**
     * @since v685
     */
    public static final int SOUND_OMINOUS_BOTTLE_END_USE = 519;
    /**
     * @since v685
     */
    public static final int SOUND_OMINOUS_ITEM_SPAWNER_SPAWN_ITEM_BEGIN = 521;
    /**
     * @since v685
     */
    public static final int SOUND_APPLY_EFFECT_BAD_OMEN = 523;
    /**
     * @since v685
     */
    public static final int SOUND_APPLY_EFFECT_RAID_OMEN = 524;
    /**
     * @since v685
     */
    public static final int SOUND_APPLY_EFFECT_TRIAL_OMEN = 525;
    /**
     * @since v685
     */
    public static final int SOUND_OMINOUS_ITEM_SPAWNER_ABOUT_TO_SPAWN_ITEM = 526;
    /**
     * @since v685
     */
    public static final int SOUND_RECORD_CREATOR = 527;
    /**
     * @since v685
     */
    public static final int SOUND_RECORD_CREATOR_MUSIC_BOX = 528;
    /**
     * @since v685
     */
    public static final int SOUND_RECORD_PRECIPICE = 529;
    /**
     * @since v712
     */
    public static final int SOUND_VAULT_REJECT_REWARDED_PLAYER = 530;
    /**
     * @since v729
     */
    public static final int SOUND_IMITATE_DROWNED = 531;
    /**
     * @since v729
     */
    public static final int SOUND_BUNDLE_INSERT_FAILED = 533;
    /**
     * @since v766
     */
    public static final int SOUND_IMITATE_CREAKING = 532;
    /**
     * @since v766
     */
    public static final int SOUND_SPONGE_ABSORB = 534;
    /**
     * @since v766
     */
    public static final int SOUND_BLOCK_CREAKING_HEART_TRAIL = 536;
    /**
     * @since v766
     */
    public static final int SOUND_CREAKING_HEART_SPAWN = 537;
    /**
     * @since v766
     */
    public static final int SOUND_ACTIVATE = 538;
    /**
     * @since v766
     */
    public static final int SOUND_DEACTIVATE = 539;
    /**
     * @since v766
     */
    public static final int SOUND_FREEZE = 540;
    /**
     * @since v766
     */
    public static final int SOUND_UNFREEZE = 541;
    /**
     * @since v766
     */
    public static final int SOUND_OPEN = 542;
    /**
     * @since v766
     */
    public static final int SOUND_OPEN_LONG = 543;
    /**
     * @since v766
     */
    public static final int SOUND_CLOSE = 544;
    /**
     * @since v766
     */
    public static final int SOUND_CLOSE_LONG = 545;
    /**
     * @since v800
     */
    public static final int SOUND_IMITATE_PHANTOM = 546;
    /**
     * @since v800
     */
    public static final int SOUND_IMITATE_ZOGLIN = 547;
    /**
     * @since v800
     */
    public static final int SOUND_IMITATE_GUARDIAN = 548;
    /**
     * @since v800
     */
    public static final int SOUND_IMITATE_RAVAGER = 549;
    /**
     * @since v800
     */
    public static final int SOUND_IMITATE_PILLAGER = 550;
    /**
     * @since v800
     */
    public static final int SOUND_PLACE_IN_WATER = 551;
    /**
     * @since v800
     */
    public static final int SOUND_STATE_CHANGE = 552;
    /**
     * @since v800
     */
    public static final int SOUND_IMITATE_HAPPY_GHAST = 553;
    /**
     * @since v800
     */
    public static final int SOUND_UNEQUIP_GENERIC = 554;
    /**
     * @since v818
     */
    public static final int SOUND_RECORD_TEARS = 555;
    /**
     * @since v818
     */
    public static final int SOUND_THE_END_LIGHT_FLASH = 556;
    /**
     * @since v818
     */
    public static final int SOUND_LEAD_LEASH = 557;
    /**
     * @since v818
     */
    public static final int SOUND_LEAD_UNLEASH = 558;
    /**
     * @since v818
     */
    public static final int SOUND_LEAD_BREAK = 559;
    /**
     * @since v818
     */
    public static final int SOUND_UNSADDLE = 560;
    /**
     * @since v819
     */
    public static final int SOUND_RECORD_LAVA_CHICKEN = 561;
    /**
     * @since v827
     */
    public static final int SOUND_EQUIP_COPPER = 562;

    public static final int SOUND_UNDEFINED = Utils.dynamic(563);

    public int sound;
    public float x;
    public float y;
    public float z;
    public int extraData = -1;
    public String entityIdentifier;
    public boolean isBabyMob;
    public boolean isGlobal;
    public long entityUniqueId = -1;

    @Override
    public void decode() {
        this.sound = (int) this.getUnsignedVarInt();
        Vector3f v = this.getVector3f();
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.extraData = this.getVarInt();
        this.entityIdentifier = this.getString();
        this.isBabyMob = this.getBoolean();
        this.isGlobal = this.getBoolean();
        if (this.protocol >= ProtocolInfo.v1_21_70_24) {
            this.entityUniqueId = this.getLLong();
        }
    }

    @Override
    public void encode() {
        this.reset();
        this.putUnsignedVarInt(this.sound);
        this.putVector3f(this.x, this.y, this.z);
        this.putVarInt(this.extraData);
        this.putString(this.entityIdentifier);
        this.putBoolean(this.isBabyMob);
        this.putBoolean(this.isGlobal);
        if (this.protocol >= ProtocolInfo.v1_21_70_24) {
            this.putLLong(this.entityUniqueId);
        }
    }

    @Override
    public byte pid() {
        return NETWORK_ID;
    }
}
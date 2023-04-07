package com.goatee.tutorial.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import com.goatee.tutorial.extras.UIDodge;
import com.goatee.tutorial.packets.PacketRegistry;
import com.goatee.tutorial.scripted.Player;
import com.goatee.tutorial.scripted.PlayerStats;

import JinRyuu.JRMCore.JRMCoreH;
import JinRyuu.JRMCore.server.JGPlayerMP;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.scripted.CustomNPCsException;
import noppes.npcs.scripted.entity.ScriptDBCPlayer;
import noppes.npcs.scripted.entity.ScriptPlayer;

@Mixin(ScriptDBCPlayer.class)
public abstract class MixinScriptDBCPlayer<T extends EntityPlayerMP> {

	@SuppressWarnings("unchecked")
	public ScriptDBCPlayer<T> sdbc = ((ScriptDBCPlayer<T>) (Object) this); // works
	public T player = sdbc.player;

	// public NBTTagCompound nbt =
	// player.getEntityData().getCompoundTag("PlayerPersisted");

	@Unique
	public void exampleMethod(String str) {
		System.out.println(str);

	}

	@Unique
	@SuppressWarnings("rawtypes")
	public PlayerStats getPlayerStats() {
		System.out.println("uuid is");
		System.out.println(player.getUniqueID());
		return Player.getAllPlayerStats().get(player.getUniqueID());
	}

	@Unique
	public boolean isFlying() {

		return getPlayerStats().isFlying();
	}

	@Unique
	public void setFlight(boolean bo) {
		System.out.println("told client1");

		if (bo) {
			System.out.println("told client22");
			if (!isFlying()) {
				PacketRegistry.tellClient(player, 0);
				System.out.println("told client2");
			}
		} else {
			if (isFlying()) {
				PacketRegistry.tellClient(player, 0);
			}
		}

	}

	@Unique
	public int getMaxBody() {
		int[] PlyrAttrbts = JRMCoreH.PlyrAttrbts(player);
		byte pwr = JRMCoreH.getByte(player, "jrmcPwrtyp");
		byte rce = JRMCoreH.getByte(player, "jrmcRace");
		byte cls = JRMCoreH.getByte(player, "jrmcClass");
		int maxBody = JRMCoreH.stat(pwr, 2, PlyrAttrbts[2], rce, cls, 0.0F);

		return maxBody;
	}

	@Unique
	public int getMaxEnergy() {
		int[] PlyrAttrbts = JRMCoreH.PlyrAttrbts(player);
		byte pwr = JRMCoreH.getByte(player, "jrmcPwrtyp");
		byte rce = JRMCoreH.getByte(player, "jrmcRace");
		byte cls = JRMCoreH.getByte(player, "jrmcClass");
		int maxEnergy = JRMCoreH.stat(pwr, 5, PlyrAttrbts[5], rce, cls, JRMCoreH.SklLvl_KiBs(player, pwr));

		return maxEnergy;
	}

	@Unique
	public int getMaxStamina() {
		int[] PlyrAttrbts = JRMCoreH.PlyrAttrbts(player);
		byte pwr = JRMCoreH.getByte(player, "jrmcPwrtyp");
		byte rce = JRMCoreH.getByte(player, "jrmcRace");
		byte cls = JRMCoreH.getByte(player, "jrmcClass");
		int maxStam = JRMCoreH.stat(pwr, 3, PlyrAttrbts[2], rce, cls, 0.0F);

		return maxStam;
	}

	@Unique
	public String StusEffectsMe() {
		return JRMCoreH.StusEfctsMe();
	}

	@Unique
	public void changeDBCAnim(int i) {

		if (i == 1 || i == 2) {
			JRMCoreH.Anim(i);
		} else {
			throw new CustomNPCsException(
					"Invalid Animation ID : " + i + "\nValid IDs are: 1 for Flight \nand 2 for Standing",
					new Object[0]);
		}
	}

	@Unique
	public int[] getAllStats() {
		int[] stats = new int[6];
		NBTTagCompound nbt = player.getEntityData().getCompoundTag("PlayerPersisted");
		stats[0] = nbt.getInteger("jrmcStrI");
		stats[1] = nbt.getInteger("jrmcDexI");
		stats[2] = nbt.getInteger("jrmcCnsI");
		stats[3] = nbt.getInteger("jrmcWilI");
		stats[4] = nbt.getInteger("jrmcIntI");
		stats[5] = nbt.getInteger("jrmcCncI");

		return stats;
	}

	@Unique
	public int getFullStat(int statid) { // str 0, dex 1, con 2, wil 3, mnd 4,
											// spi 5
		JGPlayerMP JG = new JGPlayerMP(player);
		NBTTagCompound nbt = player.getEntityData().getCompoundTag("PlayerPersisted");
		JG.setNBT(nbt);
		return JG.getAttribute(statid);
	}

	@Unique
	public int[] getAllFullStats() {
		int[] fullstats = new int[6];
		JGPlayerMP JG = new JGPlayerMP(player);
		NBTTagCompound nbt = player.getEntityData().getCompoundTag("PlayerPersisted");
		JG.setNBT(nbt);
		fullstats[0] = JG.getAttribute(0);
		fullstats[1] = JG.getAttribute(1);
		fullstats[2] = nbt.getInteger("jrmcCnsI");
		fullstats[3] = JG.getAttribute(3);
		fullstats[4] = nbt.getInteger("jrmcIntI");
		fullstats[5] = nbt.getInteger("jrmcCncI");

		return fullstats;
	}

	@Unique
	public String getAllFormMasteries() {
		return JRMCoreH.getFormMasteryData(player);

	}

	@Unique
	public String getFormName(int race, int form) {
		CustomNPCsException c = new CustomNPCsException("Invalid \nform ID for race " + JRMCoreH.Races[race],
				new Object[0]);
		CustomNPCsException r = new CustomNPCsException(
				"Invalid Race : \nValid Races are \n0 Human, 1 Saiyan\n 2 Half-Saiyan, 3 Namekian\n4 Arcosian, 5 Majin",
				new Object[1]);
		if (form >= 0) {
			if (race > 5 || race < 0) {
				System.out.println(race);
				throw r;
			} else {
				switch (race) {
				case 0:
				case 3:
					if (form > 3) {
						throw c;
					}
					break;
				case 1:
				case 2:
					if (form > 15) {
						throw c;
					}
					break;

				case 4:
					if (form > 7) {
						throw c;
					}
					break;
				case 5:
					if (form > 4) {
						throw c;
					}
					break;
				}
			}

		} else {
			throw c;
		}

		return JRMCoreH.trans[race][form];

	}

	@Unique
	public String getCurrentFormName() {
		int race = sdbc.getRace();
		int form = (int) sdbc.getForm();
		return getFormName(race, form);
	}

	@Unique
	public void changeFormMastery(ScriptPlayer<T> Player, String formName, double amount, boolean add) {
		// ScriptDBCPlayer<T> playerSDBC = new ScriptDBCPlayer<T>((T) player);
		// player =playerSDBC.player;
		T player = Player.player;
		JGPlayerMP JG = new JGPlayerMP(player);
		NBTTagCompound nbt = player.getEntityData().getCompoundTag("PlayerPersisted");
		JG.setNBT(nbt);
		int race = (int) JG.getRace();

		boolean found = false;
		boolean isKaiokenOn = false;
		boolean isMysticOn = false;
		boolean isUltraInstinctOn = false;
		boolean isGoDOn = false;
		int state = 0;
		int state2 = 0;
		double amountKK = 0;
		System.out.println("Form name is " + formName);

		switch (formName.toLowerCase()) {
		case "kaioken":
			found = true;
			isKaiokenOn = true;
			amountKK = amount;
			break;
		case "mystic":
			found = true;
			isMysticOn = true;

		case "ultrainstict":
			found = true;
			isUltraInstinctOn = true;
			break;
		case "godofdestruction":
			found = true;
			isGoDOn = true;
			break;

		}
		if (found) {
			String nonracial = nbt.getString("jrmcFormMasteryNonRacial");
			String masteries[] = nonracial.split(";");
			int foundatindex;
			String newnonracial = "";
			for (int i = 0; i < masteries.length; i++) {
				if (masteries[i].toLowerCase().contains(formName.toLowerCase())) {
					foundatindex = i;
					String[] masteryvalues = masteries[i].split(",");
					double masteryvalue = Double.parseDouble(masteryvalues[1]);
					if (add) {
						masteryvalue += amount;
					} else {
						masteryvalue = amount;
					}
					System.out.println("masteryvalue is " + masteryvalue);
					String newvalue = Double.toString(masteryvalue);
					switch (foundatindex) {
					case 0:
						newnonracial = masteryvalues[0] + "," + newvalue + ";" + masteries[1] + ";" + masteries[2] + ";"
								+ masteries[3];
						break;
					case 1:
						newnonracial = masteries[0] + ";" + masteryvalues[0] + "," + newvalue + ";" + masteries[2] + ";"
								+ masteries[3];
						break;
					case 2:
						newnonracial = masteries[0] + ";" + masteries[1] + ";" + masteryvalues[0] + "," + newvalue + ";"
								+ masteries[3];
						;
						break;
					case 3:
						newnonracial = masteries[0] + ";" + masteries[1] + ";" + masteries[2] + ";" + masteryvalues[0]
								+ "," + newvalue;
						break;
					}
					System.out.println("newnonracial is " + newnonracial);

					nbt.setString("jrmcFormMasteryNonRacial", newnonracial);
					break;
				}
			}
		}

		else if (!found) {
			for (int i = 0; i < JRMCoreH.trans[race].length; i++) {
				if (JRMCoreH.trans[race][i].equalsIgnoreCase(formName)) {
					state = i;
					found = true;
					JRMCoreH.changeFormMasteriesValue(player, amount, amountKK, add, race, state, state2, isKaiokenOn,
							isMysticOn, isUltraInstinctOn, isGoDOn, -1);

				}

			}

		}
		if (!found) {
			throw new CustomNPCsException(
					"Invalid \nform name. For non racial form names, use Kaioken, Mystic, UltraInstict and GodOfDestruction. For racial \nform names, check getFormName(int race, int form) or getCurrentFormName()",
					new Object[2]);
		}

	}

	@Unique
	public double getFormMasteryValue(String formName) {
		return getFormMasteryValue(sdbc, formName);
	}

	@Unique
	public double getFormMasteryValue(ScriptPlayer<T> Player, String formName) {

		// ScriptDBCPlayer<T> playerSDBC = new ScriptDBCPlayer<T>(player);
		T player = Player.player;

		JGPlayerMP JG = new JGPlayerMP(player);
		NBTTagCompound nbt = player.getEntityData().getCompoundTag("PlayerPersisted");
		JG.setNBT(nbt);
		int race = (int) JG.getRace();
		boolean found = false;
		double valuetoreturn = -1.0;
		System.out.println("Form name is " + formName);

		switch (formName.toLowerCase()) {
		case "kaioken":
			found = true;
			break;
		case "mystic":
			found = true;
		case "ultrainstict":
			found = true;
			break;
		case "godofdestruction":
			found = true;
			break;

		}
		if (found) {
			String nonracial = nbt.getString("jrmcFormMasteryNonRacial");
			String masteries[] = nonracial.split(";");
			for (int i = 0; i < masteries.length; i++) {
				if (masteries[i].toLowerCase().contains(formName.toLowerCase())) {
					String[] masteryvalues = masteries[i].split(",");
					double masteryvalue = Double.parseDouble(masteryvalues[1]);
					System.out.println("Form name 2 is " + formName);

					valuetoreturn = masteryvalue;
					System.out.println("valuetoreturn is " + valuetoreturn);
					return valuetoreturn;

				}
			}
		}

		else if (!found) {

			String racial = nbt.getString("jrmcFormMasteryRacial_" + JRMCoreH.Races[race]);
			String masteries[] = racial.split(";");
			for (int i = 0; i < masteries.length; i++) {
				if (masteries[i].toLowerCase().contains(formName.toLowerCase())) {
					String[] masteryvalues = masteries[i].split(",");
					double masteryvalue = Double.parseDouble(masteryvalues[1]);
					found = true;
					System.out.println("masteryvalue is " + masteryvalue);

					valuetoreturn = masteryvalue;
					System.out.println("valuetoreturn2 is " + valuetoreturn);
					return valuetoreturn;
				}
			}

		}
		if (!found) {
			throw new CustomNPCsException(
					"Invalid \nform name. For non racial form names, use Kaioken, Mystic, UltraInstict and GodOfDestruction. For racial \nform names, check getFormName(int race, int form) or getCurrentFormName()",
					new Object[2]);
		}
		if (valuetoreturn == -1.0) {
			throw new CustomNPCsException("Form Mastery value is -1.0", new Object[3]);
		}
		return valuetoreturn;
	}

	@Unique
	public void addFusionFormMasteries(ScriptPlayer<T> Controller, ScriptPlayer<T> Spectator,
			boolean multiplyaddedStats, double multiValue) {
		double multi = multiValue;
		if (multi == 0) {
			multi = 1.0;
		}
		if (!multiplyaddedStats) {
			multi = 1.0;
		}
		// ScriptDBCPlayer<T> controllerSDBC = new ScriptDBCPlayer<T>(controller);
		T controller = Controller.player;
		NBTTagCompound cnbt = controller.getEntityData().getCompoundTag("PlayerPersisted");
		int crace = Controller.getDBCPlayer().getRace();
		String cracial = cnbt.getString("jrmcFormMasteryRacial_" + JRMCoreH.Races[crace]);
		String cnonracial = cnbt.getString("jrmcFormMasteryNonRacial");

		// ScriptDBCPlayer<T> spectatorSDBC = new ScriptDBCPlayer<T>(spectator);
		T spectator = Spectator.player;
		NBTTagCompound snbt = spectator.getEntityData().getCompoundTag("PlayerPersisted");
		int srace = Spectator.getDBCPlayer().getRace();
		String sracial = snbt.getString("jrmcFormMasteryRacial_" + JRMCoreH.Races[srace]);
		String snonracial = snbt.getString("jrmcFormMasteryNonRacial");

		boolean samerace = false;
		if (crace == srace) {
			samerace = true;
		}
		String[] cnonracialmasteries = cnonracial.split(";");
		String[] snonracialmasteries = snonracial.split(";");
		int lengthnr = cnonracialmasteries.length;

		String[] cracialmasteries = cracial.split(";");
		String[] sracialmasteries = sracial.split(";");
		int lengthr = cracialmasteries.length;

		String newmasteriesnr = "";
		String newmasteriesr = "";

		boolean done = false;
		if (samerace) {
			for (int i = 0; i < lengthnr; i++) {
				String[] cmasteryvaluesnr = cnonracialmasteries[i].split(",");
				String[] smasteryvaluesnr = snonracialmasteries[i].split(",");

				// name , string( double(value1)+double(value2) ) ;
				newmasteriesnr += cmasteryvaluesnr[0] + "," + Double.toString(
						(Double.parseDouble(cmasteryvaluesnr[1]) + Double.parseDouble(smasteryvaluesnr[1])) * multi)
						+ ";";

			}
			for (int i = 0; i < lengthr; i++) {
				String[] cmasteryvaluesr = cracialmasteries[i].split(",");
				String[] smasteryvaluesr = sracialmasteries[i].split(",");

				newmasteriesr += cmasteryvaluesr[0] + "," + Double.toString(
						(Double.parseDouble(cmasteryvaluesr[1]) + Double.parseDouble(smasteryvaluesr[1])) * multi)
						+ ";";
				done = true;
			}

		} else if (!samerace) {
			multi = multiValue;
			for (int i = 0; i < lengthnr; i++) {
				String[] cmasteryvaluesnr = cnonracialmasteries[i].split(",");
				newmasteriesnr += cmasteryvaluesnr[0] + ","
						+ Double.toString(Double.parseDouble(cmasteryvaluesnr[1]) * multi) + ";";
			}
			for (int i = 0; i < lengthr; i++) {
				String[] cmasteryvaluesr = cracialmasteries[i].split(",");

				newmasteriesr += cmasteryvaluesr[0] + ","
						+ Double.toString(Double.parseDouble(cmasteryvaluesr[1]) * multi) + ";";
				done = true;
			}

		}
		newmasteriesnr.substring(0, lengthnr - 2);
		cnbt.setString("jrmcFormMasteryNonRacial", newmasteriesnr);

		newmasteriesr.substring(0, lengthr - 2);
		cnbt.setString("jrmcFormMasteryRacial_" + JRMCoreH.Races[crace], newmasteriesr);

		if (!done) {
			throw new CustomNPCsException("Invalid arguments", new Object[0]);
		}
	}

	@Unique
	public void doUIDodge(byte chance) {
		UIDodge.UltraInstinctDodge(player, (byte) 1, chance);
	}

}

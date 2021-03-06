package gregtech.loaders.oreprocessing;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.interfaces.internal.IGT_RecipeAdder;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class ProcessingGemChipped implements gregtech.api.interfaces.IOreRecipeRegistrator
{
  public ProcessingGemChipped()
  {
    OrePrefixes.gemChipped.add(this);
  }
  
  public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
  {
    if (aMaterial.mFuelPower > 0) GT_Values.RA.addFuel(GT_Utility.copyAmount(1L, new Object[] { aStack }), null, aMaterial.mFuelPower / 2, aMaterial.mFuelType);
    if (!aMaterial.contains(gregtech.api.enums.SubTag.NO_WORKING)) GT_Values.RA.addLatheRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.bolt, aMaterial, 1L), GT_OreDictUnificator.get(OrePrefixes.dustTiny, aMaterial, 1L), (int)Math.max(aMaterial.getMass(), 1L), 8);
  }
}


/* Location:              F:\Torrent\minecraft\jdgui test\gregtech_1.7.10-5.07.07-dev.jar!\gregtech\loaders\oreprocessing\ProcessingGemChipped.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */
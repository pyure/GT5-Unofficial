package gregtech.common.tileentities.machines.multi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Textures;
import gregtech.api.gui.GT_GUIContainer_MultiMachine;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicMachine_GT_Recipe;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;

public class GT_MetaTileEntity_ProcessingArray extends GT_MetaTileEntity_MultiBlockBase{
	
				  public GT_MetaTileEntity_ProcessingArray(int aID, String aName, String aNameRegional)
				  {
				    super(aID, aName, aNameRegional);
				  }
				  
				  public GT_MetaTileEntity_ProcessingArray(String aName)
				  {
				    super(aName);
				  }
				  
				  public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity)
				  {
				    return new GT_MetaTileEntity_ProcessingArray(this.mName);
				  }
				  
				  public String[] getDescription()
				  {
				    return new String[] { "Controller Block for the Processing Array", 
				    					  "Size: 3x3x3 (Hollow)", 
				    					  "Controller (front centered)", 
				    					  "1x Input (anywhere)", 
				    					  "1x Output (anywhere)", 
				    					  "1x Energy Hatch (anywhere)", 
				    					  "1x Maintenance Hatch (anywhere)", 
				    					  "Robust Tungstensteel Casings for the rest (16 at least!)",
				    					  "Place up to 16 Single Block GT Machines into the GUI Inventory",
				    					  "They will work the same way as placed directly in world"};
				  }
				  
				  public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone)
				  {
				    if (aSide == aFacing) {
				      return new ITexture[] { Textures.BlockIcons.CASING_BLOCKS[48], new GT_RenderedTexture(aActive ? Textures.BlockIcons.OVERLAY_FRONT_LARGE_BOILER_ACTIVE : Textures.BlockIcons.OVERLAY_FRONT_LARGE_BOILER) };
				    }
				    return new ITexture[] { Textures.BlockIcons.CASING_BLOCKS[48] };
				  }
				  
				  public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity)
				  {
				    return new GT_GUIContainer_MultiMachine(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "VacuumFreezer.png");
				  }
				  
				  public GT_Recipe.GT_Recipe_Map getRecipeMap()
				  {
									if(mInventory[1]==null)return null;
									String tmp = mInventory[1].getUnlocalizedName().replaceAll("gt.blockmachines.basicmachine.", "");
										if(tmp.startsWith("centrifuge")){
											return GT_Recipe.GT_Recipe_Map.sCentrifugeRecipes;
										}else if(tmp.startsWith("electrolyzer")){
											return GT_Recipe.GT_Recipe_Map.sElectrolyzerRecipes;
										}else if(tmp.startsWith("alloysmelter")){
											return GT_Recipe.GT_Recipe_Map.sAlloySmelterRecipes;
										}else if(tmp.startsWith("assembler")){
											return GT_Recipe.GT_Recipe_Map.sAssemblerRecipes;
										}else if(tmp.startsWith("compressor")){
											return GT_Recipe.GT_Recipe_Map.sCompressorRecipes;
										}else if(tmp.startsWith("extractor")){
											return GT_Recipe.GT_Recipe_Map.sExtractorRecipes;
										}else if(tmp.startsWith("macerator")){
											return GT_Recipe.GT_Recipe_Map.sMaceratorRecipes;
										}else if(tmp.startsWith("recycler")){
											return GT_Recipe.GT_Recipe_Map.sRecyclerRecipes;
										}else if(tmp.startsWith("thermalcentrifuge")){
											return GT_Recipe.GT_Recipe_Map.sThermalCentrifugeRecipes;
										}else if(tmp.startsWith("orewasher")){
											return GT_Recipe.GT_Recipe_Map.sOreWasherRecipes;
										}else if(tmp.startsWith("chemicalreactor")){
											return GT_Recipe.GT_Recipe_Map.sChemicalRecipes;
										}else if(tmp.startsWith("chemicalbath")){
											return GT_Recipe.GT_Recipe_Map.sChemicalBathRecipes;
										}else if(tmp.startsWith("electromagneticseparator")){
											return GT_Recipe.GT_Recipe_Map.sElectroMagneticSeparatorRecipes;
										}else if(tmp.startsWith("autoclave")){
											return GT_Recipe.GT_Recipe_Map.sAutoclaveRecipes;
										}else if(tmp.startsWith("mixer")){
											return GT_Recipe.GT_Recipe_Map.sMixerRecipes;
										}else if(tmp.startsWith("hammer")){
											return GT_Recipe.GT_Recipe_Map.sHammerRecipes;
										}else if(tmp.startsWith("sifter")){
											return GT_Recipe.GT_Recipe_Map.sSifterRecipes;
										}else if(tmp.startsWith("extruder")){
											return GT_Recipe.GT_Recipe_Map.sExtruderRecipes;
										}else if(tmp.startsWith("laserengraver")){
											return GT_Recipe.GT_Recipe_Map.sLaserEngraverRecipes;
										}else if(tmp.startsWith("bender")){
											return GT_Recipe.GT_Recipe_Map.sBenderRecipes;
										}
				    return null;
				  }
				  
				  public boolean isCorrectMachinePart(ItemStack aStack)
				  {
										if(aStack!=null&&aStack.getUnlocalizedName().startsWith("gt.blockmachines.basicmachine.")){
					    return true;}
									return false;
				  }
				  
				  public boolean isFacingValid(byte aFacing)
				  {
				    return aFacing > 1;
				  }
				  
								GT_Recipe mLastRecipe;
				  public boolean checkRecipe(ItemStack aStack){
									if(!isCorrectMachinePart(mInventory[1])){return false;}
									GT_Recipe.GT_Recipe_Map map = getRecipeMap();
									if(map==null){return false;}
				    ArrayList<ItemStack> tInputList = getStoredInputs();
				      int tTier = 0;
									if(mInventory[1].getUnlocalizedName().endsWith("1")){tTier=1;}
									if(mInventory[1].getUnlocalizedName().endsWith("2")){tTier=2;}
									if(mInventory[1].getUnlocalizedName().endsWith("3")){tTier=3;}
									if(mInventory[1].getUnlocalizedName().endsWith("4")){tTier=4;}
									if(mInventory[1].getUnlocalizedName().endsWith("5")){tTier=5;}
									    for (int i = 0; i < tInputList.size() - 1; i++) {
									      for (int j = i + 1; j < tInputList.size(); j++) {
									        if (GT_Utility.areStacksEqual((ItemStack)tInputList.get(i), (ItemStack)tInputList.get(j))) {
									          if (((ItemStack)tInputList.get(i)).stackSize >= ((ItemStack)tInputList.get(j)).stackSize)
									          {
									            tInputList.remove(j--);
									          }
									          else
									          {
									            tInputList.remove(i--); break;
									          }
									        }
									      }
									    }
									    ItemStack[] tInputs = (ItemStack[])Arrays.copyOfRange(tInputList.toArray(new ItemStack[tInputList.size()]), 0, 2);
									    
									    ArrayList<FluidStack> tFluidList = getStoredFluids();
									    for (int i = 0; i < tFluidList.size() - 1; i++) {
									      for (int j = i + 1; j < tFluidList.size(); j++) {
									        if (GT_Utility.areFluidsEqual((FluidStack)tFluidList.get(i), (FluidStack)tFluidList.get(j))) {
									          if (((FluidStack)tFluidList.get(i)).amount >= ((FluidStack)tFluidList.get(j)).amount)
									          {
									             tFluidList.remove(j--);
									           }
									           else
									           {
									             tFluidList.remove(i--); break;
									           }
									         }
									       }
									     }
									FluidStack[] tFluids = (FluidStack[])Arrays.copyOfRange(tFluidList.toArray(new FluidStack[tInputList.size()]), 0, 1);
									if(tInputList.size() > 0 || tFluids.length>0){
				      GT_Recipe tRecipe = map.findRecipe(getBaseMetaTileEntity(), mLastRecipe, false, gregtech.api.enums.GT_Values.V[tTier], tFluids, tInputs);
				      if (tRecipe != null) {
										mLastRecipe = tRecipe;
										this.mEUt = 0;
										this.mOutputItems = null;
										this.mOutputFluids = null;
										this.mMaxProgresstime = tRecipe.mDuration;
										int machines = Math.min(16,mInventory[1].stackSize);
										this.mEfficiency = (10000 - (getIdealStatus() - getRepairStatus()) * 1000);
										this.mEfficiencyIncrease = 10000;
										int i = 0;
										for(;i<machines;i++){
											if(!tRecipe.isRecipeInputEqual(true, tFluids, tInputs))break;
										}
										      if (tRecipe.mEUt <= 16)
										       {
										        this.mEUt = (tRecipe.mEUt * (1 << tTier - 1) * (1 << tTier - 1));
										        this.mMaxProgresstime = (tRecipe.mDuration / (1 << tTier - 1));
										      }
										       else
										      {
										       this.mEUt = tRecipe.mEUt;
										       this.mMaxProgresstime = tRecipe.mDuration;
										       while (this.mEUt <= gregtech.api.enums.GT_Values.V[(tTier - 1)])
										      {
										      this.mEUt *= 4;
										      this.mMaxProgresstime /= 2;
										   }
										 }
										 this.mEUt*=i;     
										if (this.mEUt > 0) {
										  this.mEUt = (-this.mEUt);
										}
										ItemStack[] tOut = new ItemStack[tRecipe.mOutputs.length]; 
										for(int h = 0;h<tRecipe.mOutputs.length;h++){
											tOut[h] = tRecipe.getOutput(h).copy();
											tOut[h].stackSize =0;
										}FluidStack tFOut=null;
										if(tRecipe.getFluidOutput(0)!=null) tFOut = tRecipe.getFluidOutput(0).copy();
										for(int f =0; f < tOut.length ; f++){
											if(tRecipe.mOutputs[f]!=null&&tOut[f]!=null){
												for(int g =0;g<i;g++){
													if (getBaseMetaTileEntity().getRandomNumber(10000) < tRecipe.getOutputChance(f)) tOut[f].stackSize +=  tRecipe.mOutputs[f].stackSize;
												}
											}
										}
											if(tFOut!=null){
												int tSize = tFOut.amount;
												tFOut.amount = tSize * i;
											}
										this.mMaxProgresstime = Math.max(1, this.mMaxProgresstime);
										List<ItemStack> overStacks = new ArrayList<ItemStack>();
										for(int f =0; f < tOut.length ; f++){
											if(tOut[f].getMaxStackSize()<tOut[f].stackSize){
												while(tOut[f].getMaxStackSize()<tOut[f].stackSize){
													ItemStack tmp = tOut[f].copy();
													tmp.stackSize = tmp.getMaxStackSize();
													tOut[f].stackSize = tOut[f].stackSize - tOut[f].getMaxStackSize();
													overStacks.add(tmp);
												}
											}
										}
										if(overStacks.size()>0){
											ItemStack[] tmp = new ItemStack[overStacks.size()];
											tmp = overStacks.toArray(tmp);
											tOut = ArrayUtils.addAll(tOut, tmp);
										}
										this.mOutputItems = tOut;
										this.mOutputFluids = new FluidStack[]{tFOut};
										updateSlots();
				          return true;
				      }
								}
				    return false;
				  }
				  
				  public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack)
				  {
				    int xDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetX;int zDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetZ;
				    if (!aBaseMetaTileEntity.getAirOffset(xDir, 0, zDir)) {
				      return false;
				    }
				    int tAmount = 0;
				    for (int i = -1; i < 2; i++) {
				      for (int j = -1; j < 2; j++) {
				        for (int h = -1; h < 2; h++) {
				          if ((h != 0) || (((xDir + i != 0) || (zDir + j != 0)) && ((i != 0) || (j != 0))))
				          {
				            IGregTechTileEntity tTileEntity = aBaseMetaTileEntity.getIGregTechTileEntityOffset(xDir + i, h, zDir + j);
				            if ((!addMaintenanceToMachineList(tTileEntity, 48)) && (!addInputToMachineList(tTileEntity, 48)) && (!addOutputToMachineList(tTileEntity, 48)) && (!addEnergyInputToMachineList(tTileEntity, 48)))
				            {
				              if (aBaseMetaTileEntity.getBlockOffset(xDir + i, h, zDir + j) != GregTech_API.sBlockCasings4) {
				                return false;
				              }
				              if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, h, zDir + j) != 0) {
				                return false;
				              }
				              tAmount++;
				            }
				          }
				        }
				      }
				    }
				    return tAmount >= 16;
				  }
				  
				  public int getMaxEfficiency(ItemStack aStack){return 10000;}
				  public int getPollutionPerTick(ItemStack aStack){return 0;}
				  public int getDamageToComponent(ItemStack aStack){return 0;}
				  public int getAmountOfOutputs(){return 1;}
				  public boolean explodesOnComponentBreak(ItemStack aStack){return false;}
				}
package FarnAnnoyanceFix;

public enum EnumOptionsAnnoyanceFix {
	stonestair("Improved_CobbleStoneStair", "Make cobbleStone stair drop themselves and allowed you to place upside", true),
	woodstair("Improved_Wooden_Stair", "Make wooden stair drop themselves and allowed you to place upside", true),
	slab("Improved_slab_Placement", "1.3.1/Modern Slab Placement", true),
	fencefix("Fence_Fix", "Make you able to place fence without solid blocks underneath", true),
	boatfix("Improved_boat", "Make boat drop themselves , make it no longer break when collide with something", true),
	doublestairsrecipe("Better_stair_recipe", "Make stair crafting recipe give you 8 stairs instead of 4 stairs", true),
	doubleslabsrecipe("Better_slab_recipe", "Make slab crafting recipe give you 6 slabs instead of 3 slab", true),
	farmland("No_Tramped_Farmland", "Make farmland no longer get trampled by walking", true);

	public boolean enumBoolean = true;
	public final String enumName;
	public final String enumDesc;

	private EnumOptionsAnnoyanceFix(String string3, String string1, boolean z5) {
		this.enumName = string3;
		this.enumDesc = string1;
		this.enumBoolean = z5;
	}
}

package net.themcbrothers.interiormod.compat.jei;

/**
 * @author TheMCBrothers
 */
public class FurnitureCraftingRecipeCategory/* implements IRecipeCategory<ICraftingRecipe> {
    private static final ResourceLocation CRAFTING_TABLE_GUI_TEXTURES = new ResourceLocation("textures/gui/container/crafting_table.png");

    public static final int width = 116;
    public static final int height = 54;

    private final ITextComponent localizedText;
    private final IDrawable background, icon;
    private final ICraftingGridHelper craftingGridHelper;

    public FurnitureCraftingRecipeCategory(IGuiHelper guiHelper) {
        this.localizedText = new TranslationTextComponent("container.interiormod.furniture_crafting");
        this.background = guiHelper.createDrawable(CRAFTING_TABLE_GUI_TEXTURES, 29, 16, width, height);
        this.icon = guiHelper.createDrawableIngredient(new ItemStack(InteriorBlocks.FURNITURE_WORKBENCH));
        this.craftingGridHelper = guiHelper.createCraftingGridHelper(1);
    }

    @Override
    public ResourceLocation getUid() {
        return InteriorMod.getId("furniture_crafting");
    }

    @Override
    public Class<? extends ICraftingRecipe> getRecipeClass() {
        return ICraftingRecipe.class;
    }

    @Override
    public String getTitle() {
        return getTitleAsTextComponent().getString();
    }

    @Override
    public ITextComponent getTitleAsTextComponent() {
        return this.localizedText;
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setIngredients(ICraftingRecipe recipe, IIngredients ingredients) {
        ingredients.setInputIngredients(recipe.getIngredients());
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, ICraftingRecipe recipe, IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

        guiItemStacks.init(0, false, 94, 18);

        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 3; ++x) {
                int index = 1 + x + (y * 3);
                guiItemStacks.init(index, true, x * 18, y * 18);
            }
        }

        List<List<ItemStack>> inputs = ingredients.getInputs(VanillaTypes.ITEM);
        List<List<ItemStack>> outputs = ingredients.getOutputs(VanillaTypes.ITEM);

        if (recipe instanceof ShapedRecipe) {
            craftingGridHelper.setInputs(guiItemStacks, inputs, ((ShapedRecipe) recipe).getRecipeWidth(), ((ShapedRecipe) recipe).getRecipeHeight());
        } else {
            craftingGridHelper.setInputs(guiItemStacks, inputs);
            recipeLayout.setShapeless();
        }

        guiItemStacks.set(0, outputs.get(0));
    }
}
*/ {
}
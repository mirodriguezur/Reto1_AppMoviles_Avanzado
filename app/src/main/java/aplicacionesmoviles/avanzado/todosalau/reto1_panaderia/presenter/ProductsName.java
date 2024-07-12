package aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.presenter;

public enum ProductsName {
    PAN_BRETON(ProductCategory.PANES),
    PAN_CON_CHOCOLATE(ProductCategory.PANES),
    PAN_DE_MOLDE(ProductCategory.PANES),
    PAN_EN_TRENZA(ProductCategory.PANES),
    PAN_HAMBURGUEZA(ProductCategory.PANES),
    PAN_MANCHEGO(ProductCategory.PANES),

    POSTRE_HELADO_TENTACION(ProductCategory.POSTRES),
    POSTRE_MAGDALENA(ProductCategory.POSTRES),
    POSTRE_MUFFINS(ProductCategory.POSTRES),
    POSTRE_ROLLS_CANELA(ProductCategory.POSTRES),
    POSTRE_TARTA_INTEGRAL(ProductCategory.POSTRES),
    POSTRE_TARTA_ZANAHORIA(ProductCategory.POSTRES),

    DONA_BACKED(ProductCategory.DONAS),
    DONA_CHOCOLATE_BLANCO(ProductCategory.DONAS),
    DONA_CHOCOLATE_FROSTED(ProductCategory.DONAS),
    DONA_COMBO_BOSTON(ProductCategory.DONAS),
    DONA_SPUDNUT(ProductCategory.DONAS),
    DONA_STRAWBERRY_FROSTED(ProductCategory.DONAS);

    private final ProductCategory category;

    ProductsName(ProductCategory category) {
        this.category = category;
    }

    public ProductCategory getCategory() {
        return category;
    }
}

package aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class InfoPartialOrder implements Parcelable {
    private String idProduct;
    private String category;
    private String nameProduct;
    private int amount;
    private int unitPrice;

    public InfoPartialOrder(String idProduct, String category, String nameProduct, int amount, int unitPrice) {
        this.idProduct = idProduct;
        this.category = category;
        this.nameProduct = nameProduct;
        this.amount = amount;
        this.unitPrice = unitPrice;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    // Implementación de Parcelable
    protected InfoPartialOrder(Parcel in) {
        idProduct = in.readString();
        category = in.readString();
        nameProduct = in.readString();
        amount = in.readInt();
        unitPrice = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int i) {
        dest.writeString(idProduct);
        dest.writeString(category);
        dest.writeString(nameProduct);
        dest.writeInt(amount);
        dest.writeInt(unitPrice);
    }

    public static final Creator<InfoPartialOrder> CREATOR = new Creator<InfoPartialOrder>() {
        @Override
        public InfoPartialOrder createFromParcel(Parcel in) {
            return new InfoPartialOrder(in);
        }

        @Override
        public InfoPartialOrder[] newArray(int size) {
            return new InfoPartialOrder[size];
        }
    };
}

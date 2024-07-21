package aplicacionesmoviles.avanzado.todosalau.reto1_panaderia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

import aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.model.InfoPartialOrder;
import aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.model.Producto;
import aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.presenter.OrderManagerPresenter;
import aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.view.DetailProductToBuyCellAdapter;

public class RequestOrderActivity extends AppCompatActivity {
    private List<InfoPartialOrder> partialOrders;
    private DetailProductToBuyCellAdapter detailProductToBuyCellAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_request_order);

        setupView();

    }

    private void setupView() {
        ImageButton backButton = findViewById(R.id.btnBackRequestOrder);
        Button buyButton = findViewById(R.id.buttonBuy);
        partialOrders = getIntent().getParcelableArrayListExtra("partialOrders");
        //partialOrders = orderManagerPresenter.getListPartialOrders();

        ListView listViewPartialOrders = findViewById(R.id.listViewProductsRequestOrder);
        detailProductToBuyCellAdapter = new DetailProductToBuyCellAdapter(this, R.layout.detail_product_to_buy_cell, partialOrders);
        listViewPartialOrders.setAdapter(detailProductToBuyCellAdapter);
        updateinfoPartialOrderList(partialOrders);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RequestOrderActivity.this, HomeClientActivity.class);
                startActivity(intent);
            }
        });
    }

    private void updateinfoPartialOrderList(List<InfoPartialOrder> infoPartialOrders) {
        detailProductToBuyCellAdapter.clear();
        detailProductToBuyCellAdapter.addAll(infoPartialOrders);
    }
}
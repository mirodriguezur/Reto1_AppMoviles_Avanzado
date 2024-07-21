package aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.presenter;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.model.InfoPartialOrder;
import aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.model.OrderDAO;
import aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.model.OrderDAOInterface;

public class OrderManagerPresenter {
    private OrderDAOInterface orderDAO;
    private List<InfoPartialOrder> infoPartialOrders = new ArrayList<>();
    public OrderManagerPresenter(Context context) {
        this.orderDAO = new OrderDAO(context);
    }

    public void savePartialOrder(InfoPartialOrder partialOrder) {
        infoPartialOrders.add(partialOrder);
    }

    public List<InfoPartialOrder> getListPartialOrders () {
        return infoPartialOrders;
    }


}

package com.example.lesson2layoutmenuadpter.productview;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lesson2layoutmenuadpter.R;
import com.example.lesson2layoutmenuadpter.network.ImageRequester;
import com.example.lesson2layoutmenuadpter.network.ProductEntry;
import com.example.lesson2layoutmenuadpter.productview.click_listeners.OnDeleteListener;
import com.example.lesson2layoutmenuadpter.productview.click_listeners.OnEditListener;

import java.util.List;

/**
 * Adapter used to show a simple grid of products.
 */
public class ProductCardRecyclerViewAdapter extends RecyclerView.Adapter<ProductCardViewHolder> {

    private List<ProductEntry> productList;
    private ImageRequester imageRequester;

    private OnDeleteListener deleteListener;
    private OnEditListener editListener;

    ProductCardRecyclerViewAdapter(List<ProductEntry> productList,
                                   OnDeleteListener deleteListener,
                                   OnEditListener editListener) {
        this.productList = productList;
        this.deleteListener = deleteListener;
        this.editListener = editListener;
        imageRequester = ImageRequester.getInstance();
    }

    @NonNull
    @Override
    public ProductCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card,
                parent, false);
        return new ProductCardViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductCardViewHolder holder, int position) {
        if (productList != null && position < productList.size()) {
            ProductEntry product = productList.get(position);
            holder.productTitle.setText(product.title);
            holder.productPrice.setText(product.price);
            imageRequester.setImageFromUrl(holder.productImage, product.url);

            holder.getView().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    deleteListener.deleteItem(productList.get(position));
                    return true;
                }
            });

            holder.getView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editListener.editItem(productList.get(position),position);
                }
            });
//            holder.getView().setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    switch (event.getAction()) {
//                        case MotionEvent.ACTION_MOVE: // движение
//                            editListener.editItem(productList.get(position),position);
//                            return true;
//                            //break;
//                    }
//
//
//                    return false;
//                }
//            });
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}


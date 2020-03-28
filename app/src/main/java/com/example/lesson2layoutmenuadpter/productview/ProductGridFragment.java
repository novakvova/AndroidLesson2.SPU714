package com.example.lesson2layoutmenuadpter.productview;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lesson2layoutmenuadpter.R;
import com.example.lesson2layoutmenuadpter.network.ProductEntry;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductGridFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductGridFragment extends Fragment {

    private static final String TAG = ProductGridFragment.class.getSimpleName();

    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_grid, container, false);

        // Set up the RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));

        List<ProductEntry> list = ProductEntry.initProductEntryList(getResources());
        ProductCardRecyclerViewAdapter adapter = new ProductCardRecyclerViewAdapter(list);

        recyclerView.setAdapter(adapter);

        int largePadding = getResources().getDimensionPixelSize(R.dimen.shr_product_grid_spacing);
        int smallPadding = getResources().getDimensionPixelSize(R.dimen.shr_product_grid_spacing_small);
        recyclerView.addItemDecoration(new ProductGridItemDecoration(largePadding, smallPadding));

        return view;
    }
}

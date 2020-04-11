package com.example.lesson2layoutmenuadpter.productview;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.lesson2layoutmenuadpter.R;
import com.example.lesson2layoutmenuadpter.network.ProductEntry;
import com.example.lesson2layoutmenuadpter.productview.dto.ProductDTO;
import com.example.lesson2layoutmenuadpter.productview.network.ProductDTOService;
import com.example.lesson2layoutmenuadpter.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductGridFragment extends Fragment {

    private static final String TAG = ProductGridFragment.class.getSimpleName();

    private RecyclerView recyclerView;
    private Button addButton;
    List<ProductEntry> listProductEntry;
    ProductCardRecyclerViewAdapter productAdapter;

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
        setupViews(view);
        setRecyclerView();
        loadProductEntryList();

        return view;
    }

    private void setupViews(View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
        addButton = view.findViewById(R.id.add_button);

        listProductEntry=new ArrayList<>();
        productAdapter = new ProductCardRecyclerViewAdapter(listProductEntry);


    }

    private void setRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));

        recyclerView.setAdapter(productAdapter);

        int largePadding = getResources().getDimensionPixelSize(R.dimen.shr_product_grid_spacing);
        int smallPadding = getResources().getDimensionPixelSize(R.dimen.shr_product_grid_spacing_small);
        recyclerView.addItemDecoration(new ProductGridItemDecoration(largePadding, smallPadding));
    }

    private void loadProductEntryList() {
        CommonUtils.showLoading(getActivity());
        ProductDTOService.getInstance()
                .getJSONApi()
                .getAllProducts()
                .enqueue(new Callback<List<ProductDTO>>() {
                    @Override
                    public void onResponse(Call<List<ProductDTO>> call, Response<List<ProductDTO>> response) {
                        if(response.isSuccessful()) {
                            Log.d(TAG, "----------Response good----------");
                            listProductEntry.clear();
                            List<ProductDTO> list = response.body();
                            for (ProductDTO item : list) {
                                ProductEntry pe = new ProductEntry(item.getTitle(), item.getUrl(), item.getUrl(), item.getPrice(), "sdfasd");
                                listProductEntry.add(pe);
                            }
                            productAdapter.notifyDataSetChanged();
                        }
                        else {
                            //  Toast.makeText(getContext(), "Проблема при отримані даних",Toast.LENGTH_LONG).show();
                        }
                        CommonUtils.hideLoading();
                    }

                    @Override
                    public void onFailure(Call<List<ProductDTO>> call, Throwable t) {
                        Log.e(TAG, "----------Bad Request----------");
                        CommonUtils.hideLoading();
                    }
                });

    }
}

package com.example.lesson2layoutmenuadpter.productview;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.lesson2layoutmenuadpter.R;

public class ProductCreateFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    public static final int PICKFILE_RESULT_CODE = 1;
    ImageView chooseImage;
    String chooseImageBase64;
    Button btnSelectImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_create, container, false);

        setupViews(view);
        setButtonSelectImageListener();

        return view;
    }

    private void setupViews(View view) {
        btnSelectImage = view.findViewById(R.id.btnSelectImage);
        chooseImage = (ImageView) view.findViewById(R.id.chooseImage);
    }

    private void setButtonSelectImageListener() {
        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.setType("image/*");
                chooseFile = Intent.createChooser(chooseFile, "Оберіть малюнок продукта");
                startActivityForResult(chooseFile, PICKFILE_RESULT_CODE);
            }
        });
    }

}

package com.example.lesson2layoutmenuadpter.productview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lesson2layoutmenuadpter.NavigationHost;
import com.example.lesson2layoutmenuadpter.R;
import com.example.lesson2layoutmenuadpter.productview.dto.ProductCreateDTO;
import com.example.lesson2layoutmenuadpter.productview.dto.ProductCreateInvalidDTO;
import com.example.lesson2layoutmenuadpter.productview.dto.ProductCreateSuccessDTO;
import com.example.lesson2layoutmenuadpter.productview.network.ProductDTOService;
import com.example.lesson2layoutmenuadpter.utils.CommonUtils;
import com.example.lesson2layoutmenuadpter.utils.FileUtil;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductCreateFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    public static final int PICKFILE_RESULT_CODE = 1;
    ImageView chooseImage;
    String chooseImageBase64;
    Button btnSelectImage;
    Button addButton;

    TextInputLayout titleTextInput;
    TextInputEditText titleEditText;
    TextInputLayout priceTextInput;
    TextInputEditText priceEditText;
    TextView errormessage;

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
        setButtonAddListener();
        return view;
    }

    private void setupViews(View view) {
        btnSelectImage = view.findViewById(R.id.btnSelectImage);
        chooseImage = (ImageView) view.findViewById(R.id.chooseImage);
        addButton = view.findViewById(R.id.add_button);

        titleTextInput = view.findViewById(R.id.product_title_input_text);
        titleEditText = view.findViewById(R.id.product_title_edit_text);
        priceTextInput = view.findViewById(R.id.price_text_input);
        priceEditText = view.findViewById(R.id.price_edit_text);
        errormessage = view.findViewById(R.id.error_message);
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

    private void setButtonAddListener() {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductCreateDTO productCreateDTO=new ProductCreateDTO(titleEditText.getText().toString(),
                        priceEditText.getText().toString(), chooseImageBase64);
                CommonUtils.showLoading(getActivity());
                ProductDTOService.getInstance()
                        .getJSONApi()
                        .CreateRequest(productCreateDTO)
                        .enqueue(new Callback<ProductCreateSuccessDTO>() {
                            @Override
                            public void onResponse(Call<ProductCreateSuccessDTO> call, Response<ProductCreateSuccessDTO> response) {
                                CommonUtils.hideLoading();
                                errormessage.setText("");
                                titleTextInput.setError(null);
                                priceTextInput.setError(null);
                                if(response.isSuccessful())
                                {
                                    ProductCreateSuccessDTO res=response.body();
                                    ((NavigationHost)getActivity()).navigateTo(new ProductGridFragment(),false);
                                }
                                else {
                                    try {
                                        //work error
                                        String json = response.errorBody().string();
                                        Gson gson  = new Gson();
                                        ProductCreateInvalidDTO resultBad = gson.fromJson(json, ProductCreateInvalidDTO.class);
                                        if(resultBad.getTitle() != null && !resultBad.getTitle().isEmpty()) {
                                            titleTextInput.setError(resultBad.getTitle());
                                        }
                                        if(resultBad.getPrice() != null && !resultBad.getPrice().isEmpty()) {
                                            priceTextInput.setError(resultBad.getPrice());
                                        }
                                        if(resultBad.getImageBase64() != null && !resultBad.getImageBase64().isEmpty()) {
                                            errormessage.setText(resultBad.getImageBase64());
                                        }
                                        if(resultBad.getInvalid() != null && !resultBad.getInvalid().isEmpty()) {
                                            errormessage.setText(resultBad.getInvalid());
                                        }
                                        //Log.d(TAG,"++++++++++++++++++++++++++++++++"+response.errorBody().string());
                                        //errormessage.setText(resultBad.getInvalid());
                                    } catch(Exception ex) {

                                    }

                                }
                            }

                            @Override
                            public void onFailure(Call<ProductCreateSuccessDTO> call, Throwable t) {
                                CommonUtils.hideLoading();
                            }
                        });

            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICKFILE_RESULT_CODE:
                if (resultCode == -1) {
                    Uri fileUri = data.getData();
                    try {
                        File imgFile = FileUtil.from(this.getActivity(), fileUri);
                        byte[] buffer = new byte[(int) imgFile.length() + 100];
                        int length = new FileInputStream(imgFile).read(buffer);
                        chooseImageBase64 = Base64.encodeToString(buffer, 0, length, Base64.NO_WRAP);
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        chooseImage.setImageBitmap(myBitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                break;
        }
    }

}

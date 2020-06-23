package com.tai.project4;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class MainFragment extends Fragment {

    TextView tvName, tvEmail,tvPhone , tvAddress;
    Button btnChangePSW, btnChangeDetails;
    Context context;
    public static final String PREFS = "PREFS";
    SharedPreferences sp;

    public MainFragment(Context context) {
        // Required empty public constructor
        this.context=context;
        sp = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        ImageView back = view.findViewById(R.id.back);
        tvName = view.findViewById(R.id.tvName);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvPhone = view.findViewById(R.id.tvPhone);
        tvAddress = view.findViewById(R.id.tvAddress);
        btnChangePSW = view.findViewById(R.id.btnChangePSW);
        btnChangeDetails = view.findViewById(R.id.btnChangeDetails);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        btnChangePSW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context,ChangePassword.class);
                startActivity(i);
            }
        });

        btnChangeDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context,ChangeClientDetail.class);
                startActivity(i);
            }
        });

        View photoHeader = view.findViewById(R.id.photoHeader);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        /* For devices equal or higher than lollipop set the translation above everything else */
            photoHeader.setTranslationZ(6);
        /* Redraw the view to show the translation */
            photoHeader.invalidate();
        }

        if (sp.getString("loginid", null) != null) {
            tvName.setText(sp.getString("Name", "Name"));
            tvPhone.setText((sp.getString("Phone", "Phone")));
            tvEmail.setText(sp.getString("Email", "Email"));
            tvAddress.setText(sp.getString("Address", "Address"));
        }

        return view;
    }
}

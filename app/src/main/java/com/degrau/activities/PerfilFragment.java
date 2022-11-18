package com.degrau.activities;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.degrau.R;
import com.degrau.activities.maps.MapsActivity;
import com.degrau.databinding.ActivityPerfilFragmentBinding;


public class PerfilFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ProgressBar progressBar;
    private ImageView imagePerfil;
    private TextView textMentores, textMentorias, textMentorados;
    private Button buttonEditarPerfil;
    private ActivityPerfilFragmentBinding binding;
    private Button buttonVerMaps;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PerfilFragment() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PerfilFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PerfilFragment newInstance(String param1, String param2) {
        PerfilFragment fragment = new PerfilFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.activity_perfil_fragment, container, false);

        // configurações dos componentes
        
        textMentorados = view.findViewById(R.id.textMentorados);
        textMentores = view.findViewById(R.id.textMentores);
        textMentorias = view.findViewById(R.id.textMentorias);
        buttonEditarPerfil = view.findViewById(R.id.buttonEditarPerfil);
        buttonVerMaps = view.findViewById(R.id.btnVerMaps);

        // abre mapa
        buttonVerMaps.setOnClickListener(view1 -> {
            Intent i = new Intent(getActivity(), MapsActivity.class);
            startActivity(i);
        });

        // abre edição de perfil
        buttonEditarPerfil.setOnClickListener(view1 -> {
            Intent i = new Intent(getActivity(), EditarPerfilActivity.class);
            startActivity(i);
        });


        return view;

    }
}
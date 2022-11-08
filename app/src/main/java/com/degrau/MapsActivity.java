package com.degrau;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.degrau.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    // a interface OnMapReadyCallback para usar um dos métodos da interface que será usado após o mapa ser carregado
    private GoogleMap mMap; // Objeto para fazer alterações no mapa
    private String[] permissoes = new String[] {
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Validando permissões
        Permissoes.validarPermissoes(permissoes, this, 1)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) { // método chamado após o mapa ser carregado
        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        // Add a marker in Sydney and move the camera
        LatLng quixada = new LatLng(-4.979465, -39.056837);
        mMap.addMarker(new MarkerOptions().position(quixada).title("Quixadá, CE, Brazil"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(quixada, 18));
    }

    // sobrescrevendo o método

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for( int permissaoResultado : grantResults ) {
                if(permissaoResultado == PackageManager.PERMISSION_DENIED){
                    // alerta
                } else if(permissaoResultado == PackageManager.PERMISSION_GRANTED) {
                    // recuperar a localização do usuário
                }
        }
    }
}
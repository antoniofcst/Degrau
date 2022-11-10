package com.degrau;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.degrau.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    // a interface OnMapReadyCallback para usar um dos métodos da interface que será usado após o mapa ser carregado
    private GoogleMap mMap; // Objeto para fazer alterações no mapa
    private String[] permissoes = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private LocationManager locationManager; // classe que permite localizar o usuario
    private LocationListener locationListener; // ouvindo as atualizações da localizacao do user

    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Validando permissões
        Permissoes.validarPermissoes(permissoes, this, 1);


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

        // localizacao do usuario
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("Localização", "On Location Changed: " + location.toString());

                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                mMap.clear(); // limpando o marcador do mapa
                LatLng localUsuario = new LatLng(latitude, longitude);
                mMap.addMarker(new MarkerOptions()
                        .position(localUsuario)
                        .title("Local do Usuario")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.estudante))

                );
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(localUsuario, 15));
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    0,
                    0,
                    locationListener
                    // requerendo pela segunda vez a localização do usuário após ele dar a permissão de acesso a localização

            );

        }


        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

    }

    // sobrescrevendo o método

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int permissaoResultado : grantResults) {
            if (permissaoResultado == PackageManager.PERMISSION_DENIED) {
                alertaValidacaoPermissao();
            } else if (permissaoResultado == PackageManager.PERMISSION_GRANTED) {
                // recuperar a localização do usuário
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            0,
                            0,
                            locationListener


                    );

                }

                }
        }
    }

    private void alertaValidacaoPermissao() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissões negadas.");
        builder.setMessage("Para utilizar o App é necessário aceitar as permissões de uso.");
        builder.setCancelable(false);
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
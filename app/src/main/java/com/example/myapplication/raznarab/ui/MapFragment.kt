package com.example.myapplication.raznarab.ui

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.PointF
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentMapBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.PlacemarksStyler
import com.yandex.mapkit.map.TextStyle
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import org.koin.androidx.viewmodel.ext.android.viewModel


class MapFragment : Fragment() {
    private lateinit var binding: FragmentMapBinding
    private lateinit var mapView: MapView
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val mapViewModel: MapViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView = binding.mapViewId

        mapView.mapWindow.map.move(
            CameraPosition(Point(56.013260, 92.867194), 14.0f, 0.0f, 0.0f)
        )

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        fetchLocation()
        zoomMap()
        unZoomMap()

        getAllCoordinates()


    }

    override fun onStop() {
        super.onStop()
        MapKitFactory.getInstance().onStop()
        mapView.onStop()
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
        MapKitFactory.getInstance().onStart()

    }

    private fun fetchLocation() {
        val task = fusedLocationProviderClient.lastLocation
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                101
            )
            return
        }
        task.addOnSuccessListener {
            if (it != null) {
                Toast.makeText(
                    requireContext(),
                    "${it.longitude} ${it.latitude}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    private fun zoomMap(){
        binding.plusImageId.setOnClickListener {
            val map = mapView.mapWindow.map
            val currentPosition = map.cameraPosition

            val newZoomLevel = currentPosition.zoom + 1

            map.move(
                CameraPosition(currentPosition.target, newZoomLevel, currentPosition.azimuth, currentPosition.tilt),
                Animation(Animation.Type.SMOOTH, 0.5f),
                null
            )
        }
    }

    private fun unZoomMap(){
        binding.minusImageId.setOnClickListener {
            val map = mapView.mapWindow.map
            val currentPosition = map.cameraPosition

            val newUnZoomLevel = currentPosition.zoom - 1

            map.move(
                CameraPosition(currentPosition.target, newUnZoomLevel, currentPosition.azimuth, currentPosition.tilt),
                Animation(Animation.Type.SMOOTH, 0.5f),
                null
            )
        }
    }

    private fun getAllCoordinates(){
        mapViewModel.getCoordState().observe(viewLifecycleOwner){
            state ->
            when(state){

                is MapPointStateScreen.CoordinatesContent ->{
                    addPointsToMap(state.data)
                }

                is MapPointStateScreen.ErrorData -> {
                    Toast.makeText(requireContext(), state.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun addPointsToMap(list: List<Point>){
        val map = mapView.mapWindow.map
        val mapObjects = map.mapObjects

        for(point in list){
            val placemark = mapObjects.addPlacemark(point)
            placemark.setIcon(ImageProvider.fromResource(requireContext(), R.drawable.ic_money))
        }
    }
}
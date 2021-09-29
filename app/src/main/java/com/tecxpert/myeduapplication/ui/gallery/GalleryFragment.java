package com.tecxpert.myeduapplication.ui.gallery;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.tecxpert.myeduapplication.R;
import com.tecxpert.myeduapplication.activites.LoginActivity;
import com.tecxpert.myeduapplication.databinding.FragmentGalleryBinding;
import com.tecxpert.myeduapplication.utill.PrefManager;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private FragmentGalleryBinding binding;
    private PrefManager prefManager;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        prefManager=PrefManager.getInstance(getActivity());
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(getActivity());

        // Set the message show for the Alert time
        builder.setMessage("Do you want to Logout ?");

        // Set Alert Title
        builder.setTitle("Alert !");

        // Set Cancelable false
        // for when the user clicks on the outside
        // the Dialog Box then it will remain show
        builder.setCancelable(false);

        // Set the positive button with yes name
        // OnClickListener method is use of
        // DialogInterface interface.

        builder
                .setPositiveButton(
                        "Yes",
                        new DialogInterface
                                .OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which)
                            {

                                // When the user click yes button
                                // then app will close
                               prefManager.deletepref();
                                startActivity(new Intent(getActivity(), LoginActivity.class));
                                getActivity().finish();
                            }
                        });

        // Set the Negative button with No name
        // OnClickListener method is use
        // of DialogInterface interface.
        builder
                .setNegativeButton(
                        "No",
                        new DialogInterface
                                .OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which)
                            {

                                // If user click no
                                // then dialog box is canceled.
                                dialog.cancel();
                                getActivity().onBackPressed();
                           /*     HomeFragment paymentReportFragment = new HomeFragment();
                                FragmentManager fragmentManager= getParentFragmentManager();
                                FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.nav_host_fragment,paymentReportFragment);
                                fragmentTransaction.addToBackStack("Home");
                                fragmentTransaction.commit();
*/
                            }
                        });

        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();

        // Show the Alert Dialog box
        alertDialog.show();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
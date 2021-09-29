package com.tecxpert.myeduapplication.ui.slideshow;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tecxpert.myeduapplication.R;
import com.tecxpert.myeduapplication.adapter.ReportListAdepter;
import com.tecxpert.myeduapplication.databinding.FragmentSlideshowBinding;
import com.tecxpert.myeduapplication.model.Report;
import com.tecxpert.myeduapplication.model.Subject;
import com.tecxpert.myeduapplication.utill.PrefManager;

import java.util.ArrayList;
import java.util.List;

public class SlideshowFragment extends Fragment {


    private FragmentSlideshowBinding binding;
    FirebaseDatabase firebaseDatabase;
    PrefManager prefManager;
    ProgressDialog progressDialog;
    RecyclerView recyclerView;
    ArrayList<Report> reports=new ArrayList<>();
    ReportListAdepter reportListAdepter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        prefManager=PrefManager.getInstance(getContext());
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("Please Wait.....");
        firebaseDatabase = FirebaseDatabase.getInstance();
        recyclerView=binding.textSlideshow;
        LinearLayoutManager lm = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(lm);

        reportListAdepter=new ReportListAdepter(getContext(), reports, new ReportListAdepter.OnitemClick() {
            @Override
            public void OnitemClick(int position, Report mpData, View view) {

            }
        });
recyclerView.setAdapter(reportListAdepter);
progressDialog.show();
        firebaseDatabase.getReference("report").child(prefManager.getuser().getEmail().substring(0,prefManager.getuser().getEmail().indexOf("@"))).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
             //   progressDialog.dismiss();
                if (snapshot.exists()){
                    Report report;
                    reports.clear();
               progressDialog.dismiss();
                    for (DataSnapshot dd:
                            snapshot.getChildren() ) {
                        report=dd.getValue(Report.class);
                        reports.add(report);
                    }
                    reportListAdepter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
package com.tecxpert.myeduapplication.ui.home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import com.tecxpert.myeduapplication.activites.LoginActivity;
import com.tecxpert.myeduapplication.activites.MainScreenActivity;
import com.tecxpert.myeduapplication.activites.StudyActivity;
import com.tecxpert.myeduapplication.adapter.SubjectListAdepter;
import com.tecxpert.myeduapplication.databinding.FragmentHomeBinding;
import com.tecxpert.myeduapplication.model.Subject;
import com.tecxpert.myeduapplication.model.User;
import com.tecxpert.myeduapplication.utill.PrefManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    SubjectListAdepter subjectListAdepter;
    ArrayList<Subject> subjects=new ArrayList<>();
    FirebaseDatabase firebaseDatabase;
    ProgressDialog progressDialog;
    PrefManager prefManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        RecyclerView recyclerView=binding.rcySubjects;
        prefManager=PrefManager.getInstance(getActivity());
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait....");
        firebaseDatabase = FirebaseDatabase.getInstance();
        LinearLayoutManager lm = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(lm);
        subjectListAdepter=new SubjectListAdepter(getContext(), subjects, new SubjectListAdepter.OnitemClick() {
            @Override
            public void OnitemClick(int position, Subject mpData, View view) {
                startActivity(new Intent(getActivity(), StudyActivity.class).putExtra("subject",mpData.getName()).putExtra("subject_key",mpData.getKey()));
            }
        });
        recyclerView.setAdapter(subjectListAdepter);
        progressDialog.show();
        firebaseDatabase.getReference("classes").child(prefManager.getuser().getClass_name()).child("subject").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressDialog.dismiss();
                if (snapshot.exists()){
                    Subject subject;
                    subjects.clear();
                    for (DataSnapshot dd:
                        snapshot.getChildren() ) {
                        subject=new Subject();
                        subject.setKey(dd.getKey().toString());
                    subject.setName(dd.child("name").getValue(String.class));
                        subjects.add(subject);
                    }
                    subjectListAdepter.notifyDataSetChanged();
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
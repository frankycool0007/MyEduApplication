package com.tecxpert.myeduapplication.ui.main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tecxpert.myeduapplication.activites.VedioActivity;
import com.tecxpert.myeduapplication.adapter.VedioListAdepter;
import com.tecxpert.myeduapplication.databinding.FragmentStudyBinding;
import com.tecxpert.myeduapplication.model.Vedio;
import com.tecxpert.myeduapplication.utill.PrefManager;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */

public class Placeholder2Fragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private FragmentStudyBinding binding;
    VedioListAdepter vedioListAdepter;
    ArrayList<Vedio> subjects=new ArrayList<>();
    FirebaseDatabase firebaseDatabase;
    ProgressDialog progressDialog;
    PrefManager prefManager;
    public static Placeholder2Fragment newInstance(int index) {
        Placeholder2Fragment fragment = new Placeholder2Fragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = FragmentStudyBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        RecyclerView recyclerView=binding.rcy;
        prefManager=PrefManager.getInstance(getActivity());
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait....");
        firebaseDatabase = FirebaseDatabase.getInstance();
        LinearLayoutManager lm = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(lm);
        vedioListAdepter=new VedioListAdepter(getContext(), subjects, new VedioListAdepter.OnitemClick() {
            @Override
            public void OnitemClick(int position, Vedio mpData, View view) {
                startActivity(new Intent(getActivity(), VedioActivity.class).putExtra("subject_name",getActivity().getIntent().getStringExtra("subject_key")).putExtra("topic_name",mpData.getTopic_name()).putExtra("topic_url",mpData.getUrl()).putExtra("topic_key",mpData.getKey()));
            }
        });
        recyclerView.setAdapter(vedioListAdepter);

        progressDialog.show();
        firebaseDatabase.getReference("classes").child(prefManager.getuser().getClass_name()).child("subject").child(getActivity().getIntent().getStringExtra("subject_key")).child("vedioes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressDialog.dismiss();
                if (snapshot.exists()){
                    Vedio vedio;

                    subjects.clear();
                    for (DataSnapshot dd:
                            snapshot.getChildren() ) {
                        vedio=new Vedio();
                        vedio.setKey(dd.getKey());
                        vedio.setUrl(dd.child("url").getValue(String.class));
                        vedio.setTopic_name(dd.child("topic_name").getValue(String.class));
                        subjects.add(vedio);
                    }
                    vedioListAdepter.notifyDataSetChanged();
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
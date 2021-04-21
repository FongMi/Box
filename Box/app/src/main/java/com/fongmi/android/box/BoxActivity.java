package com.fongmi.android.box;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.fongmi.android.box.databinding.ActivityBoxBinding;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class BoxActivity extends AppCompatActivity {

	private ActivityBoxBinding binding;
	private BoxAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityBoxBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());
		initView();
		getList();
	}

	private void initView() {
		binding.recycler.setLayoutManager(new LinearLayoutManager(this));
		binding.recycler.setAdapter(mAdapter = new BoxAdapter());
	}

	private void getList() {
		StorageReference listRef = FirebaseStorage.getInstance().getReference();
		listRef.listAll().addOnSuccessListener(listResult -> mAdapter.addAll(listResult.getItems()));
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		FileUtil.clearCache();
	}
}
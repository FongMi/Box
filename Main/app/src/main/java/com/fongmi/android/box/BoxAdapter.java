package com.fongmi.android.box;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fongmi.android.box.databinding.AdapterBoxBinding;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class BoxAdapter extends RecyclerView.Adapter<BoxAdapter.ViewHolder> {

	private final List<StorageReference> mItems;

	public BoxAdapter() {
		this.mItems = new ArrayList<>();
	}

	class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		private final AdapterBoxBinding binding;

		public ViewHolder(@NonNull AdapterBoxBinding binding) {
			super(binding.getRoot());
			this.binding = binding;
			itemView.setOnClickListener(this);
		}

		@Override
		public void onClick(View view) {
			FileUtil.download(mItems.get(getLayoutPosition()).getName());
		}
	}

	public void addAll(List<StorageReference> items) {
		mItems.clear();
		mItems.addAll(items);
		notifyDataSetChanged();
	}

	@Override
	public int getItemCount() {
		return mItems.size();
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		return new ViewHolder(AdapterBoxBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
	}

	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
		holder.binding.name.setText(mItems.get(position).getName());
	}
}

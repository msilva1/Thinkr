package com.android.thinkr.adapters;

import android.databinding.BaseObservable;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collection;

/**
 * Adapters requiring support of single or multiple view and data types within a
 * {@linkplain RecyclerView} should extend this class. A list is already provided
 * so there is no need to create a list instance. Modifying the data inside this adapter should be done
 * through the interfaces provided here if automatic updating of bounded data is preferred.
 * Direct access to the underlying list is provided for convenience.
 * Modifying the data directly requires notifying this adapter for updating bounded views. .
 * <p/>
 * Created by M. Silva on 1/3/16.
 */
public abstract class BaseRecyclerAdapter<T extends BaseObservable> extends RecyclerView.Adapter<BaseRecyclerAdapter.ViewBindingHolder> {

    /**
     * Contains the list of items that represent the data of this RecyclerView.Adapter.
     * The this list is referred to as "the array" in the documentation.
     */
    protected final ObservableArrayList<T> mItems;

    /**
     * Lock used to modify the content of {@link #mItems}. Any write operation
     * performed on the array should be synchronized on this lock.
     */
    private final Object mLock = new Object();

    protected OnItemClickListener mItemClickListener;

    public BaseRecyclerAdapter() {
        mItems = new ObservableArrayList<>();
    }

    public BaseRecyclerAdapter(@NonNull ObservableArrayList<T> items) {
        mItems = items;
    }

    protected abstract int getViewType(BaseObservable item);

    protected abstract int getLayoutIdByViewType(int viewType);

    protected abstract void onBindViewHolderByViewType(int viewType, ViewBindingHolder holder, BaseObservable item);

    /**
     * Returns the data used by this adapter.
     * If registering a listener on changes to the array is required
     * it can be done here.
     *
     * @return The list of items in the array.
     */
    public final ObservableArrayList<T> getItems() {
        return mItems;
    }

    /**
     * Adds the specified item at the end of the array.
     *
     * @param item The item to add at the end of the array.
     */
    public void add(T item) {
        synchronized (mLock) {
            mItems.add(item);
            notifyItemInserted(mItems.size() - 1);
        }
    }

    /**
     * Adds the specified Collection at the end of the array.
     *
     * @param items The Collection to add at the end of the array.
     */
    public void addAll(Collection<T> items) {
        synchronized (mLock) {
            mItems.addAll(items);
            notifyItemRangeChanged(mItems.size() - items.size(), items.size());
        }
    }

    /**
     * Removes the specified item from the array.
     *
     * @param item The item to remove.
     */
    public void remove(T item) {
        synchronized (mLock) {
            final int position = getPosition(item);
            mItems.remove(item);
            notifyItemRemoved(position);
        }
    }

    /**
     * Removes all items in the array.
     */
    public void clear() {
        synchronized (mLock) {
            mItems.clear();
            notifyDataSetChanged();
        }
    }

    /**
     * Returns the item at the specified position in the array.
     */
    public T getItem(int position) {
        return mItems.get(position);
    }

    /**
     * Returns the position of the specified item in the array.
     *
     * @param item The item to retrieve the position of.
     * @return The position of the specified item.
     */
    public int getPosition(T item) {
        return mItems.indexOf(item);
    }

    /**
     * Registers a click listener notifying when an item in the {@link RecyclerView} has
     * been clicked.
     *
     * @param listener The listener to call when an item is clicked.
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        mItemClickListener = listener;
    }

    @Override
    public void onBindViewHolder(ViewBindingHolder holder, int position) {
        final T item = mItems.get(position);
        onBindViewHolderByViewType(getViewType(item), holder, item);
        holder.binding.executePendingBindings();
    }

    /**
     * @return
     */
    public boolean isEmpty() {
        return mItems.isEmpty();
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        final T observable = mItems.get(position);
        return getViewType(observable);
    }

    @Override
    public ViewBindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflateView(parent, getLayoutIdByViewType(viewType));
        return new ViewBindingHolder(view, mItemClickListener);
    }

    /**
     * Convenience method for inflating a layout into a ViewGroup.
     *
     * @param parent   The target ViewGroup
     * @param resource The layout resource ID to inflate.
     * @return The inflated view.
     */
    private View inflateView(ViewGroup parent, int resource) {
        return LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    /**
     * Uses {@link ViewDataBinding} to create a view-holder
     */
    public static class ViewBindingHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ViewDataBinding binding;
        private final OnItemClickListener mOnItemClickListener;

        public ViewBindingHolder(View itemView, OnItemClickListener listener) {
            super(itemView);

            itemView.setOnClickListener(this);
            binding = DataBindingUtil.bind(itemView);

            mOnItemClickListener = listener;
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION)
                    mOnItemClickListener.onItemClick(v, position);
            }
        }
    }
}

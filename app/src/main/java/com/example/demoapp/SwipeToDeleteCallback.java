package com.example.demoapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoapp.adapter.SwipeRefreshAdapter;

public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {

    private final SwipeRefreshAdapter mAdapter;
    private final Drawable deleteIcon;
    private final ColorDrawable background;
    private final int intrinsicWidth;
    private final int intrinsicHeight;
    private final int backgroundColor = Color.RED;
    private final Paint clearPaint;

    public SwipeToDeleteCallback(Context context, SwipeRefreshAdapter adapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        mAdapter = adapter;

        // Set up the delete icon and background
        //deleteIcon = ContextCompat.getDrawable(context, R.drawable.ic_delete); // Your delete icon drawable
        deleteIcon = ContextCompat.getDrawable(context, R.drawable.ic_delete);



        intrinsicWidth = deleteIcon.getIntrinsicWidth();
        intrinsicHeight = deleteIcon.getIntrinsicHeight();
        background = new ColorDrawable();
        clearPaint = new Paint();
        clearPaint.setColor(Color.TRANSPARENT);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        mAdapter.removeItem(position);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        int itemViewHeight = viewHolder.itemView.getHeight();
        int itemViewTop = viewHolder.itemView.getTop();
        int itemViewBottom = viewHolder.itemView.getBottom();
        int itemViewRight = viewHolder.itemView.getRight();
        int itemViewLeft = viewHolder.itemView.getLeft();

        // Set background color during swipe
        // Set the background color to sky blue
        // Set the background color to light violet
        background.setColor(Color.parseColor("#D8BFD8"));
        background.setBounds(itemViewLeft, itemViewTop, itemViewRight, itemViewBottom);
        background.draw(c);



        // Calculate position for the delete icon
        int iconTop = itemViewTop + (itemViewHeight - intrinsicHeight) / 2;
        int iconMargin = (itemViewHeight - intrinsicHeight) / 2;
        int iconLeft, iconRight;

        if (dX > 0) { // Swiping to the right
            iconLeft = itemViewLeft + iconMargin;
            iconRight = itemViewLeft + iconMargin + intrinsicWidth;
        } else { // Swiping to the left
            iconLeft = itemViewRight - iconMargin - intrinsicWidth;
            iconRight = itemViewRight - iconMargin;
        }

        int iconBottom = iconTop + intrinsicHeight;

        // Draw delete icon
        deleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
        deleteIcon.draw(c);

        // Clear the background when swipe is canceled
        if (dX == 0 && !isCurrentlyActive) {
            c.drawRect(itemViewLeft, itemViewTop, itemViewRight, itemViewBottom, clearPaint);
        }
    }
}

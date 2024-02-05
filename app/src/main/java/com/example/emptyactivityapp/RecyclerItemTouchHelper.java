package com.example.emptyactivityapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emptyactivityapp.Adapter.AssignmentAdapter;

import com.example.emptyactivityapp.Adapter.ToDoAdapter;

public class RecyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback {
    private ToDoAdapter toDoAdapter;



    public RecyclerItemTouchHelper(ToDoAdapter toDoAdapter) {
        // swiping left or right
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.toDoAdapter = toDoAdapter;
    }



    @Override
    public boolean onMove(RecyclerView rView, RecyclerView.ViewHolder vHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(final RecyclerView.ViewHolder vHolder, int swipeDirection) {
        final int pos = vHolder.getAdapterPosition();
        if (swipeDirection == ItemTouchHelper.LEFT) {
            // deleting item
            Context context = toDoAdapter.getContext();
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            deleteSwipeMessages(builder);
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dial, int which) {
                    int newPos = vHolder.getAdapterPosition();
                    toDoAdapter.notifyItemChanged(newPos);
                }
            });
            builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dial, int which) {
                    toDoAdapter.deleteItem(pos);
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        } else if (swipeDirection == ItemTouchHelper.RIGHT){
            toDoAdapter.editItem(pos);
        }
    }

    private void deleteSwipeMessages(AlertDialog.Builder builder) {
        builder.setTitle("Delete task");
        builder.setMessage("Are you sure you want to delete this task?");
    }


    @Override
    public void onChildDraw(Canvas canvas, RecyclerView rView, RecyclerView.ViewHolder vHolder, float dX, float dY, int actionState, boolean isActive) {
        super.onChildDraw(canvas, rView, vHolder, dX, dY, actionState, isActive);
        Drawable symbol;
        ColorDrawable background;
        View itemView = vHolder.itemView;
        int backgroundCornerOffset = 20;
        if (dX <= 0) {
            symbol = ContextCompat.getDrawable(toDoAdapter.getContext(), R.drawable.baseline_close_24);
            background = new ColorDrawable(Color.RED);
        } else {
            symbol = ContextCompat.getDrawable(toDoAdapter.getContext(), R.drawable.baseline_edit_24);
            background = new ColorDrawable(ContextCompat.getColor(toDoAdapter.getContext(), R.color.colorPrimaryDark));

        }
        int intrinsicHeight = symbol.getIntrinsicHeight();
        int intrinsicWidth = symbol.getIntrinsicWidth();
        int iconMargin = (itemView.getHeight() - intrinsicHeight) / 2;
        int iconTop = itemView.getTop() + (itemView.getHeight() - intrinsicHeight) / 2;
        int iconBottom = iconTop + intrinsicHeight;
        if (dX > 0) {
            int iconLeft = itemView.getLeft() + iconMargin;
            int iconRight = itemView.getLeft() + iconMargin + intrinsicWidth;
            symbol.setBounds(iconLeft, iconTop, iconRight, iconBottom);
            int left = itemView.getLeft();
            int top = itemView.getTop();
            int right = itemView.getLeft() + ((int) dX) + backgroundCornerOffset;
            int bottom = itemView.getBottom();
            background.setBounds(left, top, right, bottom);

        } else if (dX < 0) { //swiping to the left
            int iconLeft = itemView.getLeft() - iconMargin - intrinsicWidth;
            int iconRight = itemView.getRight() - iconMargin;
            symbol.setBounds(iconLeft, iconTop, iconRight, iconBottom);
            int left = itemView.getRight() + ((int) dX) - backgroundCornerOffset;
            int top = itemView.getTop();
            int right = itemView.getRight();
            int bottom = itemView.getBottom();
            background.setBounds(left, top, right, bottom);

        } else {
            background.setBounds(0, 0, 0, 0);
        }
        background.draw(canvas);
        symbol.draw(canvas);
    }

}

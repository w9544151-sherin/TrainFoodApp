// Generated by view binder compiler. Do not edit!
package uk.ac.tees.w9544151.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;
import uk.ac.tees.w9544151.R;

public final class FragmentOrderListBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final AppCompatTextView labelNoData;

  @NonNull
  public final RecyclerView rvOrders;

  @NonNull
  public final TextView tvHeading;

  private FragmentOrderListBinding(@NonNull ConstraintLayout rootView,
      @NonNull AppCompatTextView labelNoData, @NonNull RecyclerView rvOrders,
      @NonNull TextView tvHeading) {
    this.rootView = rootView;
    this.labelNoData = labelNoData;
    this.rvOrders = rvOrders;
    this.tvHeading = tvHeading;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentOrderListBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentOrderListBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_order_list, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentOrderListBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.labelNoData;
      AppCompatTextView labelNoData = ViewBindings.findChildViewById(rootView, id);
      if (labelNoData == null) {
        break missingId;
      }

      id = R.id.rvOrders;
      RecyclerView rvOrders = ViewBindings.findChildViewById(rootView, id);
      if (rvOrders == null) {
        break missingId;
      }

      id = R.id.tvHeading;
      TextView tvHeading = ViewBindings.findChildViewById(rootView, id);
      if (tvHeading == null) {
        break missingId;
      }

      return new FragmentOrderListBinding((ConstraintLayout) rootView, labelNoData, rvOrders,
          tvHeading);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}

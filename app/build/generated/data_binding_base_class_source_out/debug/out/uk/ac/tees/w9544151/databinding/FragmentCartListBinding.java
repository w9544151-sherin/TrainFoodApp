// Generated by view binder compiler. Do not edit!
package uk.ac.tees.w9544151.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public final class FragmentCartListBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final AppCompatTextView btnPlaceOrder;

  @NonNull
  public final AppCompatTextView labelNoData;

  @NonNull
  public final RecyclerView rvCarts;

  private FragmentCartListBinding(@NonNull ConstraintLayout rootView,
      @NonNull AppCompatTextView btnPlaceOrder, @NonNull AppCompatTextView labelNoData,
      @NonNull RecyclerView rvCarts) {
    this.rootView = rootView;
    this.btnPlaceOrder = btnPlaceOrder;
    this.labelNoData = labelNoData;
    this.rvCarts = rvCarts;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentCartListBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentCartListBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_cart_list, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentCartListBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnPlaceOrder;
      AppCompatTextView btnPlaceOrder = ViewBindings.findChildViewById(rootView, id);
      if (btnPlaceOrder == null) {
        break missingId;
      }

      id = R.id.labelNoData;
      AppCompatTextView labelNoData = ViewBindings.findChildViewById(rootView, id);
      if (labelNoData == null) {
        break missingId;
      }

      id = R.id.rvCarts;
      RecyclerView rvCarts = ViewBindings.findChildViewById(rootView, id);
      if (rvCarts == null) {
        break missingId;
      }

      return new FragmentCartListBinding((ConstraintLayout) rootView, btnPlaceOrder, labelNoData,
          rvCarts);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}

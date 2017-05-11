package eu.davidea.samples.flexibleadapter.items;

import android.animation.Animator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.helpers.AnimatorHelper;
import eu.davidea.flexibleadapter.utils.DrawableUtils;
import eu.davidea.samples.flexibleadapter.R;
import eu.davidea.utils.Utils;
import eu.davidea.viewholders.FlexibleViewHolder;

/**
 * This item is a Scrollable Header.
 */
public class ScrollableUseCaseItem extends AbstractItem<ScrollableUseCaseItem.UCViewHolder> {

	public ScrollableUseCaseItem(String title, String subTitle) {
		super("UC");
		setTitle(title);
		setSubtitle(subTitle);
	}

	@Override
	public int getSpanSize(int spanCount, int position) {
		return spanCount;
	}

	@Override
	public int getLayoutRes() {
		return R.layout.recycler_scrollable_usecase_item;
	}

	@Override
	public UCViewHolder createViewHolder(View view, FlexibleAdapter adapter) {
		return new UCViewHolder(view, adapter);
	}

	@Override
	public void bindViewHolder(FlexibleAdapter adapter, UCViewHolder holder, int position, List payloads) {
		Context context = holder.itemView.getContext();
		DrawableUtils.setBackgroundCompat(holder.itemView, DrawableUtils.getRippleDrawable(
				DrawableUtils.getColorDrawable(context.getResources().getColor(R.color.material_color_blue_grey_50)),
				DrawableUtils.getColorControlHighlight(context))
		);
		holder.mTitle.setText(Utils.fromHtmlCompat(getTitle()));
		holder.mSubtitle.setText(Utils.fromHtmlCompat(getSubtitle()));

		//Support for StaggeredGridLayoutManager
		if (holder.itemView.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams) {
			((StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams()).setFullSpan(true);
			Log.d("LayoutItem", "LayoutItem configured fullSpan for StaggeredGridLayout");
		}
	}

	public static class UCViewHolder extends FlexibleViewHolder {

		public TextView mTitle;
		public TextView mSubtitle;
		ImageView mDismissIcon;

		public UCViewHolder(View view, FlexibleAdapter adapter) {
			super(view, adapter);
			mTitle = (TextView) view.findViewById(R.id.title);
			mSubtitle = (TextView) view.findViewById(R.id.subtitle);
			mDismissIcon = (ImageView) view.findViewById(R.id.dismiss_icon);
			mDismissIcon.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					//Don't need anymore to set permanent deletion for Scrollable Headers and Footers
					//mAdapter.setPermanentDelete(true);
					//noinspection unchecked, ConstantConditions
					mAdapter.removeScrollableHeader(mAdapter.getItem(getAdapterPosition()));
					//mAdapter.setPermanentDelete(false);
				}
			});

			//Support for StaggeredGridLayoutManager
			if (itemView.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams) {
				((StaggeredGridLayoutManager.LayoutParams) itemView.getLayoutParams()).setFullSpan(true);
			}
		}

		@Override
		public void scrollAnimators(@NonNull List<Animator> animators, int position, boolean isForward) {
			AnimatorHelper.flipAnimator(animators, itemView);
			AnimatorHelper.setDuration(animators, 500L);
		}
	}

	@Override
	public String toString() {
		return "ScrollableUseCaseItem[" + super.toString() + "]";
	}

}
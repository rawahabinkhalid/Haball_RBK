//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.haball.Distributor.ui.orders.OrdersTabsNew;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.haball.Distributor.ui.orders.OrdersTabsNew.ParentViewHolder.ParentViewHolderExpandCollapseListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class ExpandableRecyclerAdapter<P extends Parent<C>, C, PVH extends ParentViewHolder, CVH extends ChildViewHolder> extends Adapter<ViewHolder> {
    private static final String EXPANDED_STATE_MAP = "ExpandableRecyclerAdapter.ExpandedStateMap";
    public static final int TYPE_PARENT = 0;
    public static final int TYPE_CHILD = 1;
    public static final int TYPE_FIRST_USER = 2;
    private static final int INVALID_FLAT_POSITION = -1;
    @NonNull
    protected List<ExpandableWrapper<P, C>> mFlatItemList;
    private P LastExpandedParent = null;
//    private int LastExpandedParentPosition = -1;
    @NonNull
    private List<P> mParentList;
    @Nullable
    private ExpandableRecyclerAdapter.ExpandCollapseListener mExpandCollapseListener;
    @NonNull
    private List<RecyclerView> mAttachedRecyclerViewPool;
    private Map<P, Boolean> mExpansionStateMap;
    private ParentViewHolderExpandCollapseListener mParentViewHolderExpandCollapseListener = new ParentViewHolderExpandCollapseListener() {
        @UiThread
        public void onParentExpanded(int flatParentPosition) {

//            if(LastExpandedParentPosition != -1) {
////                collapseParent(LastExpandedParentPosition);
//                ExpandableRecyclerAdapter.this.parentCollapsedFromViewHolder(LastExpandedParentPosition);
//            }
//
//
            ExpandableRecyclerAdapter.this.parentExpandedFromViewHolder(flatParentPosition);
//            LastExpandedParentPosition = flatParentPosition;
        }

        @UiThread
        public void onParentCollapsed(int flatParentPosition) {
//            LastExpandedParentPosition = -1;
            ExpandableRecyclerAdapter.this.parentCollapsedFromViewHolder(flatParentPosition);
        }
    };

    public ExpandableRecyclerAdapter(@NonNull List<P> parentList) {
        this.mParentList = parentList;
        this.mFlatItemList = this.generateFlattenedParentChildList(parentList);
        this.mAttachedRecyclerViewPool = new ArrayList();
        this.mExpansionStateMap = new HashMap(this.mParentList.size());
    }

    @NonNull
    @UiThread
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (this.isParentViewType(viewType)) {
            PVH pvh = this.onCreateParentViewHolder(viewGroup, viewType);
            pvh.setParentViewHolderExpandCollapseListener(this.mParentViewHolderExpandCollapseListener);
            pvh.mExpandableAdapter = this;
            return pvh;
        } else {
            CVH cvh = this.onCreateChildViewHolder(viewGroup, viewType);
            cvh.mExpandableAdapter = this;
            return cvh;
        }
    }

    @UiThread
    public void onBindViewHolder(@NonNull ViewHolder holder, int flatPosition) {
        if (flatPosition > this.mFlatItemList.size()) {
            throw new IllegalStateException("Trying to bind item out of bounds, size " + this.mFlatItemList.size() + " flatPosition " + flatPosition + ". Was the data changed without a call to notify...()?");
        } else {
            ExpandableWrapper<P, C> listItem = (ExpandableWrapper) this.mFlatItemList.get(flatPosition);
            if (listItem.isParent()) {
                PVH parentViewHolder = (PVH) holder;
                if (parentViewHolder.shouldItemViewClickToggleExpansion()) {
                    parentViewHolder.setMainItemClickToExpand();
                }

                parentViewHolder.setExpanded(listItem.isExpanded());
                parentViewHolder.mParent = listItem.getParent();
                this.onBindParentViewHolder(parentViewHolder, this.getNearestParentPosition(flatPosition), listItem.getParent());
            } else {
                CVH childViewHolder = (CVH) holder;
                childViewHolder.mChild = listItem.getChild();
                this.onBindChildViewHolder(childViewHolder, this.getNearestParentPosition(flatPosition), this.getChildPosition(flatPosition), listItem.getChild());
            }

        }
    }

    @NonNull
    @UiThread
    public abstract PVH onCreateParentViewHolder(@NonNull ViewGroup var1, int var2);

    @NonNull
    @UiThread
    public abstract CVH onCreateChildViewHolder(@NonNull ViewGroup var1, int var2);

    @UiThread
    public abstract void onBindParentViewHolder(@NonNull PVH var1, int var2, @NonNull P var3);

    @UiThread
    public abstract void onBindChildViewHolder(@NonNull CVH var1, int var2, int var3, @NonNull C var4);

    @UiThread
    public int getItemCount() {
        return this.mFlatItemList.size();
    }

    @UiThread
    public int getItemViewType(int flatPosition) {
        ExpandableWrapper<P, C> listItem = (ExpandableWrapper) this.mFlatItemList.get(flatPosition);
        return listItem.isParent() ? this.getParentViewType(this.getNearestParentPosition(flatPosition)) : this.getChildViewType(this.getNearestParentPosition(flatPosition), this.getChildPosition(flatPosition));
    }

    public int getParentViewType(int parentPosition) {
        return 0;
    }

    public int getChildViewType(int parentPosition, int childPosition) {
        return 1;
    }

    public boolean isParentViewType(int viewType) {
        return viewType == 0;
    }

    @NonNull
    @UiThread
    public List<P> getParentList() {
        return this.mParentList;
    }

    @UiThread
    public void setParentList(@NonNull List<P> parentList, boolean preserveExpansionState) {
        this.mParentList = parentList;
        this.notifyParentDataSetChanged(preserveExpansionState);
    }

    @UiThread
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.mAttachedRecyclerViewPool.add(recyclerView);
    }

    @UiThread
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.mAttachedRecyclerViewPool.remove(recyclerView);
    }

    @UiThread
    public void setExpandCollapseListener(@Nullable ExpandableRecyclerAdapter.ExpandCollapseListener expandCollapseListener) {
        this.mExpandCollapseListener = expandCollapseListener;
    }

    @UiThread
    protected void parentExpandedFromViewHolder(int flatParentPosition) {
//        collapseAllParents();
        try {
            ExpandableWrapper<P, C> parentWrapper = (ExpandableWrapper) this.mFlatItemList.get(flatParentPosition);
            this.updateExpandedParent(parentWrapper, flatParentPosition, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @UiThread
    protected void parentCollapsedFromViewHolder(int flatParentPosition) {
        ExpandableWrapper<P, C> parentWrapper = (ExpandableWrapper) this.mFlatItemList.get(flatParentPosition);
        this.updateCollapsedParent(parentWrapper, flatParentPosition, true);
    }

    @UiThread
    public void expandParent(@NonNull P parent) {
//        collapseAllParents();
        ExpandableWrapper<P, C> parentWrapper = new ExpandableWrapper(parent);
        int flatParentPosition = this.mFlatItemList.indexOf(parentWrapper);
        if (flatParentPosition != -1) {
            this.expandViews((ExpandableWrapper) this.mFlatItemList.get(flatParentPosition), flatParentPosition);
        }
    }

    @UiThread
    public void expandParent(int parentPosition) {
//        collapseAllParents();
        this.expandParent((P) this.mParentList.get(parentPosition));
    }

    @UiThread
    public void expandParentRange(int startParentPosition, int parentCount) {
        int endParentPosition = startParentPosition + parentCount;

        for (int i = startParentPosition; i < endParentPosition; ++i) {
            this.expandParent(i);
        }

    }

    @UiThread
    public void expandAllParents() {
        Iterator var1 = this.mParentList.iterator();

        while (var1.hasNext()) {
            P parent = (P) var1.next();
            this.expandParent(parent);
        }

    }

    @UiThread
    public void collapseParent(@NonNull P parent) {
        ExpandableWrapper<P, C> parentWrapper = new ExpandableWrapper(parent);
        int flatParentPosition = this.mFlatItemList.indexOf(parentWrapper);
        if (flatParentPosition != -1) {
            this.collapseViews((ExpandableWrapper) this.mFlatItemList.get(flatParentPosition), flatParentPosition);
        }
    }

    @UiThread
    public void collapseParent(int parentPosition) {
        this.collapseParent((P) this.mParentList.get(parentPosition));
    }

    @UiThread
    public void collapseParentRange(int startParentPosition, int parentCount) {
        int endParentPosition = startParentPosition + parentCount;

        for (int i = startParentPosition; i < endParentPosition; ++i) {
            this.collapseParent(i);
        }

    }

    @UiThread
    public void collapseAllParents() {
        Iterator var1 = this.mParentList.iterator();

        while (var1.hasNext()) {
            P parent = (P) var1.next();
            this.collapseParent(parent);
        }

    }

    private void collapseAllParents_custom(int flatParentPosition) {

        expandAllParents();
        collapseAllParents();

//        Iterator var1 = this.mParentList.iterator();
//        int iter = 0;
//        while (var1.hasNext()) {
//            P parent = (P) var1.next();
////            if(parent.isInitiallyExpanded())
////                this.collapseParent(parent);
//            ExpandableRecyclerAdapter.this.parentCollapsedFromViewHolder(iter);
//            iter++;
//        }
        ExpandableRecyclerAdapter.this.parentExpandedFromViewHolder(flatParentPosition);

    }

    @UiThread
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        savedInstanceState.putSerializable("ExpandableRecyclerAdapter.ExpandedStateMap", this.generateExpandedStateMap());
    }

    @UiThread
    public void onRestoreInstanceState(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey("ExpandableRecyclerAdapter.ExpandedStateMap")) {
            HashMap<Integer, Boolean> expandedStateMap = (HashMap) savedInstanceState.getSerializable("ExpandableRecyclerAdapter.ExpandedStateMap");
            if (expandedStateMap != null) {
                List<ExpandableWrapper<P, C>> itemList = new ArrayList();
                int parentsCount = this.mParentList.size();

                for (int i = 0; i < parentsCount; ++i) {
                    ExpandableWrapper<P, C> parentWrapper = new ExpandableWrapper((Parent) this.mParentList.get(i));
                    itemList.add(parentWrapper);
                    if (expandedStateMap.containsKey(i)) {
                        boolean expanded = (Boolean) expandedStateMap.get(i);
                        parentWrapper.setExpanded(expanded);
                        if (expanded) {
                            List<ExpandableWrapper<P, C>> wrappedChildList = parentWrapper.getWrappedChildList();
                            int childrenCount = wrappedChildList.size();

                            for (int j = 0; j < childrenCount; ++j) {
                                ExpandableWrapper<P, C> childWrapper = (ExpandableWrapper) wrappedChildList.get(j);
                                itemList.add(childWrapper);
                            }
                        }
                    }
                }

                this.mFlatItemList = itemList;
                this.notifyDataSetChanged();
            }
        }
    }

    @UiThread
    private void expandViews(@NonNull ExpandableWrapper<P, C> parentWrapper, int flatParentPosition) {
//        collapseAllParents();
        Iterator var4 = this.mAttachedRecyclerViewPool.iterator();

        while (var4.hasNext()) {
            RecyclerView recyclerView = (RecyclerView) var4.next();
            PVH viewHolder = (PVH) recyclerView.findViewHolderForAdapterPosition(flatParentPosition);
            if (viewHolder != null && !viewHolder.isExpanded()) {
                viewHolder.setExpanded(true);
                viewHolder.onExpansionToggled(false);
            }
        }

        this.updateExpandedParent(parentWrapper, flatParentPosition, false);
    }

    @UiThread
    private void collapseViews(@NonNull ExpandableWrapper<P, C> parentWrapper, int flatParentPosition) {
        Iterator var4 = this.mAttachedRecyclerViewPool.iterator();

        while (var4.hasNext()) {
            RecyclerView recyclerView = (RecyclerView) var4.next();
            PVH viewHolder = (PVH) recyclerView.findViewHolderForAdapterPosition(flatParentPosition);
            if (viewHolder != null && viewHolder.isExpanded()) {
                viewHolder.setExpanded(false);
                viewHolder.onExpansionToggled(true);
            }
        }

        this.updateCollapsedParent(parentWrapper, flatParentPosition, false);
    }

    @UiThread
    private void updateExpandedParent(@NonNull ExpandableWrapper<P, C> parentWrapper, int flatParentPosition, boolean expansionTriggeredByListItemClick) {
//        collapseAllParents();
        if (!parentWrapper.isExpanded()) {
            parentWrapper.setExpanded(true);
            this.mExpansionStateMap.put(parentWrapper.getParent(), true);
            List<ExpandableWrapper<P, C>> wrappedChildList = parentWrapper.getWrappedChildList();
            if (wrappedChildList != null) {
                int childCount = wrappedChildList.size();

                for (int i = 0; i < childCount; ++i) {
                    this.mFlatItemList.add(flatParentPosition + i + 1, wrappedChildList.get(i));
                }

                this.notifyItemRangeInserted(flatParentPosition + 1, childCount);
            }

            if (expansionTriggeredByListItemClick && this.mExpandCollapseListener != null) {
                this.mExpandCollapseListener.onParentExpanded(this.getNearestParentPosition(flatParentPosition));
            }

        } else {
            parentWrapper.setExpanded(false);

        }
    }

    @UiThread
    private void updateCollapsedParent(@NonNull ExpandableWrapper<P, C> parentWrapper, int flatParentPosition, boolean collapseTriggeredByListItemClick) {
        if (parentWrapper.isExpanded()) {
            parentWrapper.setExpanded(false);
            this.mExpansionStateMap.put(parentWrapper.getParent(), false);
            List<ExpandableWrapper<P, C>> wrappedChildList = parentWrapper.getWrappedChildList();
            if (wrappedChildList != null) {
                int childCount = wrappedChildList.size();

                for (int i = childCount - 1; i >= 0; --i) {
                    this.mFlatItemList.remove(flatParentPosition + i + 1);
                }

                this.notifyItemRangeRemoved(flatParentPosition + 1, childCount);
            }

            if (collapseTriggeredByListItemClick && this.mExpandCollapseListener != null) {
                this.mExpandCollapseListener.onParentCollapsed(this.getNearestParentPosition(flatParentPosition));
            }

        }
    }

    @UiThread
    int getNearestParentPosition(int flatPosition) {
        if (flatPosition == 0) {
            return 0;
        } else {
            int parentCount = -1;

            for (int i = 0; i <= flatPosition; ++i) {
                ExpandableWrapper<P, C> listItem = (ExpandableWrapper) this.mFlatItemList.get(i);
                if (listItem.isParent()) {
                    ++parentCount;
                }
            }

            return parentCount;
        }
    }

    @UiThread
    int getChildPosition(int flatPosition) {
        if (flatPosition == 0) {
            return 0;
        } else {
            int childCount = 0;

            for (int i = 0; i < flatPosition; ++i) {
                ExpandableWrapper<P, C> listItem = (ExpandableWrapper) this.mFlatItemList.get(i);
                if (listItem.isParent()) {
                    childCount = 0;
                } else {
                    ++childCount;
                }
            }

            return childCount;
        }
    }

    @UiThread
    public void notifyParentDataSetChanged(boolean preserveExpansionState) {
        if (preserveExpansionState) {
            this.mFlatItemList = this.generateFlattenedParentChildList(this.mParentList, this.mExpansionStateMap);
        } else {
            this.mFlatItemList = this.generateFlattenedParentChildList(this.mParentList);
        }

        this.notifyDataSetChanged();
    }

    @UiThread
    public void notifyParentInserted(int parentPosition) {
        P parent = (P) this.mParentList.get(parentPosition);
        int flatParentPosition;
        if (parentPosition < this.mParentList.size() - 1) {
            flatParentPosition = this.getFlatParentPosition(parentPosition);
        } else {
            flatParentPosition = this.mFlatItemList.size();
        }

        int sizeChanged = this.addParentWrapper(flatParentPosition, parent);
        this.notifyItemRangeInserted(flatParentPosition, sizeChanged);
    }

    @UiThread
    public void notifyParentRangeInserted(int parentPositionStart, int itemCount) {
        int initialFlatParentPosition;
        if (parentPositionStart < this.mParentList.size() - itemCount) {
            initialFlatParentPosition = this.getFlatParentPosition(parentPositionStart);
        } else {
            initialFlatParentPosition = this.mFlatItemList.size();
        }

        int sizeChanged = 0;
        int flatParentPosition = initialFlatParentPosition;
        int parentPositionEnd = parentPositionStart + itemCount;

        for (int i = parentPositionStart; i < parentPositionEnd; ++i) {
            P parent = (P) this.mParentList.get(i);
            int changed = this.addParentWrapper(flatParentPosition, parent);
            flatParentPosition += changed;
            sizeChanged += changed;
        }

        this.notifyItemRangeInserted(initialFlatParentPosition, sizeChanged);
    }

    @UiThread
    private int addParentWrapper(int flatParentPosition, P parent) {
        int sizeChanged = 1;
        ExpandableWrapper<P, C> parentWrapper = new ExpandableWrapper(parent);
        this.mFlatItemList.add(flatParentPosition, parentWrapper);
        if (parentWrapper.isParentInitiallyExpanded()) {
            parentWrapper.setExpanded(true);
            List<ExpandableWrapper<P, C>> wrappedChildList = parentWrapper.getWrappedChildList();
            this.mFlatItemList.addAll(flatParentPosition + sizeChanged, wrappedChildList);
            sizeChanged += wrappedChildList.size();
        }

        return sizeChanged;
    }

    @UiThread
    public void notifyParentRemoved(int parentPosition) {
        int flatParentPosition = this.getFlatParentPosition(parentPosition);
        int sizeChanged = this.removeParentWrapper(flatParentPosition);
        this.notifyItemRangeRemoved(flatParentPosition, sizeChanged);
    }

    public void notifyParentRangeRemoved(int parentPositionStart, int itemCount) {
        int sizeChanged = 0;
        int flatParentPositionStart = this.getFlatParentPosition(parentPositionStart);

        for (int i = 0; i < itemCount; ++i) {
            sizeChanged += this.removeParentWrapper(flatParentPositionStart);
        }

        this.notifyItemRangeRemoved(flatParentPositionStart, sizeChanged);
    }

    @UiThread
    private int removeParentWrapper(int flatParentPosition) {
        int sizeChanged = 1;
        ExpandableWrapper<P, C> parentWrapper = (ExpandableWrapper) this.mFlatItemList.remove(flatParentPosition);
        if (parentWrapper.isExpanded()) {
            int childListSize = parentWrapper.getWrappedChildList().size();

            for (int i = 0; i < childListSize; ++i) {
                this.mFlatItemList.remove(flatParentPosition);
                ++sizeChanged;
            }
        }

        return sizeChanged;
    }

    @UiThread
    public void notifyParentChanged(int parentPosition) {
        P parent = (P) this.mParentList.get(parentPosition);
        int flatParentPositionStart = this.getFlatParentPosition(parentPosition);
        int sizeChanged = this.changeParentWrapper(flatParentPositionStart, parent);
        this.notifyItemRangeChanged(flatParentPositionStart, sizeChanged);
    }

    @UiThread
    public void notifyParentRangeChanged(int parentPositionStart, int itemCount) {
        int flatParentPositionStart = this.getFlatParentPosition(parentPositionStart);
        int flatParentPosition = flatParentPositionStart;
        int sizeChanged = 0;

        for (int j = 0; j < itemCount; ++j) {
            P parent = (P) this.mParentList.get(parentPositionStart);
            int changed = this.changeParentWrapper(flatParentPosition, parent);
            sizeChanged += changed;
            flatParentPosition += changed;
            ++parentPositionStart;
        }

        this.notifyItemRangeChanged(flatParentPositionStart, sizeChanged);
    }

    private int changeParentWrapper(int flatParentPosition, P parent) {
        ExpandableWrapper<P, C> parentWrapper = (ExpandableWrapper) this.mFlatItemList.get(flatParentPosition);
        parentWrapper.setParent(parent);
        int sizeChanged = 1;
        if (parentWrapper.isExpanded()) {
            List<ExpandableWrapper<P, C>> wrappedChildList = parentWrapper.getWrappedChildList();
            int childSize = wrappedChildList.size();

            for (int i = 0; i < childSize; ++i) {
                this.mFlatItemList.set(flatParentPosition + i + 1, wrappedChildList.get(i));
                ++sizeChanged;
            }
        }

        return sizeChanged;
    }

    @UiThread
    public void notifyParentMoved(int fromParentPosition, int toParentPosition) {
        int fromFlatParentPosition = this.getFlatParentPosition(fromParentPosition);
        ExpandableWrapper<P, C> fromParentWrapper = (ExpandableWrapper) this.mFlatItemList.get(fromFlatParentPosition);
        boolean isCollapsed = !fromParentWrapper.isExpanded();
        boolean isExpandedNoChildren = !isCollapsed && fromParentWrapper.getWrappedChildList().size() == 0;
        int sizeChanged;
        int toFlatParentPosition;
        if (!isCollapsed && !isExpandedNoChildren) {
            sizeChanged = 0;
            int childListSize = fromParentWrapper.getWrappedChildList().size();

            for (toFlatParentPosition = 0; toFlatParentPosition < childListSize + 1; ++toFlatParentPosition) {
                this.mFlatItemList.remove(fromFlatParentPosition);
                ++sizeChanged;
            }

            this.notifyItemRangeRemoved(fromFlatParentPosition, sizeChanged);
            toFlatParentPosition = this.getFlatParentPosition(toParentPosition);
            int childOffset = 0;
            if (toFlatParentPosition != -1) {
                ExpandableWrapper<P, C> toParentWrapper = (ExpandableWrapper) this.mFlatItemList.get(toFlatParentPosition);
                if (toParentWrapper.isExpanded()) {
                    childOffset = toParentWrapper.getWrappedChildList().size();
                }
            } else {
                toFlatParentPosition = this.mFlatItemList.size();
            }

            this.mFlatItemList.add(toFlatParentPosition + childOffset, fromParentWrapper);
            List<ExpandableWrapper<P, C>> wrappedChildList = fromParentWrapper.getWrappedChildList();
            sizeChanged = wrappedChildList.size() + 1;
            this.mFlatItemList.addAll(toFlatParentPosition + childOffset + 1, wrappedChildList);
            this.notifyItemRangeInserted(toFlatParentPosition + childOffset, sizeChanged);
        } else {
            sizeChanged = this.getFlatParentPosition(toParentPosition);
            ExpandableWrapper<P, C> toParentWrapper = (ExpandableWrapper) this.mFlatItemList.get(sizeChanged);
            this.mFlatItemList.remove(fromFlatParentPosition);
            toFlatParentPosition = 0;
            if (toParentWrapper.isExpanded()) {
                toFlatParentPosition = toParentWrapper.getWrappedChildList().size();
            }

            this.mFlatItemList.add(sizeChanged + toFlatParentPosition, fromParentWrapper);
            this.notifyItemMoved(fromFlatParentPosition, sizeChanged + toFlatParentPosition);
        }

    }

    @UiThread
    public void notifyChildInserted(int parentPosition, int childPosition) {
        int flatParentPosition = this.getFlatParentPosition(parentPosition);
        ExpandableWrapper<P, C> parentWrapper = (ExpandableWrapper) this.mFlatItemList.get(flatParentPosition);
        parentWrapper.setParent((P) this.mParentList.get(parentPosition));
        if (parentWrapper.isExpanded()) {
            ExpandableWrapper<P, C> child = (ExpandableWrapper) parentWrapper.getWrappedChildList().get(childPosition);
            this.mFlatItemList.add(flatParentPosition + childPosition + 1, child);
            this.notifyItemInserted(flatParentPosition + childPosition + 1);
        }

    }

    @UiThread
    public void notifyChildRangeInserted(int parentPosition, int childPositionStart, int itemCount) {
        int flatParentPosition = this.getFlatParentPosition(parentPosition);
        ExpandableWrapper<P, C> parentWrapper = (ExpandableWrapper) this.mFlatItemList.get(flatParentPosition);
        parentWrapper.setParent((P) this.mParentList.get(parentPosition));
        if (parentWrapper.isExpanded()) {
            List<ExpandableWrapper<P, C>> wrappedChildList = parentWrapper.getWrappedChildList();

            for (int i = 0; i < itemCount; ++i) {
                ExpandableWrapper<P, C> child = (ExpandableWrapper) wrappedChildList.get(childPositionStart + i);
                this.mFlatItemList.add(flatParentPosition + childPositionStart + i + 1, child);
            }

            this.notifyItemRangeInserted(flatParentPosition + childPositionStart + 1, itemCount);
        }

    }

    @UiThread
    public void notifyChildRemoved(int parentPosition, int childPosition) {
        int flatParentPosition = this.getFlatParentPosition(parentPosition);
        ExpandableWrapper<P, C> parentWrapper = (ExpandableWrapper) this.mFlatItemList.get(flatParentPosition);
        parentWrapper.setParent((P) this.mParentList.get(parentPosition));
        if (parentWrapper.isExpanded()) {
            this.mFlatItemList.remove(flatParentPosition + childPosition + 1);
            this.notifyItemRemoved(flatParentPosition + childPosition + 1);
        }

    }

    @UiThread
    public void notifyChildRangeRemoved(int parentPosition, int childPositionStart, int itemCount) {
        int flatParentPosition = this.getFlatParentPosition(parentPosition);
        ExpandableWrapper<P, C> parentWrapper = (ExpandableWrapper) this.mFlatItemList.get(flatParentPosition);
        parentWrapper.setParent((P) this.mParentList.get(parentPosition));
        if (parentWrapper.isExpanded()) {
            for (int i = 0; i < itemCount; ++i) {
                this.mFlatItemList.remove(flatParentPosition + childPositionStart + 1);
            }

            this.notifyItemRangeRemoved(flatParentPosition + childPositionStart + 1, itemCount);
        }

    }

    @UiThread
    public void notifyChildChanged(int parentPosition, int childPosition) {
        P parent = (P) this.mParentList.get(parentPosition);
        int flatParentPosition = this.getFlatParentPosition(parentPosition);
        ExpandableWrapper<P, C> parentWrapper = (ExpandableWrapper) this.mFlatItemList.get(flatParentPosition);
        parentWrapper.setParent(parent);
        if (parentWrapper.isExpanded()) {
            int flatChildPosition = flatParentPosition + childPosition + 1;
            ExpandableWrapper<P, C> child = (ExpandableWrapper) parentWrapper.getWrappedChildList().get(childPosition);
            this.mFlatItemList.set(flatChildPosition, child);
            this.notifyItemChanged(flatChildPosition);
        }

    }

    @UiThread
    public void notifyChildRangeChanged(int parentPosition, int childPositionStart, int itemCount) {
        P parent = (P) this.mParentList.get(parentPosition);
        int flatParentPosition = this.getFlatParentPosition(parentPosition);
        ExpandableWrapper<P, C> parentWrapper = (ExpandableWrapper) this.mFlatItemList.get(flatParentPosition);
        parentWrapper.setParent(parent);
        if (parentWrapper.isExpanded()) {
            int flatChildPosition = flatParentPosition + childPositionStart + 1;

            for (int i = 0; i < itemCount; ++i) {
                ExpandableWrapper<P, C> child = (ExpandableWrapper) parentWrapper.getWrappedChildList().get(childPositionStart + i);
                this.mFlatItemList.set(flatChildPosition + i, child);
            }

            this.notifyItemRangeChanged(flatChildPosition, itemCount);
        }

    }

    @UiThread
    public void notifyChildMoved(int parentPosition, int fromChildPosition, int toChildPosition) {
        P parent = (P) this.mParentList.get(parentPosition);
        int flatParentPosition = this.getFlatParentPosition(parentPosition);
        ExpandableWrapper<P, C> parentWrapper = (ExpandableWrapper) this.mFlatItemList.get(flatParentPosition);
        parentWrapper.setParent(parent);
        if (parentWrapper.isExpanded()) {
            ExpandableWrapper<P, C> fromChild = (ExpandableWrapper) this.mFlatItemList.remove(flatParentPosition + 1 + fromChildPosition);
            this.mFlatItemList.add(flatParentPosition + 1 + toChildPosition, fromChild);
            this.notifyItemMoved(flatParentPosition + 1 + fromChildPosition, flatParentPosition + 1 + toChildPosition);
        }

    }

    private List<ExpandableWrapper<P, C>> generateFlattenedParentChildList(List<P> parentList) {
        List<ExpandableWrapper<P, C>> flatItemList = new ArrayList();
        int parentCount = parentList.size();

        for (int i = 0; i < parentCount; ++i) {
            P parent = (P) parentList.get(i);
            this.generateParentWrapper(flatItemList, parent, parent.isInitiallyExpanded());
        }

        return flatItemList;
    }

    private List<ExpandableWrapper<P, C>> generateFlattenedParentChildList(List<P> parentList, Map<P, Boolean> savedLastExpansionState) {
        List<ExpandableWrapper<P, C>> flatItemList = new ArrayList();
        int parentCount = parentList.size();

        for (int i = 0; i < parentCount; ++i) {
            P parent = (P) parentList.get(i);
            Boolean lastExpandedState = (Boolean) savedLastExpansionState.get(parent);
            boolean shouldExpand = lastExpandedState == null ? parent.isInitiallyExpanded() : lastExpandedState;
            this.generateParentWrapper(flatItemList, parent, shouldExpand);
        }

        return flatItemList;
    }

    private void generateParentWrapper(List<ExpandableWrapper<P, C>> flatItemList, P parent, boolean shouldExpand) {
        ExpandableWrapper<P, C> parentWrapper = new ExpandableWrapper(parent);
        flatItemList.add(parentWrapper);
        if (shouldExpand) {
            this.generateExpandedChildren(flatItemList, parentWrapper);
        }

    }

    private void generateExpandedChildren(List<ExpandableWrapper<P, C>> flatItemList, ExpandableWrapper<P, C> parentWrapper) {
        parentWrapper.setExpanded(true);
        List<ExpandableWrapper<P, C>> wrappedChildList = parentWrapper.getWrappedChildList();
        int childCount = wrappedChildList.size();

        for (int j = 0; j < childCount; ++j) {
            ExpandableWrapper<P, C> childWrapper = (ExpandableWrapper) wrappedChildList.get(j);
            flatItemList.add(childWrapper);
        }

    }

    @NonNull
    @UiThread
    private HashMap<Integer, Boolean> generateExpandedStateMap() {
        HashMap<Integer, Boolean> parentHashMap = new HashMap();
        int childCount = 0;
        int listItemCount = this.mFlatItemList.size();

        for (int i = 0; i < listItemCount; ++i) {
            if (this.mFlatItemList.get(i) != null) {
                ExpandableWrapper<P, C> listItem = (ExpandableWrapper) this.mFlatItemList.get(i);
                if (listItem.isParent()) {
                    parentHashMap.put(i - childCount, listItem.isExpanded());
                } else {
                    ++childCount;
                }
            }
        }

        return parentHashMap;
    }

    @UiThread
    private int getFlatParentPosition(int parentPosition) {
        int parentCount = 0;
        int listItemCount = this.mFlatItemList.size();

        for (int i = 0; i < listItemCount; ++i) {
            if (((ExpandableWrapper) this.mFlatItemList.get(i)).isParent()) {
                ++parentCount;
                if (parentCount > parentPosition) {
                    return i;
                }
            }
        }

        return -1;
    }

    public interface ExpandCollapseListener {
        @UiThread
        void onParentExpanded(int var1);

        @UiThread
        void onParentCollapsed(int var1);
    }
}

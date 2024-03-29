/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model.value.swing;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionListener;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;

/**
 * This ListSelectionModel is aware of the ListModel and
 * provides convenience methods to access and set the
 * selected *objects*, as opposed to the selected *indexes*.
 */
public class ObjectListSelectionModel
	extends DefaultListSelectionModel
{
	/** The list model referenced by the list selection model. */
	private final ListModel listModel;

	/** A listener that allows us to clear the selection when the list model has changed. */
	private final ListDataListener listDataListener;

	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Construct a list selection model for the specified list model.
	 */
	public ObjectListSelectionModel(ListModel listModel) {
		super();
		this.listModel = listModel;
		this.listDataListener = this.buildListDataListener();
	}


	// ********** initialization **********

	private ListDataListener buildListDataListener() {
		return new ListDataListener() {
			public void intervalAdded(ListDataEvent event) {
				// this does not affect the selection
			}
			public void intervalRemoved(ListDataEvent event) {
				// this does not affect the selection
			}
			public void contentsChanged(ListDataEvent event) {
				ObjectListSelectionModel.this.listModelContentsChanged(event);
			}
			@Override
			public String toString() {
				return "list data listener"; //$NON-NLS-1$
			}
		};
	}

	/**
	 * Typically, the selection does not need to be cleared when the
	 * contents of the list have changed. Most of the time this just
	 * means an item has changed in a way that affects its display string
	 * or icon. We typically only use the class for edits involving
	 * single selection.
	 * A subclass can override this method if the selection
	 * should be cleared because a change could mean the selection is invalid.
	 */
	protected void listModelContentsChanged(@SuppressWarnings("unused") ListDataEvent event) {
		/**this.clearSelection();*/
	}


	// ********** ListSelectionModel implementation **********

	@Override
	public void addListSelectionListener(ListSelectionListener l) {
		if (this.hasNoListSelectionListeners()) {
			this.listModel.addListDataListener(this.listDataListener);
		}
		super.addListSelectionListener(l);
	}

	@Override
	public void removeListSelectionListener(ListSelectionListener l) {
		super.removeListSelectionListener(l);
		if (this.hasNoListSelectionListeners()) {
			this.listModel.removeListDataListener(this.listDataListener);
		}
	}


	// ********** queries **********

	/**
	 * Return whether this model has no listeners.
	 */
	protected boolean hasNoListSelectionListeners() {	// private-protected
		return this.getListSelectionListeners().length == 0;
	}

	/**
	 * Return the list model referenced by the list selection model.
	 */
	public ListModel getListModel() {
		return this.listModel;
	}

	public int selectedValuesSize() {
		int min = this.getMinSelectionIndex();
		int max = this.getMaxSelectionIndex();

		if ((min < 0) || (max < 0)) {
			return 0;
		}

		int n = 0;
		int count = this.getListModel().getSize();
		for (int i = min; i <= max; i++) {
			if (this.isSelectedIndex(i) && (i < count)) {
				n++;
			}
		}
		return n;
	}

	/**
	 * Return the first selected value.
	 * Return null if the selection is empty.
	 */
	public Object selectedValue() {
		int index = this.getMinSelectionIndex();
		if (index == -1) {
			return null;
		}
		if (this.getListModel().getSize() <= index) {
			return null;
		}
		return this.getListModel().getElementAt(index);
	}

	/**
	 * Return an array of the selected values.
	 */
	public Object[] selectedValues() {
		int min = this.getMinSelectionIndex();
		int max = this.getMaxSelectionIndex();

		if ((min < 0) || (max < 0)) {
			return ObjectTools.EMPTY_OBJECT_ARRAY;
		}

		int maxSize = (max - min) + 1;
		Object[] temp = new Object[maxSize];
		int n = 0;
		int count = this.getListModel().getSize();
		for (int i = min; i <= max; i++) {
			if (this.isSelectedIndex(i) && (i < count)) {
				temp[n++] = this.getListModel().getElementAt(i);
			}
		}
		if (n == maxSize) {
			// all the elements in the range were selected
			return temp;
		}
		// only some of the elements in the range were selected
		Object[] result = new Object[n];
		System.arraycopy(temp, 0, result, 0, n);
		return result;
	}

	/**
	 * Return an array of the selected indices in order.
	 */
	public int[] selectedIndices() {
		int min = this.getMinSelectionIndex();
		int max = this.getMaxSelectionIndex();

		if ((min < 0) || (max < 0)) {
			return new int[0];
		}

		int maxSize = (max - min) + 1;
		int[] temp = new int[maxSize];
		int n = 0;
		int count = this.getListModel().getSize();
		for (int i = min; i <= max; i++) {
			if (this.isSelectedIndex(i) && (i < count)) {
				temp[n++] = i;
			}
		}
		if (n == maxSize) {
			// all the elements in the range were selected
			Arrays.sort(temp);
			return temp;
		}
		// only some of the elements in the range were selected
		int[] result = new int[n];
		System.arraycopy(temp, 0, result, 0, n);
		Arrays.sort(result);
		return result;
	}

	/**
	 * Set the selected value.
	 */
	public void setSelectedValue(Object object) {
		this.setSelectedValues(IteratorTools.singletonIterator(object));
	}

	/**
	 * Set the current set of selected objects to the specified objects.
	 * @see javax.swing.ListSelectionModel#setSelectionInterval(int, int)
	 */
	public void setSelectedValues(Iterator<?> objects) {
		this.setValueIsAdjusting(true);
		this.clearSelection();
		this.addSelectedValuesInternal(objects);
		this.setValueIsAdjusting(false);
	}

	/**
	 * Set the current set of selected objects to the specified objects.
	 * @see javax.swing.ListSelectionModel#setSelectionInterval(int, int)
	 */
	public void setSelectedValues(Collection<?> objects) {
		this.setSelectedValues(objects.iterator());
	}

	/**
	 * Set the current set of selected objects to the specified objects.
	 * @see javax.swing.ListSelectionModel#setSelectionInterval(int, int)
	 */
	public void setSelectedValues(Object[] objects) {
		this.setSelectedValues(IteratorTools.iterator(objects));
	}

	/**
	 * Add the specified object to the current set of selected objects.
	 * @see javax.swing.ListSelectionModel#addSelectionInterval(int, int)
	 */
	public void addSelectedValue(Object object) {
		this.addSelectedValues(IteratorTools.singletonIterator(object));
	}

	/**
	 * Add the specified objects to the current set of selected objects.
	 * @see javax.swing.ListSelectionModel#addSelectionInterval(int, int)
	 */
	public void addSelectedValues(Iterator<?> objects) {
		this.setValueIsAdjusting(true);
		this.addSelectedValuesInternal(objects);
		this.setValueIsAdjusting(false);
	}

	/**
	 * Add the specified objects to the current set of selected objects.
	 * @see javax.swing.ListSelectionModel#addSelectionInterval(int, int)
	 */
	public void addSelectedValues(Collection<?> objects) {
		this.addSelectedValues(objects.iterator());
	}

	/**
	 * Add the specified objects to the current set of selected objects.
	 * @see javax.swing.ListSelectionModel#addSelectionInterval(int, int)
	 */
	public void addSelectedValues(Object[] objects) {
		this.addSelectedValues(IteratorTools.iterator(objects));
	}

	/**
	 * Remove the specified object from the current set of selected objects.
	 * @see javax.swing.ListSelectionModel#removeSelectionInterval(int, int)
	 */
	public void removeSelectedValue(Object object) {
		this.removeSelectedValues(IteratorTools.singletonIterator(object));
	}

	/**
	 * Remove the specified objects from the current set of selected objects.
	 * @see javax.swing.ListSelectionModel#removeSelectionInterval(int, int)
	 */
	public void removeSelectedValues(Iterator<?> objects) {
		this.setValueIsAdjusting(true);
		ListModel lm = this.getListModel();
		int lmSize = lm.getSize();
		while (objects.hasNext()) {
			int index = this.indexOf(objects.next(), lm, lmSize);
			this.removeSelectionInterval(index, index);
		}
		this.setValueIsAdjusting(false);
	}

	/**
	 * Remove the specified objects from the current set of selected objects.
	 * @see javax.swing.ListSelectionModel#removeSelectionInterval(int, int)
	 */
	public void removeSelectedValues(Collection<?> objects) {
		this.removeSelectedValues(objects.iterator());
	}

	/**
	 * Remove the specified objects from the current set of selected objects.
	 * @see javax.swing.ListSelectionModel#removeSelectionInterval(int, int)
	 */
	public void removeSelectedValues(Object[] objects) {
		this.removeSelectedValues(IteratorTools.iterator(objects));
	}

	/**
	 * @see javax.swing.ListSelectionModel#getAnchorSelectionIndex()
	 * Return null if the anchor selection is empty.
	 */
	public Object getAnchorSelectedValue() {
		int index = this.getAnchorSelectionIndex();
		if (index == -1) {
			return null;
		}
		return this.getListModel().getElementAt(index);
	}

	/**
	 * @see javax.swing.ListSelectionModel#setAnchorSelectionIndex(int)
	 */
	public void setAnchorSelectedValue(Object object) {
		this.setAnchorSelectionIndex(this.indexOf(object));
	}

	/**
	 * @see javax.swing.ListSelectionModel#getLeadSelectionIndex()
	 * Return null if the lead selection is empty.
	 */
	public Object getLeadSelectedValue() {
		int index = this.getLeadSelectionIndex();
		if (index == -1) {
			return null;
		}
		return this.getListModel().getElementAt(index);
	}

	/**
	 * @see javax.swing.ListSelectionModel#setLeadSelectionIndex(int)
	 */
	public void setLeadSelectedValue(Object object) {
		this.setLeadSelectionIndex(this.indexOf(object));
	}

	/**
	 * @see javax.swing.ListSelectionModel#getMaxSelectionIndex()
	 * Return null if the max selection is empty.
	 */
	public Object getMaxSelectedValue() {
		int index = this.getMaxSelectionIndex();
		if (index == -1) {
			return null;
		}
		return this.getListModel().getElementAt(index);
	}

	/**
	 * @see javax.swing.ListSelectionModel#getMinSelectionIndex()
	 * Return null if the min selection is empty.
	 */
	public Object getMinSelectedValue() {
		int index = this.getMinSelectionIndex();
		if (index == -1) {
			return null;
		}
		return this.getListModel().getElementAt(index);
	}

	/**
	 * @see javax.swing.ListSelectionModel#isSelectedIndex(int)
	 */
	public boolean valueIsSelected(Object object) {
		return this.isSelectedIndex(this.indexOf(object));
	}

	/**
	 * Add the specified objects to the current set of selected objects,
	 * without wrapping the actions in "adjusting" events.
	 */
	private void addSelectedValuesInternal(Iterator<?> objects) {
		ListModel lm = this.getListModel();
		int listModelSize = lm.getSize();
		while (objects.hasNext()) {
			int index = this.indexOf(objects.next(), lm, listModelSize);
			this.addSelectionInterval(index, index);
		}
	}

	/**
	 * Return the index in the list model of the specified object.
	 * Return -1 if the object is not in the list model.
	 */
	private int indexOf(Object object) {
		ListModel lm = this.getListModel();
		return this.indexOf(object, lm, lm.getSize());
	}

	/**
	 * Return the index in the list model of the specified object.
	 * Return -1 if the object is not in the list model.
	 */
	// we're just jerking around with performance optimizations here
	// (in memory of Phil...);
	// call this method inside loops that do not modify the listModel
	private int indexOf(Object object, ListModel lm, int listModelSize) {
		for (int i = listModelSize; i-- > 0; ) {
			if (lm.getElementAt(i).equals(object)) {
				return i;
			}
		}
		return -1;
	}

}

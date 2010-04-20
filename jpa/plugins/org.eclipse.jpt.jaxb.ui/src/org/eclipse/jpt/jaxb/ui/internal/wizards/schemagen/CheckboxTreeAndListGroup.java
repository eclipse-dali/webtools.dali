/*******************************************************************************
* Copyright (c) 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal.wizards.schemagen;

import java.util.Iterator;

import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Tree;

/**
 *  Façade class to change accessibility of CheckboxTreeAndListGroup.
 */
@SuppressWarnings("restriction")
public class CheckboxTreeAndListGroup extends org.eclipse.jdt.internal.ui.jarpackager.CheckboxTreeAndListGroup
{

	public CheckboxTreeAndListGroup(Composite parent, Object rootObject, ITreeContentProvider treeContentProvider, ILabelProvider treeLabelProvider, IStructuredContentProvider listContentProvider, ILabelProvider listLabelProvider, int style, int width, int height) {
		super(parent, rootObject, treeContentProvider, treeLabelProvider, listContentProvider, listLabelProvider, style, width, height);
	}

	/**
	 *	Sets the initial checked state of the passed element to true,
	 *	as well as to all of its children and associated list elements
	 */
	@Override
	public void initialCheckTreeItem(Object element) {
		super.initialCheckTreeItem(element) ;
	}

	/**
	 *	Sets the initial checked state of the passed list element to true.
	 */
	@Override
	public void initialCheckListItem(Object element) {
		super.initialCheckListItem(element) ;
	}

	@Override
	public void addCheckStateListener(ICheckStateListener listener) {
		super.addCheckStateListener(listener);
	}
	
	@Override
	public void addTreeFilter(ViewerFilter filter) {
		super.addTreeFilter(filter);
	}

	@Override
	public void addListFilter(ViewerFilter filter) {
		super.addListFilter(filter);
	}
	
	/**
	 *Sets the contents of the list viewer based upon the specified selected
	 *tree element.  This also includes checking the appropriate list items.
	 */
	@Override
	public void populateListViewer(final Object treeElement) {
		super.populateListViewer(treeElement);
	}
	
	@Override
	public Iterator<?> getAllCheckedListItems() {
		return super.getAllCheckedListItems();
	}

	@Override
	public Table getTable() {
		return super.getTable();
	}

	@Override
	public Tree getTree() {
		return super.getTree();
	}

	@Override
	public void setListComparator(ViewerComparator comparator) {
		super.setListComparator(comparator);
	}
	
	@Override
	public void setTreeComparator(ViewerComparator sorter) {
		super.setTreeComparator(sorter);
	}
}

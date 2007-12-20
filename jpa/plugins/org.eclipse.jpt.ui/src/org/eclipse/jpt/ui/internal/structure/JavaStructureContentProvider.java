/*******************************************************************************
 *  Copyright (c) 2007 Oracle.
 *  All rights reserved.  This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors:
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.structure;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jpt.core.internal.context.java.IJavaPersistentAttribute;
import org.eclipse.jpt.core.internal.context.java.IJavaPersistentType;
import org.eclipse.jpt.utility.internal.CollectionTools;

public class JavaStructureContentProvider extends ResourceModelStructureContentProvider
	implements ITreeContentProvider
{
	public JavaStructureContentProvider() {
		super();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		super.dispose();
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub
		super.inputChanged(viewer, oldInput, newInput);
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof IJavaPersistentType) {
			return CollectionTools.array(((IJavaPersistentType) parentElement).attributes());
		}

		return super.getChildren(parentElement);
	}

	@Override
	public Object getParent(Object element) {
		if (element instanceof IJavaPersistentAttribute) {
			return ((IJavaPersistentAttribute) element).persistentType();
		}

		return super.getParent(element);
	}

	@Override
	public boolean hasChildren(Object element) {
		return true;
	}
}

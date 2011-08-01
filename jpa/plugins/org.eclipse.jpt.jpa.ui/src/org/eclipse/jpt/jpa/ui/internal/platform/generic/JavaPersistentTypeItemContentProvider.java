/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.platform.generic;

import org.eclipse.jpt.common.ui.internal.jface.AbstractTreeItemContentProvider;
import org.eclipse.jpt.common.ui.internal.jface.DelegatingTreeContentAndLabelProvider;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListCollectionValueModelAdapter;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;

public class JavaPersistentTypeItemContentProvider extends AbstractTreeItemContentProvider<JavaPersistentAttribute>
{
	public JavaPersistentTypeItemContentProvider(
			JavaPersistentType persistentType, DelegatingTreeContentAndLabelProvider contentProvider) {
		super(persistentType, contentProvider);
	}
	
	@Override
	public JavaPersistentType getModel() {
		return (JavaPersistentType) super.getModel();
	}
	
	@Override
	public Object getParent() {
		return getModel().getParent();
	}
	
	@Override
	protected CollectionValueModel<JavaPersistentAttribute> buildChildrenModel() {
		return new ListCollectionValueModelAdapter<JavaPersistentAttribute>(
		new ListAspectAdapter<JavaPersistentType, JavaPersistentAttribute>(JavaPersistentType.ATTRIBUTES_LIST, getModel()) {
			@Override
			protected ListIterable<JavaPersistentAttribute> getListIterable() {
				return subject.getAttributes();
			}
			
			@Override
			protected int size_() {
				return subject.getAttributesSize();
			}
		});
	}
}
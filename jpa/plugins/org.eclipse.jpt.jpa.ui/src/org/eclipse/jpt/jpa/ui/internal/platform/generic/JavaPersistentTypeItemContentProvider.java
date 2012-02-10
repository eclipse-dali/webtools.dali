/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.platform.generic;

import org.eclipse.jpt.common.ui.internal.jface.AbstractItemTreeContentProvider;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListCollectionValueModelAdapter;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;

public class JavaPersistentTypeItemContentProvider
	extends AbstractItemTreeContentProvider<JavaPersistentType, JavaPersistentAttribute>
{
	public JavaPersistentTypeItemContentProvider(JavaPersistentType persistentType, Manager manager) {
		super(persistentType, manager);
	}

	public Object getParent() {
		return this.item.getParent();
	}
	
	@Override
	protected CollectionValueModel<JavaPersistentAttribute> buildChildrenModel() {
		return new ListCollectionValueModelAdapter<JavaPersistentAttribute>(new ChildrenModel(this.item));
	}

	protected static class ChildrenModel
		extends ListAspectAdapter<JavaPersistentType, JavaPersistentAttribute>
	{
		ChildrenModel(JavaPersistentType javaPersistentType) {
			super(JavaPersistentType.ATTRIBUTES_LIST, javaPersistentType);
		}

		@Override
		protected ListIterable<JavaPersistentAttribute> getListIterable() {
			return this.subject.getAttributes();
		}

		@Override
		public int size_() {
			return this.subject.getAttributesSize();
		}
	}
}

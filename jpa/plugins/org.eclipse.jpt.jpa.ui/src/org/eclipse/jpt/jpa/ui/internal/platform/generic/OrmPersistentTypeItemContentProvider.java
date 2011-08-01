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

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jpt.common.ui.internal.jface.AbstractTreeItemContentProvider;
import org.eclipse.jpt.common.ui.internal.jface.DelegatingTreeContentAndLabelProvider;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SuperListIterableWrapper;
import org.eclipse.jpt.common.utility.internal.model.value.CompositeCollectionValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListCollectionValueModelAdapter;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmReadOnlyPersistentAttribute;

public class OrmPersistentTypeItemContentProvider
	extends AbstractTreeItemContentProvider<OrmReadOnlyPersistentAttribute>
{
	public OrmPersistentTypeItemContentProvider(
			OrmPersistentType persistentType, DelegatingTreeContentAndLabelProvider contentProvider) {
		super(persistentType, contentProvider);
	}
	
	@Override
	public OrmPersistentType getModel() {
		return (OrmPersistentType) super.getModel();
	}
	
	@Override
	public Object getParent() {
		return getModel().getParent();
	}
	
	@Override
	protected CollectionValueModel<OrmReadOnlyPersistentAttribute> buildChildrenModel() {
		List<CollectionValueModel<OrmReadOnlyPersistentAttribute>> list = new ArrayList<CollectionValueModel<OrmReadOnlyPersistentAttribute>>(2);
		list.add(buildSpecifiedPersistentAttributesModel());
		list.add(buildVirtualPersistentAttributesModel());
		return new CompositeCollectionValueModel<CollectionValueModel<OrmReadOnlyPersistentAttribute>, OrmReadOnlyPersistentAttribute>(list);
	}
	
	protected CollectionValueModel<OrmReadOnlyPersistentAttribute> buildSpecifiedPersistentAttributesModel() {
		return new ListCollectionValueModelAdapter<OrmReadOnlyPersistentAttribute>(
		new ListAspectAdapter<OrmPersistentType, OrmReadOnlyPersistentAttribute>(OrmPersistentType.SPECIFIED_ATTRIBUTES_LIST, getModel()) {
			@Override
			protected ListIterable<OrmReadOnlyPersistentAttribute> getListIterable() {
				return new SuperListIterableWrapper<OrmReadOnlyPersistentAttribute>(this.getSpecifiedAttributes());
			}
			protected ListIterable<OrmPersistentAttribute> getSpecifiedAttributes() {
				return this.subject.getSpecifiedAttributes();
			}
			@Override
			protected int size_() {
				return this.subject.getSpecifiedAttributesSize();
			}
		});
	}
	
	protected CollectionValueModel<OrmReadOnlyPersistentAttribute> buildVirtualPersistentAttributesModel() {
		return new ListCollectionValueModelAdapter<OrmReadOnlyPersistentAttribute>(
		new ListAspectAdapter<OrmPersistentType, OrmReadOnlyPersistentAttribute>(OrmPersistentType.VIRTUAL_ATTRIBUTES_LIST, getModel()) {
			@Override
			protected ListIterable<OrmReadOnlyPersistentAttribute> getListIterable() {
				return this.subject.getVirtualAttributes();
			}
			@Override
			protected int size_() {
				return this.subject.getVirtualAttributesSize();
			}
		});
	}
}

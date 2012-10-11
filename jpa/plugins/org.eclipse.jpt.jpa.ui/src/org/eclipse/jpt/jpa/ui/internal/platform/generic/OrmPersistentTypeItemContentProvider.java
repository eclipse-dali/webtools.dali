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

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jpt.common.ui.internal.jface.AbstractItemTreeContentProvider;
import org.eclipse.jpt.common.utility.internal.iterable.SuperListIterableWrapper;
import org.eclipse.jpt.common.utility.internal.model.value.CompositeCollectionValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListCollectionValueModelAdapter;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmReadOnlyPersistentAttribute;

public class OrmPersistentTypeItemContentProvider
	extends AbstractItemTreeContentProvider<OrmPersistentType, OrmReadOnlyPersistentAttribute>
{
	public OrmPersistentTypeItemContentProvider(OrmPersistentType persistentType, Manager manager) {
		super(persistentType, manager);
	}

	public Object getParent() {
		return this.item.getParent();
	}

	@Override
	protected CollectionValueModel<OrmReadOnlyPersistentAttribute> buildChildrenModel() {
		List<CollectionValueModel<OrmReadOnlyPersistentAttribute>> list = new ArrayList<CollectionValueModel<OrmReadOnlyPersistentAttribute>>(2);
		list.add(this.buildSpecifiedPersistentAttributesModel());
		list.add(this.buildDefaultPersistentAttributesModel());
		return CompositeCollectionValueModel.forModels(list);
	}

	protected CollectionValueModel<OrmReadOnlyPersistentAttribute> buildSpecifiedPersistentAttributesModel() {
		return new ListCollectionValueModelAdapter<OrmReadOnlyPersistentAttribute>(new SpecifiedPersistentAttributesModel(this.item));
	}

	protected static class SpecifiedPersistentAttributesModel
		extends ListAspectAdapter<OrmPersistentType, OrmReadOnlyPersistentAttribute>
	{
		SpecifiedPersistentAttributesModel(OrmPersistentType ormPersistentType) {
			super(OrmPersistentType.SPECIFIED_ATTRIBUTES_LIST, ormPersistentType);
		}

		@Override
		protected ListIterable<OrmReadOnlyPersistentAttribute> getListIterable() {
			return new SuperListIterableWrapper<OrmReadOnlyPersistentAttribute>(this.getSpecifiedAttributes());
		}

		protected ListIterable<OrmPersistentAttribute> getSpecifiedAttributes() {
			return this.subject.getSpecifiedAttributes();
		}

		@Override
		public int size_() {
			return this.subject.getSpecifiedAttributesSize();
		}
	}

	protected CollectionValueModel<OrmReadOnlyPersistentAttribute> buildDefaultPersistentAttributesModel() {
		return new ListCollectionValueModelAdapter<OrmReadOnlyPersistentAttribute>(new DefaultPersistentAttributesModel(this.item));
	}

	protected static class DefaultPersistentAttributesModel
		extends ListAspectAdapter<OrmPersistentType, OrmReadOnlyPersistentAttribute>
	{
		DefaultPersistentAttributesModel(OrmPersistentType ormPersistentType) {
			super(OrmPersistentType.DEFAULT_ATTRIBUTES_LIST, ormPersistentType);
		}

		@Override
		protected ListIterable<OrmReadOnlyPersistentAttribute> getListIterable() {
			return this.subject.getDefaultAttributes();
		}

		@Override
		public int size_() {
			return this.subject.getDefaultAttributesSize();
		}
	}
}

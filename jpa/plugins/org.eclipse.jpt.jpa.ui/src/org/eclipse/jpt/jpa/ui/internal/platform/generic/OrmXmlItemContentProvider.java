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
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.jpa.core.context.XmlFile;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmXml;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;

public class OrmXmlItemContentProvider
	extends AbstractItemTreeContentProvider<OrmXml, OrmPersistentType>
{
	public OrmXmlItemContentProvider(OrmXml ormXml, Manager manager) {
		super(ormXml, manager);
	}

	public PersistenceUnit getParent() {
		return this.item.getPersistenceUnit();
	}
	
	@Override
	protected CollectionValueModel<OrmPersistentType> buildChildrenModel() {
		return new ListCollectionValueModelAdapter<OrmPersistentType>(new ChildrenModel(this.item));
	}

	protected static class ChildrenModel
		extends ListAspectAdapter<EntityMappings, OrmPersistentType>
	{
		ChildrenModel(OrmXml ormXml) {
			super(new EntityMappingsModel(ormXml), EntityMappings.PERSISTENT_TYPES_LIST);
		}

		@Override
		protected ListIterable<OrmPersistentType> getListIterable() {
			return subject.getPersistentTypes();
		}

		@Override
		protected int size_() {
			return subject.getPersistentTypesSize();
		}
	}

	protected static class EntityMappingsModel
		extends PropertyAspectAdapter<OrmXml, EntityMappings>
	{
		public EntityMappingsModel(OrmXml subject) {
			super(XmlFile.ROOT_PROPERTY, subject);
		}
		@Override
		protected EntityMappings buildValue_() {
			return this.subject.getRoot();
		}
	}
}

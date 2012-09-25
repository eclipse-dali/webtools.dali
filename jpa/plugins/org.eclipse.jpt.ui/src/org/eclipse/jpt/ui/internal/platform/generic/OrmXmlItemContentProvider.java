/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.platform.generic;

import org.eclipse.jpt.core.context.orm.EntityMappings;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmXml;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.ui.internal.jface.AbstractTreeItemContentProvider;
import org.eclipse.jpt.ui.internal.jface.DelegatingTreeContentAndLabelProvider;
import org.eclipse.jpt.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.ListCollectionValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;

public class OrmXmlItemContentProvider
	extends AbstractTreeItemContentProvider<OrmPersistentType>
{
	public OrmXmlItemContentProvider(
			OrmXml ormXml, DelegatingTreeContentAndLabelProvider contentProvider) {
		super(ormXml, contentProvider);
	}
	
	@Override
	public OrmXml getModel() {
		return (OrmXml) super.getModel();
	}
	
	@Override
	public PersistenceUnit getParent() {
		return getModel().getPersistenceUnit();
	}
	
	@Override
	protected CollectionValueModel<OrmPersistentType> buildChildrenModel() {
		return new ListCollectionValueModelAdapter<OrmPersistentType>(
		new ListAspectAdapter<EntityMappings, OrmPersistentType>(
				buildEntityMappingsHolder(),
				EntityMappings.PERSISTENT_TYPES_LIST) {
			@Override
			protected ListIterable<OrmPersistentType> getListIterable() {
				return subject.getPersistentTypes();
			}
			@Override
			protected int size_() {
				return subject.getPersistentTypesSize();
			}
		});
	}
	
	protected PropertyValueModel<EntityMappings> buildEntityMappingsHolder() {
		return new PropertyAspectAdapter<OrmXml, EntityMappings>(
				OrmXml.ENTITY_MAPPINGS_PROPERTY, getModel()) {
			@Override
			protected EntityMappings buildValue_() {
				return subject.getEntityMappings();
			}
		};
	}
}

/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.platform.generic;

import org.eclipse.jpt.common.ui.internal.jface.AbstractItemTreeContentProvider;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListCollectionValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.jpa.core.context.JpaContextModelRoot;
import org.eclipse.jpt.jpa.core.context.XmlFile;
import org.eclipse.jpt.jpa.core.context.persistence.Persistence;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceXml;

public class PersistenceXmlItemContentProvider
	extends AbstractItemTreeContentProvider<PersistenceXml, PersistenceUnit>
{
	public PersistenceXmlItemContentProvider(PersistenceXml persistenceXml, Manager manager) {
		super(persistenceXml, manager);
	}
	
	public JpaContextModelRoot getParent() {
		return (JpaContextModelRoot) this.item.getParent();
	}
	
	@Override
	protected CollectionValueModel<PersistenceUnit> buildChildrenModel() {
		return new ListCollectionValueModelAdapter<PersistenceUnit>(new ChildrenModel(this.item));
	}

	protected static class ChildrenModel
		extends ListAspectAdapter<Persistence, PersistenceUnit>
	{
		ChildrenModel(PersistenceXml subject) {
			super(new PersistenceModel(subject), Persistence.PERSISTENCE_UNITS_LIST);
		}

		@Override
		protected ListIterable<PersistenceUnit> getListIterable() {
			return this.subject.getPersistenceUnits();
		}

		@Override
		protected int size_() {
			return this.subject.getPersistenceUnitsSize();
		}
	}

	protected static class PersistenceModel
		extends PropertyAspectAdapter<PersistenceXml, Persistence>
	{
		public PersistenceModel(PersistenceXml subject) {
			super(XmlFile.ROOT_PROPERTY, subject);
		}
		@Override
		protected Persistence buildValue_() {
			return this.subject.getRoot();
		}
	}
}

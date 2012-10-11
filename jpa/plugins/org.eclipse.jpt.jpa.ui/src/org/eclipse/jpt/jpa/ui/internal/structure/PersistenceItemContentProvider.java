/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.structure;

import org.eclipse.jpt.common.ui.internal.jface.AbstractItemTreeContentProvider;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListCollectionValueModelAdapter;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.jpa.core.context.persistence.Persistence;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;

public class PersistenceItemContentProvider
	extends AbstractItemTreeContentProvider<Persistence, PersistenceUnit>
{
	public PersistenceItemContentProvider(Persistence persistence, Manager manager) {
		super(persistence, manager);
	}

	public Object getParent() {
		// I'd like to return the resource model here, but that involves a hefty
		// API change - we'll see what happens with this code first
		return null;
	}

	@Override
	protected CollectionValueModel<PersistenceUnit> buildChildrenModel() {
		return new ListCollectionValueModelAdapter<PersistenceUnit>(new ChildrenModel(this.item));
	}

	protected static class ChildrenModel
		extends ListAspectAdapter<Persistence, PersistenceUnit>
	{
		ChildrenModel(Persistence persistence) {
			super(Persistence.PERSISTENCE_UNITS_LIST, persistence);
		}

		@Override
		protected ListIterable<PersistenceUnit> getListIterable() {
			return this.subject.getPersistenceUnits();
		}

		@Override
		public int size_() {
			return this.subject.getPersistenceUnitsSize();
		}
	}
}
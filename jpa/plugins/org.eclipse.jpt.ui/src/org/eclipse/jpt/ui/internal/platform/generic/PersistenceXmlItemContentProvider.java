/*******************************************************************************
 *  Copyright (c) 2008  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.platform.generic;

import java.util.ListIterator;
import org.eclipse.jpt.core.context.JpaRootContextNode;
import org.eclipse.jpt.core.context.persistence.Persistence;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.ui.internal.jface.AbstractTreeItemContentProvider;
import org.eclipse.jpt.ui.internal.jface.DelegatingTreeContentAndLabelProvider;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.ListValueModel;

public class PersistenceXmlItemContentProvider
	extends AbstractTreeItemContentProvider<PersistenceUnit>
{
	public PersistenceXmlItemContentProvider(
			PersistenceXml persistenceXml, DelegatingTreeContentAndLabelProvider contentProvider) {
		super(persistenceXml, contentProvider);
	}
	
	@Override
	public PersistenceXml model() {
		return (PersistenceXml) super.model();
	}
	
	@Override
	public JpaRootContextNode getParent() {
		return (JpaRootContextNode) model().getParent();
	}
	
	@Override
	protected ListValueModel<PersistenceUnit> buildChildrenModel() {
		return new ListAspectAdapter<Persistence, PersistenceUnit>(
				new PropertyAspectAdapter<PersistenceXml, Persistence>(
						PersistenceXml.PERSISTENCE_PROPERTY, model()) {
					@Override
					protected Persistence buildValue_() {
						return subject.getPersistence();
					}
				},
				Persistence.PERSISTENCE_UNITS_LIST) {
			@Override
			protected ListIterator<PersistenceUnit> listIterator_() {
				return subject.persistenceUnits();
			}
		};
	}
}

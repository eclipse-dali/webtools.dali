/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.navigator;

import org.eclipse.core.resources.IProject;
import org.eclipse.jpt.common.ui.internal.jface.AbstractItemTreeContentProvider;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyCollectionValueModelAdapter;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.JpaRootContextNode;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.jpa.ui.JpaRootContextNodeModel;

public class JpaRootContextNodeModelItemContentProvider
	extends AbstractItemTreeContentProvider<JpaRootContextNodeModel, PersistenceXml>
{
	public JpaRootContextNodeModelItemContentProvider(JpaRootContextNodeModel rootContextModel, Manager manager) {
		super(rootContextModel, manager);
	}
	
	public IProject getParent() {
		return this.item.getProject();
	}
	
	@Override
	protected CollectionValueModel<PersistenceXml> buildChildrenModel() {
		return new PropertyCollectionValueModelAdapter<PersistenceXml>(this.buildPersistenceXmlModel());
	}

	protected PropertyValueModel<PersistenceXml> buildPersistenceXmlModel() {
		return new PersistenceXmlModel(this.item);
	}

	public static class PersistenceXmlModel
		extends PropertyAspectAdapter<JpaRootContextNode, PersistenceXml>
	{
		public PersistenceXmlModel(PropertyValueModel<JpaRootContextNode> rootContextNodeModel) {
			super(rootContextNodeModel, JpaRootContextNode.PERSISTENCE_XML_PROPERTY);
		}
		@Override
		protected PersistenceXml buildValue_() {
			return this.subject.getPersistenceXml();
		}
	}
}

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

import org.eclipse.core.resources.IProject;
import org.eclipse.jpt.core.context.JpaRootContextNode;
import org.eclipse.jpt.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.ui.internal.jface.AbstractTreeItemContentProvider;
import org.eclipse.jpt.ui.internal.jface.DelegatingTreeContentAndLabelProvider;
import org.eclipse.jpt.utility.internal.model.value.CollectionListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyCollectionValueModelAdapter;
import org.eclipse.jpt.utility.model.value.ListValueModel;

public class RootContextItemContentProvider
	extends AbstractTreeItemContentProvider<PersistenceXml>
{
	public RootContextItemContentProvider(
			JpaRootContextNode rootContext, DelegatingTreeContentAndLabelProvider contentProvider) {
		super(rootContext, contentProvider);
	}
	
	
	@Override
	public IProject getParent() {
		return ((JpaRootContextNode) model()).getJpaProject().getProject();
	}
	
	@Override
	protected ListValueModel<PersistenceXml> buildChildrenModel() {
		return new CollectionListValueModelAdapter<PersistenceXml>(
				new PropertyCollectionValueModelAdapter<PersistenceXml>(
					new PropertyAspectAdapter<JpaRootContextNode, PersistenceXml>(
							JpaRootContextNode.PERSISTENCE_XML_PROPERTY,
							(JpaRootContextNode) model()) {
						 @Override
						protected PersistenceXml buildValue_() {
							return subject.getPersistenceXml();
						}
					}));
	}
}

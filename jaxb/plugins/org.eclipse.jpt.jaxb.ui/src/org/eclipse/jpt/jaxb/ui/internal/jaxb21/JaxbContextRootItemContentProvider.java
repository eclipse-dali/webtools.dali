/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal.jaxb21;

import org.eclipse.core.resources.IProject;
import org.eclipse.jpt.jaxb.core.context.JaxbContextRoot;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.ui.internal.jface.AbstractTreeItemContentProvider;
import org.eclipse.jpt.ui.internal.jface.DelegatingTreeContentAndLabelProvider;
import org.eclipse.jpt.utility.internal.model.value.CollectionAspectAdapter;
import org.eclipse.jpt.utility.model.value.CollectionValueModel;


public class JaxbContextRootItemContentProvider
		extends AbstractTreeItemContentProvider<JaxbPackage> {
	
	public JaxbContextRootItemContentProvider(
			JaxbContextRoot rootContext, DelegatingTreeContentAndLabelProvider contentProvider) {
		super(rootContext, contentProvider);
	}
	
	
	@Override
	public JaxbContextRoot getModel() {
		return (JaxbContextRoot) super.getModel();
	}
	
	@Override
	public IProject getParent() {
		return getModel().getJaxbProject().getProject();
	}
	
	@Override
	protected CollectionValueModel<JaxbPackage> buildChildrenModel() {
		return new CollectionAspectAdapter<JaxbContextRoot, JaxbPackage>(
				JaxbContextRoot.PACKAGES_COLLECTION,
				getModel()) {
			
			@Override
			protected Iterable<JaxbPackage> getIterable() {
				return this.subject.getPackages();
			}
		};
	}
}

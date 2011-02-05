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

import org.eclipse.jpt.common.ui.internal.jface.AbstractTreeItemContentProvider;
import org.eclipse.jpt.common.ui.internal.jface.DelegatingTreeContentAndLabelProvider;
import org.eclipse.jpt.common.utility.internal.model.value.CollectionAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.jaxb.core.context.JaxbContextRoot;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.JaxbType;


public class JaxbPackageItemContentProvider
		extends AbstractTreeItemContentProvider<JaxbType> {
	
	public JaxbPackageItemContentProvider(
			JaxbPackage jaxbPackage, DelegatingTreeContentAndLabelProvider contentProvider) {
		
		super(jaxbPackage, contentProvider);
	}
	
	
	@Override
	public JaxbPackage getModel() {
		return (JaxbPackage) super.getModel();
	}
	
	@Override
	public JaxbContextRoot getParent() {
		return (JaxbContextRoot) getModel().getParent();
	}
	
	@Override
	protected CollectionValueModel<JaxbType> buildChildrenModel() {
		return new CollectionAspectAdapter<JaxbContextRoot, JaxbType>(
				JaxbContextRoot.TYPES_COLLECTION, getParent()) {
			@Override
			protected Iterable<JaxbType> getIterable() {
				return this.subject.getTypes(getModel());
			}
		};
	}
}

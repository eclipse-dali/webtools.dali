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

import org.eclipse.jpt.jaxb.core.context.JaxbContextRoot;
import org.eclipse.jpt.jaxb.core.context.JaxbEnumConstant;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentEnum;
import org.eclipse.jpt.ui.internal.jface.AbstractTreeItemContentProvider;
import org.eclipse.jpt.ui.internal.jface.DelegatingTreeContentAndLabelProvider;
import org.eclipse.jpt.utility.internal.model.value.CollectionAspectAdapter;
import org.eclipse.jpt.utility.model.value.CollectionValueModel;


public class JaxbPersistentEnumItemContentProvider
		extends AbstractTreeItemContentProvider<JaxbEnumConstant> {
	
	public JaxbPersistentEnumItemContentProvider(
			JaxbPersistentEnum jaxbPersistentEnum, DelegatingTreeContentAndLabelProvider contentProvider) {
		
		super(jaxbPersistentEnum, contentProvider);
	}
	
	
	@Override
	public JaxbPersistentEnum getModel() {
		return (JaxbPersistentEnum) super.getModel();
	}
	
	@Override
	public JaxbContextRoot getParent() {
		return (JaxbContextRoot) getModel().getParent();
	}
	
	@Override
	protected CollectionValueModel<JaxbEnumConstant> buildChildrenModel() {
		return new CollectionAspectAdapter<JaxbPersistentEnum, JaxbEnumConstant>(
			JaxbPersistentEnum.ENUM_CONSTANTS_COLLECTION, getModel()) {
			@Override
			protected Iterable<JaxbEnumConstant> getIterable() {
				return this.subject.getEnumConstants();
			}
		};
	}
}

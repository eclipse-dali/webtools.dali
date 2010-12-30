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
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentClass;
import org.eclipse.jpt.ui.internal.jface.AbstractTreeItemContentProvider;
import org.eclipse.jpt.ui.internal.jface.DelegatingTreeContentAndLabelProvider;
import org.eclipse.jpt.utility.internal.model.value.CollectionAspectAdapter;
import org.eclipse.jpt.utility.model.value.CollectionValueModel;


public class JaxbPersistentClassItemContentProvider
		extends AbstractTreeItemContentProvider<JaxbPersistentAttribute> {
	
	public JaxbPersistentClassItemContentProvider(
		JaxbPersistentClass jaxbPersistentClass, DelegatingTreeContentAndLabelProvider contentProvider) {
		
		super(jaxbPersistentClass, contentProvider);
	}
	
	
	@Override
	public JaxbPersistentClass getModel() {
		return (JaxbPersistentClass) super.getModel();
	}
	
	@Override
	public JaxbContextRoot getParent() {
		return (JaxbContextRoot) getModel().getParent();
	}
	
	@Override
	protected CollectionValueModel<JaxbPersistentAttribute> buildChildrenModel() {
		return new CollectionAspectAdapter<JaxbPersistentClass, JaxbPersistentAttribute>(
			JaxbPersistentClass.ATTRIBUTES_COLLECTION, getModel()) {
			@Override
			protected Iterable<JaxbPersistentAttribute> getIterable() {
				return this.subject.getAttributes();
			}
		};
	}
}

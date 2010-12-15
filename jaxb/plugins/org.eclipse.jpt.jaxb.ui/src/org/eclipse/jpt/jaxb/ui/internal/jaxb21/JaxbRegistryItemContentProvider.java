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
import org.eclipse.jpt.jaxb.core.context.JaxbElementFactoryMethod;
import org.eclipse.jpt.jaxb.core.context.JaxbRegistry;
import org.eclipse.jpt.ui.internal.jface.AbstractTreeItemContentProvider;
import org.eclipse.jpt.ui.internal.jface.DelegatingTreeContentAndLabelProvider;


public class JaxbRegistryItemContentProvider
		extends AbstractTreeItemContentProvider<JaxbElementFactoryMethod> {
	
	public JaxbRegistryItemContentProvider(
		JaxbRegistry jaxbRegistry, DelegatingTreeContentAndLabelProvider contentProvider) {
		
		super(jaxbRegistry, contentProvider);
	}
	
	
	@Override
	public JaxbRegistry getModel() {
		return (JaxbRegistry) super.getModel();
	}
	
	@Override
	public JaxbContextRoot getParent() {
		return (JaxbContextRoot) getModel().getParent();
	}

}

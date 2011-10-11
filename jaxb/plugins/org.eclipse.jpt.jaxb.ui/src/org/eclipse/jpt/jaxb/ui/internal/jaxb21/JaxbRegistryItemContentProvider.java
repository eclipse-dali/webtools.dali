/*******************************************************************************
 *  Copyright (c) 2010, 2011  Oracle. All rights reserved.
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
import org.eclipse.jpt.jaxb.core.context.JaxbContextRoot;
import org.eclipse.jpt.jaxb.core.context.JaxbElementFactoryMethod;
import org.eclipse.jpt.jaxb.core.context.XmlRegistry;


public class JaxbRegistryItemContentProvider
		extends AbstractTreeItemContentProvider<JaxbElementFactoryMethod> {
	
	public JaxbRegistryItemContentProvider(
		XmlRegistry jaxbRegistry, DelegatingTreeContentAndLabelProvider contentProvider) {
		
		super(jaxbRegistry, contentProvider);
	}
	
	
	@Override
	public XmlRegistry getModel() {
		return (XmlRegistry) super.getModel();
	}
	
	@Override
	public JaxbContextRoot getParent() {
		return (JaxbContextRoot) getModel().getParent();
	}

}

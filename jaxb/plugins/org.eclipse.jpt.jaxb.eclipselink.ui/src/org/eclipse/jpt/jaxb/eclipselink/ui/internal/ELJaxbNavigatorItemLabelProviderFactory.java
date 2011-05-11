/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.ui.internal;

import org.eclipse.jpt.common.ui.jface.DelegatingContentAndLabelProvider;
import org.eclipse.jpt.common.ui.jface.ItemLabelProvider;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.ui.internal.jaxb21.GenericJaxb_2_1_NavigatorItemLabelProviderFactory;


public class ELJaxbNavigatorItemLabelProviderFactory
		extends GenericJaxb_2_1_NavigatorItemLabelProviderFactory {
	
	private static ELJaxbNavigatorItemLabelProviderFactory INSTANCE = new ELJaxbNavigatorItemLabelProviderFactory();
	
	
	public static GenericJaxb_2_1_NavigatorItemLabelProviderFactory instance() {
		return INSTANCE;
	}
	
	
	private ELJaxbNavigatorItemLabelProviderFactory() {
		super();
	}
	
	
	@Override
	public ItemLabelProvider buildItemLabelProvider(
			Object item,
			DelegatingContentAndLabelProvider contentAndLabelProvider) {
		
		if (item instanceof JaxbPersistentAttribute) {
			return new ELJaxbPersistentAttributeItemLabelProvider((JaxbPersistentAttribute) item, contentAndLabelProvider);
		}
		
		return super.buildItemLabelProvider(item, contentAndLabelProvider);
	}
}

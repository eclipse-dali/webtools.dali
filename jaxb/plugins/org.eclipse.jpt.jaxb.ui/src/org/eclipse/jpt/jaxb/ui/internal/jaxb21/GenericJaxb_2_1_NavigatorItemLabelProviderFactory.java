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
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentEnum;
import org.eclipse.jpt.jaxb.core.context.JaxbType;
import org.eclipse.jpt.ui.jface.DelegatingContentAndLabelProvider;
import org.eclipse.jpt.ui.jface.ItemLabelProvider;
import org.eclipse.jpt.ui.jface.ItemLabelProviderFactory;


public class GenericJaxb_2_1_NavigatorItemLabelProviderFactory
	implements ItemLabelProviderFactory {
	
	private static GenericJaxb_2_1_NavigatorItemLabelProviderFactory INSTANCE;
	
	
	public static GenericJaxb_2_1_NavigatorItemLabelProviderFactory instance() {
		if (INSTANCE == null) {
			INSTANCE = new GenericJaxb_2_1_NavigatorItemLabelProviderFactory();
		}
		return INSTANCE;
	}
	
	
	private GenericJaxb_2_1_NavigatorItemLabelProviderFactory() {
		super();
	}
	
	
	public ItemLabelProvider buildItemLabelProvider(
			Object item,
			DelegatingContentAndLabelProvider contentAndLabelProvider) {
		
		if (item instanceof JaxbContextRoot) {
			return new JaxbContextRootItemLabelProvider((JaxbContextRoot) item, contentAndLabelProvider);
		}
		else if (item instanceof JaxbPackage) {
			return new JaxbPackageItemLabelProvider((JaxbPackage) item, contentAndLabelProvider);
		}
		else if (item instanceof JaxbPersistentEnum) {
			return new JaxbPersistentEnumItemLabelProvider((JaxbPersistentEnum) item, contentAndLabelProvider);
		}
		else if (item instanceof JaxbType) {
			return new JaxbTypeItemLabelProvider((JaxbType) item, contentAndLabelProvider);
		}
		else if (item instanceof JaxbEnumConstant) {
			return new JaxbEnumConstantItemLabelProvider((JaxbEnumConstant) item, contentAndLabelProvider);
		}
		return null;
	}
}

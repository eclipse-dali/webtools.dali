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
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentClass;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentEnum;
import org.eclipse.jpt.jaxb.core.context.JaxbRegistry;
import org.eclipse.jpt.ui.internal.jface.DelegatingTreeContentAndLabelProvider;
import org.eclipse.jpt.ui.jface.DelegatingContentAndLabelProvider;
import org.eclipse.jpt.ui.jface.TreeItemContentProvider;
import org.eclipse.jpt.ui.jface.TreeItemContentProviderFactory;


public class GenericJaxb_2_1_NavigatorTreeItemContentProviderFactory
	implements TreeItemContentProviderFactory {
	
	private static GenericJaxb_2_1_NavigatorTreeItemContentProviderFactory INSTANCE;
	
	
	public static GenericJaxb_2_1_NavigatorTreeItemContentProviderFactory instance() {
		if (INSTANCE == null) {
			INSTANCE = new GenericJaxb_2_1_NavigatorTreeItemContentProviderFactory();
		}
		return INSTANCE;
	}
	
	
	private GenericJaxb_2_1_NavigatorTreeItemContentProviderFactory() {
		super();
	}
	
	
	public TreeItemContentProvider buildItemContentProvider(
			Object item,
			DelegatingContentAndLabelProvider contentAndLabelProvider) {
		
		DelegatingTreeContentAndLabelProvider treeContentAndLabelProvider = 
				(DelegatingTreeContentAndLabelProvider) contentAndLabelProvider;
		
		if (item instanceof JaxbContextRoot) {
			return new JaxbContextRootItemContentProvider((JaxbContextRoot) item, treeContentAndLabelProvider);
		}
		else if (item instanceof JaxbPackage) {
			return new JaxbPackageItemContentProvider((JaxbPackage) item, treeContentAndLabelProvider);	
		}
		else if (item instanceof JaxbPersistentClass) {
			return new JaxbPersistentClassItemContentProvider((JaxbPersistentClass) item, treeContentAndLabelProvider);	
		}
		else if (item instanceof JaxbPersistentEnum) {
			return new JaxbPersistentEnumItemContentProvider((JaxbPersistentEnum) item, treeContentAndLabelProvider);	
		}
		else if (item instanceof JaxbRegistry) {
			return new JaxbRegistryItemContentProvider((JaxbRegistry) item, treeContentAndLabelProvider);	
		}
		return null;
	}
}

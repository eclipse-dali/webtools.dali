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

import org.eclipse.jpt.common.ui.jface.ItemLabelProviderFactory;
import org.eclipse.jpt.common.ui.jface.TreeItemContentProviderFactory;
import org.eclipse.jpt.jaxb.ui.navigator.JaxbNavigatorUi;


public class GenericJaxb_2_1_NavigatorUi
		implements JaxbNavigatorUi {
	
	private static GenericJaxb_2_1_NavigatorUi INSTANCE;
	
	
	public static GenericJaxb_2_1_NavigatorUi instance() {
		if (INSTANCE == null) {
			INSTANCE = new GenericJaxb_2_1_NavigatorUi();
		}
		return INSTANCE;
	}
	
	
	private GenericJaxb_2_1_NavigatorUi() {
		super();
	}
	
	
	public TreeItemContentProviderFactory getTreeItemContentProviderFactory() {
		return GenericJaxb_2_1_NavigatorTreeItemContentProviderFactory.instance();
	}
	
	public ItemLabelProviderFactory getItemLabelProviderFactory() {
		return GenericJaxb_2_1_NavigatorItemLabelProviderFactory.instance();
	}
}

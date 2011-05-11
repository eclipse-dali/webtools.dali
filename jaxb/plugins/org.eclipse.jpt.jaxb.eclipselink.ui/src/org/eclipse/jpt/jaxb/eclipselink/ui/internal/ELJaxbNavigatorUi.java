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

import org.eclipse.jpt.common.ui.jface.ItemLabelProviderFactory;
import org.eclipse.jpt.jaxb.ui.internal.jaxb21.GenericJaxb_2_1_NavigatorUi;


public class ELJaxbNavigatorUi
		extends GenericJaxb_2_1_NavigatorUi {
	
	private static ELJaxbNavigatorUi INSTANCE = new ELJaxbNavigatorUi();
	
	
	public static ELJaxbNavigatorUi instance() {
		return INSTANCE;
	}
	
	
	private ELJaxbNavigatorUi() {
		super();
	}
	
	
	@Override
	public ItemLabelProviderFactory getItemLabelProviderFactory() {
		return ELJaxbNavigatorItemLabelProviderFactory.instance();
	}
}

/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.ui.internal.navigator;

import org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProviderFactory;
import org.eclipse.jpt.common.ui.jface.ItemTreeContentProviderFactory;
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
	public ItemExtendedLabelProviderFactory getItemLabelProviderFactory() {
		return ELJaxbNavigatorItemLabelProviderFactory.instance();
	}
	
	@Override
	public ItemTreeContentProviderFactory getTreeItemContentProviderFactory() {
		return ELJaxbNavigatorTreeItemContentProviderFactory.instance();
	}
}

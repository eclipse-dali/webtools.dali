/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal.jaxb21;

import org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProvider;
import org.eclipse.jpt.common.ui.jface.ItemTreeContentProvider;
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
	
	
	protected GenericJaxb_2_1_NavigatorUi() {
		super();
	}
	
	
	public ItemTreeContentProvider.Factory getTreeItemContentProviderFactory() {
		return GenericJaxb_2_1_NavigatorItemContentProviderFactory.instance();
	}
	
	public ItemExtendedLabelProvider.Factory getItemLabelProviderFactory() {
		return GenericJaxb_2_1_NavigatorItemLabelProviderFactory.instance();
	}
}

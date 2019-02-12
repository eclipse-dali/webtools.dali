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
import org.eclipse.jpt.jaxb.ui.internal.AbstractNavigatorItemLabelProviderFactory;


public class GenericJaxb_2_1_NavigatorItemLabelProviderFactory
	extends AbstractNavigatorItemLabelProviderFactory
{
	private static ItemExtendedLabelProvider.Factory INSTANCE;
	
	
	public static ItemExtendedLabelProvider.Factory instance() {
		if (INSTANCE == null) {
			INSTANCE = new GenericJaxb_2_1_NavigatorItemLabelProviderFactory();
		}
		return INSTANCE;
	}
	
	
	private GenericJaxb_2_1_NavigatorItemLabelProviderFactory() {
		super();
	}
}

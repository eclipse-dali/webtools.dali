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

import org.eclipse.jpt.common.ui.jface.ItemTreeContentProvider;
import org.eclipse.jpt.jaxb.ui.internal.AbstractNavigatorItemContentProviderFactory;

public class GenericJaxb_2_1_NavigatorItemContentProviderFactory
	extends AbstractNavigatorItemContentProviderFactory
{
	// singleton
	private static final ItemTreeContentProvider.Factory INSTANCE = new GenericJaxb_2_1_NavigatorItemContentProviderFactory();

	/**
	 * Return the singleton
	 */
	public static ItemTreeContentProvider.Factory instance() {
		return INSTANCE;
	}

	private GenericJaxb_2_1_NavigatorItemContentProviderFactory() {
		super();
	}
}

/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.platform.generic;

import org.eclipse.jpt.common.ui.jface.ItemTreeContentProvider;
import org.eclipse.jpt.jpa.ui.internal.platform.base.AbstractNavigatorItemContentProviderFactory;

/**
 * This factory builds item content providers for the JPA content in the
 * Project Explorer. It is to be used by
 * {@link org.eclipse.jpt.jpa.ui.internal.navigator.JpaNavigatorItemContentProviderFactory}
 * as a delegate for <em>Generic</em> JPA projects.
 */
public class GenericNavigatorItemContentProviderFactory
	extends AbstractNavigatorItemContentProviderFactory
{
	// singleton
	private static final ItemTreeContentProvider.Factory INSTANCE = new GenericNavigatorItemContentProviderFactory();

	/**
	 * Return the singleton
	 */
	public static ItemTreeContentProvider.Factory instance() {
		return INSTANCE;
	}


	private GenericNavigatorItemContentProviderFactory() {
		super();
	}
}

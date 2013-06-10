/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.structure;

import org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProvider;

/**
 * This factory builds item label providers for a Java source file
 * JPA Structure View.
 */
public class JavaStructureItemLabelProviderFactory
	extends MappingStructureItemLabelProviderFactory
{
	// singleton
	private static final ItemExtendedLabelProvider.Factory INSTANCE = new JavaStructureItemLabelProviderFactory();

	/**
	 * Return the singleton
	 */
	public static ItemExtendedLabelProvider.Factory instance() {
		return INSTANCE;
	}


	private JavaStructureItemLabelProviderFactory() {
		super();
	}
}

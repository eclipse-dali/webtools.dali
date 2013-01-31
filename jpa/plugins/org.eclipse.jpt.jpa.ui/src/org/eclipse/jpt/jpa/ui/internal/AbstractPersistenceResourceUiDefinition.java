/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal;

import org.eclipse.jpt.common.ui.internal.jface.SimpleItemTreeStateProviderFactoryProvider;
import org.eclipse.jpt.common.ui.jface.ItemTreeStateProviderFactoryProvider;
import org.eclipse.jpt.jpa.ui.internal.structure.JpaFileStructureItemContentProviderFactory;
import org.eclipse.jpt.jpa.ui.internal.structure.PersistenceStructureItemLabelProviderFactory;

/**
 * All the state in the definition should be "static" (i.e. unchanging once it is initialized).
 */
public abstract class AbstractPersistenceResourceUiDefinition
	extends AbstractResourceUiDefinition
{


	/**
	 * zero-argument constructor
	 */
	protected AbstractPersistenceResourceUiDefinition() {
		super();
	}

	
	public ItemTreeStateProviderFactoryProvider getStructureViewFactoryProvider() {
		return STRUCTURE_VIEW_FACTORY_PROVIDER;
	}
	
	public static final ItemTreeStateProviderFactoryProvider STRUCTURE_VIEW_FACTORY_PROVIDER =
			new SimpleItemTreeStateProviderFactoryProvider(
					JpaFileStructureItemContentProviderFactory.instance(),
					PersistenceStructureItemLabelProviderFactory.instance()
				);
}

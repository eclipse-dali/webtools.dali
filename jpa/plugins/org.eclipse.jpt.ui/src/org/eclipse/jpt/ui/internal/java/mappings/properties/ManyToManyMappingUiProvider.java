/*******************************************************************************
 * Copyright (c) 2006 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.java.mappings.properties;

import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.ui.internal.details.IJpaComposite;
import org.eclipse.jpt.ui.internal.java.details.IAttributeMappingUiProvider;
import org.eclipse.jpt.ui.internal.mappings.JpaUiMappingsMessages;
import org.eclipse.jpt.ui.internal.mappings.details.ManyToManyComposite;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class ManyToManyMappingUiProvider
	implements IAttributeMappingUiProvider
{
	// singleton
	private static final ManyToManyMappingUiProvider INSTANCE = new ManyToManyMappingUiProvider();

	/**
	 * Return the singleton.
	 */
	public static IAttributeMappingUiProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private ManyToManyMappingUiProvider() {
		super();
	}

	public String key() {
		return IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY;
	}
	
	public String label() {
		return JpaUiMappingsMessages.PersistentAttributePage_ManyToManyLabel;
	}

	public IJpaComposite buildAttributeMappingComposite(Composite parent, CommandStack commandStack, TabbedPropertySheetWidgetFactory widgetFactory) {
		return new ManyToManyComposite(parent, commandStack, widgetFactory);
	}
}
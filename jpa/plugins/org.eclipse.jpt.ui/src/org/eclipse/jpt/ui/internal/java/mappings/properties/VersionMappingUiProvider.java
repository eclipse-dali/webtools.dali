/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
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
import org.eclipse.jpt.ui.internal.mappings.details.VersionComposite;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class VersionMappingUiProvider
	implements IAttributeMappingUiProvider
{
	
	// singleton
	private static final VersionMappingUiProvider INSTANCE = new VersionMappingUiProvider();

	/**
	 * Return the singleton.
	 */
	public static IAttributeMappingUiProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private VersionMappingUiProvider() {
		super();
	}

	public String key() {
		return IMappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY;
	}
	
	public String label() {
		return JpaUiMappingsMessages.PersistentAttributePage_VersionLabel;
	}
	
	public IJpaComposite buildAttributeMappingComposite(Composite parent, CommandStack commandStack, TabbedPropertySheetWidgetFactory widgetFactory) {
		return new VersionComposite(parent, commandStack, widgetFactory);
	}
}
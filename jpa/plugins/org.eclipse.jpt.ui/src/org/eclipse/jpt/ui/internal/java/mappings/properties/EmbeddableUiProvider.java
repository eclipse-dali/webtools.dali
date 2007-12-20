/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.java.mappings.properties;

import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.ui.internal.details.IJpaComposite;
import org.eclipse.jpt.ui.internal.java.details.ITypeMappingUiProvider;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.mappings.details.EmbeddableComposite;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class EmbeddableUiProvider implements ITypeMappingUiProvider
{
	// singleton
	private static final EmbeddableUiProvider INSTANCE = new EmbeddableUiProvider();

	/**
	 * Return the singleton.
	 */
	public static ITypeMappingUiProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private EmbeddableUiProvider() {
		super();
	}

	public String mappingKey() {
		return IMappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY;
	}
	
	public String label() {
		return JptUiMappingsMessages.PersistentTypePage_EmbeddableLabel;
	}
	
	public IJpaComposite buildPersistentTypeMappingComposite(
				Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		return new EmbeddableComposite(parent, widgetFactory);
	}
}
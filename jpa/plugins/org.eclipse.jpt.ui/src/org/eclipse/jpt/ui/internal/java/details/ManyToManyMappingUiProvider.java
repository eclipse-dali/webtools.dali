/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.java.details;

import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.ManyToManyMapping;
import org.eclipse.jpt.ui.JpaUiFactory;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.java.details.AttributeMappingUiProvider;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class ManyToManyMappingUiProvider
	implements AttributeMappingUiProvider<ManyToManyMapping>
{
	// singleton
	private static final ManyToManyMappingUiProvider INSTANCE = new ManyToManyMappingUiProvider();

	/**
	 * Return the singleton.
	 */
	public static AttributeMappingUiProvider<ManyToManyMapping> instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private ManyToManyMappingUiProvider() {
		super();
	}

	public String attributeMappingKey() {
		return MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY;
	}

	public String label() {
		return JptUiMappingsMessages.PersistentAttributePage_ManyToManyLabel;
	}

	public JpaComposite<ManyToManyMapping> buildAttributeMappingComposite(
			JpaUiFactory factory,
			PropertyValueModel<ManyToManyMapping> subjectHolder,
			Composite parent,
			TabbedPropertySheetWidgetFactory widgetFactory) {

		return factory.createManyToManyMappingComposite(subjectHolder, parent, widgetFactory);
	}
}

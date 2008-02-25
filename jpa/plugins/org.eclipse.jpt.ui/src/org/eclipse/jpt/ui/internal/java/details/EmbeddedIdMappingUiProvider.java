/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.java.details;

import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.EmbeddedIdMapping;
import org.eclipse.jpt.ui.JpaUiFactory;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.java.details.AttributeMappingUiProvider;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class EmbeddedIdMappingUiProvider
	implements AttributeMappingUiProvider<EmbeddedIdMapping>
{

	// singleton
	private static final EmbeddedIdMappingUiProvider INSTANCE = new EmbeddedIdMappingUiProvider();

	/**
	 * Return the singleton.
	 */
	public static AttributeMappingUiProvider<EmbeddedIdMapping> instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private EmbeddedIdMappingUiProvider() {
		super();
	}

	public String attributeMappingKey() {
		return MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY;
	}

	public String label() {
		return JptUiMappingsMessages.PersistentAttributePage_EmbeddedIdLabel;
	}

	public JpaComposite<EmbeddedIdMapping> buildAttributeMappingComposite(
			JpaUiFactory factory,
			PropertyValueModel<EmbeddedIdMapping> subjectHolder,
			Composite parent,
			TabbedPropertySheetWidgetFactory widgetFactory) {

		return factory.createEmbeddedIdMappingComposite(subjectHolder, parent, widgetFactory);
	}
}
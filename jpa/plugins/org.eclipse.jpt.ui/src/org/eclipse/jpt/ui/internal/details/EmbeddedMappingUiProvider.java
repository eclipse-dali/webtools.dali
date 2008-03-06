/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.EmbeddedMapping;
import org.eclipse.jpt.ui.JpaUiFactory;
import org.eclipse.jpt.ui.details.AttributeMappingUiProvider;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.WidgetFactory;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

public class EmbeddedMappingUiProvider
	implements AttributeMappingUiProvider<EmbeddedMapping>
{

	// singleton
	private static final EmbeddedMappingUiProvider INSTANCE = new EmbeddedMappingUiProvider();

	/**
	 * Return the singleton.
	 */
	public static AttributeMappingUiProvider<EmbeddedMapping> instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private EmbeddedMappingUiProvider() {
		super();
	}

	public String mappingKey() {
		return MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY;
	}

	public String label() {
		return JptUiMappingsMessages.PersistentAttributePage_EmbeddedLabel;
	}

	public JpaComposite<EmbeddedMapping> buildAttributeMappingComposite(
			JpaUiFactory factory,
			PropertyValueModel<EmbeddedMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {

		return factory.createEmbeddedMappingComposite(subjectHolder, parent, widgetFactory);
	}
}
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
import org.eclipse.jpt.core.context.EmbeddedIdMapping;
import org.eclipse.jpt.ui.JpaUiFactory;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.AttributeMappingUiProvider;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.internal.JpaMappingImageHelper;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

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

	public String getMappingKey() {
		return MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY;
	}

	public String getLabel() {
		return JptUiMappingsMessages.PersistentAttributePage_EmbeddedIdLabel;
	}

	public Image getImage() {
		return JpaMappingImageHelper.imageForAttributeMapping(getMappingKey());
	}

	public JpaComposite<EmbeddedIdMapping> buildAttributeMappingComposite(
		JpaUiFactory factory,
		PropertyValueModel<EmbeddedIdMapping> subjectHolder,
		Composite parent,
		WidgetFactory widgetFactory) {

		return factory.createEmbeddedIdMappingComposite(subjectHolder, parent, widgetFactory);
	}
}
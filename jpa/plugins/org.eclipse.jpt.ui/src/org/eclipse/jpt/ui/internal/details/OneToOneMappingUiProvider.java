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
import org.eclipse.jpt.core.context.OneToOneMapping;
import org.eclipse.jpt.ui.JpaUiFactory;
import org.eclipse.jpt.ui.details.AttributeMappingUiProvider;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.internal.JpaMappingImageHelper;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.WidgetFactory;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

public class OneToOneMappingUiProvider
	implements AttributeMappingUiProvider<OneToOneMapping>
{

	// singleton
	private static final OneToOneMappingUiProvider INSTANCE = new OneToOneMappingUiProvider();

	/**
	 * Return the singleton.
	 */
	public static AttributeMappingUiProvider<OneToOneMapping> instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private OneToOneMappingUiProvider() {
		super();
	}

	public String mappingKey() {
		return MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY;
	}

	public String label() {
		return JptUiMappingsMessages.PersistentAttributePage_OneToOneLabel;
	}

	public Image image() {
		return JpaMappingImageHelper.imageForAttributeMapping(mappingKey());
	}

	public JpaComposite<OneToOneMapping> buildAttributeMappingComposite(
		JpaUiFactory factory,
		PropertyValueModel<OneToOneMapping> subjectHolder,
		Composite parent,
		WidgetFactory widgetFactory) {

		return factory.createOneToOneMappingComposite(subjectHolder, parent, widgetFactory);
	}
}
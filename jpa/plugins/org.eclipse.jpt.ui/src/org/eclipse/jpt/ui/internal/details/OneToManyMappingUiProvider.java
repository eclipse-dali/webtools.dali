/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.OneToManyMapping;
import org.eclipse.jpt.ui.JpaUiFactory;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.AttributeMappingUiProvider;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.internal.JpaMappingImageHelper;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

public class OneToManyMappingUiProvider
	implements AttributeMappingUiProvider<OneToManyMapping>
{
	// singleton
	private static final OneToManyMappingUiProvider INSTANCE = new OneToManyMappingUiProvider();

	/**
	 * Return the singleton.
	 */
	public static AttributeMappingUiProvider<OneToManyMapping> instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private OneToManyMappingUiProvider() {
		super();
	}

	public String mappingKey() {
		return MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY;
	}

	public String label() {
		return JptUiMappingsMessages.PersistentAttributePage_OneToManyLabel;
	}

	public Image image() {
		return JpaMappingImageHelper.imageForAttributeMapping(mappingKey());
	}

	public JpaComposite<OneToManyMapping> buildAttributeMappingComposite(
		JpaUiFactory factory,
		PropertyValueModel<OneToManyMapping> subjectHolder,
		Composite parent,
		WidgetFactory widgetFactory) {

		return factory.createOneToManyMappingComposite(subjectHolder, parent, widgetFactory);
	}
}

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
import org.eclipse.jpt.core.context.BasicMapping;
import org.eclipse.jpt.ui.JpaUiFactory;
import org.eclipse.jpt.ui.details.AttributeMappingUiProvider;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.internal.JpaMappingImageHelper;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.WidgetFactory;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

public class BasicMappingUiProvider
	implements AttributeMappingUiProvider<BasicMapping>
{

	// singleton
	private static final BasicMappingUiProvider INSTANCE = new BasicMappingUiProvider();

	/**
	 * Ensure non-instantiability.
	 */
	private BasicMappingUiProvider() {
		super();
	}

	/**
	 * Return the singleton.
	 */
	public static AttributeMappingUiProvider<BasicMapping> instance() {
		return INSTANCE;
	}

	public JpaComposite<BasicMapping> buildAttributeMappingComposite(
		JpaUiFactory factory,
		PropertyValueModel<BasicMapping> subjectHolder,
		Composite parent,
		WidgetFactory widgetFactory) {

		return factory.createBasicMappingComposite(subjectHolder, parent, widgetFactory);
	}

	public Image image() {
		return JpaMappingImageHelper.imageForAttributeMapping(mappingKey());
	}

	public String label() {
		return JptUiMappingsMessages.PersistentAttributePage_BasicLabel;
	}

	public String mappingKey() {
		return MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY;
	}
}
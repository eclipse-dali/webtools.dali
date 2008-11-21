/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.java.details;

import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.OneToOneMapping;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.EclipseLinkUiMappingsMessages;
import org.eclipse.jpt.ui.JpaUiFactory;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.DefaultAttributeMappingUiProvider;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.internal.JpaMappingImageHelper;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

public class DefaultOneToOneMappingUiProvider
	implements DefaultAttributeMappingUiProvider<OneToOneMapping>
{
	// singleton
	private static final DefaultOneToOneMappingUiProvider INSTANCE = new DefaultOneToOneMappingUiProvider();

	/**
	 * Return the singleton.
	 */
	public static DefaultAttributeMappingUiProvider<OneToOneMapping> instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private DefaultOneToOneMappingUiProvider() {
		super();
	}

	public String getMappingKey() {
		return null;
	}

	public String getDefaultMappingKey() {
		return MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY;
	}
	
	public String getLabel() {
		return EclipseLinkUiMappingsMessages.DefaultOneToOneMappingUiProvider_label;
	}

	public String getLinkLabel() {
		return EclipseLinkUiMappingsMessages.DefaultOneToOneMappingUiProvider_linkLabel;
	}

	public Image getImage() {
		return JpaMappingImageHelper.imageForAttributeMapping(getDefaultMappingKey());
	}

	public JpaComposite buildAttributeMappingComposite(JpaUiFactory factory,
		PropertyValueModel<OneToOneMapping> subjectHolder,
		Composite parent,
		WidgetFactory widgetFactory) {

		return factory.createOneToOneMappingComposite(subjectHolder, parent, widgetFactory);
	}
}

/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.mappings.details;

import org.eclipse.jpt.eclipselink.core.EclipseLinkMappingKeys;
import org.eclipse.jpt.eclipselink.core.context.BasicCollectionMapping;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkJpaUiFactory;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.EclipseLinkUiMappingsMessages;
import org.eclipse.jpt.ui.JpaUiFactory;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.AttributeMappingUiProvider;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.internal.JptUiIcons;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

public class BasicCollectionMappingUiProvider
	implements AttributeMappingUiProvider<BasicCollectionMapping>
{
	// singleton
	private static final BasicCollectionMappingUiProvider INSTANCE = new BasicCollectionMappingUiProvider();
	
	/**
	 * Ensure single instance.
	 */
	private BasicCollectionMappingUiProvider() {
		super();
	}
	
	/**
	 * Return the singleton.
	 */
	public static AttributeMappingUiProvider<BasicCollectionMapping> instance() {
		return INSTANCE;
	}
	
	public JpaComposite buildAttributeMappingComposite(
		JpaUiFactory factory,
		PropertyValueModel<BasicCollectionMapping> subjectHolder,
		Composite parent,
		WidgetFactory widgetFactory) {
		
		return ((EclipseLinkJpaUiFactory) factory).createBasicCollectionMappingComposite(subjectHolder, parent, widgetFactory);
	}
	
	public Image getImage() {
		return JptUiPlugin.getImage(JptUiIcons.JPA_CONTENT);
	}
	
	public String getLabel() {
		return EclipseLinkUiMappingsMessages.BasicCollectionMappingUiProvider_label;
	}
	
	public String getLinkLabel() {
		return EclipseLinkUiMappingsMessages.BasicCollectionMappingUiProvider_linkLabel;
	}
	
	public String getMappingKey() {
		return EclipseLinkMappingKeys.BASIC_COLLECTION_ATTRIBUTE_MAPPING_KEY;
	}
}
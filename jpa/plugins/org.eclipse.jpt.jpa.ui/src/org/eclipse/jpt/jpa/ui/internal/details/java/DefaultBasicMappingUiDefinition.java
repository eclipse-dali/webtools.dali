/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details.java;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaBasicMapping;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.details.java.DefaultJavaAttributeMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.details.java.JavaUiFactory;
import org.eclipse.jpt.jpa.ui.internal.JpaMappingImageHelper;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractBasicMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.details.JptUiDetailsMessages;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

public class DefaultBasicMappingUiDefinition
	extends AbstractBasicMappingUiDefinition<ReadOnlyPersistentAttribute, JavaBasicMapping>
	implements DefaultJavaAttributeMappingUiDefinition<JavaBasicMapping>
{
	// singleton
	private static final DefaultBasicMappingUiDefinition INSTANCE = 
		new DefaultBasicMappingUiDefinition();
	
	
	/**
	 * Return the singleton.
	 */
	public static DefaultJavaAttributeMappingUiDefinition<JavaBasicMapping> instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Ensure single instance.
	 */
	private DefaultBasicMappingUiDefinition() {
		super();
	}
	
	
	@Override
	public String getKey() {
		return null;
	}
	
	public String getDefaultKey() {
		return MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY;
	}
	
	@Override
	public String getLabel() {
		return JptUiDetailsMessages.DefaultBasicMappingUiProvider_label;
	}

	@Override
	public String getLinkLabel() {
		return JptUiDetailsMessages.DefaultBasicMappingUiProvider_linkLabel;
	}

	@Override
	public Image getImage() {
		return JpaMappingImageHelper.imageForAttributeMapping(getDefaultKey());
	}

	public JpaComposite buildAttributeMappingComposite(
				JavaUiFactory factory,
				PropertyValueModel<JavaBasicMapping> subjectHolder,
				Composite parent,
				WidgetFactory widgetFactory) {
		
		return factory.createJavaBasicMappingComposite(subjectHolder, parent, widgetFactory);
	}
}

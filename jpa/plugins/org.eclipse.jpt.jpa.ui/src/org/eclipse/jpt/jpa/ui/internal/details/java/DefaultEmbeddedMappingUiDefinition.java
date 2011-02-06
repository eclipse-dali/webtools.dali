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
import org.eclipse.jpt.jpa.core.context.java.JavaEmbeddedMapping;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.details.java.DefaultJavaAttributeMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.details.java.JavaUiFactory;
import org.eclipse.jpt.jpa.ui.internal.JpaMappingImageHelper;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractEmbeddedMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.details.JptUiDetailsMessages;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

public class DefaultEmbeddedMappingUiDefinition
	extends AbstractEmbeddedMappingUiDefinition<ReadOnlyPersistentAttribute, JavaEmbeddedMapping>
	implements DefaultJavaAttributeMappingUiDefinition<JavaEmbeddedMapping>
{
	// singleton
	private static final DefaultEmbeddedMappingUiDefinition INSTANCE = 
			new DefaultEmbeddedMappingUiDefinition();
	
	
	/**
	 * Return the singleton.
	 */
	public static DefaultJavaAttributeMappingUiDefinition<JavaEmbeddedMapping> instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Ensure single instance.
	 */
	private DefaultEmbeddedMappingUiDefinition() {
		super();
	}
	
	
	@Override
	public String getKey() {
		return null;
	}
	
	public String getDefaultKey() {
		return MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY;
	}
	
	@Override
	public String getLabel() {
		return JptUiDetailsMessages.DefaultEmbeddedMappingUiProvider_label;
	}

	@Override
	public String getLinkLabel() {
		return JptUiDetailsMessages.DefaultEmbeddedMappingUiProvider_linkLabel;
	}

	@Override
	public Image getImage() {
		return JpaMappingImageHelper.imageForAttributeMapping(getDefaultKey());
	}
	
	public JpaComposite buildAttributeMappingComposite(
			JavaUiFactory factory,
			PropertyValueModel<JavaEmbeddedMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		
		return factory.createJavaEmbeddedMappingComposite(subjectHolder, parent, widgetFactory);
	}
}

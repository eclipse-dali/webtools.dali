/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.java.details;

import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.java.JavaBasicMapping;
import org.eclipse.jpt.ui.JpaUiFactory;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.DefaultAttributeMappingUiProvider;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.internal.details.AbstractBasicMappingUiProvider;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

public class DefaultBasicMappingUiProvider
	extends AbstractBasicMappingUiProvider<JavaBasicMapping>
	implements DefaultAttributeMappingUiProvider<JavaBasicMapping>
{
	// singleton
	private static final DefaultBasicMappingUiProvider INSTANCE = 
		new DefaultBasicMappingUiProvider();
	
	/**
	 * Return the singleton.
	 */
	public static DefaultAttributeMappingUiProvider<JavaBasicMapping> instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Ensure single instance.
	 */
	private DefaultBasicMappingUiProvider() {
		super();
	}
	
	
	@Override
	public String getMappingKey() {
		return null;
	}
	
	public String getDefaultMappingKey() {
		return MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY;
	}
	
	public JpaComposite buildAttributeMappingComposite(
			JpaUiFactory factory,
			PropertyValueModel<JavaBasicMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return factory.createJavaBasicMappingComposite(subjectHolder, parent, widgetFactory);
	}
}

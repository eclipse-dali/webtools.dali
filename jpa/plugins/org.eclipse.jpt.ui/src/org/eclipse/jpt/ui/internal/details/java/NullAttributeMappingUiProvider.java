/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details.java;

import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.ui.JpaUiFactory;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.DefaultAttributeMappingUiProvider;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.internal.JpaMappingImageHelper;
import org.eclipse.jpt.ui.internal.details.JptUiDetailsMessages;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

public class NullAttributeMappingUiProvider
	implements DefaultAttributeMappingUiProvider<JavaAttributeMapping>
{
	// singleton
	private static final NullAttributeMappingUiProvider INSTANCE = 
		new NullAttributeMappingUiProvider();
	
	/**
	 * Return the singleton.
	 */
	public static DefaultAttributeMappingUiProvider<JavaAttributeMapping> instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Ensure single instance.
	 */
	private NullAttributeMappingUiProvider() {
		super();
	}
	
	public IContentType getContentType() {
		return JptCorePlugin.JAVA_SOURCE_CONTENT_TYPE;
	}

	public Image getImage() {
		return JpaMappingImageHelper.imageForAttributeMapping(getKey());
	}
	
	public String getLabel() {
		return JptUiDetailsMessages.NullAttributeMappingUiProvider_label;
	}
	
	public String getLinkLabel() {
		return null;
	}
	
	public String getKey() {
		return null;
	}
	
	public String getDefaultKey() {
		return MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY;
	}
	
	public JpaComposite buildAttributeMappingComposite(
			JpaUiFactory factory,
			PropertyValueModel<JavaAttributeMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new NullComposite(subjectHolder, parent, widgetFactory);
	}
	
	
	public static class NullComposite extends FormPane<JavaAttributeMapping>
		implements JpaComposite
	{
		NullComposite(
				PropertyValueModel<JavaAttributeMapping> subjectHolder,
		        Composite parent,
		        WidgetFactory widgetFactory) {
			super(subjectHolder, parent, widgetFactory);
		}
		
		@Override
		protected void initializeLayout(Composite container) {}
	}
}

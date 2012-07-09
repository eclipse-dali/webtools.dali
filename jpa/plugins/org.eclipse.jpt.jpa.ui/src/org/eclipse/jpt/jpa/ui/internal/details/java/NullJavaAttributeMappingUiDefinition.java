/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details.java;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.details.java.DefaultJavaAttributeMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.details.java.JavaUiFactory;
import org.eclipse.jpt.jpa.ui.internal.JpaMappingImageHelper;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.details.JptUiDetailsMessages;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

public class NullJavaAttributeMappingUiDefinition
	extends AbstractMappingUiDefinition<ReadOnlyPersistentAttribute, JavaAttributeMapping>
	implements DefaultJavaAttributeMappingUiDefinition<JavaAttributeMapping>
{
	// singleton
	private static final NullJavaAttributeMappingUiDefinition INSTANCE = 
		new NullJavaAttributeMappingUiDefinition();
	
	/**
	 * Return the singleton.
	 */
	public static DefaultJavaAttributeMappingUiDefinition<JavaAttributeMapping> instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Ensure single instance.
	 */
	private NullJavaAttributeMappingUiDefinition() {
		super();
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
			JavaUiFactory factory,
			PropertyValueModel<JavaAttributeMapping> subjectHolder,
			PropertyValueModel<Boolean> enabledModel,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new NullComposite(subjectHolder, parent, widgetFactory);
	}
	
	
	public static class NullComposite extends Pane<JavaAttributeMapping>
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

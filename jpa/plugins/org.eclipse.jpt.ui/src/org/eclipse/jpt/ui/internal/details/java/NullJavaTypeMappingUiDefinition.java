/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details.java;

import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.details.java.DefaultJavaTypeMappingUiDefinition;
import org.eclipse.jpt.ui.details.java.JavaUiFactory;
import org.eclipse.jpt.ui.internal.JpaMappingImageHelper;
import org.eclipse.jpt.ui.internal.details.AbstractMappingUiDefinition;
import org.eclipse.jpt.ui.internal.details.JptUiDetailsMessages;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

public class NullJavaTypeMappingUiDefinition
	extends AbstractMappingUiDefinition<PersistentType, JavaTypeMapping>
	implements DefaultJavaTypeMappingUiDefinition<JavaTypeMapping>
{
	// singleton
	private static final NullJavaTypeMappingUiDefinition INSTANCE = new NullJavaTypeMappingUiDefinition();
	
	
	/**
	 * Return the singleton.
	 */
	public static DefaultJavaTypeMappingUiDefinition<JavaTypeMapping> instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Ensure single instance.
	 */
	private NullJavaTypeMappingUiDefinition() {
		super();
	}
	
	
	public Image getImage() {
		return JpaMappingImageHelper.imageForTypeMapping(null);
	}
	
	public String getLabel() {
		return JptUiDetailsMessages.NullTypeMappingUiProvider_label;
	}
	
	public String getLinkLabel() {
		return null;
	}
	
	public String getKey() {
		return null;
	}
	
	public String getDefaultKey() {
		return null;
	}
	
	public JpaComposite buildTypeMappingComposite(
			JavaUiFactory factory, 
			PropertyValueModel<JavaTypeMapping> subjectHolder, 
			Composite parent, 
			WidgetFactory widgetFactory) {
		
		return new NullComposite(subjectHolder, parent, widgetFactory);
	}
	
	public static class NullComposite 
		extends Pane<JavaTypeMapping>
		implements JpaComposite 
	{
		NullComposite(
				PropertyValueModel<JavaTypeMapping> subjectHolder,
				Composite parent,
			    WidgetFactory widgetFactory) {
			
			super(subjectHolder, parent, widgetFactory);
		}
		
		
		@Override
		protected void initializeLayout(Composite container) {}
	}
}

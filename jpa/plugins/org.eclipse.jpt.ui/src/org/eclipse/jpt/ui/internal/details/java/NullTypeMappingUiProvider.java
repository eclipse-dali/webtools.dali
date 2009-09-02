/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details.java;

import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.ui.JpaUiFactory;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.details.TypeMappingUiProvider;
import org.eclipse.jpt.ui.internal.JpaMappingImageHelper;
import org.eclipse.jpt.ui.internal.details.JptUiDetailsMessages;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

@SuppressWarnings("nls")
public class NullTypeMappingUiProvider 
	implements TypeMappingUiProvider<TypeMapping>
{
	// singleton
	private static final NullTypeMappingUiProvider INSTANCE = new NullTypeMappingUiProvider();
	
	/**
	 * Return the singleton.
	 */
	public static TypeMappingUiProvider<TypeMapping> instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Ensure single instance.
	 */
	private NullTypeMappingUiProvider() {
		super();
	}
	
	public IContentType getContentType() {
		throw new UnsupportedOperationException();
	}
	
	public String getKey() {
		return null;
	}
	
	public String getLabel() {
		return JptUiDetailsMessages.NullTypeMappingUiProvider_label;
	}
	
	public String getLinkLabel() {
		return null;
	}
	
	public Image getImage() {
		return JpaMappingImageHelper.imageForTypeMapping(
			MappingKeys.NULL_TYPE_MAPPING_KEY
		);
	}
	
	public JpaComposite buildPersistentTypeMappingComposite(
			JpaUiFactory jpaUiFactory,
			PropertyValueModel<TypeMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new NullComposite(subjectHolder, parent, widgetFactory);
	}
	
	
	public static class NullComposite 
		extends FormPane<TypeMapping>
		implements JpaComposite 
	{
		NullComposite(
				PropertyValueModel<TypeMapping> subjectHolder,
				Composite parent,
			    WidgetFactory widgetFactory) {
			super(subjectHolder, parent, widgetFactory);
		}

		@Override
		protected void initializeLayout(Composite container) {}
	}
}

/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.ui.JpaUiFactory;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.DefaultTypeMappingUiProvider;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.internal.JpaMappingImageHelper;
import org.eclipse.jpt.ui.internal.details.JptUiDetailsMessages;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

public class JavaDefaultTypeMappingUiProvider implements DefaultTypeMappingUiProvider<TypeMapping>
{

	// singleton
	private static final JavaDefaultTypeMappingUiProvider INSTANCE = new JavaDefaultTypeMappingUiProvider();
	
	/**
	 * Return the singleton.
	 */
	public static DefaultTypeMappingUiProvider<TypeMapping> instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Ensure single instance.
	 */
	private JavaDefaultTypeMappingUiProvider() {
		super();
	}
	
	public IContentType getContentType() {
		return JptCorePlugin.JAVA_SOURCE_CONTENT_TYPE;
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
	
	public JpaComposite buildPersistentTypeMappingComposite(JpaUiFactory factory, PropertyValueModel<TypeMapping> subjectHolder, Composite parent, WidgetFactory widgetFactory) {
		return null;
	}
	
}

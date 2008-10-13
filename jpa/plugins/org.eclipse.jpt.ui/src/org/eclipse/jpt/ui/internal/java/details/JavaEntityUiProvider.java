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
import org.eclipse.jpt.core.context.java.JavaEntity;
import org.eclipse.jpt.ui.JpaUiFactory;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.details.TypeMappingUiProvider;
import org.eclipse.jpt.ui.internal.JpaMappingImageHelper;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

public class JavaEntityUiProvider implements TypeMappingUiProvider<JavaEntity>
{
	// singleton
	private static final JavaEntityUiProvider INSTANCE = new JavaEntityUiProvider();

	/**
	 * Return the singleton.
	 */
	public static TypeMappingUiProvider<JavaEntity> instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private JavaEntityUiProvider() {
		super();
	}

	public String getMappingKey() {
		return MappingKeys.ENTITY_TYPE_MAPPING_KEY;
	}

	public String getLabel() {
		return JptUiMappingsMessages.EntityUiProvider_label;
	}

	public String getLinkLabel() {
		return JptUiMappingsMessages.EntityUiProvider_linkLabel;
	}
	
	public Image getImage() {
		return JpaMappingImageHelper.imageForTypeMapping(getMappingKey());
	}

	public JpaComposite buildPersistentTypeMappingComposite(
		JpaUiFactory factory,
		PropertyValueModel<JavaEntity> subjectHolder,
		Composite parent,
		WidgetFactory widgetFactory) {

		return factory.createJavaEntityComposite(subjectHolder, parent, widgetFactory);
	}
}

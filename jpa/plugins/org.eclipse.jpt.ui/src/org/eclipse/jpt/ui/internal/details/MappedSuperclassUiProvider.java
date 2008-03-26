/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.MappedSuperclass;
import org.eclipse.jpt.ui.JpaUiFactory;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.details.TypeMappingUiProvider;
import org.eclipse.jpt.ui.internal.JpaMappingImageHelper;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

public class MappedSuperclassUiProvider implements TypeMappingUiProvider<MappedSuperclass>
{
	// singleton
	private static final MappedSuperclassUiProvider INSTANCE = new MappedSuperclassUiProvider();

	/**
	 * Return the singleton.
	 */
	public static TypeMappingUiProvider<MappedSuperclass> instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private MappedSuperclassUiProvider() {
		super();
	}

	public String getMappingKey() {
		return MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY;
	}

	public String getLabel() {
		return JptUiMappingsMessages.PersistentTypePage_MappedSuperclassLabel;
	}

	public Image getImage() {
		return JpaMappingImageHelper.imageForTypeMapping(getMappingKey());
	}

	public JpaComposite<MappedSuperclass> buildPersistentTypeMappingComposite(
		JpaUiFactory factory,
		PropertyValueModel<MappedSuperclass> subjectHolder,
		Composite parent,
		WidgetFactory widgetFactory) {

		return factory.createMappedSuperclassComposite(subjectHolder, parent, widgetFactory);
	}
}

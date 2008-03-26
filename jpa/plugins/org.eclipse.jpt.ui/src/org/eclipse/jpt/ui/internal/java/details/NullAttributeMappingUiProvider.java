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
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.ui.JpaUiFactory;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.AttributeMappingUiProvider;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.internal.JpaMappingImageHelper;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

public class NullAttributeMappingUiProvider
	implements AttributeMappingUiProvider<AttributeMapping>
{

	// singleton
	private static final NullAttributeMappingUiProvider INSTANCE = new NullAttributeMappingUiProvider();

	/**
	 * Return the singleton.
	 */
	public static AttributeMappingUiProvider<AttributeMapping> instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private NullAttributeMappingUiProvider() {
		super();
	}

	/*
	 * (non-Javadoc)
	 */
	public String getMappingKey() {
		return MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY;
	}

	/*
	 * (non-Javadoc)
	 */
	public String getLabel() {
		return JptUiMappingsMessages.NullAttributeMappingUiProvider_label;
	}

	/*
	 * (non-Javadoc)
	 */
	public Image getImage() {
		return JpaMappingImageHelper.imageForAttributeMapping(getMappingKey());
	}

	/*
	 * (non-Javadoc)
	 */
	public JpaComposite<AttributeMapping> buildAttributeMappingComposite(
		JpaUiFactory factory,
		PropertyValueModel<AttributeMapping> subjectHolder,
		Composite parent,
		WidgetFactory widgetFactory) {

		return new NullComposite(subjectHolder, parent, widgetFactory);
	}

	public static class NullComposite extends AbstractFormPane<AttributeMapping>
	                                  implements JpaComposite<AttributeMapping>{

		NullComposite(PropertyValueModel<AttributeMapping> subjectHolder,
		              Composite parent,
		              WidgetFactory widgetFactory) {

			super(subjectHolder, parent, widgetFactory);
		}

		/*
		 * (non-Javadoc)
		 */
		@Override
		protected void initializeLayout(Composite container) {
		}
	}
}
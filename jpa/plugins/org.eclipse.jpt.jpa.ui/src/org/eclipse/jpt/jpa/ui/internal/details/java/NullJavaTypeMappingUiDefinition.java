/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
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
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.details.java.DefaultJavaTypeMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.details.java.JavaUiFactory;
import org.eclipse.jpt.jpa.ui.internal.JptUiIcons;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.details.JptUiDetailsMessages;
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

	public String getKey() {
		return MappingKeys.NULL_TYPE_MAPPING_KEY;
	}

	public String getDefaultKey() {
		return MappingKeys.NULL_TYPE_MAPPING_KEY;
	}

	public String getLabel() {
		return JptUiDetailsMessages.NullTypeMappingUiProvider_label;
	}

	public String getLinkLabel() {
		return null;
	}

	@Override
	protected String getImageKey() {
		return JptUiIcons.NULL_TYPE_MAPPING;
	}

	public JpaComposite buildTypeMappingComposite(JavaUiFactory factory, PropertyValueModel<JavaTypeMapping> mappingModel, Composite parent, WidgetFactory widgetFactory) {
		return new NullComposite(mappingModel, parent, widgetFactory);
	}


	// ********** null composite **********

	/* CU private */ static class NullComposite 
		extends Pane<JavaTypeMapping>
		implements JpaComposite 
	{
		NullComposite(PropertyValueModel<JavaTypeMapping> mappingModel, Composite parent, WidgetFactory widgetFactory) {
			super(mappingModel, parent, widgetFactory);
		}

		@Override
		protected void initializeLayout(Composite container) {
			// NOP
		}
	}
}

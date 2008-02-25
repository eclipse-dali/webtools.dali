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

import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.ui.JpaUiFactory;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.ui.java.details.AttributeMappingUiProvider;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

@SuppressWarnings("nls")
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
	public String attributeMappingKey() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 */
	public String label() {
		return "";
	}

	/*
	 * (non-Javadoc)
	 */
	public JpaComposite<AttributeMapping> buildAttributeMappingComposite(
			JpaUiFactory factory,
			PropertyValueModel<AttributeMapping> subjectHolder,
			Composite parent,
			TabbedPropertySheetWidgetFactory widgetFactory) {

		return new NullComposite(subjectHolder, parent, widgetFactory);
	}

	public static class NullComposite extends AbstractFormPane<AttributeMapping>
	                                  implements JpaComposite<AttributeMapping>{

		NullComposite(PropertyValueModel<AttributeMapping> subjectHolder,
		              Composite parent,
		              TabbedPropertySheetWidgetFactory widgetFactory) {

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
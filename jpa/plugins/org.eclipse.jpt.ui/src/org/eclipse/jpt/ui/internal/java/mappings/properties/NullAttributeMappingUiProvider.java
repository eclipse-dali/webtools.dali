/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.java.mappings.properties;

import org.eclipse.jpt.core.internal.context.base.IAttributeMapping;
import org.eclipse.jpt.ui.internal.IJpaUiFactory;
import org.eclipse.jpt.ui.internal.details.IJpaComposite;
import org.eclipse.jpt.ui.internal.java.details.IAttributeMappingUiProvider;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

@SuppressWarnings("nls")
public class NullAttributeMappingUiProvider
	implements IAttributeMappingUiProvider<IAttributeMapping>
{

	// singleton
	private static final NullAttributeMappingUiProvider INSTANCE = new NullAttributeMappingUiProvider();

	/**
	 * Return the singleton.
	 */
	public static IAttributeMappingUiProvider<IAttributeMapping> instance() {
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
	public IJpaComposite<IAttributeMapping> buildAttributeMappingComposite(
			IJpaUiFactory factory,
			PropertyValueModel<IAttributeMapping> subjectHolder,
			Composite parent,
			TabbedPropertySheetWidgetFactory widgetFactory) {

		return new NullComposite(subjectHolder, parent, widgetFactory);
	}

	public static class NullComposite extends AbstractFormPane<IAttributeMapping>
	                                  implements IJpaComposite<IAttributeMapping>{

		NullComposite(PropertyValueModel<IAttributeMapping> subjectHolder,
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
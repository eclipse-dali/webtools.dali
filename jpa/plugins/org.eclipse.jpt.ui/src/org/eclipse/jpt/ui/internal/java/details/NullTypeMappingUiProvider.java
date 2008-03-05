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

import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.ui.java.details.TypeMappingUiProvider;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

@SuppressWarnings("nls")
public class NullTypeMappingUiProvider implements TypeMappingUiProvider<TypeMapping>
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
	 * Ensure non-instantiability.
	 */
	private NullTypeMappingUiProvider() {
		super();
	}

	public String mappingKey() {
		return null;
	}

	public String label() {
		return "";
	}

	public JpaComposite<TypeMapping> buildPersistentTypeMappingComposite(
			PropertyValueModel<TypeMapping> subjectHolder,
			Composite parent,
			TabbedPropertySheetWidgetFactory widgetFactory) {

		return new NullComposite(subjectHolder, parent, widgetFactory);
	}


	public static class NullComposite extends AbstractFormPane<TypeMapping>
	                                  implements JpaComposite<TypeMapping>
	{
		NullComposite(PropertyValueModel<TypeMapping> subjectHolder,
		              Composite parent,
		              TabbedPropertySheetWidgetFactory widgetFactory) {

			super(subjectHolder, parent, widgetFactory);
		}

		@Override
		protected void initializeLayout(Composite container) {
		}
	}
}
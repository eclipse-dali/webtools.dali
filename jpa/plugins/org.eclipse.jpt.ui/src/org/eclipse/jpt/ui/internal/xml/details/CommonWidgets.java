/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.xml.details;

import org.eclipse.jpt.core.internal.context.orm.XmlAttributeMapping;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentType;
import org.eclipse.jpt.core.internal.resource.orm.AttributeMapping;
import org.eclipse.jpt.ui.internal.mappings.details.StringWithDefaultChooser;
import org.eclipse.jpt.ui.internal.xml.JptUiXmlMessages;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class CommonWidgets
{
	public static Label buildJavaClassLabel(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		Label label = widgetFactory.createLabel(parent, JptUiXmlMessages.PersistentTypePage_javaClassLabel);
		return label;
	}

	public static XmlJavaClassChooser buildJavaClassChooser(
			PropertyValueModel<? extends XmlPersistentType> subjectHolder,
			Composite parent,
			TabbedPropertySheetWidgetFactory widgetFactory) {

		return new XmlJavaClassChooser(subjectHolder, parent, widgetFactory);
	}

	public static Label buildJavaAttributeNameLabel(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		Label label = widgetFactory.createLabel(parent, JptUiXmlMessages.PersistentAttributePage_javaAttributeLabel);
		return label;
	}

	public static XmlJavaAttributeChooser buildJavaAttributeChooser(
			PropertyValueModel<? extends XmlAttributeMapping<? extends AttributeMapping>> subjectHolder,
			Composite parent,
			TabbedPropertySheetWidgetFactory widgetFactory) {

		return new XmlJavaAttributeChooser(subjectHolder, parent, widgetFactory);
	}


	public static Label buildAccessLabel(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		return widgetFactory.createLabel(parent, JptUiXmlMessages.PersistentTypePage_AccessLabel);
	}

	public static <T> AccessTypeComboViewer<T> buildAccessTypeComboViewer(
		PropertyValueModel<? extends AccessTypeComboViewer.AccessHolder<? extends T>> subjectHolder,
		Composite parent,
		TabbedPropertySheetWidgetFactory widgetFactory) {

		return new AccessTypeComboViewer<T>(subjectHolder, parent, widgetFactory);
	}


	public static Label buildCatalogLabel(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		return widgetFactory.createLabel(parent, JptUiXmlMessages.XmlCatalogChooser_CatalogChooser);
	}

	public static StringWithDefaultChooser buildCatalogChooser(
			PropertyValueModel<?> subjectHolder,
			Composite parent,
			TabbedPropertySheetWidgetFactory widgetFactory) {

		return new StringWithDefaultChooser(subjectHolder, parent, widgetFactory);
	}


	public static Label buildSchemaLabel(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		return widgetFactory.createLabel(parent, JptUiXmlMessages.XmlSchemaChooser_SchemaChooser);
	}

	public static StringWithDefaultChooser buildSchemaChooser(
			PropertyValueModel<?> subjectHolder,
			Composite parent,
			TabbedPropertySheetWidgetFactory widgetFactory) {

		return new StringWithDefaultChooser(subjectHolder, parent, widgetFactory);
	}


	public static Label buildPackageLabel(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		return widgetFactory.createLabel(parent, "Package:");
	}

	public static XmlPackageChooser buildXmlPackageChooser(
			PropertyValueModel<?> subjectHolder,
			Composite parent,
			TabbedPropertySheetWidgetFactory widgetFactory) {

		return new XmlPackageChooser(subjectHolder, parent, widgetFactory);
	}
}
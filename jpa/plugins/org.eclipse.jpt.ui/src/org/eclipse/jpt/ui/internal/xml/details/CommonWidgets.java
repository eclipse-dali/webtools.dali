/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.xml.details;

import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.jpt.ui.internal.mappings.details.StringWithDefaultChooser;
import org.eclipse.jpt.ui.internal.xml.JptUiXmlMessages;
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
			Composite parent, CommandStack commandStack, TabbedPropertySheetWidgetFactory widgetFactory) {
		return new XmlJavaClassChooser(parent, commandStack, widgetFactory);
	}
	
	public static Label buildJavaAttributeNameLabel(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		Label label = widgetFactory.createLabel(parent, JptUiXmlMessages.PersistentAttributePage_javaAttributeLabel);
		return label;
	}
	
	public static XmlJavaAttributeChooser buildJavaAttributeChooser(
			Composite parent, CommandStack commandStack, TabbedPropertySheetWidgetFactory widgetFactory) {
		return new XmlJavaAttributeChooser(parent, commandStack, widgetFactory);
	}
	
	
	public static Label buildAccessLabel(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		return widgetFactory.createLabel(parent, JptUiXmlMessages.PersistentTypePage_AccessLabel);
	}
	
	public static AccessTypeComboViewer buildAccessTypeComboViewer(
			Composite parent, CommandStack commandStack, TabbedPropertySheetWidgetFactory widgetFactory) {
		return new AccessTypeComboViewer(parent, commandStack, widgetFactory);
	}

	
	public static Label buildCatalogLabel(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		return widgetFactory.createLabel(parent, JptUiXmlMessages.XmlCatalogChooser_CatalogChooser);
	}
	
	public static StringWithDefaultChooser buildCatalogChooser(
			Composite parent, CommandStack commandStack, TabbedPropertySheetWidgetFactory widgetFactory) {
		return new StringWithDefaultChooser(parent, commandStack, widgetFactory);
	}

	
	public static Label buildSchemaLabel(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		return widgetFactory.createLabel(parent, JptUiXmlMessages.XmlSchemaChooser_SchemaChooser);
	}
	
	public static StringWithDefaultChooser buildSchemaChooser(
			Composite parent, CommandStack commandStack, TabbedPropertySheetWidgetFactory widgetFactory) {
		return new StringWithDefaultChooser(parent, commandStack, widgetFactory);
	}

	
	public static Label buildPackageLabel(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		return widgetFactory.createLabel(parent, "Package:");
	}
	
	public static XmlPackageChooser buildXmlPackageChooser(
			Composite parent, CommandStack commandStack, TabbedPropertySheetWidgetFactory widgetFactory) {
		return new XmlPackageChooser(parent, commandStack, widgetFactory);
	}

}

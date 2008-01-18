/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;


import org.eclipse.jpt.core.internal.context.base.IEntity;
import org.eclipse.jpt.core.internal.context.base.IRelationshipMapping;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class CommonWidgets
{
	public static Label buildEntityNameLabel(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		return widgetFactory.createLabel(parent, JptUiMappingsMessages.EntityGeneralSection_name);
	}

	public static EntityNameCombo buildEntityNameCombo(
			PropertyValueModel<? extends IEntity> subjectHolder,
			Composite parent,
			TabbedPropertySheetWidgetFactory widgetFactory) {

		return new EntityNameCombo(subjectHolder, parent, widgetFactory);
	}

	public static Label buildTableLabel(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		return widgetFactory.createLabel(parent, JptUiMappingsMessages.TableChooser_label);
	}

	public static Label buildCatalogLabel(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		return widgetFactory.createLabel(parent, JptUiMappingsMessages.CatalogChooser_label);
	}

	public static Label buildSchemaLabel(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		return widgetFactory.createLabel(parent, JptUiMappingsMessages.SchemaChooser_label);
	}

	public static StringWithDefaultChooser buildStringWithDefaultChooser(
			PropertyValueModel<?> subjectHolder,
			Composite parent,
			TabbedPropertySheetWidgetFactory widgetFactory) {

		return new StringWithDefaultChooser(subjectHolder, parent, widgetFactory);
	}

	public static Label buildFetchLabel(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		return widgetFactory.createLabel(parent, JptUiMappingsMessages.BasicGeneralSection_fetchLabel);
	}

	public static Label buildTargetEntityLabel(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		return widgetFactory.createLabel(parent, JptUiMappingsMessages.TargetEntityChooser_label);
	}

	public static Label buildOptionalLabel(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		return widgetFactory.createLabel(parent, JptUiMappingsMessages.BasicGeneralSection_optionalLabel);
	}

	public static Label buildTemporalLabel(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		return widgetFactory.createLabel(parent, JptUiMappingsMessages.BasicGeneralSection_temporalLabel);
	}

	public static Label buildEnumeratedLabel(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		return widgetFactory.createLabel(parent, JptUiMappingsMessages.BasicGeneralSection_enumeratedLabel);
	}

	public static Label buildMappedByLabel(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		return widgetFactory.createLabel(parent, JptUiMappingsMessages.NonOwningMapping_mappedByLabel);
	}

//	public static EnumHolder<IMultiRelationshipMapping, FetchType> buildMultiRelationshipMappingFetchEnumHolder(IMultiRelationshipMapping mapping) {
//		return new FetchHolder(mapping);
//	}

//	public static EnumHolder<ISingleRelationshipMapping, FetchType> buildSingleRelationshipMappingFetchEnumHolder(ISingleRelationshipMapping mapping) {
//		return new SingleRelationshipMappingFetchHolder(mapping);
//	}

//	public static EnumHolder<ISingleRelationshipMapping, Boolean> buildOptionalHolder(ISingleRelationshipMapping mapping) {
//		return new OptionalHolder(mapping);
//	}

	public static Label buildColumnLabel(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		return widgetFactory.createLabel(parent, JptUiMappingsMessages.ColumnChooser_label);
	}

	public static Label buildColumnTableLabel(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		return widgetFactory.createLabel(parent, JptUiMappingsMessages.ColumnTableChooser_label);
	}

//	public static ColumnTableHolder buildColumnTableHolder(IColumn column) {
//		return new ColumnTableHolder(column);
//	}

	public static TargetEntityComposite buildTargetEntityChooser(
			PropertyValueModel<? extends IRelationshipMapping> subjectHolder,
			Composite parent,
			TabbedPropertySheetWidgetFactory widgetFactory) {

		return new TargetEntityComposite(subjectHolder, parent, widgetFactory);
	}
}
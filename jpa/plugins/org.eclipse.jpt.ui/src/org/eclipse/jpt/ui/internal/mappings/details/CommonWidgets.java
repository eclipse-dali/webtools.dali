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


import org.eclipse.jpt.core.internal.context.base.FetchType;
import org.eclipse.jpt.core.internal.context.base.IAbstractColumn;
import org.eclipse.jpt.core.internal.context.base.IColumn;
import org.eclipse.jpt.core.internal.context.base.IEntity;
import org.eclipse.jpt.core.internal.context.base.IMultiRelationshipMapping;
import org.eclipse.jpt.core.internal.context.base.IRelationshipMapping;
import org.eclipse.jpt.core.internal.context.base.ISingleRelationshipMapping;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.mappings.details.StringWithDefaultChooser.StringHolder;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.osgi.util.NLS;
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

	public static EnumHolder<IMultiRelationshipMapping, FetchType> buildMultiRelationshipMappingFetchEnumHolder(IMultiRelationshipMapping mapping) {
		return new FetchHolder(mapping);
	}

	public static EnumHolder<ISingleRelationshipMapping, FetchType> buildSingleRelationshipMappingFetchEnumHolder(ISingleRelationshipMapping mapping) {
		return new SingleRelationshipMappingFetchHolder(mapping);
	}

	public static EnumHolder<ISingleRelationshipMapping, Boolean> buildOptionalHolder(ISingleRelationshipMapping mapping) {
		return new OptionalHolder(mapping);
	}

	public static Label buildColumnLabel(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		return widgetFactory.createLabel(parent, JptUiMappingsMessages.ColumnChooser_label);
	}

	public static Label buildColumnTableLabel(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		return widgetFactory.createLabel(parent, JptUiMappingsMessages.ColumnTableChooser_label);
	}

	public static ColumnTableHolder buildColumnTableHolder(IColumn column) {
		return new ColumnTableHolder(column);
	}

	public static TargetEntityChooser buildTargetEntityChooser(
			PropertyValueModel<? extends IRelationshipMapping> subjectHolder,
			Composite parent,
			TabbedPropertySheetWidgetFactory widgetFactory) {

		return new TargetEntityChooser(subjectHolder, parent, widgetFactory);
	}

	private static class FetchHolder implements EnumHolder<IMultiRelationshipMapping, FetchType> {

		private IMultiRelationshipMapping mapping;

		FetchHolder(IMultiRelationshipMapping mapping) {
			super();
			this.mapping = mapping;
		}

		public FetchType get() {
			return this.mapping.getFetch();
		}

		public void set(FetchType enumSetting) {
			this.mapping.setSpecifiedFetch(enumSetting);

		}

		public Class<IMultiRelationshipMapping> featureClass() {
			return IMultiRelationshipMapping.class;
		}

		public int featureId() {
			return JpaCoreMappingsPackage.IMULTI_RELATIONSHIP_MAPPING__FETCH;
		}

		public IMultiRelationshipMapping subject() {
			return this.mapping;
		}

		public FetchType[] enumValues() {
			return FetchType.values();
		}

		public FetchType defaultValue() {
			return DefaultLazyFetchType.DEFAULT;
		}

		public String defaultString() {
			//TODO move this out of the UI into the model
			return "Lazy";
		}
	}

	private static class SingleRelationshipMappingFetchHolder implements EnumHolder<ISingleRelationshipMapping, FetchType> {

		private ISingleRelationshipMapping mapping;

		SingleRelationshipMappingFetchHolder(ISingleRelationshipMapping mapping) {
			super();
			this.mapping = mapping;
		}

		public FetchType get() {
			return this.mapping.getFetch();
		}

		public void set(FetchType enumSetting) {
			this.mapping.setSpecifiedFetch(enumSetting);
		}

		public Class<ISingleRelationshipMapping> featureClass() {
			return ISingleRelationshipMapping.class;
		}

		public int featureId() {
			return JpaCoreMappingsPackage.ISINGLE_RELATIONSHIP_MAPPING__FETCH;
		}

		public ISingleRelationshipMapping subject() {
			return this.mapping;
		}

		public FetchType[] enumValues() {
			return FetchType.values();
		}

		public FetchType defaultValue() {
			return DefaultEagerFetchType.DEFAULT;
		}

		public String defaultString() {
			//TODO move this out of the UI into the model
			return "Eager";
		}
	}
	private static abstract class ColumnHolder implements StringHolder<IAbstractColumn> {
		private IAbstractColumn column;

		ColumnHolder(IAbstractColumn column) {
			super();
			this.column = column;
		}

		public Class<IAbstractColumn> featureClass() {
			return IAbstractColumn.class;
		}

		public boolean supportsDefault() {
			return true;
		}

		public IAbstractColumn wrappedObject() {
			return this.column;
		}

		protected IAbstractColumn getColumn() {
			return this.column;
		}
	}

	public static class ColumnTableHolder extends ColumnHolder implements StringHolder<IAbstractColumn> {

		ColumnTableHolder(IAbstractColumn column) {
			super(column);
		}

		public int featureId() {
			return JpaCoreMappingsPackage.IABSTRACT_COLUMN__SPECIFIED_TABLE;
		}

		public int defaultFeatureId() {
			return JpaCoreMappingsPackage.IABSTRACT_COLUMN__DEFAULT_TABLE;
		}

		public String defaultItem() {
			String defaultName = getColumn().getDefaultTable();
			if (defaultName != null) {
				return NLS.bind(JptUiMappingsMessages.ColumnComposite_defaultWithOneParam, defaultName);
			}
			return JptUiMappingsMessages.ColumnComposite_defaultEmpty;
		}

		public String getString() {
			return getColumn().getSpecifiedTable();
		}

		public void setString(String newName) {
			getColumn().setSpecifiedTable(newName);
		}
	}

	private static class OptionalHolder implements EnumHolder<ISingleRelationshipMapping, Boolean> {

		private ISingleRelationshipMapping mapping;

		OptionalHolder(ISingleRelationshipMapping mapping) {
			super();
			this.mapping = mapping;
		}

		public Boolean get() {
			return this.mapping.getOptional();
		}

		public void set(Boolean enumSetting) {
			this.mapping.setSpecifiedOptional(enumSetting);
		}

		public Class<ISingleRelationshipMapping> featureClass() {
			return ISingleRelationshipMapping.class;
		}

		public int featureId() {
			return JpaCoreMappingsPackage.ISINGLE_RELATIONSHIP_MAPPING__OPTIONAL;
		}

		public ISingleRelationshipMapping subject() {
			return this.mapping;
		}

		public Boolean[] enumValues() {
			//TODO
			return new Boolean[] { Boolean.TRUE, Boolean.FALSE };
//			return DefaultTrueBoolean.VALUES.toArray();
		}

		public Boolean defaultValue() {
			return DefaultTrueBoolean.DEFAULT;
		}

		public String defaultString() {
			//TODO move this out of the UI into the model
			return "True";
		}

	}
}
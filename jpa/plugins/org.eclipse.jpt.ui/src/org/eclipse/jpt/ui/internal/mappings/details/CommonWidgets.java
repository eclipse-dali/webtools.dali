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


import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.jpt.core.internal.mappings.DefaultEagerFetchType;
import org.eclipse.jpt.core.internal.mappings.DefaultLazyFetchType;
import org.eclipse.jpt.core.internal.mappings.EnumType;
import org.eclipse.jpt.core.internal.mappings.IAbstractColumn;
import org.eclipse.jpt.core.internal.mappings.IColumn;
import org.eclipse.jpt.core.internal.mappings.IMultiRelationshipMapping;
import org.eclipse.jpt.core.internal.mappings.ISingleRelationshipMapping;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.mappings.details.EnumComboViewer.EnumHolder;
import org.eclipse.jpt.ui.internal.mappings.details.StringWithDefaultChooser.StringHolder;
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
			Composite parent, CommandStack commandStack, TabbedPropertySheetWidgetFactory widgetFactory) {
		return new EntityNameCombo(parent, commandStack, widgetFactory);
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
			Composite parent, CommandStack commandStack, TabbedPropertySheetWidgetFactory widgetFactory) {
		return new StringWithDefaultChooser(parent, commandStack, widgetFactory);
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
	
	public static EnumComboViewer buildFetchTypeComboViewer(
			Composite parent, CommandStack commandStack, TabbedPropertySheetWidgetFactory widgetFactory) {
		return new EnumComboViewer(parent, commandStack, widgetFactory);
	}

	public static EnumComboViewer buildEnumComboViewer(
			Composite parent, CommandStack commandStack, TabbedPropertySheetWidgetFactory widgetFactory) {
		return new EnumComboViewer(parent, commandStack, widgetFactory);
	}
	
	public static EnumHolder buildMultiRelationshipMappingFetchEnumHolder(IMultiRelationshipMapping mapping) {
		return new FetchHolder(mapping);
	}
	
	public static EnumHolder buildSingleRelationshipMappingFetchEnumHolder(ISingleRelationshipMapping mapping) {
		return new SingleRelationshipMappingFetchHolder(mapping);
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

	public static TargetEntityChooser buildTargetEntityChooser(Composite parent, CommandStack commandStack, TabbedPropertySheetWidgetFactory widgetFactory) {
		return new TargetEntityChooser(parent, commandStack, widgetFactory);
	}
	private static class FetchHolder extends EObjectImpl implements EnumHolder {
		
		private IMultiRelationshipMapping mapping;
		
		FetchHolder(IMultiRelationshipMapping mapping) {
			super();
			this.mapping = mapping;
		}
		
		public Object get() {
			return this.mapping.getFetch();
		}
		
		public void set(Object enumSetting) {
			this.mapping.setFetch((DefaultLazyFetchType) enumSetting);
			
		}
		
		public Class featureClass() {
			return IMultiRelationshipMapping.class;
		}
		
		public int featureId() {
			return JpaCoreMappingsPackage.IMULTI_RELATIONSHIP_MAPPING__FETCH;
		}
		
		public EObject wrappedObject() {
			return this.mapping;
		}
		
		public Object[] enumValues() {
			return DefaultLazyFetchType.VALUES.toArray();
		}
		
		public Object defaultValue() {
			return DefaultLazyFetchType.DEFAULT;
		}
		
		public String defaultString() {
			//TODO move this out of the UI into the model
			return "Lazy";
		}
	}
	
	private static class SingleRelationshipMappingFetchHolder extends EObjectImpl implements EnumHolder {
		
		private ISingleRelationshipMapping mapping;
		
		SingleRelationshipMappingFetchHolder(ISingleRelationshipMapping mapping) {
			super();
			this.mapping = mapping;
		}
		
		public Object get() {
			return this.mapping.getFetch();
		}
		
		public void set(Object enumSetting) {
			this.mapping.setFetch((DefaultEagerFetchType) enumSetting);
			
		}
		
		public Class featureClass() {
			return ISingleRelationshipMapping.class;
		}
		
		public int featureId() {
			return JpaCoreMappingsPackage.ISINGLE_RELATIONSHIP_MAPPING__FETCH;
		}
		
		public EObject wrappedObject() {
			return this.mapping;
		}
		
		public Object[] enumValues() {
			return DefaultEagerFetchType.VALUES.toArray();
		}
		
		public Object defaultValue() {
			return DefaultEagerFetchType.DEFAULT;
		}
		
		public String defaultString() {
			//TODO move this out of the UI into the model
			return "Eager";
		}
	}	
	private static abstract class ColumnHolder extends EObjectImpl implements StringHolder {
		private IAbstractColumn column;
		
		ColumnHolder(IAbstractColumn column) {
			super();
			this.column = column;
		}
		
		public Class featureClass() {
			return IAbstractColumn.class;
		}
		
		public boolean supportsDefault() {
			return true;
		}
		
		public EObject wrappedObject() {
			return this.column;
		}
		
		protected IAbstractColumn getColumn() {
			return this.column;
		}
	}
	
	public static class ColumnTableHolder extends ColumnHolder implements StringHolder {
		
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
}

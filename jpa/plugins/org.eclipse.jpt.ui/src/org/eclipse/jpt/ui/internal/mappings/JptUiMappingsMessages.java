/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/   
package org.eclipse.jpt.ui.internal.mappings;

import org.eclipse.osgi.util.NLS;

public class JptUiMappingsMessages extends NLS 
{
	private static final String BUNDLE_NAME = "jpt_ui_mappings"; //$NON-NLS-1$
	
	public static String PersistentTypePage_EntityLabel;
	public static String PersistentTypePage_EmbeddableLabel;
	public static String PersistentTypePage_MappedSuperclassLabel;

	public static String PersistentAttributePage_BasicLabel;
	public static String PersistentAttributePage_TransientLabel;
	public static String PersistentAttributePage_IdLabel;
	public static String PersistentAttributePage_OneToManyLabel;
	public static String PersistentAttributePage_ManyToOneLabel;
	public static String PersistentAttributePage_ManyToManyLabel;
	public static String PersistentAttributePage_VersionLabel;
	public static String PersistentAttributePage_EmbeddedLabel;
	public static String PersistentAttributePage_EmbeddedIdLabel;
	public static String PersistentAttributePage_OneToOneLabel;

	
	public static String EntityGeneralSection_nameDefaultWithOneParam;
	public static String EntityGeneralSection_nameDefaultEmpty;
	public static String EntityGeneralSection_name;
	
	public static String BasicGeneralSection_name;
	public static String BasicGeneralSection_nameDefault;
	public static String BasicGeneralSection_fetchLabel;
	public static String BasicGeneralSection_optionalLabel;
	public static String BasicGeneralSection_lobLabel;
	public static String BasicGeneralSection_temporalLabel;
	public static String BasicGeneralSection_enumeratedLabel;

	public static String EntityComposite_tableDefault;
	public static String EntityComposite_tableNoDefaultSpecified;
	public static String EntityComposite_inheritance;
	public static String TableComposite_tableSection;
	public static String TableComposite_defaultEmpty;
	public static String TableComposite_defaultWithOneParam;
	
	public static String TableChooser_label;
	public static String CatalogChooser_label;
	public static String SchemaChooser_label;

	public static String ColumnChooser_label;
	public static String ColumnTableChooser_label;
	
	public static String TargetEntityChooser_label;
	public static String TargetEntityChooser_defaultEmpty;
	public static String TargetEntityChooser_defaultWithOneParam;
	public static String TargetEntityChooser_browse;
	
	public static String NonOwningMapping_mappedByLabel;
		
	public static String JoinTableComposite_add;
	public static String JoinTableComposite_defaultEmpty;
	public static String JoinTableComposite_defaultWithOneParam;
	public static String JoinTableComposite_edit;
	public static String JoinTableComposite_inverseJoinColumn;
	public static String JoinTableComposite_joinColumn;
	public static String JoinTableComposite_mappingBetweenTwoParams;
	public static String JoinTableComposite_mappingBetweenTwoParamsDefault;
	public static String JoinTableComposite_mappingBetweenTwoParamsBothDefault;
	public static String JoinTableComposite_mappingBetweenTwoParamsFirstDefault;
	public static String JoinTableComposite_mappingBetweenTwoParamsSecDefault;
	public static String JoinTableComposite_name;
	public static String JoinTableComposite_remove;
	public static String JoinTableComposite_overrideDefaultJoinColumns;
	public static String JoinTableComposite_overrideDefaultInverseJoinColumns;

	public static String JoinColumnDialog_addJoinColumn;
	public static String JoinColumnDialog_editJoinColumn;
	public static String JoinColumnDialog_name;
	public static String JoinColumnDialog_insertable;
	public static String JoinColumnDialog_updatable;
	public static String JoinColumnDialog_defaultWithOneParam;
	public static String JoinColumnDialog_referencedColumnName;
	public static String JoinColumnDialog_table;
	
	public static String InverseJoinColumnDialog_defaultWithOneParam;
	public static String InverseJoinColumnDialog_editInverseJoinColumn;

	
	public static String MultiRelationshipMappingComposite_cascadeType;
	public static String MultiRelationshipMappingComposite_fetchType;
	public static String MultiRelationshipMappingComposite_general;
	public static String MultiRelationshipMappingComposite_joinTable;
	public static String MultiRelationshipMappingComposite_mappedBy;
	public static String MultiRelationshipMappingComposite_targetEntity;

	public static String ColumnComposite_columnSection;
	public static String ColumnComposite_defaultWithOneParam;
	public static String ColumnComposite_defaultEmpty;
	public static String ColumnComposite_insertable;	
	public static String ColumnComposite_updatable;

	public static String JoinColumnComposite_defaultEmpty;
	public static String JoinColumnComposite_defaultWithOneParam;
	public static String JoinColumnComposite_joinColumn;
	public static String JoinColumnComposite_name;
	public static String JoinColumnComposite_add;
	public static String JoinColumnComposite_edit;
	public static String JoinColumnComposite_mappingBetweenTwoParams;
	public static String JoinColumnComposite_mappingBetweenTwoParamsDefault;
	public static String JoinColumnComposite_mappingBetweenTwoParamsBothDefault;
	public static String JoinColumnComposite_mappingBetweenTwoParamsFirstDefault;
	public static String JoinColumnComposite_mappingBetweenTwoParamsSecDefault;
	public static String JoinColumnComposite_remove;
	public static String JoinColumnComposite_overrideDefaultJoinColumns;

	public static String PrimaryKeyJoinColumnsComposite_overrideDefaultPrimaryKeyJoinColumns;
	public static String PrimaryKeyJoinColumnsComposite_add;
	public static String PrimaryKeyJoinColumnsComposite_edit;
	public static String PrimaryKeyJoinColumnsComposite_remove;
	public static String PrimaryKeyJoinColumnsComposite_defaultEmpty;
	public static String PrimaryKeyJoinColumnsComposite_defaultWithOneParam;
	public static String PrimaryKeyJoinColumnsComposite_primaryKeyJoinColumn;
	public static String PrimaryKeyJoinColumnsComposite_mappingBetweenTwoParams;
	public static String PrimaryKeyJoinColumnsComposite_mappingBetweenTwoParamsDefault;
	public static String PrimaryKeyJoinColumnsComposite_mappingBetweenTwoParamsBothDefault;
	public static String PrimaryKeyJoinColumnsComposite_mappingBetweenTwoParamsFirstDefault;
	public static String PrimaryKeyJoinColumnsComposite_mappingBetweenTwoParamsSecDefault;
	
	public static String AttributeOverridesComposite_attributeOverrides;
	public static String AttributeOverridesComposite_overridDefault;
	public static String OverridesComposite_joinColumn;

	public static String InheritanceComposite_strategy;	
	public static String InheritanceComposite_discriminatorValue;	
	public static String InheritanceComposite_discriminatorValueDefaultWithOneParam;

	public static String DiscriminatorColumnComposite_defaultEmpty;	
	public static String DiscriminatorColumnComposite_column;	
	public static String DiscriminatorColumnComposite_discriminatorType;

	public static String GeneratedValueComposite_generatedValue;
	public static String GeneratedValueComposite_generatorName;
	public static String GeneratedValueComposite_strategy;

	public static String TableGeneratorComposite_default;
	public static String TableGeneratorComposite_name;
	public static String TableGeneratorComposite_pkColumn;
	public static String TableGeneratorComposite_pkColumnValue;
	public static String TableGeneratorComposite_table;
	public static String TableGeneratorComposite_tableGenerator;
	public static String TableGeneratorComposite_valueColumn;

	public static String SequenceGeneratorComposite_sequenceGenerator;
	public static String SequenceGeneratorComposite_name;
	public static String SequenceGeneratorComposite_sequence;
	public static String SequenceGeneratorComposite_default;

	public static String IdMappingComposite_pk_generation;	
	public static String IdMappingComposite_primaryKeyGeneration;	
	public static String IdMappingComposite_tableGenerator;
	public static String IdMappingComposite_sequenceGenerator;

	public static String OrderByComposite_orderByGroup;	
	public static String OrderByComposite_noOrdering;	
	public static String OrderByComposite_primaryKeyOrdering;	
	public static String OrderByComposite_customOrdering;
	public static String OrderByComposite_orderByLabel;

	public static String SecondaryTablesComposite_secondaryTables;
	public static String SecondaryTablesComposite_add;
	public static String SecondaryTablesComposite_edit;
	public static String SecondaryTablesComposite_remove;

	public static String SecondaryTableDialog_editSecondaryTable;
	public static String SecondaryTableDialog_name;
	public static String SecondaryTableDialog_catalog;
	public static String SecondaryTableDialog_schema;
	public static String SecondaryTableDialog_defaultSchema;
	public static String SecondaryTableDialog_defaultCatalog;	
	
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, JptUiMappingsMessages.class);
	}

	private JptUiMappingsMessages() {
		throw new UnsupportedOperationException();
	}

}

/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings;

import org.eclipse.osgi.util.NLS;

/**
 * Localized messages used by Dali mapping panes.
 *
 * @version 2.0
 * @since 1.0
 */
public class JptUiMappingsMessages {

	public static String AccessTypeCombo_default;
	public static String AttributeOverridesComposite_attributeOverridesGroup;
	public static String AttributeOverridesComposite_attributeOverridesSection;
	public static String AttributeOverridesComposite_overrideDefault;
	public static String BasicGeneralSection_enumeratedLabel;
	public static String BasicGeneralSection_fetchLabel;
	public static String BasicGeneralSection_lobLabel;
	public static String BasicGeneralSection_name;
	public static String BasicGeneralSection_nameDefault;
	public static String BasicGeneralSection_optionalLabel;
	public static String BasicGeneralSection_optionalLabelDefault;
	public static String BasicGeneralSection_temporalLabel;
	public static String TypeSection_type;
	public static String TypeSection_default;
	public static String TypeSection_lob;
	public static String TypeSection_temporal;
	public static String TypeSection_enumerated;
	public static String Boolean_False;
	public static String Boolean_True;
	public static String CascadeComposite_all;
	public static String CascadeComposite_cascadeTitle;
	public static String CascadeComposite_merge;
	public static String CascadeComposite_persist;
	public static String CascadeComposite_refresh;
	public static String CascadeComposite_remove;
	public static String CatalogChooser_label;
	public static String ColumnComposite_columnDefinition;
	public static String ColumnComposite_columnSection;
	public static String ColumnComposite_details;
	public static String ColumnComposite_insertable;
	public static String ColumnComposite_insertableWithDefault;
	public static String ColumnComposite_length;
	public static String ColumnComposite_name;
	public static String ColumnComposite_nullable;
	public static String ColumnComposite_nullableWithDefault;
	public static String ColumnComposite_precision;
	public static String ColumnComposite_scale;
	public static String ColumnComposite_table;
	public static String ColumnComposite_unique;
	public static String ColumnComposite_uniqueWithDefault;
	public static String ColumnComposite_updatable;
	public static String ColumnComposite_updatableWithDefault;
	public static String DefaultEmpty;
	public static String DefaultWithOneParam;
	public static String ProviderDefault;
	public static String DiscriminatorColumnComposite_discriminatorType;
	public static String DiscriminatorColumnComposite_name;
	public static String DiscriminatorColumnComposite_char;
	public static String DiscriminatorColumnComposite_integer;
	public static String DiscriminatorColumnComposite_string;
	public static String EntityComposite_inheritance;
	public static String EntityComposite_queries;
	public static String EntityComposite_tableDefault;
	public static String EntityComposite_tableNoDefaultSpecified;
	public static String EntityGeneralSection_name;
	public static String EntityNameComposite_name;
	public static String EnumTypeComposite_ordinal;
	public static String EnumTypeComposite_string;
	public static String FetchTypeComposite_eager;
	public static String FetchTypeComposite_lazy;
	public static String GeneratedValueComposite_auto;
	public static String GeneratedValueComposite_generatedValue;
	public static String GeneratedValueComposite_generatorName;
	public static String GeneratedValueComposite_identity;
	public static String GeneratedValueComposite_sequence;
	public static String GeneratedValueComposite_strategy;
	public static String GeneratedValueComposite_table;
	public static String GeneratorComposite_allocationSize;
	public static String GeneratorComposite_initialValue;
	public static String GeneratorsComposite_sequenceGeneratorCheckBox;
	public static String GeneratorsComposite_sequenceGeneratorSection;
	public static String GeneratorsComposite_tableGeneratorCheckBox;
	public static String GeneratorsComposite_tableGeneratorSection;
	public static String IdClassComposite_label;
	public static String IdMappingComposite_pk_generation;
	public static String IdMappingComposite_primaryKeyGenerationCheckBox;
	public static String IdMappingComposite_primaryKeyGenerationSection;
	public static String IdMappingComposite_sequenceGeneratorCheckBox;
	public static String IdMappingComposite_sequenceGeneratorSection;
	public static String IdMappingComposite_tableGeneratorCheckBox;
	public static String IdMappingComposite_tableGeneratorSection;
	public static String InheritanceComposite_detailsGroupBox;
	public static String InheritanceComposite_discriminatorColumnGroupBox;
	public static String InheritanceComposite_discriminatorValue;
	public static String AbstractInheritanceComposite_joined;
	public static String AbstractInheritanceComposite_single_table;
	public static String InheritanceComposite_strategy;
	public static String AbstractInheritanceComposite_table_per_class;
	public static String InverseJoinColumnDialog_editInverseJoinColumnTitle;
	public static String JoinColumnComposite_add;
	public static String JoinColumnComposite_edit;
	public static String JoinColumnComposite_joinColumn;
	public static String JoinColumnComposite_mappingBetweenTwoParams;
	public static String JoinColumnComposite_mappingBetweenTwoParamsBothDefault;
	public static String JoinColumnComposite_mappingBetweenTwoParamsDefault;
	public static String JoinColumnComposite_mappingBetweenTwoParamsFirstDefault;
	public static String JoinColumnComposite_mappingBetweenTwoParamsSecDefault;
	public static String JoinColumnComposite_name;
	public static String JoinColumnComposite_overrideDefaultJoinColumns;
	public static String JoinColumnComposite_remove;
	public static String JoinColumnDialog_addJoinColumnDescriptionTitle;
	public static String JoinColumnDialog_addJoinColumnTitle;
	public static String JoinColumnDialog_description;
	public static String JoinColumnDialog_editJoinColumnDescriptionTitle;
	public static String JoinColumnDialog_editJoinColumnTitle;
	public static String JoinColumnDialog_name;
	public static String JoinColumnDialog_referencedColumnName;
	public static String JoinColumnDialogPane_columnDefinition;
	public static String JoinColumnDialogPane_insertable;
	public static String JoinColumnDialogPane_insertableWithDefault;
	public static String JoinColumnDialogPane_nullable;
	public static String JoinColumnDialogPane_nullableWithDefault;
	public static String JoinColumnDialogPane_table;
	public static String JoinColumnDialogPane_unique;
	public static String JoinColumnDialogPane_uniqueWithDefault;
	public static String JoinColumnDialogPane_updatable;
	public static String JoinColumnDialogPane_updatableWithDefault;
	public static String JoinTableComposite_add;
	public static String JoinTableComposite_edit;
	public static String JoinTableComposite_inverseJoinColumn;
	public static String JoinTableComposite_joinColumn;
	public static String JoinTableComposite_mappingBetweenTwoParams;
	public static String JoinTableComposite_mappingBetweenTwoParamsBothDefault;
	public static String JoinTableComposite_mappingBetweenTwoParamsDefault;
	public static String JoinTableComposite_mappingBetweenTwoParamsFirstDefault;
	public static String JoinTableComposite_mappingBetweenTwoParamsSecDefault;
	public static String JoinTableComposite_name;
	public static String JoinTableComposite_overrideDefaultInverseJoinColumns;
	public static String JoinTableComposite_overrideDefaultJoinColumns;
	public static String JoinTableComposite_remove;
	
	public static String DefaultBasicMappingUiProvider_label;
	public static String DefaultEmbeddedMappingUiProvider_label;
	public static String BasicMappingUiProvider_label;
	public static String EmbeddedIdMappingUiProvider_label;
	public static String EmbeddedMappingUiProvider_label;
	public static String IdMappingUiProvider_label;
	public static String ManyToManyMappingUiProvider_label;
	public static String ManyToOneMappingUiProvider_label;
	public static String OneToManyMappingUiProvider_label;
	public static String OneToOneMappingUiProvider_label;
	public static String TransientMappingUiProvider_label;
	public static String VersionMappingUiProvider_label;
	public static String DefaultBasicMappingUiProvider_linkLabel;
	public static String DefaultEmbeddedMappingUiProvider_linkLabel;
	public static String BasicMappingUiProvider_linkLabel;
	public static String EmbeddedIdMappingUiProvider_linkLabel;
	public static String EmbeddedMappingUiProvider_linkLabel;
	public static String IdMappingUiProvider_linkLabel;
	public static String ManyToManyMappingUiProvider_linkLabel;
	public static String ManyToOneMappingUiProvider_linkLabel;
	public static String OneToManyMappingUiProvider_linkLabel;
	public static String OneToOneMappingUiProvider_linkLabel;
	public static String TransientMappingUiProvider_linkLabel;
	public static String VersionMappingUiProvider_linkLabel;

	public static String MapAsComposite_changeMappingType;
	public static String MapAsComposite_default;
	public static String MapAsComposite_dialogTitle;
	public static String MapAsComposite_labelText;
	public static String MapAsComposite_mappedAttributeText;
	public static String MapAsComposite_mappedTypeText;
	public static String MapAsComposite_unmappedAttributeText;
	public static String MapAsComposite_unmappedTypeText;
	public static String MapAsComposite_virtualAttributeText;
	
	public static String EmbeddableUiProvider_label;
	public static String EntityUiProvider_label;
	public static String MappedSuperclassUiProvider_label;
	public static String EmbeddableUiProvider_linkLabel;
	public static String EntityUiProvider_linkLabel;
	public static String MappedSuperclassUiProvider_linkLabel;

	public static String NullTypeMappingUiProvider_label;
	
	public static String MetaDataCompleteCombo_Default;
	public static String MultiRelationshipMappingComposite_cascadeType;
	public static String MultiRelationshipMappingComposite_fetchType;
	public static String MultiRelationshipMappingComposite_general;
	public static String MultiRelationshipMappingComposite_joinTable;
	public static String MultiRelationshipMappingComposite_mappedBy;
	public static String MultiRelationshipMappingComposite_targetEntity;
	public static String NamedNativeQueryPropertyComposite_query;
	public static String NamedNativeQueryPropertyComposite_queryHintsGroupBox;
	public static String NamedNativeQueryPropertyComposite_resultClass;
	public static String NamedQueryPropertyComposite_query;
	public static String NamedQueryPropertyComposite_queryHintsGroupBox;
	public static String NewNameStateObject_nameAlreadyExists;
	public static String NewNameStateObject_nameMustBeSpecified;
	public static String NoNameSet;
	public static String NoneSelected;
	public static String NonOwningMapping_mappedByLabel;
	public static String NullAttributeMappingUiProvider_label;
	public static String OptionalComposite_false;
	public static String OptionalComposite_true;
	public static String OrderByComposite_customOrdering;
	public static String OrderByComposite_noOrdering;
	public static String OrderByComposite_orderByGroup;
	public static String OrderByComposite_orderByLabel;
	public static String OrderByComposite_primaryKeyOrdering;
	public static String OrmSecondaryTablesComposite_defineInXml;
	public static String OverridesComposite_association;
	public static String OverridesComposite_attribute;
	public static String OverridesComposite_joinColumn;
	public static String OverridesComposite_noName;
	public static String PrimaryKeyJoinColumnDialog_addDescriptionTitle;
	public static String PrimaryKeyJoinColumnDialog_addTitle;
	public static String PrimaryKeyJoinColumnDialog_editDescriptionTitle;
	public static String PrimaryKeyJoinColumnDialog_editTitle;
	public static String PrimaryKeyJoinColumnInSecondaryTableDialog_addDescriptionTitle;
	public static String PrimaryKeyJoinColumnInSecondaryTableDialog_addTitle;
	public static String PrimaryKeyJoinColumnInSecondaryTableDialog_editDescriptionTitle;
	public static String PrimaryKeyJoinColumnInSecondaryTableDialog_editTitle;
	public static String PrimaryKeyJoinColumnsComposite_edit;
	public static String PrimaryKeyJoinColumnsComposite_mappingBetweenTwoParams;
	public static String PrimaryKeyJoinColumnsComposite_mappingBetweenTwoParamsBothDefault;
	public static String PrimaryKeyJoinColumnsComposite_mappingBetweenTwoParamsDefault;
	public static String PrimaryKeyJoinColumnsComposite_mappingBetweenTwoParamsFirstDefault;
	public static String PrimaryKeyJoinColumnsComposite_mappingBetweenTwoParamsSecDefault;
	public static String PrimaryKeyJoinColumnsComposite_overrideDefaultPrimaryKeyJoinColumns;
	public static String PrimaryKeyJoinColumnsComposite_primaryKeyJoinColumn;
	public static String QueriesComposite_addNamedNativeQuery;
	public static String QueriesComposite_addNamedNativeQueryDescription;
	public static String QueriesComposite_addNamedNativeQueryDescriptionTitle;
	public static String QueriesComposite_addNamedNativeQueryTitle;
	public static String QueriesComposite_addNamedQuery;
	public static String QueriesComposite_addNamedQueryDescription;
	public static String QueriesComposite_addNamedQueryDescriptionTitle;
	public static String QueriesComposite_addNamedQueryTitle;
	public static String QueriesComposite_displayString;
	public static String QueriesComposite_edit;
	public static String QueriesComposite_editNamedNativeQueryDescription;
	public static String QueriesComposite_editNamedNativeQueryDescriptionTitle;
	public static String QueriesComposite_editNamedNativeQueryTitle;
	public static String QueriesComposite_editNamedQueryDescription;
	public static String QueriesComposite_editNamedQueryDescriptionTitle;
	public static String QueriesComposite_editNamedQueryTitle;
	public static String QueriesComposite_label;
	public static String QueryHintsComposite_nameColumn;
	public static String QueryHintsComposite_valueColumn;
	public static String SchemaChooser_label;
	public static String SecondaryTableDialog_addSecondaryTable;
	public static String SecondaryTableDialog_catalog;
	public static String SecondaryTableDialog_defaultCatalog;
	public static String SecondaryTableDialog_defaultSchema;
	public static String SecondaryTableDialog_editSecondaryTable;
	public static String SecondaryTableDialog_name;
	public static String SecondaryTableDialog_schema;
	public static String SecondaryTablesComposite_edit;
	public static String SecondaryTablesComposite_secondaryTables;
	public static String SequenceGeneratorComposite_default;
	public static String SequenceGeneratorComposite_name;
	public static String SequenceGeneratorComposite_sequence;
	public static String SequenceGeneratorComposite_sequenceGenerator;
	public static String TableChooser_label;
	public static String TableComposite_tableSection;
	public static String TableGeneratorComposite_catalog;
	public static String TableGeneratorComposite_default;
	public static String TableGeneratorComposite_name;
	public static String TableGeneratorComposite_pkColumn;
	public static String TableGeneratorComposite_pkColumnValue;
	public static String TableGeneratorComposite_schema;
	public static String TableGeneratorComposite_table;
	public static String TableGeneratorComposite_tableGenerator;
	public static String TableGeneratorComposite_valueColumn;
	public static String TargetEntityChooser_browse;
	public static String TargetEntityChooser_label;
	public static String TargetEntityChooser_selectTypeTitle;
	public static String TemporalTypeComposite_date;
	public static String TemporalTypeComposite_time;
	public static String TemporalTypeComposite_timestamp;

	private static final String BUNDLE_NAME = "jpt_ui_mappings"; //$NON-NLS-1$
	private static final Class<?> BUNDLE_CLASS = JptUiMappingsMessages.class;
	static {
		NLS.initializeMessages(BUNDLE_NAME, BUNDLE_CLASS);
	}

	private JptUiMappingsMessages() {
		throw new UnsupportedOperationException();
	}

}

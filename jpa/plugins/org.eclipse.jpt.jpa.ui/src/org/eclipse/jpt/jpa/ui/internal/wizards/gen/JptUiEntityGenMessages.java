/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.wizards.gen;

import org.eclipse.osgi.util.NLS;

/**
 * Localized messages used by Dali UI.
 *
 * @version 2.0
 * @since 2.0
 */
public class JptUiEntityGenMessages {
	private static final String BUNDLE_NAME = "jpt_ui_entity_gen"; //$NON-NLS-1$
	private static final Class<?> BUNDLE_CLASS = JptUiEntityGenMessages.class;
	
	public static String GenerateEntitiesWizard_tableSelectPage_Restore_Defaults;
	public static String GenerateEntitiesWizard_generateEntities;
	public static String GenerateEntitiesWizard_doNotShowWarning;
	public static String GenerateEntitiesWizard_selectJPAProject;
	public static String GenerateEntitiesWizard_selectJPAProject_msg;
	public static String GenerateEntitiesWizard_tableSelectPage_selectTable;
	public static String GenerateEntitiesWizard_tableSelectPage_chooseEntityTable;
	public static String GenerateEntitiesWizard_tableSelectPage_updatePersistenceXml;
	public static String GenerateEntitiesWizard_tableSelectPage_tables;
	public static String GenerateEntitiesWizard_tableSelectPage_tableColumn;
	//Database connection group
	public static String connection;
	public static String addConnectionLink;
	public static String connectLink;
	public static String schemaInfo;
	public static String schema;
	public static String connectingToDatabase;
	
	//Default table gen properties
	public static String GenerateEntitiesWizard_defaultTablePage_title;
	public static String GenerateEntitiesWizard_defaultTablePage_desc;
	public static String GenerateEntitiesWizard_defaultTablePage_domainJavaClass;
	public static String GenerateEntitiesWizard_defaultTablePage_tableMapping;
	public static String GenerateEntitiesWizard_tablePanel_className;
	public static String GenerateEntitiesWizard_defaultTablePage_fetch;
	public static String GenerateEntitiesWizard_defaultTablePage_collType;
	public static String GenerateEntitiesWizard_defaultTablePage_sequence;
	public static String GenerateEntitiesWizard_defaultTablePage_sequenceNote;
	public static String GenerateEntitiesWizard_defaultTablePage_access;
	public static String GenerateEntitiesWizard_defaultTablePage_keyGen;
	public static String GenerateEntitiesWizard_defaultTablePage_genOptionalAnnotations;
	public static String GenerateEntitiesWizard_defaultTablePage_genOptionalAnnotations_desc;
	
	//Asso figure
	public static String manyToOneDesc;
	public static String oneToOneDesc;
	public static String manyToManyDesc;
	//table association wizard page
	public static String GenerateEntitiesWizard_assocPage_title;
	public static String GenerateEntitiesWizard_assocPage_desc;
	public static String GenerateEntitiesWizard_assocPage_label;
	public static String GenerateEntitiesWizard_assocPage_newAssoc;
	public static String GenerateEntitiesWizard_assocPage_delAssoc;
	public static String GenerateEntitiesWizard_assocEditor_genAssoc;
	public static String GenerateEntitiesWizard_assocEditor_entityRef;
	public static String property;
	public static String cascade;
	public static String GenerateEntitiesWizard_assocEditor_setRef;
	public static String GenerateEntitiesWizard_assocEditor_joinedWhen;
	public static String GenerateEntitiesWizard_assocEditor_tableJoin;
	public static String cardinality;
	public static String selectCascadeDlgTitle;
	//new association wizard
	public static String GenerateEntitiesWizard_newAssoc_title;
	public static String GenerateEntitiesWizard_newAssoc_tablesPage_title;
	public static String GenerateEntitiesWizard_newAssoc_tablesPage_desc;
	public static String GenerateEntitiesWizard_newAssoc_tablesPage_assocKind;
	public static String GenerateEntitiesWizard_newAssoc_tablesPage_simpleAssoc;
	public static String GenerateEntitiesWizard_newAssoc_tablesPage_m2mAssoc;
	public static String GenerateEntitiesWizard_newAssoc_tablesPage_assocTables;
	public static String GenerateEntitiesWizard_newAssoc_tablesPage_table1;
	public static String GenerateEntitiesWizard_newAssoc_tablesPage_table2;
	public static String GenerateEntitiesWizard_newAssoc_tablesPage_intermediateTable;
	public static String GenerateEntitiesWizard_newAssoc_colsPage_title;
	public static String GenerateEntitiesWizard_newAssoc_colsPage_desc;
	public static String GenerateEntitiesWizard_newAssoc_colsPage_label;
	public static String add;
	public static String remove;
	public static String GenerateEntitiesWizard_newAssoc_cardinalityPage_title;
	public static String GenerateEntitiesWizard_newAssoc_cardinalityPage_desc;
	public static String manyToOne;
	public static String oneToMany;
	public static String oneToOne;
	public static String manyToMany;
	//select table dialog
	public static String selectTableDlgTitle;
	public static String selectTableDlgDesc;
	//individual table and column gen properties
	public static String GenerateEntitiesWizard_tablesAndColumnsPage_title;
	public static String GenerateEntitiesWizard_tablesAndColumnsPage_desc;
	public static String GenerateEntitiesWizard_tablesAndColumnsPage_labelTableAndColumns;
	public static String GenerateEntitiesWizard_colPanel_genProp;
	public static String GenerateEntitiesWizard_colPanel_colMapping;
	public static String GenerateEntitiesWizard_colPanel_propName;
	public static String GenerateEntitiesWizard_colPanel_propType;
	public static String GenerateEntitiesWizard_colPanel_mapKind;
	public static String GenerateEntitiesWizard_colPanel_colUpdateable;
	public static String GenerateEntitiesWizard_colPanel_colInsertable;
	public static String GenerateEntitiesWizard_colPanel_beanProp;
	public static String GenerateEntitiesWizard_colPanel_getterScope;
	public static String GenerateEntitiesWizard_colPanel_setterScope;
	static {
		NLS.initializeMessages(BUNDLE_NAME, BUNDLE_CLASS);
	}

	private JptUiEntityGenMessages() {
		throw new UnsupportedOperationException();
	}

}

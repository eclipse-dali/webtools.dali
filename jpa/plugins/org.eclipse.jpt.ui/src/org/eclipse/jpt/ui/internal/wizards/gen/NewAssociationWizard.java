/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.ui.internal.wizards.gen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.gen.internal2.Association;
import org.eclipse.jpt.gen.internal2.ORMGenCustomizer;


public class NewAssociationWizard extends Wizard {	

	public static String ASSOCIATION_SCHEMA = "ASSOCIATION_SCHEMA";
	public static String ASSOCIATION_REFERRER_TABLE = "ASSOCIATION_REFERRER_TABLE";
	public static String ASSOCIATION_REFERENCED_TABLE = "ASSOCIATION_REFERENCED_TABLE";
	public static String ASSOCIATION_JOIN_COLUMNS1 = "ASSOCIATION_REFERRER_COLUMNS1";
	public static String ASSOCIATION_JOIN_COLUMNS2 = "ASSOCIATION_REFERRER_COLUMNS2"; //used in MTM associations only
	public static String ASSOCIATION_JOIN_TABLE = "ASSOCIATION_JOIN_TABLE"; // TreeMap<String, String>
	public static String ASSOCIATION_CADINALITY = "ASSOCIATION_CADINALITY";
	
	private JpaProject jpaProject;
	private HashMap<String, Object> associationDataModel = new HashMap<String, Object>();

	private ORMGenCustomizer customizer = null;	
	
	private AssociationTablesPage associationTablesPage;
	private JoinColumnsPage joinColumnsPage;
	private CardinalityPage cardinalityPage;
	
	public NewAssociationWizard( JpaProject jpaProject, ORMGenCustomizer customizer ) {
		super();
		this.jpaProject = jpaProject;
		this.customizer = customizer;
		this.setWindowTitle( JptUiEntityGenMessages.GenerateEntitiesWizard_newAssoc_title);
		
		this.associationDataModel.put( NewAssociationWizard.ASSOCIATION_SCHEMA, this.customizer.getSchema());
		this.associationDataModel.put( NewAssociationWizard.ASSOCIATION_JOIN_COLUMNS1, new TreeMap<String, String>());
		this.associationDataModel.put( NewAssociationWizard.ASSOCIATION_JOIN_COLUMNS2, new TreeMap<String, String>());
	}

	public void addPages() {
		super.addPages();
		this.associationTablesPage = new AssociationTablesPage( customizer );
		addPage(this.associationTablesPage);
		
		this.joinColumnsPage = new JoinColumnsPage(customizer);
		addPage(this.joinColumnsPage);
		
		this.cardinalityPage = new CardinalityPage(customizer);
		addPage(this.cardinalityPage);
	}

	public boolean performFinish() {
		return true;
	}
	
	public ORMGenCustomizer getCustomizer (){
		return customizer;
	} 
	
	public JpaProject getJpaProject(){
		return this.jpaProject;
	}

//	public Schema getDefaultSchema() {
//		return getJpaProject().getDefaultDbSchema();
//	}
//
//	private boolean projectDefaultSchemaExists() {
//		return ( this.getDefaultSchema() != null);
//	}

	public HashMap<String, Object> getDataModel(){
		return this.associationDataModel;
	}
	
	public void updateTableNames(){
		IWizardPage[] pages = this.getPages();
		for( IWizardPage page : pages){
			((NewAssociationWizardPage)page).updateWithNewTables();
		}
	}
	
	@SuppressWarnings("unchecked")
	public Association getNewAssociation(){
		String referrerTableName =getReferrerTableName(); 
		String referencedTableName = getReferencedTableName();
		List<String> referrerColNames = new ArrayList<String>();
		List<String> referencedColNames = new ArrayList<String>();
		
		String cardinality = (String)associationDataModel.get( NewAssociationWizard.ASSOCIATION_CADINALITY );
		if( cardinality.equals(Association.MANY_TO_MANY) ){
			return createManyToManyAssociation();
		}
		
		Object value = associationDataModel.get( NewAssociationWizard.ASSOCIATION_JOIN_COLUMNS1);
		if(value!=null){
			TreeMap<String, String> joinColumns = (TreeMap<String, String>)value;		
			for( String pk : joinColumns.keySet()){
				referrerColNames.add(pk);
				referencedColNames.add( joinColumns.get(pk));
			}
		}

		/*if one-to-many then convert it to many-to-one to be consistent 
		 * with the associations computed from the db foreign keys.
		 * Don't see at this point how one-to-many would 
		 * affect the generation.*/
		if( cardinality.equals(Association.ONE_TO_MANY) ){
			cardinality = Association.MANY_TO_ONE;
			
			String temp1 = referrerTableName;
			referrerTableName = referencedTableName;
			referencedTableName = temp1;
			
			List<String> temp2 = referrerColNames;
			referrerColNames = referencedColNames;
			referencedColNames = temp2;
		}
		
		Association association = null;
		association = new Association(this.customizer, referrerTableName, referrerColNames	, referencedTableName, referencedColNames);
		association.setCardinality( cardinality );
		association.setCustom(true);
		return association;
	}
	
	@SuppressWarnings("unchecked")
	private Association createManyToManyAssociation() {
		String referrerTableName =getReferrerTableName(); 
		String joinTable = getJoinTableName();
		String referencedTableName = getReferencedTableName();

		List<String> referrerColNames = new ArrayList<String>();
		List<String> referrerJoinColNames = new ArrayList<String>();
		Object value = associationDataModel.get( NewAssociationWizard.ASSOCIATION_JOIN_COLUMNS1);
		if(value!=null){
			TreeMap<String, String> joinColumns = (TreeMap<String, String>)value;		
			for( String pk : joinColumns.keySet()){
				referrerColNames.add(pk);
				referrerJoinColNames.add( joinColumns.get(pk));
			}
		}
		
		value = associationDataModel.get( NewAssociationWizard.ASSOCIATION_JOIN_COLUMNS2);
		List<String> referencedColNames = new ArrayList<String>();
		List<String> referencedJoinColNames = new ArrayList<String>();
		if(value!=null){
			TreeMap<String, String> joinColumns = (TreeMap<String, String>)value;		
			for( String pk : joinColumns.keySet()){
				referencedJoinColNames.add(pk);
				referencedColNames.add( joinColumns.get(pk));
			}
		}

		
		Association association = null;
		association = new Association(this.customizer, referrerTableName, referrerColNames, 
				referencedTableName, referencedColNames, joinTable, referrerJoinColNames, referencedJoinColNames);
		return association;
	}

	String getReferrerTableName(){
		return (String)associationDataModel.get(NewAssociationWizard.ASSOCIATION_REFERRER_TABLE);
	}

	String getReferencedTableName(){
		return (String)associationDataModel.get(NewAssociationWizard.ASSOCIATION_REFERENCED_TABLE);
	}
	
	String getJoinTableName(){
		return (String)associationDataModel.get(NewAssociationWizard.ASSOCIATION_JOIN_TABLE );
	}
	
	String getCardinality(){
		return (String)associationDataModel.get(NewAssociationWizard.ASSOCIATION_CADINALITY );
	}
	
}
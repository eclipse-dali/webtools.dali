/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.gen.internal;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jpt.db.Column;
import org.eclipse.jpt.db.ForeignKey;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.gen.internal.util.DTPUtil;
import org.eclipse.jpt.gen.internal.util.FileUtil;
import org.eclipse.jpt.gen.internal.util.ForeignKeyInfo;
import org.eclipse.jpt.gen.internal.util.StringUtil;

/**
 * Contains the information used to customize the database schema to ORM entity 
 * generation.
 * 
 * <p>The customization settings are mainly exposed in the form of 
 * properties. There are no assumptions in this class about the meaning of the 
 * property names. Properties can be associated to specific tables and table 
 * columns, or globally for any table and/or column.
 * 
 * <p>Subclass can implement the sets of abstract methods to provide ORM vendor
 * specific properties.
 * 
 */
public abstract class ORMGenCustomizer implements java.io.Serializable
{
	private final static long serialVersionUID = 1;

	/**
	 * A value passed for the table name argument to get/setProperty 
	 * indicating that the value applies to any table.
	 */
	public static final String ANY_TABLE = "__anyTable__";
	public static final String GENERATE_DDL_ANNOTATION = "generateDDLAnnotations";
	/*the string used in the property name in mProps to indicate 
	 * a null table value.*/
	private static final String NULL_TABLE = "";
	/*the string used in the property name in mProps to indicate 
	 * a null column value.*/
	private static final String NULL_COLUMN = "";
	
	/*This version number is written in the header of the customization stream
	 * and read at de-serialization time, if it is different then the file is invalidated.
	 */
	private static final int FILE_VERSION = 2;
	
	private static final String UPDATE_CONFIG_FILE = "updateConfigFile";

	private transient Schema mSchema;
	private transient File mFile;
	
	private List<String> mTableNames;
	/*key: table name, value: ORMGenTable object.*/
	private transient Map<String , ORMGenTable> mTables;
	/*the <code>Association</code> objects sorted by their "from" 
	 * table name. Includes all association derived from foreign keys 
	 * in user selected tables. Since some of the foreign keys may point to table
	 * user does not select, this list may be different from  mValidAssociations
	 */
	private List<Association> mAssociations;
	/*
	 * List of valid associations within the user selected tables  
	 */
	private transient List<Association> mValidAssociations;
	private transient boolean mInvalidForeignAssociations;
	
	/*the property name is in the form $tableName.$columnName.$propertyName.
	 * Where tableName could be NULL_TABLE or ANY_TABLE
	 * and columnName could be NULL_COLUMN*/
	private Map<String, String> mProps = new java.util.HashMap<String, String>();

	private transient DatabaseAnnotationNameBuilder databaseAnnotationNameBuilder = DatabaseAnnotationNameBuilder.Default.INSTANCE;
	
	//-----------------------------------------
	//---- abstract methods
	//-----------------------------------------
	/**
	 * Returns all the primary key generator schemes.
	 * This can return any strings as far as the Velocity template 
	 * processor understand them.
	 */
	public abstract List<String> getAllIdGenerators();
	/**
	 * Returns the string representing the developer-assigned id generator.
	 * This can return any strings as far as the Velocity template 
	 * processor understand them.
	 */
	public abstract String getNoIdGenerator();
	/**
	 * Returns the string representing the identity id generator.
	 * This can return any strings as far as the Velocity template 
	 * processor understand them.
	 */
	public abstract String getIdentityIdGenerator();
	/**
	 * Returns the strings representing the sequence generators.
	 * This can return any strings as far as the Velocity template 
	 * processor understand them.
	 */
	public abstract Set<String> getSequenceIdGenerators();
	/**
	 * Returns a property type from the given database column.
	 * This can return any strings as far as the Velocity template 
	 * processor understand them.
	 */
	public abstract String getPropertyTypeFromColumn(Column column) ;
	/**
	 * Returns all the strings representing property types.
	 * This can return any strings as far as the Velocity template 
	 * processor understand them.
	 */
	public abstract String[] getAllPropertyTypes();
	/**
	 * Returns all the strings representing property mapping kinds.
	 * This can return any strings as far as the Velocity template 
	 * processor understand them.
	 */
	public abstract String[] getAllMappingKinds();
	/**
	 * Returns the basic (default) property mapping kind.
	 * This can return any strings as far as the Velocity template 
	 * processor understand them.
	 */
	public abstract String getBasicMappingKind();
	/**
	 * Returns the id (primary key) property mapping kind.
	 * This can return any strings as far as the Velocity template 
	 * processor understand them.
	 */
	public abstract String getIdMappingKind();
	/**
	 * Interacts with the user to edit the cascade of the given 
	 * role.
	 * This method should also call <code>AssociationRole.setCascade</code>.
	 * 
	 * @return false if the user interaction is cancelled.
	 */
	public abstract boolean editCascade(AssociationRole role);
	
	//-----------------------------------------
	//-----------------------------------------

	/**
	 * @param file The file that contains the customization settings.
	 * The file is created if necessary when the <code>save</code> 
	 * method is called.
	 */
	public void init( File file, Schema schema) {
		this.mSchema = schema;
		mFile = file;
		
		if (!file.exists()) {
			setProperty(ORMGenTable.DEFAULT_FETCH, ORMGenTable.DEFAULT_FETCH, ORMGenCustomizer.ANY_TABLE, null);
			return;
		}
		InputStream istream = null;
		ObjectInputStream ois = null;
		try 
		{
			//read it in a file first to speedup deserialization
			byte[] bytes = FileUtil.readFile(file);
			istream = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(istream);
			
			FileHeader header = (FileHeader)ois.readObject();
			if (header.mVersion == FILE_VERSION) {
				ORMGenCustomizer customizer = (ORMGenCustomizer)ois.readObject();
				restore(customizer);
			}
		} catch (Exception ex) {
			System.out.println("***ORMGenCustomizer.load failed "+file+": " + ex);				
		}
		finally 
		{
			if (ois != null)
			{
				try 
				{
					ois.close();
				} catch (IOException e) {
				}
			}
			
			if (istream != null)
			{
				try 
				{
					istream.close();
				} catch (IOException e) {
				}
			}
		}
	}
	
	public File getFile(){
		return this.mFile;
	}
	
	public void setSchema(Schema schema){
		this.mSchema = schema;
	}

	public Schema getSchema(){
		return mSchema;
	}
	
	/**
	 * Empty constructor needed by the deserialization.
	 */
	public ORMGenCustomizer() {
		super();
	}

	/**
	 * Saves the customization file.
	 * The file is created if necessary.
	 */
	public void save() throws IOException {
		//System.out.println("---ORMGenCustomizer.save: " + mFile);
		if (!mFile.exists() && !mFile.createNewFile()) {
			return;
		}
		java.io.FileOutputStream fos = null;
		java.io.ObjectOutputStream oos = null;
		boolean deleteIt = true;
		try {
			fos = new java.io.FileOutputStream(mFile);
			oos = new java.io.ObjectOutputStream(fos);
			FileHeader header = new FileHeader();
			oos.writeObject(header);
			oos.writeObject(this);
			deleteIt = false;
		} catch (Exception ex) {
			//deleteIt is true, so the cache is not saved.
			CoreException ce = new CoreException(new Status(IStatus.ERROR, JptGenPlugin.PLUGIN_ID,
					"Unable to save the ORMGenCustomizer file: "+mFile,ex));
			JptGenPlugin.logException( ce );
		} finally {
			try {
				if (oos!=null) oos.close();
				if (fos!=null) fos.close();
				if (deleteIt) {
					mFile.delete();
				}
			} catch (java.io.IOException ex2) {}	
		}
	}
	
	public DatabaseAnnotationNameBuilder getDatabaseAnnotationNameBuilder() {
		return this.databaseAnnotationNameBuilder;
	}
	public void setDatabaseAnnotationNameBuilder(DatabaseAnnotationNameBuilder databaseAnnotationNameBuilder) {
		if (databaseAnnotationNameBuilder == null) {
			throw new NullPointerException("database annotation name builder is required");  //$NON-NLS-1$
		}
		this.databaseAnnotationNameBuilder = databaseAnnotationNameBuilder;
	}	

	/**
	 * Returns {@link #GENERATE_DDL_ANNOTATION}  indicating whether
	 * the optional DDL parameters like length, nullable, unqiue, etc should be generated 
	 * in @Column annotation.
	 * defaults to false.
	 */
	public boolean isGenerateDDLAnnotations() {
		return "true".equals(getProperty(GENERATE_DDL_ANNOTATION, ANY_TABLE, null)); //defaults to false
	}

	/**
	 * Returns a property value.
	 */
	public String getProperty(String propertyName, String tableName, String colName) {
		String key = getPropKey(propertyName, tableName, colName);
		String value = mProps.get(key);
		/*if the key does not exist and it is a table property then 
		 * get the default table property.*/
		if (value == null && tableName != null && colName == null && !tableName.equals(ANY_TABLE)) {
			value = getProperty(propertyName, ANY_TABLE, colName);
		}
		return value;
	}
	/**
	 * Changes a property value.
	 * 
	 * @param value The new value, could be null.
	 */
	public void setProperty(String propertyName, String value, String tableName, String colName) {
		String key = getPropKey(propertyName, tableName, colName);
		if (value != null) {
			mProps.put(key, value);
		} else {
			mProps.remove(key);
		}
	}
	/**
	 * Same as {@link #getProperty(String, String, String)} but 
	 * converts the value to boolean.
	 */
	public boolean getBooleanProperty(String propertyName, String tableName, String colName) {
		String value = getProperty(propertyName, tableName, colName);
		return "true".equals(value);
	}
	/**
	 * Changes a table boolean property value.
	 */
	public void setBooleanProperty(String propertyName, boolean value, String tableName, String colName) {
		setProperty(propertyName, value ? "true" : "false", tableName, colName);
	}
	/**
	 * Returns the names of the tables to generate.
	 */
	@SuppressWarnings("unchecked")
	public List<String> getTableNames() {
		return mTableNames != null ? mTableNames : java.util.Collections.EMPTY_LIST;
	}
	
	/**
	 * Returns the fetch type annotation member value, or empty string 
	 * if none.
	 * Empty string is returned instead of null because Velocity does not like null 
	 * when used in #set.
	 */
	public String genFetch(ORMGenTable table) {
		return "";
	}	
	/**
	 * Called when the table user selection is changed in the 
	 * generation wizard.
	 */
	public void setTableNames(List<String> tableNames) {
		mTableNames = tableNames;
		mTables = null;
		mValidAssociations = null; //recompute
		mInvalidForeignAssociations = true; //make sure foreign associations from newly added tables are computed.
	}
	/**
	 * Returns the table names to be generated.
	 * This might be different from <code>getTableNames</code> if there 
	 * are many-to-many join tables and are not contributing 
	 * in any other associations.
	 */
	public List<String> getGenTableNames()  {
		List<String> names = getTableNames();
		List<String> result = new java.util.ArrayList<String>(names.size());
		
		/*filter out join tables*/
		List<Association> associations = getAssociations();
		for (Iterator<String> tableNamesIter = names.iterator(); tableNamesIter.hasNext(); ) {
			String tableName = tableNamesIter.next();
			boolean isValid = true;
			
			for (Iterator<Association> assocIter = associations.iterator(); assocIter.hasNext(); ) {
				Association association = assocIter.next();
				if (!association.isGenerated()) {
					continue;
				}
				if (tableName.equals(association.getReferrerTableName())
						|| tableName.equals(association.getReferencedTableName())) {
					isValid = true;
					break;
				}
				if (tableName.equals(association.getJoinTableName())) {
					isValid = false;
				}
			}
			if (isValid) {
				result.add(tableName);
			}
		}
		return result;
	}
	/**
	 * Returns an <code>ORMGenTable</code> object given its name, or 
	 * null if none.
	 */
	public ORMGenTable getTable(String tableName)  {
		if (mTables == null) {
			mTables = new java.util.HashMap<String, ORMGenTable>(mTableNames.size());
		}
		
		if(mTableNames!=null && mSchema!=null){
			for (Iterator<String> iter = mTableNames.iterator(); iter.hasNext(); ) {
				String name = iter.next();
				Table dbTable = mSchema.getTableNamed( name );
				if (dbTable != null) {
					mTables.put(name, createGenTable(dbTable));
				}
			}
		}
		return mTables.get(tableName);
	}
	/**
	 * Returns the <code>Association</code> objects sorted by their "from" 
	 * table name.
	 */
	public List<Association> getAssociations(){
		return getAssociations(true/*validOnly*/);
	}
	/**
	 * Adds the given association.
	 */
	public void addAssociation(Association association)  {
		getAssociations(false/*validOnly*/).add(association);
		if (mValidAssociations != null) {
			mValidAssociations.add(association);
		}
		
	}
	/**
	 * Deletes the given association.
	 */
	public void deleteAssociation(Association association)  {
		boolean removed = getAssociations(false/*validOnly*/).remove(association);
		assert(removed);
		
		if (mValidAssociations != null) {
			removed = mValidAssociations.remove(association);
			assert(removed);
		}
	}
	/**
	 * Returns true if an association similar to the given association 
	 * already exists.
	 * This is decided based only on the association tables and columns.
	 */
	public boolean similarAssociationExists(Association association) {
		try {
			for (Iterator<Association> iter = getAssociations(false/*validOnly*/).iterator(); iter.hasNext(); ) {
				Association association2 = iter.next();
				if (!association.getReferrerTableName().equals(association2.getReferrerTableName())
						|| !association.getReferencedTableName().equals(association2.getReferencedTableName())
						|| !StringUtil.equalObjects(association.getJoinTableName(), association2.getJoinTableName())
						|| !association.getReferrerColumnNames().equals(association2.getReferrerColumnNames())
						|| !association.getReferencedColumnNames().equals(association2.getReferencedColumnNames())
						) {
					continue;
				}					
				/*the 2 association have the same referrer, referenced and join table*/
				if (association.getJoinTableName() == null) {
					return true;
				}
				if (association.getReferrerJoinColumnNames().equals(association2.getReferrerJoinColumnNames())
						&& association.getReferencedJoinColumnNames().equals(association2.getReferencedJoinColumnNames())) {
					return true;
				}
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}
	/**
	 * Creates the <code>ORMGenTable</code> instance. 
	 */
	public ORMGenTable createGenTable(Table dbTable) {
		return new ORMGenTable(dbTable, this);
	}
	/**
	 * Creates the <code>ORMGenColumn</code> instance.
	 */
	protected ORMGenColumn createGenColumn(Column dbCol) {
		return new ORMGenColumn(dbCol, this);
	}
	/**
	 * Returns true of the underlying persistence specs require the "many" side 
	 * of an association to be the owner (like EJB3).
	 */
	protected boolean manySideIsAssociationOwner() {
		return false;
	}
	public boolean isUpdateConfigFile() {
		return !"false".equals(getProperty(UPDATE_CONFIG_FILE, null, null)); //defaults to true
	}
	public void setUpdateConfigFile(boolean value) {
		if (value) { //default is true
			setProperty(UPDATE_CONFIG_FILE, null, null, null); //remove it
		} else {
			setBooleanProperty(UPDATE_CONFIG_FILE, value, null, null);
		}
	}

	//-----------------------------------------
	//---- Velocity templates methods
	//-----------------------------------------
	/**
	 * Returns a getter method name given a property name.
	 */
	public String propertyGetter(String propertyName) {
		return "get"+StringUtil.initUpper(propertyName);
	}
	/**
	 * Returns a setter method name given a property name.
	 */
	public String propertySetter(String propertyName) {
		return "set"+StringUtil.initUpper(propertyName);
	}
	public String quote(String s) {
		return StringUtil.quote(s, '"');
	}
	public String quote(boolean b) {
		return quote(String.valueOf(b));
	}
	public String quote(int i) {
		return quote(String.valueOf(i));
	}
	/**
	 * Appends an annotation member name and value to an existing annotation.
	 * 
	 * @param s The annotation members string.
	 *
	 * @param memberValue The member value, if null or empty strings then 
	 * nothing is appened.
	 * 
	 * @param whether to double quote the member value.
	 */
	public String appendAnnotation(String s, String memberName, String memberValue, boolean quote) {
		if (memberValue == null || memberValue.length() == 0) {
			return s;
		}
		StringBuffer buffer = new StringBuffer(s);
		if (buffer.length() != 0) {
			buffer.append(", ");
		}
		buffer.append(memberName);
		buffer.append('=');
		if (quote) {
			buffer.append('"');
		}
		buffer.append(memberValue);
		if (quote) {
			buffer.append('"');
		}
		return buffer.toString();
	}
	public boolean isJDK1_5() {
		return true;
	}
	
	//-----------------------------------------
	//---- private methods
	//-----------------------------------------
	/**
	 * Restores the customization settings from the given 
	 * (persisted) customizer.
	 */
	private void restore(ORMGenCustomizer customizer)  {
		mTableNames = customizer.mTableNames;
		mAssociations = customizer.mAssociations;
		mProps = customizer.mProps;
		if( mSchema == null )
			return;
		
		/*remove invalid table names*/
		for (int i = mTableNames.size()-1; i >= 0; --i) {
			String tableName = mTableNames.get(i);
			if (mSchema.getTableNamed( tableName) == null) {
				mTableNames.remove(i);
			}
		}
		if( mAssociations!=null ){
			/*restore the associations*/
			for (Iterator<Association> iter = mAssociations.iterator(); iter.hasNext(); ) {
				Association association = iter.next();
				association.restore(this);
			}
			/*add the foreign keys associations just in case the tables changed since 
			 * the last time the state was persisted. Pass checkExisting true so that the 
			 * associations restored above are not overwritten.*/
			addForeignKeyAssociations(true/*checkExisting*/);
			// sort on restore
			sortAssociations( mAssociations );
		}
	}
	/**
	 * Returns the key in mProps corresponding to the specified 
	 * propertyName, table and column.
	 */
	private String getPropKey(String propertyName, String tableName, String colName) {
		if (tableName == null) {
			tableName = NULL_TABLE;
		}
		if (colName == null) {
			colName = NULL_COLUMN;
		}
		return tableName + '.' + colName + '.' + propertyName;
	}
	/**
	 * Returns the associations that are valid for the 
	 * current tables.
	 */
	private List<Association> getAssociations(boolean validOnly){
		if (mAssociations == null) {
			mAssociations = new java.util.ArrayList<Association>();
			
			addForeignKeyAssociations(false/*checkExisting*/);
		} else if (mInvalidForeignAssociations) {
			mInvalidForeignAssociations = false;
			
			addForeignKeyAssociations(true/*checkExisting*/);
		}
		List<Association> associations;
		if (validOnly) {
			if (mValidAssociations == null) {
				/*filter out the invalid associations*/
				mValidAssociations = new ArrayList<Association>(mAssociations.size());
				for (int i = 0, n = mAssociations.size(); i < n; ++i) {
					Association association = mAssociations.get(i);
					if (association.isValid()) {
						mValidAssociations.add(association);
					}
				}
			}
			associations = mValidAssociations;
		} else {
			associations = mAssociations;
		}
		return associations;
	}
	private void addForeignKeyAssociations(boolean checkExisting) {
		List<String> tableNames = getTableNames();
		for (Iterator<String> iter = tableNames.iterator(); iter.hasNext(); ) {
			ORMGenTable table = getTable(iter.next());
			addForeignKeyAssociations(table, checkExisting);
		}
	}
	private void addForeignKeyAssociations(ORMGenTable table, boolean checkExisting) {
		if(table==null)
			return;
		
		
		List<ForeignKeyInfo> fKeys = null;
		
		try{
			fKeys = DTPUtil.getForeignKeys(table.getDbTable());
		}catch(Exception ise){
			//workaround Dali bug for now
			return;
		}
		
		if( fKeys.size()==0 )
			return;
		
		List<Association> addedAssociations = new java.util.ArrayList<Association>();
		for (Iterator<ForeignKeyInfo> iter = fKeys.iterator(); iter.hasNext(); ) {
			ForeignKeyInfo fki = iter.next();
			ORMGenTable referencedTable = getTable(fki.getReferencedTableName());
			if (referencedTable == null) {
				continue;
			}
			Association association = new Association(this, table.getName(), fki.getReferrerColumnNames()
						, referencedTable.getName(), fki.getReferencedColumnNames());
			association.computeCardinality();
			//Defer the check of similarAssociationExists after computeManyToMany()
			//otherwise the MTM association will not computed correctly in some cases.
			//if (checkExisting && similarAssociationExists(association)) {
			//	continue;
			//}
			addedAssociations.add(association);
		}
		
		Association m2m = computeManyToMany(table, addedAssociations);
		if (m2m != null) {
			/*do not generate the 2 many-to-one*/
			addedAssociations.clear();
			addedAssociations.add(0, m2m);
		}
		//remove the association if already existing
		Iterator<Association> it =  addedAssociations.iterator(); 
		while( it.hasNext() ){
			Association newAssociation = it.next();
			for( Association association : mAssociations ){
				if( newAssociation.equals( association )){
					it.remove();
				}
			}
		}
		mAssociations.addAll(addedAssociations);
	}
	private Association computeManyToMany(ORMGenTable table, List<Association> addedAssociations) {
		/** many-to-many associations if:
		 * - addedAssociations contains 2 many-to-one associations
		 * - tables t1 and t2 does NOT have to be different( for self-MTM-self situation)
		 * - <code>table</code> contains only the foreign key columns.
		 * 
		 * Note: following restrictions have been removed:
		 * -table has only two columns
		 * -t1 and t2 must be different
		 * -the primary key of <code>table</code> is the concatenation of its foreign 
		 * 	keys to t1 and t2.*/
		
		if (addedAssociations.size() != 2) {
			return null;
		}
		
		//MTM table should have two MTO relations to orginal tables
		Association assoc1 = addedAssociations.get(0);
		Association assoc2 = addedAssociations.get(1);
		if (assoc1.getCardinality() != Association.MANY_TO_ONE
				|| assoc2.getCardinality() != Association.MANY_TO_ONE) {
			return null;
		}

		//MTM table should only include foreign key columns
		for( ORMGenColumn col : table.getColumns()){
			if( !col.isForeignKey())
				return null;
		}
		

		ORMGenTable t1 = assoc1.getReferencedTable();
		ORMGenTable t2 = assoc2.getReferencedTable();

		if( t1.getName().equals(table.getName()) || t2.getName().equals(table.getName()) ) {
			return null;
		}

		//Make a guess which table is the owning side of the MTM relation
		//See https://bugs.eclipse.org/bugs/show_bug.cgi?id=268445
		//Logic borrowed from DTPTableWrapper.getJoinTableOwningForeignKey()
		if( !table.getName().equals(t1.getName() + "_" + t2.getName() ) ) {
			//swap t1 and t2  
			ORMGenTable t3 = t1;
			t1=t2;
			t2=t3;
			//swap assoc1 and assoc2
			Association assoc3=assoc1;
			assoc1=assoc2;
			assoc2=assoc3;
		}
		
//Commented out because the assumption is too restrictive: 
//this check will prevent generating MTM mapping table not having 
//primary key defined 		
//		List pkNames = DTPUtil.getPrimaryKeyColumnNames(table.getDbTable());
//		if (pkNames.size() != table.getColumnNames().size()) {
//			return null;
//		}
//		List fkNames = new java.util.ArrayList(assoc1.getReferrerColumnNames()); //clone because we modify by addAll below
//		fkNames.addAll(assoc2.getReferrerColumnNames());
//		if (!CollectionUtil.equalsIgnoreOrder(pkNames, fkNames)) {
//			return null;
//		}
		Association m2m = new Association(this, t1.getName()/*referrerTableName*/, assoc1.getReferencedColumnNames()/*referrerColNames*/
				, t2.getName()/*referencedTableName*/, assoc2.getReferencedColumnNames()/*referencedColNames*/
				, table.getName(), assoc1.getReferrerColumnNames()/*referrerJoinColNames*/, assoc2.getReferrerColumnNames()/*referencedJoinColNames*/);
		m2m.setCustom(false);
		return m2m;
	}

	//---------------------------------------------------
	//---- FileHeader class -----------------------------
	//---------------------------------------------------
	/**
	 * The header of the customization file.
	 */
	private static class FileHeader implements java.io.Serializable
	{
		private static final long serialVersionUID = 1L;
		/**
		 * Should be argument-less because it is used in 
		 * the de-serialization process.
		 */
		public FileHeader() {
			mVersion = FILE_VERSION;
		}
		int mVersion;
	}
	
	private void sortAssociations( List< Association > list ) {
	   Collections.sort( list, new Comparator< Association >() {
	      public int compare( Association lhs, Association rhs ) {
	         // sort by referrer table name first...
	         int test = lhs.getReferrerTableName().compareTo( rhs.getReferrerTableName() );
            if( test != 0 )
               return test;
            // then by referenced table name...
            test = lhs.getReferencedTableName().compareTo( rhs.getReferencedTableName() );
            if( test != 0 )
               return test;
            // if referrer and referenced tables are the same, they should
            // appear next to each other
            return 0;
	      }
	   } );
	}
}

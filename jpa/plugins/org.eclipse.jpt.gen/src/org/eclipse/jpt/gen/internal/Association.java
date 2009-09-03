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

import java.util.List;

import org.eclipse.jpt.db.ForeignKey;
import org.eclipse.jpt.gen.internal.util.StringUtil;

/**
 * Represents an ORM association.
 * There are two types of associations:
 * <ul><li>simple association: An association between two database tables.
 * The <em>referrer</code> table is the one containing the foreign key
 * , the <em>referenced</code> table is the other party.<br>
 * 
 * <li>many to many association: An association between two tables joined by 
 * a <em>join table</em>.
 * In the example AUTHOR, BOOK, AUTHOR_BOOK, The referrer and referenced are 
 * AUTHOR and  BOOK, and the join table is AUTHOR_BOOK.
 * </ul>
 * 
 */
public class Association implements java.io.Serializable
{
	private final static long serialVersionUID = 2;

	public static final String MANY_TO_ONE = "many-to-one";
	public static final String MANY_TO_MANY = "many-to-many";
	public static final String ONE_TO_ONE = "one-to-one";
	public static final String ONE_TO_MANY = "one-to-many";
	
	public static final String BI_DI = "bi-di";
	public static final String NORMAL_DI = "normal-di"; //referrer->referenced
	public static final String OPPOSITE_DI = "opposite-di"; //referenced->referrer
	
	private transient ORMGenCustomizer mCustomizer;
	private String mReferrerTableName;
	private String mReferencedTableName;
	private String mJoinTableName;

	private List<String> mReferrerColNames; /*String objects*/
	private List<String> mReferencedColNames; /*String objects*/
	private List<String> mReferrerJoinColNames; /*String objects*/
	private List<String> mReferencedJoinColNames; /*String objects*/

	private transient List<ORMGenColumn> mReferrerCols; /*ORMGenColumn objects*/
	private transient List<ORMGenColumn> mReferencedCols; /*ORMGenColumn objects*/
	private transient List<ORMGenColumn> mReferrerJoinCols; /*ORMGenColumn objects*/
	private transient List<ORMGenColumn> mReferencedJoinCols; /*ORMGenColumn objects*/
	
	private String mCardinality;
	private String mDirectionality;
	private byte mFlags = GENERATED;
	
	private AssociationRole mReferrerRole;
	private AssociationRole mReferencedRole;

	private transient ForeignKey mForeignKey;	
	
	/*constants for mFlags*/
	/*whether the association should be generated*/
	private static final byte GENERATED = 1 << 0;
	/*whether the association is custom (i.e is not auto computed from foreign keys relationships).*/
	private static final byte CUSTOM = 1 << 1;
	
	/**
	 * The simple association constructor.
	 * The 2 tables are joined when the values of each column in
	 * referrerColNames match its corresponding column in referencedColNames.
	 * 
	 * @param referrerTableName The "foreign key" table.
	 * @param referrerColNames The column names in the referrer table.
	 * @param referencedTableName The "primary key" table.
	 * @param referencedColNames The column names in the referenced table.
	 */
	public Association(ORMGenCustomizer customizer, String referrerTableName, List<String> referrerColNames
			, String referencedTableName, List<String> referencedColNames)  {
		super();
		
		mCustomizer = customizer;
		mReferrerTableName = referrerTableName;
		mReferencedTableName = referencedTableName;
		mReferrerColNames = referrerColNames;
		mReferencedColNames = referencedColNames;
		
		mCardinality = MANY_TO_ONE;
		mDirectionality = BI_DI;
		
		setCustom(true);
	}
	/**
	 * The many to many constructor.
	 * The 2 tables are joined when the values of each column in
	 * referrerColNames match its corresponding column in referrerJoinColNames
	 * , and each column in referencedColNames match its corresponding column in referencedJoinColNames.
	 *
	 */
	public Association(ORMGenCustomizer customizer, String referrerTableName, List<String> referrerColNames
			, String referencedTableName, List<String> referencedColNames
			, String joinTableName, List<String> referrerJoinColNames, List<String> referencedJoinColNames) {
		super();
		
		mCustomizer = customizer;
		mReferrerTableName = referrerTableName;
		mReferencedTableName = referencedTableName;
		mReferrerColNames = referrerColNames;
		mReferencedColNames = referencedColNames;
		mJoinTableName = joinTableName;
		mReferrerJoinColNames = referrerJoinColNames;
		mReferencedJoinColNames = referencedJoinColNames;
		
		mCardinality = MANY_TO_MANY;	
		mDirectionality = BI_DI;
		
		setCustom(true);
	}
	/**
	 * Empty constructor needed by the deserialization (should not be used otherwise).
	 */
	public Association() {
	}
	/**
	 * Computes the cardinality basedon the forign key definitions.
	 */
	public void computeCardinality()  {
		/*by default the association is many-to-one unless the foreign key 
		 * is also the primary key, in which case it is a one-to-one.*/
		mCardinality = MANY_TO_ONE;
		
		List<ORMGenColumn> referrerCols = getReferrerColumns();
		List<ORMGenColumn> pkCols = getReferrerTable().getPrimaryKeyColumns();
		if (pkCols.size() == referrerCols.size()) {
			boolean isFkPk = true;
			for (int i = 0, n = pkCols.size(); i < n; ++i) {
				if (!((ORMGenColumn)pkCols.get(i)).getName().equals(((ORMGenColumn)referrerCols.get(i)).getName())) {
					isFkPk = false;
					break;
				}
			}
			if (isFkPk) {
				mCardinality = ONE_TO_ONE;
			}
		}
		
		setCustom(false);
	}
	/**
	 * Called after the asscociations are deserialized to attach 
	 * the customizer object.
	 */
	protected void restore(ORMGenCustomizer customizer) {
		mCustomizer = customizer;
		
		if (mReferrerRole != null) {
			mReferrerRole.restore(this);
		}
		if (mReferencedRole != null) {
			mReferencedRole.restore(this);
		}
	}
	public ORMGenTable getReferrerTable()  {
		return mCustomizer.getTable(mReferrerTableName);
	}
	public String getReferrerTableName() {
		return mReferrerTableName;
	}
	public ORMGenTable getReferencedTable()  {
		return mCustomizer.getTable(mReferencedTableName);
	}
	public String getReferencedTableName() {
		return mReferencedTableName;
	}
	public ORMGenTable getJoinTable()  {
		return mCustomizer.getTable(mJoinTableName);
	}
	public String getJoinTableName() {
		return mJoinTableName;
	}
	/**
	 * Returns the <code>ORMGenColumn</code> objects for the referrer
	 * columns.
	 */
	public List<ORMGenColumn> getReferrerColumns() {
		if (mReferrerCols == null) {
			ORMGenTable referrerTable = getReferrerTable();
			mReferrerCols = referrerTable.getColumnsByNames(mReferrerColNames);
		}
		return mReferrerCols;
	}
	public List<String> getReferrerColumnNames() {
		return mReferrerColNames;
	}
	/**
	 * Returns the <code>ORMGenColumn</code> objects for the referenced
	 * columns.
	 */
	public List<ORMGenColumn> getReferencedColumns() {
		if (mReferencedCols == null) {
			mReferencedCols = getReferencedTable().getColumnsByNames(mReferencedColNames);
		}
		return mReferencedCols;
	}
	public List<String> getReferencedColumnNames() {
		return mReferencedColNames;
	}
	public List<ORMGenColumn> getReferrerJoinColumns() {
		if (mReferrerJoinCols == null) {
			mReferrerJoinCols = getJoinTable().getColumnsByNames(mReferrerJoinColNames);
		}
		return mReferrerJoinCols;
	}
	public List<String> getReferrerJoinColumnNames() {
		return mReferrerJoinColNames;
	}
	public List<ORMGenColumn> getReferencedJoinColumns()  {
		if (mReferencedJoinCols == null) {
			mReferencedJoinCols = getJoinTable().getColumnsByNames(mReferencedJoinColNames);
		}
		return mReferencedJoinCols;
	}
	public List<String> getReferencedJoinColumnNames() {
		return mReferencedJoinColNames;
	}
	/**
	 * Returns the association cardinality, one of {@link #MANY_TO_ONE}|{@link #MANY_TO_MANY}
	 * |{@link #ONE_TO_ONE}|{@link #ONE_TO_MANY}
	 */
	public String getCardinality() {
		return mCardinality;
	}
	public void setCardinality(String cardinality) {
		assert(cardinality.equals(MANY_TO_ONE) || cardinality.equals(MANY_TO_MANY) || cardinality.equals(ONE_TO_ONE) || cardinality.equals(ONE_TO_MANY));
		mCardinality = cardinality;
	}
	/**
	 * Returns the association directionality, one of {@link #BI_DI}|{@link #NORMAL_DI}
	 * |{@link #OPPOSITE_DI}
	 */
	public String getDirectionality() {
		return mDirectionality;
	}
	public void setDirectionality(String dir) {
		assert(dir.equals(BI_DI) || dir.equals(NORMAL_DI) || dir.equals(OPPOSITE_DI));
		if (!dir.equals(mDirectionality)) {
			mDirectionality = dir;
			
			if (dir.equals(NORMAL_DI)) {
				mReferencedRole = null;
			} else if (dir.equals(OPPOSITE_DI)) {
				mReferrerRole = null;
			}
		}
	}
	
	/**
	 * Tests whether this association is bidirectional.
	 * This is a shortcut for <code>getDirectionality().equals(BI_DI)</code>.
	 */
	public boolean isBidirectional() {
		return mDirectionality.equals(BI_DI);
	}
	/**
	 * Returns true of this association should be generated. 
	 */
	public boolean isGenerated() {
		return (mFlags & GENERATED) != 0;
	}
	public void setGenerated(boolean generated) {
		if (generated != isGenerated()) {
			if (generated) {
				mFlags |= GENERATED;
			} else {
				mFlags &= ~GENERATED;
			}
			mReferrerRole = mReferencedRole = null;
		}
	}
	/**
	 * Returns true of this association is custom (i.e is not auto computed from foreign keys relationships). 
	 */
	public boolean isCustom() {
		return (mFlags & CUSTOM) != 0;
	}
	public void setCustom(boolean custom) {
		if (custom) {
			mFlags |= CUSTOM;
		} else {
			mFlags &= ~CUSTOM;
		}
	}
	/**
	 * Returns the association role for the referrer side, or null 
	 * if none (i.e if the directionality does not include it).
	 */
	public AssociationRole getReferrerRole() {
		if (mReferrerRole == null && isGenerated()) {
			if (!getDirectionality().equals(OPPOSITE_DI)) { //BI_DI or NORMAL_DI
				mReferrerRole = new AssociationRole(this, true/*isReferrerEnd*/);
			}
		}
		return mReferrerRole;
	}
	/**
	 * Returns the association role for the referenced side, or null 
	 * if none (i.e if the directionality does not include it).
	 */
	public AssociationRole getReferencedRole() {
		if (mReferencedRole == null && isGenerated()) {
			if (!getDirectionality().equals(Association.NORMAL_DI)) { //BI_DI or OPPOSITE_DI
				mReferencedRole = new AssociationRole(this, false/*isReferrerEnd*/);
			}
		}
		return mReferencedRole;
	}
	/**
	 * Tests whether this association is valid (valid table and column names).
	 */
	protected boolean isValid(){
		if (!isValidTableAndColumns(mReferrerTableName, mReferrerColNames)
				|| !isValidTableAndColumns(mReferencedTableName, mReferencedColNames)) {
			return false;
		}
		if (mJoinTableName != null) {
			if (!isValidTableAndColumns(mJoinTableName, mReferrerJoinColNames)
					|| !isValidTableAndColumns(mJoinTableName, mReferencedJoinColNames)) {
				return false;
			}			
		}
		return true;
	}
	private boolean isValidTableAndColumns(String tableName, List<String> columnNames) {
		ORMGenTable table = mCustomizer.getTable(tableName);
		if (table == null) {
			return false;
		}
		for (int i = 0, n = columnNames.size(); i < n; ++i) {
			String colName = (String)columnNames.get(i);
			if (table.getColumnByName(colName) == null) {
				return false;
			}
		}
		return true;
	}
	
	public void setForeignKey(ForeignKey foreignKey) {
		this.mForeignKey = foreignKey;
		
	}
	public ForeignKey getForeignKey(){
		return this.mForeignKey;
	};	
	public boolean equals(Object obj) {
		if( this == obj )
			return true;
		if( obj instanceof Association ){
			Association association2 = (Association)obj;
			if (!this.getReferrerTableName().equals(association2.getReferrerTableName())
					|| !this.getReferencedTableName().equals(association2.getReferencedTableName())
					|| !StringUtil.equalObjects(this.getJoinTableName(), association2.getJoinTableName())
					|| !this.getReferrerColumnNames().equals(association2.getReferrerColumnNames())
					|| !this.getReferencedColumnNames().equals(association2.getReferencedColumnNames())
					) {
				return false;
			}					
			/*the 2 association have the same referrer, referenced and join table*/
			//If MTO or OTM association
			if (this.getJoinTableName() == null) {
				return true;
			}
			if (this.getReferrerJoinColumnNames().equals(association2.getReferrerJoinColumnNames())
					&& this.getReferencedJoinColumnNames().equals(association2.getReferencedJoinColumnNames())) {
				return true;
			}
		}
		return false;
	}	
	public String toString(){
		return mReferrerTableName + " " + mCardinality + " " + mReferencedTableName ;
	}
}

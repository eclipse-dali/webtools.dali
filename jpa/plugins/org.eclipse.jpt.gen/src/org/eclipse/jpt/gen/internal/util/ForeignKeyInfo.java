/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.gen.internal.util;

import java.util.List;

import org.eclipse.jpt.jpa.db.ForeignKey;

/**
 * Represents the metadata for a particular foreign key relationship 
 * in a relational database schema.
 * <p/>
 * The referrer column is one which actually contains a foreign 
 * key constraint, and is equivalent to the "foreign key" column.
 * <p/>
 * The referenced column is one which a referrer or foreign key 
 * column references, and is equivalent to the "primary key" column.
 * 
 */
public class ForeignKeyInfo
{
	private transient ForeignKey mForeignKey ;
    private String mName;
    private String mReferrerTableName;
    private String mReferencedTableName;
    private List<String> mReferrerColNames = new java.util.ArrayList<String>();
    private List<String> mReferencedColNames = new java.util.ArrayList<String>();
    
    /**
     * @param fk The name of the constraint backing 
     * this foreign key metadata instance.
     */
    public ForeignKeyInfo(ForeignKey fk, String referrerTableName, String referencedTableName) {
    	mForeignKey = fk;
    	mName = fk.getName();
    	mReferrerTableName = referrerTableName;
    	mReferencedTableName = referencedTableName;
	}
    /**
     * Obtain the constraint name for this foreign key specification.
     * The name for a Foreign Key may, as per the JDBC specification,
     * be <code>null</code> where the constraint is not named.
     * In addition, it may be hardly recognizable to the user,
     * particularly for DB/2 constraints.
     * 
     * @return The name of the constraint backing
     *         this foreign key metadata instance.
     */
    public String getName()
    {
        return mName;
    }
    
    public ForeignKey getForeignKey(){
    	return mForeignKey;
    }
    /**
     * Add another pair of foreign key mappings for this foreign key
     * definition.
     * 
     * @param referrerColumn The referrer column name for this mapping.
     * 
     * @param referencedColumn The referenced column name for this mapping.
     */
    public void addColumnMapping(String referrerColName, String referencedColName) {
    	mReferrerColNames.add(referrerColName);
    	mReferencedColNames.add(referencedColName);
    }
    /**
     * Returns the referrer table name of this foreign key
     * relationship.
     */
    public String getReferrerTableName() {
        return mReferrerTableName;
    }
    /**
     * Returns the referrer column names for this
     * foreign key.
     * The size of this list is always the same as the size of 
     * the list retured from <code>getReferencedColumnNames</code>
     */
    public List<String> getReferrerColumnNames() {
        return mReferrerColNames;
    }
    /**
     * Returns the referenced table name of this foreign key
     * relationship.
     */
    public String getReferencedTableName() {
    	 return mReferencedTableName;
    }
    /**
     * Returns the referenced column names for this
     * foreign key.
     * The size of this list is always the same as the size of 
     * the list retured from <code>getReferrerColumnNames</code>
     */
    public List<String> getReferencedColumnNames() {
        return mReferencedColNames;
    }
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
    	return "name=" + mName
		+ ", referrerTable=" + mReferrerTableName
		+ ", referencedTable=" + mReferencedTableName
		+ ", referrerColumns=" + mReferrerColNames
		+ ", referencedColumns=" + mReferencedColNames
			;
    }
}


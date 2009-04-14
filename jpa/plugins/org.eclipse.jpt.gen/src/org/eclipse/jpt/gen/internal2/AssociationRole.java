/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.gen.internal2;

import java.util.Iterator;
import java.util.List;

/**
 * Represents an association role (the referrer or referenced role).
 * 
 */
public class AssociationRole implements java.io.Serializable
{
	private transient Association mAssociation; //transient: see restore
	private boolean mIsReferrerRole;
	private String mPropertyName;
	private String mCascade;
	
	private final static long serialVersionUID = 1;
		
	AssociationRole(Association association, boolean isReferrerRole) {
		super();
		
		mAssociation = association;
		mIsReferrerRole = isReferrerRole;
	}
	
	/**
	 * Empty constructor needed by the deserialization (should not be used otherwise).
	 */
	public AssociationRole() {
	}
	
	/**
	 * Called after the asscociations are deserialized to attach 
	 * the customizer object.
	 */
	protected void restore(Association association) {
		mAssociation = association;
	}
	
	public Association getAssociation() {
		return mAssociation;
	}
	
	public boolean isReferrerRole() {
		return mIsReferrerRole;
	}
	
	/**
	 * Returns the opposite role or null if the association 
	 * is not bi directional.
	 */
	public AssociationRole getOppositeRole() {
		Association association = getAssociation();
		if (!association.getDirectionality().equals(Association.BI_DI)) {
			return null;
		}
		if (isReferrerRole()) {
			return association.getReferencedRole();
		} else {
			return association.getReferrerRole();
		}
	}
	
	/**
	 * Returns the association cardinality, one of {@link #MANY_TO_ONE}|{@link #MANY_TO_MANY}
	 * |{@link #ONE_TO_ONE}|{@link #ONE_TO_MANY}
	 */
	public String getCardinality() {
		String type = mAssociation.getCardinality();
		if (!mIsReferrerRole) {
			if (type.equals(Association.ONE_TO_MANY)) {
				type = Association.MANY_TO_ONE;
			} else if (type.equals(Association.MANY_TO_ONE)) {
				type = Association.ONE_TO_MANY;
			}
		}
		return type;
	}
	
	public ORMGenTable getReferrerTable() {
		if (mIsReferrerRole) {
			return mAssociation.getReferrerTable();
		} else {
			return mAssociation.getReferencedTable();
		}
	}
	
	public List<ORMGenColumn> getReferrerColumns(){
		if (mIsReferrerRole) {
			return mAssociation.getReferrerColumns();
		} else {
			return mAssociation.getReferencedColumns();
		}
	}
	
	/**
	 * Returns the referenced column corresponding to a referrer column.
	 */
	public ORMGenColumn getReferencedColumn(String referrerColumn)  {
		boolean hasJoinTable = mAssociation.getJoinTable() != null;
		List<ORMGenColumn> referrerColumns = getReferrerColumns();
		for (int i = 0, n = referrerColumns.size(); i < n; ++i) {
			ORMGenColumn column = referrerColumns.get(i);
			if (column.getName().equals(referrerColumn)) {
				if (hasJoinTable) {
					return getReferrerJoinColumns().get(i);
				} else {
					return getReferencedColumns().get(i);
				}
			}
		}
		assert(false);
		return null;
	}
	
	/**
	 * Returns the referrer column corresponding to a referenced column.
	 */
	public ORMGenColumn getReferrerColumn(String referencedColumn)  {
		boolean hasJoinTable = mAssociation.getJoinTable() != null;
		List<ORMGenColumn> referencedColumns = getReferencedColumns();
		for (int i = 0, n = referencedColumns.size(); i < n; ++i) {
			ORMGenColumn column = referencedColumns.get(i);
			if (column.getName().equals(referencedColumn)) {
				if (hasJoinTable) {
					return getReferencedJoinColumns().get(i);
				} else {
					return getReferrerColumns().get(i);
				}
			}
		}
		assert(false);
		return null;
	}
	
	public ORMGenTable getReferencedTable()  {
		if (mIsReferrerRole) {
			return mAssociation.getReferencedTable();
		} else {
			return mAssociation.getReferrerTable();
		}
	}
	
	public List<ORMGenColumn> getReferencedColumns()  {
		if (mIsReferrerRole) {
			return mAssociation.getReferencedColumns();
		} else {
			return mAssociation.getReferrerColumns();
		}
	}
	
	public List<ORMGenColumn> getReferrerJoinColumns()  {
		if (mIsReferrerRole) {
			return mAssociation.getReferrerJoinColumns();
		} else {
			return mAssociation.getReferencedJoinColumns();
		}
	}
	public List<ORMGenColumn> getReferencedJoinColumns()  {
		if (mIsReferrerRole) {
			return mAssociation.getReferencedJoinColumns();
		} else {
			return mAssociation.getReferrerJoinColumns();
		}
	}
	
	/**
	 * Returns the name that should be used by the generator for 
	 * the property corresponding to this role.
	 */
	public String getPropertyName()   {
		if (mPropertyName != null) { //if the user explicitly set it then don't be too smart
			return mPropertyName;
		}
		return getDefaultPropertyName();
	}
		
	private String getDefaultPropertyName() {
		String propName = "";
		ORMGenTable referrerTable = getReferrerTable();
		ORMGenTable referencedTable = getReferencedTable();

		boolean isSingular = isSingular();
		propName = referencedTable.getVarName(isSingular);
		
		List<AssociationRole> clashingRoles = new java.util.ArrayList<AssociationRole>(); //roles with our same referrer and referenced tables (i.e would yield the same property name in the same bean)
		/*make sure there is no role with the same name.*/
		for (Iterator<AssociationRole> iter = referrerTable.getAssociationRoles().iterator(); iter.hasNext(); ) {
			AssociationRole role = iter.next();
			if (role.getReferrerTable().getName().equals(referrerTable.getName())
					&& role.getReferencedTable().getName().equals(referencedTable.getName())
					&& role.isSingular() == isSingular) {
				clashingRoles.add(role);
			}
		}
		if (clashingRoles.size() > 1) {
			int index = clashingRoles.indexOf(this);
			assert(index >= 0);
			propName += index+1;
		}
		
		/*make sure there is no column with the same name.*/
		for (Iterator<ORMGenColumn> iter = referrerTable.getColumns().iterator(); iter.hasNext(); ) {
			ORMGenColumn column = iter.next();
			if (column.getPropertyName().equals(propName)) {
				String prefix = isSingular ? "Bean" : "Set";
				propName += prefix;
				break;
			}
		}
		
		return propName;
	}
	
	private boolean isSingular() {
		String cardinality = getCardinality();
		return cardinality.equals(Association.ONE_TO_ONE) || cardinality.equals(Association.MANY_TO_ONE);
	}
	
	/**
	 * Changes the name that should be used by the generator for 
	 * the property corresponding to this role.
	 * If the argument name is null or empty string then the 
	 * default computed name is used.
	 */
	public void setPropertyName(String name)  {
		if (name != null && name.length() == 0) {
			name = null;
		}
		if (name != null && name.equals(getDefaultPropertyName())) {
			name = null;
		}
		mPropertyName = name;
	}
	
	/**
	 * Returns the cascade value for this role, or null if none.
	 */
	public String getCascade() {
		return mCascade;
	}
	
	public void setCascade(String cascade) {
		if (cascade != null && cascade.length() == 0) {
			cascade = null;
		}
		mCascade = cascade;
	}
	
	/**
	 * Returns a descriptive string used in a comment in the generated 
	 * file (from the Velocity template).
	 */
	public String getDescription()  {
		//<!-- $directionality $cardinality association to $referencedClassName -->
		String directionality;
		if (getAssociation().getDirectionality().equals(Association.BI_DI)) {
			directionality = "bi-directional";
		} else {
			directionality = "uni-directional";
		}
		return directionality + " " + getAssociation().getCardinality() + " association to " + getReferencedTable().getClassName();
	}
}

/*******************************************************************************
* Copyright (c) 2008, 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.persistence.general;

import java.util.Map;

import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.eclipselink.core.context.persistence.general.GeneralProperties;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.EclipseLinkPersistenceUnitProperties;

/**
 *  EclipseLinkGeneralProperties
 */
public class EclipseLinkGeneralProperties extends EclipseLinkPersistenceUnitProperties
	implements GeneralProperties
{
	// ********** EclipseLink properties **********
	private Boolean excludeEclipselinkOrm;

	// ********** constructors **********
	public EclipseLinkGeneralProperties(PersistenceUnit parent) {
		super(parent);
	}

	// ********** initialization **********
	/**
	 * Initializes properties with values from the persistence unit.
	 */
	@Override
	protected void initializeProperties() {
		
		this.excludeEclipselinkOrm = 
			this.getBooleanValue(ECLIPSELINK_EXCLUDE_ECLIPSELINK_ORM);
	}


	// ********** behavior **********
	
	public void propertyValueChanged(String propertyName, String newValue) {
		if (propertyName.equals(ECLIPSELINK_EXCLUDE_ECLIPSELINK_ORM)) {
			excludeEclipselinkOrmChanged(newValue);
		}
	}
	
	public void propertyRemoved(String propertyName) {
		if (propertyName.equals(ECLIPSELINK_EXCLUDE_ECLIPSELINK_ORM)) {
			excludeEclipselinkOrmChanged(null);			
		}
	}
	
	
	/**
	 * Adds property names key/value pairs, where: 
	 * 		key = EclipseLink property key
	 * 		value = property id
	 */
	@Override
	protected void addPropertyNames(Map<String, String> propertyNames) {
		propertyNames.put(
			ECLIPSELINK_EXCLUDE_ECLIPSELINK_ORM,
			EXCLUDE_ECLIPSELINK_ORM_PROPERTY);
	}

	
	// ********** ExcludeEclipselinkOrm **********
	public Boolean getExcludeEclipselinkOrm() {
		return this.excludeEclipselinkOrm;
	}

	public void setExcludeEclipselinkOrm(Boolean newExcludeEclipselinkOrm) {
		Boolean old = this.excludeEclipselinkOrm;
		this.excludeEclipselinkOrm = newExcludeEclipselinkOrm;
		this.putProperty(EXCLUDE_ECLIPSELINK_ORM_PROPERTY, newExcludeEclipselinkOrm);
		this.firePropertyChanged(EXCLUDE_ECLIPSELINK_ORM_PROPERTY, old, newExcludeEclipselinkOrm);
	}

	private void excludeEclipselinkOrmChanged(String stringValue) {
		Boolean newValue = getBooleanValueOf(stringValue);
		
		Boolean old = this.excludeEclipselinkOrm;
		this.excludeEclipselinkOrm = newValue;
		this.firePropertyChanged(EXCLUDE_ECLIPSELINK_ORM_PROPERTY, old, newValue);
	}

	public Boolean getDefaultExcludeEclipselinkOrm() {
		return DEFAULT_EXCLUDE_ECLIPSELINK_ORM;
	}
	
}

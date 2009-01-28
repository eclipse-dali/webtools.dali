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
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.EclipseLinkPersistenceUnitProperties;
import org.eclipse.jpt.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.model.value.ListValueModel;

/**
 *  EclipseLinkGeneralProperties
 */
public class EclipseLinkGeneralProperties extends EclipseLinkPersistenceUnitProperties
	implements GeneralProperties
{
	// ********** EclipseLink properties **********
	private Boolean excludeEclipselinkOrm;

	// ********** constructors **********
	public EclipseLinkGeneralProperties(PersistenceUnit parent, ListValueModel<PersistenceUnit.Property> propertyListAdapter) {
		super(parent, propertyListAdapter);
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

	public void propertyChanged(PropertyChangeEvent event) {
		String aspectName = event.getAspectName();
		if (aspectName.equals(EXCLUDE_ECLIPSELINK_ORM_PROPERTY)) {
			this.excludeEclipselinkOrmChanged(event);
		}
		else {
			throw new IllegalArgumentException("Illegal event received - property not applicable: " + aspectName); //$NON-NLS-1$
		}
		return;
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

	private void excludeEclipselinkOrmChanged(PropertyChangeEvent event) {
		String stringValue = (event.getNewValue() == null) ? null : ((PersistenceUnit.Property) event.getNewValue()).getValue();
		Boolean newValue = getBooleanValueOf(stringValue);
		
		Boolean old = this.excludeEclipselinkOrm;
		this.excludeEclipselinkOrm = newValue;
		this.firePropertyChanged(event.getAspectName(), old, newValue);
	}

	public Boolean getDefaultExcludeEclipselinkOrm() {
		return DEFAULT_EXCLUDE_ECLIPSELINK_ORM;
	}
	
}

/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.context;

import org.eclipse.jpt.core.context.JpaContextNode;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.1
 * @since 2.1
 */
public interface ChangeTracking extends JpaContextNode
{

	/**
	 * Return true if the existence-checking model object exists.  
	 * Have to have a separate flag for this since the default existence
	 * type is different depending on whether hasExistenceChecking() returns
	 * true or false.
	 */
	boolean hasChangeTracking();
	void setChangeTracking(boolean changeTracking);
		String CHANGE_TRACKING_PROPERTY = "changeTrackingProperty"; //$NON-NLS-1$
	
	/**
	 * This is the combination of defaultExistenceType and specifiedExistenceType.
	 * If getSpecifiedExistenceType() returns null, then return getDefaultExistenceType()
	 */
	ChangeTrackingType getChangeTrackingType();
	
	ChangeTrackingType getDefaultChangeTrackingType();		
		String DEFAULT_CHANGE_TRACKING_TYPE_PROPERTY = "defaultChangeTrackinProperty"; //$NON-NLS-1$
		ChangeTrackingType DEFAULT_CHANGE_TRACKING_TYPE = ChangeTrackingType.AUTO;
		
	ChangeTrackingType getSpecifiedChangeTrackingType();	
	void setSpecifiedChangeTrackingType(ChangeTrackingType newSpecifiedChangeTrackingType);
		String SPECIFIED_CHANGE_TRACKING_TYPE_PROPERTY = "specifiedChangeTrackingTypeProperty"; //$NON-NLS-1$

}

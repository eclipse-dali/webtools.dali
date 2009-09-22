/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.jpa2.context.persistence;

import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.jpa2.StaticMetamodelGenerator;
import org.eclipse.jpt.core.jpa2.context.persistence.connection.JpaConnection2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.options.JpaOptions2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.options.ValidationMode;

/**
 * JPA 2.0 <code>persistence-unit</code>
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface PersistenceUnit2_0
	extends PersistenceUnit, StaticMetamodelGenerator
{

	// ********** validation mode **********

	/** 
	 * Return the persistence unit's validation mode,
	 * whether specified or defaulted.
	 */
	ValidationMode getValidationMode();

	/**
	 * String constant associated with changes to the persistence unit's 
	 * specified validation mode
	 */
	String SPECIFIED_VALIDATION_MODE_PROPERTY = "specifiedValidationMode"; //$NON-NLS-1$

	/** 
	 * Return the persistence unit's specified validation mode.
	 */
	ValidationMode getSpecifiedValidationMode();

	/** 
	 * Set the persistence unit's specified validation mode.
	 */
	void setSpecifiedValidationMode(ValidationMode validationMode);

	/**
	 * String constant associated with changes to the persistence unit's 
	 * default validation mode (not typically changed).
	 */
	String DEFAULT_VALIDATION_MODE_PROPERTY = "defaultValidationMode"; //$NON-NLS-1$

	/** 
	 * Return the persistence unit's default validation mode.
	 */
	ValidationMode getDefaultValidationMode();


	// ********** properties **********

	JpaConnection2_0 getConnection();

	JpaOptions2_0 getOptions();
}

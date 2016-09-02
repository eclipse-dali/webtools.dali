/*******************************************************************************
 * Copyright (c) 2009, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.jpa2.context.persistence;

import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.jpa2.JpaMetamodelSynchronizer2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.connection.Connection2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.options.Options2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.options.SharedCacheMode2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.options.ValidationMode2_0;

/**
 * JPA 2.0 <code>persistence-unit</code>
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.3
 */
public interface PersistenceUnit2_0
	extends PersistenceUnit, JpaMetamodelSynchronizer2_0
{
	// ********** shared cache mode **********

	/** 
	 * Return the persistence unit's shared cache mode,
	 * whether specified or defaulted.
	 */
	SharedCacheMode2_0 getSharedCacheMode();
		String SHARED_CACHE_MODE_PROPERTY = "sharedCacheMode"; //$NON-NLS-1$

	/** 
	 * Return the persistence unit's specified shared cache mode.
	 */
	SharedCacheMode2_0 getSpecifiedSharedCacheMode();
		String SPECIFIED_SHARED_CACHE_MODE_PROPERTY = "specifiedSharedCacheMode"; //$NON-NLS-1$

	/** 
	 * Set the persistence unit's specified shared cache mode.
	 */
	void setSpecifiedSharedCacheMode(SharedCacheMode2_0 sharedCacheMode);

	/**
	 * String constant associated with changes to the persistence unit's 
	 * default shared cache mode (not typically changed).
	 */
	String DEFAULT_SHARED_CACHE_MODE_PROPERTY = "defaultSharedCacheMode"; //$NON-NLS-1$

	/** 
	 * Return the persistence unit's default shared cache mode.
	 */
	SharedCacheMode2_0 getDefaultSharedCacheMode();

	boolean calculateDefaultCacheable();

	// ********** validation mode **********

	/** 
	 * Return the persistence unit's validation mode,
	 * whether specified or defaulted.
	 */
	ValidationMode2_0 getValidationMode();
		String VALIDATION_MODE_PROPERTY = "validationMode"; //$NON-NLS-1$

	/** 
	 * Return the persistence unit's specified validation mode.
	 */
	ValidationMode2_0 getSpecifiedValidationMode();
		String SPECIFIED_VALIDATION_MODE_PROPERTY = "specifiedValidationMode"; //$NON-NLS-1$

	/** 
	 * Set the persistence unit's specified validation mode.
	 */
	void setSpecifiedValidationMode(ValidationMode2_0 validationMode);

	/**
	 * String constant associated with changes to the persistence unit's 
	 * default validation mode (not typically changed).
	 */
	String DEFAULT_VALIDATION_MODE_PROPERTY = "defaultValidationMode"; //$NON-NLS-1$

	/** 
	 * Return the persistence unit's default validation mode.
	 */
	ValidationMode2_0 getDefaultValidationMode();
	
	ValidationMode2_0 DEFAULT_VALIDATION_MODE = ValidationMode2_0.AUTO;

	// ********** properties **********

	Connection2_0 getConnection();
		Transformer<PersistenceUnit, Connection2_0> CONNECTION_TRANSFORMER = new ConnectionTransformer();
	class ConnectionTransformer
		extends TransformerAdapter<PersistenceUnit, Connection2_0>
	{
		@Override
		public Connection2_0 transform(PersistenceUnit persistenceUnit) {
			return ((PersistenceUnit2_0) persistenceUnit).getConnection();
		}
	}

	Options2_0 getOptions();
		Transformer<PersistenceUnit, Options2_0> OPTIONS_TRANSFORMER = new OptionsTransformer();
	class OptionsTransformer
		extends TransformerAdapter<PersistenceUnit, Options2_0>
	{
		@Override
		public Options2_0 transform(PersistenceUnit persistenceUnit) {
			return ((PersistenceUnit2_0) persistenceUnit).getOptions();
		}
	}

	// ********** ORM persistence unit defaults **********

	/**
	 * String constant associated with changes to the persistence unit's
	 * default "delimited identifiers" flag.
	 */
	String DEFAULT_DELIMITED_IDENTIFIERS_PROPERTY = "defaultDelimitedIdentifiers"; //$NON-NLS-1$

	/**
	 * Return the default "delimited identifiers" flag from the first persistence unit defaults
	 * found in the persistence unit's list of mapping files.
	 */
	boolean getDefaultDelimitedIdentifiers();

}

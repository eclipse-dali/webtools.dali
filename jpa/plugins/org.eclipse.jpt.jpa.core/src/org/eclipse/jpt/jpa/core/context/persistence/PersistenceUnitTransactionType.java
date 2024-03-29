/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.persistence;

import org.eclipse.jpt.jpa.core.resource.persistence.XmlPersistenceUnitTransactionType;

/**
 * Context model corresponding to the XML resource model
 * {@link XmlPersistenceUnitTransactionType},
 * which corresponds to the <code>transaction-type</code> element in the
 * <code>persistence.xml</code> file.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.0
 * @since 2.0
 */
public enum PersistenceUnitTransactionType 
{
	/** Corresponds to JTA transaction type */
	JTA,
	
	/** Corresponds to RESOURCE_LOCAL transaction type */
	RESOURCE_LOCAL;
	
	public static PersistenceUnitTransactionType fromXmlResourceModel(XmlPersistenceUnitTransactionType transactionType) {
		if (transactionType == null) {
			return null;
		}
		switch (transactionType) {
			case JTA:
				return JTA;
			case RESOURCE_LOCAL:
				return RESOURCE_LOCAL;
			default:
				throw new IllegalArgumentException("unknown transaction type: " + transactionType); //$NON-NLS-1$
		}
	}
	
	public static XmlPersistenceUnitTransactionType toXmlResourceModel(PersistenceUnitTransactionType transactionType) {
		if (transactionType == null) {
			return null;
		}
		switch (transactionType) {
			case JTA:
				return XmlPersistenceUnitTransactionType.JTA;
			case RESOURCE_LOCAL:
				return XmlPersistenceUnitTransactionType.RESOURCE_LOCAL;
			default:
				throw new IllegalArgumentException("unknown transaction type: " + transactionType); //$NON-NLS-1$
		}
	}
}

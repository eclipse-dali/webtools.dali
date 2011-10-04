/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.v2_3.context.java;

import org.eclipse.jpt.jpa.core.context.java.JavaReadOnlyNamedDiscriminatorColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaReadOnlyTableColumn;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.context.ReadOnlyTenantDiscriminatorColumn;

/**
 * tenant discriminator column
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.1
 * @since 3.1
 */
public interface JavaReadOnlyTenantDiscriminatorColumn
	extends JavaReadOnlyNamedDiscriminatorColumn, ReadOnlyTenantDiscriminatorColumn, JavaReadOnlyTableColumn
{

	// ********** owner **********

	/**
	 * interface allowing tenant discriminator columns to be used in multiple places
	 */
	interface Owner
		extends ReadOnlyTenantDiscriminatorColumn.Owner, JavaReadOnlyNamedDiscriminatorColumn.Owner
	{
		//combining interfaces
	}
}

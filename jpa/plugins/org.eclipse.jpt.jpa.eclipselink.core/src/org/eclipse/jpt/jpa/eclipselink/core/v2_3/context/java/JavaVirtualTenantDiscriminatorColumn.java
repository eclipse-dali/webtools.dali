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

import org.eclipse.jpt.jpa.core.context.java.JavaReadOnlyTableColumn;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.context.ReadOnlyTenantDiscriminatorColumn;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.context.VirtualTenantDiscriminatorColumn;

/**
 * Java virtual tenant discriminator column
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JavaVirtualTenantDiscriminatorColumn
	extends VirtualTenantDiscriminatorColumn, JavaReadOnlyTenantDiscriminatorColumn, JavaReadOnlyTableColumn
{
	/**
	 * The overridden column can be either a Java tenant discriminator column or an
	 * <code>orm.xml</code> tenant discriminator column; so we don't change the return type
	 * here.
	 */
	ReadOnlyTenantDiscriminatorColumn getOverriddenColumn();

}

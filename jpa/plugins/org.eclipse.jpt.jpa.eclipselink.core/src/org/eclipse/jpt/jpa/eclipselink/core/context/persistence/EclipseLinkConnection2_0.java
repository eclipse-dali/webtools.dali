/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.context.persistence;

import org.eclipse.jpt.jpa.core.jpa2.context.persistence.connection.Connection2_0;

/**
 * EclipseLink 2.0 connection
 */
public interface EclipseLinkConnection2_0
	extends EclipseLinkConnection, Connection2_0
{
	// combine EclipseLink 1.0 and JPA 2.0 connections
}
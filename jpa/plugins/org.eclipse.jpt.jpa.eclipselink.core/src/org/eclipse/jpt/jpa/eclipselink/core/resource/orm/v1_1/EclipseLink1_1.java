/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License 2.0 which accompanies this distribution, and is
 * available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/

package org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1;

import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLink;

@SuppressWarnings("nls")
public interface EclipseLink1_1
	extends EclipseLink
{
	String SCHEMA_NAMESPACE = EclipseLink.SCHEMA_NAMESPACE;
	String SCHEMA_LOCATION = "http://www.eclipse.org/eclipselink/xsds/eclipselink_orm_1_1.xsd";
	String SCHEMA_VERSION	= "1.1";
	
	// EclipseLink 1.1 specific nodes
	
	String PRIMARY_KEY = "primary-key";  //$NON-NLS-1$
	String PRIMARY_KEY__VALIDATION = "validation";  //$NON-NLS-1$
	String PRIMARY_KEY__COLUMN = "column";  //$NON-NLS-1$
}

/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.resource.orm.translators;

import org.eclipse.jpt.core.internal.resource.orm.translators.OrmXmlMapper;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkConstants;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage;

public interface EclipseLinkOrmXmlMapper
	extends OrmXmlMapper, EclipseLinkConstants
{
	EclipseLinkOrmPackage ECLIPSELINK_ORM_PKG = EclipseLinkOrmPackage.eINSTANCE;
	
	
	String READ_ONLY = "read-only";  //$NON-NLS-1$
}

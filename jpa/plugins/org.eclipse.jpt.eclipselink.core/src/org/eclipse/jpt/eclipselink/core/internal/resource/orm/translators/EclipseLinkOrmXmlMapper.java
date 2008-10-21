/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved. This
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
	
	String CACHE = "cache";  //$NON-NLS-1$
	String CACHE__EXPIRY = "expiry";  //$NON-NLS-1$
	String CACHE__SIZE = "size";  //$NON-NLS-1$
	String CACHE__SHARED = "shared";  //$NON-NLS-1$
	String CACHE__TYPE = "type";  //$NON-NLS-1$
	String CACHE__ALWAYS_REFRESH = "always-refresh";  //$NON-NLS-1$
	String CACHE__REFRESH_ONLY_IF_NEWER = "refresh-only-if-newer";  //$NON-NLS-1$
	String CACHE__DISABLE_HITS = "disable-hits";  //$NON-NLS-1$
	String CACHE__COORDINATION_TYPE = "coordination-type";  //$NON-NLS-1$
	
	String CUSTOMIZER = "customizer";  //$NON-NLS-1$

	String EXISTENCE_CHECKING = "existence-checking";  //$NON-NLS-1$
	
	String EXPIRY_TIME_OF_DAY = "expiry-time-of-day";  //$NON-NLS-1$
	String EXPIRY_TIME_OF_DAY__HOUR = "hour";  //$NON-NLS-1$
	String EXPIRY_TIME_OF_DAY__MINUTE = "minute";  //$NON-NLS-1$
	String EXPIRY_TIME_OF_DAY__SECOND = "second";  //$NON-NLS-1$
	String EXPIRY_TIME_OF_DAY__MILLISECOND = "millisecond";  //$NON-NLS-1$
	
	String MUTABLE = "mutable";  //$NON-NLS-1$
	
	String PRIVATE_OWNED = "private-owned";  //$NON-NLS-1$
	
	String READ_ONLY = "read-only";  //$NON-NLS-1$

}

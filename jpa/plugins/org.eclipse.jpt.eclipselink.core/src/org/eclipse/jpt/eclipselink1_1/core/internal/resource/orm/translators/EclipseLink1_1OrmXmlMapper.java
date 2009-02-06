/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink1_1.core.internal.resource.orm.translators;

import org.eclipse.jpt.eclipselink.core.internal.resource.orm.translators.EclipseLinkOrmXmlMapper;
import org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage;

public interface EclipseLink1_1OrmXmlMapper
	extends EclipseLinkOrmXmlMapper
{
	EclipseLink1_1OrmPackage ECLIPSELINK1_1_ORM_PKG = EclipseLink1_1OrmPackage.eINSTANCE;
	
	String PRIMARY_KEY = "primary-key";  //$NON-NLS-1$

}

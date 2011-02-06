/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/

package org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_2;

import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.EclipseLink1_1;

@SuppressWarnings("nls")
public interface EclipseLink1_2
	extends EclipseLink1_1
{
	String SCHEMA_NAMESPACE = EclipseLink.SCHEMA_NAMESPACE;
	String SCHEMA_LOCATION = "http://www.eclipse.org/eclipselink/xsds/eclipselink_orm_1_2.xsd";
	String SCHEMA_VERSION	= "1.2";

}

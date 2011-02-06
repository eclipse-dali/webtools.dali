/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.v1_2;

import org.eclipse.jpt.jpa.core.context.PersistentType.Owner;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaFactory;
import org.eclipse.jpt.jpa.eclipselink.core.internal.v1_2.context.java.EclipseLinkJavaPersistentType1_2;

public class EclipseLink1_2JpaFactory
	extends EclipseLinkJpaFactory
{
	public EclipseLink1_2JpaFactory() {
		super();
	}
	
	// ********** Java Context Model **********

	@Override
	public JavaPersistentType buildJavaPersistentType(Owner owner, JavaResourcePersistentType jrpt) {
		return new EclipseLinkJavaPersistentType1_2(owner, jrpt);
	}
}

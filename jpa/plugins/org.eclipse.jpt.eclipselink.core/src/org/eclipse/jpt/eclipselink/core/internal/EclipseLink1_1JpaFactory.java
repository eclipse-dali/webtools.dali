/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal;

import org.eclipse.jpt.core.context.PersistentType.Owner;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLink1_1JavaPersistentTypeImpl;

public class EclipseLink1_1JpaFactory
	extends EclipseLinkJpaFactory
{
	protected EclipseLink1_1JpaFactory() {
		super();
	}

	@Override
	public JavaPersistentType buildJavaPersistentType(Owner owner, JavaResourcePersistentType jrpt) {
		return new EclipseLink1_1JavaPersistentTypeImpl(owner, jrpt);
	}

}

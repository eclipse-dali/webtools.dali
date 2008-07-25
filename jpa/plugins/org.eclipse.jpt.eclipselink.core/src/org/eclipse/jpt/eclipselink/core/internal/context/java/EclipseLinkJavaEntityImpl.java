/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.java;

import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.internal.context.java.GenericJavaEntity;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.eclipselink.core.EclipseLinkJpaFactory;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkCaching;
import org.eclipse.jpt.eclipselink.core.context.java.EclipseLinkJavaCaching;
import org.eclipse.jpt.eclipselink.core.context.java.EclipseLinkJavaEntity;

public class EclipseLinkJavaEntityImpl extends GenericJavaEntity implements EclipseLinkJavaEntity
{
	protected EclipseLinkJavaCaching eclipseLinkCaching;
	
	public EclipseLinkJavaEntityImpl(JavaPersistentType parent) {
		super(parent);
		this.eclipseLinkCaching = getJpaFactory().buildEclipseLinkJavaCaching(this);
	}

	
	public EclipseLinkCaching getCaching() {
		return this.eclipseLinkCaching;
	}
	
	@Override
	protected EclipseLinkJpaFactory getJpaFactory() {
		return (EclipseLinkJpaFactory) super.getJpaFactory();
	}
	
	
	@Override
	public void update(JavaResourcePersistentType resourcePersistentType) {
		super.update(resourcePersistentType);
		this.eclipseLinkCaching.update(resourcePersistentType);
	}
	
	@Override
	public void initializeFromResource(JavaResourcePersistentType resourcePersistentType) {
		super.initializeFromResource(resourcePersistentType);
		this.eclipseLinkCaching.initialize(resourcePersistentType);
	}
}

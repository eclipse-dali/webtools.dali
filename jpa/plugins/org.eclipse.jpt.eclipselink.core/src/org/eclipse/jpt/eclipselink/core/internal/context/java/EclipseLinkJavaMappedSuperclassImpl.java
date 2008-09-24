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

import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.internal.context.java.GenericJavaMappedSuperclass;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.eclipselink.core.EclipseLinkJpaFactory;
import org.eclipse.jpt.eclipselink.core.context.java.EclipseLinkJavaCaching;
import org.eclipse.jpt.eclipselink.core.context.java.EclipseLinkJavaMappedSuperclass;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class EclipseLinkJavaMappedSuperclassImpl extends GenericJavaMappedSuperclass implements EclipseLinkJavaMappedSuperclass
{
	protected EclipseLinkJavaCaching eclipseLinkCaching;
	
	public EclipseLinkJavaMappedSuperclassImpl(JavaPersistentType parent) {
		super(parent);
		this.eclipseLinkCaching = getJpaFactory().buildEclipseLinkJavaCaching(this);
	}

	
	public EclipseLinkJavaCaching getCaching() {
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
	public void initialize(JavaResourcePersistentType resourcePersistentType) {
		super.initialize(resourcePersistentType);
		this.eclipseLinkCaching.initialize(resourcePersistentType);
	}
	
	//********** Validation ********************************************

	@Override
	public void validate(List<IMessage> messages, CompilationUnit astRoot) {
		super.validate(messages, astRoot);
		this.eclipseLinkCaching.validate(messages, astRoot);
	}
}

/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.java;

import java.util.List;
import java.util.Vector;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaOneToOneMapping;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkJoinFetch;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkPrivateOwned;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.eclipselink.core.v2_0.context.EclipseLinkOneToOneMapping2_0;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractJavaEclipseLinkOneToOneMapping
	extends AbstractJavaOneToOneMapping
	implements EclipseLinkOneToOneMapping2_0
{
	protected final JavaEclipseLinkJoinFetch joinFetch;
	
	protected final JavaEclipseLinkPrivateOwned privateOwned;
	
	
	protected AbstractJavaEclipseLinkOneToOneMapping(JavaPersistentAttribute parent) {
		super(parent);
		this.joinFetch = new JavaEclipseLinkJoinFetch(this);
		this.privateOwned = new JavaEclipseLinkPrivateOwned(this);
	}
	
	@Override
	protected void addSupportingAnnotationNamesTo(Vector<String> names) {
		super.addSupportingAnnotationNamesTo(names);
		names.add(EclipseLink.JOIN_FETCH);
		names.add(EclipseLink.PRIVATE_OWNED);
	}
	
	public EclipseLinkJoinFetch getJoinFetch() {
		return this.joinFetch;
	}

	public EclipseLinkPrivateOwned getPrivateOwned() {
		return this.privateOwned;
	}
	
	@Override
	protected void initialize() {
		super.initialize();
		this.joinFetch.initialize(this.getResourcePersistentAttribute());
		this.privateOwned.initialize(this.getResourcePersistentAttribute());
	}
	
	@Override
	protected void update() {
		super.update();
		this.joinFetch.update(this.getResourcePersistentAttribute());
		this.privateOwned.update(this.getResourcePersistentAttribute());
	}
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		this.joinFetch.validate(messages, reporter, astRoot);
		this.privateOwned.validate(messages, reporter, astRoot);
	}
}

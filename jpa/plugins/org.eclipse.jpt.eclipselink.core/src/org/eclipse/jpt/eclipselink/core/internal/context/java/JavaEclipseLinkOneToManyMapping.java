/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.context.java.JavaMappingRelationship;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaOneToManyMapping;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkJoinFetch;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkPrivateOwned;
import org.eclipse.jpt.eclipselink.core.v2_0.context.EclipseLinkOneToManyMapping2_0;
import org.eclipse.jpt.eclipselink.core.v2_0.context.java.EclipseLinkJavaOneToManyRelationship2_0;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class JavaEclipseLinkOneToManyMapping
	extends AbstractJavaOneToManyMapping
	implements EclipseLinkOneToManyMapping2_0
{
	protected final JavaEclipseLinkJoinFetch joinFetch;

	protected final JavaEclipseLinkPrivateOwned privateOwned;


	public JavaEclipseLinkOneToManyMapping(JavaPersistentAttribute parent) {
		super(parent);
		this.joinFetch = new JavaEclipseLinkJoinFetch(this);
		this.privateOwned = new JavaEclipseLinkPrivateOwned(this);
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.privateOwned.synchronizeWithResourceModel();
		this.joinFetch.synchronizeWithResourceModel();
	}

	@Override
	public void update() {
		super.update();
		this.privateOwned.update();
		this.joinFetch.update();
	}


	// ********** private owned **********

	public EclipseLinkPrivateOwned getPrivateOwned() {
		return this.privateOwned;
	}


	// ********** join fetch **********

	public EclipseLinkJoinFetch getJoinFetch() {
		return this.joinFetch;
	}


	// ********** relationship **********

	@Override
	public EclipseLinkJavaOneToManyRelationship2_0 getRelationship() {
		return (EclipseLinkJavaOneToManyRelationship) super.getRelationship();
	}

	@Override
	protected JavaMappingRelationship buildRelationship() {
		return new EclipseLinkJavaOneToManyRelationship(this);
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		this.joinFetch.validate(messages, reporter, astRoot);
		this.privateOwned.validate(messages, reporter, astRoot);
	}
}

/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.java;

import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaOneToOneMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkJoinFetch;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkPrivateOwned;
import org.eclipse.jpt.jpa.eclipselink.core.v2_0.context.EclipseLinkOneToOneMapping2_0;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class JavaEclipseLinkOneToOneMapping
	extends AbstractJavaOneToOneMapping
	implements EclipseLinkOneToOneMapping2_0
{
	protected final JavaEclipseLinkJoinFetch joinFetch;

	protected final JavaEclipseLinkPrivateOwned privateOwned;


	public JavaEclipseLinkOneToOneMapping(JavaPersistentAttribute parent) {
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


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		this.joinFetch.validate(messages, reporter, astRoot);
		this.privateOwned.validate(messages, reporter, astRoot);
	}
}

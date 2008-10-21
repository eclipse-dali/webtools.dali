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
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.internal.context.java.GenericJavaOneToOneMapping;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkOneToOneMapping;
import org.eclipse.jpt.eclipselink.core.context.JoinFetchable;
import org.eclipse.jpt.eclipselink.core.context.PrivateOwned;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class EclipseLinkJavaOneToOneMappingImpl extends GenericJavaOneToOneMapping implements EclipseLinkOneToOneMapping
{
	
	protected final EclipseLinkJavaJoinFetchable joinFetchable;
	protected final EclipseLinkJavaPrivateOwned privateOwned;

	public EclipseLinkJavaOneToOneMappingImpl(JavaPersistentAttribute parent) {
		super(parent);
		this.joinFetchable = new EclipseLinkJavaJoinFetchable(this);
		this.privateOwned = new EclipseLinkJavaPrivateOwned(this);
	}
	
	public JoinFetchable getJoinFetchable() {
		return this.joinFetchable;
	}

	public PrivateOwned getPrivateOwned() {
		return this.privateOwned;
	}
	
	@Override
	public void initialize(JavaResourcePersistentAttribute jrpa) {
		super.initialize(jrpa);
		this.joinFetchable.initialize(jrpa);
		this.privateOwned.initialize(jrpa);
	}
	
	@Override
	public void update(JavaResourcePersistentAttribute jrpa) {
		super.update(jrpa);
		this.joinFetchable.update(jrpa);
		this.privateOwned.update(jrpa);
	}
	
	@Override
	public void validate(List<IMessage> messages, CompilationUnit astRoot) {
		super.validate(messages, astRoot);
		this.joinFetchable.validate(messages, astRoot);
		this.privateOwned.validate(messages, astRoot);
	}

}

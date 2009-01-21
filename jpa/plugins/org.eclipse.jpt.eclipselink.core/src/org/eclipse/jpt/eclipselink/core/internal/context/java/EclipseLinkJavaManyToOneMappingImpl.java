/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.internal.context.java.GenericJavaManyToOneMapping;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkRelationshipMapping;
import org.eclipse.jpt.eclipselink.core.context.JoinFetch;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class EclipseLinkJavaManyToOneMappingImpl extends GenericJavaManyToOneMapping implements EclipseLinkRelationshipMapping
{
	protected final EclipseLinkJavaJoinFetch joinFetch;
	
	
	public EclipseLinkJavaManyToOneMappingImpl(JavaPersistentAttribute parent) {
		super(parent);
		this.joinFetch = new EclipseLinkJavaJoinFetch(this);
	}
	
	
	public JoinFetch getJoinFetch() {
		return this.joinFetch;
	}
	
	@Override
	protected void initialize() {
		super.initialize();
		this.joinFetch.initialize(this.resourcePersistentAttribute);
	}
	
	@Override
	protected void update() {
		super.update();
		this.joinFetch.update(this.resourcePersistentAttribute);
	}
	
	@Override
	public void validate(List<IMessage> messages, CompilationUnit astRoot) {
		super.validate(messages, astRoot);
		this.joinFetch.validate(messages, astRoot);
	}
}

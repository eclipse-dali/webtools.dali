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

import java.util.Iterator;
import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaRelationshipReference;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaOneToManyMapping;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkOneToManyMapping;
import org.eclipse.jpt.eclipselink.core.context.JoinFetch;
import org.eclipse.jpt.eclipselink.core.context.PrivateOwned;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkJPA;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkPrivateOwnedAnnotation;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class EclipseLinkJavaOneToManyMapping
	extends AbstractJavaOneToManyMapping
	implements EclipseLinkOneToManyMapping
{
	protected final EclipseLinkJavaJoinFetch joinFetch;
	
	protected final EclipseLinkJavaPrivateOwned privateOwned;
	
	
	public EclipseLinkJavaOneToManyMapping(JavaPersistentAttribute parent) {
		super(parent);
		this.joinFetch = new EclipseLinkJavaJoinFetch(this);
		this.privateOwned = new EclipseLinkJavaPrivateOwned(this);
	}
	
	@Override
	protected JavaRelationshipReference buildRelationshipReference() {
		return new EclipseLinkJavaOneToManyRelationshipReference(this);
	}
	
	@Override
	public Iterator<String> supportingAnnotationNames() {
		return new CompositeIterator<String>(
			super.supportingAnnotationNames(),
			new ArrayIterator<String>(
				EclipseLinkJPA.JOIN_FETCH,
				EclipseLinkJPA.PRIVATE_OWNED));
	}
	
	@Override
	public EclipseLinkJavaOneToManyRelationshipReference getRelationshipReference() {
		return (EclipseLinkJavaOneToManyRelationshipReference) super.getRelationshipReference();
	}
	
	
	// **************** private owned ******************************************
	
	public PrivateOwned getPrivateOwned() {
		return this.privateOwned;
	}
	
	protected String getPrivateOwnedAnnotationName() {
		return EclipseLinkPrivateOwnedAnnotation.ANNOTATION_NAME;
	}
	
	protected EclipseLinkPrivateOwnedAnnotation getResourcePrivateOwned() {
		return (EclipseLinkPrivateOwnedAnnotation) this.resourcePersistentAttribute.getSupportingAnnotation(getPrivateOwnedAnnotationName());
	}
	
	protected void addResourcePrivateOwned() {
		this.resourcePersistentAttribute.addSupportingAnnotation(getPrivateOwnedAnnotationName());
	}
	
	protected void removeResourcePrivateOwned() {
		this.resourcePersistentAttribute.removeSupportingAnnotation(getPrivateOwnedAnnotationName());
	}
	
	
	// **************** join fetch *********************************************
	
	public JoinFetch getJoinFetch() {
		return this.joinFetch;
	}
	
	
	// **************** resource -> context ************************************
	
	@Override
	protected void initialize() {
		super.initialize();
		this.joinFetch.initialize(this.resourcePersistentAttribute);
		this.privateOwned.initialize(this.resourcePersistentAttribute);
	}
	
	@Override
	protected void update() {
		super.update();
		this.joinFetch.update(this.resourcePersistentAttribute);
		this.privateOwned.update(this.resourcePersistentAttribute);
	}
	
	
	// **************** validation *********************************************
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		this.joinFetch.validate(messages, reporter, astRoot);
		this.privateOwned.validate(messages, reporter, astRoot);
	}
}

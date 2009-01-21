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
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.internal.context.java.GenericJavaOneToManyMapping;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkOneToManyMapping;
import org.eclipse.jpt.eclipselink.core.context.JoinFetch;
import org.eclipse.jpt.eclipselink.core.context.PrivateOwned;
import org.eclipse.jpt.eclipselink.core.resource.java.PrivateOwnedAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class EclipseLinkJavaOneToManyMappingImpl extends GenericJavaOneToManyMapping implements EclipseLinkOneToManyMapping
{
	protected final EclipseLinkJavaJoinFetch joinFetch;
	
	protected final EclipseLinkJavaPrivateOwned privateOwned;
	
	
	public EclipseLinkJavaOneToManyMappingImpl(JavaPersistentAttribute parent) {
		super(parent);
		this.joinFetch = new EclipseLinkJavaJoinFetch(this);
		this.privateOwned = new EclipseLinkJavaPrivateOwned(this);
	}
	
	// ********** NonOwningMapping implementation **********
	@Override
	public boolean mappedByIsValid(AttributeMapping mappedByMapping) {
		return super.mappedByIsValid(mappedByMapping) || (mappedByMapping.getKey() == MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
	}

	protected String getPrivateOwnedAnnotationName() {
		return PrivateOwnedAnnotation.ANNOTATION_NAME;
	}
	
	protected PrivateOwnedAnnotation getResourcePrivateOwned() {
		return (PrivateOwnedAnnotation) this.resourcePersistentAttribute.getSupportingAnnotation(getPrivateOwnedAnnotationName());
	}
	
	protected void addResourcePrivateOwned() {
		this.resourcePersistentAttribute.addSupportingAnnotation(getPrivateOwnedAnnotationName());
	}
	
	protected void removeResourcePrivateOwned() {
		this.resourcePersistentAttribute.removeSupportingAnnotation(getPrivateOwnedAnnotationName());
	}
	
	public JoinFetch getJoinFetch() {
		return this.joinFetch;
	}

	public PrivateOwned getPrivateOwned() {
		return this.privateOwned;
	}
	
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
		
	@Override
	public void validate(List<IMessage> messages, CompilationUnit astRoot) {
		super.validate(messages, astRoot);
		this.joinFetch.validate(messages, astRoot);
		this.privateOwned.validate(messages, astRoot);
	}
}
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
import org.eclipse.jpt.core.internal.context.java.GenericJavaOneToManyMapping;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkOneToManyMapping;
import org.eclipse.jpt.eclipselink.core.context.PrivateOwnable;
import org.eclipse.jpt.eclipselink.core.context.java.JavaJoinFetchable;
import org.eclipse.jpt.eclipselink.core.context.java.JavaPrivateOwnable;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkJpaFactory;
import org.eclipse.jpt.eclipselink.core.resource.java.PrivateOwnedAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class EclipseLinkJavaOneToManyMappingImpl extends GenericJavaOneToManyMapping implements EclipseLinkOneToManyMapping
{
	
	protected final JavaJoinFetchable joinFetchable;
	protected final JavaPrivateOwnable privateOwnable;
	
	public EclipseLinkJavaOneToManyMappingImpl(JavaPersistentAttribute parent) {
		super(parent);
		this.joinFetchable = getJpaFactory().buildJavaJoinFetchable(this);
		this.privateOwnable = getJpaFactory().buildJavaPrivateOwnable(this);
	}
	
	@Override
	protected EclipseLinkJpaFactory getJpaFactory() {
		return (EclipseLinkJpaFactory) super.getJpaFactory();
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
	
	public JavaJoinFetchable getJoinFetchable() {
		return this.joinFetchable;
	}

	public PrivateOwnable getPrivateOwnable() {
		return this.privateOwnable;
	}
	
	@Override
	public void initialize(JavaResourcePersistentAttribute jrpa) {
		super.initialize(jrpa);
		this.joinFetchable.initialize(jrpa);
		this.privateOwnable.initialize(jrpa);
	}
	
	@Override
	public void update(JavaResourcePersistentAttribute jrpa) {
		super.update(jrpa);
		this.joinFetchable.update(jrpa);
		this.privateOwnable.update(jrpa);
	}
		
	@Override
	public void validate(List<IMessage> messages, CompilationUnit astRoot) {
		super.validate(messages, astRoot);
		this.joinFetchable.validate(messages, astRoot);
		this.privateOwnable.validate(messages, astRoot);
	}
}
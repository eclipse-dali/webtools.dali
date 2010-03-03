/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.FetchType;
import org.eclipse.jpt.core.context.Nullable;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.jpa2.JpaFactory2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaDerivedIdentity2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaSingleRelationshipMapping2_0;
import org.eclipse.jpt.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.core.jpa2.resource.java.RelationshipMapping2_0Annotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Java single relationship (1:1, m:1) mapping
 */
public abstract class AbstractJavaSingleRelationshipMapping<T extends RelationshipMapping2_0Annotation>
	extends AbstractJavaRelationshipMapping<T>
	implements JavaSingleRelationshipMapping2_0
{
	protected Boolean specifiedOptional;

	protected final JavaDerivedIdentity2_0 derivedIdentity;
	
	
	protected AbstractJavaSingleRelationshipMapping(JavaPersistentAttribute parent) {
		super(parent);
		this.derivedIdentity = buildDerivedIdentity();
	}
	
	@Override
	protected void initialize() {
		super.initialize();
		this.specifiedOptional = this.getResourceOptional();
		this.derivedIdentity.initialize();
	}
	
	@Override
	protected void update() {
		super.update();
		this.setSpecifiedOptional_(this.getResourceOptional());
		this.derivedIdentity.update();
	}
	
	@Override
	protected void addSupportingAnnotationNamesTo(Vector<String> names) {
		super.addSupportingAnnotationNamesTo(names);
		names.add(JPA.JOIN_COLUMN);
		names.add(JPA.JOIN_COLUMNS);
		names.add(JPA.JOIN_TABLE);
		if (this.isJpa2_0Compatible()) {
			names.add(JPA.ID);
			names.add(JPA2_0.MAPS_ID);
		}
	}
	
	
	// ********** optional **********
	
	public boolean isOptional() {
		return (this.specifiedOptional != null) ? this.specifiedOptional.booleanValue() : this.isDefaultOptional();
	}
	
	public Boolean getSpecifiedOptional() {
		return this.specifiedOptional;
	}
	
	public void setSpecifiedOptional(Boolean optional) {
		Boolean old = this.specifiedOptional;
		this.specifiedOptional = optional;
		this.setResourceOptional(optional);
		this.firePropertyChanged(Nullable.SPECIFIED_OPTIONAL_PROPERTY, old, optional);
	}
	
	protected void setSpecifiedOptional_(Boolean optional) {
		Boolean old = this.specifiedOptional;
		this.specifiedOptional = optional;
		this.firePropertyChanged(Nullable.SPECIFIED_OPTIONAL_PROPERTY, old, optional);
	}
	
	public boolean isDefaultOptional() {
		return Nullable.DEFAULT_OPTIONAL;
	}
	
	protected abstract Boolean getResourceOptional();
	
	protected abstract void setResourceOptional(Boolean newOptional);
	
	
	// ********** 2.0 derived identity **********
	
	protected JavaDerivedIdentity2_0 buildDerivedIdentity() {
		return ((JpaFactory2_0) getJpaFactory()).buildJavaDerivedIdentity(this);
	}
	
	public JavaDerivedIdentity2_0 getDerivedIdentity() {
		return this.derivedIdentity;
	}
	
	
	// ********** AbstractJavaRelationshipMapping implementation **********
	
	@Override
	protected String buildDefaultTargetEntity() {
		return this.getPersistentAttribute().getSingleReferenceTargetTypeName();
	}
	
	
	// ********** Fetchable implementation **********
	
	public FetchType getDefaultFetch() {
		return DEFAULT_FETCH_TYPE;
	}
	
	
	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result == null) {
			result = this.derivedIdentity.javaCompletionProposals(pos, filter, astRoot);
		}
		return result;
	}
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		this.derivedIdentity.validate(messages, reporter, astRoot);
	}
}

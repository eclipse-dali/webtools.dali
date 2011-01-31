/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.core.jpa2.resource.java.OneToOne2_0Annotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;

/**
 * javax.persistence.OneToOne
 */
public class BinaryOneToOneAnnotation
	extends BinaryRelationshipMappingAnnotation
	implements OneToOne2_0Annotation
{
	private Boolean optional;
	private String mappedBy;
	private Boolean orphanRemoval; //added in JPA 2.0

	public BinaryOneToOneAnnotation(JavaResourcePersistentAttribute parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.optional = this.buildOptional();
		this.mappedBy = this.buildMappedBy();
		this.orphanRemoval = this.buildOrphanRemoval();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void update() {
		super.update();
		this.setOptional_(this.buildOptional());
		this.setMappedBy_(this.buildMappedBy());
		this.setOrphanRemoval_(this.buildOrphanRemoval());
	}


	// ********** BinaryRelationshipMappingAnnotation implementation **********

	@Override
	String getTargetEntityElementName() {
		return JPA.ONE_TO_ONE__TARGET_ENTITY;
	}

	@Override
	String getFetchElementName() {
		return JPA.ONE_TO_ONE__FETCH;
	}

	@Override
	String getCascadeElementName() {
		return JPA.ONE_TO_ONE__CASCADE;
	}


	//**************** OwnableRelationshipMappingAnnotation implementation **************

	// ***** mapped by
	public String getMappedBy() {
		return this.mappedBy;
	}

	public void setMappedBy(String mappedBy) {
		throw new UnsupportedOperationException();
	}

	private void setMappedBy_(String mappedBy) {
		String old = this.mappedBy;
		this.mappedBy = mappedBy;
		this.firePropertyChanged(MAPPED_BY_PROPERTY, old, mappedBy);
	}

	private String buildMappedBy() {
		return (String) this.getJdtMemberValue(JPA.ONE_TO_ONE__MAPPED_BY);
	}

	public TextRange getMappedByTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	public boolean mappedByTouches(int pos, CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}


	//**************** OneToOneAnnotation implementation **************

	// ***** optional
	public Boolean getOptional() {
		return this.optional;
	}

	public void setOptional(Boolean optional) {
		throw new UnsupportedOperationException();
	}

	private void setOptional_(Boolean optional) {
		Boolean old = this.optional;
		this.optional = optional;
		this.firePropertyChanged(OPTIONAL_PROPERTY, old, optional);
	}

	private Boolean buildOptional() {
		return (Boolean) this.getJdtMemberValue(JPA.ONE_TO_ONE__OPTIONAL);
	}

	public TextRange getOptionalTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}


	// ********** OneToOne2_0Annotation implementation **********

	public Boolean getOrphanRemoval() {
		return this.orphanRemoval;
	}

	public void setOrphanRemoval(Boolean orphanRemoval) {
		throw new UnsupportedOperationException();
	}

	public TextRange getOrphanRemovalTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	private Boolean buildOrphanRemoval() {
		return (Boolean) this.getJdtMemberValue(JPA2_0.ONE_TO_ONE__ORPHAN_REMOVAL);
	}

	private void setOrphanRemoval_(Boolean orphanRemoval) {
		Boolean old = this.orphanRemoval;
		this.orphanRemoval = orphanRemoval;
		this.firePropertyChanged(ORPHAN_REMOVAL_PROPERTY, old, orphanRemoval);
	}
}

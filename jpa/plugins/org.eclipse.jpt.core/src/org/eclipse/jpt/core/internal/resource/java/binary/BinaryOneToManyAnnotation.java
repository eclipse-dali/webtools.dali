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
import org.eclipse.jpt.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.core.jpa2.resource.java.OneToMany2_0Annotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * javax.persistence.OneToMany
 */
public final class BinaryOneToManyAnnotation
	extends BinaryRelationshipMappingAnnotation
	implements OneToMany2_0Annotation
{
	private String mappedBy;

	private Boolean orphanRemoval; //added in JPA 2.0

	public BinaryOneToManyAnnotation(JavaResourcePersistentAttribute parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.mappedBy = this.buildMappedBy();
		this.orphanRemoval = this.buildOrphanRemoval();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void update() {
		super.update();
		this.setMappedBy_(this.buildMappedBy());
		this.setOrphanRemoval_(this.buildOrphanRemoval());
	}


	// ********** BinaryRelationshipMappingAnnotation implementation **********

	@Override
	String getTargetEntityElementName() {
		return JPA.ONE_TO_MANY__TARGET_ENTITY;
	}

	@Override
	String getFetchElementName() {
		return JPA.ONE_TO_MANY__FETCH;
	}

	@Override
	String getCascadeElementName() {
		return JPA.ONE_TO_MANY__CASCADE;
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
		return (String) this.getJdtMemberValue(JPA.ONE_TO_MANY__MAPPED_BY);
	}

	public TextRange getMappedByTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	public boolean mappedByTouches(int pos, CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}


	// ********** OneToMany2_0Annotation implementation **********

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
		return (Boolean) this.getJdtMemberValue(JPA2_0.ONE_TO_MANY__ORPHAN_REMOVAL);
	}

	private void setOrphanRemoval_(Boolean orphanRemoval) {
		Boolean old = this.orphanRemoval;
		this.orphanRemoval = orphanRemoval;
		this.firePropertyChanged(ORPHAN_REMOVAL_PROPERTY, old, orphanRemoval);
	}

}

/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.OneToManyAnnotation;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * javax.persistence.OneToMany
 */
public final class BinaryOneToManyAnnotation
	extends BinaryRelationshipMappingAnnotation
	implements OneToManyAnnotation
{
	private String mappedBy;


	public BinaryOneToManyAnnotation(JavaResourcePersistentAttribute parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.mappedBy = this.buildMappedBy();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void update() {
		super.update();
		this.setMappedBy_(this.buildMappedBy());
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

}

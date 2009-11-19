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
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.NestableJoinColumnAnnotation;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * javax.persistence.JoinColumn
 */
public final class BinaryJoinColumnAnnotation
	extends BinaryBaseColumnAnnotation
	implements NestableJoinColumnAnnotation
{
	private String referencedColumnName;


	public BinaryJoinColumnAnnotation(JavaResourceNode parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.referencedColumnName = this.buildReferencedColumnName();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void update() {
		super.update();
		this.setReferencedColumnName_(this.buildReferencedColumnName());
	}


	// ********** JavaSourceNamedColumnAnnotation implementation **********

	@Override
	protected String getNameElementName() {
		return JPA.JOIN_COLUMN__NAME;
	}

	@Override
	protected String getColumnDefinitionElementName() {
		return JPA.JOIN_COLUMN__COLUMN_DEFINITION;
	}


	// ********** JavaSourceNamedColumnAnnotation implementation **********

	@Override
	String getTableElementName() {
		return JPA.JOIN_COLUMN__TABLE;
	}

	@Override
	String getUniqueElementName() {
		return JPA.JOIN_COLUMN__UNIQUE;
	}

	@Override
	String getNullableElementName() {
		return JPA.JOIN_COLUMN__NULLABLE;
	}

	@Override
	String getInsertableElementName() {
		return JPA.JOIN_COLUMN__INSERTABLE;
	}

	@Override
	String getUpdatableElementName() {
		return JPA.JOIN_COLUMN__UPDATABLE;
	}


	//************ JoinColumn implementation ***************

	// referenced column name
	public String getReferencedColumnName() {
		return this.referencedColumnName;
	}

	public void setReferencedColumnName(String referencedColumnName) {
		throw new UnsupportedOperationException();
	}

	private void setReferencedColumnName_(String referencedColumnName) {
		String old = this.referencedColumnName;
		this.referencedColumnName = referencedColumnName;
		this.firePropertyChanged(REFERENCED_COLUMN_NAME_PROPERTY, old, referencedColumnName);
	}

	private String buildReferencedColumnName() {
		return (String) this.getJdtMemberValue(JPA.JOIN_COLUMN__REFERENCED_COLUMN_NAME);
	}

	public TextRange getReferencedColumnNameTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	public boolean referencedColumnNameTouches(int pos, CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

}

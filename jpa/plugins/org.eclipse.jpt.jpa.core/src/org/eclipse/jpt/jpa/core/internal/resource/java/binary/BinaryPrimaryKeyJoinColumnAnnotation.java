/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceModel;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.PrimaryKeyJoinColumnAnnotation;

/**
 * <code>javax.persistence.PrimaryKeyJoinColumn</code>
 */
public final class BinaryPrimaryKeyJoinColumnAnnotation
	extends BinaryNamedColumnAnnotation
	implements PrimaryKeyJoinColumnAnnotation
{
	private String referencedColumnName;


	public BinaryPrimaryKeyJoinColumnAnnotation(JavaResourceModel parent, IAnnotation jdtAnnotation) {
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


	// ********** BinaryNamedColumnAnnotation implementation **********

	@Override
	protected String getNameElementName() {
		return JPA.PRIMARY_KEY_JOIN_COLUMN__NAME;
	}

	@Override
	protected String getColumnDefinitionElementName() {
		return JPA.PRIMARY_KEY_JOIN_COLUMN__COLUMN_DEFINITION;
	}


	// ********** PrimaryKeyJoinColumnAnnotation implementation **********

	// ***** referenced column name
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
		return (String) this.getJdtMemberValue(JPA.PRIMARY_KEY_JOIN_COLUMN__REFERENCED_COLUMN_NAME);
	}

	public TextRange getReferencedColumnNameTextRange() {
		throw new UnsupportedOperationException();
	}

	public boolean referencedColumnNameTouches(int pos) {
		throw new UnsupportedOperationException();
	}
}

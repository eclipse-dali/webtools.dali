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
import org.eclipse.jpt.jpa.core.resource.java.BaseJoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JPA;

/**
 * <code><ul>
 * <li>javax.persistence.JoinColumn
 * <li>javax.persistence.MapKeyJoinColumn
 * </ul></code>
 */
public abstract class BinaryBaseJoinColumnAnnotation
	extends BinaryBaseColumnAnnotation
	implements BaseJoinColumnAnnotation
{
	private String referencedColumnName;


	public BinaryBaseJoinColumnAnnotation(JavaResourceModel parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.referencedColumnName = this.buildReferencedColumnName();
	}

	@Override
	public void update() {
		super.update();
		this.setReferencedColumnName_(this.buildReferencedColumnName());
	}

	protected abstract String getReferencedColumnNameElementName();


	//************ BaseJoinColumnAnnotation implementation ***************

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

	public TextRange getReferencedColumnNameTextRange() {
		throw new UnsupportedOperationException();
	}

	public boolean referencedColumnNameTouches(int pos) {
		throw new UnsupportedOperationException();
	}
}

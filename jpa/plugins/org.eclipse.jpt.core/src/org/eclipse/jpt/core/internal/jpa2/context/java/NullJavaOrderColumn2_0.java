/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.context.java.JavaNamedColumn;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaNamedColumn;
import org.eclipse.jpt.core.jpa2.context.java.JavaOrderColumn2_0;
import org.eclipse.jpt.core.jpa2.resource.java.OrderColumn2_0Annotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.utility.TextRange;


public class NullJavaOrderColumn2_0
	extends AbstractJavaNamedColumn<OrderColumn2_0Annotation>
	implements JavaOrderColumn2_0
{

	public NullJavaOrderColumn2_0(JavaJpaContextNode parent, JavaNamedColumn.Owner owner) {
		super(parent, owner);
	}

	public void initialize(JavaResourcePersistentAttribute resource) {
		//no-op
	}

	public void update(JavaResourcePersistentAttribute resource) {
		//no-op
	}

	public boolean isNullable() {
		return false;
	}

	public boolean isDefaultNullable() {
		return false;
	}

	public Boolean getSpecifiedNullable() {
		return null;
	}

	public void setSpecifiedNullable(Boolean newSpecifiedNullable) {
		throw new UnsupportedOperationException();
	}

	public boolean isInsertable() {
		return false;
	}

	public boolean isDefaultInsertable() {
		return false;
	}

	public Boolean getSpecifiedInsertable() {
		return null;
	}

	public void setSpecifiedInsertable(Boolean newSpecifiedInsertable) {
		throw new UnsupportedOperationException();
	}

	public boolean isUpdatable() {
		return false;
	}

	public boolean isDefaultUpdatable() {
		return false;
	}

	public Boolean getSpecifiedUpdatable() {
		return null;
	}

	public void setSpecifiedUpdatable(Boolean newSpecifiedUpdatable) {
		throw new UnsupportedOperationException();
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return null;
	}
}

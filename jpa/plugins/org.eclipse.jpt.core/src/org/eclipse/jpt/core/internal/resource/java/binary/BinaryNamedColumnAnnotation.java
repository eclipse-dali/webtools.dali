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
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.NamedColumnAnnotation;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * javax.persistence.Column
 * javax.persistence.JoinColumn
 * javax.persistence.DiscriminatorColumn
 * javax.persistence.PrimaryKeyJoinColumn.
 */
public abstract class BinaryNamedColumnAnnotation
	extends BinaryAnnotation
	implements NamedColumnAnnotation
{
	private String name;
	private String columnDefinition;


	protected BinaryNamedColumnAnnotation(JavaResourceNode parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.name = this.buildName();
		this.columnDefinition = this.buildColumnDefinition();
	}

	@Override
	public void update() {
		super.update();
		this.setName_(this.buildName());
		this.setColumnDefinition_(this.buildColumnDefinition());
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}


	// ********** NamedColumn implementation **********

	public boolean isSpecified() {
		return true;
	}

	// ***** name
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		throw new UnsupportedOperationException();
	}

	private void setName_(String name) {
		String old = this.name;
		this.name = name;
		this.firePropertyChanged(NAME_PROPERTY, old, name);
	}

	private String buildName() {
		return (String) this.getJdtMemberValue(this.getNameElementName());
	}

	protected abstract String getNameElementName();

	public TextRange getNameTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	public boolean nameTouches(int pos, CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	// ***** column definition
	public String getColumnDefinition() {
		return null;
	}

	public void setColumnDefinition(String columnDefinition) {
		throw new UnsupportedOperationException();
	}

	private void setColumnDefinition_(String columnDefinition) {
		String old = this.columnDefinition;
		this.columnDefinition = columnDefinition;
		this.firePropertyChanged(COLUMN_DEFINITION_PROPERTY, old, columnDefinition);
	}

	private String buildColumnDefinition() {
		return (String) this.getJdtMemberValue(this.getColumnDefinitionElementName());
	}

	protected abstract String getColumnDefinitionElementName();

	public TextRange getColumnDefinitionTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

}

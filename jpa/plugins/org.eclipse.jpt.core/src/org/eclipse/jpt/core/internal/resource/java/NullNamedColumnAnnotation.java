/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

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
public abstract class NullNamedColumnAnnotation
	extends NullAnnotation
	implements NamedColumnAnnotation
{
	protected NullNamedColumnAnnotation(JavaResourceNode parent) {
		super(parent);
	}

	public boolean isSpecified() {
		return false;
	}
	
	@Override
	protected NamedColumnAnnotation addAnnotation() {
		return (NamedColumnAnnotation) super.addAnnotation();
	}
	
	// ***** name
	public String getName() {
		return null;
	}

	public void setName(String name) {
		if (name != null) {
			this.addAnnotation().setName(name);
		}	
	}

	public TextRange getNameTextRange(CompilationUnit astRoot) {
		return null;
	}

	public boolean nameTouches(int pos, CompilationUnit astRoot) {
		return false;
	}

	// ***** column definition
	public String getColumnDefinition() {
		return null;
	}

	public void setColumnDefinition(String columnDefinition) {
		if (columnDefinition != null) {
			this.addAnnotation().setColumnDefinition(columnDefinition);
		}	
	}

	public TextRange getColumnDefinitionTextRange(CompilationUnit astRoot) {
		return null;
	}

}

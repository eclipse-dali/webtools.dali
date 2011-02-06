/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.jpa.core.resource.java.NamedColumnAnnotation;

/**
 * <ul>
 * <li><code>javax.persistence.Column</code>
 * <li><code>javax.persistence.JoinColumn</code>
 * <li><code>javax.persistence.MapKeyColumn</code>
 * <li><code>javax.persistence.DiscriminatorColumn</code>
 * <li><code>javax.persistence.OrderColumn</code>
 * <li><code>javax.persistence.PrimaryKeyJoinColumn</code>
 * </ul>
 */
public abstract class NullNamedColumnAnnotation<A extends NamedColumnAnnotation>
	extends NullAnnotation<A>
	implements NamedColumnAnnotation
{
	protected NullNamedColumnAnnotation(JavaResourceNode parent) {
		super(parent);
	}

	public boolean isSpecified() {
		return false;
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

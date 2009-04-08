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
import org.eclipse.jpt.core.resource.java.PrimaryKeyJoinColumnAnnotation;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * javax.persistence.PrimaryKeyJoinColumn
 */
public final class NullPrimaryKeyJoinColumnAnnotation
	extends NullNamedColumnAnnotation
	implements PrimaryKeyJoinColumnAnnotation
{	
	public NullPrimaryKeyJoinColumnAnnotation(JavaResourceNode parent) {
		super(parent);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	protected NamedColumnAnnotation buildAnnotation() {
		throw new UnsupportedOperationException();
	}

	// ***** referenced column name
	public String getReferencedColumnName() {
		return null;
	}

	public void setReferencedColumnName(String referencedColumnName) {
		throw new UnsupportedOperationException();
	}

	public TextRange getReferencedColumnNameTextRange(CompilationUnit astRoot) {
		return null;
	}
	
	public boolean referencedColumnNameTouches(int pos, CompilationUnit astRoot) {
		return false;
	}

}

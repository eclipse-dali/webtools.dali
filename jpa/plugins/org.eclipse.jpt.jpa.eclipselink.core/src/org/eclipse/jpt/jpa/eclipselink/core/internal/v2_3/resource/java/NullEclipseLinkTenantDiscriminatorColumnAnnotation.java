/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.v2_3.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.internal.resource.java.NullBaseDiscriminatorColumnAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.resource.java.EclipseLinkTenantDiscriminatorColumnAnnotation;

/**
 * <code>org.eclipse.persistence.annotations.TenantDiscriminatorColumn</code>
 */
public final class NullEclipseLinkTenantDiscriminatorColumnAnnotation
	extends NullBaseDiscriminatorColumnAnnotation<EclipseLinkTenantDiscriminatorColumnAnnotation>
	implements EclipseLinkTenantDiscriminatorColumnAnnotation
{	
	public NullEclipseLinkTenantDiscriminatorColumnAnnotation(JavaResourceAnnotatedElement parent) {
		super(parent);
	}
	
	public String getAnnotationName() {
		return EclipseLinkTenantDiscriminatorColumnAnnotation.ANNOTATION_NAME;
	}

	// ***** context property
	public String getContextProperty() {
		return null;
	}

	public void setContextProperty(String contextProperty) {
		if (contextProperty != null) {
			this.addAnnotation().setContextProperty(contextProperty);
		}
	}

	public TextRange getContextPropertyTextRange(CompilationUnit astRoot) {
		return null;
	}

	// ***** table
	public String getTable() {
		return null;
	}

	public void setTable(String table) {
		if (table != null) {
			this.addAnnotation().setTable(table);
		}
	}

	public TextRange getTableTextRange(CompilationUnit astRoot) {
		return null;
	}

	public boolean tableTouches(int pos, CompilationUnit astRoot) {
		return false;
	}

	// ***** primary key
	public Boolean getPrimaryKey() {
		return null;
	}

	public void setPrimaryKey(Boolean primaryKey) {
		if (primaryKey != null) {
			this.addAnnotation().setPrimaryKey(primaryKey);
		}
	}

	public TextRange getPrimaryKeyTextRange(CompilationUnit astRoot) {
		return null;
	}
}

/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.internal.resource.java.NullBaseDiscriminatorColumnAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkTenantDiscriminatorColumnAnnotation2_3;

/**
 * <code>org.eclipse.persistence.annotations.TenantDiscriminatorColumn</code>
 */
public final class NullEclipseLinkTenantDiscriminatorColumnAnnotation2_3
	extends NullBaseDiscriminatorColumnAnnotation<EclipseLinkTenantDiscriminatorColumnAnnotation2_3>
	implements EclipseLinkTenantDiscriminatorColumnAnnotation2_3
{	
	public NullEclipseLinkTenantDiscriminatorColumnAnnotation2_3(JavaResourceAnnotatedElement parent) {
		super(parent);
	}
	
	public String getAnnotationName() {
		return EclipseLinkTenantDiscriminatorColumnAnnotation2_3.ANNOTATION_NAME;
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

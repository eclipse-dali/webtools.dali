/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.v2_3.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.JavaEclipseLinkNonEmbeddableTypeMapping;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.context.EclipseLinkMultitenantType;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.context.TenantDiscriminatorColumn;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.context.java.JavaEclipseLinkMultitenancy;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.context.java.JavaTenantDiscriminatorColumn;

public class NullJavaEclipseLinkMultitenancy
	extends AbstractJavaJpaContextNode
	implements JavaEclipseLinkMultitenancy
{

	public NullJavaEclipseLinkMultitenancy(JavaEclipseLinkNonEmbeddableTypeMapping parent) {
		super(parent);
	}

	public EclipseLinkMultitenantType getType() {
		return null;
	}

	public EclipseLinkMultitenantType getSpecifiedType() {
		return null;
	}

	public void setSpecifiedType(EclipseLinkMultitenantType type) {
		throw new UnsupportedOperationException("Multitenancy is only supported in EclipseLink version 2.3 and higher"); //$NON-NLS-1$
	}

	public EclipseLinkMultitenantType getDefaultType() {
		return null;
	}

	public boolean isIncludeCriteria() {
		return false;
	}

	public Boolean getSpecifiedIncludeCriteria() {
		return null;
	}

	public void setSpecifiedIncludeCriteria(Boolean includeCriteria) {
		throw new UnsupportedOperationException("Multitenancy is only supported in EclipseLink version 2.3 and higher"); //$NON-NLS-1$
	}

	public boolean isDefaultIncludeCriteria() {
		return false;
	}

	public ListIterable<JavaTenantDiscriminatorColumn> getSpecifiedTenantDiscriminatorColumns() {
		return EmptyListIterable.instance();
	}

	public int getSpecifiedTenantDiscriminatorColumnsSize() {
		return 0;
	}

	public JavaTenantDiscriminatorColumn addSpecifiedTenantDiscriminatorColumn() {
		throw new UnsupportedOperationException("Multitenancy is only supported in EclipseLink version 2.3 and higher"); //$NON-NLS-1$
	}

	public JavaTenantDiscriminatorColumn addSpecifiedTenantDiscriminatorColumn(int index) {
		throw new UnsupportedOperationException("Multitenancy is only supported in EclipseLink version 2.3 and higher"); //$NON-NLS-1$
	}

	public void removeSpecifiedTenantDiscriminatorColumn(TenantDiscriminatorColumn tenantDiscriminatorColumn) {
		throw new UnsupportedOperationException("Multitenancy is only supported in EclipseLink version 2.3 and higher"); //$NON-NLS-1$
	}

	public void removeSpecifiedTenantDiscriminatorColumn(int index) {
		throw new UnsupportedOperationException("Multitenancy is only supported in EclipseLink version 2.3 and higher"); //$NON-NLS-1$
	}

	public void moveSpecifiedTenantDiscriminatorColumn(int targetIndex, int sourceIndex) {
		throw new UnsupportedOperationException("Multitenancy is only supported in EclipseLink version 2.3 and higher"); //$NON-NLS-1$
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException("Multitenancy is only supported in EclipseLink version 2.3 and higher"); //$NON-NLS-1$
	}
}

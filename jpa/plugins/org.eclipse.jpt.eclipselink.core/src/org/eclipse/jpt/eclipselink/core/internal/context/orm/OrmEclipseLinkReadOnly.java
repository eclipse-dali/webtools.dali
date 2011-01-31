/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.orm;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.core.resource.orm.XmlTypeMapping;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkReadOnly;
import org.eclipse.jpt.eclipselink.core.context.java.JavaEclipseLinkNonEmbeddableTypeMapping;
import org.eclipse.jpt.eclipselink.core.context.orm.OrmEclipseLinkNonEmbeddableTypeMapping;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlReadOnly;

public class OrmEclipseLinkReadOnly
	extends AbstractOrmXmlContextNode
	implements EclipseLinkReadOnly
{
	protected Boolean specifiedReadOnly;
	protected boolean defaultReadOnly;


	public OrmEclipseLinkReadOnly(OrmEclipseLinkNonEmbeddableTypeMapping parent) {
		super(parent);
		this.specifiedReadOnly = this.buildSpecifiedReadOnly();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setSpecifiedReadOnly_(this.buildSpecifiedReadOnly());
	}

	@Override
	public void update() {
		super.update();
		this.setDefaultReadOnly(this.buildDefaultReadOnly());
	}


	// ********** read-only **********

	public boolean isReadOnly() {
		return (this.specifiedReadOnly != null) ? this.specifiedReadOnly.booleanValue() : this.defaultReadOnly;
	}

	public Boolean getSpecifiedReadOnly() {
		return this.specifiedReadOnly;
	}

	public void setSpecifiedReadOnly(Boolean readOnly) {
		this.setSpecifiedReadOnly_(readOnly);
		this.getXmlReadOnly().setReadOnly(readOnly);
	}

	protected void setSpecifiedReadOnly_(Boolean readOnly) {
		Boolean old = this.specifiedReadOnly;
		this.specifiedReadOnly = readOnly;
		this.firePropertyChanged(SPECIFIED_READ_ONLY_PROPERTY, old, readOnly);
	}

	protected Boolean buildSpecifiedReadOnly() {
		return this.getXmlReadOnly().getReadOnly();
	}

	public boolean isDefaultReadOnly() {
		return this.defaultReadOnly;
	}

	protected void setDefaultReadOnly(boolean readOnly) {
		boolean old = this.defaultReadOnly;
		this.defaultReadOnly = readOnly;
		this.firePropertyChanged(DEFAULT_READ_ONLY_PROPERTY, old, readOnly);
	}

	protected boolean buildDefaultReadOnly() {
		EclipseLinkReadOnly javaReadOnly = this.getJavaReadOnlyForDefaults();
		return (javaReadOnly != null) ? javaReadOnly.isReadOnly() : DEFAULT_READ_ONLY;
	}


	// ********** misc **********

	@Override
	public OrmEclipseLinkNonEmbeddableTypeMapping getParent() {
		return (OrmEclipseLinkNonEmbeddableTypeMapping) super.getParent();
	}

	protected OrmEclipseLinkNonEmbeddableTypeMapping getTypeMapping() {
		return this.getParent();
	}

	protected XmlTypeMapping getXmlTypeMapping() {
		return this.getTypeMapping().getXmlTypeMapping();
	}

	protected XmlReadOnly getXmlReadOnly() {
		return (XmlReadOnly) this.getXmlTypeMapping();
	}

	protected JavaEclipseLinkNonEmbeddableTypeMapping getJavaTypeMappingForDefaults() {
		return this.getTypeMapping().getJavaTypeMappingForDefaults();
	}

	protected EclipseLinkReadOnly getJavaReadOnlyForDefaults() {
		JavaEclipseLinkNonEmbeddableTypeMapping javaTypeMapping = this.getJavaTypeMappingForDefaults();
		return (javaTypeMapping == null) ? null : javaTypeMapping.getReadOnly();
	}


	// ********** validation **********

	public TextRange getValidationTextRange() {
		return this.getXmlReadOnly().getReadOnlyTextRange();
	}
}

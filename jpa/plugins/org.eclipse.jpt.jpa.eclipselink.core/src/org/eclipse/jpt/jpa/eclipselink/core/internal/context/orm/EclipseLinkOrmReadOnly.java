/*******************************************************************************
 * Copyright (c) 2008, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmXmlContextModel;
import org.eclipse.jpt.jpa.core.resource.orm.XmlTypeMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkReadOnly;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaNonEmbeddableTypeMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmNonEmbeddableTypeMapping;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlReadOnly;

public class EclipseLinkOrmReadOnly
	extends AbstractOrmXmlContextModel<EclipseLinkOrmNonEmbeddableTypeMapping>
	implements EclipseLinkReadOnly
{
	protected Boolean specifiedReadOnly;
	protected boolean defaultReadOnly;


	public EclipseLinkOrmReadOnly(EclipseLinkOrmNonEmbeddableTypeMapping parent) {
		super(parent);
		this.specifiedReadOnly = this.buildSpecifiedReadOnly();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.setSpecifiedReadOnly_(this.buildSpecifiedReadOnly());
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
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

	protected EclipseLinkOrmNonEmbeddableTypeMapping getTypeMapping() {
		return this.parent;
	}

	protected XmlTypeMapping getXmlTypeMapping() {
		return this.getTypeMapping().getXmlTypeMapping();
	}

	protected XmlReadOnly getXmlReadOnly() {
		return (XmlReadOnly) this.getXmlTypeMapping();
	}

	protected EclipseLinkJavaNonEmbeddableTypeMapping getJavaTypeMappingForDefaults() {
		return this.getTypeMapping().getJavaTypeMappingForDefaults();
	}

	protected EclipseLinkReadOnly getJavaReadOnlyForDefaults() {
		EclipseLinkJavaNonEmbeddableTypeMapping javaTypeMapping = this.getJavaTypeMappingForDefaults();
		return (javaTypeMapping == null) ? null : javaTypeMapping.getReadOnly();
	}


	// ********** validation **********

	public TextRange getValidationTextRange() {
		TextRange textRange = this.getXmlValidationTextRange();
		return (textRange != null) ? textRange : this.getTypeMapping().getValidationTextRange();
	}

	protected TextRange getXmlValidationTextRange() {
		return this.getXmlReadOnly().getReadOnlyTextRange();
	}
}

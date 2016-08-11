/*******************************************************************************
 * Copyright (c) 2008, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmXmlContextModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkMutable;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaPersistentAttribute;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlMutable;

public class EclipseLinkOrmMutable
	extends AbstractOrmXmlContextModel<OrmAttributeMapping>
	implements EclipseLinkMutable
{
	protected Boolean specifiedMutable;
	protected boolean defaultMutable;


	public EclipseLinkOrmMutable(OrmAttributeMapping parent) {
		super(parent);
		this.specifiedMutable = this.buildSpecifiedMutable();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.setSpecifiedMutable_(this.buildSpecifiedMutable());
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.setDefaultMutable(this.buildDefaultMutable());
	}


	// ********** mutable **********

	public boolean isMutable() {
		return (this.specifiedMutable != null) ? this.specifiedMutable.booleanValue() : this.defaultMutable;
	}

	public Boolean getSpecifiedMutable() {
		return this.specifiedMutable;
	}

	public void setSpecifiedMutable(Boolean mutable) {
		this.setSpecifiedMutable_(mutable);
		this.getXmlMutable().setMutable(mutable);
	}

	protected void setSpecifiedMutable_(Boolean mutable) {
		Boolean old = this.specifiedMutable;
		this.specifiedMutable = mutable;
		this.firePropertyChanged(SPECIFIED_MUTABLE_PROPERTY, old, mutable);
	}

	protected Boolean buildSpecifiedMutable() {
		return this.getXmlMutable().getMutable();
	}

	public boolean getDefaultMutable() {
		return this.defaultMutable;
	}

	protected void setDefaultMutable(boolean mutable) {
		boolean old = this.defaultMutable;
		this.defaultMutable = mutable;
		this.firePropertyChanged(DEFAULT_MUTABLE_PROPERTY, old, mutable);
	}

	protected boolean buildDefaultMutable() {
		EclipseLinkJavaPersistentAttribute javaAttribute = this.getJavaPersistentAttribute();
		if (javaAttribute == null) {
			return false;
		}
		if (javaAttribute.typeIsDateOrCalendar()) {
			Boolean puTemporalMutable = this.getPersistenceUnit().getEclipseLinkOptions().getTemporalMutable();
			return (puTemporalMutable == null) ? false : puTemporalMutable.booleanValue();
		}
		return javaAttribute.typeIsSerializable();
	}


	// ********** misc **********

	protected OrmAttributeMapping getAttributeMapping() {
		return this.parent;
	}

	protected XmlMutable getXmlMutable() {
		return (XmlMutable) this.getAttributeMapping().getXmlAttributeMapping();
	}

	protected EclipseLinkJavaPersistentAttribute getJavaPersistentAttribute() {
		return (EclipseLinkJavaPersistentAttribute) this.getAttributeMapping().getPersistentAttribute().getJavaPersistentAttribute();
	}

	@Override
	public EclipseLinkPersistenceUnit getPersistenceUnit() {
		return (EclipseLinkPersistenceUnit) super.getPersistenceUnit();
	}


	// ********** validation **********

	public TextRange getValidationTextRange() {
		TextRange textRange = this.getXmlValidationTextRange();
		return (textRange != null) ? textRange : this.getAttributeMapping().getValidationTextRange();
	}

	protected TextRange getXmlValidationTextRange() {
		return this.getXmlMutable().getMutableTextRange();
	}
}

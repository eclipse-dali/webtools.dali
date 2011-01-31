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
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkMutable;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkPersistentAttribute;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlMutable;

public class OrmEclipseLinkMutable
	extends AbstractOrmXmlContextNode
	implements EclipseLinkMutable
{
	protected Boolean specifiedMutable;
	protected boolean defaultMutable;


	public OrmEclipseLinkMutable(OrmAttributeMapping parent) {
		super(parent);
		this.specifiedMutable = this.buildSpecifiedMutable();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setSpecifiedMutable_(this.buildSpecifiedMutable());
	}

	@Override
	public void update() {
		super.update();
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

	public boolean isDefaultMutable() {
		return this.defaultMutable;
	}

	protected void setDefaultMutable(boolean mutable) {
		boolean old = this.defaultMutable;
		this.defaultMutable = mutable;
		this.firePropertyChanged(DEFAULT_MUTABLE_PROPERTY, old, mutable);
	}

	protected boolean buildDefaultMutable() {
		JavaEclipseLinkPersistentAttribute javaAttribute = this.getJavaPersistentAttribute();
		if (javaAttribute == null) {
			return false;
		}
		if (javaAttribute.typeIsDateOrCalendar()) {
			Boolean puTemporalMutable = this.getPersistenceUnit().getOptions().getTemporalMutable();
			return (puTemporalMutable == null) ? false : puTemporalMutable.booleanValue();
		}
		return javaAttribute.typeIsSerializable();
	}


	// ********** misc **********

	@Override
	public OrmAttributeMapping getParent() {
		return (OrmAttributeMapping) super.getParent();
	}

	protected OrmAttributeMapping getAttributeMapping() {
		return this.getParent();
	}

	protected XmlMutable getXmlMutable() {
		return (XmlMutable) this.getAttributeMapping().getXmlAttributeMapping();
	}

	protected JavaEclipseLinkPersistentAttribute getJavaPersistentAttribute() {
		return (JavaEclipseLinkPersistentAttribute) this.getAttributeMapping().getPersistentAttribute().getJavaPersistentAttribute();
	}

	@Override
	public EclipseLinkPersistenceUnit getPersistenceUnit() {
		return (EclipseLinkPersistenceUnit) super.getPersistenceUnit();
	}


	// ********** validation **********

	public TextRange getValidationTextRange() {
		return this.getXmlMutable().getMutableTextRange();
	}
}

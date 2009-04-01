/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.orm;

import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.internal.context.AbstractXmlContextNode;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.context.Mutable;
import org.eclipse.jpt.eclipselink.core.internal.context.java.AbstractEclipseLinkJavaPersistentAttribute;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlMutable;

public class EclipseLinkOrmMutable extends AbstractXmlContextNode 
	implements Mutable
{
	protected final XmlMutable resource;
	
	protected boolean defaultMutable;
	
	protected Boolean specifiedMutable;
	
	
	public EclipseLinkOrmMutable(OrmAttributeMapping parent, XmlMutable resource) {
		super(parent);
		this.resource = resource;
		this.defaultMutable = this.calculateDefaultMutable();
		this.specifiedMutable = this.getResourceMutable();
	}
		
	@Override
	public EclipseLinkPersistenceUnit getPersistenceUnit() {
		return (EclipseLinkPersistenceUnit) super.getPersistenceUnit();
	}
	
	protected OrmAttributeMapping getAttributeMapping() {
		return (OrmAttributeMapping) getParent();
	}
	
	public boolean isMutable() {
		return (this.specifiedMutable != null) ? this.specifiedMutable.booleanValue() : this.defaultMutable;
	}
	
	public boolean isDefaultMutable() {
		return this.defaultMutable;
	}
	
	protected void setDefaultMutable(boolean newDefaultMutable) {
		boolean oldDefaultMutable = this.defaultMutable;
		this.defaultMutable = newDefaultMutable;
		firePropertyChanged(DEFAULT_MUTABLE_PROPERTY, oldDefaultMutable, newDefaultMutable);
	}
	
	public Boolean getSpecifiedMutable() {
		return this.specifiedMutable;
	}
	
	public void setSpecifiedMutable(Boolean newSpecifiedMutable) {
		Boolean oldSpecifiedMutable = this.specifiedMutable;
		this.specifiedMutable = newSpecifiedMutable;
		this.resource.setMutable(newSpecifiedMutable);
		firePropertyChanged(SPECIFIED_MUTABLE_PROPERTY, oldSpecifiedMutable, newSpecifiedMutable);
	}
	
	protected void setSpecifiedMutable_(Boolean newSpecifiedMutable) {
		Boolean oldSpecifiedMutable = this.specifiedMutable;
		this.specifiedMutable = newSpecifiedMutable;
		firePropertyChanged(SPECIFIED_MUTABLE_PROPERTY, oldSpecifiedMutable, newSpecifiedMutable);
	}
	
	
	// **************** initialize/update **************************************
		
	protected void update() {
		setDefaultMutable(this.calculateDefaultMutable());
		setSpecifiedMutable_(this.getResourceMutable());
	}
	
	protected Boolean getResourceMutable() {
		return this.resource.getMutable();
	}
	
	protected boolean calculateDefaultMutable() {
		AbstractEclipseLinkJavaPersistentAttribute javaAttribute = (AbstractEclipseLinkJavaPersistentAttribute) this.getAttributeMapping().getPersistentAttribute().getJavaPersistentAttribute();
		if (javaAttribute == null) {
			return false;
		}
		if (javaAttribute.typeIsDateOrCalendar()) {
			Boolean persistenceUnitDefaultMutable = getPersistenceUnit().getOptions().getTemporalMutable();
			return persistenceUnitDefaultMutable == null ? false : persistenceUnitDefaultMutable.booleanValue();
		}
		return javaAttribute.typeIsSerializable();
	}
	
	
	// **************** validation **************************************
	
	public TextRange getValidationTextRange() {
		return this.resource.getMutableTextRange();
	}
}

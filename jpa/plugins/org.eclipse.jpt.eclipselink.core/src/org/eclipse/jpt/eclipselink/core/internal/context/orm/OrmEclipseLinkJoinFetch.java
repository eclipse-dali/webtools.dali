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
import org.eclipse.jpt.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkJoinFetch;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkJoinFetchType;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlJoinFetch;

public class OrmEclipseLinkJoinFetch
	extends AbstractOrmXmlContextNode
	implements EclipseLinkJoinFetch
{
	protected EclipseLinkJoinFetchType value;


	public OrmEclipseLinkJoinFetch(OrmAttributeMapping parent) {
		super(parent);
		this.value = this.buildValue();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setValue_(this.buildValue());
	}


	// ********** value **********

	public EclipseLinkJoinFetchType getValue() {
		return this.value;
	}

	public void setValue(EclipseLinkJoinFetchType value) {
		this.setValue_(value);
		this.getXmlJoinFetch().setJoinFetch(EclipseLinkJoinFetchType.toOrmResourceModel(value));
	}

	protected void setValue_(EclipseLinkJoinFetchType value) {
		EclipseLinkJoinFetchType old = this.value;
		this.value = value;
		this.firePropertyChanged(VALUE_PROPERTY, old, value);
	}

	protected EclipseLinkJoinFetchType buildValue() {
		return EclipseLinkJoinFetchType.fromOrmResourceModel(this.getXmlJoinFetch().getJoinFetch());
	}


	// ********** misc **********

	@Override
	public OrmAttributeMapping getParent() {
		return (OrmAttributeMapping) super.getParent();
	}

	protected OrmAttributeMapping getAttributeMapping() {
		return this.getParent();
	}

	protected XmlAttributeMapping getXmlAttributeMapping() {
		return this.getAttributeMapping().getXmlAttributeMapping();
	}

	protected XmlJoinFetch getXmlJoinFetch() {
		return (XmlJoinFetch) this.getXmlAttributeMapping();
	}


	// ********** validation **********

	public TextRange getValidationTextRange() {
		return this.getXmlJoinFetch().getJoinFetchTextRange();
	}
}

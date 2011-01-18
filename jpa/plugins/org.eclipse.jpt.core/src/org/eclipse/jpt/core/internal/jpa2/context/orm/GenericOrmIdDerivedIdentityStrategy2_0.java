/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.orm;

import java.util.List;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.core.jpa2.context.orm.OrmDerivedIdentity2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmIdDerivedIdentityStrategy2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmSingleRelationshipMapping2_0;
import org.eclipse.jpt.core.resource.orm.v2_0.XmlSingleRelationshipMapping_2_0;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericOrmIdDerivedIdentityStrategy2_0
	extends AbstractOrmXmlContextNode
	implements OrmIdDerivedIdentityStrategy2_0
{
	protected boolean value;


	public GenericOrmIdDerivedIdentityStrategy2_0(OrmDerivedIdentity2_0 parent) {
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

	public boolean getValue() {
		return this.value;
	}

	public void setValue(boolean value) {
		this.setValue_(value);
		this.getXmlMapping().setId(value ? Boolean.TRUE : null);
	}

	protected void setValue_(boolean value) {
		boolean old = this.value;
		this.value = value;
		this.firePropertyChanged(VALUE_PROPERTY, old, value);
	}

	protected boolean buildValue() {
		Boolean xmlValue = this.getXmlMapping().getId();
		return (xmlValue != null) && xmlValue.booleanValue();
	}


	// ********** misc **********

	@Override
	public OrmDerivedIdentity2_0 getParent() {
		return (OrmDerivedIdentity2_0) super.getParent();
	}

	protected OrmDerivedIdentity2_0 getDerivedIdentity() {
		return this.getParent();
	}

	protected OrmSingleRelationshipMapping2_0 getMapping() {
		return this.getDerivedIdentity().getMapping();
	}

	protected XmlSingleRelationshipMapping_2_0 getXmlMapping() {
		return this.getMapping().getXmlAttributeMapping();
	}

	public boolean isSpecified() {
		return this.value;
	}

	public void addStrategy() {
		this.setValue(true);
	}

	public void removeStrategy() {
		this.setValue(false);
	}

	public void initializeFrom(OrmIdDerivedIdentityStrategy2_0 oldStrategy) {
		this.setValue(oldStrategy.getValue());
	}


	// ********** validation **********

	public TextRange getValidationTextRange() {
		return this.getXmlMapping().getIdTextRange();
	}

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		// no validation rules
	}
}

/*******************************************************************************
 * Copyright (c) 2009, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.context.orm;

import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmXmlContextModel;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmDerivedIdentity2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmIdDerivedIdentityStrategy2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmSingleRelationshipMapping2_0;
import org.eclipse.jpt.jpa.core.resource.orm.v2_0.XmlSingleRelationshipMapping_2_0;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericOrmIdDerivedIdentityStrategy2_0
	extends AbstractOrmXmlContextModel<OrmDerivedIdentity2_0>
	implements OrmIdDerivedIdentityStrategy2_0
{
	protected boolean value;


	public GenericOrmIdDerivedIdentityStrategy2_0(OrmDerivedIdentity2_0 parent) {
		super(parent);
		this.value = this.buildValue();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
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

	protected OrmDerivedIdentity2_0 getDerivedIdentity() {
		return this.parent;
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

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		// no validation rules
	}

	public TextRange getValidationTextRange() {
		TextRange textRange = this.getXmlMapping().getIdTextRange();
		return (textRange != null) ? textRange : this.getDerivedIdentity().getValidationTextRange();
	}
}

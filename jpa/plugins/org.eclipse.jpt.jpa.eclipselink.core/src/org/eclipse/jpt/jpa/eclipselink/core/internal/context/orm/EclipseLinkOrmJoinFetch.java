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
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmXmlContextModel;
import org.eclipse.jpt.jpa.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkJoinFetch;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkJoinFetchType;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlJoinFetch;

public class EclipseLinkOrmJoinFetch
	extends AbstractOrmXmlContextModel<OrmAttributeMapping>
	implements EclipseLinkJoinFetch
{
	protected EclipseLinkJoinFetchType value;


	public EclipseLinkOrmJoinFetch(OrmAttributeMapping parent) {
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

	protected OrmAttributeMapping getAttributeMapping() {
		return this.parent;
	}

	protected XmlAttributeMapping getXmlAttributeMapping() {
		return this.getAttributeMapping().getXmlAttributeMapping();
	}

	protected XmlJoinFetch getXmlJoinFetch() {
		return (XmlJoinFetch) this.getXmlAttributeMapping();
	}


	// ********** validation **********

	public TextRange getValidationTextRange() {
		TextRange textRange = this.getXmlValidationTextRange();
		return (textRange != null) ? textRange : this.getAttributeMapping().getValidationTextRange();
	}

	protected TextRange getXmlValidationTextRange() {
		return this.getXmlJoinFetch().getJoinFetchTextRange();
	}
}

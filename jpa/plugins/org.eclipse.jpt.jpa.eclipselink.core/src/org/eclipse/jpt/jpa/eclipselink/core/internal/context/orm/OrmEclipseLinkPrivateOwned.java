/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmXmlContextModel;
import org.eclipse.jpt.jpa.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkPrivateOwned;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPrivateOwned;

public class OrmEclipseLinkPrivateOwned
	extends AbstractOrmXmlContextModel<OrmAttributeMapping>
	implements EclipseLinkPrivateOwned
{
	protected boolean privateOwned;


	public OrmEclipseLinkPrivateOwned(OrmAttributeMapping parent) {
		super(parent);
		this.privateOwned = this.buildPrivateOwned();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setPrivateOwned_(this.buildPrivateOwned());
	}


	// ********** private owned **********

	public boolean isPrivateOwned() {
		return this.privateOwned;
	}

	public void setPrivateOwned(boolean privateOwned) {
		this.setPrivateOwned_(privateOwned);
		this.getXmlPrivateOwned().setPrivateOwned(privateOwned);
	}

	protected void setPrivateOwned_(boolean privateOwned) {
		boolean old = this.privateOwned;
		this.privateOwned = privateOwned;
		this.firePropertyChanged(PRIVATE_OWNED_PROPERTY, old, privateOwned);
	}

	protected boolean buildPrivateOwned() {
		return this.getXmlPrivateOwned().isPrivateOwned();
	}


	// ********** misc **********

	protected OrmAttributeMapping getAttributeMapping() {
		return this.parent;
	}

	protected XmlAttributeMapping getXmlAttributeMapping() {
		return this.getAttributeMapping().getXmlAttributeMapping();
	}

	protected XmlPrivateOwned getXmlPrivateOwned() {
		return (XmlPrivateOwned) this.getXmlAttributeMapping();
	}


	// ********** validation **********

	public TextRange getValidationTextRange() {
		TextRange textRange = this.getXmlValidationTextRange();
		return (textRange != null) ? textRange : this.getAttributeMapping().getValidationTextRange();
	}

	protected TextRange getXmlValidationTextRange() {
		return this.getXmlPrivateOwned().getPrivateOwnedTextRange();
	}
}

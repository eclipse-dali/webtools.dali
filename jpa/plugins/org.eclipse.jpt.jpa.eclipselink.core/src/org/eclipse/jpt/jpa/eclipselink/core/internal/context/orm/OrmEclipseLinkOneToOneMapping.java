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

import java.util.List;
import org.eclipse.jpt.common.utility.internal.iterable.CompositeIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmOneToOneMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkJoinFetch;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkOneToOneMapping2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkPrivateOwned;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlOneToOne;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class OrmEclipseLinkOneToOneMapping
	extends AbstractOrmOneToOneMapping<XmlOneToOne>
	implements EclipseLinkOneToOneMapping2_0
{
	protected final OrmEclipseLinkPrivateOwned privateOwned;

	protected final OrmEclipseLinkJoinFetch joinFetch;


	protected OrmEclipseLinkOneToOneMapping(OrmPersistentAttribute parent, XmlOneToOne xmlMapping) {
		super(parent, xmlMapping);
		this.privateOwned = new OrmEclipseLinkPrivateOwned(this);
		this.joinFetch = new OrmEclipseLinkJoinFetch(this);
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.privateOwned.synchronizeWithResourceModel();
		this.joinFetch.synchronizeWithResourceModel();
	}

	@Override
	public void update() {
		super.update();
		this.privateOwned.update();
		this.joinFetch.update();
	}


	// ********** private owned **********

	public EclipseLinkPrivateOwned getPrivateOwned() {
		return this.privateOwned;
	}


	// ********** join fetch **********

	public EclipseLinkJoinFetch getJoinFetch() {
		return this.joinFetch;
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		// TODO - private owned, join fetch validation
	}

	// ********** completion proposals **********

	@Override
	@SuppressWarnings("unchecked")
	protected Iterable<String> getCandidateTargetEntityClassNames() {
		return new CompositeIterable<String>(
				super.getCandidateTargetEntityClassNames(),
				IterableTools.sort(((EclipseLinkPersistenceUnit) this.getPersistenceUnit()).getEclipseLinkDynamicPersistentTypeNames())
				);
	}
}

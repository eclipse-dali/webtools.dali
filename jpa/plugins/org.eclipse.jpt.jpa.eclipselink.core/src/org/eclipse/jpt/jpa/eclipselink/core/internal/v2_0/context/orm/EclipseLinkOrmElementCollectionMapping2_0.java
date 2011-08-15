/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.v2_0.context.orm;

import java.util.List;
import org.eclipse.jpt.common.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.jpa.core.context.orm.OrmConverter;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.orm.AbstractOrmElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.eclipselink.core.JptJpaEclipseLinkCorePlugin;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkJoinFetch;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.OrmEclipseLinkConvert;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.OrmEclipseLinkJoinFetch;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlElementCollection;
import org.eclipse.jpt.jpa.eclipselink.core.v2_0.context.EclipseLinkElementCollectionMapping2_0;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class EclipseLinkOrmElementCollectionMapping2_0
	extends AbstractOrmElementCollectionMapping2_0<XmlElementCollection>
	implements EclipseLinkElementCollectionMapping2_0
{
	protected final OrmEclipseLinkJoinFetch joinFetch;


	public EclipseLinkOrmElementCollectionMapping2_0(OrmPersistentAttribute parent, XmlElementCollection resourceMapping) {
		super(parent, resourceMapping);
		this.joinFetch = new OrmEclipseLinkJoinFetch(this);
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.joinFetch.synchronizeWithResourceModel();
	}

	@Override
	public void update() {
		super.update();
		this.joinFetch.update();
	}


	// ********** join fetch **********

	public EclipseLinkJoinFetch getJoinFetch() {
		if (getJpaPlatformVersion().getVersion().equals(JptJpaEclipseLinkCorePlugin.ECLIPSELINK_PLATFORM_VERSION_2_0)) {
			throw new UnsupportedOperationException("join-fetch not supported in EclipseLink 2.0 platform"); //$NON-NLS-1$
		}
		return this.joinFetch;
	}

	// ********** converter adapters **********

	/**
	 * put the EclipseLink convert adapter first - this is the order EclipseLink searches
	 */
	@Override
	protected Iterable<OrmConverter.Adapter> getConverterAdapters() {
		return new CompositeIterable<OrmConverter.Adapter>(
				OrmEclipseLinkConvert.Adapter.instance(),
				super.getConverterAdapters()
			);
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		// TODO join fetch validation
	}
}

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

import java.util.List;
import org.eclipse.jpt.core.context.orm.OrmXml;
import org.eclipse.jpt.core.internal.context.orm.AbstractEntityMappings;
import org.eclipse.jpt.eclipselink.core.context.orm.EclipseLinkConverterHolder;
import org.eclipse.jpt.eclipselink.core.context.orm.EclipseLinkEntityMappings;
import org.eclipse.jpt.eclipselink.core.internal.JptEclipseLinkCorePlugin;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLink;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.eclipselink.core.resource.orm.v1_1.EclipseLink1_1;
import org.eclipse.jpt.eclipselink.core.resource.orm.v2_0.EclipseLink2_0;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class EclipseLinkEntityMappingsImpl
	extends AbstractEntityMappings
	implements EclipseLinkEntityMappings
{

	protected final OrmEclipseLinkConverterHolder converterHolder;
	
	public EclipseLinkEntityMappingsImpl(OrmXml parent, XmlEntityMappings resource) {
		super(parent, resource);
		this.converterHolder = new OrmEclipseLinkConverterHolder(this, (XmlEntityMappings) this.xmlEntityMappings);
	}
	
	// **************** EclipseLinkEntityMappings impl **********************************

	public EclipseLinkConverterHolder getConverterHolder() {
		return this.converterHolder;
	}
	
	
	@Override
	public void update() {
		super.update();
		this.converterHolder.update();
	}
	
	// **************** validation *********************************************
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.converterHolder.validate(messages, reporter);
	}
	
	@Override
	protected String latestDocumentVersion() {
		String jpaPlatformVersion = getJpaPlatform().getJpaVersion().getVersion();
		if (jpaPlatformVersion.equals(JptEclipseLinkCorePlugin.ECLIPSELINK_PLATFORM_VERSION_1_0)) {
			return EclipseLink.SCHEMA_VERSION;
		}
		else if (jpaPlatformVersion.equals(JptEclipseLinkCorePlugin.ECLIPSELINK_PLATFORM_VERSION_1_1)) {
			return EclipseLink1_1.SCHEMA_VERSION;
		}
		else if (jpaPlatformVersion.equals(JptEclipseLinkCorePlugin.ECLIPSELINK_PLATFORM_VERSION_2_0)) {
			return EclipseLink2_0.SCHEMA_VERSION;
		}
		else {
			throw new IllegalStateException("Platform version not recognized.");
		}
	}
}

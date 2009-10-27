/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.orm;

import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.orm.OrmXml;
import org.eclipse.jpt.core.internal.context.orm.AbstractEntityMappings;
import org.eclipse.jpt.core.resource.orm.JPA;
import org.eclipse.jpt.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.core.resource.orm.v2_0.JPA2_0;

public class GenericEntityMappings
	extends AbstractEntityMappings
{
	public GenericEntityMappings(OrmXml parent, XmlEntityMappings resource) {
		super(parent, resource);
	}
	
	
	@Override
	protected String latestDocumentVersion() {
		String jpaPlatformVersion = getJpaPlatform().getJpaVersion().getJpaVersion();
		if (jpaPlatformVersion.equals(JptCorePlugin.JPA_FACET_VERSION_1_0)) {
			return JPA.SCHEMA_VERSION;
		}
		else if (jpaPlatformVersion.equals(JptCorePlugin.JPA_FACET_VERSION_2_0)) {
			return JPA2_0.SCHEMA_VERSION;
		}
		else {
			throw new IllegalStateException("Platform version not recognized.");
		}
	}
}

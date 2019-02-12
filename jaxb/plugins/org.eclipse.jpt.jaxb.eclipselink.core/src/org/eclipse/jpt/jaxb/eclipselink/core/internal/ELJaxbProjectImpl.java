/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal;

import org.eclipse.jpt.common.core.resource.xml.JptXmlResource;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jaxb.core.JaxbFile;
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.core.internal.AbstractJaxbProject;
import org.eclipse.jpt.jaxb.eclipselink.core.ELJaxbProject;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.Oxm;

public class ELJaxbProjectImpl
		extends AbstractJaxbProject
		implements ELJaxbProject {
	
	public ELJaxbProjectImpl(JaxbProject.Config config) {
		super(config);
	}
	
	
	public Iterable<JptXmlResource> getOxmResources() {
		return IterableTools.downCast(IterableTools.transform(getJaxbFiles(Oxm.CONTENT_TYPE), JaxbFile.RESOURCE_MODEL_TRANSFORMER));
	}
}

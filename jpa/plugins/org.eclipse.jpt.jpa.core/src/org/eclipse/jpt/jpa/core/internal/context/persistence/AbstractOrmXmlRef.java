/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.persistence;

import org.eclipse.core.runtime.Path;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.core.resource.xml.JptXmlResource;
import org.eclipse.jpt.jpa.core.context.orm.OrmXml;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;

/**
 * <code>persistence.xml</code> file
 * <br>
 * <code>mapping-file</code> element (when it refers to an
 * <code>orm.xml</code> file)
 */
public abstract class AbstractOrmXmlRef
	extends AbstractMappingFileRef<OrmXml>
{
	protected AbstractOrmXmlRef(PersistenceUnit parent, String fileName) {
		super(parent, fileName);
	}

	@Override
	protected OrmXml buildMappingFile() {
		JptXmlResource xmlResource = this.resolveResourceMappingFile();
		return (xmlResource == null) ? null : this.buildMappingFile(xmlResource);
	}

	/**
	 * The mapping file ref resource is in the persistence XML resource
	 * (<code>persistence.xml</code>). This returns the resource of
	 * the mapping file itself (<code>orm.xml</code>).
	 */
	@Override
	protected JptXmlResource resolveResourceMappingFile() {
		if (this.fileName == null) {
			return null;
		}
		JptXmlResource xmlResource = this.getJpaProject().getMappingFileXmlResource(new Path(this.fileName));
		if (xmlResource == null) {
			return null;
		}
		if (xmlResource.isReverting()) {
			// 308254 - this can happen when orm.xml is closed without saving;
			// the model is completely whacked in another thread - so wipe our model(?)
			return null;
		}
		JptResourceType resourceType = xmlResource.getResourceType();
		if (resourceType == null) {
			return null;
		}
		if ( ! this.getJpaPlatform().supportsResourceType(resourceType)) {
			return null;
		}
		return xmlResource;
	}

	@Override
	protected boolean mappingFileContentIsUnsupported() {
		JptXmlResource xmlResource = this.getJpaProject().getMappingFileXmlResource(new Path(this.fileName));
		return (xmlResource != null) && ! this.getJpaPlatform().supportsResourceType(xmlResource.getResourceType());
	}
}

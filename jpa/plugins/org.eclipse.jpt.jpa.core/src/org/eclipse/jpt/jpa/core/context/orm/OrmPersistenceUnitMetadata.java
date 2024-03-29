/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.orm;

import org.eclipse.jpt.jpa.core.context.MappingFilePersistenceUnitMetadata;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.core.resource.orm.XmlPersistenceUnitMetadata;

/**
 * Context <code>orm.xml</code> persistence unit metadata.
 * Context model corresponding to the
 * XML resource model {@link XmlPersistenceUnitMetadata},
 * which corresponds to the <code>persistence-unit-metadata</code> element
 * in the <code>orm.xml</code> file.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface OrmPersistenceUnitMetadata
	extends MappingFilePersistenceUnitMetadata
{
	/**
	 * Covariant override.
	 */
	EntityMappings getParent();

	/**
	 * This can be <code>null</code>. The context metadata is always present,
	 * even if the XML metadata is missing.
	 */
	XmlEntityMappings getXmlEntityMappings();
	void setXmlMappingMetadataComplete(boolean xmlMappingMetadataComplete);
		String XML_MAPPING_METADATA_COMPLETE_PROPERTY = "xmlMappingMetadataComplete"; //$NON-NLS-1$

	OrmPersistenceUnitDefaults getPersistenceUnitDefaults();

	XmlPersistenceUnitMetadata getXmlPersistenceUnitMetadata();
	XmlPersistenceUnitMetadata getXmlPersistenceUnitMetadataForUpdate();
	void removeXmlPersistenceUnitMetadataIfUnset();
}

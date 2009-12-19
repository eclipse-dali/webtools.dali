/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.orm;

import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.core.resource.orm.XmlPersistenceUnitMetadata;

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
public interface PersistenceUnitMetadata
	extends XmlContextNode
{
	EntityMappings getParent();

	XmlEntityMappings getXmlEntityMappings();

	boolean isXmlMappingMetadataComplete();
	void setXmlMappingMetadataComplete(boolean value);
		String XML_MAPPING_METADATA_COMPLETE_PROPERTY = "xmlMappingMetadataComplete"; //$NON-NLS-1$

	OrmPersistenceUnitDefaults getPersistenceUnitDefaults();

	XmlPersistenceUnitMetadata getXmlPersistenceUnitMetadata();

	XmlPersistenceUnitMetadata buildXmlPersistenceUnitMetadata();

	/**
	 * Update the persistence unit metadata context model to match the resource model.
	 * @see org.eclipse.jpt.core.JpaProject#update()
	 */
	void update();

}

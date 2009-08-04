/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context;

import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.core.JpaFactory;
import org.eclipse.jpt.core.context.orm.OrmAttributeMappingProvider;
import org.eclipse.jpt.core.context.orm.NullOrmAttributeMappingProvider;
import org.eclipse.jpt.core.context.orm.OrmTypeMappingProvider;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.resource.xml.JpaXmlResource;

/**
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface MappingFileDefinition 
{
	/**
	 * Return the associated mapping file content type.
	 */
	IContentType getContentType();

	/**
	 * Build a mapping with the specified parent and resource.
	 */
	MappingFile buildMappingFile(MappingFileRef parent, JpaXmlResource resource, JpaFactory factory);
	
	
	// ********** ORM type/attribute mappings **********
	
	/**
	 * Return an {@link OrmTypeMappingProvider} for the given type mapping key.
	 * Throws an {@link IllegalArgumentException} if the mapping key is not supported.
	 * 
	 * @param mappingKey The type mapping key
	 * @return The mapping provider for the given mapping key
	 */
	OrmTypeMappingProvider getOrmTypeMappingProvider(String mappingKey);
	
	/**
	 * Return an {@link OrmAttributeMappingProvider} for the given attribute mapping key.
	 * This must not return null.  (@see {@link NullOrmAttributeMappingProvider})
	 * Throws an {@link IllegalArgumentException} if the mapping key is not supported.
	 * 
	 * @param mappingKey The attribute mapping key
	 * @return The mapping provider for the given mapping key
	 */
	OrmAttributeMappingProvider getOrmAttributeMappingProvider(String mappingKey);
}

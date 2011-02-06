/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.java;

import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.resource.java.RelationshipMappingAnnotation;

/**
 * Java relationship (1:1, 1:m, m:1, m:m) mapping.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.0
 */
public interface JavaRelationshipMapping
	extends RelationshipMapping, JavaAttributeMapping
{
	RelationshipMappingAnnotation getMappingAnnotation();

	RelationshipMappingAnnotation getAnnotationForUpdate();

	JavaCascade getCascade();

	JavaMappingRelationship getRelationship();

	/**
	 * If the target entity is specified, this will return it fully qualified.
	 * If not specified, it returns the default target entity, which is always
	 * fully qualified
	 */
	String getFullyQualifiedTargetEntity();
		String FULLY_QUALIFIED_TARGET_ENTITY_PROPERTY = "fullyQualifiedTargetEntity"; //$NON-NLS-1$
}

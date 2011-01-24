/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.java;

import org.eclipse.jpt.core.context.MappedByRelationship;
import org.eclipse.jpt.core.resource.java.OwnableRelationshipMappingAnnotation;

/**
 * Java "mapped by" relationship (1:1, 1:m, m:m)
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.2
 * @since 2.2
 */
public interface JavaMappedByRelationship
	extends MappedByRelationship, JavaRelationship
{
	JavaMappedByJoiningStrategy getMappedByJoiningStrategy();

	OwnableRelationshipMappingAnnotation getMappingAnnotation();

	OwnableRelationshipMappingAnnotation getMappingAnnotationForUpdate();
}

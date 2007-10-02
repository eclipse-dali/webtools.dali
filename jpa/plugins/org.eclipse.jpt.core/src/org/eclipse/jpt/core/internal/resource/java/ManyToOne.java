/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;

public interface ManyToOne extends RelationshipMappingAnnotation
{
	DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA.MANY_TO_ONE);
	
	/**
	 * Corresponds to the optional element of the ManyToOne annotation.
	 * Returns null if the optional element does not exist in java.
	 */
	Boolean getOptional();
	
	/**
	 * Corresponds to the optional element of the ManyToOne annotation.
	 * Set to null to remove the optional element.
	 */
	void setOptional(Boolean optional);

}

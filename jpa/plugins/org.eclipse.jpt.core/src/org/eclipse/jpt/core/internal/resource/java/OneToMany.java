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

public interface OneToMany extends MappingAnnotation
{
	DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA.ONE_TO_MANY);
	
	/**
	 * Corresponds to the fetch element of the ManyToOne annotation.
	 * Returns null if the fetch element does not exist in java.
	 */
	FetchType getFetch();
	
	/**
	 * Corresponds to the fetch element of the ManyToOne annotation.
	 * Set to null to remove the fetch element.
	 */
	void setFetch(FetchType fetch);
}

/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.jpa2.context.java;

import org.eclipse.jpt.core.context.java.JavaAssociationOverrideContainer;
import org.eclipse.jpt.core.context.java.JavaAttributeOverrideContainer;
import org.eclipse.jpt.core.context.java.JavaColumn;
import org.eclipse.jpt.core.context.java.JavaConvertibleMapping;
import org.eclipse.jpt.core.jpa2.context.ElementCollectionMapping2_0;
import org.eclipse.jpt.core.jpa2.resource.java.ElementCollection2_0Annotation;

/**
 * Java element collection mapping
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.3
 */
public interface JavaElementCollectionMapping2_0
	extends ElementCollectionMapping2_0, JavaCollectionMapping2_0, JavaConvertibleMapping
{
	ElementCollection2_0Annotation getMappingAnnotation();

	JavaCollectionTable2_0 getCollectionTable();
	
	JavaColumn getValueColumn();

	JavaAttributeOverrideContainer getValueAttributeOverrideContainer();
	
	JavaAssociationOverrideContainer getValueAssociationOverrideContainer();

	/**
	 * If the target class is specified, this will return it fully qualified. If not
	 * specified, it returns the default target class, which is always fully qualified
	 */
	String getFullyQualifiedTargetClass();
		String FULLY_QUALIFIED_TARGET_CLASS_PROPERTY = "fullyQualifiedTargetClass"; //$NON-NLS-1$
}

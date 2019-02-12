/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

/**
 * JPA attribute mapping that is optional (e.g. basic, single relationship).
 * From the JPA spec:
 * <pre>
 * Whether the value of the field or property may be null. This is a hint 
 * and is disregarded for primitive types; it may be used in schema generation.
 * </pre>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.2
 * @since 2.0
 */
public interface OptionalMapping
	extends AttributeMapping
{
	boolean isOptional();
	
	boolean isDefaultOptional();
		String DEFAULT_OPTIONAL_PROPERTY = "defaultOptional"; //$NON-NLS-1$
		boolean DEFAULT_OPTIONAL = true;
	
	Boolean getSpecifiedOptional();
	void setSpecifiedOptional(Boolean newSpecifiedOptional);
		String SPECIFIED_OPTIONAL_PROPERTY = "specifiedOptional"; //$NON-NLS-1$
	
}

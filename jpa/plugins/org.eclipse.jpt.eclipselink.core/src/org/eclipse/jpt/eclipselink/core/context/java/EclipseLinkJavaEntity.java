/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.context.java;

import java.util.ListIterator;
import org.eclipse.jpt.core.context.java.JavaAssociationOverride;
import org.eclipse.jpt.core.context.java.JavaAttributeOverride;
import org.eclipse.jpt.core.context.java.JavaEntity;
import org.eclipse.jpt.core.context.java.JavaNamedNativeQuery;
import org.eclipse.jpt.core.context.java.JavaNamedQuery;
import org.eclipse.jpt.core.context.java.JavaPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.context.java.JavaSecondaryTable;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkEntity;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.1
 * @since 2.1
 */
public interface EclipseLinkJavaEntity extends EclipseLinkEntity, JavaEntity
{
	JavaConverterHolder getConverterHolder();

	EclipseLinkJavaCaching getCaching();

	JavaReadOnly getReadOnly();
	
	JavaCustomizer getCustomizer();
	
	// included these to prevent warnings on the implementation
	ListIterator<JavaSecondaryTable> secondaryTables();	
	ListIterator<JavaSecondaryTable> specifiedSecondaryTables();
	ListIterator<JavaPrimaryKeyJoinColumn> primaryKeyJoinColumns();
	ListIterator<JavaPrimaryKeyJoinColumn> specifiedPrimaryKeyJoinColumns();
	ListIterator<JavaAttributeOverride> attributeOverrides();
	ListIterator<JavaAttributeOverride> specifiedAttributeOverrides();
	ListIterator<JavaAttributeOverride> virtualAttributeOverrides();
	ListIterator<JavaAssociationOverride> associationOverrides();
	ListIterator<JavaAssociationOverride> specifiedAssociationOverrides();
	ListIterator<JavaAssociationOverride> virtualAssociationOverrides();
	ListIterator<JavaNamedQuery> namedQueries();
	ListIterator<JavaNamedNativeQuery> namedNativeQueries();

}

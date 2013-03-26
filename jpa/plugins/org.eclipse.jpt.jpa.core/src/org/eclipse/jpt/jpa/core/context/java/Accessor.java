/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.java;

import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.jpa.core.context.AccessType;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.SpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;

/**
 * Represents a JPA accessor (field or property).
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 3.1
 */
public interface Accessor
	extends JpaContextModel, JavaElementReference
{
	/**
	 * Return the <em>resource</em> attribute(field/method) that is to be annotated. 
	 */
	JavaResourceAttribute getResourceAttribute();

	boolean isFor(JavaResourceField field);

	boolean isFor(JavaResourceMethod getterMethod, JavaResourceMethod setterMethod);

	/**
	 * Build a Java persistent attribute that wraps the original Java resource
	 * attributes and behaves as though it has no annotations. This will cause
	 * all the settings in the Java <em>context</em> attribute to default.
	 */
	JavaSpecifiedPersistentAttribute buildUnannotatedJavaAttribute(PersistentType type);

	AccessType getDefaultAccess();

	JpaValidator buildAttributeValidator(SpecifiedPersistentAttribute persistentAttribute);
}

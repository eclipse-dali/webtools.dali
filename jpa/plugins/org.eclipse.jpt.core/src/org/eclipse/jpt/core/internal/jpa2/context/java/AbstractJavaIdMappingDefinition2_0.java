/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.java;

import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaIdMappingDefinition;

/**
 * JPA 2.0 Id mapping
 */
public abstract class AbstractJavaIdMappingDefinition2_0
	extends AbstractJavaIdMappingDefinition
{
	protected AbstractJavaIdMappingDefinition2_0() {
		super();
	}

	/**
	 * Return the annotation only if it is not "derived".
	 */
	@Override
	public boolean isSpecified(JavaPersistentAttribute persistentAttribute) {
		boolean idSpecified = super.isSpecified(persistentAttribute);
		return idSpecified && ! this.isDerivedId(persistentAttribute);
	}

	/**
	 * Return whether the specified attribute's <code>Id</code> annotation is
	 * a supporting annotation for M-1 or 1-1 mapping, as opposed to a primary
	 * mapping annotation.
	 * <p>
	 * This might produce confusing behavior if the annotations look something
	 * like:<pre>
	 *     @Id @Basic @ManyToOne private int foo;
	 * </pre>
	 */
	protected boolean isDerivedId(JavaPersistentAttribute persistentAttribute) {
		return this.attributeHasManyToOneMapping(persistentAttribute) ||
			this.attributeHasOneToOneMapping(persistentAttribute);
	}

	protected boolean attributeHasManyToOneMapping(JavaPersistentAttribute persistentAttribute) {
		return JavaManyToOneMappingDefinition2_0.instance().isSpecified(persistentAttribute);
	}

	protected boolean attributeHasOneToOneMapping(JavaPersistentAttribute persistentAttribute) {
		return JavaOneToOneMappingDefinition2_0.instance().isSpecified(persistentAttribute);
	}
}

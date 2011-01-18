/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.java;

import org.eclipse.jpt.core.context.Column;
import org.eclipse.jpt.core.context.java.JavaAttributeOverride;
import org.eclipse.jpt.core.context.java.JavaAttributeOverrideContainer;
import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.context.java.JavaReadOnlyAttributeOverride;
import org.eclipse.jpt.core.context.java.JavaVirtualAttributeOverride;
import org.eclipse.jpt.core.resource.java.AttributeOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.AttributeOverridesAnnotation;

/**
 * Java attribute override container
 */
public class GenericJavaAttributeOverrideContainer
	extends AbstractJavaOverrideContainer<
			JavaAttributeOverrideContainer.Owner,
			JavaReadOnlyAttributeOverride,
			JavaAttributeOverride,
			JavaVirtualAttributeOverride,
			AttributeOverrideAnnotation
		>
	implements JavaAttributeOverrideContainer
{
	public GenericJavaAttributeOverrideContainer(JavaJpaContextNode parent, JavaAttributeOverrideContainer.Owner owner) {
		super(parent, owner);
	}


	public Column resolveOverriddenColumn(String attributeName) {
		return (attributeName == null) ? null : this.owner.resolveOverriddenColumn(attributeName);
	}

	@Override
	protected String getOverrideAnnotationName() {
		return AttributeOverrideAnnotation.ANNOTATION_NAME;
	}

	@Override
	protected String getOverrideContainerAnnotationName() {
		return AttributeOverridesAnnotation.ANNOTATION_NAME;
	}

	@Override
	protected JavaAttributeOverride buildSpecifiedOverride(AttributeOverrideAnnotation overrideAnnotation) {
		return this.getJpaFactory().buildJavaAttributeOverride(this, overrideAnnotation);
	}

	@Override
	protected void initializeSpecifiedOverride(JavaAttributeOverride specifiedOverride, JavaVirtualAttributeOverride virtualOverride) {
		specifiedOverride.initializeFromVirtual(virtualOverride);
	}

	@Override
	protected JavaVirtualAttributeOverride buildVirtualOverride(String name) {
		return this.getJpaFactory().buildJavaVirtualAttributeOverride(this, name);
	}
}

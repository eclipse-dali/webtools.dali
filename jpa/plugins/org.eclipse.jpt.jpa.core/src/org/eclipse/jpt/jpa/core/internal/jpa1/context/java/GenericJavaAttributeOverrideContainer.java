/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.AttributeOverride;
import org.eclipse.jpt.jpa.core.context.Column;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedAttributeOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualAttributeOverride;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaAttributeOverrideContainer2_0;
import org.eclipse.jpt.jpa.core.resource.java.AttributeOverrideAnnotation;

/**
 * Java attribute override container
 */
public class GenericJavaAttributeOverrideContainer
	extends AbstractJavaOverrideContainer<
			JavaAttributeOverrideContainer.Owner,
			AttributeOverride,
			JavaSpecifiedAttributeOverride,
			JavaVirtualAttributeOverride,
			AttributeOverrideAnnotation
		>
	implements JavaAttributeOverrideContainer2_0
{
	public GenericJavaAttributeOverrideContainer(JpaContextModel parent, JavaAttributeOverrideContainer.Owner owner) {
		super(parent, owner);
	}


	public Column resolveOverriddenColumn(String attributeName) {
		return (attributeName == null) ? null : this.owner.resolveOverriddenColumn(attributeName);
	}

	public Column getOverrideColumn(String overrideName) {
		return this.getOverrideNamed(overrideName).getColumn();
	}

	@Override
	protected String getOverrideAnnotationName() {
		return AttributeOverrideAnnotation.ANNOTATION_NAME;
	}

	@Override
	protected JavaSpecifiedAttributeOverride buildSpecifiedOverride(AttributeOverrideAnnotation overrideAnnotation) {
		return this.getJpaFactory().buildJavaAttributeOverride(this, overrideAnnotation);
	}

	@Override
	protected void initializeSpecifiedOverride(JavaSpecifiedAttributeOverride specifiedOverride, JavaVirtualAttributeOverride virtualOverride) {
		specifiedOverride.initializeFromVirtual(virtualOverride);
	}

	@Override
	protected JavaVirtualAttributeOverride buildVirtualOverride(String name) {
		return this.getJpaFactory().buildJavaVirtualAttributeOverride(this, name);
	}
}

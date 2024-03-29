/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import org.eclipse.jpt.jpa.core.context.AttributeOverride;
import org.eclipse.jpt.jpa.core.context.Column;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedAttributeOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualAttributeOverride;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaAttributeOverrideContainer2_0;
import org.eclipse.jpt.jpa.core.resource.java.AttributeOverrideAnnotation;

/**
 * Java attribute override container
 */
public class GenericJavaAttributeOverrideContainer
	extends AbstractJavaOverrideContainer<
			JavaAttributeOverrideContainer.ParentAdapter,
			AttributeOverride,
			JavaSpecifiedAttributeOverride,
			JavaVirtualAttributeOverride,
			AttributeOverrideAnnotation
		>
	implements JavaAttributeOverrideContainer2_0
{
	public GenericJavaAttributeOverrideContainer(JpaContextModel parent) {
		super(parent);
	}

	public GenericJavaAttributeOverrideContainer(JavaAttributeOverrideContainer.ParentAdapter parentAdapter) {
		super(parentAdapter);
	}


	public Column resolveOverriddenColumn(String attributeName) {
		return (attributeName == null) ? null : this.parentAdapter.resolveOverriddenColumn(attributeName);
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
		specifiedOverride.initializeFrom(virtualOverride);
	}

	@Override
	protected JavaVirtualAttributeOverride buildVirtualOverride(String name) {
		return this.getJpaFactory().buildJavaVirtualAttributeOverride(this, name);
	}
}

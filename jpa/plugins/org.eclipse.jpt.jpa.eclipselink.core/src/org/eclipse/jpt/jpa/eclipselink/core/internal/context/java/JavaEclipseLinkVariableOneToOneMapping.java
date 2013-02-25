/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.java;

import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaAttributeMapping;
import org.eclipse.jpt.jpa.eclipselink.core.EclipseLinkMappingKeys;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkVariableOneToOneMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaConvertibleMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.JavaEclipseLinkConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkVariableOneToOneAnnotation;

public class JavaEclipseLinkVariableOneToOneMapping 
	extends AbstractJavaAttributeMapping<EclipseLinkVariableOneToOneAnnotation>
	implements EclipseLinkVariableOneToOneMapping, EclipseLinkJavaConvertibleMapping
{
	
	protected final JavaEclipseLinkConverterContainer converterContainer;

	public JavaEclipseLinkVariableOneToOneMapping(JavaPersistentAttribute parent) {
		super(parent);
		this.converterContainer = this.buildConverterContainer();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.converterContainer.synchronizeWithResourceModel();
	}

	@Override
	public void update() {
		super.update();
		this.converterContainer.update();
	}


	// ********** converters **********

	public JavaEclipseLinkConverterContainer getConverterContainer() {
		return this.converterContainer;
	}

	protected JavaEclipseLinkConverterContainer buildConverterContainer() {
		return new JavaEclipseLinkConverterContainerImpl(this);
	}

	// ********** converter container parent adapter **********

	public JpaContextModel getConverterContainerParent() {
		return this;  // no adapter
	}

	public JavaResourceAnnotatedElement getJavaResourceAnnotatedElement() {
		return this.getResourceAttribute();
	}

	public boolean parentSupportsConverters() {
		return ! this.getPersistentAttribute().isVirtual();
	}

	// ********** misc **********
	
	public String getKey() {
		return EclipseLinkMappingKeys.VARIABLE_ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY;
	}
	
	@Override
	protected String getAnnotationName() {
		return EclipseLinkVariableOneToOneAnnotation.ANNOTATION_NAME;
	}
}

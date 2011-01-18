/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.v1_2.context.java;

import java.util.Iterator;

import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaPersistentType;
import org.eclipse.jpt.core.jpa2.resource.java.Access2_0Annotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;

/**
 * EclipseLink Java 1.2
 * Support for specified access.
 */
public class EclipseLinkJavaPersistentType1_2
	extends AbstractJavaPersistentType
{
	public EclipseLinkJavaPersistentType1_2(PersistentType.Owner parent, JavaResourcePersistentType jrpt) {
		super(parent, jrpt);
	}


	// ********** access annotation **********

	protected Access2_0Annotation getAccessAnnotation() {
		return (Access2_0Annotation) this.resourcePersistentType.getNonNullAnnotation(this.getAccessAnnotationName());
	}

	protected void removeAccessAnnotationIfUnset() {
		if (this.getAccessAnnotation().isUnset()) {
			this.removeAccessAnnotation();
		}
	}

	protected void removeAccessAnnotation() {
		this.resourcePersistentType.removeAnnotation(this.getAccessAnnotationName());
	}

	protected String getAccessAnnotationName() {
		return Access2_0Annotation.ANNOTATION_NAME;
	}


	// ********** access **********

	public void setSpecifiedAccess(AccessType access) {
		if (this.valuesAreDifferent(this.specifiedAccess, access)) {
			this.getAccessAnnotation().setValue(AccessType.toJavaResourceModel(access));
			this.removeAccessAnnotationIfUnset();
			this.setSpecifiedAccess_(access);
		}
	}

	@Override
	protected AccessType buildSpecifiedAccess() {
		return AccessType.fromJavaResourceModel(this.getAccessAnnotation().getValue());
	}


	// ********** attributes **********

	@Override
	protected Iterator<JavaResourcePersistentAttribute> resourceAttributes() {
		return (this.specifiedAccess == null) ?
				super.resourceAttributes() :
				this.resourcePersistentType.persistableAttributes(AccessType.toJavaResourceModel(this.specifiedAccess));
	}
}

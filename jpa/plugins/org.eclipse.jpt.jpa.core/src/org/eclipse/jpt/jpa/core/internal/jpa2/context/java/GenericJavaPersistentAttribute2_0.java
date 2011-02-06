/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.jpa.core.context.AccessType;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.GenericPersistentAttributeValidator;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.Access2_0Annotation;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentAttribute;

/**
 * JPA 2.0 Java persistent attribute
 */
public class GenericJavaPersistentAttribute2_0
	extends AbstractJavaPersistentAttribute
{
	protected AccessType specifiedAccess;


	public GenericJavaPersistentAttribute2_0(PersistentType parent, JavaResourcePersistentAttribute resourcePersistentAttribute) {
		super(parent, resourcePersistentAttribute);
		this.specifiedAccess = this.buildSpecifiedAccess();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setSpecifiedAccess_(this.buildSpecifiedAccess());
	}


	// ********** access **********

	@Override
	public AccessType getSpecifiedAccess() {
		return this.specifiedAccess;
	}

	/**
	 * Don't support changing to specified access on a java persistent attribute, this
	 * involves a larger process of moving the annotations to the corresponding field/property
	 * which may or may not exist or might need to be created.
	 */
	public void setSpecifiedAccess(AccessType specifiedAccess) {
		throw new UnsupportedOperationException();
	}

	protected void setSpecifiedAccess_(AccessType access) {
		AccessType old = this.specifiedAccess;
		this.specifiedAccess = access;
		this.firePropertyChanged(SPECIFIED_ACCESS_PROPERTY, old, access);
	}

	protected AccessType buildSpecifiedAccess() {
		Access2_0Annotation accessAnnotation = this.getAccessAnnotation();
		return (accessAnnotation == null) ? null : AccessType.fromJavaResourceModel(accessAnnotation.getValue());
	}

	protected Access2_0Annotation getAccessAnnotation() {
		return (Access2_0Annotation) this.resourcePersistentAttribute.getAnnotation(Access2_0Annotation.ANNOTATION_NAME);
	}


	// ********** validation **********

	@Override
	protected JptValidator buildAttibuteValidator(CompilationUnit astRoot) {
		return new GenericPersistentAttributeValidator(this, this, buildTextRangeResolver(astRoot));
	}
}

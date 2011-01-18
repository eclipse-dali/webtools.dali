/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.java;

import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;
import org.eclipse.core.resources.IFile;
import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaPersistentType;
import org.eclipse.jpt.core.jpa2.context.MetamodelSourceType;
import org.eclipse.jpt.core.jpa2.context.java.JavaPersistentType2_0;
import org.eclipse.jpt.core.jpa2.resource.java.Access2_0Annotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.utility.BodySourceWriter;

/**
 * JPA 2.0 Java persistent type.
 * Support for specified access and metamodel generation.
 */
public class GenericJavaPersistentType2_0
	extends AbstractJavaPersistentType
	implements JavaPersistentType2_0
{
	protected String declaringTypeName;

	protected final MetamodelSourceType.Synchronizer metamodelSynchronizer;


	public GenericJavaPersistentType2_0(PersistentType.Owner parent, JavaResourcePersistentType jrpt) {
		super(parent, jrpt);
		this.declaringTypeName = this.buildDeclaringTypeName();
		this.metamodelSynchronizer = this.buildMetamodelSynchronizer();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setDeclaringTypeName(this.buildDeclaringTypeName());
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

	// suppress type-safety warning
	@Override
	public ListIterator<JavaPersistentAttribute> attributes() {
		return super.attributes();
	}

	@Override
	protected Iterator<JavaResourcePersistentAttribute> resourceAttributes() {
		return (this.specifiedAccess == null) ?
				super.resourceAttributes() :
				this.resourcePersistentType.persistableAttributes(AccessType.toJavaResourceModel(this.specifiedAccess));
	}


	// ********** declaring type name **********

	public String getDeclaringTypeName() {
		return this.declaringTypeName;
	}

	protected void setDeclaringTypeName(String declaringTypeName) {
		String old = this.declaringTypeName;
		this.declaringTypeName = declaringTypeName;
		this.firePropertyChanged(DECLARING_TYPE_NAME_PROPERTY, old, declaringTypeName);
	}

	protected String buildDeclaringTypeName() {
		return this.resourcePersistentType.getDeclaringTypeName();
	}


	// ********** metamodel **********

	public IFile getMetamodelFile() {
		return this.metamodelSynchronizer.getFile();
	}

	public void initializeMetamodel() {
		// do nothing - probably shouldn't be called...
	}

	public boolean isManaged() {
		return true;
	}

	public void synchronizeMetamodel(Map<String, Collection<MetamodelSourceType>> memberTypeTree) {
		this.metamodelSynchronizer.synchronize(memberTypeTree);
	}

	public void printBodySourceOn(BodySourceWriter pw, Map<String, Collection<MetamodelSourceType>> memberTypeTree) {
		this.metamodelSynchronizer.printBodySourceOn(pw, memberTypeTree);
	}

	public void disposeMetamodel() {
		// do nothing - probably shouldn't be called...
	}

	protected MetamodelSourceType.Synchronizer buildMetamodelSynchronizer() {
		return this.getJpaFactory2_0().buildMetamodelSynchronizer(this);
	}
}

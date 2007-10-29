/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jpt.core.internal.jdtutility.Attribute;
import org.eclipse.jpt.core.internal.jdtutility.FieldAttribute;
import org.eclipse.jpt.core.internal.jdtutility.JPTTools;
import org.eclipse.jpt.core.internal.jdtutility.MethodAttribute;
import org.eclipse.jpt.core.internal.jdtutility.Type;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;

public class JavaPersistentTypeResourceImpl extends AbstractJavaPersistentResource<Type> implements JavaPersistentTypeResource
{	
	/**
	 * store all member types including those that aren't persistable so we can include validation errors.
	 */
	private final Collection<JavaPersistentTypeResource> nestedTypes;
	
	private final Collection<JavaPersistentAttributeResource> attributes;
	
	private AccessType accessType;
	
	private String superClassQualifiedName;
	
	private String qualifiedName;
	
	public JavaPersistentTypeResourceImpl(JavaResource parent, Type type){
		super(parent, type);
		this.nestedTypes = new ArrayList<JavaPersistentTypeResource>(); 
		this.attributes = new ArrayList<JavaPersistentAttributeResource>();
	}

	// ******** AbstractJavaPersistentResource implementation ********

	@Override
	protected Annotation buildMappingAnnotation(String mappingAnnotationName) {
		return annotationProvider().buildTypeMappingAnnotation(this, getMember(), mappingAnnotationName);
	}

	@Override
	protected Annotation buildAnnotation(String annotationName) {
		return annotationProvider().buildTypeAnnotation(this, getMember(), annotationName);
	}
		
	@Override
	protected Iterator<String> correspondingAnnotationNames(String mappingAnnotationName) {
		return annotationProvider().correspondingTypeAnnotationNames(mappingAnnotationName);
	}
	
	@Override
	protected ListIterator<String> possibleMappingAnnotationNames() {
		return annotationProvider().typeMappingAnnotationNames();
	}
	
	@Override
	protected boolean isPossibleAnnotation(String annotationName) {
		return CollectionTools.contains(annotationProvider().typeAnnotationNames(), annotationName);
	}
	
	@Override
	protected boolean isPossibleMappingAnnotation(String annotationName) {
		return CollectionTools.contains(annotationProvider().typeMappingAnnotationNames(), annotationName);
	}
	
	@Override
	protected boolean calculatePersistability(CompilationUnit astRoot) {
		return JPTTools.typeIsPersistable(getMember().binding(astRoot));
	}

	// ******** JavaPersistentTypeResource implementation ********
	public JavaPersistentTypeResource javaPersistentTypeResource(String fullyQualifiedTypeName) {
		if (getQualifiedName().equals(fullyQualifiedTypeName)) {
			return this;
		}
		for (JavaPersistentTypeResource jptr : CollectionTools.iterable(nestedTypes())) {
			if (jptr.getQualifiedName().equals(fullyQualifiedTypeName)) {
				return jptr;
			}
		}
		return null;
	}

	public Iterator<JavaPersistentTypeResource> nestedTypes() {
		//TODO since we are filtering how do we handle the case where a type becomes persistable?
		//what kind of change notificiation for that case?
		return new FilteringIterator<JavaPersistentTypeResource>(new CloneIterator<JavaPersistentTypeResource>(this.nestedTypes)) {
			@Override
			protected boolean accept(Object o) {
				return ((JavaPersistentTypeResource) o).isPersistable();
			}
		};
	}
	
	private JavaPersistentTypeResource nestedTypeFor(IType type) {
		for (JavaPersistentTypeResource nestedType : this.nestedTypes) {
			if (nestedType.isFor(type)) {
				return nestedType;
			}
		}
		return null;
	}
	
	//I think this should be private since adding/removing of nestedTypes
	//only depends on the underlying java IType, not on anything our context model can do.
	private JavaPersistentTypeResource addNestedType(IType nestedType) {
		JavaPersistentTypeResource persistentType = createJavaPersistentType(nestedType);
		addNestedType(persistentType);
		return persistentType;
	}

	private void addNestedType(JavaPersistentTypeResource nestedType) {
		this.nestedTypes.add(nestedType);
		//TODO property change notification, or other notification to the context model
	}
	
	//I think this should be private since adding/removing of nestedTypes
	//only depends on the underlying java IType, not on anything our context model can do.
	private void removeNestedType(JavaPersistentTypeResource nestedType) {
		this.nestedTypes.remove(nestedType);
		//TODO property change notification, or other notification to the context model
	}
	
	private JavaPersistentTypeResource createJavaPersistentType(IType nestedType) {
		Type type = new Type(nestedType, this.modifySharedDocumentCommandExecutorProvider());
		return new JavaPersistentTypeResourceImpl(this, type);
	}

	public Iterator<JavaPersistentAttributeResource> attributes() {
		//TODO since we are filtering how do we handle the case where an attribute becomes persistable?
		//what kind of change notificiation for that case?
		return new FilteringIterator<JavaPersistentAttributeResource>(new CloneIterator<JavaPersistentAttributeResource>(this.attributes)) {
			@Override
			protected boolean accept(Object o) {
				return ((JavaPersistentAttributeResource) o).isPersistable();
			}
		};
	}
	
	public Iterator<JavaPersistentAttributeResource> fields() {
		return new FilteringIterator<JavaPersistentAttributeResource>(attributes()) {
			@Override
			protected boolean accept(Object o) {
				return ((JavaPersistentAttributeResource) o).isForField();
			}
		};
	}
	
	public Iterator<JavaPersistentAttributeResource> properties() {
		return new FilteringIterator<JavaPersistentAttributeResource>(attributes()) {
			@Override
			protected boolean accept(Object o) {
				return ((JavaPersistentAttributeResource) o).isForProperty();
			}
		};
	}

	private JavaPersistentAttributeResource addAttribute(IMember jdtMember) {
		JavaPersistentAttributeResource persistentAttribute = createJavaPersistentAttribute(jdtMember);
		addAttribute(persistentAttribute);
		return persistentAttribute;
	}
	
	private void addAttribute(JavaPersistentAttributeResource attribute) {
		this.attributes.add(attribute);
		//TODO fire change notification
	}

	private JavaPersistentAttributeResource createJavaPersistentAttribute(IMember member) {
		Attribute attribute = null;
		if (member instanceof IField) {
			attribute = new FieldAttribute((IField) member, this.modifySharedDocumentCommandExecutorProvider());
		}
		else if (member instanceof IMethod) {
			attribute = new MethodAttribute((IMethod) member, this.modifySharedDocumentCommandExecutorProvider());
		}
		else {
			throw new IllegalArgumentException();
		}
		return new JavaPersistentAttributeResourceImpl(this, attribute);
	}
	
	private void removeAttribute(JavaPersistentAttributeResource attribute) {
		this.attributes.remove(attribute);
		//TODO fire change notification	
	}
	
	private JavaPersistentAttributeResource attributeFor(IMember member) {
		for (JavaPersistentAttributeResource persistentAttribute : this.attributes) {
			if (persistentAttribute.isFor(member)) {
				return persistentAttribute;
			}
		}
		return null;
	}
	
	public AccessType getAccess() {
		return this.accessType;
	}
	
	//seems we could have a public changeAccess() api which would
	//move all annotations from fields to their corresponding methods or vice versa
	//though of course it's more complicated than that since what if the
	//corresponding field/method does not exist?
	//making this internal since it should only be set based on changes in the source, the
	//context model should not need to set this
	private void setAccess(AccessType access) {
		this.accessType = access;
		//fire property change notification
	}

	public String getSuperClassQualifiedName() {
		return this.superClassQualifiedName;
	}
	
	private void setSuperClassQualifiedName(String qualifiedName) {
		this.superClassQualifiedName = qualifiedName;
		//TODO change notification
	}

	public String getQualifiedName() {
		return this.qualifiedName;
	}
	
	protected void setQualifiedName(String qualifiedName) {
		this.qualifiedName = qualifiedName;
		//TODO change notification
	}
	
	@Override
	public void updateFromJava(CompilationUnit astRoot) {
		super.updateFromJava(astRoot);
		setQualifiedName(getMember().binding(astRoot).getQualifiedName());
		updateNestedTypes(astRoot);
		updatePersistentAttributes(astRoot);
		setAccess(accessType());
		setSuperClassQualifiedName(superClassQualifiedName(astRoot));
	}
	
	private void updateNestedTypes(CompilationUnit astRoot) {
		IType[] declaredTypes = getMember().declaredTypes();
		
		List<JavaPersistentTypeResource> nestedTypesToRemove = new ArrayList<JavaPersistentTypeResource>(this.nestedTypes);
		for (IType declaredType : declaredTypes) {
			JavaPersistentTypeResource nestedType = nestedTypeFor(declaredType);
			if (nestedType == null) {
				nestedType = addNestedType(declaredType);
			}
			else {
				nestedTypesToRemove.remove(nestedType);
			}
			nestedType.updateFromJava(astRoot);
		}
		for (JavaPersistentTypeResource nestedType : nestedTypesToRemove) {
			removeNestedType(nestedType);
		}
	}
	
	private void updatePersistentAttributes(CompilationUnit astRoot) {
		List<JavaPersistentAttributeResource> persistentAttributesToRemove = new ArrayList<JavaPersistentAttributeResource>(this.attributes);
		updatePersistentFields(astRoot, persistentAttributesToRemove);
		updatePersistentProperties(astRoot, persistentAttributesToRemove);
		for (JavaPersistentAttributeResource persistentAttribute : persistentAttributesToRemove) {
			removeAttribute(persistentAttribute);
		}
	}
	
	private void updatePersistentFields(CompilationUnit astRoot, List<JavaPersistentAttributeResource> persistentAttributesToRemove) {
		updatePersistentAttributes(astRoot, persistentAttributesToRemove, getMember().fields());
	}

	private void updatePersistentProperties(CompilationUnit astRoot, List<JavaPersistentAttributeResource> persistentAttributesToRemove) {
		updatePersistentAttributes(astRoot, persistentAttributesToRemove, getMember().methods());
	}

	private void updatePersistentAttributes(CompilationUnit astRoot, List<JavaPersistentAttributeResource> persistentAttributesToRemove, IMember[] members) {
		for (IMember member : members) {
			JavaPersistentAttributeResource persistentAttribute = attributeFor(member);
			if (persistentAttribute == null) {
				persistentAttribute = addAttribute(member);
			}
			else {
				persistentAttributesToRemove.remove(persistentAttribute);
			}
			persistentAttribute.updateFromJava(astRoot);
		}
	}
	
	/**
	 * Return the AccessType currently implied by the Java source code:
	 *     - if only Fields are annotated => FIELD
	 *     - if only Properties are annotated => PROPERTY
	 *     - if both Fields and Properties are annotated => FIELD
	 *     - if nothing is annotated
	 *     		- and fields exist => FIELD
	 *     		- and properties exist, but no fields exist => PROPERTY
	 *     		- and neither fields nor properties exist => null at this level (FIELD in the context model)
	 */
	private AccessType accessType() {
		boolean hasPersistableFields = false;
		boolean hasPersistableProperties = false;
		for (JavaPersistentAttributeResource field : CollectionTools.iterable(fields())) {
			hasPersistableFields = true;
			if (field.hasAnyAnnotation()) {
				// any field is annotated => FIELD
				return AccessType.FIELD;
			}
		}
		for (JavaPersistentAttributeResource property : CollectionTools.iterable(properties())) {
			hasPersistableProperties = true;
			if (property.hasAnyAnnotation()) {
				// none of the fields are annotated and a getter is annotated => PROPERTY
				return AccessType.PROPERTY;
			}
		}

		if (hasPersistableProperties && !hasPersistableFields) {
			return AccessType.PROPERTY;
		}
		//no annotations exist, access is null at the resource model level
		return null;
	}
	
	private String superClassQualifiedName(CompilationUnit astRoot) {
		ITypeBinding typeBinding = getMember().binding(astRoot);
		if (typeBinding == null) {
			return null;
		}
		ITypeBinding superClassTypeBinding = typeBinding.getSuperclass();
		if (superClassTypeBinding == null) {
			return null;
		}
		return superClassTypeBinding.getQualifiedName();
	}
	

}

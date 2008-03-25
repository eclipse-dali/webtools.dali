/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.internal.utility.jdt.JDTFieldAttribute;
import org.eclipse.jpt.core.internal.utility.jdt.JDTMethodAttribute;
import org.eclipse.jpt.core.internal.utility.jdt.JDTType;
import org.eclipse.jpt.core.internal.utility.jdt.JPTTools;
import org.eclipse.jpt.core.resource.java.AccessType;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.core.utility.jdt.AnnotationEditFormatter;
import org.eclipse.jpt.core.utility.jdt.Attribute;
import org.eclipse.jpt.core.utility.jdt.Type;
import org.eclipse.jpt.utility.CommandExecutorProvider;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;

public class JavaResourcePersistentTypeImpl
	extends AbstractJavaResourcePersistentMember<Type>
	implements JavaResourcePersistentType
{	
	/**
	 * store all member types including those that aren't persistable so we can include validation errors.
	 */
	private final Collection<JavaResourcePersistentType> nestedTypes;
	
	private final Collection<JavaResourcePersistentAttribute> attributes;
	
	private AccessType accessType;
	
	private String superClassQualifiedName;
	
	private String qualifiedName;
	
	private String name;
	
	private boolean isAbstract;
	
	public JavaResourcePersistentTypeImpl(JavaResourceNode parent, Type type){
		super(parent, type);
		this.nestedTypes = new ArrayList<JavaResourcePersistentType>(); 
		this.attributes = new ArrayList<JavaResourcePersistentAttribute>();
	}

	@Override
	public void initialize(CompilationUnit astRoot) {
		super.initialize(astRoot);
		this.qualifiedName = this.qualifiedName(astRoot);
		this.name = this.name(astRoot);
		this.initializeNestedTypes(astRoot);
		this.initializeAttributes(astRoot);
		this.accessType = this.calculateAccessType();
		this.superClassQualifiedName = this.superClassQualifiedName(astRoot);
		this.isAbstract = this.isAbstract(astRoot);
	}
	
	protected void initializeNestedTypes(CompilationUnit astRoot) {
		for (IType declaredType : getMember().jdtTypes()) {
			this.nestedTypes.add(buildJavaResourcePersistentType(declaredType, astRoot));
		}
	}
	
	protected void initializeAttributes(CompilationUnit astRoot) {
		for (IField field : getMember().jdtFields()) {
			this.attributes.add(createJavaPersistentAttribute(field, astRoot));
		}
		for (IMethod method : getMember().jdtMethods()) {
			this.attributes.add(createJavaPersistentAttribute(method, astRoot));
		}
	}
	
	// ******** AbstractJavaPersistentResource implementation ********

	@Override
	protected Annotation buildMappingAnnotation(String mappingAnnotationName) {
		return annotationProvider().buildTypeMappingAnnotation(this, getMember(), mappingAnnotationName);
	}
	
	@Override
	protected Annotation buildNullMappingAnnotation(String annotationName) {
		return annotationProvider().buildNullTypeMappingAnnotation(this, getMember(), annotationName);
	}

	@Override
	protected Annotation buildAnnotation(String annotationName) {
		return annotationProvider().buildTypeAnnotation(this, getMember(), annotationName);
	}
	
	@Override
	protected Annotation buildNullAnnotation(String annotationName) {
		return annotationProvider().buildNullTypeAnnotation(this, getMember(), annotationName);
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


	@Override
	@SuppressWarnings("unchecked")
	//overriding purely to suppress the warning you get at the class level
	public ListIterator<NestableAnnotation> annotations(String nestableAnnotationName, String containerAnnotationName) {
		return super.annotations(nestableAnnotationName, containerAnnotationName);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	//overriding purely to suppress the warning you get at the class level
	public Iterator<Annotation> mappingAnnotations() {
		return super.mappingAnnotations();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	//overriding purely to suppress the warning you get at the class level
	public Iterator<Annotation> annotations() {
		return super.annotations();
	}

	
	// ******** JavaPersistentTypeResource implementation ********
	public JavaResourcePersistentType javaPersistentTypeResource(String fullyQualifiedTypeName) {
		if (getQualifiedName().equals(fullyQualifiedTypeName)) {
			return this;
		}
		for (JavaResourcePersistentType jptr : CollectionTools.iterable(nestedTypes())) {
			if (jptr.getQualifiedName().equals(fullyQualifiedTypeName)) {
				return jptr;
			}
		}
		return null;
	}

	public Iterator<JavaResourcePersistentType> nestedTypes() {
		//TODO since we are filtering how do we handle the case where a type becomes persistable?
		//what kind of change notificiation for that case?
		return new FilteringIterator<JavaResourcePersistentType, JavaResourcePersistentType>(new CloneIterator<JavaResourcePersistentType>(this.nestedTypes)) {
			@Override
			protected boolean accept(JavaResourcePersistentType o) {
				return o.isPersistable();
			}
		};
	}
	
	protected JavaResourcePersistentType nestedTypeFor(IType type) {
		for (JavaResourcePersistentType nestedType : this.nestedTypes) {
			if (nestedType.isFor(type)) {
				return nestedType;
			}
		}
		return null;
	}
	
	protected JavaResourcePersistentType addNestedType(IType nestedType, CompilationUnit astRoot) {
		JavaResourcePersistentType persistentType = buildJavaResourcePersistentType(nestedType, astRoot);
		addNestedType(persistentType);
		return persistentType;
	}

	protected void addNestedType(JavaResourcePersistentType nestedType) {
		addItemToCollection(nestedType, this.nestedTypes, NESTED_TYPES_COLLECTION);
	}
	
	protected void removeNestedType(JavaResourcePersistentType nestedType) {
		removeItemFromCollection(nestedType, this.nestedTypes, NESTED_TYPES_COLLECTION);
	}
	
	protected JavaResourcePersistentType buildJavaResourcePersistentType(IType nestedType, CompilationUnit astRoot) {
		return buildJavaResourcePersistentType(this, nestedType, modifySharedDocumentCommandExecutorProvider(), annotationEditFormatter(), astRoot);
	}

	public static JavaResourcePersistentType buildJavaResourcePersistentType(
		JavaResourceNode parent, 
		IType nestedType, 
		CommandExecutorProvider modifySharedDocumentCommandExecutorProvider,
		AnnotationEditFormatter annotationEditFormatter, 
		CompilationUnit astRoot) {
		
		Type type = new JDTType(nestedType, modifySharedDocumentCommandExecutorProvider, annotationEditFormatter);
		JavaResourcePersistentTypeImpl javaPersistentType = new JavaResourcePersistentTypeImpl(parent, type);
		javaPersistentType.initialize(astRoot);
		return javaPersistentType;	
	}
	
	public Iterator<JavaResourcePersistentAttribute> attributes() {
		//TODO since we are filtering how do we handle the case where an attribute becomes persistable?
		//what kind of change notificiation for that case?
		return new FilteringIterator<JavaResourcePersistentAttribute, JavaResourcePersistentAttribute>(new CloneIterator<JavaResourcePersistentAttribute>(this.attributes)) {
			@Override
			protected boolean accept(JavaResourcePersistentAttribute o) {
				return o.isPersistable();
			}
		};
	}
	
	public Iterator<JavaResourcePersistentAttribute> fields() {
		return new FilteringIterator<JavaResourcePersistentAttribute, JavaResourcePersistentAttribute>(attributes()) {
			@Override
			protected boolean accept(JavaResourcePersistentAttribute o) {
				return o.isForField();
			}
		};
	}
	
	public Iterator<JavaResourcePersistentAttribute> properties() {
		return new FilteringIterator<JavaResourcePersistentAttribute, JavaResourcePersistentAttribute>(attributes()) {
			@Override
			protected boolean accept(JavaResourcePersistentAttribute o) {
				return o.isForProperty();
			}
		};
	}

	protected JavaResourcePersistentAttribute addAttribute(IMember jdtMember, CompilationUnit astRoot) {
		JavaResourcePersistentAttribute persistentAttribute = createJavaPersistentAttribute(jdtMember, astRoot);
		addAttribute(persistentAttribute);
		return persistentAttribute;
	}
	
	protected void addAttribute(JavaResourcePersistentAttribute attribute) {
		addItemToCollection(attribute, this.attributes, ATTRIBUTES_COLLECTION);
	}

	protected JavaResourcePersistentAttribute createJavaPersistentAttribute(IMember member, CompilationUnit astRoot) {
		Attribute attribute = null;
		if (member instanceof IField) {
			attribute = new JDTFieldAttribute((IField) member, this.modifySharedDocumentCommandExecutorProvider(), this.annotationEditFormatter());
		}
		else if (member instanceof IMethod) {
			attribute = new JDTMethodAttribute((IMethod) member, this.modifySharedDocumentCommandExecutorProvider(), this.annotationEditFormatter());
		}
		else {
			throw new IllegalArgumentException();
		}
		JavaResourcePersistentAttribute javaPersistentAttribute = new JavaResourcePersistentAttributeImpl(this, attribute);
		javaPersistentAttribute.initialize(astRoot);
		return javaPersistentAttribute;
	}
	
	protected void removeAttribute(JavaResourcePersistentAttribute attribute) {
		removeItemFromCollection(attribute, this.attributes, ATTRIBUTES_COLLECTION);
	}
	
	protected JavaResourcePersistentAttribute attributeFor(IMember member) {
		for (JavaResourcePersistentAttribute persistentAttribute : this.attributes) {
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
	protected void setAccess(AccessType newAccess) {
		AccessType oldAccess = this.accessType;
		this.accessType = newAccess;
		firePropertyChanged(ACCESS_PROPERTY, oldAccess, newAccess);
	}

	public String getSuperClassQualifiedName() {
		return this.superClassQualifiedName;
	}
	
	private void setSuperClassQualifiedName(String newSuperClassQualifiedName) {
		String oldSuperClassQualifiedName = this.superClassQualifiedName;
		this.superClassQualifiedName = newSuperClassQualifiedName;
		firePropertyChanged(SUPER_CLASS_QUALIFIED_NAME_PROPERTY, oldSuperClassQualifiedName, newSuperClassQualifiedName);
	}

	public String getQualifiedName() {
		return this.qualifiedName;
	}
	
	protected void setQualifiedName(String newQualifiedName) {
		String oldQualifiedName = this.qualifiedName;
		this.qualifiedName = newQualifiedName;
		firePropertyChanged(QUALIFIED_NAME_PROPERTY, oldQualifiedName, newQualifiedName);
	}
	
	public String getName() {
		return this.name;
	}
	
	protected void setName(String newName) {
		String oldName = this.name;
		this.name = newName;
		firePropertyChanged(NAME_PROPERTY, oldName, newName);
	}
	
	public boolean isAbstract() {
		return this.isAbstract;
	}
	
	protected void setAbstract(boolean newAbstract) {
		boolean oldAbstract = this.isAbstract;
		this.isAbstract = newAbstract;
		firePropertyChanged(ABSTRACT_PROPERTY, oldAbstract, newAbstract);
	}
	
	@Override
	public void updateFromJava(CompilationUnit astRoot) {
		super.updateFromJava(astRoot);
		this.setQualifiedName(this.qualifiedName(astRoot));
		this.setName(this.name(astRoot));
		this.updateNestedTypes(astRoot);
		this.updatePersistentAttributes(astRoot);
		this.setAccess(this.calculateAccessType());
		this.setSuperClassQualifiedName(this.superClassQualifiedName(astRoot));
		this.setAbstract(isAbstract(astRoot));
	}
	
	@Override
	public void resolveTypes(CompilationUnit astRoot) {
		super.resolveTypes(astRoot);
		this.setSuperClassQualifiedName(this.superClassQualifiedName(astRoot));

		for (JavaResourcePersistentAttribute attribute : this.attributes) {
			attribute.resolveTypes(astRoot);
		}
		for (JavaResourcePersistentType persistentType : this.nestedTypes) {
			persistentType.resolveTypes(astRoot);
		}
	}
	
	protected boolean isAbstract(CompilationUnit astRoot) {
		return JPTTools.typeIsAbstract(getMember().binding(astRoot));
	}

	protected String qualifiedName(CompilationUnit astRoot) {
		return getMember().binding(astRoot).getQualifiedName();
	}
	
	protected String name(CompilationUnit astRoot) {
		return getMember().binding(astRoot).getName();
	}
	
	protected void updateNestedTypes(CompilationUnit astRoot) {
		IType[] declaredTypes = getMember().jdtTypes();
		
		List<JavaResourcePersistentType> nestedTypesToRemove = new ArrayList<JavaResourcePersistentType>(this.nestedTypes);
		for (IType declaredType : declaredTypes) {
			JavaResourcePersistentType nestedType = nestedTypeFor(declaredType);
			if (nestedType == null) {
				nestedType = addNestedType(declaredType, astRoot);
			}
			else {
				nestedTypesToRemove.remove(nestedType);
			}
			nestedType.updateFromJava(astRoot);
		}
		for (JavaResourcePersistentType nestedType : nestedTypesToRemove) {
			removeNestedType(nestedType);
		}
	}
	
	protected void updatePersistentAttributes(CompilationUnit astRoot) {
		List<JavaResourcePersistentAttribute> persistentAttributesToRemove = new ArrayList<JavaResourcePersistentAttribute>(this.attributes);
		updatePersistentFields(astRoot, persistentAttributesToRemove);
		updatePersistentProperties(astRoot, persistentAttributesToRemove);
		for (JavaResourcePersistentAttribute persistentAttribute : persistentAttributesToRemove) {
			removeAttribute(persistentAttribute);
		}
	}
	
	protected void updatePersistentFields(CompilationUnit astRoot, List<JavaResourcePersistentAttribute> persistentAttributesToRemove) {
		updatePersistentAttributes(astRoot, persistentAttributesToRemove, getMember().jdtFields());
	}

	protected void updatePersistentProperties(CompilationUnit astRoot, List<JavaResourcePersistentAttribute> persistentAttributesToRemove) {
		updatePersistentAttributes(astRoot, persistentAttributesToRemove, getMember().jdtMethods());
	}

	protected void updatePersistentAttributes(CompilationUnit astRoot, List<JavaResourcePersistentAttribute> persistentAttributesToRemove, IMember[] members) {
		for (IMember member : members) {
			JavaResourcePersistentAttribute persistentAttribute = attributeFor(member);
			if (persistentAttribute == null) {
				persistentAttribute = addAttribute(member, astRoot);
			}
			else {
				persistentAttributesToRemove.remove(persistentAttribute);
			}
			persistentAttribute.updateFromJava(astRoot);
		}
	}
	
	public boolean hasAnyAttributeAnnotations() {
		for (JavaResourcePersistentAttribute attribute : CollectionTools.iterable(attributes())) {
			if (attribute.hasAnyAnnotation()) {
				return true;
			}
		}
		return false;
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
	private AccessType calculateAccessType() {
		boolean hasPersistableFields = false;
		boolean hasPersistableProperties = false;
		for (JavaResourcePersistentAttribute field : CollectionTools.iterable(fields())) {
			hasPersistableFields = true;
			if (field.hasAnyAnnotation()) {
				// any field is annotated => FIELD
				return AccessType.FIELD;
			}
		}
		for (JavaResourcePersistentAttribute property : CollectionTools.iterable(properties())) {
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
	
	@Override
	public void toString(StringBuilder sb) {
		sb.append(getName());
	}

}

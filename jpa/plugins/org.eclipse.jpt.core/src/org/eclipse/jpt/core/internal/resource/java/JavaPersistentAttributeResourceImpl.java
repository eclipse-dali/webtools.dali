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

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jpt.core.internal.jdtutility.Attribute;
import org.eclipse.jpt.core.internal.jdtutility.JPTTools;
import org.eclipse.jpt.utility.internal.CollectionTools;

public class JavaPersistentAttributeResourceImpl
	extends AbstractJavaPersistentResource<Attribute>
	implements JavaPersistentAttributeResource
{

	public JavaPersistentAttributeResourceImpl(JavaPersistentTypeResource parent, Attribute attribute){
		super(parent, attribute);
	}

	public String getName() {
		return getMember().attributeName();
	}

	// ******** AbstractJavaPersistentResource implementation ********
	
	@Override
	protected Annotation buildMappingAnnotation(String mappingAnnotationName) {
		return annotationProvider().buildAttributeMappingAnnotation(this, getMember(), mappingAnnotationName);
	}

	@Override
	protected Annotation buildAnnotation(String annotationName) {
		return annotationProvider().buildAttributeAnnotation(this, getMember(), annotationName);
	}
		
	@Override
	protected Iterator<String> correspondingAnnotationNames(String mappingAnnotationName) {
		return annotationProvider().correspondingAttributeAnnotationNames(mappingAnnotationName);
	}
	
	@Override
	protected ListIterator<String> possibleMappingAnnotationNames() {
		return annotationProvider().attributeMappingAnnotationNames();
	}
		
	@Override
	protected boolean isPossibleAnnotation(String annotationName) {
		return CollectionTools.contains(annotationProvider().attributeAnnotationNames(), annotationName);
	}
	
	@Override
	protected boolean isPossibleMappingAnnotation(String annotationName) {
		return CollectionTools.contains(annotationProvider().attributeMappingAnnotationNames(), annotationName);
	}

	@Override
	protected boolean calculatePersistability(CompilationUnit astRoot) {
		if (isForField()) {
			return JPTTools.fieldIsPersistable((IVariableBinding) getMember().binding(astRoot));
		}
		return JPTTools.methodIsPersistablePropertyGetter((IMethodBinding) getMember().binding(astRoot));
	}

	// ******** JavaPersistentAttributeResource implementation ********
		
	public boolean isForField() {
		return getMember().isField();
	}
	
	public boolean isForProperty() {
		return !isForField();
	}
	
	public boolean hasAnyAnnotation() {
		if (CollectionTools.size(mappingAnnotations()) > 0) {
			return true;
		}
		if (CollectionTools.size(annotations()) > 0) {
			return true;
		}
		return false;
	}
	
	
//	private void setTypeName(String typeName) {
//		this.typeName = typeName;
//		//TODO change notification
//	}
//	
//	private void setTypeNameInContainer(String typeNameInContainer) {
//		this.typeNameInContainer = typeNameInContainer;
//		//TODO change notification
//	}
//	
//	private void setTypeIsArray(boolean typeIsArray) {
//		this.typeIsArray = typeIsArray;
//		//TODO change notification
//	}

	@Override
	public void updateFromJava(CompilationUnit astRoot) {
		super.updateFromJava(astRoot);
//TODO		updateTypeName(astRoot);
	}


//	private void updateTypeName(CompilationUnit astRoot) {
//		ITypeBinding typeBinding = getMember().typeBinding(astRoot);
//		setTypeIsArray(typeBinding.isArray());
//		setTypeName(buildReferenceEntityTypeName(typeBinding));
//		setTypeNameInContainer(typeNameInContainer)
//		
//	}
//	
//	//relationshipMapping
//	/**
//	 * the default 'targetEntity' is calculated from the attribute type;
//	 * return null if the attribute type cannot possibly be an entity
//	 */
//	protected final String javaDefaultTargetEntity(ITypeBinding typeBinding) {
//		return builTODO Embeddable???dReferenceEntityTypeName(typeBinding);
//	}
//
//	//	// 
//	public static String buildReferenceEntityTypeName(ITypeBinding typeBinding) {
//	//	if (!typeBinding.isArray()) { // arrays cannot be entities
//			return typeBinding.getTypeDeclaration().getQualifiedName();
//	//	}
//	//	return null;
//	}
//
//	
//	//multiRelationshipMapping
//	/**
//	 * extract the element type from the specified container signature and
//	 * convert it into a reference entity type name;
//	 * return null if the type is not a valid reference entity type (e.g. it's
//	 * another container or an array or a primitive or other Basic type)
//	 */
//	@Override
//	protected String javaDefaultTargetEntity(ITypeBinding typeBinding) {
//		String typeName = super.javaDefaultTargetEntity(typeBinding);
//		return typeNamedIsContainer(typeName) ? javaDefaultTargetEntityFromContainer(typeBinding) : null;
//	}
//
//	public static String javaDefaultTargetEntityFromContainer(ITypeBinding typeBinding) {
//		ITypeBinding[] typeArguments = typeBinding.getTypeArguments();
//		if (typeArguments.length != 1) {
//			return null;
//		}
//		ITypeBinding elementTypeBinding = typeArguments[0];
//		String elementTypeName = buildReferenceEntityTypeName(elementTypeBinding);
//		return typeNamedIsContainer(elementTypeName) ? null : elementTypeName;
//	}
//
//	
//	//singleRelationshipMapping
//	/**
//	 * extend to eliminate any "container" types
//	 */
//	@Override
//	protected String javaDefaultTargetEntity(ITypeBinding typeBinding) {
//		String typeName = super.javaDefaultTargetEntity(typeBinding);
//		// if the attribute is a container, don't use it
//		return typeNamedIsContainer(typeName) ? null : typeName;
//	}
//
//	
//	
//	
//	
//	/**
//	 * return whether the specified non-array type is one of the container
//	 * types allowed by the JPA spec
//	 */
//	public static boolean typeNamedIsContainer(String typeName) {
//		return CollectionTools.contains(CONTAINER_TYPE_NAMES, typeName);
//	}
//
//	private static final String[] CONTAINER_TYPE_NAMES = {
//		java.util.Collection.class.getName(),
//		java.util.Set.class.getName(),
//		java.util.List.class.getName(),
//		java.util.Map.class.getName()
//	};
	
}

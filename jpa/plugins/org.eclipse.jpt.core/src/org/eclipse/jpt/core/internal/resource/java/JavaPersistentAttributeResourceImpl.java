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
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jpt.core.internal.jdtutility.Attribute;
import org.eclipse.jpt.core.internal.jdtutility.JPTTools;
import org.eclipse.jpt.utility.internal.CollectionTools;

public class JavaPersistentAttributeResourceImpl
	extends AbstractJavaPersistentResource<Attribute>
	implements JavaPersistentAttributeResource
{

	private boolean typeIsBasic;
	
	private String qualifiedTypeName;
	
	public JavaPersistentAttributeResourceImpl(JavaPersistentTypeResource parent, Attribute attribute){
		super(parent, attribute);
	}

	@Override
	public void initialize(CompilationUnit astRoot) {
		super.initialize(astRoot);
		this.typeIsBasic = this.typeIsBasic(astRoot);
		this.qualifiedTypeName = this.qualifiedTypeName(astRoot);
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
	protected Annotation buildNullAnnotation(String annotationName) {
		return annotationProvider().buildNullAttributeAnnotation(this, getMember(), annotationName);
	}
	
	@Override
	protected Annotation buildNullMappingAnnotation(String annotationName) {
		return annotationProvider().buildNullAttributeMappingAnnotation(this, getMember(), annotationName);
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
		IBinding binding = getMember().binding(astRoot);
		if (binding == null) {
			return false;
		}
		if (isForField()) {
			return JPTTools.fieldIsPersistable((IVariableBinding) binding);
		}
		return JPTTools.methodIsPersistablePropertyGetter((IMethodBinding) binding);
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

	public boolean typeIsBasic() {
		return this.typeIsBasic;
	}
	
	protected void setTypeIsBasic(boolean newTypeIsBasic) {
		boolean oldTypeIsBasic = this.typeIsBasic;
		this.typeIsBasic = newTypeIsBasic;
		firePropertyChanged(TYPE_IS_BASIC_PROPERTY, oldTypeIsBasic, newTypeIsBasic);
	}

	public String getQualifiedTypeName() {
		return this.qualifiedTypeName;
	}
	
	protected void setQualifiedTypeName(String newQualifiedTypeName) {
		String oldQualifiedTypeName = this.qualifiedTypeName;
		this.qualifiedTypeName = newQualifiedTypeName;
		firePropertyChanged(QUALFIED_TYPE_NAME_PROPERTY, oldQualifiedTypeName, newQualifiedTypeName);
		
	}
	
	@Override
	public void updateFromJava(CompilationUnit astRoot) {
		super.updateFromJava(astRoot);
		this.setTypeIsBasic(this.typeIsBasic(astRoot));
		this.setQualifiedTypeName(this.qualifiedTypeName(astRoot));
//TODO		updateTypeName(astRoot);
	}

	@Override
	public void resolveTypes(CompilationUnit astRoot) {
		super.resolveTypes(astRoot);
		this.setTypeIsBasic(this.typeIsBasic(astRoot));
		this.setQualifiedTypeName(this.qualifiedTypeName(astRoot));
	}

	protected boolean typeIsBasic(CompilationUnit astRoot) {
		return typeIsBasic(getMember().typeBinding(astRoot), astRoot.getAST());
	}
	
	protected String qualifiedTypeName(CompilationUnit astRoot) {
		ITypeBinding typeBinding = getMember().typeBinding(astRoot);
		if (typeBinding == null) {
			return null;
		}
		return typeBinding.getQualifiedName();
	}

	
	/**
	 * From the JPA spec, when the basic mapping applies:
	 * If the type of the attribute (field or property) is one of the following
	 * it must be mapped as @Basic:
	 *     primitive types
	 *     byte[]
	 *     Byte[]
	 *     char[]
	 *     Character[]
	 *     primitive wrappers
	 *     java.lang.String
	 *     java.math.BigInteger
	 *     java.math.BigDecimal
	 *     java.util.Date
	 *     java.util.Calendar
	 *     java.sql.Date
	 *     java.sql.Time
	 *     java.sql.Timestamp
	 *     enums
	 *     any other type that implements java.io.Serializable
	 */
	public static boolean typeIsBasic(ITypeBinding typeBinding, AST ast) {
		if (typeBinding == null) {
			return false; // type not found
		}
		if (typeBinding.isPrimitive()) {
			return true;
		}
		if (typeBinding.isArray()) {
			if (typeBinding.getDimensions() > 1) {
				return false; // multi-dimensional arrays are not supported
			}
			ITypeBinding elementTypeBinding = typeBinding.getElementType();
			if (elementTypeBinding == null) {
				return false;// unable to resolve the type
			}
			return elementTypeIsValid(elementTypeBinding.getQualifiedName());
		}
		String typeName = typeBinding.getQualifiedName();
		if (typeIsPrimitiveWrapper(typeName)) {
			return true;
		}
		if (typeIsOtherSupportedType(typeName)) {
			return true;
		}
		if (typeBinding.isEnum()) {
			return true;
		}
		if (typeImplementsSerializable(typeBinding, ast)) {
			return true;
		}
		return false;
	}

	/**
	 * Return whether the specified type is a valid element type for
	 * a one-dimensional array:
	 *     byte
	 *     char
	 *     java.lang.Byte
	 *     java.lang.Character
	 */
	private static boolean elementTypeIsValid(String elementTypeName) {
		return CollectionTools.contains(VALID_ELEMENT_TYPE_NAMES, elementTypeName);
	}

	private static final String[] VALID_ELEMENT_TYPE_NAMES = {
		byte.class.getName(),
		char.class.getName(),
		java.lang.Byte.class.getName(),
		java.lang.Character.class.getName()
	};

	/**
	 * Return whether the specified type is a primitive wrapper.
	 */
	private static boolean typeIsPrimitiveWrapper(String typeName) {
		return CollectionTools.contains(PRIMITIVE_WRAPPER_TYPE_NAMES, typeName);
	}
	
	private static final String[] PRIMITIVE_WRAPPER_TYPE_NAMES = {
		java.lang.Byte.class.getName(),
		java.lang.Character.class.getName(),
		java.lang.Double.class.getName(),
		java.lang.Float.class.getName(),
		java.lang.Integer.class.getName(),
		java.lang.Long.class.getName(),
		java.lang.Short.class.getName(),
		java.lang.Boolean.class.getName(),
	};

	/**
	 * Return whether the specified type is among the various other types
	 * that default to a Basic mapping.
	 */
	private static boolean typeIsOtherSupportedType(String typeName) {
		return CollectionTools.contains(OTHER_SUPPORTED_TYPE_NAMES, typeName);
	}
	
	private static final String[] OTHER_SUPPORTED_TYPE_NAMES = {
		java.lang.String.class.getName(),
		java.math.BigInteger.class.getName(),
		java.math.BigDecimal.class.getName(),
		java.util.Date.class.getName(),
		java.util.Calendar.class.getName(),
		java.sql.Date.class.getName(),
		java.sql.Time.class.getName(),
		java.sql.Timestamp.class.getName(),
	};
	
	/**
	 * Return whether the specified type implements java.io.Serializable.
	 */
	private static boolean typeImplementsSerializable(ITypeBinding typeBinding, AST ast) {
		ITypeBinding serializableTypeBinding = ast.resolveWellKnownType(SERIALIZABLE_TYPE_NAME);
		return typeBinding.isAssignmentCompatible(serializableTypeBinding);
	}

	private static final String SERIALIZABLE_TYPE_NAME = java.io.Serializable.class.getName();
	

	
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
	
	@Override
	public void toString(StringBuilder sb) {
		sb.append(getName());
	}


}

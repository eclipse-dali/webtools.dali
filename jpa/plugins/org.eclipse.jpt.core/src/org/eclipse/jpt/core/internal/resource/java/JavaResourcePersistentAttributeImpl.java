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

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jpt.core.internal.utility.jdt.JDTFieldAttribute;
import org.eclipse.jpt.core.internal.utility.jdt.JDTMethodAttribute;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.JpaCompilationUnit;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.core.utility.jdt.Attribute;
import org.eclipse.jpt.core.utility.jdt.MethodAttribute;
import org.eclipse.jpt.core.utility.jdt.Type;
import org.eclipse.jpt.utility.MethodSignature;
import org.eclipse.jpt.utility.internal.CollectionTools;

public class JavaResourcePersistentAttributeImpl
	extends AbstractJavaResourcePersistentMember<Attribute>
	implements JavaResourcePersistentAttribute
{

	private boolean typeIsBasic;
	
	private String qualifiedTypeName;
	
	private boolean typeIsContainer;
	
	private String qualifiedReferenceEntityTypeName;
	
	private String qualifiedReferenceEntityElementTypeName;
	
	private boolean public_;  // 'public' is a reserved word
	
	private boolean final_;  // 'final' is a reserved word

	/**
	 * construct field attribute
	 */
	public static JavaResourcePersistentAttribute newInstance(
			JavaResourcePersistentType parent,
			Type declaringType,
			String name,
			int occurrence,
			JpaCompilationUnit jpaCompilationUnit,
			CompilationUnit astRoot) {
		Attribute attribute = new JDTFieldAttribute(
				declaringType,
				name,
				occurrence,
				jpaCompilationUnit.getCompilationUnit(),
				jpaCompilationUnit.getModifySharedDocumentCommandExecutorProvider(),
				jpaCompilationUnit.getAnnotationEditFormatter());
		JavaResourcePersistentAttribute field = new JavaResourcePersistentAttributeImpl(parent, attribute);
		field.initialize(astRoot);
		return field;
	}
	
	/**
	 * construct property attribute
	 */
	public static JavaResourcePersistentAttribute newInstance(
			JavaResourcePersistentType parent,
			Type declaringType,
			MethodSignature signature,
			int occurrence,
			JpaCompilationUnit jpaCompilationUnit,
			CompilationUnit astRoot) {
		Attribute attribute = JDTMethodAttribute.newInstance(
				declaringType,
				signature,
				occurrence,
				jpaCompilationUnit.getCompilationUnit(),
				jpaCompilationUnit.getModifySharedDocumentCommandExecutorProvider(),
				jpaCompilationUnit.getAnnotationEditFormatter());
		JavaResourcePersistentAttribute field = new JavaResourcePersistentAttributeImpl(parent, attribute);
		field.initialize(astRoot);
		return field;
	}
	
	public JavaResourcePersistentAttributeImpl(JavaResourcePersistentType parent, Attribute attribute){
		super(parent, attribute);
	}
	
	public String getName() {
		return getMember().getAttributeName();
	}

	// ******** AbstractJavaPersistentResource implementation ********
	
	@Override
	protected Annotation buildMappingAnnotation(String mappingAnnotationName) {
		return getAnnotationProvider().buildAttributeMappingAnnotation(this, getMember(), mappingAnnotationName);
	}

	@Override
	protected Annotation buildAnnotation(String annotationName) {
		return getAnnotationProvider().buildAttributeAnnotation(this, getMember(), annotationName);
	}
		
	@Override
	protected Annotation buildNullAnnotation(String annotationName) {
		return getAnnotationProvider().buildNullAttributeAnnotation(this, getMember(), annotationName);
	}
	
	@Override
	protected Annotation buildNullMappingAnnotation(String annotationName) {
		return getAnnotationProvider().buildNullAttributeMappingAnnotation(this, getMember(), annotationName);
	}
	
	@Override
	protected ListIterator<String> possibleMappingAnnotationNames() {
		return getAnnotationProvider().attributeMappingAnnotationNames();
	}
		
	@Override
	protected boolean isPossibleAnnotation(String annotationName) {
		return CollectionTools.contains(getAnnotationProvider().attributeAnnotationNames(), annotationName);
	}
	
	@Override
	protected boolean isPossibleMappingAnnotation(String annotationName) {
		return CollectionTools.contains(getAnnotationProvider().attributeMappingAnnotationNames(), annotationName);
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
	
	@Override
	public boolean isFor(MethodSignature signature, int occurrence) {
		return ((MethodAttribute) this.getMember()).matches(signature, occurrence);
	}

	// ******** JavaPersistentAttributeResource implementation ********
		
	public boolean isForField() {
		return getMember().isField();
	}
	
	public boolean isForProperty() {
		return !isForField();
	}
	
	public boolean hasAnyAnnotation() {
		if (mappingAnnotationsSize() > 0) {
			return true;
		}
		if (annotationsSize() > 0) {
			return true;
		}
		return false;
	}

	public boolean isPublic() {
		return this.public_;
	}
	
	protected void setPublic(boolean newPublic) {
		boolean oldPublic = this.public_;
		this.public_ = newPublic;
		firePropertyChanged(PUBLIC_PROPERTY, oldPublic, newPublic);
	}
	
	public boolean isFinal() {
		return this.final_;
	}
	
	protected void setFinal(boolean newFinal) {
		boolean oldFinal = this.final_;
		this.final_ = newFinal;
		firePropertyChanged(FINAL_PROPERTY, oldFinal, newFinal);
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
		firePropertyChanged(QUALIFIED_TYPE_NAME_PROPERTY, oldQualifiedTypeName, newQualifiedTypeName);		
	}
	
	public String getQualifiedReferenceEntityTypeName() {
		return this.qualifiedReferenceEntityTypeName;
	}
	
	protected void setQualifiedReferenceEntityTypeName(String newQualifiedReferenceEntityTypeName) {
		String oldQualifiedReferenceEntityTypeName = this.qualifiedReferenceEntityTypeName;
		this.qualifiedReferenceEntityTypeName = newQualifiedReferenceEntityTypeName;
		firePropertyChanged(QUALIFIED_REFERENCE_ENTITY_TYPE_NAME_PROPERTY, oldQualifiedReferenceEntityTypeName, newQualifiedReferenceEntityTypeName);
	}
	
	public String getQualifiedReferenceEntityElementTypeName() {
		return this.qualifiedReferenceEntityElementTypeName;
	}
	
	protected void setQualifiedReferenceEntityElementTypeName(String newQualifiedReferenceEntityElementTypeName) {
		String oldQualifiedReferenceEntityElementTypeName = this.qualifiedReferenceEntityElementTypeName;
		this.qualifiedReferenceEntityElementTypeName = newQualifiedReferenceEntityElementTypeName;
		firePropertyChanged(QUALIFIED_REFERENCE_ENTITY_ELEMENT_TYPE_NAME_PROPERTY, oldQualifiedReferenceEntityElementTypeName, newQualifiedReferenceEntityElementTypeName);
	}
	
	public boolean typeIsContainer() {
		return this.typeIsContainer;
	}
	
	protected void setTypeIsContainer(boolean newTypeIsContainer) {
		boolean oldTypeIsContainer = this.typeIsContainer;
		this.typeIsContainer = newTypeIsContainer;
		firePropertyChanged(TYPE_IS_CONTAINER_PROPERTY, oldTypeIsContainer, newTypeIsContainer);
	}
	
	@Override
	public void initialize(CompilationUnit astRoot) {
		super.initialize(astRoot);
		this.typeIsBasic = this.typeIsBasic(astRoot);
		this.qualifiedTypeName = this.qualifiedTypeName(astRoot);
		this.qualifiedReferenceEntityTypeName = this.qualifiedReferenceEntityTypeName(astRoot);
		this.qualifiedReferenceEntityElementTypeName = this.qualifiedReferenceEntityElementTypeName(astRoot);
		this.typeIsContainer = this.typeIsContainer(astRoot);
		this.final_ = this.isFinal(astRoot);
		this.public_ = this.isPublic(astRoot);
	}

	@Override
	public void update(CompilationUnit astRoot) {
		super.update(astRoot);
		this.setTypeIsBasic(this.typeIsBasic(astRoot));
		this.setQualifiedTypeName(this.qualifiedTypeName(astRoot));
		this.setQualifiedReferenceEntityTypeName(this.qualifiedReferenceEntityTypeName(astRoot));
		this.setQualifiedReferenceEntityElementTypeName(this.qualifiedReferenceEntityElementTypeName(astRoot));
		this.setTypeIsContainer(this.typeIsContainer(astRoot));
		this.setFinal(this.isFinal(astRoot));
		this.setPublic(this.isPublic(astRoot));
	}

	@Override
	public void resolveTypes(CompilationUnit astRoot) {
		super.resolveTypes(astRoot);
		this.setTypeIsBasic(this.typeIsBasic(astRoot));
		this.setQualifiedTypeName(this.qualifiedTypeName(astRoot));
		this.setQualifiedReferenceEntityTypeName(this.qualifiedReferenceEntityTypeName(astRoot));
		this.setQualifiedReferenceEntityElementTypeName(this.qualifiedReferenceEntityElementTypeName(astRoot));
		this.setTypeIsContainer(this.typeIsContainer(astRoot));
	}

	protected boolean typeIsBasic(CompilationUnit astRoot) {
		return typeIsBasic(this.getMember().getTypeBinding(astRoot), astRoot.getAST());
	}
	
	protected boolean isFinal(CompilationUnit astRoot) {
		IBinding binding = getMember().getBinding(astRoot);
		return (binding == null) ? false : Modifier.isFinal(binding.getModifiers());
	}
		
	protected boolean isPublic(CompilationUnit astRoot) {
		IBinding binding = getMember().getBinding(astRoot);	
		return (binding == null) ? false : Modifier.isPublic(binding.getModifiers());
	}
	
	protected String qualifiedReferenceEntityTypeName(CompilationUnit astRoot) {
		ITypeBinding typeBinding = this.getMember().getTypeBinding(astRoot);
		return (typeBinding == null) ? null : buildReferenceEntityTypeName(typeBinding);
	}

	public static String buildReferenceEntityTypeName(ITypeBinding typeBinding) {
		if (typeBinding == null) {
			return null;
		}
		if (typeBinding.isArray()) {
			return null;  // arrays cannot be entities
		}
		return typeBinding.getTypeDeclaration().getQualifiedName();
	}
	
	protected String qualifiedReferenceEntityElementTypeName(CompilationUnit astRoot) {
		ITypeBinding typeBinding = this.getMember().getTypeBinding(astRoot);
		if (typeBinding == null) {
			return null;
		}

		ITypeBinding[] typeArguments = typeBinding.getTypeArguments();
		if (typeArguments.length != 1) {
			return null;
		}
		ITypeBinding elementTypeBinding = typeArguments[0];
		String elementTypeName = buildReferenceEntityTypeName(elementTypeBinding);
		return typeNamedIsContainer(elementTypeName) ? null : elementTypeName;
	}

	
	protected boolean typeIsContainer(CompilationUnit astRoot) {
		String typeName = buildReferenceEntityTypeName(this.getMember().getTypeBinding(astRoot));
		return (typeName == null) ? false : typeNamedIsContainer(typeName);
	}
	
	/**
	 * return whether the specified non-array type is one of the container
	 * types allowed by the JPA spec
	 */
	public static boolean typeNamedIsContainer(String typeName) {
		return CollectionTools.contains(CONTAINER_TYPE_NAMES, typeName);
	}

	private static final String[] CONTAINER_TYPE_NAMES = {
		java.util.Collection.class.getName(),
		java.util.Set.class.getName(),
		java.util.List.class.getName(),
		java.util.Map.class.getName()
	};
	
	protected String qualifiedTypeName(CompilationUnit astRoot) {
		ITypeBinding typeBinding = this.getMember().getTypeBinding(astRoot);
		return (typeBinding == null) ? null : typeBinding.getTypeDeclaration().getQualifiedName();
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
	
	@Override
	public void toString(StringBuilder sb) {
		sb.append(getName());
	}
}

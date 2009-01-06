/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import java.util.ListIterator;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jpt.core.internal.utility.jdt.JDTFieldAttribute;
import org.eclipse.jpt.core.internal.utility.jdt.JDTMethodAttribute;
import org.eclipse.jpt.core.internal.utility.jdt.JDTTools;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.JpaCompilationUnit;
import org.eclipse.jpt.core.utility.jdt.Attribute;
import org.eclipse.jpt.core.utility.jdt.MethodAttribute;
import org.eclipse.jpt.core.utility.jdt.Type;
import org.eclipse.jpt.utility.MethodSignature;
import org.eclipse.jpt.utility.internal.CollectionTools;

/**
 * 
 */
public class JavaResourcePersistentAttributeImpl
	extends AbstractJavaResourcePersistentMember<Attribute>
	implements JavaResourcePersistentAttribute
{

	private boolean typeIsBasic;

	private String qualifiedTypeName;
	
	private boolean typeIsSerializable;

	private boolean typeIsDateOrCalendar;

	private boolean typeIsContainer;
	
	private boolean typeIsInterface;
	
	private boolean typeIsValueHolder;
	
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
				jpaCompilationUnit.getModifySharedDocumentCommandExecutor(),
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
				jpaCompilationUnit.getModifySharedDocumentCommandExecutor(),
				jpaCompilationUnit.getAnnotationEditFormatter());
		JavaResourcePersistentAttribute field = new JavaResourcePersistentAttributeImpl(parent, attribute);
		field.initialize(astRoot);
		return field;
	}
	
	public JavaResourcePersistentAttributeImpl(JavaResourcePersistentType parent, Attribute attribute){
		super(parent, attribute);
	}
	
	@Override
	public void initialize(CompilationUnit astRoot) {
		super.initialize(astRoot);
		this.typeIsBasic = this.buildTypeIsBasic(astRoot);
		this.qualifiedTypeName = this.buildQualifiedTypeName(astRoot);
		this.qualifiedReferenceEntityTypeName = this.buildQualifiedReferenceEntityTypeName(astRoot);
		this.qualifiedReferenceEntityElementTypeName = this.buildQualifiedReferenceEntityElementTypeName(astRoot);
		this.typeIsSerializable = this.buildTypeIsSerializable(astRoot);
		this.typeIsDateOrCalendar = this.buildTypeIsDateOrCalendar(astRoot);
		this.typeIsContainer = this.buildTypeIsContainer(astRoot);
		this.typeIsInterface = this.buildTypeIsInterface(astRoot);
		this.typeIsValueHolder = this.buildTypeIsValueHolder(astRoot);
		this.final_ = this.buildFinal(astRoot);
		this.public_ = this.buildPublic(astRoot);
	}

	public String getName() {
		return this.getMember().getAttributeName();
	}

	// ******** AbstractJavaPersistentResource implementation ********
	
	@Override
	protected Annotation buildMappingAnnotation(String mappingAnnotationName) {
		return this.getAnnotationProvider().buildAttributeMappingAnnotation(this, this.getMember(), mappingAnnotationName);
	}

	@Override
	protected Annotation buildSupportingAnnotation(String annotationName) {
		return this.getAnnotationProvider().buildAttributeSupportingAnnotation(this, this.getMember(), annotationName);
	}
		
	@Override
	protected Annotation buildNullSupportingAnnotation(String annotationName) {
		return this.getAnnotationProvider().buildNullAttributeSupportingAnnotation(this, this.getMember(), annotationName);
	}
	
	@Override
	protected Annotation buildNullMappingAnnotation(String annotationName) {
		return this.getAnnotationProvider().buildNullAttributeMappingAnnotation(this, this.getMember(), annotationName);
	}
	
	@Override
	protected ListIterator<String> validMappingAnnotationNames() {
		return this.getAnnotationProvider().attributeMappingAnnotationNames();
	}
		
	@Override
	protected ListIterator<String> validSupportingAnnotationNames() {
		return this.getAnnotationProvider().attributeSupportingAnnotationNames();
	}

	@Override
	public boolean isFor(MethodSignature signature, int occurrence) {
		return ((MethodAttribute) this.getMember()).matches(signature, occurrence);
	}

	// ******** JavaPersistentAttributeResource implementation ********
		
	public boolean isForField() {
		return this.getMember().isField();
	}
	
	public boolean isForProperty() {
		return ! this.isForField();
	}
	
	public boolean hasAnyAnnotations() {
		if (this.mappingAnnotationsSize() > 0) {
			return true;
		}
		if (this.supportingAnnotationsSize() > 0) {
			return true;
		}
		return false;
	}

	public boolean isPublic() {
		return this.public_;
	}
	
	protected void setPublic(boolean public_) {
		boolean old = this.public_;
		this.public_ = public_;
		this.firePropertyChanged(PUBLIC_PROPERTY, old, public_);
	}
	
	public boolean isFinal() {
		return this.final_;
	}
	
	protected void setFinal(boolean final_) {
		boolean old = this.final_;
		this.final_ = final_;
		this.firePropertyChanged(FINAL_PROPERTY, old, final_);
	}
	
	public boolean typeIsBasic() {
		return this.typeIsBasic;
	}
	
	protected void setTypeIsBasic(boolean typeIsBasic) {
		boolean old = this.typeIsBasic;
		this.typeIsBasic = typeIsBasic;
		this.firePropertyChanged(TYPE_IS_BASIC_PROPERTY, old, typeIsBasic);
	}

	public String getQualifiedTypeName() {
		return this.qualifiedTypeName;
	}
	
	protected void setQualifiedTypeName(String qualifiedTypeName) {
		String old = this.qualifiedTypeName;
		this.qualifiedTypeName = qualifiedTypeName;
		this.firePropertyChanged(QUALIFIED_TYPE_NAME_PROPERTY, old, qualifiedTypeName);		
	}
	
	public String getQualifiedReferenceEntityTypeName() {
		return this.qualifiedReferenceEntityTypeName;
	}
	
	protected void setQualifiedReferenceEntityTypeName(String qualifiedReferenceEntityTypeName) {
		String old = this.qualifiedReferenceEntityTypeName;
		this.qualifiedReferenceEntityTypeName = qualifiedReferenceEntityTypeName;
		this.firePropertyChanged(QUALIFIED_REFERENCE_ENTITY_TYPE_NAME_PROPERTY, old, qualifiedReferenceEntityTypeName);
	}
	
	public String getQualifiedReferenceEntityElementTypeName() {
		return this.qualifiedReferenceEntityElementTypeName;
	}
	
	protected void setQualifiedReferenceEntityElementTypeName(String qualifiedReferenceEntityElementTypeName) {
		String old = this.qualifiedReferenceEntityElementTypeName;
		this.qualifiedReferenceEntityElementTypeName = qualifiedReferenceEntityElementTypeName;
		this.firePropertyChanged(QUALIFIED_REFERENCE_ENTITY_ELEMENT_TYPE_NAME_PROPERTY, old, qualifiedReferenceEntityElementTypeName);
	}
	
	public boolean typeIsSerializable() {
		return this.typeIsSerializable;
	}
	
	protected void setTypeIsSerializable(boolean typeIsSerializable) {
		boolean old = this.typeIsSerializable;
		this.typeIsSerializable = typeIsSerializable;
		this.firePropertyChanged(TYPE_IS_SERIALIZABLE_PROPERTY, old, typeIsSerializable);
	}
	
	public boolean typeIsDateOrCalendar() {
		return this.typeIsDateOrCalendar;
	}
	
	protected void setTypeIsDateOrCalendar(boolean typeIsDateOrCalendar) {
		boolean old = this.typeIsDateOrCalendar;
		this.typeIsDateOrCalendar = typeIsDateOrCalendar;
		firePropertyChanged(TYPE_IS_DATE_OR_CALENDAR_PROPERTY, old, typeIsDateOrCalendar);
	}

	public boolean typeIsContainer() {
		return this.typeIsContainer;
	}
	
	protected void setTypeIsContainer(boolean typeIsContainer) {
		boolean old = this.typeIsContainer;
		this.typeIsContainer = typeIsContainer;
		this.firePropertyChanged(TYPE_IS_CONTAINER_PROPERTY, old, typeIsContainer);
	}
	
	public boolean typeIsInterface() {
		return this.typeIsInterface;
	}
	
	protected void setTypeIsInterface(boolean typeIsInterface) {
		boolean old = this.typeIsInterface;
		this.typeIsInterface = typeIsInterface;
		this.firePropertyChanged(TYPE_IS_INTERFACE_PROPERTY, old, typeIsInterface);
	}
	
	public boolean typeIsValueHolder() {
		return this.typeIsValueHolder;
	}
	
	protected void setTypeIsValueHolder(boolean typeIsValueHolder) {
		boolean old = this.typeIsValueHolder;
		this.typeIsValueHolder = typeIsValueHolder;
		this.firePropertyChanged(TYPE_IS_VALUE_HOLDER_PROPERTY, old, typeIsValueHolder);
	}
	
	
	@Override
	public void update(CompilationUnit astRoot) {
		super.update(astRoot);
		this.setTypeIsBasic(this.buildTypeIsBasic(astRoot));
		this.setQualifiedTypeName(this.buildQualifiedTypeName(astRoot));
		this.setQualifiedReferenceEntityTypeName(this.buildQualifiedReferenceEntityTypeName(astRoot));
		this.setQualifiedReferenceEntityElementTypeName(this.buildQualifiedReferenceEntityElementTypeName(astRoot));
		this.setTypeIsSerializable(this.buildTypeIsSerializable(astRoot));
		this.setTypeIsDateOrCalendar(this.buildTypeIsDateOrCalendar(astRoot));
		this.setTypeIsContainer(this.buildTypeIsContainer(astRoot));
		this.setTypeIsInterface(this.buildTypeIsInterface(astRoot));
		this.setTypeIsValueHolder(this.buildTypeIsValueHolder(astRoot));
		this.setFinal(this.buildFinal(astRoot));
		this.setPublic(this.buildPublic(astRoot));
	}

	@Override
	public void resolveTypes(CompilationUnit astRoot) {
		super.resolveTypes(astRoot);
		this.setTypeIsBasic(this.buildTypeIsBasic(astRoot));
		this.setQualifiedTypeName(this.buildQualifiedTypeName(astRoot));
		this.setQualifiedReferenceEntityTypeName(this.buildQualifiedReferenceEntityTypeName(astRoot));
		this.setQualifiedReferenceEntityElementTypeName(this.buildQualifiedReferenceEntityElementTypeName(astRoot));
		this.setTypeIsSerializable(this.buildTypeIsSerializable(astRoot));
		this.setTypeIsDateOrCalendar(this.buildTypeIsDateOrCalendar(astRoot));
		this.setTypeIsContainer(this.buildTypeIsContainer(astRoot));
	}

	protected boolean buildTypeIsBasic(CompilationUnit astRoot) {
		return typeIsBasic(this.getMember().getTypeBinding(astRoot), astRoot.getAST());
	}
	
	protected boolean buildFinal(CompilationUnit astRoot) {
		IBinding binding = this.getMember().getBinding(astRoot);
		return (binding == null) ? false : Modifier.isFinal(binding.getModifiers());
	}
		
	protected boolean buildPublic(CompilationUnit astRoot) {
		IBinding binding = this.getMember().getBinding(astRoot);	
		return (binding == null) ? false : Modifier.isPublic(binding.getModifiers());
	}
	
	protected String buildQualifiedReferenceEntityTypeName(CompilationUnit astRoot) {
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
	
	protected String buildQualifiedReferenceEntityElementTypeName(CompilationUnit astRoot) {
		ITypeBinding typeBinding = this.getMember().getTypeBinding(astRoot);
		if (typeBinding == null) {
			return null;
		}

		ITypeBinding[] typeArguments = typeBinding.getTypeArguments();
		ITypeBinding elementTypeBinding;
		if (typeArguments.length == 1) {
			elementTypeBinding = typeArguments[0];
		}
		else if (typeArguments.length == 2 && typeNamedIsMap(buildQualifiedTypeName(astRoot))) {
			elementTypeBinding = typeArguments[1];
		}
		else {
			return null;
		}
		String elementTypeName = buildReferenceEntityTypeName(elementTypeBinding);
		return typeNamedIsContainer(elementTypeName) ? null : elementTypeName;
	}

	
	protected boolean buildTypeIsSerializable(CompilationUnit astRoot) {
		return typeImplementsSerializable(this.getMember().getTypeBinding(astRoot), astRoot.getAST());
	}

	protected boolean buildTypeIsDateOrCalendar(CompilationUnit astRoot) {
		return typeImplementsDateOrCalendar(this.getMember().getTypeBinding(astRoot));
	}
	
	protected boolean buildTypeIsContainer(CompilationUnit astRoot) {
		String typeName = buildReferenceEntityTypeName(this.getMember().getTypeBinding(astRoot));
		return (typeName == null) ? false : typeNamedIsContainer(typeName);
	}
	
	protected boolean buildTypeIsInterface(CompilationUnit astRoot) {
		ITypeBinding typeBinding = getMember().getTypeBinding(astRoot);
		return typeBinding == null ? false : typeBinding.isInterface();
	}
	
	protected boolean buildTypeIsValueHolder(CompilationUnit astRoot) {
		return typeIsValueHolder(this.getMember().getTypeBinding(astRoot));
	}

	private static final String MAP_TYPE_NAME = java.util.Map.class.getName();

	/**
	 * return whether the specified non-array type is one of the container
	 * types allowed by the JPA spec
	 */
	public static boolean typeNamedIsMap(String typeName) {
		return MAP_TYPE_NAME.equals(typeName);
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
		MAP_TYPE_NAME,
	};
	
	
	protected String buildQualifiedTypeName(CompilationUnit astRoot) {
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
		if (typeBinding == null) {
			return false;
		}
		ITypeBinding serializableTypeBinding = ast.resolveWellKnownType(SERIALIZABLE_TYPE_NAME);
		return typeBinding.isAssignmentCompatible(serializableTypeBinding);
	}

	private static final String SERIALIZABLE_TYPE_NAME = java.io.Serializable.class.getName();
	
	/**
	 * Return whether the specified type implements java.util.Date or java.util.Calendar.
	 */
	private static boolean typeImplementsDateOrCalendar(ITypeBinding typeBinding) {
		return typeImplementsDate(typeBinding) || typeImplementsCalendar(typeBinding);
	}

	/**
	 * Return whether the specified type implements java.util.Date.
	 */
	private static boolean typeImplementsDate(ITypeBinding typeBinding) {
		if (typeBinding == null) {
			return false;
		}
		return JDTTools.findTypeInHierarchy(typeBinding, DATE_TYPE_NAME) != null;
	}

	private static final String DATE_TYPE_NAME = java.util.Date.class.getName();
	
	/**
	 * Return whether the specified type implements java.util.Calendar.
	 */
	private static boolean typeImplementsCalendar(ITypeBinding typeBinding) {
		if (typeBinding == null) {
			return false;
		}
		return JDTTools.findTypeInHierarchy(typeBinding, CALENDAR_TYPE_NAME) != null;
	}

	private static final String CALENDAR_TYPE_NAME = java.util.Calendar.class.getName();

	/**
	 * Return whether the specified type implements java.io.Serializable.
	 */
	private static boolean typeIsValueHolder(ITypeBinding typeBinding) {
		if (typeBinding == null) {
			return false;
		}
		return typeBinding.getQualifiedName().equals(VALUE_HOLDER_INTERFACE_NAME);
	}
	private static final String VALUE_HOLDER_INTERFACE_NAME = "org.eclipse.persistence.indirection.ValueHolderInterface"; //$NON-NLS-1$
	
	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.getName());
	}

}

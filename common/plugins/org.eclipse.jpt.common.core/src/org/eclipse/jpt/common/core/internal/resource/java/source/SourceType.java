/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.resource.java.source;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jpt.common.core.internal.resource.java.InheritedAttributeKey;
import org.eclipse.jpt.common.core.internal.utility.jdt.ASTTools;
import org.eclipse.jpt.common.core.internal.utility.jdt.JDTType;
import org.eclipse.jpt.common.core.internal.utility.jdt.JavaResourceTypeBinding;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.resource.java.JavaResourceCompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceEnum;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.utility.jdt.Type;
import org.eclipse.jpt.common.core.utility.jdt.TypeBinding;
import org.eclipse.jpt.common.utility.MethodSignature;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.reference.SimpleIntReference;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * Java source type (type or interface)
 */
final class SourceType
		extends SourceAbstractType<Type>
		implements JavaResourceType {
	
	private String superclassQualifiedName;
	
	private boolean abstract_;  // 'abstract' is a reserved word
	
	private boolean hasNoArgConstructor;
	
	private boolean hasPrivateNoArgConstructor;
	
	private final Vector<JavaResourceType> types;
	
	private final Vector<JavaResourceEnum> enums;
	
	private final Vector<JavaResourceField> fields;
	
	private final Vector<JavaResourceMethod> methods;
	
	private final Map<InheritedAttributeKey, JavaResourceTypeBinding> inheritedFieldTypes;
	
	private final Map<InheritedAttributeKey, JavaResourceTypeBinding> inheritedMethodTypes;
	

	// ********** construction/initialization **********

	/**
	 * build top-level type
	 */
	static JavaResourceType newInstance(
			JavaResourceCompilationUnit javaResourceCompilationUnit,
			TypeDeclaration typeDeclaration) {
		Type type = new JDTType(
				typeDeclaration,
				javaResourceCompilationUnit.getCompilationUnit(),
				javaResourceCompilationUnit.getModifySharedDocumentCommandExecutor(),
				javaResourceCompilationUnit.getAnnotationEditFormatter());
		SourceType jrpt = new SourceType(javaResourceCompilationUnit, type);
		jrpt.initialize(typeDeclaration);
		return jrpt;
	}

	/**
	 * build nested type
	 */
	private static JavaResourceType newInstance(
			JavaResourceCompilationUnit javaResourceCompilationUnit,
			Type declaringType,
			TypeDeclaration typeDeclaration,
			int occurrence) {
		Type type = new JDTType(
				declaringType,
				typeDeclaration,
				occurrence,
				javaResourceCompilationUnit.getCompilationUnit(),
				javaResourceCompilationUnit.getModifySharedDocumentCommandExecutor(),
				javaResourceCompilationUnit.getAnnotationEditFormatter());
		SourceType jrpt = new SourceType(javaResourceCompilationUnit, type);
		jrpt.initialize(typeDeclaration);
		return jrpt;
	}

	private SourceType(JavaResourceCompilationUnit javaResourceCompilationUnit, Type type) {
		super(javaResourceCompilationUnit, type);
		this.types = new Vector<JavaResourceType>();
		this.enums = new Vector<JavaResourceEnum>();
		this.fields = new Vector<JavaResourceField>();
		this.methods = new Vector<JavaResourceMethod>();
		this.inheritedFieldTypes = new Hashtable<InheritedAttributeKey, JavaResourceTypeBinding>();
		this.inheritedMethodTypes = new Hashtable<InheritedAttributeKey, JavaResourceTypeBinding>();
	}

	protected void initialize(TypeDeclaration typeDeclaration) {
		super.initialize(typeDeclaration);
		this.initializeTypes(typeDeclaration);
		this.initializeEnums(typeDeclaration);
		this.initializeFields(typeDeclaration);
		this.initializeMethods(typeDeclaration);
	}

	@Override
	protected void initialize(ITypeBinding typeBinding) {
		super.initialize(typeBinding);
		this.superclassQualifiedName = this.buildSuperclassQualifiedName(typeBinding);
		this.abstract_ = this.buildAbstract(typeBinding);
		this.hasNoArgConstructor = this.buildHasNoArgConstructor(typeBinding);
		this.hasPrivateNoArgConstructor = this.buildHasPrivateNoArgConstructor(typeBinding);
		initInheritedFieldTypes(typeBinding);
		initInheritedMethodTypes(typeBinding);
	}


	// ********** update **********

	public void synchronizeWith(TypeDeclaration typeDeclaration) {
		super.synchronizeWith(typeDeclaration);
		this.syncTypes(typeDeclaration);
		this.syncEnums(typeDeclaration);
		this.syncFields(typeDeclaration);
		this.syncMethods(typeDeclaration);
	}

	@Override
	protected void synchronizeWith(ITypeBinding typeBinding) {
		super.synchronizeWith(typeBinding);
		syncSuperclassQualifiedName(buildSuperclassQualifiedName(typeBinding));
		syncAbstract(buildAbstract(typeBinding));
		syncHasNoArgConstructor(buildHasNoArgConstructor(typeBinding));
		syncHasPrivateNoArgConstructor(buildHasPrivateNoArgConstructor(typeBinding));
		syncInheritedFieldTypes(typeBinding);
		syncInheritedMethodTypes(typeBinding);
	}


	// ********** SourceAnnotatedElement implementation **********

	public void resolveTypes(TypeDeclaration typeDeclaration) {

		this.syncSuperclassQualifiedName(this.buildSuperclassQualifiedName(typeDeclaration.resolveBinding()));

		FieldDeclaration[] fieldDeclarations = typeDeclaration.getFields();
		CounterMap counters = new CounterMap(fieldDeclarations.length);
		HashSet<JavaResourceField> remainingFields = new HashSet<JavaResourceField>(this.fields);
		for (FieldDeclaration fieldDeclaration : fieldDeclarations) {
			for (VariableDeclarationFragment fragment : fragments(fieldDeclaration)) {
				String fieldName = fragment.getName().getFullyQualifiedName();
				int occurrence = counters.increment(fieldName);

				JavaResourceField field = getField(remainingFields, fieldName, occurrence);
				field.resolveTypes(fieldDeclaration, fragment);
				remainingFields.remove(field);
			}
		}

		// a new type can trigger a method parameter type to be a resolved,
		// fully-qualified name, so we need to rebuild our list of methods:
		//     "setFoo(Foo)" is not the same as "setFoo(com.bar.Foo)"
		// and, vice-versa, a removed type can "unresolve" a parameter type
		//thus we are not calling resolveTypes on methods, that would be redundant to syncMethods 
		this.syncMethods(typeDeclaration);

		TypeDeclaration[] typeDeclarations = typeDeclaration.getTypes();
		int i = 0;
		for (JavaResourceType type : this.getTypes()) {
			type.resolveTypes(typeDeclarations[i++]);
		}
		
		EnumDeclaration[] enumDeclarations = enums(typeDeclaration);
		i = 0;
		for (JavaResourceEnum enum_ : this.getEnums()) {
			enum_.resolveTypes(enumDeclarations[i++]);
		}
	}


	// ******** JavaResourceType implementation ********
	
	public AstNodeType getAstNodeType() {
		return AstNodeType.TYPE;
	}
	
	// ***** superclass qualified name
	public String getSuperclassQualifiedName() {
		return this.superclassQualifiedName;
	}

	private void syncSuperclassQualifiedName(String astSuperclassQualifiedName) {
		String old = this.superclassQualifiedName;
		this.superclassQualifiedName = astSuperclassQualifiedName;
		this.firePropertyChanged(SUPERCLASS_QUALIFIED_NAME_PROPERTY, old, astSuperclassQualifiedName);
	}

	private String buildSuperclassQualifiedName(ITypeBinding binding) {
		ITypeBinding superclass = binding == null ? null : binding.getSuperclass();
		return (superclass == null) ? null : superclass.getTypeDeclaration().getQualifiedName();
	}

	// ***** abstract
	public boolean isAbstract() {
		return this.abstract_;
	}

	private void syncAbstract(boolean astAbstract) {
		boolean old = this.abstract_;
		this.abstract_ = astAbstract;
		this.firePropertyChanged(ABSTRACT_PROPERTY, old, astAbstract);
	}

	private boolean buildAbstract(ITypeBinding binding) {
		return (binding == null) ? false : Modifier.isAbstract(binding.getModifiers());
	}

	// ***** no-arg constructor
	public boolean hasNoArgConstructor() {
		return this.hasNoArgConstructor;
	}

	private void syncHasNoArgConstructor(boolean astHasNoArgConstructor) {
		boolean old = this.hasNoArgConstructor;
		this.hasNoArgConstructor = astHasNoArgConstructor;
		this.firePropertyChanged(NO_ARG_CONSTRUCTOR_PROPERTY, old, astHasNoArgConstructor);
	}

	private boolean buildHasNoArgConstructor(ITypeBinding binding) {
		return (binding == null) ? false : typeHasNoArgConstructor(binding);
	}

	protected static boolean typeHasNoArgConstructor(ITypeBinding binding) {
		return findNoArgConstructor(binding) != null;
	}
	
	protected static IMethodBinding findNoArgConstructor(ITypeBinding binding) {
		for (IMethodBinding method : binding.getDeclaredMethods()) {
			if (method.isConstructor()) {
				if (method.getParameterTypes().length == 0) {
					return method;
				}
			}
		}
		return null;
	}

	// ***** private no-arg constructor
	public boolean hasPrivateNoArgConstructor() {
		return this.hasPrivateNoArgConstructor;
	}

	private void syncHasPrivateNoArgConstructor(boolean astHasPrivateNoArgConstructor) {
		boolean old = this.hasPrivateNoArgConstructor;
		this.hasPrivateNoArgConstructor = astHasPrivateNoArgConstructor;
		this.firePropertyChanged(PRIVATE_NO_ARG_CONSTRUCTOR_PROPERTY, old, astHasPrivateNoArgConstructor);
	}

	private boolean buildHasPrivateNoArgConstructor(ITypeBinding binding) {
		return (binding == null) ? false : typeHasPrivateNoArgConstructor(binding);
	}

	protected static boolean typeHasPrivateNoArgConstructor(ITypeBinding binding) {
		IMethodBinding method = findNoArgConstructor(binding);
		return (method != null) && Modifier.isPrivate(method.getModifiers());
	}
	
	
	// ***** public/protected no-arg constructor *****
	
	public boolean hasPublicOrProtectedNoArgConstructor() {
		Iterable<JavaResourceMethod> constructors = getConstructors();
		if (IterableTools.size(constructors) == 0) {
			return true;
		}
		for (JavaResourceMethod constructor : constructors) {
			if (constructor.getParametersSize() == 0) {
				return Modifier.isPublic(constructor.getModifiers())
						|| Modifier.isProtected(constructor.getModifiers());
			}
		}
		return false;
	}
	
	public boolean hasPublicNoArgConstructor() {
		Iterable<JavaResourceMethod> constructors = this.getConstructors();
		if (IterableTools.size(constructors) == 0) {
			return true;
		}
		for (JavaResourceMethod constructor : constructors) {
			if (constructor.getParametersSize() == 0) {
				return Modifier.isPublic(constructor.getModifiers());
			}
		}
		return false;
	}

	protected Iterable<JavaResourceMethod> getConstructors() {
		return IterableTools.filter(this.getMethods(), JavaResourceMethod.IS_CONSTRUCTOR);
	}
	

	// ********** types **********

	public Iterable<JavaResourceType> getTypes() {
		return IterableTools.cloneLive(this.types);  // read-only
	}

	public Iterable<JavaResourceType> getAllTypes() {
		return IterableTools.treeIterable(this, TYPES_TRANSFORMER);
	}

	private static final Transformer<JavaResourceType, Iterator<? extends JavaResourceType>> TYPES_TRANSFORMER = new TypesTransformer();

	/* CU private */ static class TypesTransformer
		extends TransformerAdapter<JavaResourceType, Iterator<? extends JavaResourceType>>
	{
		@Override
		public Iterator<? extends JavaResourceType> transform(JavaResourceType type) {
			return type.getTypes().iterator();
		}
	}

	private JavaResourceType getType(String typeName, int occurrence) {
		for (JavaResourceType type : this.getTypes()) {
			if (type.isFor(typeName, occurrence)) {
				return type;
			}
		}
		return null;
	}

	private void addType(JavaResourceType type) {
		this.addItemToCollection(type, this.types, TYPES_COLLECTION);
	}

	private void removeTypes(Collection<JavaResourceType> remove) {
		this.removeItemsFromCollection(remove, this.types, TYPES_COLLECTION);
	}

	private void initializeTypes(TypeDeclaration typeDeclaration) {
		TypeDeclaration[] typeDeclarations = typeDeclaration.getTypes();
		CounterMap counters = new CounterMap(typeDeclarations.length);
		for (TypeDeclaration nestedTypeDeclaration : typeDeclarations) {
			String tdName = nestedTypeDeclaration.getName().getFullyQualifiedName();
			int occurrence = counters.increment(tdName);
			this.types.add(this.buildType(nestedTypeDeclaration, occurrence));
		}
	}

	private void syncTypes(TypeDeclaration typeDeclaration) {
		TypeDeclaration[] typeDeclarations = typeDeclaration.getTypes();
		CounterMap counters = new CounterMap(typeDeclarations.length);
		HashSet<JavaResourceType> typesToRemove = new HashSet<JavaResourceType>(this.types);
		for (TypeDeclaration nestedTypeDeclaration : typeDeclarations) {
			String tdName = nestedTypeDeclaration.getName().getFullyQualifiedName();
			int occurrence = counters.increment(tdName);

			JavaResourceType type = this.getType(tdName, occurrence);
			if (type == null) {
				this.addType(this.buildType(nestedTypeDeclaration, occurrence));
			} else {
				typesToRemove.remove(type);
				type.synchronizeWith(nestedTypeDeclaration);
			}
		}
		this.removeTypes(typesToRemove);
	}

	private JavaResourceType buildType(TypeDeclaration nestedTypeDeclaration, int occurrence) {
		return newInstance(this.getJavaResourceCompilationUnit(), this.annotatedElement, nestedTypeDeclaration, occurrence);
	}


	// ********** enums **********

	public Iterable<JavaResourceEnum> getEnums() {
		return IterableTools.cloneLive(this.enums);  // read-only
	}

	public Iterable<JavaResourceEnum> getAllEnums() {
		return this.getEnums();
	}

	private JavaResourceEnum getEnum(String enumName, int occurrence) {
		for (JavaResourceEnum enum_ : this.getEnums()) {
			if (enum_.isFor(enumName, occurrence)) {
				return enum_;
			}
		}
		return null;
	}

	private void addEnum(JavaResourceEnum enum_) {
		this.addItemToCollection(enum_, this.enums, ENUMS_COLLECTION);
	}

	private void removeEnums(Collection<JavaResourceEnum> remove) {
		this.removeItemsFromCollection(remove, this.enums, ENUMS_COLLECTION);
	}

	private void initializeEnums(TypeDeclaration typeDeclaration) {
		EnumDeclaration[] enumDeclarations = enums(typeDeclaration);
		CounterMap counters = new CounterMap(enumDeclarations.length);
		for (EnumDeclaration ed : enumDeclarations) {
			String tdName = ed.getName().getFullyQualifiedName();
			int occurrence = counters.increment(tdName);
			this.enums.add(this.buildEnum(ed, occurrence));
		}
	}

	private void syncEnums(TypeDeclaration typeDeclaration) {
		EnumDeclaration[] enumDeclarations = enums(typeDeclaration);
		CounterMap counters = new CounterMap(enumDeclarations.length);
		HashSet<JavaResourceEnum> enumsToRemove = new HashSet<JavaResourceEnum>(this.enums);
		for (EnumDeclaration enumDeclaration : enumDeclarations) {
			String tdName = enumDeclaration.getName().getFullyQualifiedName();
			int occurrence = counters.increment(tdName);

			JavaResourceEnum enum_ = this.getEnum(tdName, occurrence);
			if (enum_ == null) {
				this.addEnum(this.buildEnum(enumDeclaration, occurrence));
			} else {
				enumsToRemove.remove(enum_);
				enum_.synchronizeWith(enumDeclaration);
			}
		}
		this.removeEnums(enumsToRemove);
	}

	private JavaResourceEnum buildEnum(EnumDeclaration nestedEnumDeclaration, int occurrence) {
		return SourceEnum.newInstance(this.getJavaResourceCompilationUnit(), this.annotatedElement, nestedEnumDeclaration, occurrence);
	}


	// ********** fields **********

	public Iterable<JavaResourceField> getFields() {
		return IterableTools.cloneLive(this.fields);
	}

	private void addField(JavaResourceField field) {
		this.addItemToCollection(field, this.fields, FIELDS_COLLECTION);
	}

	private static JavaResourceField getField(Collection<JavaResourceField> fields, String fieldName, int occurrence) {
		for (JavaResourceField field : fields) {
			if (field.isFor(fieldName, occurrence)) {
				return field;
			}
		}
		return null;
	}

	private void removeFields(Collection<JavaResourceField> remove) {
		this.removeItemsFromCollection(remove, this.fields, FIELDS_COLLECTION);
	}

	private void initializeFields(TypeDeclaration typeDeclaration) {
		FieldDeclaration[] fieldDeclarations = typeDeclaration.getFields();
		CounterMap counters = new CounterMap(fieldDeclarations.length);
		for (FieldDeclaration fieldDeclaration : fieldDeclarations) {
			for (VariableDeclarationFragment fragment : fragments(fieldDeclaration)) {
				String fieldName = fragment.getName().getFullyQualifiedName();
				int occurrence = counters.increment(fieldName);
				this.fields.add(this.buildField(fieldName, occurrence, fieldDeclaration, fragment));
			}
		}
	}

	private void syncFields(TypeDeclaration typeDeclaration) {
		FieldDeclaration[] fieldDeclarations = typeDeclaration.getFields();
		CounterMap counters = new CounterMap(fieldDeclarations.length);
		HashSet<JavaResourceField> fieldsToRemove = new HashSet<JavaResourceField>(this.fields);
		for (FieldDeclaration fieldDeclaration : fieldDeclarations) {
			for (VariableDeclarationFragment fragment : fragments(fieldDeclaration)) {
				String fieldName = fragment.getName().getFullyQualifiedName();
				int occurrence = counters.increment(fieldName);

				JavaResourceField field = getField(fieldsToRemove, fieldName, occurrence);
				if (field == null) {
					this.addField(this.buildField(fieldName, occurrence, fieldDeclaration, fragment));
				} else {
					fieldsToRemove.remove(field);
					field.synchronizeWith(fieldDeclaration, fragment);
				}
			}
		}
		this.removeFields(fieldsToRemove);
	}

	private JavaResourceField buildField(
			String fieldName, int occurrence, 
			FieldDeclaration fieldDeclaration, VariableDeclarationFragment variableDeclaration) {
		
		return SourceField.newInstance(
				this, this.annotatedElement, fieldName, occurrence, 
				getJavaResourceCompilationUnit(), fieldDeclaration, variableDeclaration);
	}
	
	public JavaResourceField getField(String name) {
		for (JavaResourceField field : getFields()) {
			if (ObjectTools.equals(field.getName(), name)) {
				return field;
			}
		}
		return null;
	}

	// minimize scope of suppressed warnings
	@SuppressWarnings("unchecked")
	private static List<VariableDeclarationFragment> fragments(FieldDeclaration fd) {
		return fd.fragments();
	}


	// ********** methods **********

	public Iterable<JavaResourceMethod> getMethods() {
		return IterableTools.cloneLive(this.methods);
	}

	private static JavaResourceMethod getMethod(Collection<JavaResourceMethod> methods, MethodSignature signature, int occurrence) {
		for (JavaResourceMethod method : methods) {
			if (method.isFor(signature, occurrence)) {
				return method;
			}
		}
		return null;
	}

	private void addMethod(JavaResourceMethod method) {
		this.addItemToCollection(method, this.methods, METHODS_COLLECTION);
	}

	private void removeMethods(Collection<JavaResourceMethod> remove) {
		this.removeItemsFromCollection(remove, this.methods, METHODS_COLLECTION);
	}

	private void initializeMethods(TypeDeclaration typeDeclaration) {
		MethodDeclaration[] methodDeclarations = typeDeclaration.getMethods();
		CounterMap counters = new CounterMap(methodDeclarations.length);
		for (MethodDeclaration methodDeclaration : methodDeclarations) {
			MethodSignature signature = ASTTools.buildMethodSignature(methodDeclaration);
			int occurrence = counters.increment(signature);
			this.methods.add(this.buildMethod(signature, occurrence, methodDeclaration));
		}
	}

	private void syncMethods(TypeDeclaration typeDeclaration) {
		MethodDeclaration[] methodDeclarations = typeDeclaration.getMethods();
		CounterMap counters = new CounterMap(methodDeclarations.length);
		HashSet<JavaResourceMethod> methodsToRemove = new HashSet<JavaResourceMethod>(this.methods);
		for (MethodDeclaration methodDeclaration : methodDeclarations) {
			MethodSignature signature = ASTTools.buildMethodSignature(methodDeclaration);
			int occurrence = counters.increment(signature);

			JavaResourceMethod method = getMethod(methodsToRemove, signature, occurrence);
			if (method == null) {
				this.addMethod(this.buildMethod(signature, occurrence, methodDeclaration));
			} else {
				methodsToRemove.remove(method);
				method.synchronizeWith(methodDeclaration);
			}
		}
		this.removeMethods(methodsToRemove);
	}

	private JavaResourceMethod buildMethod(MethodSignature signature, int occurrence, MethodDeclaration methodDeclaration) {
		return SourceMethod.newInstance(this, this.annotatedElement, signature, occurrence, this.getJavaResourceCompilationUnit(), methodDeclaration);
	}
	
	public JavaResourceMethod getMethod(String propertyName) {
		for (JavaResourceMethod method : this.getMethods()) {
			if (ObjectTools.equals(method.getMethodName(), propertyName)) {
				return method;
			}
		}
		return null;
	}


	public boolean hasAnyAnnotatedFields() {
		for (JavaResourceField field : this.getFields()) {
			if (field.isAnnotated()) {
				return true;
			}
		}
		return false;
	}

	public boolean hasAnyAnnotatedMethods() {
		for (JavaResourceMethod method : this.getMethods()) {
			if (method.isAnnotated()) {
				return true;
			}
		}
		return false;
	}
	
	// Two more requirements for a valid equals() method:
	// 1. It should be public 
	// 2. The return type should be boolean
	// Both requirements are validated by the compiler so they are excluded here
	public boolean hasEqualsMethod() {
		for (JavaResourceMethod method : this.getMethods()) {
			if (ObjectTools.equals(method.getMethodName(), "equals") //$NON-NLS-1$
					&& method.getParametersSize() == 1
					&& ObjectTools.equals(method.getParameterTypeName(0), Object.class.getName())) {
				return true;
			}
		}
		return false;
	}
	
	// Two more requirements for a valid hashCode() method:
	// 1. It should be public 
	// 2. The return type should be int
	// Both requirements are validated by the compiler so they are excluded here
	public boolean hasHashCodeMethod() {
		for (JavaResourceMethod method : this.getMethods()) {
			if (ObjectTools.equals(method.getMethodName(), "hashCode") //$NON-NLS-1$
					&& method.getParametersSize() == 0) {
				return true;
			}
		}
		return false;
	}
	
	
	// ***** inherited field/method types *****
	
	private void initInheritedFieldTypes(ITypeBinding typeBinding) {
		if (typeBinding == null) {
			return;
		}
		ITypeBinding scTypeBinding = typeBinding.getSuperclass();
		while (scTypeBinding != null && scTypeBinding.isParameterizedType()) {
			// if the superclass is not parameterized, 
			// then this class will have no increased type information for inherited fields
			initInheritedFieldTypes_(scTypeBinding);
			scTypeBinding = scTypeBinding.getSuperclass();
		}
		
	}
	
	private void initInheritedFieldTypes_(ITypeBinding typeBinding) {
		String typeName = typeBinding.getTypeDeclaration().getQualifiedName();
		IVariableBinding[] fields = typeBinding.getDeclaredFields();
		CounterMap counters = new CounterMap(fields.length);
		for (IVariableBinding field : fields) {
			String fieldName = field.getName();
			int occurrence = counters.increment(fieldName);
			if (occurrence == 1) { // only keep the first occurrence
				this.inheritedFieldTypes.put(new InheritedAttributeKey(typeName, fieldName), new JavaResourceTypeBinding(field.getType()));
			}
		}
	}
	
	private void syncInheritedFieldTypes(ITypeBinding typeBinding) {
		ITypeBinding scTypeBinding = typeBinding == null ? null : typeBinding.getSuperclass();
		Map<InheritedAttributeKey, JavaResourceTypeBinding> removedTypes = 
				new HashMap<InheritedAttributeKey, JavaResourceTypeBinding>(this.inheritedFieldTypes);
		while (scTypeBinding != null && scTypeBinding.isParameterizedType()) {
			// if the superclass is not parameterized, 
			// then this class will have no increased type information for inherited fields
			syncInheritedFieldTypes_(scTypeBinding, removedTypes);
			scTypeBinding = scTypeBinding.getSuperclass();
		}
		for (InheritedAttributeKey removedTypeKey : removedTypes.keySet()) {
			this.inheritedFieldTypes.remove(removedTypeKey);
		}
	}
	
	private void syncInheritedFieldTypes_(ITypeBinding typeBinding, Map<InheritedAttributeKey, JavaResourceTypeBinding> removedTypes) {
		String typeName = typeBinding.getTypeDeclaration().getQualifiedName();
		IVariableBinding[] fields = typeBinding.getDeclaredFields();
		CounterMap counters = new CounterMap(fields.length);
		for (IVariableBinding field : fields) {
			String fieldName = field.getName();
			int occurrence = counters.increment(fieldName);
			if (occurrence == 1) { // only keep the first occurrence
				InheritedAttributeKey key = new InheritedAttributeKey(typeName, fieldName);
				this.inheritedFieldTypes.put(key, new JavaResourceTypeBinding(field.getType()));
				removedTypes.remove(key);
			}
		}
	}
	
	private void initInheritedMethodTypes(ITypeBinding typeBinding) {
		if (typeBinding == null) {
			return;
		}
		ITypeBinding scTypeBinding = typeBinding.getSuperclass();
		while (scTypeBinding != null && scTypeBinding.isParameterizedType()) {
			// if the superclass is not parameterized, 
			// then this class will have no increased type information for inherited fields
			initInheritedMethodTypes_(scTypeBinding);
			scTypeBinding = scTypeBinding.getSuperclass();
		}
	}
	
	private void initInheritedMethodTypes_(ITypeBinding typeBinding) {
		// we are choosing to only store types for methods with no parameters,
		// as determining whether a method overrides another can be a bit tricky with generics,
		// and in general, we are only really interested in types of "get" methods,
		// which have no parameters
		String typeName = typeBinding.getTypeDeclaration().getQualifiedName();
		IMethodBinding[] methods = typeBinding.getDeclaredMethods();
		CounterMap counters = new CounterMap(methods.length);
		for (IMethodBinding method : methods) {
			if (method.getParameterTypes().length == 0) {
				String methodName = method.getName();
				int occurrence = counters.increment(methodName);
				if (occurrence == 1) { // only keep the first occurrence
					this.inheritedMethodTypes.put(new InheritedAttributeKey(typeName, methodName), new JavaResourceTypeBinding(method.getReturnType()));
				}
			}
		}
	}
	
	private void syncInheritedMethodTypes(ITypeBinding typeBinding) {
		ITypeBinding scTypeBinding = typeBinding == null ? null : typeBinding.getSuperclass();
		Map<InheritedAttributeKey, JavaResourceTypeBinding> removedTypes = 
				new HashMap<InheritedAttributeKey, JavaResourceTypeBinding>(this.inheritedMethodTypes);
		while (scTypeBinding != null && scTypeBinding.isParameterizedType()) {
			// if the superclass is not parameterized, 
			// then this class will have no increased type information for inherited fields
			syncInheritedMethodTypes_(scTypeBinding, removedTypes);
			scTypeBinding = scTypeBinding.getSuperclass();
		}
		for (InheritedAttributeKey removedTypeKey : removedTypes.keySet()) {
			this.inheritedMethodTypes.remove(removedTypeKey);
		}
	}
	
	private void syncInheritedMethodTypes_(ITypeBinding typeBinding, Map<InheritedAttributeKey, JavaResourceTypeBinding> removedTypes) {
		// we are choosing to only store types for methods with no parameters,
		// as determining whether a method overrides another can be a bit tricky with generics,
		// and in general, we are only really interested in types of "get" methods,
		// which have no parameters
		String typeName = typeBinding.getTypeDeclaration().getQualifiedName();
		IMethodBinding[] methods = typeBinding.getDeclaredMethods();
		CounterMap counters = new CounterMap(methods.length);
		for (IMethodBinding method : methods) {
			String methodName = method.getName();
			int occurrence = counters.increment(methodName);
			if (occurrence == 1) { // only keep the first occurrence
				InheritedAttributeKey key = new InheritedAttributeKey(typeName, methodName);
				this.inheritedMethodTypes.put(key, new JavaResourceTypeBinding(method.getReturnType()));
				removedTypes.remove(key);
			}
		}
	}
	
	public TypeBinding getAttributeTypeBinding(JavaResourceAttribute attribute) {
		if (attribute.getParent() == this) {
			return attribute.getTypeBinding();
		}
		InheritedAttributeKey key = 
				new InheritedAttributeKey(attribute.getParent().getTypeBinding().getQualifiedName(), attribute.getName());
		if (attribute.getAstNodeType() == JavaResourceAnnotatedElement.AstNodeType.FIELD) {
			return this.inheritedFieldTypes.get(key);
		}
		/* attribute.getAstNodeType() == JavaResourceAnnotatedElement.AstNodeType.METHOD */
		return this.inheritedMethodTypes.get(key);
	}
	
	
	// ***** CounterMap *****

	protected static class CounterMap {
		private final HashMap<Object, SimpleIntReference> counters;

		protected CounterMap(int initialCapacity) {
			super();
			this.counters = new HashMap<Object, SimpleIntReference>(initialCapacity);
		}

		/**
		 * Return the incremented count for the specified object.
		 */
		int increment(Object o) {
			SimpleIntReference counter = this.counters.get(o);
			if (counter == null) {
				counter = new SimpleIntReference();
				this.counters.put(o, counter);
			}
			counter.increment();
			return counter.getValue();
		}
	}


	public TypeDeclaration[] getTypes(TypeDeclaration typeDeclaration) {
		return typeDeclaration.getTypes();
	}

	public EnumDeclaration[] getEnums(TypeDeclaration typeDeclaration) {
		return enums(typeDeclaration);
	}

	public FieldDeclaration[] getFields(TypeDeclaration typeDeclaration) {
		return typeDeclaration.getFields();
	}

	public MethodDeclaration[] getMethods(TypeDeclaration typeDeclaration) {
		return typeDeclaration.getMethods();
	}

	protected static EnumDeclaration[] enums(TypeDeclaration declaringTypeDeclaration) {
		List<BodyDeclaration> bd = bodyDeclarations(declaringTypeDeclaration);
		int typeCount = 0;
		for (Iterator<BodyDeclaration> it = bd.listIterator(); it.hasNext(); ) {
			if (it.next().getNodeType() == ASTNode.ENUM_DECLARATION) {
				typeCount++;
			}
		}
		EnumDeclaration[] memberEnums = new EnumDeclaration[typeCount];
		int next = 0;
		for (Iterator<BodyDeclaration> it = bd.listIterator(); it.hasNext(); ) {
			BodyDeclaration decl = it.next();
			if (decl.getNodeType() == ASTNode.ENUM_DECLARATION) {
				memberEnums[next++] = (EnumDeclaration) decl;
			}
		}
		return memberEnums;
	}

	@SuppressWarnings("unchecked")
	protected static List<BodyDeclaration> bodyDeclarations(TypeDeclaration typeDeclaration) {
		return typeDeclaration.bodyDeclarations();
	}
}

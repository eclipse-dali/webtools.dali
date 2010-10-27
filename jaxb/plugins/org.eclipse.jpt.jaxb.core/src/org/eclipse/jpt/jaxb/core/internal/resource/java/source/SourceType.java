/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.resource.java.source;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jpt.core.internal.utility.jdt.ASTTools;
import org.eclipse.jpt.core.internal.utility.jdt.JDTType;
import org.eclipse.jpt.core.utility.jdt.Type;
import org.eclipse.jpt.jaxb.core.resource.java.Annotation;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceCompilationUnit;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceType;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAccessType;
import org.eclipse.jpt.utility.MethodSignature;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.IntReference;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.utility.internal.iterables.LiveCloneIterable;
import org.eclipse.jpt.utility.internal.iterables.TreeIterable;

/**
 * Java source type
 */
final class SourceType
	extends SourceMember<Type>
	implements JavaResourceType
{
	private String name;

	private String qualifiedName;

	private String packageName;

	private String superclassQualifiedName;

	private String declaringTypeName;

	private boolean abstract_;  // 'abstract' is a reserved word

	private boolean static_;  // 'static' is a reserved word

	private boolean memberType;

	private boolean hasPrivateNoArgConstructor;

	private boolean hasNoArgConstructor;

	private final Vector<JavaResourceType> types;

	private final Vector<JavaResourceAttribute> fields;

	private final Vector<JavaResourceAttribute> methods;


	// ********** construction/initialization **********

	/**
	 * build top-level type
	 */
	static JavaResourceType newInstance(
			JavaResourceCompilationUnit javaResourceCompilationUnit,
			TypeDeclaration typeDeclaration,
			CompilationUnit astRoot) {
		Type type = new JDTType(
				typeDeclaration,
				javaResourceCompilationUnit.getCompilationUnit(),
				javaResourceCompilationUnit.getModifySharedDocumentCommandExecutor(),
				javaResourceCompilationUnit.getAnnotationEditFormatter());
		JavaResourceType jrpt = new SourceType(javaResourceCompilationUnit, type);
		jrpt.initialize(astRoot);
		return jrpt;
	}

	/**
	 * build nested type
	 */
	private static JavaResourceType newInstance(
			JavaResourceCompilationUnit javaResourceCompilationUnit,
			Type declaringType,
			TypeDeclaration typeDeclaration,
			int occurrence,
			CompilationUnit astRoot) {
		Type type = new JDTType(
				declaringType,
				typeDeclaration,
				occurrence,
				javaResourceCompilationUnit.getCompilationUnit(),
				javaResourceCompilationUnit.getModifySharedDocumentCommandExecutor(),
				javaResourceCompilationUnit.getAnnotationEditFormatter());
		JavaResourceType jrpt = new SourceType(javaResourceCompilationUnit, type);
		jrpt.initialize(astRoot);
		return jrpt;
	}

	private SourceType(JavaResourceCompilationUnit javaResourceCompilationUnit, Type type) {
		super(javaResourceCompilationUnit, type);
		this.types = new Vector<JavaResourceType>();
		this.fields = new Vector<JavaResourceAttribute>();
		this.methods = new Vector<JavaResourceAttribute>();
	}

	@Override
	public void initialize(CompilationUnit astRoot) {
		super.initialize(astRoot);
		ITypeBinding binding = this.annotatedElement.getBinding(astRoot);
		this.name = this.buildName(binding);
		this.qualifiedName = this.buildQualifiedName(binding);
		this.packageName = this.buildPackageName(binding);
		this.superclassQualifiedName = this.buildSuperclassQualifiedName(binding);
		this.declaringTypeName = this.buildDeclaringTypeName(binding);
		this.abstract_ = this.buildAbstract(binding);
		this.static_ = this.buildStatic(binding);
		this.memberType = this.buildMemberType(binding);
		this.hasNoArgConstructor = this.buildHasNoArgConstructor(binding);
		this.hasPrivateNoArgConstructor = this.buildHasPrivateNoArgConstructor(binding);
		this.initializeTypes(astRoot);
		this.initializeFields(astRoot);
		this.initializeMethods(astRoot);
	}


	// ********** update **********

	@Override
	public void synchronizeWith(CompilationUnit astRoot) {
		super.synchronizeWith(astRoot);
		ITypeBinding binding = this.annotatedElement.getBinding(astRoot);
		this.syncName(this.buildName(binding));
		this.syncQualifiedName(this.buildQualifiedName(binding));
		this.syncPackageName(this.buildPackageName(binding));
		this.syncSuperclassQualifiedName(this.buildSuperclassQualifiedName(binding));
		this.syncDeclaringTypeName(this.buildDeclaringTypeName(binding));		
		this.syncAbstract(this.buildAbstract(binding));
		this.syncStatic(this.buildStatic(binding));
		this.syncMemberType(this.buildMemberType(binding));
		this.syncHasNoArgConstructor(this.buildHasNoArgConstructor(binding));
		this.syncHasPrivateNoArgConstructor(this.buildHasPrivateNoArgConstructor(binding));
		this.syncTypes(astRoot);
		this.syncFields(astRoot);
		this.syncMethods(astRoot);
	}


	// ********** SourceAnnotatedElement implementation **********

	@Override
	Iterable<String> getValidAnnotationNames() {
		return this.getAnnotationProvider().getTypeAnnotationNames();
	}

	@Override
	Annotation buildAnnotation(String annotationName) {
		return this.getAnnotationProvider().buildTypeAnnotation(this, this.annotatedElement, annotationName);
	}

	@Override
	Annotation buildNullAnnotation(String annotationName) {
		return this.getAnnotationProvider().buildNullTypeAnnotation(this, annotationName);
	}

	@Override
	public void resolveTypes(CompilationUnit astRoot) {
		super.resolveTypes(astRoot);

		this.syncSuperclassQualifiedName(this.buildSuperclassQualifiedName(this.annotatedElement.getBinding(astRoot)));

		for (JavaResourceAttribute field : this.getFields()) {
			field.resolveTypes(astRoot);
		}

		// a new type can trigger a method parameter type to be a resolved,
		// fully-qualified name, so we need to rebuild our list of methods:
		//     "setFoo(Foo)" is not the same as "setFoo(com.bar.Foo)"
		// and, vice-versa, a removed type can "unresolve" a parameter type
		this.syncMethods(astRoot);

		for (JavaResourceAttribute method : this.getMethods()) {
			method.resolveTypes(astRoot);
		}
		for (JavaResourceType type : this.getTypes()) {
			type.resolveTypes(astRoot);
		}
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}


	// ******** JavaResourceType implementation ********

	// ***** name
	public String getName() {
		return this.name;
	}

	private void syncName(String astName) {
		String old = this.name;
		this.name = astName;
		this.firePropertyChanged(NAME_PROPERTY, old, astName);
	}

	private String buildName(ITypeBinding binding) {
		return (binding == null) ? null : binding.getName();
	}

	// ***** qualified name
	public String getQualifiedName() {
		return this.qualifiedName;
	}

	private void syncQualifiedName(String astQualifiedName) {
		String old = this.qualifiedName;
		this.qualifiedName = astQualifiedName;
		this.firePropertyChanged(QUALIFIED_NAME_PROPERTY, old, astQualifiedName);
	}

	private String buildQualifiedName(ITypeBinding binding) {
		return (binding == null) ? null : binding.getQualifiedName();
	}

	// ***** package name
	public String getPackageName() {
		return this.packageName;
	}

	private void syncPackageName(String astPackageName) {
		String old = this.packageName;
		this.packageName = astPackageName;
		this.firePropertyChanged(PACKAGE_NAME_PROPERTY, old, astPackageName);
	}

	private String buildPackageName(ITypeBinding binding) {
		return (binding == null) ? null : binding.getPackage().getName();
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
		if (binding == null) {
			return null;
		}
		ITypeBinding superclass = binding.getSuperclass();
		return (superclass == null) ? null : superclass.getTypeDeclaration().getQualifiedName();
	}

	// ***** package
	public boolean isIn(IPackageFragment packageFragment) {
		return StringTools.stringsAreEqual(packageFragment.getElementName(), this.packageName);
	}

	// ***** declaring type name
	public String getDeclaringTypeName() {
		return this.declaringTypeName;
	}

	private void syncDeclaringTypeName(String astDeclaringTypeName) {
		String old = this.declaringTypeName;
		this.declaringTypeName = astDeclaringTypeName;
		this.firePropertyChanged(DECLARING_TYPE_NAME_PROPERTY, old, astDeclaringTypeName);
	}

	private String buildDeclaringTypeName(ITypeBinding binding) {
		if (binding == null) {
			return null;
		}
		ITypeBinding declaringClass = binding.getDeclaringClass();
		return (declaringClass == null) ? null : declaringClass.getTypeDeclaration().getQualifiedName();
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

	// ***** static
	public boolean isStatic() {
		return this.static_;
	}

	private void syncStatic(boolean static_) {
		boolean old = this.static_;
		this.static_ = static_;
		this.firePropertyChanged(STATIC_PROPERTY, old, static_);
	}

	private boolean buildStatic(ITypeBinding binding) {
		return (binding == null) ? false : Modifier.isStatic(binding.getModifiers());
	}

	// ***** member type
	public boolean isMemberType() {
		return this.memberType;
	}

	private void syncMemberType(boolean memberType) {
		boolean old = this.memberType;
		this.memberType = memberType;
		this.firePropertyChanged(MEMBER_TYPE_PROPERTY, old, memberType);
	}

	private boolean buildMemberType(ITypeBinding binding) {
		return (binding == null) ? false : binding.isMember();
	}

	// ***** no-arg constructor
	public boolean hasNoArgConstructor() {
		return this.hasNoArgConstructor;
	}

	private void syncHasNoArgConstructor(boolean hasNoArgConstructor) {
		boolean old = this.hasNoArgConstructor;
		this.hasNoArgConstructor = hasNoArgConstructor;
		this.firePropertyChanged(NO_ARG_CONSTRUCTOR_PROPERTY, old, hasNoArgConstructor);
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

	private void syncHasPrivateNoArgConstructor(boolean hasPrivateNoArgConstructor) {
		boolean old = this.hasPrivateNoArgConstructor;
		this.hasPrivateNoArgConstructor = hasPrivateNoArgConstructor;
		this.firePropertyChanged(PRIVATE_NO_ARG_CONSTRUCTOR_PROPERTY, old, hasPrivateNoArgConstructor);
	}

	private boolean buildHasPrivateNoArgConstructor(ITypeBinding binding) {
		return (binding == null) ? false : typeHasPrivateNoArgConstructor(binding);
	}

	protected static boolean typeHasPrivateNoArgConstructor(ITypeBinding binding) {
		IMethodBinding method = findNoArgConstructor(binding);
		return method != null && Modifier.isPrivate(method.getModifiers());
	}

	public boolean isMapped() {
		for (Annotation each : this.getAnnotations()) {
			if (this.annotationIsMappingAnnotation(each)) {
				return true;
			}
		}
		return false;
	}

	private boolean annotationIsMappingAnnotation(Annotation annotation) {
		return CollectionTools.contains(this.getMappingAnnotationNames(), annotation.getAnnotationName());
	}

	private Iterable<String> getMappingAnnotationNames() {
		return this.getAnnotationProvider().getTypeMappingAnnotationNames();
	}

	/**
	 * check only persistable attributes
	 */
	public boolean hasAnyAnnotatedAttributes() {
		for (JavaResourceAttribute attribute: this.getPersistableAttributes()) {
			if (attribute.isAnnotated()) {
				return true;
			}
		}
		return false;
	}


	// ********** types **********

	public Iterable<JavaResourceType> getTypes() {
		return new LiveCloneIterable<JavaResourceType>(this.types);  // read-only
	}

	public Iterable<JavaResourceType> getAllTypes() {
		return new TreeIterable<JavaResourceType>(this) {
			@Override
			protected Iterator<? extends JavaResourceType> children(JavaResourceType type) {
				return type.getTypes().iterator();
			}
		};
	}

	public Iterable<JavaResourceType> getPersistableTypes() {
		return this.getPersistableMembers(this.getTypes());
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

	private void initializeTypes(CompilationUnit astRoot) {
		TypeDeclaration[] typeDeclarations = this.annotatedElement.getTypes(astRoot);
		CounterMap counters = new CounterMap(typeDeclarations.length);
		for (TypeDeclaration td : typeDeclarations) {
			String tdName = td.getName().getFullyQualifiedName();
			int occurrence = counters.increment(tdName);
			this.types.add(this.buildType(td, occurrence, astRoot));
		}
	}

	private void syncTypes(CompilationUnit astRoot) {
		TypeDeclaration[] typeDeclarations = this.annotatedElement.getTypes(astRoot);
		CounterMap counters = new CounterMap(typeDeclarations.length);
		HashSet<JavaResourceType> typesToRemove = new HashSet<JavaResourceType>(this.types);
		for (TypeDeclaration typeDeclaration : typeDeclarations) {
			String tdName = typeDeclaration.getName().getFullyQualifiedName();
			int occurrence = counters.increment(tdName);

			JavaResourceType type = this.getType(tdName, occurrence);
			if (type == null) {
				this.addType(this.buildType(typeDeclaration, occurrence, astRoot));
			} else {
				typesToRemove.remove(type);
				type.synchronizeWith(astRoot);
			}
		}
		this.removeTypes(typesToRemove);
	}

	private JavaResourceType buildType(TypeDeclaration nestedTypeDeclaration, int occurrence, CompilationUnit astRoot) {
		return newInstance(this.getJavaResourceCompilationUnit(), this.annotatedElement, nestedTypeDeclaration, occurrence, astRoot);
	}


	// ********** fields **********

	public Iterable<JavaResourceAttribute> getFields() {
		return new LiveCloneIterable<JavaResourceAttribute>(this.fields);
	}

	public Iterable<JavaResourceAttribute> getPersistableFields() {
		return this.getPersistableMembers(this.getFields());
	}

	private void addField(JavaResourceAttribute field) {
		this.addItemToCollection(field, this.fields, FIELDS_COLLECTION);
	}

	private JavaResourceAttribute getField(String fieldName, int occurrence) {
		for (JavaResourceAttribute field : this.getFields()) {
			if (field.isFor(fieldName, occurrence)) {
				return field;
			}
		}
		return null;
	}

	private void removeFields(Collection<JavaResourceAttribute> remove) {
		this.removeItemsFromCollection(remove, this.fields, FIELDS_COLLECTION);
	}

	private void initializeFields(CompilationUnit astRoot) {
		FieldDeclaration[] fieldDeclarations = this.annotatedElement.getFields(astRoot);
		CounterMap counters = new CounterMap(fieldDeclarations.length);
		for (FieldDeclaration fieldDeclaration : fieldDeclarations) {
			for (VariableDeclarationFragment fragment : fragments(fieldDeclaration)) {
				String fieldName = fragment.getName().getFullyQualifiedName();
				int occurrence = counters.increment(fieldName);
				this.fields.add(this.buildField(fieldName, occurrence, astRoot));
			}
		}
	}

	private void syncFields(CompilationUnit astRoot) {
		FieldDeclaration[] fieldDeclarations = this.annotatedElement.getFields(astRoot);
		CounterMap counters = new CounterMap(fieldDeclarations.length);
		HashSet<JavaResourceAttribute> fieldsToRemove = new HashSet<JavaResourceAttribute>(this.fields);
		for (FieldDeclaration fieldDeclaration : fieldDeclarations) {
			for (VariableDeclarationFragment fragment : fragments(fieldDeclaration)) {
				String fieldName = fragment.getName().getFullyQualifiedName();
				int occurrence = counters.increment(fieldName);

				JavaResourceAttribute field = this.getField(fieldName, occurrence);
				if (field == null) {
					this.addField(this.buildField(fieldName, occurrence, astRoot));
				} else {
					fieldsToRemove.remove(field);
					field.synchronizeWith(astRoot);
				}
			}
		}
		this.removeFields(fieldsToRemove);
	}

	private JavaResourceAttribute buildField(String fieldName, int occurrence, CompilationUnit astRoot) {
		return SourceAttribute.newInstance(this, this.annotatedElement, fieldName, occurrence, this.getJavaResourceCompilationUnit(), astRoot);
	}

	// minimize scope of suppressed warnings
	@SuppressWarnings("unchecked")
	private static List<VariableDeclarationFragment> fragments(FieldDeclaration fd) {
		return fd.fragments();
	}


	// ********** methods **********

	public Iterable<JavaResourceAttribute> getMethods() {
		return new LiveCloneIterable<JavaResourceAttribute>(this.methods);
	}

	public Iterable<JavaResourceAttribute> getPersistableProperties() {
		return this.getPersistableMembers(this.getMethods());
	}

	private JavaResourceAttribute getMethod(MethodSignature signature, int occurrence) {
		for (JavaResourceAttribute method : this.getMethods()) {
			if (method.isFor(signature, occurrence)) {
				return method;
			}
		}
		return null;
	}

	private void addMethod(JavaResourceAttribute method) {
		this.addItemToCollection(method, this.methods, METHODS_COLLECTION);
	}

	private void removeMethods(Collection<JavaResourceAttribute> remove) {
		this.removeItemsFromCollection(remove, this.methods, METHODS_COLLECTION);
	}

	private void initializeMethods(CompilationUnit astRoot) {
		MethodDeclaration[] methodDeclarations = this.annotatedElement.getMethods(astRoot);
		CounterMap counters = new CounterMap(methodDeclarations.length);
		for (MethodDeclaration methodDeclaration : methodDeclarations) {
			MethodSignature signature = ASTTools.buildMethodSignature(methodDeclaration);
			int occurrence = counters.increment(signature);
			this.methods.add(this.buildMethod(signature, occurrence, astRoot));
		}
	}

	private void syncMethods(CompilationUnit astRoot) {
		MethodDeclaration[] methodDeclarations = this.annotatedElement.getMethods(astRoot);
		CounterMap counters = new CounterMap(methodDeclarations.length);
		HashSet<JavaResourceAttribute> methodsToRemove = new HashSet<JavaResourceAttribute>(this.methods);
		for (MethodDeclaration methodDeclaration : methodDeclarations) {
			MethodSignature signature = ASTTools.buildMethodSignature(methodDeclaration);
			int occurrence = counters.increment(signature);

			JavaResourceAttribute method = this.getMethod(signature, occurrence);
			if (method == null) {
				this.addMethod(this.buildMethod(signature, occurrence, astRoot));
			} else {
				methodsToRemove.remove(method);
				method.synchronizeWith(astRoot);
			}
		}
		this.removeMethods(methodsToRemove);
	}

	private JavaResourceAttribute buildMethod(MethodSignature signature, int occurrence, CompilationUnit astRoot) {
		return SourceAttribute.newInstance(this, this.annotatedElement, signature, occurrence, this.getJavaResourceCompilationUnit(), astRoot);
	}


	// ********** attributes **********

	@SuppressWarnings("unchecked")
	public Iterable<JavaResourceAttribute> getPersistableAttributes() {
		return new CompositeIterable<JavaResourceAttribute>(
				this.getPersistableFields(),
				this.getPersistableProperties()
			);
	}

	//TODO XmlAccessType.PUBLIC_MEMBER and XmlAccessType.NONE
	public Iterable<JavaResourceAttribute> getPersistableAttributes(XmlAccessType specifiedAccess) {
		if (specifiedAccess == null) {
			throw new IllegalArgumentException("specified access is null"); //$NON-NLS-1$
		}
		return (specifiedAccess == XmlAccessType.FIELD) ?
					this.getPersistableAttributesForFieldAccessType() :
					this.getPersistableAttributesForPropertyAccessType();
	}

	private Iterable<JavaResourceAttribute> getPersistableAttributesForFieldAccessType() {
		return this.getPersistableFields();
	}

	private Iterable<JavaResourceAttribute> getPersistableAttributesForPropertyAccessType() {
		return this.getPersistableProperties();
	}


	// ********** CounterMap **********

	private static class CounterMap {
		private final HashMap<Object, IntReference> counters;

		protected CounterMap(int initialCapacity) {
			super();
			this.counters = new HashMap<Object, IntReference>(initialCapacity);
		}

		/**
		 * Return the incremented count for the specified object.
		 */
		int increment(Object o) {
			IntReference counter = this.counters.get(o);
			if (counter == null) {
				counter = new IntReference();
				this.counters.put(o, counter);
			}
			counter.increment();
			return counter.getValue();
		}
	}

}

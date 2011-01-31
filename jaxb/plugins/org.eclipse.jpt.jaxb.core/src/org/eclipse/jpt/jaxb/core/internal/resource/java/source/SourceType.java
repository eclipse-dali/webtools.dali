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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jpt.common.core.internal.utility.jdt.ASTTools;
import org.eclipse.jpt.common.core.internal.utility.jdt.JDTType;
import org.eclipse.jpt.common.core.utility.jdt.Type;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceField;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceCompilationUnit;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceEnum;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceType;
import org.eclipse.jpt.utility.MethodSignature;
import org.eclipse.jpt.utility.internal.iterables.LiveCloneIterable;
import org.eclipse.jpt.utility.internal.iterables.TreeIterable;

/**
 * Java source type
 */
final class SourceType
	extends SourceAbstractType<Type>
	implements JavaResourceType
{

	private String superclassQualifiedName;

	private boolean abstract_;  // 'abstract' is a reserved word

	private boolean hasNoArgConstructor;

	private final Vector<JavaResourceType> types;

	private final Vector<JavaResourceEnum> enums;

	private final Vector<JavaResourceField> fields;

	private final Vector<JavaResourceMethod> methods;


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
		this.enums = new Vector<JavaResourceEnum>();
		this.fields = new Vector<JavaResourceField>();
		this.methods = new Vector<JavaResourceMethod>();
	}

	@Override
	public void initialize(CompilationUnit astRoot) {
		super.initialize(astRoot);
		ITypeBinding binding = this.annotatedElement.getBinding(astRoot);
		this.superclassQualifiedName = this.buildSuperclassQualifiedName(binding);
		this.abstract_ = this.buildAbstract(binding);
		this.hasNoArgConstructor = this.buildHasNoArgConstructor(binding);
		this.initializeTypes(astRoot);
		this.initializeEnums(astRoot);
		this.initializeFields(astRoot);
		this.initializeMethods(astRoot);
	}


	// ********** update **********

	@Override
	public void synchronizeWith(CompilationUnit astRoot) {
		super.synchronizeWith(astRoot);
		ITypeBinding binding = this.annotatedElement.getBinding(astRoot);
		this.syncSuperclassQualifiedName(this.buildSuperclassQualifiedName(binding));
		this.syncAbstract(this.buildAbstract(binding));
		this.syncHasNoArgConstructor(this.buildHasNoArgConstructor(binding));
		this.syncTypes(astRoot);
		this.syncEnums(astRoot);
		this.syncFields(astRoot);
		this.syncMethods(astRoot);
	}


	// ********** SourceAnnotatedElement implementation **********

	@Override
	public void resolveTypes(CompilationUnit astRoot) {
		super.resolveTypes(astRoot);

		this.syncSuperclassQualifiedName(this.buildSuperclassQualifiedName(this.annotatedElement.getBinding(astRoot)));

		for (JavaResourceField field : this.getFields()) {
			field.resolveTypes(astRoot);
		}

		// a new type can trigger a method parameter type to be a resolved,
		// fully-qualified name, so we need to rebuild our list of methods:
		//     "setFoo(Foo)" is not the same as "setFoo(com.bar.Foo)"
		// and, vice-versa, a removed type can "unresolve" a parameter type
		this.syncMethods(astRoot);

		for (JavaResourceMethod method : this.getMethods()) {
			method.resolveTypes(astRoot);
		}
		for (JavaResourceType type : this.getTypes()) {
			type.resolveTypes(astRoot);
		}
		for (JavaResourceEnum enum_ : this.getEnums()) {
			enum_.resolveTypes(astRoot);
		}
	}


	// ******** JavaResourceType implementation ********

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


	// ********** enums **********

	public Iterable<JavaResourceEnum> getEnums() {
		return new LiveCloneIterable<JavaResourceEnum>(this.enums);  // read-only
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

	private void initializeEnums(CompilationUnit astRoot) {
		EnumDeclaration[] enumDeclarations = this.annotatedElement.getEnums(astRoot);
		CounterMap counters = new CounterMap(enumDeclarations.length);
		for (EnumDeclaration ed : enumDeclarations) {
			String tdName = ed.getName().getFullyQualifiedName();
			int occurrence = counters.increment(tdName);
			this.enums.add(this.buildEnum(ed, occurrence, astRoot));
		}
	}

	private void syncEnums(CompilationUnit astRoot) {
		EnumDeclaration[] enumDeclarations = this.annotatedElement.getEnums(astRoot);
		CounterMap counters = new CounterMap(enumDeclarations.length);
		HashSet<JavaResourceEnum> enumsToRemove = new HashSet<JavaResourceEnum>(this.enums);
		for (EnumDeclaration enumDeclaration : enumDeclarations) {
			String tdName = enumDeclaration.getName().getFullyQualifiedName();
			int occurrence = counters.increment(tdName);

			JavaResourceEnum enum_ = this.getEnum(tdName, occurrence);
			if (enum_ == null) {
				this.addEnum(this.buildEnum(enumDeclaration, occurrence, astRoot));
			} else {
				enumsToRemove.remove(enum_);
				enum_.synchronizeWith(astRoot);
			}
		}
		this.removeEnums(enumsToRemove);
	}

	private JavaResourceEnum buildEnum(EnumDeclaration nestedEnumDeclaration, int occurrence, CompilationUnit astRoot) {
		return SourceEnum.newInstance(this.getJavaResourceCompilationUnit(), this.annotatedElement, nestedEnumDeclaration, occurrence, astRoot);
	}


	// ********** fields **********

	public Iterable<JavaResourceField> getFields() {
		return new LiveCloneIterable<JavaResourceField>(this.fields);
	}

	private void addField(JavaResourceField field) {
		this.addItemToCollection(field, this.fields, FIELDS_COLLECTION);
	}

	private JavaResourceField getField(String fieldName, int occurrence) {
		for (JavaResourceField field : this.getFields()) {
			if (field.isFor(fieldName, occurrence)) {
				return field;
			}
		}
		return null;
	}

	private void removeFields(Collection<JavaResourceField> remove) {
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
		HashSet<JavaResourceField> fieldsToRemove = new HashSet<JavaResourceField>(this.fields);
		for (FieldDeclaration fieldDeclaration : fieldDeclarations) {
			for (VariableDeclarationFragment fragment : fragments(fieldDeclaration)) {
				String fieldName = fragment.getName().getFullyQualifiedName();
				int occurrence = counters.increment(fieldName);

				JavaResourceField field = this.getField(fieldName, occurrence);
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

	private JavaResourceField buildField(String fieldName, int occurrence, CompilationUnit astRoot) {
		return SourceField.newInstance(this, this.annotatedElement, fieldName, occurrence, this.getJavaResourceCompilationUnit(), astRoot);
	}

	// minimize scope of suppressed warnings
	@SuppressWarnings("unchecked")
	private static List<VariableDeclarationFragment> fragments(FieldDeclaration fd) {
		return fd.fragments();
	}


	// ********** methods **********

	public Iterable<JavaResourceMethod> getMethods() {
		return new LiveCloneIterable<JavaResourceMethod>(this.methods);
	}

	private JavaResourceMethod getMethod(MethodSignature signature, int occurrence) {
		for (JavaResourceMethod method : this.getMethods()) {
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
		HashSet<JavaResourceMethod> methodsToRemove = new HashSet<JavaResourceMethod>(this.methods);
		for (MethodDeclaration methodDeclaration : methodDeclarations) {
			MethodSignature signature = ASTTools.buildMethodSignature(methodDeclaration);
			int occurrence = counters.increment(signature);

			JavaResourceMethod method = this.getMethod(signature, occurrence);
			if (method == null) {
				this.addMethod(this.buildMethod(signature, occurrence, astRoot));
			} else {
				methodsToRemove.remove(method);
				method.synchronizeWith(astRoot);
			}
		}
		this.removeMethods(methodsToRemove);
	}

	private JavaResourceMethod buildMethod(MethodSignature signature, int occurrence, CompilationUnit astRoot) {
		return SourceMethod.newInstance(this, this.annotatedElement, signature, occurrence, this.getJavaResourceCompilationUnit(), astRoot);
	}
}

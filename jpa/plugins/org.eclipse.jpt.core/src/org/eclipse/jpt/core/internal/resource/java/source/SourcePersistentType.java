/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java.source;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jpt.core.internal.utility.jdt.JDTTools;
import org.eclipse.jpt.core.internal.utility.jdt.JDTType;
import org.eclipse.jpt.core.internal.utility.jdt.JPTTools;
import org.eclipse.jpt.core.resource.java.AccessType;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.JavaResourceCompilationUnit;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.utility.jdt.Type;
import org.eclipse.jpt.utility.MethodSignature;
import org.eclipse.jpt.utility.internal.Counter;
import org.eclipse.jpt.utility.internal.iterables.LiveCloneIterable;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.TreeIterator;

/**
 * Java source persistent type
 */
final class SourcePersistentType
	extends SourcePersistentMember<Type>
	implements JavaResourcePersistentType
{
	private String name;

	private String qualifiedName;

	private String superclassQualifiedName;

	private boolean abstract_;  // 'abstract' is a reserved word

	private final Vector<JavaResourcePersistentType> types;

	private final Vector<JavaResourcePersistentAttribute> fields;

	private final Vector<JavaResourcePersistentAttribute> methods;

	private AccessType access;


	// ********** construction/initialization **********

	/**
	 * build top-level persistent type
	 */
	static JavaResourcePersistentType newInstance(
			JavaResourceCompilationUnit javaResourceCompilationUnit,
			TypeDeclaration typeDeclaration,
			CompilationUnit astRoot) {
		Type type = new JDTType(
				typeDeclaration,
				javaResourceCompilationUnit.getCompilationUnit(),
				javaResourceCompilationUnit.getModifySharedDocumentCommandExecutor(),
				javaResourceCompilationUnit.getAnnotationEditFormatter());
		JavaResourcePersistentType jrpt = new SourcePersistentType(javaResourceCompilationUnit, type);
		jrpt.initialize(astRoot);
		return jrpt;	
	}

	/**
	 * build nested persistent type
	 */
	private static JavaResourcePersistentType newInstance(
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
		JavaResourcePersistentType jrpt = new SourcePersistentType(javaResourceCompilationUnit, type);
		jrpt.initialize(astRoot);
		return jrpt;	
	}

	private SourcePersistentType(JavaResourceCompilationUnit javaResourceCompilationUnit, Type type) {
		super(javaResourceCompilationUnit, type);
		this.types = new Vector<JavaResourcePersistentType>(); 
		this.fields = new Vector<JavaResourcePersistentAttribute>();
		this.methods = new Vector<JavaResourcePersistentAttribute>();
	}

	@Override
	public void initialize(CompilationUnit astRoot) {
		super.initialize(astRoot);
		this.name = this.buildName(astRoot);
		this.qualifiedName = this.buildQualifiedName(astRoot);
		this.superclassQualifiedName = this.buildSuperclassQualifiedName(astRoot);
		this.abstract_ = this.buildAbstract(astRoot);
		this.initializeTypes(astRoot);
		this.initializeFields(astRoot);
		this.initializeMethods(astRoot);
		// need to wait until everything is built to calculate 'access'
		this.access = this.buildAccess();
	}

	private void initializeTypes(CompilationUnit astRoot) {
		TypeDeclaration[] typeDeclarations = this.member.getTypes(astRoot);
		CounterMap counters = new CounterMap(typeDeclarations.length);
		for (TypeDeclaration td : typeDeclarations) {
			String tdName = td.getName().getFullyQualifiedName();
			int occurrence = counters.increment(tdName);
			this.types.add(this.buildType(td, occurrence, astRoot));
		}
	}

	private void initializeFields(CompilationUnit astRoot) {
		FieldDeclaration[] fieldDeclarations = this.member.getFields(astRoot);
		CounterMap counters = new CounterMap(fieldDeclarations.length);
		for (FieldDeclaration fieldDeclaration : fieldDeclarations) {
			for (VariableDeclarationFragment fragment : fragments(fieldDeclaration)) {
				String fieldName = fragment.getName().getFullyQualifiedName();
				int occurrence = counters.increment(fieldName);
				this.fields.add(this.buildField(fieldName, occurrence, astRoot));
			}
		}
	}

	private void initializeMethods(CompilationUnit astRoot) {
		MethodDeclaration[] methodDeclarations = this.member.getMethods(astRoot);
		CounterMap counters = new CounterMap(methodDeclarations.length);
		for (MethodDeclaration methodDeclaration : methodDeclarations) {
			MethodSignature signature = JDTTools.buildMethodSignature(methodDeclaration);
			int occurrence = counters.increment(signature);
			this.methods.add(this.buildMethod(signature, occurrence, astRoot));
		}
	}


	// ********** AbstractJavaResourcePersistentMember implementation **********
	
	@Override
	Iterator<String> validAnnotationNames() {
		return this.getAnnotationProvider().typeAnnotationNames();
	}
	
	@Override
	Annotation buildAnnotation(String mappingAnnotationName) {
		return this.getAnnotationProvider().
				buildTypeAnnotation(this, this.member, mappingAnnotationName);
	}
	
	@Override
	Annotation buildNullAnnotation(String annotationName) {
		return this.getAnnotationProvider().
				buildNullTypeAnnotation(this, annotationName);
	}
	
	@Override
	public void resolveTypes(CompilationUnit astRoot) {
		super.resolveTypes(astRoot);
		
		this.setSuperclassQualifiedName(this.buildSuperclassQualifiedName(astRoot));
		
		for (JavaResourcePersistentAttribute field : this.getFields()) {
			field.resolveTypes(astRoot);
		}
		
		// a new type can trigger a method parameter type to be a resolved,
		// fully-qualified name, so we need to rebuild our list of methods:
		//     "setFoo(Foo)" is not the same as "setFoo(com.bar.Foo)"
		// and, vice-versa, a removed type can "unresolve" a parameter type
		this.updateMethods(astRoot);
		
		for (JavaResourcePersistentAttribute method : this.getMethods()) {
			method.resolveTypes(astRoot);
		}
		for (JavaResourcePersistentType type : this.getTypes()) {
			type.resolveTypes(astRoot);
		}
	}
	
	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}
	
	
	// ******** JavaResourcePersistentType implementation ********

	// ***** name
	public String getName() {
		return this.name;
	}

	private void setName(String name) {
		String old = this.name;
		this.name = name;
		this.firePropertyChanged(NAME_PROPERTY, old, name);
	}

	private String buildName(CompilationUnit astRoot) {
		ITypeBinding binding = this.member.getBinding(astRoot);
		return (binding == null) ? null : binding.getName();
	}

	// ***** qualified name
	public String getQualifiedName() {
		return this.qualifiedName;
	}

	private void setQualifiedName(String qualifiedName) {
		String old = this.qualifiedName;
		this.qualifiedName = qualifiedName;
		this.firePropertyChanged(QUALIFIED_NAME_PROPERTY, old, qualifiedName);
	}

	private String buildQualifiedName(CompilationUnit astRoot) {
		ITypeBinding binding = this.member.getBinding(astRoot);
		return (binding == null) ? null : binding.getQualifiedName();
	}

	// ***** superclass qualified name
	public String getSuperclassQualifiedName() {
		return this.superclassQualifiedName;
	}

	private void setSuperclassQualifiedName(String superclassQualifiedName) {
		String old = this.superclassQualifiedName;
		this.superclassQualifiedName = superclassQualifiedName;
		this.firePropertyChanged(SUPERCLASS_QUALIFIED_NAME_PROPERTY, old, superclassQualifiedName);
	}

	private String buildSuperclassQualifiedName(CompilationUnit astRoot) {
		ITypeBinding binding = this.member.getBinding(astRoot);
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

	private void setAbstract(boolean abstract_) {
		boolean old = this.abstract_;
		this.abstract_ = abstract_;
		this.firePropertyChanged(ABSTRACT_PROPERTY, old, abstract_);
	}

	private boolean buildAbstract(CompilationUnit astRoot) {
		ITypeBinding binding = this.member.getBinding(astRoot);
		return (binding == null) ? false : Modifier.isAbstract(binding.getModifiers());	
	}

	// ***** access
	public AccessType getAccess() {
		return this.access;
	}

	// TODO
	//seems we could have a public changeAccess() api which would
	//move all annotations from fields to their corresponding methods or vice versa
	//though of course it's more complicated than that since what if the
	//corresponding field/method does not exist?
	//making this internal since it should only be set based on changes in the source, the
	//context model should not need to set this
	private void setAccess(AccessType access) {
		AccessType old = this.access;
		this.access = access;
		this.firePropertyChanged(ACCESS_PROPERTY, old, access);
	}

	private AccessType buildAccess() {
		return JPTTools.buildAccess(this);
	}

	/**
	 * check only persistable attributes
	 */
	public boolean hasAnyAnnotatedAttributes() {
		for (Iterator<JavaResourcePersistentAttribute> stream = 
					this.persistableAttributes(); stream.hasNext(); ) {
			if (stream.next().isAnnotated()) {
				return true;
			}
		}
		return false;
	}


	// ********** types **********

	public Iterator<JavaResourcePersistentType> types() {
		return this.getTypes().iterator();
	}

	private Iterable<JavaResourcePersistentType> getTypes() {
		return new LiveCloneIterable<JavaResourcePersistentType>(this.types);  // read-only
	}

	public Iterator<JavaResourcePersistentType> allTypes() {
		return new TreeIterator<JavaResourcePersistentType>(this) {
			@Override
			protected Iterator<? extends JavaResourcePersistentType> children(JavaResourcePersistentType type) {
				return type.types();
			}
		};
	}

	public Iterator<JavaResourcePersistentType> persistableTypes() {
		return this.persistableMembers(this.types());
	}

	private JavaResourcePersistentType getType(String typeName, int occurrence) {
		for (JavaResourcePersistentType type : this.getTypes()) {
			if (type.isFor(typeName, occurrence)) {
				return type;
			}
		}
		return null;
	}

	private void addType(JavaResourcePersistentType type) {
		this.addItemToCollection(type, this.types, TYPES_COLLECTION);
	}

	private void removeTypes(Collection<JavaResourcePersistentType> remove) {
		this.removeItemsFromCollection(remove, this.types, TYPES_COLLECTION);
	}


	// ********** fields **********

	public Iterator<JavaResourcePersistentAttribute> fields() {
		return this.getFields().iterator();
	}

	private Iterable<JavaResourcePersistentAttribute> getFields() {
		return new LiveCloneIterable<JavaResourcePersistentAttribute>(this.fields);
	}

	public Iterator<JavaResourcePersistentAttribute> persistableFields() {
		return this.persistableMembers(this.fields());
	}
	
	public Iterator<JavaResourcePersistentAttribute> persistableFieldsWithSpecifiedFieldAccess() {
		return new FilteringIterator<JavaResourcePersistentAttribute, JavaResourcePersistentAttribute>(this.persistableFields()) {
			@Override
			protected boolean accept(JavaResourcePersistentAttribute resourceAttribute) {
				return resourceAttribute.getSpecifiedAccess() == AccessType.FIELD;
			}
		};
	}
	
	private void addField(JavaResourcePersistentAttribute field) {
		this.addItemToCollection(field, this.fields, FIELDS_COLLECTION);
	}

	private JavaResourcePersistentAttribute getField(String fieldName, int occurrence) {
		for (JavaResourcePersistentAttribute field : this.getFields()) {
			if (field.isFor(fieldName, occurrence)) {
				return field;
			}
		}
		return null;
	}

	private void removeFields(Collection<JavaResourcePersistentAttribute> remove) {
		this.removeItemsFromCollection(remove, this.fields, FIELDS_COLLECTION);
	}


	// ********** methods **********

	public Iterator<JavaResourcePersistentAttribute> methods() {
		return this.getMethods().iterator();
	}

	private Iterable<JavaResourcePersistentAttribute> getMethods() {
		return new LiveCloneIterable<JavaResourcePersistentAttribute>(this.methods);
	}

	public Iterator<JavaResourcePersistentAttribute> persistableProperties() {
		return this.persistableMembers(this.methods());
	}
	
	public Iterator<JavaResourcePersistentAttribute> persistablePropertiesWithSpecifiedPropertyAccess() {
		return new FilteringIterator<JavaResourcePersistentAttribute, JavaResourcePersistentAttribute>(this.persistableProperties()) {
			@Override
			protected boolean accept(JavaResourcePersistentAttribute resourceAttribute) {
				return resourceAttribute.getSpecifiedAccess() == AccessType.PROPERTY;
			}
		};
	}

	private JavaResourcePersistentAttribute getMethod(MethodSignature signature, int occurrence) {
		for (JavaResourcePersistentAttribute method : this.getMethods()) {
			if (method.isFor(signature, occurrence)) {
				return method;
			}
		}
		return null;
	}

	private void addMethod(JavaResourcePersistentAttribute method) {
		this.addItemToCollection(method, this.methods, METHODS_COLLECTION);
	}

	private void removeMethods(Collection<JavaResourcePersistentAttribute> remove) {
		this.removeItemsFromCollection(remove, this.methods, METHODS_COLLECTION);
	}


	// ********** attributes **********

	@SuppressWarnings("unchecked")
	public Iterator<JavaResourcePersistentAttribute> persistableAttributes() {
		return new CompositeIterator<JavaResourcePersistentAttribute>(
				this.persistableFields(),
				this.persistableProperties()
			);
	}
	
	public Iterator<JavaResourcePersistentAttribute> persistableAttributes(AccessType specifiedAccess) {
		if (specifiedAccess == null) {
			throw new IllegalArgumentException("specified access is null"); //$NON-NLS-1$
		}
		return (specifiedAccess == AccessType.FIELD) ?
					this.persistableAttributesForFieldAccessType() :
					this.persistableAttributesForPropertyAccessType();
	}
	
	@SuppressWarnings("unchecked")
	private Iterator<JavaResourcePersistentAttribute> persistableAttributesForFieldAccessType() {
		return new CompositeIterator<JavaResourcePersistentAttribute>(
				this.persistableFields(),
				this.persistablePropertiesWithSpecifiedPropertyAccess()
			);
	}
	
	@SuppressWarnings("unchecked")
	private Iterator<JavaResourcePersistentAttribute> persistableAttributesForPropertyAccessType() {
		return new CompositeIterator<JavaResourcePersistentAttribute>(
				this.persistableProperties(),
				this.persistableFieldsWithSpecifiedFieldAccess()
			);
	}
	

	// ********** update from Java **********

	@Override
	public void update(CompilationUnit astRoot) {
		super.update(astRoot);
		this.setName(this.buildName(astRoot));
		this.setQualifiedName(this.buildQualifiedName(astRoot));
		this.setSuperclassQualifiedName(this.buildSuperclassQualifiedName(astRoot));
		this.setAbstract(this.buildAbstract(astRoot));
		this.updateTypes(astRoot);
		this.updateFields(astRoot);
		this.updateMethods(astRoot);
		// need to wait until everything is built to calculate 'access'
		this.setAccess(this.buildAccess());
	}

	private void updateTypes(CompilationUnit astRoot) {
		TypeDeclaration[] typeDeclarations = this.member.getTypes(astRoot);
		CounterMap counters = new CounterMap(typeDeclarations.length);
		HashSet<JavaResourcePersistentType> typesToRemove = new HashSet<JavaResourcePersistentType>(this.types);
		for (TypeDeclaration typeDeclaration : typeDeclarations) {
			String tdName = typeDeclaration.getName().getFullyQualifiedName();
			int occurrence = counters.increment(tdName);

			JavaResourcePersistentType type = this.getType(tdName, occurrence);
			if (type == null) {
				this.addType(this.buildType(typeDeclaration, occurrence, astRoot));
			} else {
				typesToRemove.remove(type);
				type.update(astRoot);
			}
		}
		this.removeTypes(typesToRemove);
	}

	private JavaResourcePersistentType buildType(TypeDeclaration nestedTypeDeclaration, int occurrence, CompilationUnit astRoot) {
		return newInstance(this.getJavaResourceCompilationUnit(), this.member, nestedTypeDeclaration, occurrence, astRoot);
	}

	private void updateFields(CompilationUnit astRoot) {
		FieldDeclaration[] fieldDeclarations = this.member.getFields(astRoot);
		CounterMap counters = new CounterMap(fieldDeclarations.length);
		HashSet<JavaResourcePersistentAttribute> fieldsToRemove = new HashSet<JavaResourcePersistentAttribute>(this.fields);
		for (FieldDeclaration fieldDeclaration : fieldDeclarations) {
			for (VariableDeclarationFragment fragment : fragments(fieldDeclaration)) {
				String fieldName = fragment.getName().getFullyQualifiedName();
				int occurrence = counters.increment(fieldName);

				JavaResourcePersistentAttribute field = this.getField(fieldName, occurrence);
				if (field == null) {
					this.addField(this.buildField(fieldName, occurrence, astRoot));
				} else {
					fieldsToRemove.remove(field);
					field.update(astRoot);
				}
			}
		}
		this.removeFields(fieldsToRemove);
	}

	private JavaResourcePersistentAttribute buildField(String fieldName, int occurrence, CompilationUnit astRoot) {
		return SourcePersistentAttribute.newInstance(this, this.member, fieldName, occurrence, this.getJavaResourceCompilationUnit(), astRoot);
	}

	private void updateMethods(CompilationUnit astRoot) {
		MethodDeclaration[] methodDeclarations = this.member.getMethods(astRoot);
		CounterMap counters = new CounterMap(methodDeclarations.length);
		HashSet<JavaResourcePersistentAttribute> methodsToRemove = new HashSet<JavaResourcePersistentAttribute>(this.methods);
		for (MethodDeclaration methodDeclaration : methodDeclarations) {
			MethodSignature signature = JDTTools.buildMethodSignature(methodDeclaration);
			int occurrence = counters.increment(signature);

			JavaResourcePersistentAttribute method = this.getMethod(signature, occurrence);
			if (method == null) {
				this.addMethod(this.buildMethod(signature, occurrence, astRoot));
			} else {
				methodsToRemove.remove(method);
				method.update(astRoot);
			}
		}
		this.removeMethods(methodsToRemove);
	}

	private JavaResourcePersistentAttribute buildMethod(MethodSignature signature, int occurrence, CompilationUnit astRoot) {
		return SourcePersistentAttribute.newInstance(this, this.member, signature, occurrence, this.getJavaResourceCompilationUnit(), astRoot);
	}


	// ********** static methods **********

	// minimize scope of suppressed warnings
	@SuppressWarnings("unchecked")
	private static List<VariableDeclarationFragment> fragments(FieldDeclaration fd) {
		return fd.fragments();
	}


	// ********** CounterMap **********

	private static class CounterMap {
		private final HashMap<Object, Counter> counters;

		protected CounterMap(int initialCapacity) {
			super();
			this.counters = new HashMap<Object, Counter>(initialCapacity);
		}

		/**
		 * Return the incremented count for the specified object.
		 */
		int increment(Object o) {
			Counter counter = this.counters.get(o);
			if (counter == null) {
				counter = new Counter();
				this.counters.put(o, counter);
			}
			counter.increment();
			return counter.count();
		}
	}

}

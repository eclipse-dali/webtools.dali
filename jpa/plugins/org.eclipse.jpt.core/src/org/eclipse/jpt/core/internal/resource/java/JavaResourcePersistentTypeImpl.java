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

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
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
import org.eclipse.jpt.core.resource.java.AccessType;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.JavaResourceCompilationUnit;
import org.eclipse.jpt.core.utility.jdt.Type;
import org.eclipse.jpt.utility.MethodSignature;
import org.eclipse.jpt.utility.internal.Counter;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.TreeIterator;

/**
 * 
 */
public class JavaResourcePersistentTypeImpl
	extends AbstractJavaResourcePersistentMember<Type>
	implements JavaResourcePersistentType
{
	private String name;

	private String qualifiedName;

	private String superClassQualifiedName;

	private boolean abstract_;  // 'abstract' is a reserved word

	private final Vector<JavaResourcePersistentType> types;

	private final Vector<JavaResourcePersistentAttribute> fields;

	private final Vector<JavaResourcePersistentAttribute> methods;

	private AccessType access;


	// ********** construction **********

	/**
	 * build top-level persistent type
	 */
	public static JavaResourcePersistentType newInstance(
			JavaResourceCompilationUnit javaResourceCompilationUnit,
			TypeDeclaration typeDeclaration,
			CompilationUnit astRoot) {
		Type type = new JDTType(
				typeDeclaration,
				javaResourceCompilationUnit.getCompilationUnit(),
				javaResourceCompilationUnit.getModifySharedDocumentCommandExecutor(),
				javaResourceCompilationUnit.getAnnotationEditFormatter());
		JavaResourcePersistentType jrpt = new JavaResourcePersistentTypeImpl(javaResourceCompilationUnit, type);
		jrpt.initialize(astRoot);
		return jrpt;	
	}

	/**
	 * build nested persistent type
	 */
	protected static JavaResourcePersistentType newInstance(
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
		JavaResourcePersistentType jrpt = new JavaResourcePersistentTypeImpl(javaResourceCompilationUnit, type);
		jrpt.initialize(astRoot);
		return jrpt;	
	}

	public JavaResourcePersistentTypeImpl(JavaResourceCompilationUnit javaResourceCompilationUnit, Type type) {
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
		this.superClassQualifiedName = this.buildSuperClassQualifiedName(astRoot);
		this.abstract_ = this.buildAbstract(astRoot);
		this.initializeTypes(astRoot);
		this.initializeFields(astRoot);
		this.initializeMethods(astRoot);
		this.access = this.buildAccess();
	}

	protected void initializeTypes(CompilationUnit astRoot) {
		TypeDeclaration[] typeDeclarations = this.getMember().getTypes(astRoot);
		CounterMap counters = new CounterMap(typeDeclarations.length);
		for (TypeDeclaration td : typeDeclarations) {
			String tdName = td.getName().getFullyQualifiedName();
			int occurrence = counters.increment(tdName);
			this.types.add(this.buildType(td, occurrence, astRoot));
		}
	}

	protected void initializeFields(CompilationUnit astRoot) {
		FieldDeclaration[] fieldDeclarations = this.getMember().getFields(astRoot);
		CounterMap counters = new CounterMap(fieldDeclarations.length);
		for (FieldDeclaration fieldDeclaration : fieldDeclarations) {
			for (VariableDeclarationFragment fragment : fragments(fieldDeclaration)) {
				String fieldName = fragment.getName().getFullyQualifiedName();
				int occurrence = counters.increment(fieldName);
				this.fields.add(this.buildField(fieldName, occurrence, astRoot));
			}
		}
	}

	protected void initializeMethods(CompilationUnit astRoot) {
		MethodDeclaration[] methodDeclarations = this.getMember().getMethods(astRoot);
		CounterMap counters = new CounterMap(methodDeclarations.length);
		for (MethodDeclaration methodDeclaration : methodDeclarations) {
			MethodSignature signature = JDTTools.buildMethodSignature(methodDeclaration);
			int occurrence = counters.increment(signature);
			this.methods.add(this.buildMethod(signature, occurrence, astRoot));
		}
	}


	// ********** AbstractJavaResourcePersistentMember implementation **********

	@Override
	protected Annotation buildMappingAnnotation(String mappingAnnotationName) {
		return this.getAnnotationProvider().buildTypeMappingAnnotation(this, this.getMember(), mappingAnnotationName);
	}
	
	@Override
	protected Annotation buildNullMappingAnnotation(String annotationName) {
		return this.getAnnotationProvider().buildNullTypeMappingAnnotation(this, this.getMember(), annotationName);
	}

	@Override
	protected Annotation buildSupportingAnnotation(String annotationName) {
		return this.getAnnotationProvider().buildTypeSupportingAnnotation(this, this.getMember(), annotationName);
	}
	
	@Override
	protected Annotation buildNullSupportingAnnotation(String annotationName) {
		return this.getAnnotationProvider().buildNullTypeSupportingAnnotation(this, this.getMember(), annotationName);
	}
	
	@Override
	protected ListIterator<String> validMappingAnnotationNames() {
		return this.getAnnotationProvider().typeMappingAnnotationNames();
	}
	
	@Override
	protected ListIterator<String> validSupportingAnnotationNames() {
		return this.getAnnotationProvider().typeSupportingAnnotationNames();
	}
	
	@Override
	public void resolveTypes(CompilationUnit astRoot) {
		super.resolveTypes(astRoot);

		this.setSuperClassQualifiedName(this.buildSuperClassQualifiedName(astRoot));

		for (Iterator<JavaResourcePersistentAttribute> stream = this.fields(); stream.hasNext(); ) {
			stream.next().resolveTypes(astRoot);
		}

		// a new type can trigger a method parameter type to be a resolved,
		// fully-qualified name, so we need to rebuild our list of methods:
		//     "setFoo(Foo)" is not the same as "setFoo(com.bar.Foo)"
		// and, vice-versa, a removed type can "unresolve" a parameter type
		this.updateMethods(astRoot);

		for (Iterator<JavaResourcePersistentAttribute> stream = this.methods(); stream.hasNext(); ) {
			stream.next().resolveTypes(astRoot);
		}
		synchronized (this.types) {
			for (JavaResourcePersistentType type : this.types) {
				type.resolveTypes(astRoot);
			}
		}
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}


	// ******** JavaResourcePersistentType implementation ********

	public String getName() {
		return this.name;
	}

	protected void setName(String name) {
		String old = this.name;
		this.name = name;
		this.firePropertyChanged(NAME_PROPERTY, old, name);
	}

	public String getQualifiedName() {
		return this.qualifiedName;
	}

	protected void setQualifiedName(String qualifiedName) {
		String old = this.qualifiedName;
		this.qualifiedName = qualifiedName;
		this.firePropertyChanged(QUALIFIED_NAME_PROPERTY, old, qualifiedName);
	}

	public String getSuperClassQualifiedName() {
		return this.superClassQualifiedName;
	}

	protected void setSuperClassQualifiedName(String superClassQualifiedName) {
		String old = this.superClassQualifiedName;
		this.superClassQualifiedName = superClassQualifiedName;
		this.firePropertyChanged(SUPER_CLASS_QUALIFIED_NAME_PROPERTY, old, superClassQualifiedName);
	}

	public boolean isAbstract() {
		return this.abstract_;
	}

	protected void setAbstract(boolean abstract_) {
		boolean old = this.abstract_;
		this.abstract_ = abstract_;
		this.firePropertyChanged(ABSTRACT_PROPERTY, old, abstract_);
	}

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
	protected void setAccess(AccessType access) {
		AccessType old = this.access;
		this.access = access;
		this.firePropertyChanged(ACCESS_PROPERTY, old, access);
	}

	/**
	 * check only persistable attributes
	 */
	public boolean hasAnyAttributeAnnotations() {
		for (Iterator<JavaResourcePersistentAttribute> stream = this.persistableAttributes(); stream.hasNext(); ) {
			if (stream.next().hasAnyAnnotations()) {
				return true;
			}
		}
		return false;
	}


	// ********** types **********

	public Iterator<JavaResourcePersistentType> types() {
		return new CloneIterator<JavaResourcePersistentType>(this.types);  // read-only
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
		return persistableMembers(this.types());
	}

	public Iterator<JavaResourcePersistentType> allPersistableTypes() {
		return persistableMembers(this.allTypes());
	}

	protected JavaResourcePersistentType getType(String typeName, int occurrence) {
		for (JavaResourcePersistentType type : this.types) {
			if (type.isFor(typeName, occurrence)) {
				return type;
			}
		}
		return null;
	}

	protected void addType(JavaResourcePersistentType type) {
		this.addItemToCollection(type, this.types, TYPES_COLLECTION);
	}

	protected void removeTypes(Collection<JavaResourcePersistentType> remove) {
		this.removeItemsFromCollection(remove, this.types, TYPES_COLLECTION);
	}


	// ********** fields **********

	public Iterator<JavaResourcePersistentAttribute> fields() {
		return new CloneIterator<JavaResourcePersistentAttribute>(this.fields);
	}

	public Iterator<JavaResourcePersistentAttribute> persistableFields() {
		return persistableMembers(this.fields());
	}
	
	public Iterator<JavaResourcePersistentAttribute> persistableFieldsWithSpecifiedFieldAccess() {
		return new FilteringIterator<JavaResourcePersistentAttribute, JavaResourcePersistentAttribute>(persistableFields()) {
			@Override
			protected boolean accept(JavaResourcePersistentAttribute resourceAttribute) {
				return resourceAttribute.getSpecifiedAccess() == AccessType.FIELD;
			}
		};
	}
	
	protected void addField(JavaResourcePersistentAttribute field) {
		this.addItemToCollection(field, this.fields, FIELDS_COLLECTION);
	}

	protected JavaResourcePersistentAttribute getField(String fieldName, int occurrence) {
		for (JavaResourcePersistentAttribute field : this.fields) {
			if (field.isFor(fieldName, occurrence)) {
				return field;
			}
		}
		return null;
	}

	protected void removeFields(Collection<JavaResourcePersistentAttribute> remove) {
		this.removeItemsFromCollection(remove, this.fields, FIELDS_COLLECTION);
	}


	// ********** methods **********

	public Iterator<JavaResourcePersistentAttribute> methods() {
		return new CloneIterator<JavaResourcePersistentAttribute>(this.methods);
	}

	public Iterator<JavaResourcePersistentAttribute> persistableProperties() {
		return persistableMembers(this.methods());
	}
	
	public Iterator<JavaResourcePersistentAttribute> persistablePropertiesWithSpecifiedPropertyAccess() {
		return new FilteringIterator<JavaResourcePersistentAttribute, JavaResourcePersistentAttribute>(persistableProperties()) {
			@Override
			protected boolean accept(JavaResourcePersistentAttribute resourceAttribute) {
				return resourceAttribute.getSpecifiedAccess() == AccessType.PROPERTY;
			}
		};
	}

	protected JavaResourcePersistentAttribute getMethod(MethodSignature signature, int occurrence) {
		for (JavaResourcePersistentAttribute method : this.methods) {
			if (method.isFor(signature, occurrence)) {
				return method;
			}
		}
		return null;
	}

	protected void addMethod(JavaResourcePersistentAttribute method) {
		this.addItemToCollection(method, this.methods, METHODS_COLLECTION);
	}

	protected void removeMethods(Collection<JavaResourcePersistentAttribute> remove) {
		this.removeItemsFromCollection(remove, this.methods, METHODS_COLLECTION);
	}

	// ********** attributes **********

	@SuppressWarnings("unchecked")
	public Iterator<JavaResourcePersistentAttribute> persistableAttributes() {
		return new CompositeIterator<JavaResourcePersistentAttribute>(this.persistableFields(), this.persistableProperties());
	}


	// ********** update from Java **********

	@Override
	public void update(CompilationUnit astRoot) {
		super.update(astRoot);
		this.setName(this.buildName(astRoot));
		this.setQualifiedName(this.buildQualifiedName(astRoot));
		this.setSuperClassQualifiedName(this.buildSuperClassQualifiedName(astRoot));
		this.setAbstract(this.buildAbstract(astRoot));
		this.updateTypes(astRoot);
		this.updateFields(astRoot);
		this.updateMethods(astRoot);
		this.setAccess(this.buildAccess());
	}

	protected String buildName(CompilationUnit astRoot) {
		return this.getMember().getBinding(astRoot).getName();
	}

	protected String buildQualifiedName(CompilationUnit astRoot) {
		return this.getMember().getBinding(astRoot).getQualifiedName();
	}

	protected String buildSuperClassQualifiedName(CompilationUnit astRoot) {
		ITypeBinding typeBinding = this.getMember().getBinding(astRoot);
		if (typeBinding == null) {
			return null;
		}
		ITypeBinding superClassTypeBinding = typeBinding.getSuperclass();
		if (superClassTypeBinding == null) {
			return null;
		}
		return superClassTypeBinding.getTypeDeclaration().getQualifiedName();
	}

	protected boolean buildAbstract(CompilationUnit astRoot) {
		return Modifier.isAbstract(this.getMember().getBinding(astRoot).getModifiers());	
	}

	protected void updateTypes(CompilationUnit astRoot) {
		synchronized (this.types) {
			this.updateTypes_(astRoot);
		}
	}

	protected void updateTypes_(CompilationUnit astRoot) {
		TypeDeclaration[] typeDeclarations = this.getMember().getTypes(astRoot);
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

	protected JavaResourcePersistentType buildType(TypeDeclaration nestedTypeDeclaration, int occurrence, CompilationUnit astRoot) {
		return newInstance(this.getJavaResourceCompilationUnit(), this.getMember(), nestedTypeDeclaration, occurrence, astRoot);
	}

	protected void updateFields(CompilationUnit astRoot) {
		synchronized (this.fields) {
			this.updateFields_(astRoot);
		}
	}

	protected void updateFields_(CompilationUnit astRoot) {
		FieldDeclaration[] fieldDeclarations = this.getMember().getFields(astRoot);
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

	protected JavaResourcePersistentAttribute buildField(String fieldName, int occurrence, CompilationUnit astRoot) {
		return JavaResourcePersistentAttributeImpl.newInstance(this, this.getMember(), fieldName, occurrence, this.getJavaResourceCompilationUnit(), astRoot);
	}

	protected void updateMethods(CompilationUnit astRoot) {
		synchronized (this.methods) {
			this.updateMethods_(astRoot);
		}
	}

	protected void updateMethods_(CompilationUnit astRoot) {
		MethodDeclaration[] methodDeclarations = this.getMember().getMethods(astRoot);
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

	protected JavaResourcePersistentAttribute buildMethod(MethodSignature signature, int occurrence, CompilationUnit astRoot) {
		return JavaResourcePersistentAttributeImpl.newInstance(this, this.getMember(), signature, occurrence, this.getJavaResourceCompilationUnit(), astRoot);
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
	protected AccessType buildAccess() {
		boolean hasPersistableFields = false;
		for (Iterator<JavaResourcePersistentAttribute> stream = this.persistableFields(); stream.hasNext(); ) {
			hasPersistableFields = true;
			if (stream.next().hasAnyAnnotations()) {
				// any field is annotated => FIELD
				return AccessType.FIELD;
			}
		}

		boolean hasPersistableProperties = false;
		for (Iterator<JavaResourcePersistentAttribute> stream = this.persistableProperties(); stream.hasNext(); ) {
			hasPersistableProperties = true;
			if (stream.next().hasAnyAnnotations()) {
				// none of the fields are annotated and a getter is annotated => PROPERTY
				return AccessType.PROPERTY;
			}
		}

		if (hasPersistableProperties && ! hasPersistableFields) {
			return AccessType.PROPERTY;
		}

		// if no annotations exist, access is null at the resource model level
		return null;
	}


	// ********** static methods **********

	// minimize scope of suppressed warnings
	@SuppressWarnings("unchecked")
	protected static List<VariableDeclarationFragment> fragments(FieldDeclaration fd) {
		return fd.fragments();
	}


	// ********** CounterMap **********

	protected static class CounterMap {
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

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

import java.util.Collection;
import java.util.HashMap;
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
import org.eclipse.jpt.core.resource.java.JpaCompilationUnit;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.core.utility.jdt.Type;
import org.eclipse.jpt.utility.MethodSignature;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.Counter;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;

public class JavaResourcePersistentTypeImpl
	extends AbstractJavaResourcePersistentMember<Type>
	implements JavaResourcePersistentType
{
	private String qualifiedName;

	private String name;

	private String superClassQualifiedName;

	private boolean abstract_;  // 'abstract' is a reserved word

	/**
	 * store all member types including those that aren't persistable so we can
	 * generate validation errors
	 */
	private final Vector<JavaResourcePersistentType> nestedTypes;

	private final Vector<JavaResourcePersistentAttribute> fields;

	private final Vector<JavaResourcePersistentAttribute> methods;

	private AccessType accessType;


	// ********** construction **********

	/**
	 * build top-level persistent type
	 */
	// TODO use JPA factory
	public static JavaResourcePersistentType newInstance(
			JpaCompilationUnit jpaCompilationUnit,
			TypeDeclaration typeDeclaration,
			CompilationUnit astRoot) {
		Type type = new JDTType(
				typeDeclaration,
				jpaCompilationUnit.getCompilationUnit(),
				jpaCompilationUnit.getModifySharedDocumentCommandExecutorProvider(),
				jpaCompilationUnit.getAnnotationEditFormatter());
		JavaResourcePersistentType jrpt = new JavaResourcePersistentTypeImpl(jpaCompilationUnit, type);
		jrpt.initialize(astRoot);
		return jrpt;	
	}

	/**
	 * build nested persistent type
	 */
	// TODO use JPA factory
	protected static JavaResourcePersistentType newInstance(
			JpaCompilationUnit jpaCompilationUnit,
			Type declaringType,
			TypeDeclaration typeDeclaration,
			int occurrence,
			CompilationUnit astRoot) {
		Type type = new JDTType(
				declaringType,
				typeDeclaration,
				occurrence,
				jpaCompilationUnit.getCompilationUnit(),
				jpaCompilationUnit.getModifySharedDocumentCommandExecutorProvider(),
				jpaCompilationUnit.getAnnotationEditFormatter());
		JavaResourcePersistentType jrpt = new JavaResourcePersistentTypeImpl(jpaCompilationUnit, type);
		jrpt.initialize(astRoot);
		return jrpt;	
	}

	public JavaResourcePersistentTypeImpl(JpaCompilationUnit jpaCompilationUnit, Type type) {
		super(jpaCompilationUnit, type);
		this.nestedTypes = new Vector<JavaResourcePersistentType>(); 
		this.fields = new Vector<JavaResourcePersistentAttribute>();
		this.methods = new Vector<JavaResourcePersistentAttribute>();
	}

	@Override
	public void initialize(CompilationUnit astRoot) {
		super.initialize(astRoot);
		this.qualifiedName = this.buildQualifiedName(astRoot);
		this.name = this.buildName(astRoot);
		this.superClassQualifiedName = this.buildSuperClassQualifiedName(astRoot);
		this.abstract_ = this.buildAbstract(astRoot);
		this.initializeNestedTypes(astRoot);
		this.initializeFields(astRoot);
		this.initializeMethods(astRoot);
		this.accessType = this.buildAccessType();
	}

	protected void initializeNestedTypes(CompilationUnit astRoot) {
		CounterMap counters = new CounterMap();
		for (TypeDeclaration td : this.getMember().getTypes(astRoot)) {
			String tdName = td.getName().getFullyQualifiedName();
			int occurrence = counters.increment(tdName);
			this.nestedTypes.add(this.buildNestedType(td, occurrence, astRoot));
		}
	}

	protected void initializeFields(CompilationUnit astRoot) {
		CounterMap counters = new CounterMap();
		for (FieldDeclaration fieldDeclaration : this.getMember().getFields(astRoot)) {
			for (VariableDeclarationFragment fragment : fragments(fieldDeclaration)) {
				String fieldName = fragment.getName().getFullyQualifiedName();
				int occurrence = counters.increment(fieldName);
				this.fields.add(this.buildField(fieldName, occurrence, astRoot));
			}
		}
	}

	protected void initializeMethods(CompilationUnit astRoot) {
		CounterMap counters = new CounterMap();
		for (MethodDeclaration methodDeclaration : this.getMember().getMethods(astRoot)) {
			MethodSignature signature = JDTTools.buildMethodSignature(methodDeclaration);
			int occurrence = counters.increment(signature);
			this.methods.add(this.buildMethod(signature, occurrence, astRoot));
		}
	}


	// ********** AbstractJavaResourcePersistentMember implementation **********

	@Override
	protected Annotation buildMappingAnnotation(String mappingAnnotationName) {
		return getAnnotationProvider().buildTypeMappingAnnotation(this, getMember(), mappingAnnotationName);
	}
	
	@Override
	protected Annotation buildNullMappingAnnotation(String annotationName) {
		return getAnnotationProvider().buildNullTypeMappingAnnotation(this, getMember(), annotationName);
	}

	@Override
	protected Annotation buildAnnotation(String annotationName) {
		return getAnnotationProvider().buildTypeAnnotation(this, getMember(), annotationName);
	}
	
	@Override
	protected Annotation buildNullAnnotation(String annotationName) {
		return getAnnotationProvider().buildNullTypeAnnotation(this, getMember(), annotationName);
	}
	
	@Override
	protected ListIterator<String> possibleMappingAnnotationNames() {
		return getAnnotationProvider().typeMappingAnnotationNames();
	}
	
	@Override
	protected boolean isPossibleAnnotation(String annotationName) {
		return CollectionTools.contains(getAnnotationProvider().typeAnnotationNames(), annotationName);
	}
	
	@Override
	protected boolean isPossibleMappingAnnotation(String annotationName) {
		return CollectionTools.contains(getAnnotationProvider().typeMappingAnnotationNames(), annotationName);
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
	public void resolveTypes(CompilationUnit astRoot) {
		super.resolveTypes(astRoot);

		this.setSuperClassQualifiedName(this.buildSuperClassQualifiedName(astRoot));

		for (Iterator<JavaResourcePersistentAttribute> stream = this.fields_(); stream.hasNext(); ) {
			stream.next().resolveTypes(astRoot);
		}

		// a new type can trigger a method parameter type to be a resolved,
		// fully-qualified name, so we need to rebuild our list of methods:
		//     "setFoo(Foo)" is not the same as "setFoo(com.bar.Foo)"
		// and, vice-versa, a removed type can "unresolve" a parameter type
		this.updateMethods(astRoot);

		for (Iterator<JavaResourcePersistentAttribute> stream = this.methods_(); stream.hasNext(); ) {
			stream.next().resolveTypes(astRoot);
		}
		for (Iterator<JavaResourcePersistentType> stream = this.nestedTypes_(); stream.hasNext(); ) {
			stream.next().resolveTypes(astRoot);
		}
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}


	// ******** JavaResourcePersistentType implementation ********

	public JavaResourcePersistentType getJavaPersistentTypeResource(String fullyQualifiedTypeName) {
		// TODO not sure why a null name is coming through here...  ~bjv
//		if (fullyQualifiedTypeName.equals(this.getQualifiedName())) {
		if (this.getQualifiedName().equals(fullyQualifiedTypeName)) {
			return this;
		}
		for (Iterator<JavaResourcePersistentType> stream = this.nestedTypes(); stream.hasNext(); ) {
			JavaResourcePersistentType jrpt = stream.next();
			// recurse
			JavaResourcePersistentType nestedJRPT = jrpt.getJavaPersistentTypeResource(fullyQualifiedTypeName);
			if (nestedJRPT != null) {
				return nestedJRPT;
			}
		}
		return null;
	}

	/**
	 * check only persistable attributes
	 */
	public boolean hasAnyAttributeAnnotations() {
		for (Iterator<JavaResourcePersistentAttribute> stream = this.attributes(); stream.hasNext(); ) {
			if (stream.next().hasAnyAnnotation()) {
				return true;
			}
		}
		return false;
	}
	

	// ********** nested types **********

	/**
	 * return only persistable nested types
	 */
	public Iterator<JavaResourcePersistentType> nestedTypes() {
		//TODO since we are filtering how do we handle the case where a type becomes persistable?
		//what kind of change notificiation for that case?
		return new FilteringIterator<JavaResourcePersistentType, JavaResourcePersistentType>(this.nestedTypes_()) {
			@Override
			protected boolean accept(JavaResourcePersistentType jrpt) {
				return jrpt.isPersistable();
			}
		};
	}

	/**
	 * *all* nested types
	 */
	protected Iterator<JavaResourcePersistentType> nestedTypes_() {
		return new CloneIterator<JavaResourcePersistentType>(this.nestedTypes);
	}

	protected void addNestedType(JavaResourcePersistentType nestedType) {
		this.addItemToCollection(nestedType, this.nestedTypes, NESTED_TYPES_COLLECTION);
	}

	protected void removeNestedType(JavaResourcePersistentType nestedType) {
		this.removeItemFromCollection(nestedType, this.nestedTypes, NESTED_TYPES_COLLECTION);
	}

	protected void removeNestedTypes(Collection<JavaResourcePersistentType> remove) {
		this.removeItemsFromCollection(remove, this.nestedTypes, NESTED_TYPES_COLLECTION);
	}

	protected JavaResourcePersistentType getNestedType(String typeName, int occurrence) {
		for (Iterator<JavaResourcePersistentType> stream = this.nestedTypes_(); stream.hasNext(); ) {
			JavaResourcePersistentType nestedType = stream.next();
			if (nestedType.isFor(typeName, occurrence)) {
				return nestedType;
			}
		}
		return null;
	}


	// ********** attributes **********

	/**
	 * return only persistable attributes
	 */
	// TODO since we are filtering, how do we handle the case where an attribute becomes persistable?
	// what kind of change notification for that case?
	public Iterator<JavaResourcePersistentAttribute> attributes() {
		return persistableMembers(this.attributes_());
	}

	/**
	 * *all* fields and methods
	 */
	@SuppressWarnings("unchecked")
	protected Iterator<JavaResourcePersistentAttribute> attributes_() {
		return new CompositeIterator<JavaResourcePersistentAttribute>(this.fields_(), this.methods_());
	}


	// ********** fields **********

	/**
	 * return only persistable fields
	 */
	public Iterator<JavaResourcePersistentAttribute> fields() {
		return persistableMembers(this.fields_());
	}
	
	/**
	 * *all* fields
	 */
	protected Iterator<JavaResourcePersistentAttribute> fields_() {
		return new CloneIterator<JavaResourcePersistentAttribute>(this.fields);
	}

	protected void addField(JavaResourcePersistentAttribute attribute) {
		this.addItemToCollection(attribute, this.fields, ATTRIBUTES_COLLECTION);
	}

	protected void removeField(JavaResourcePersistentAttribute attribute) {
		this.removeItemFromCollection(attribute, this.fields, ATTRIBUTES_COLLECTION);
	}

	protected void removeFields(Collection<JavaResourcePersistentAttribute> remove) {
		this.removeItemsFromCollection(remove, this.fields, ATTRIBUTES_COLLECTION);
	}

	protected JavaResourcePersistentAttribute getField(String fieldName, int occurrence) {
		for (Iterator<JavaResourcePersistentAttribute> stream = this.fields_(); stream.hasNext(); ) {
			JavaResourcePersistentAttribute field = stream.next();
			if (field.isFor(fieldName, occurrence)) {
				return field;
			}
		}
		return null;
	}


	// ********** methods **********

	/**
	 * return only persistable properties
	 */
	public Iterator<JavaResourcePersistentAttribute> properties() {
		return persistableMembers(this.methods_());
	}

	/**
	 * *all* methods
	 */
	protected Iterator<JavaResourcePersistentAttribute> methods_() {
		return new CloneIterator<JavaResourcePersistentAttribute>(this.methods);
	}

	protected void addMethod(JavaResourcePersistentAttribute attribute) {
		this.addItemToCollection(attribute, this.methods, ATTRIBUTES_COLLECTION);
	}

	protected void removeMethod(JavaResourcePersistentAttribute attribute) {
		this.removeItemFromCollection(attribute, this.methods, ATTRIBUTES_COLLECTION);
	}

	protected void removeMethods(Collection<JavaResourcePersistentAttribute> remove) {
		this.removeItemsFromCollection(remove, this.methods, ATTRIBUTES_COLLECTION);
	}

	protected JavaResourcePersistentAttribute getMethod(MethodSignature signature, int occurrence) {
		for (Iterator<JavaResourcePersistentAttribute> stream = this.methods_(); stream.hasNext(); ) {
			JavaResourcePersistentAttribute method = stream.next();
			if (method.isFor(signature, occurrence)) {
				return method;
			}
		}
		return null;
	}


	// ********** simple instance variables **********

	public String getQualifiedName() {
		return this.qualifiedName;
	}

	protected void setQualifiedName(String qualifiedName) {
		String old = this.qualifiedName;
		this.qualifiedName = qualifiedName;
		this.firePropertyChanged(QUALIFIED_NAME_PROPERTY, old, qualifiedName);
	}

	public String getName() {
		return this.name;
	}

	protected void setName(String name) {
		String old = this.name;
		this.name = name;
		this.firePropertyChanged(NAME_PROPERTY, old, name);
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
		return this.accessType;
	}

	// TODO
	//seems we could have a public changeAccess() api which would
	//move all annotations from fields to their corresponding methods or vice versa
	//though of course it's more complicated than that since what if the
	//corresponding field/method does not exist?
	//making this internal since it should only be set based on changes in the source, the
	//context model should not need to set this
	protected void setAccess(AccessType accessType) {
		AccessType old = this.accessType;
		this.accessType = accessType;
		this.firePropertyChanged(ACCESS_PROPERTY, old, accessType);
	}


	// ********** update from Java **********

	@Override
	public void update(CompilationUnit astRoot) {
		super.update(astRoot);
		this.setQualifiedName(this.buildQualifiedName(astRoot));
		this.setName(this.buildName(astRoot));
		this.setSuperClassQualifiedName(this.buildSuperClassQualifiedName(astRoot));
		this.setAbstract(this.buildAbstract(astRoot));
		this.updateNestedTypes(astRoot);
		this.updateFields(astRoot);
		this.updateMethods(astRoot);
		this.setAccess(this.buildAccessType());
	}

	protected void updateNestedTypes(CompilationUnit astRoot) {
		CounterMap counters = new CounterMap();
		@SuppressWarnings("unchecked")
		Vector<JavaResourcePersistentType> nestedTypesToRemove = (Vector<JavaResourcePersistentType>) this.nestedTypes.clone();
		for (TypeDeclaration typeDeclaration : this.getMember().getTypes(astRoot)) {
			String tdName = typeDeclaration.getName().getFullyQualifiedName();
			int occurrence = counters.increment(tdName);

			JavaResourcePersistentType nestedType = this.getNestedType(tdName, occurrence);
			if (nestedType == null) {
				this.addNestedType(this.buildNestedType(typeDeclaration, occurrence, astRoot));
			} else {
				nestedTypesToRemove.remove(nestedType);
				nestedType.update(astRoot);
			}
		}
		this.removeNestedTypes(nestedTypesToRemove);
	}

	protected void updateFields(CompilationUnit astRoot) {
		CounterMap counters = new CounterMap();
		@SuppressWarnings("unchecked")
		Vector<JavaResourcePersistentAttribute> fieldsToRemove = (Vector<JavaResourcePersistentAttribute>) this.fields.clone();
		for (FieldDeclaration fieldDeclaration : this.getMember().getFields(astRoot)) {
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

	protected void updateMethods(CompilationUnit astRoot) {
		CounterMap counters = new CounterMap();
		@SuppressWarnings("unchecked")
		Vector<JavaResourcePersistentAttribute> methodsToRemove = (Vector<JavaResourcePersistentAttribute>) this.methods.clone();
		for (MethodDeclaration methodDeclaration : this.getMember().getMethods(astRoot)) {
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


	// ********** building state from AST **********

	protected String buildQualifiedName(CompilationUnit astRoot) {
		return this.getMember().getBinding(astRoot).getQualifiedName();
	}

	protected String buildName(CompilationUnit astRoot) {
		return this.getMember().getBinding(astRoot).getName();
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

	// TODO use JPA factory
	protected JavaResourcePersistentType buildNestedType(TypeDeclaration nestedTypeDeclaration, int occurrence, CompilationUnit astRoot) {
		return newInstance(this.getJpaCompilationUnit(), this.getMember(), nestedTypeDeclaration, occurrence, astRoot);
	}

	// TODO use JPA factory
	protected JavaResourcePersistentAttribute buildField(String fieldName, int occurrence, CompilationUnit astRoot) {
		return JavaResourcePersistentAttributeImpl.newInstance(this, this.getMember(), fieldName, occurrence, this.getJpaCompilationUnit(), astRoot);
	}

	// TODO use JPA factory
	protected JavaResourcePersistentAttribute buildMethod(MethodSignature signature, int occurrence, CompilationUnit astRoot) {
		return JavaResourcePersistentAttributeImpl.newInstance(this, this.getMember(), signature, occurrence, this.getJpaCompilationUnit(), astRoot);
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
	protected AccessType buildAccessType() {
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


	// ********** static methods **********

	// minimize scope of suppressed warnings
	@SuppressWarnings("unchecked")
	protected static List<VariableDeclarationFragment> fragments(FieldDeclaration fd) {
		return fd.fragments();
	}


	// ********** StringCounter **********

	protected static class CounterMap {
		HashMap<Object, Counter> counters = new HashMap<Object, Counter>();

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

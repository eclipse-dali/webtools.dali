/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.jar;

import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Vector;

import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.internal.utility.jdt.JPTTools;
import org.eclipse.jpt.core.resource.jar.JarResourceClassFile;
import org.eclipse.jpt.core.resource.jar.JarResourcePersistentType;
import org.eclipse.jpt.core.resource.java.AccessType;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.utility.MethodSignature;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;

/**
 * JAR persistent type
 */
public class JarResourcePersistentTypeImpl
	extends AbstractJarResourcePersistentMember
	implements JarResourcePersistentType
{
	private String name;

	private String qualifiedName;

	private String superClassQualifiedName;

	private boolean abstract_;  // 'abstract' is a reserved word

	private final Vector<JavaResourcePersistentAttribute> fields;

	private final Vector<JavaResourcePersistentAttribute> methods;

	private AccessType access;


	// ********** construction/initialization **********

	public JarResourcePersistentTypeImpl(JarResourceClassFile parent, IType type) {
		super(parent, new TypeAdapter(type));
		this.name = type.getElementName();
		this.qualifiedName = type.getFullyQualifiedName();
		this.superClassQualifiedName = this.buildSuperClassQualifiedName(type);
		this.abstract_ = this.buildAbstract(type);
		this.fields = this.buildFields(type);
		this.methods = this.buildMethods(type);
		// need to wait until everything is built to calculate 'access'
		this.access = this.buildAccess();
	}


	// ********** AbstractJarResourcePersistentMember implementation **********

	@Override
	protected Annotation buildMappingAnnotation(IAnnotation jdtAnnotation) {
		return this.getAnnotationProvider().buildTypeMappingAnnotation(this, jdtAnnotation);
	}
	
	@Override
	protected Annotation buildNullMappingAnnotation(String annotationName) {
		return this.getAnnotationProvider().buildNullTypeMappingAnnotation(this, annotationName);
	}

	@Override
	protected Annotation buildSupportingAnnotation(IAnnotation jdtAnnotation) {
		return this.getAnnotationProvider().buildTypeSupportingAnnotation(this, jdtAnnotation);
	}
	
	@Override
	protected Annotation buildNullSupportingAnnotation(String annotationName) {
		return this.getAnnotationProvider().buildNullTypeSupportingAnnotation(this, annotationName);
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
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}


	// ********** JarResourcePersistentType implementation **********

	public String getName() {
		return this.name;
	}

	public String getQualifiedName() {
		return this.qualifiedName;
	}

	// ***** superclass qualified name
	public String getSuperClassQualifiedName() {
		return this.superClassQualifiedName;
	}

	protected void setSuperClassQualifiedName(String superClassQualifiedName) {
		String old = this.superClassQualifiedName;
		this.superClassQualifiedName = superClassQualifiedName;
		this.firePropertyChanged(SUPER_CLASS_QUALIFIED_NAME_PROPERTY, old, superClassQualifiedName);
	}

	protected String buildSuperClassQualifiedName(IType type) {
		try {
			return type.getSuperclassName();
		} catch (JavaModelException ex) {
			JptCorePlugin.log(ex);
			return null;
		}
	}

	// ***** abstract
	public boolean isAbstract() {
		return this.abstract_;
	}

	protected void setAbstract(boolean abstract_) {
		boolean old = this.abstract_;
		this.abstract_ = abstract_;
		this.firePropertyChanged(ABSTRACT_PROPERTY, old, abstract_);
	}

	protected boolean buildAbstract(IType type) {
		try {
			return Flags.isAbstract(type.getFlags());
		} catch (JavaModelException ex) {
			JptCorePlugin.log(ex);
			return false;
		}
	}

	// ***** access
	public AccessType getAccess() {
		return this.access;
	}

	protected void setAccess(AccessType access) {
		AccessType old = this.access;
		this.access = access;
		this.firePropertyChanged(ACCESS_PROPERTY, old, access);
	}

	protected AccessType buildAccess() {
		return JPTTools.buildAccess(this);
	}

	/**
	 * check only persistable attributes
	 */
	public boolean hasAnyAttributePersistenceAnnotations() {
		for (Iterator<JavaResourcePersistentAttribute> stream = this.persistableAttributes(); stream.hasNext(); ) {
			if (stream.next().hasAnyPersistenceAnnotations()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public IType getMember() {
		return (IType) super.getMember();
	}


	// ********** fields **********

	public Iterator<JavaResourcePersistentAttribute> fields() {
		return new CloneIterator<JavaResourcePersistentAttribute>(this.fields);
	}

	public Iterator<JavaResourcePersistentAttribute> persistableFields() {
		return persistableMembers(this.fields());
	}
	
	public Iterator<JavaResourcePersistentAttribute> persistableFieldsWithSpecifiedFieldAccess() {
		return new FilteringIterator<JavaResourcePersistentAttribute, JavaResourcePersistentAttribute>(this.persistableFields()) {
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
		synchronized (this.fields) {
			for (JavaResourcePersistentAttribute field : this.fields) {
				if (field.isFor(fieldName, occurrence)) {
					return field;
				}
			}
		}
		return null;
	}

	protected void removeFields(Collection<JavaResourcePersistentAttribute> remove) {
		this.removeItemsFromCollection(remove, this.fields, FIELDS_COLLECTION);
	}

	protected Vector<JavaResourcePersistentAttribute> buildFields(IType type) {
		IField[] jdtFields = this.getFields(type);
		Vector<JavaResourcePersistentAttribute> result = new Vector<JavaResourcePersistentAttribute>(jdtFields.length);
		for (IField jdtField : jdtFields) {
			result.add(this.buildField(jdtField));
		}
		return result;
	}

	protected IField[] getFields(IType type) {
		try {
			return type.getFields();
		} catch (JavaModelException ex) {
			JptCorePlugin.log(ex);
			return EMPTY_FIELD_ARRAY;
		}
	}
	protected static final IField[] EMPTY_FIELD_ARRAY = new IField[0];

	protected JavaResourcePersistentAttribute buildField(IField jdtField) {
		return new JarResourcePersistentAttributeImpl(this, jdtField);
	}


	// ********** methods **********

	public Iterator<JavaResourcePersistentAttribute> methods() {
		return new CloneIterator<JavaResourcePersistentAttribute>(this.methods);
	}

	public Iterator<JavaResourcePersistentAttribute> persistableProperties() {
		return persistableMembers(this.methods());
	}
	
	public Iterator<JavaResourcePersistentAttribute> persistablePropertiesWithSpecifiedPropertyAccess() {
		return new FilteringIterator<JavaResourcePersistentAttribute, JavaResourcePersistentAttribute>(this.persistableProperties()) {
			@Override
			protected boolean accept(JavaResourcePersistentAttribute resourceAttribute) {
				return resourceAttribute.getSpecifiedAccess() == AccessType.PROPERTY;
			}
		};
	}

	protected JavaResourcePersistentAttribute getMethod(MethodSignature signature, int occurrence) {
		synchronized (this.methods) {
			for (JavaResourcePersistentAttribute method : this.methods) {
				if (method.isFor(signature, occurrence)) {
					return method;
				}
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

	protected Vector<JavaResourcePersistentAttribute> buildMethods(IType type) {
		IMethod[] jdtMethods = this.getMethods(type);
		Vector<JavaResourcePersistentAttribute> result = new Vector<JavaResourcePersistentAttribute>(jdtMethods.length);
		for (IMethod jdtMethod : jdtMethods) {
			result.add(this.buildMethod(jdtMethod));
		}
		return result;
	}

	protected IMethod[] getMethods(IType type) {
		try {
			return type.getMethods();
		} catch (JavaModelException ex) {
			JptCorePlugin.log(ex);
			return EMPTY_METHOD_ARRAY;
		}
	}
	protected static final IMethod[] EMPTY_METHOD_ARRAY = new IMethod[0];

	protected JavaResourcePersistentAttribute buildMethod(IMethod jdtMethod) {
		return new JarResourcePersistentAttributeImpl(this, jdtMethod);
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
	public Iterator<JavaResourcePersistentAttribute> persistableAttributesForFieldAccessType() {
		return new CompositeIterator<JavaResourcePersistentAttribute>(
				this.persistableFields(),
				this.persistablePropertiesWithSpecifiedPropertyAccess()
			);
	}
	
	@SuppressWarnings("unchecked")
	public Iterator<JavaResourcePersistentAttribute> persistableAttributesForPropertyAccessType() {
		return new CompositeIterator<JavaResourcePersistentAttribute>(
				this.persistableProperties(),
				this.persistableFieldsWithSpecifiedFieldAccess()
			);
	}
	

	// ********** updating **********

	@Override
	public void update() {
		super.update();
		// TODO
	}

	protected static class TypeAdapter implements Adapter {
		private final IType type;

		protected TypeAdapter(IType type) {
			super();
			this.type = type;
		}

		public IType getMember() {
			return this.type;
		}

		public boolean isPersistable() {
			return true;  // we only build a JAR type if it is "persistable"
		}

		public IAnnotation[] getAnnotations() throws JavaModelException {
			return this.type.getAnnotations();
		}

	}

	// ============== TODO remove... ========================
	public Iterator<JavaResourcePersistentType> types() {
		throw new UnsupportedOperationException();
	}
	public Iterator<JavaResourcePersistentType> allTypes() {
		throw new UnsupportedOperationException();
	}
	public Iterator<JavaResourcePersistentType> persistableTypes() {
		throw new UnsupportedOperationException();
	}
	public Iterator<JavaResourcePersistentType> allPersistableTypes() {
		return null;
	}
}
